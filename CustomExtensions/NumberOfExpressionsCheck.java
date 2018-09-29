package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NumberOfExpressionsCheck extends AbstractCheck {

  private int count = 0;

  @Override
  public int[] getAcceptableTokens() {
    return new int[] { TokenTypes.EXPR};
  }

  @Override
  public int[] getRequiredTokens() {
    return new int[0];
  }

  @Override
  public int[] getDefaultTokens() {
    return new int[] { TokenTypes.EXPR};
  }

  public void setMax(int limit) {

  }

  @Override
  public void visitToken(DetailAST ast) {
   this.count++;
  }
  
  public void finishTree(DetailAST ast) {
    log(ast, "numberofexpressions", count);
    count = 0;
  }
}
