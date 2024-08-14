package AST;

import Util.position;

public class returnStmtNode extends StmtNode {
    public ExpressionNode retExpr;

    public returnStmtNode(position pos, ExpressionNode retExpr) {
        super(pos);
        this.retExpr = retExpr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
