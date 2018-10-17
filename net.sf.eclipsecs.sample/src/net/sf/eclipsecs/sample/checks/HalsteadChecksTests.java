package net.sf.eclipsecs.sample.checks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class HalsteadChecksTests {
  
  HalsteadChecks hc = new HalsteadChecks();
  
  @BeforeEach
  public void setUp() throws Exception {
    
  }
  
  @AfterEach
  public void tearDown() throws Exception {
    
  }
  
  @Test
  public void testCheckOperatorAndOperand() {
    assertEquals(hc.operandCount, 10);
    
  }

}
