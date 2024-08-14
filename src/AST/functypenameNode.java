package AST;

import Util.position;

public class functypenameNode extends ASTNode {
    public typenameNode retType;
    public boolean isVoid;

    public functypenameNode(position pos, boolean isVoid) {
        super (pos);
        this.isVoid = isVoid;
    }

    public functypenameNode(position pos, boolean isVoid, typenameNode retType) {
        super(pos);
        this.retType = retType;
        this.isVoid = isVoid;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}