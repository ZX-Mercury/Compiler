package AST.Statement;

import AST.ASTNode;
import Util.position;

public abstract class StmtNode extends ASTNode {
    public StmtNode(position pos) {
        super(pos);
    }
}
