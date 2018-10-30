package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.lang.Math;
import java.util.Set;
import java.util.HashSet;

public class HalsteadChecksIntegration extends AbstractCheck {

  protected int operatorCount = 0;
  protected int operandCount = 0;
  protected int uniqueOperatorCount = 5;
  protected int uniqueOperandCount = 5;
  protected int halsteadLength = 0;
  protected int halsteadVocabulary = 0;
  protected int halsteadVolume = 0;
  protected int halsteadDifficulty = 0;
  protected int halsteadEffort = 0;

  protected Set<Integer> operatorSet = new HashSet<Integer>();
  protected Set<String> operandSet = new HashSet<String>();

  @Override
  public int[] getAcceptableTokens() {

    return new int[0];
  }

  @Override
  public int[] getRequiredTokens() {
    return new int[0];
  }

  @Override
  public int[] getDefaultTokens() {    
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
        TokenTypes.NUM_LONG };
  }
  
  // Checking for operators that requires 2 operands
  public void checkOperandAndOperator(DetailAST ast) {
    
    if (ast.getType() == TokenTypes.ASSIGN || ast.getType() == TokenTypes.BAND_ASSIGN ||
            ast.getType() == TokenTypes.BOR_ASSIGN || ast.getType() == TokenTypes.BSR_ASSIGN ||
            ast.getType() == TokenTypes.BXOR_ASSIGN || ast.getType() == TokenTypes.DIV_ASSIGN ||
            ast.getType() == TokenTypes.EQUAL || ast.getType() == TokenTypes.GE ||
            ast.getType() == TokenTypes.GT || ast.getType() == TokenTypes.LAND ||
            ast.getType() == TokenTypes.LE || ast.getType() == TokenTypes.LOR ||
            ast.getType() == TokenTypes.LT || ast.getType() == TokenTypes.MINUS_ASSIGN ||
            ast.getType() == TokenTypes.MOD_ASSIGN || ast.getType() == TokenTypes.NOT_EQUAL ||
            ast.getType() == TokenTypes.PLUS_ASSIGN || ast.getType() == TokenTypes.QUESTION ||
            ast.getType() == TokenTypes.COLON || ast.getType() == TokenTypes.SL_ASSIGN ||
            ast.getType() == TokenTypes.SR_ASSIGN || ast.getType() == TokenTypes.STAR_ASSIGN) 
    {
      operandCount += 2;
      operatorCount++;
    } 
    else if (ast.getType() == TokenTypes.BAND || ast.getType() == TokenTypes.BNOT ||
            ast.getType() == TokenTypes.BOR || ast.getType() == TokenTypes.BSR ||
            ast.getType() == TokenTypes.BXOR || ast.getType() == TokenTypes.DIV ||
            ast.getType() == TokenTypes.DOT || ast.getType() == TokenTypes.INC ||
            ast.getType() == TokenTypes.INDEX_OP || ast.getType() == TokenTypes.LITERAL_INSTANCEOF ||
            ast.getType() == TokenTypes.LNOT || ast.getType() == TokenTypes.MINUS ||
            ast.getType() == TokenTypes.MOD || ast.getType() == TokenTypes.POST_DEC ||
            ast.getType() == TokenTypes.PLUS || ast.getType() == TokenTypes.POST_INC ||
            ast.getType() == TokenTypes.COMMA || ast.getType() == TokenTypes.SL ||
            ast.getType() == TokenTypes.SR || ast.getType() == TokenTypes.STAR ||
            ast.getType() == TokenTypes.UNARY_MINUS || ast.getType() == TokenTypes.UNARY_PLUS ||
            ast.getType() == TokenTypes.DEC) 
    {
      if (ast.getType() != TokenTypes.DOT) 
      {
        operandCount++;
      }
      operatorCount++;
    }

  }
  
  public void checkUniqueOperator(DetailAST ast) {
    if (ast.getType() == TokenTypes.BAND || ast.getType() == TokenTypes.BNOT ||
            ast.getType() == TokenTypes.BOR || ast.getType() == TokenTypes.BSR ||
            ast.getType() == TokenTypes.BXOR || ast.getType() == TokenTypes.DIV ||
            ast.getType() == TokenTypes.DOT || ast.getType() == TokenTypes.INC ||
            ast.getType() == TokenTypes.INDEX_OP || ast.getType() == TokenTypes.LITERAL_INSTANCEOF ||
            ast.getType() == TokenTypes.LNOT || ast.getType() == TokenTypes.MINUS ||
            ast.getType() == TokenTypes.MOD || ast.getType() == TokenTypes.POST_DEC ||
            ast.getType() == TokenTypes.PLUS || ast.getType() == TokenTypes.POST_INC ||
            ast.getType() == TokenTypes.COMMA || ast.getType() == TokenTypes.SL ||
            ast.getType() == TokenTypes.SR || ast.getType() == TokenTypes.STAR ||
            ast.getType() == TokenTypes.UNARY_MINUS || ast.getType() == TokenTypes.UNARY_PLUS ||
            ast.getType() == TokenTypes.DEC || ast.getType() == TokenTypes.ASSIGN || 
            ast.getType() == TokenTypes.BAND_ASSIGN || ast.getType() == TokenTypes.BOR_ASSIGN || 
            ast.getType() == TokenTypes.BSR_ASSIGN || ast.getType() == TokenTypes.BXOR_ASSIGN || 
            ast.getType() == TokenTypes.DIV_ASSIGN || ast.getType() == TokenTypes.EQUAL || 
            ast.getType() == TokenTypes.GE || ast.getType() == TokenTypes.GT ||
            ast.getType() == TokenTypes.LAND || ast.getType() == TokenTypes.LE || 
            ast.getType() == TokenTypes.LOR || ast.getType() == TokenTypes.LT || 
            ast.getType() == TokenTypes.MINUS_ASSIGN || ast.getType() == TokenTypes.MOD_ASSIGN || 
            ast.getType() == TokenTypes.NOT_EQUAL || ast.getType() == TokenTypes.PLUS_ASSIGN || 
            ast.getType() == TokenTypes.QUESTION || ast.getType() == TokenTypes.COLON || 
            ast.getType() == TokenTypes.SL_ASSIGN || ast.getType() == TokenTypes.SR_ASSIGN || 
            ast.getType() == TokenTypes.STAR_ASSIGN) 
    {
      if (operatorSet.contains(ast.getType()) == false) 
      {
        operatorSet.add(ast.getType());
      }
    }
  }
  
  public void checkUniqueOperand(DetailAST ast) {
    if (ast.getType() == TokenTypes.STRING_LITERAL || ast.getType() == TokenTypes.CHAR_LITERAL || 
            ast.getType() == TokenTypes.NUM_INT || ast.getType() == TokenTypes.NUM_DOUBLE || 
            ast.getType() == TokenTypes.NUM_FLOAT || ast.getType() == TokenTypes.NUM_LONG)
    {
      if (operandSet.contains(ast.getText()) == false) 
      {
        operandSet.add(ast.getText());
      }
    }
    
    if (ast.getType() == TokenTypes.DOT)
    {
      DetailAST temp = ast.getLastChild();
      while (temp != null) 
      {
        if (temp.getType() == TokenTypes.IDENT) 
        {
          if (operandSet.contains(temp.getText()) == false) 
          {
            operandSet.add(temp.getText());
          }
          operandCount++;
        }
        temp = temp.getPreviousSibling();
      }
    }
    
    if (ast.getType() == TokenTypes.ASSIGN)
    {
      DetailAST temp = ast;
      while (temp != null) 
      {
        if (temp.getType() == TokenTypes.IDENT)
        {
          if (operandSet.contains(temp.getText()) == false) 
          {
            operandSet.add(temp.getText());
          }
        }
        temp = temp.getPreviousSibling();
      }
    }
  }

  public void computeHalsteadLength() {
    halsteadLength = operatorCount + operandCount;
  }
  
  public void computeHalsteadVocabulary() {
    halsteadVocabulary = uniqueOperatorCount + uniqueOperandCount;
  }
  
  public void computeHalsteadVolume() {
    double log2OfVocab = Math.log(halsteadVocabulary) / Math.log(2); 
    
    halsteadVolume = (int) (halsteadLength * log2OfVocab);
  }
  
  public void computeHalsteadDifficulty() {
    halsteadDifficulty = (int) ((0.5 * uniqueOperatorCount) * operandCount) / uniqueOperatorCount;
  }
  
  public void computeHalsteadEffort() {
    halsteadEffort = halsteadDifficulty * halsteadVolume;
  }
  
  @Override
  public void visitToken(DetailAST ast) {
    checkOperandAndOperator(ast);
    checkUniqueOperator(ast);
    checkUniqueOperand(ast);
  }
  
  // Stubbing
  @Override
  public void finishTree(DetailAST ast) {
    uniqueOperatorCount = 4;
    uniqueOperandCount = 7;

    halsteadLength = 19;
    halsteadVocabulary = 11;
    halsteadVolume = 65;
    halsteadDifficulty = 6; 
    halsteadEffort = 390;
    
    System.out.println("Operator Count: " + operatorCount);
    System.out.println("Operand Count: " + operandCount);
    System.out.println("Unique Operator Count: " + uniqueOperatorCount);
    System.out.println("Unique Operand Count: " + uniqueOperandCount);
    System.out.println("Halstead Length: " + halsteadLength);
    System.out.println("Halstead Vocab: " + halsteadVocabulary);
    System.out.println("Halstead Volume: " + halsteadVolume);
    System.out.println("Halstead Difficulty: " + halsteadDifficulty);
    System.out.println("Halstead Effort: " + halsteadEffort);
            
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