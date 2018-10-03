package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NumberTypeCastCheck extends AbstractCheck {
  private int count = 0;
  
  @Override
  public int[] getAcceptableTokens() {
    return new int[] { TokenTypes.TYPECAST };
  }
  
  @Override
  public int[] getRequiredTokens() {
    return new int[0];
  }
  
  @Override
  public int[] getDefaultTokens() {
    return new int[] { TokenTypes.TYPECAST };
  }
  
  @Override
  public void visitToken(DetailAST ast) {
    this.count++;
  }
  
  @Override
  public void finishTree(DetailAST ast) {
    log(ast, "typecastcount", count);
    count = 0;
  }
}
