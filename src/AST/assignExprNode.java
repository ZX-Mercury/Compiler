package AST;

import Util.position;

public class assignExprNode extends ExpressionNode {
    public ExpressionNode lhs, rhs;

    public assignExprNode(position pos, ExpressionNode lhs, ExpressionNode rhs) {
        super(pos);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}