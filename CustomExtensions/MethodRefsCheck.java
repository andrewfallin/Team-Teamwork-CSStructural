package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MethodRefsCheck extends AbstractCheck {

  private int count = 0;
  private int countLocals = 0;
  private int countExternals = 0;
  List<String> all = new ArrayList<String>();
  List<String> locals = new ArrayList<String>();

  @Override
  public int[] getAcceptableTokens() {
    return new int[] { TokenTypes.METHOD_CALL, TokenTypes.METHOD_REF, TokenTypes.METHOD_DEF };
  }

  @Override
  public int[] getRequiredTokens() {
    return new int[0];
  }

  @Override
  public int[] getDefaultTokens() {
    return new int[] { TokenTypes.METHOD_CALL, TokenTypes.METHOD_REF, TokenTypes.METHOD_DEF };
  }

  @Override
  public void visitToken(DetailAST ast) {
    if(ast.getType() == TokenTypes.METHOD_CALL || ast.getType() == TokenTypes.METHOD_REF)
    {
      this.count++;//a method is referenced
      //find ident
      if(ast.getFirstChild().getType() == TokenTypes.IDENT)
      {//methodCall()
        this.all.add(ast.getFirstChild().getText());//add the reference to our list of references we made
      }
      else if(ast.getFirstChild().getType() == TokenTypes.DOT)
      {//object.methodCall()
        DetailAST node = ast.getFirstChild().getLastChild();//currnode.getDot.getLastChild
        while(node.getType() != TokenTypes.IDENT)
        {
          node = node.getPreviousSibling();//work back up tree
        }
        String temp = node.getPreviousSibling().getText();
        if(!locals.contains(temp))
        {
          this.all.add(node.getText() + "(Ext. Method)");
          //add (External Method) to the name of all external methods in case of overriden methods having same name.
        }
      }
    }
    if(ast.getType() == TokenTypes.METHOD_DEF)
    {
      //find ident
      this.locals.add(ast.getFirstChild().getNextSibling().getNextSibling().getText());//add the name of the locally defined method to this list
    }
  }
  
  @Override
  public void finishTree(DetailAST ast) {
    System.out.println(Arrays.toString(locals.toArray()));
    System.out.println(Arrays.toString(all.toArray()));
    List<String> commons = new ArrayList<String>(all);
    commons.retainAll(locals);
    countLocals = commons.size();
    countExternals = all.size() - countLocals;
    log(ast, "refcounts", countLocals, countExternals);
    count = 0;
    countLocals = 0;
    countExternals = 0;
    all.clear();
    locals.clear();
  }
}
