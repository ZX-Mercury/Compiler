package AST.Expression;

import Util.position;

abstract public class LiteralNode extends ExpressionNode {
    public LiteralNode(position pos) {
        super(pos);
    }
}
