package AST;

import Util.position;

public class varDefStmtNode extends StmtNode {
    public varDefNode varDef;

    public varDefStmtNode(position pos, varDefNode varDef) {
        super(pos);
        this.varDef = varDef;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
