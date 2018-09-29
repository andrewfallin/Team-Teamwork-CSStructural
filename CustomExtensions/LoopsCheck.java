package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class LoopsCheck extends AbstractCheck {

  private int count = 0;

  @Override
  public int[] getAcceptableTokens() {
    return new int[] { TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE};
  }

  @Override
  public int[] getRequiredTokens() {
    return new int[0];
  }

  @Override
  public int[] getDefaultTokens() {
    return new int[] {TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_DO};
  }

  public void setMax(int limit) {

  }

  @Override
  public void visitToken(DetailAST ast) {
   this.count++;
  }
  
  public void finishTree(DetailAST ast) {
    log(ast, "loopscount", count);
    count = 0;
  }
}
