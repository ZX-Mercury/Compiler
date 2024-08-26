package AST;

import Util.position;

public class intLiteralNode extends LiteralNode {
    public int value;

    public intLiteralNode(position pos, int value) {
        super(pos);
        this.value = value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
