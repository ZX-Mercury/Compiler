package AST;

import Util.position;

public class forExpStmtNode extends LoopStmtNode {
    public ExpressionNode init, condition, step;
    public StmtNode statement;

    public forExpStmtNode(position pos, ExpressionNode init, ExpressionNode condition, ExpressionNode step, StmtNode statement) {
        super(pos);
        this.init = init;
        this.condition = condition;
        this.step = step;
        this.statement = statement;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
