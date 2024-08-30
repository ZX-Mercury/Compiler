package AST.Expression;

import AST.ASTVisitor;
import Util.position;
import Util.Type;

public class boolLiteralNode extends LiteralNode {
    public boolean value;

    public boolLiteralNode(position pos, boolean value) {
        super(pos);
        this.value = value;
    }

    @Override
    public void checkType() {
        type = new Type(Type.basicType.Bool, 0, false);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
