package AST;

import Util.position;

public class stringLiteralNode extends LiteralNode {
    public String value;

    public stringLiteralNode(position pos, String _value) {
        super(pos);
        this.value = _value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
