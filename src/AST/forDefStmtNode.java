package AST;

import Util.position;

public class forDefStmtNode extends LoopStmtNode {
    public varDefNode varDef;
    public ExpressionNode condition, step;
    public StmtNode statement;

    public forDefStmtNode(position pos, varDefNode varDef, ExpressionNode condition, ExpressionNode step, StmtNode statement) {
        super(pos);
        this.varDef = varDef;
        this.condition = condition;
        this.step = step;
        this.statement = statement;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
