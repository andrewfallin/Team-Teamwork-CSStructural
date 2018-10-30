package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

public class SuperCheckIntegration extends AbstractCheck {

  protected int CommentCount = 0;
  protected int CommentLinesCount = 0;
  protected int begin = 0;
  protected int end = 0;
  
  protected int VariableCount = 0;
  protected int LoopCount = 0;
  protected int ExpCount = 0;
  protected int typeCastCount = 0;
  
  //private int refcount = 0;
  protected int countLocals = 0;
  protected int countExternals = 0;
  protected List<String> all = new ArrayList<String>();
  protected List<String> locals = new ArrayList<String>();
  
  @Override
  public boolean isCommentNodesRequired() {
    return true;
  }

  @Override
  public int[] getAcceptableTokens() {
    return new int[] { TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.BLOCK_COMMENT_END,
                     TokenTypes.METHOD_CALL, TokenTypes.METHOD_REF, TokenTypes.METHOD_DEF,
                     TokenTypes.VARIABLE_DEF,
                     TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE,
                     TokenTypes.EXPR, TokenTypes.TYPECAST};
  }

  @Override
  public int[] getRequiredTokens() {
    return new int[0];
  }

  @Override
  public int[] getDefaultTokens() {
    return new int[] { TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.BLOCK_COMMENT_END,
                      TokenTypes.METHOD_CALL, TokenTypes.METHOD_REF, TokenTypes.METHOD_DEF,
                      TokenTypes.VARIABLE_DEF,
                      TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE,
                      TokenTypes.EXPR, TokenTypes.TYPECAST};
  }

  public void computeCommentCount(DetailAST ast) {
    if (ast.getType() == TokenTypes.SINGLE_LINE_COMMENT || ast.getType() == TokenTypes.BLOCK_COMMENT_END) 
    {
      CommentCount++;
    }
  }
  
  public void computeCommentLineCount(DetailAST ast) {
    // Lines of comments count
    if (ast.getType() == TokenTypes.SINGLE_LINE_COMMENT) 
    {
      CommentLinesCount++;
    }
    if (ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN)
    {
      begin = ast.getLineNo();
    }
    
    if (ast.getType() == TokenTypes.BLOCK_COMMENT_END) 
    {
      end = ast.getLineNo();
      CommentLinesCount += end-begin+1;
      /*5-4=1. if a comment block begins on 4 and ends on 5, it is spanning two lines. prevent off by one with +1. */
    }
  }
  
  public void checkRefCounts(DetailAST ast) {
    if (ast.getType() == TokenTypes.METHOD_DEF)
    {
    //find ident
      locals.add(ast.getFirstChild().getNextSibling().getNextSibling().getText());//add the name of the locally defined method to this list
    }
    if (ast.getType() == TokenTypes.METHOD_CALL || ast.getType() == TokenTypes.METHOD_REF)
    {
      //find ident
      if (ast.getFirstChild().getType() == TokenTypes.IDENT)
      {//methodCall()
        all.add(ast.getFirstChild().getText());//add the reference to our list of references we made
      }
      else if (ast.getFirstChild().getType() == TokenTypes.DOT)
      {//object.methodCall()
        DetailAST node = ast.getFirstChild().getLastChild();//currnode.getDot.getLastChild
        while(node.getType() != TokenTypes.IDENT)
        {
          node = node.getPreviousSibling();//work back up tree
        }
        String temp = node.getPreviousSibling().getText();
        if (!locals.contains(temp))
        {
          all.add(node.getText() + "(Ext. Method)");
          //add (External Method) to the name of all external methods in case of overriden methods having same name.
        }
      }
    }
  }
  
  public void computeRefCounts() {
    List<String> commons = new ArrayList<String>(locals);
    commons.retainAll(all);
    countLocals = commons.size() + locals.size();
    countExternals = all.size() - countLocals;
  }
  
  public void computeVariableDefCount(DetailAST ast) {
    if (ast.getType() == TokenTypes.METHOD_DEF)
    {
      VariableCount++;
    }
  }
  
  public void computeLoopCount(DetailAST ast) {
    if (ast.getType() == TokenTypes.LITERAL_FOR || ast.getType() == TokenTypes.LITERAL_WHILE)
    {
      LoopCount++;
    }
  }
  
  public void computeExpressionCount(DetailAST ast) {
    if (ast.getType() == TokenTypes.EXPR)
    {
      ExpCount++;
    }
  }
  
  public void computeTypeCastCount(DetailAST ast) {
    if (ast.getType() == TokenTypes.TYPECAST)
    {
      typeCastCount++;
    }
  }
  
  @Override
  public void visitToken(DetailAST ast) {
      this.computeCommentCount(ast);
      this.computeCommentLineCount(ast);
      this.computeExpressionCount(ast);
      this.computeLoopCount(ast);
      this.computeTypeCastCount(ast);
      this.computeVariableDefCount(ast);
      this.checkRefCounts(ast);
  }
 
  // ACTING AS STUB
  @Override
  public void finishTree(DetailAST ast) {
    countLocals = 1;
    countExternals = 0;
    CommentCount = 1;
    CommentLinesCount = 4;
    VariableCount = 2;
    LoopCount = 1;
    ExpCount = 7;
    typeCastCount = 1;
    
    System.out.println("commentcount: " + CommentCount);
    System.out.println("commentlinescount: " + CommentLinesCount);
    System.out.println("localcount: " + countLocals);
    System.out.println("externalcount:" + countExternals);
    System.out.println("variablecount:" + VariableCount);
    System.out.println("loopscount: " + LoopCount);
    System.out.println("numberofexpressions: " + ExpCount);
    System.out.println("typecastcount: " + typeCastCount);
    
    all.clear();
    locals.clear();
    countLocals = 0;
    countExternals = 0;
    CommentCount = 0;
    CommentLinesCount = 0;
    VariableCount = 0;
    LoopCount = 0;
    ExpCount = 0;
    typeCastCount = 0;
  }

}