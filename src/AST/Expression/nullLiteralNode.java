package AST.Expression;

import AST.ASTVisitor;
import Util.position;
import Util.Type;

public class nullLiteralNode extends LiteralNode {
    public nullLiteralNode(position pos) {
        super(pos);
    }

    @Override
    public void checkType() {
        type = new Type(Type.basicType.Null, 0, false);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
