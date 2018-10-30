package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MaintainabilityIndexCheckTest {
  MaintainabilityIndexCheck MIC; 
  
  @Before
  public void setUp() throws Exception {
    MIC = new MaintainabilityIndexCheck();
  }

  @After
  public void tearDown() throws Exception {
    MIC = null;
  }
  
  @Test
  public void countCommentsTest() {
    DetailAST ast = new DetailAST();
    // Test single line comments
    ast.initialize(TokenTypes.SINGLE_LINE_COMMENT, "testToken");
    MIC.singleline = 0;
    
    MIC.countComments(ast);
    assertEquals(1, MIC.singleline);
    
    // test block comments
    MIC.blockcom = 0;
    ast.initialize(TokenTypes.BLOCK_COMMENT_BEGIN, "testTokenTwo");
    ast.setLineNo(4);
    MIC.countComments(ast);
    
    ast.initialize(TokenTypes.BLOCK_COMMENT_END, "testTokenThree");
    ast.setLineNo(5);
    MIC.countComments(ast);
    assertEquals(2, MIC.blockcom);
    
    // Total lines of comments
    assertEquals(3, MIC.count);
  }
  
  @Test
  public void cyclomaticTest() {
    DetailAST ast = new DetailAST();
    ast.initialize(TokenTypes.LITERAL_IF, "testToken");
    
    MIC.cyclo = 0;
    
    MIC.getCycloComplexity(ast);
    assertEquals(1, MIC.cyclo);
  }
  
  @Test
  public void linesOfCodeTest() {
    DetailAST ast = new DetailAST();
    ast.initialize(TokenTypes.RCURLY, "testToken");
    ast.setColumnNo(0);
    ast.setLineNo(35);
    
    MIC.LOC = 0;
    
    MIC.countLinesofCode(ast);
    assertEquals(35, MIC.LOC);
  }
  
}
