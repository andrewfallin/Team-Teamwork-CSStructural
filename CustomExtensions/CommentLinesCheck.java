package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CommentLinesCheck extends AbstractCheck {

 private int count = 0;
 private int begin = 0;
 private int end = 0;
 
 @Override
 public boolean isCommentNodesRequired() {
  return true;
 }

 @Override
 public int[] getAcceptableTokens() {
   return new int[] { TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.BLOCK_COMMENT_END };
 }

 @Override
 public int[] getRequiredTokens() {
   return new int[0];
 }

 @Override
 public int[] getDefaultTokens() {
   return new int[] { TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.BLOCK_COMMENT_END };
 }

 public void setMax(int limit) {
 }

 @Override
 public void visitToken(DetailAST ast) {
   if(ast.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
     this.count++;
   }
   else if(ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN)
   {
     begin = ast.getLineNo();
   }
   else {
     end = ast.getLineNo();
     this.count += end-begin+1;
     /*5-4=1. if a comment block begins on 4 and ends on 5, it is spanning two lines. prevent off by one with +1.
     */
   }
 }
 
 @Override
 public void finishTree(DetailAST ast) {
   log(ast, "commentlinescount", count);
   count = 0;
 }

}