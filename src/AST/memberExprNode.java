package AST;

import Util.position;

public class memberExprNode extends ExpressionNode {
    public ExpressionNode expr;
    public String member;

    public memberExprNode (position pos, ExpressionNode expr, String member) {
        super (pos) ;
        this.expr = expr;
        this.member = member;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}
