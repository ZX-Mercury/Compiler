package AST;

import Util.position;

public class functypenameNode extends ASTNode {
    public varTypeNode retType;
    public Boolean isVoid;

    public functypenameNode(position pos, Boolean isVoid) {
        super (pos);
        this.isVoid = isVoid;
    }

    public functypenameNode(position pos, Boolean isVoid, varTypeNode retType) {
        super(pos);
        this.retType = retType;
        this.isVoid = isVoid;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}