package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.*;

public class SuperCheckTests {
  
  SuperCheck superChecks;
  
  @Before
  public void setUp() throws Exception {
    superChecks = new SuperCheck();
  }
  
  @After
  public void tearDown() throws Exception {
    superChecks = null;
  }
  
  @Test
  public void testCheckCommentLineCount() {
    DetailAST ast = new DetailAST();
    ast.initialize(TokenTypes.SINGLE_LINE_COMMENT, "testToken");
    superChecks.CommentLinesCount = 0;
    
    superChecks.computeCommentLineCount(ast);
    assertEquals(1, superChecks.CommentLinesCount);
    
    ast.initialize(TokenTypes.BLOCK_COMMENT_BEGIN, "testTokenTwo");
    ast.setLineNo(4);
    superChecks.computeCommentLineCount(ast);
    ast.initialize(TokenTypes.BLOCK_COMMENT_END, "testTokenThree");
    ast.setLineNo(5);
    superChecks.computeCommentLineCount(ast);
    assertEquals(3, superChecks.CommentLinesCount);
  }
  
  @Test
  public void testCheckCommentCount() {
    DetailAST ast = new DetailAST();
    ast.initialize(TokenTypes.SINGLE_LINE_COMMENT, "testToken");  
    superChecks.CommentCount = 0;
    
    superChecks.computeCommentCount(ast);
    assertEquals(1, superChecks.CommentCount);
    
    ast.initialize(TokenTypes.BLOCK_COMMENT_END, "testTokenTwo");
    superChecks.computeCommentCount(ast);
    assertEquals(2, superChecks.CommentCount);
  }
  
  @Test
  public void testCheckRefCounts() {
    DetailAST ast = new DetailAST();
    DetailAST astChild = new DetailAST();
    DetailAST astGrandChild = new DetailAST();
    DetailAST astGrandChildSibling = new DetailAST();
    DetailAST astBrother = new DetailAST();
    DetailAST astSister = new DetailAST();
    superChecks.countLocals = 0;
    superChecks.countExternals = 0;
    superChecks.locals.clear();
    superChecks.all.clear();
    
    //add a local via def
    ast.initialize(TokenTypes.METHOD_DEF, "testToken");
    astChild.initialize(TokenTypes.STRING_LITERAL, "testTokenChild");//building tree for a local method def
    astBrother.initialize(TokenTypes.STRING_LITERAL, "testTokenBrother");
    astSister.initialize(TokenTypes.STRING_LITERAL, "testTokenSister");
    astChild.addNextSibling(astBrother);
    astChild.addNextSibling(astSister); 
    ast.setFirstChild(astChild);
    superChecks.checkRefCounts(ast);
    superChecks.computeRefCounts();
    assertEquals(1, superChecks.locals.size());
    assertEquals(1, superChecks.countLocals);    
    
    //test our local by using a ref
    ast.initialize(TokenTypes.METHOD_REF, "testTokenTwo");
    astChild.initialize(TokenTypes.IDENT, "testTokenSister");
    ast.setFirstChild(astChild);
    superChecks.checkRefCounts(ast);
    assertEquals(1, superChecks.all.size()); 
    superChecks.computeRefCounts();
    assertEquals(1, superChecks.countLocals); 
    
    //add an external by using a ref
    ast.initialize(TokenTypes.METHOD_REF, "testTokenThree");
    astChild.initialize(TokenTypes.DOT, "testTokenThreeDot");
    ast.setFirstChild(astChild);
    astGrandChild.initialize(TokenTypes.STRING_LITERAL, "testTokenThreeIdent");
    astChild.setFirstChild(astGrandChildSibling);
    astGrandChildSibling.initialize(TokenTypes.IDENT, "testTokenThreeExternal");
    astGrandChild.addNextSibling(astGrandChildSibling);
    superChecks.checkRefCounts(ast);
    assertEquals(2, superChecks.all.size()); 
    superChecks.computeRefCounts();
    assertEquals(1, superChecks.countExternals); 
    assertEquals(1, superChecks.countLocals); 
  }
  
  @Test
  public void testCheckVariableDefCount() {
    DetailAST ast = new DetailAST();
    ast.initialize(TokenTypes.METHOD_DEF, "testToken");   
    superChecks.VariableCount = 0;
    
    superChecks.computeVariableDefCount(ast);
    assertEquals(1, superChecks.VariableCount);     
  }
  
  @Test
  public void testCheckLoopCount() {
    DetailAST ast = new DetailAST();
    ast.initialize(TokenTypes.LITERAL_FOR, "testToken");   
    superChecks.LoopCount = 0;
    
    superChecks.computeLoopCount(ast);
    assertEquals(1, superChecks.LoopCount);     
    ast.initialize(TokenTypes.LITERAL_WHILE, "testTokenTwo");
    superChecks.computeLoopCount(ast);
    assertEquals(2, superChecks.LoopCount);
  }
  
  @Test
  public void testCheckExpressionCount() {
    DetailAST ast = new DetailAST();
    ast.initialize(TokenTypes.EXPR, "testToken");   
    superChecks.ExpCount = 0;
    
    superChecks.computeExpressionCount(ast);
    assertEquals(1, superChecks.ExpCount);     
  }
  
  @Test
  public void testCheckTypeCastCount() {
    DetailAST ast = new DetailAST();
    ast.initialize(TokenTypes.TYPECAST, "testToken");   
    superChecks.typeCastCount = 0;
    
    superChecks.computeTypeCastCount(ast);
    assertEquals(1, superChecks.typeCastCount);    
  }
}