package AST;

import Util.position;

public class whileStmtNode extends LoopStmtNode {
    public ExpressionNode condition;
    public StmtNode statement;

    public whileStmtNode(position pos, ExpressionNode condition, StmtNode statement) {
        super(pos);
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
