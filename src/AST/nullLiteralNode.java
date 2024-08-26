package AST;

import Util.position;

public class nullLiteralNode extends LiteralNode {
    public nullLiteralNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
