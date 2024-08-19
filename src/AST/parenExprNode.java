package AST;

import Util.position;

public class parenExprNode extends ExpressionNode {
    public ExpressionNode expr;

    public parenExprNode(position pos, ExpressionNode expr) {
        super(pos);
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
