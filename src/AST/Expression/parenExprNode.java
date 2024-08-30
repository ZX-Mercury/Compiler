package AST.Expression;

import AST.ASTVisitor;
import Util.position;

public class parenExprNode extends ExpressionNode {
    public ExpressionNode expr;

    public parenExprNode(position pos, ExpressionNode expr) {
        super(pos);
        this.expr = expr;
    }

    @Override
    public void checkType() {
        type = expr.type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
