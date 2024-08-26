package AST;

import Util.position;

import java.util.ArrayList;

public class arrayLiteralNode extends LiteralNode {
    public ArrayList<LiteralNode> elements;

    public arrayLiteralNode(position pos) {
        super(pos);
        elements = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
