package AST.Expression;

import AST.ASTVisitor;
import Util.position;
import Util.Type;

public class stringLiteralNode extends LiteralNode {
    public String value;

    public stringLiteralNode(position pos, String _value) {
        super(pos);
        this.value = _value;
    }

    @Override
    public void checkType() {
        type = new Type(Type.basicType.String, 0, false);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
