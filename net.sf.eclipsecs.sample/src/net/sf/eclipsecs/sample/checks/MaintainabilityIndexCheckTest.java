package net.sf.eclipsecs.sample.checks;


import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
//import org.mockito.*;

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
    ast.initialize(TokenTypes.SINGLE_LINE_COMMENT, "testToken");
    
    MIC.singleline = 0;
    
    MIC.countComments(ast);
    assertEquals(1, MIC.singleline);
    
    //I have no idea how to get a block comment unit test to work
    ast.initialize(TokenTypes.BLOCK_COMMENT_BEGIN, "testToken2");
   
   MIC.count = 0;
   MIC.countComments(ast);
   assertEquals(1, MIC.count);
   
   ast.initialize(TokenTypes.BLOCK_COMMENT_END, "testToken3");
    MIC.count = 0;
    
    MIC.countComments(ast);
    assertEquals(1, MIC.blockcom);
    
    
   
  }
  
  @Test
  public void CyclomaticTest() {
    DetailAST ast = new DetailAST();
    ast.initialize(TokenTypes.LITERAL_IF, "testToken");
    
    MIC.cyclo = 0;
    
    MIC.getCycloComplexity(ast);
    assertEquals(1, MIC.cyclo);
  }
  
  @Test
  public void linesofcodetest() {
    DetailAST ast = new DetailAST();
    ast.initialize(TokenTypes.RCURLY, "testToken");
    
    MIC.LOC = 0;
    
    MIC.countLinesofCode(ast);
    assertEquals(14, MIC.LOC);
  }
  
}
