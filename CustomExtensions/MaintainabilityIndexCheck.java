package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.lang.Math;
import java.util.Set;
import java.util.HashSet;

public class MaintainabilityIndexCheck extends HalsteadChecks {
  private int cyclo = 1;
  private int LOC;
  private double percentComments;
  private int count = 0;
  private int begin = 0;
  private int end = 0;
  private double MI = 0;

  private Set<Integer> operatorSet = new HashSet<Integer>();
  private Set<String> operandSet = new HashSet<String>();
  
  
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
        TokenTypes.BLOCK_COMMENT_END, TokenTypes.EOF};
  }

  @Override
  public int[] getDefaultTokens() {
    return getAcceptableTokens();
  }

  @Override
  public int[] getRequiredTokens() {
    return new int[0];
  }
  
  @Override
  public void visitToken(DetailAST ast) {
    checkOperandAndOperator(ast);
    checkUniqueOperator(ast);
    checkUniqueOperand(ast);
    
    //calculates line comment count for percent comments
    if (ast.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
      count++;
    } else if (ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
      begin = ast.getLineNo();
    } else if (ast.getType() == TokenTypes.BLOCK_COMMENT_END) {
      end = ast.getLineNo();
      count += end - begin + 1;
    }
    
    //each decision increases cyclomatic complexity
    if (ast.getType() == TokenTypes.QUESTION || ast.getType() == TokenTypes.LITERAL_WHILE
            || ast.getType() == TokenTypes.LITERAL_DO || ast.getType() == TokenTypes.LITERAL_FOR
            || ast.getType() == TokenTypes.LITERAL_IF || ast.getType() == TokenTypes.LITERAL_SWITCH
            || ast.getType() == TokenTypes.LITERAL_CASE || ast.getType() == TokenTypes.LAND
            || ast.getType() == TokenTypes.LITERAL_CATCH || ast.getType() == TokenTypes.LOR) {
      cyclo++; 
    }
    
    //calculates lines of code
    if (ast.getType() == TokenTypes.EOF) {
      LOC = ast.getLineNo();
    }
  }
  
  @Override
  public void finishTree(DetailAST ast) {
    uniqueOperatorCount = operatorSet.size();
    uniqueOperandCount = operandSet.size();

    computeHalsteadLength();
    computeHalsteadVocabulary();
    computeHalsteadVolume();
    computeHalsteadDifficulty();
    computeHalsteadEffort();
    percentComments = count / LOC;
    
    MI = 171 - 5.2 * Math.log(halsteadVolume) - .23 * cyclo - 15.2 * Math.log(LOC) + 50 * Math.sin(Math.sqrt(2.4 * percentComments));
    
    log(ast, "maintainabiltyindex", MI);
    
    MI = 0;
    percentComments = 0;
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
    
    operatorSet.clear();
    operandSet.clear();
  }
}
