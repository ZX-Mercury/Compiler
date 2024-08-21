package AST;

import Util.position;

public class breakStmt extends ControlStmtNode {
    public breakStmt(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
