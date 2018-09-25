package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CommentCountCheck extends AbstractCheck {

 private int count = 0;
 
 @Override
 public boolean isCommentNodesRequired() {
  return true;
 }

 @Override
 public int[] getAcceptableTokens() {
   return new int[] { TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN };
 }

 @Override
 public int[] getRequiredTokens() {
   return new int[0];
 }

 @Override
 public int[] getDefaultTokens() {
   return new int[] { TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN };
 }

 public void setMax(int limit) {
 }

 @Override
 public void visitToken(DetailAST ast) {
   this.count++;
 }
 
 @Override
 public void finishTree(DetailAST ast) {
   log(ast, "commentcount", count);
   count = 0;
 }

}