package AST;

import Util.position;
import Util.Type;

public class intLiteralNode extends LiteralNode {
    public int value;

    public intLiteralNode(position pos, int value) {
        super(pos);
        this.value = value;
    }

    @Override
    public void checkType() {
        type = new Type(Type.basicType.Int, 0, false);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
