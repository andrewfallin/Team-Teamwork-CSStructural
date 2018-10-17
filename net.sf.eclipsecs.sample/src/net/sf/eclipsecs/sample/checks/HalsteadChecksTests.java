package net.sf.eclipsecs.sample.checks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class HalsteadChecksTests {
  
  HalsteadChecks halsteadChecks;
  
  @BeforeEach
  public void setUp() throws Exception {
    halsteadChecks = new HalsteadChecks();
  }
  
  @AfterEach
  public void tearDown() throws Exception {
    halsteadChecks = null;
  }
  
  @Test
  public void testCheckOperatorAndOperand() {
    // Need to figure out best way to setup AST
    // Not sure if this the best way to unit test the checks, will talk to prof on friday
    DetailAST ast = new DetailAST();
    ast.initialize(TokenTypes.EQUAL, "testToken");
    
    halsteadChecks.operatorCount = 0;
    halsteadChecks.operandCount = 0;
    
    halsteadChecks.checkOperandAndOperator(ast);
    assertEquals(1, halsteadChecks.operatorCount);
    assertEquals(2, halsteadChecks.operandCount);
    
    
    ast.initialize(TokenTypes.PLUS, "testTokenTwo");
    halsteadChecks.operatorCount = 0;
    halsteadChecks.operandCount = 0;
    
    halsteadChecks.checkOperandAndOperator(ast);
    assertEquals(1, halsteadChecks.operatorCount);
    assertEquals(1, halsteadChecks.operandCount);
  }
  
  @Test
  public void testComputeHalsteadLength() {
    halsteadChecks.operandCount = 10;
    halsteadChecks.operatorCount = 25;
    
    halsteadChecks.computeHalsteadLength();
    assertEquals(35, halsteadChecks.halsteadLength);
    
    halsteadChecks.operandCount = 50;
    halsteadChecks.operatorCount = 13;
    
    halsteadChecks.computeHalsteadLength();
    assertNotEquals(65, halsteadChecks.halsteadLength);
  }
  
  @Test
  public void testComputeHalsteadVocabulary() {
    halsteadChecks.uniqueOperandCount = 5;
    halsteadChecks.uniqueOperatorCount = 10;
    
    halsteadChecks.computeHalsteadVocabulary();
    assertEquals(15, halsteadChecks.halsteadVocabulary);
    
    halsteadChecks.uniqueOperandCount = 25;
    halsteadChecks.uniqueOperatorCount = 10;
    
    halsteadChecks.computeHalsteadVocabulary();
    assertNotEquals(15, halsteadChecks.halsteadVocabulary);
  }

  @Test
  public void testComputeHalsteadVolume() {
    halsteadChecks.halsteadVocabulary = 16;
    halsteadChecks.halsteadLength = 25;
    
    halsteadChecks.computeHalsteadVolume();
    assertEquals(100, halsteadChecks.halsteadVolume);
    
    halsteadChecks.halsteadVocabulary = 25;
    halsteadChecks.halsteadLength = 10;
    
    halsteadChecks.computeHalsteadVolume();
    assertEquals(46, halsteadChecks.halsteadVolume);
  }
  
  @Test
  public void testComputeHalsteadDifficulty() {
    halsteadChecks.operandCount = 10;
    halsteadChecks.uniqueOperatorCount = 20;
    
    halsteadChecks.computeHalsteadDifficulty();
    assertEquals(5, halsteadChecks.halsteadDifficulty);
    
    halsteadChecks.operandCount = 50;
    halsteadChecks.uniqueOperatorCount = 15;
    
    halsteadChecks.computeHalsteadDifficulty();
    assertEquals(25, halsteadChecks.halsteadDifficulty);
  }
  
  @Test
  public void testComputeHalsteadEffort() {
    halsteadChecks.halsteadDifficulty = 10;
    halsteadChecks.halsteadVolume = 25;
    
    halsteadChecks.computeHalsteadEffort();
    assertEquals(250, halsteadChecks.halsteadEffort);
    
    halsteadChecks.halsteadDifficulty = 2;
    halsteadChecks.halsteadVolume = 13;
    
    halsteadChecks.computeHalsteadEffort();
    assertNotEquals(27, halsteadChecks.halsteadEffort);
  }
}
