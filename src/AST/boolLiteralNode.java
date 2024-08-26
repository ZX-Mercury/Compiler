package AST;

import Util.position;
public class boolLiteralNode extends LiteralNode {
    public boolean value;

    public boolLiteralNode(position pos, boolean value) {
        super(pos);
        this.value = value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
