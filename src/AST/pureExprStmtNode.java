package AST;

import Util.position;

public class pureExprStmtNode extends StmtNode {
    public ExpressionNode expr;

    public pureExprStmtNode(position pos, ExpressionNode expr) {
        super(pos);
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
