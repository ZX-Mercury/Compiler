package AST;

import Util.position;
import Util.Type;

abstract public class ExpressionNode extends ASTNode {
    public Type type ;
    public ExpressionNode (position pos) {
        super (pos) ;
    }

    public void checkType(){}
}