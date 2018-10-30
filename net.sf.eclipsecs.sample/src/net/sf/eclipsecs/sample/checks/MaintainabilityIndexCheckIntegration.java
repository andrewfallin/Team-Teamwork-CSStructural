package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.lang.Math;
import java.util.Set;
import java.util.HashSet;

public class MaintainabilityIndexCheckIntegration extends HalsteadChecks {
  protected int cyclo = 1;
  protected int LOC = 0;
  protected int percentComments = 0;
  protected int count = 0;
  protected int singleline = 0;
  protected int blockcom = 0;
  protected int begin = 0;
  protected int end = 0;
  protected int MI = 0;

  @Override
  public boolean isCommentNodesRequired() {
    return true;
  }
 
  @Override
  public int[] getAcceptableTokens() {
    return new int[] { TokenTypes.ASSIGN, TokenTypes.BAND, TokenTypes.BAND_ASSIGN,
        TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN, TokenTypes.BSR,
        TokenTypes.BSR_ASSIGN, TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN, TokenTypes.COLON,
        TokenTypes.COMMA, TokenTypes.DEC, TokenTypes.DIV, TokenTypes.DIV_ASSIGN, 
        TokenTypes.DOT, TokenTypes.EQUAL, TokenTypes.GE, TokenTypes.GT,
        TokenTypes.INC, TokenTypes.INDEX_OP, TokenTypes.LAND, TokenTypes.LE,
        TokenTypes.LITERAL_INSTANCEOF, TokenTypes.LNOT, TokenTypes.LOR, TokenTypes.LT,
        TokenTypes.MINUS, TokenTypes.MINUS_ASSIGN, TokenTypes.MOD, TokenTypes.MOD_ASSIGN,
        TokenTypes.NOT_EQUAL, TokenTypes.PLUS, TokenTypes.PLUS_ASSIGN, TokenTypes.POST_DEC,
        TokenTypes.POST_INC, TokenTypes.QUESTION, TokenTypes.SL, TokenTypes.SL_ASSIGN,
        TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.STAR, TokenTypes.STAR_ASSIGN,
        TokenTypes.UNARY_MINUS, TokenTypes.UNARY_PLUS, TokenTypes.STRING_LITERAL, 
        TokenTypes.CHAR_LITERAL,TokenTypes.NUM_INT, TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT, 
        TokenTypes.NUM_LONG, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_DO, TokenTypes.LITERAL_FOR,
        TokenTypes.LITERAL_IF, TokenTypes.LITERAL_SWITCH, TokenTypes.LITERAL_CASE, 
        TokenTypes.LITERAL_CATCH, TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.SINGLE_LINE_COMMENT,
        TokenTypes.BLOCK_COMMENT_END, TokenTypes.RCURLY};
  }

  @Override
  public int[] getDefaultTokens() {
    return getAcceptableTokens();
  }

  @Override
  public int[] getRequiredTokens() {
    return new int[0];
  }
  
  public void countComments(DetailAST ast) {
    
    if (ast.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
      singleline++;
    }
    if (ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
      begin = ast.getLineNo();
    }
    if (ast.getType() == TokenTypes.BLOCK_COMMENT_END) {
      end = ast.getLineNo();
      blockcom += end - begin + 1;
    }
    count = blockcom + singleline;
  }
  
  public void getCycloComplexity(DetailAST ast) {
    if (ast.getType() == TokenTypes.QUESTION || ast.getType() == TokenTypes.LITERAL_WHILE
            || ast.getType() == TokenTypes.LITERAL_DO || ast.getType() == TokenTypes.LITERAL_FOR
            || ast.getType() == TokenTypes.LITERAL_IF || ast.getType() == TokenTypes.LITERAL_SWITCH
            || ast.getType() == TokenTypes.LITERAL_CASE || ast.getType() == TokenTypes.LAND
            || ast.getType() == TokenTypes.LITERAL_CATCH || ast.getType() == TokenTypes.LOR) {
      cyclo++; 
    }
  }
  
  public void countLinesofCode(DetailAST ast) {
    if (ast.getType() == TokenTypes.RCURLY && ast.getColumnNo() == 0) {
      LOC = ast.getLineNo();
    }
  }
  
  @Override
  public void visitToken(DetailAST ast) {
    checkOperandAndOperator(ast);
    checkUniqueOperator(ast);
    checkUniqueOperand(ast);
   
    //calculates line comment count for percent comments
    countComments(ast);
    
    //each decision increases cyclomatic complexity
    getCycloComplexity(ast);
    
    //calculates lines of code
    countLinesofCode(ast);
  }
  
  public void computePercent() {
    this.percentComments = count / LOC;
  }
  
  // Acting as stub
  @Override
  public void finishTree(DetailAST ast) {
    
    // Stub Halstead Check FinishTree
    uniqueOperatorCount = 4;
    uniqueOperandCount = 7;

    halsteadLength = 19;
    halsteadVocabulary = 11;
    halsteadVolume = 65;
    halsteadDifficulty = 6; 
    halsteadEffort = 390;
    
    // Stub MI check
    MI = 106;
    
    System.out.println("maintainabilityindex: " + MI);
    
    MI = 0;
    LOC = 0;
    cyclo = 0;
    count = 0;
    operatorCount = 0;
    operandCount = 0;
    uniqueOperatorCount = 0;
    uniqueOperandCount = 0;
    halsteadLength = 0;
    halsteadVocabulary = 0;
    halsteadVolume = 0;
    halsteadDifficulty = 0;
    halsteadEffort = 0;
  }
}