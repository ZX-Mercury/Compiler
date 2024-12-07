package AST.Expression;

import AST.ASTNode;
import MIR.IREntity.entity;
import MIR.Value.value;
import Util.position;
import Util.Type;

abstract public class ExpressionNode extends ASTNode {
    public Type type ;
    public value val ;
    public boolean isLeftValue = false;
    public ExpressionNode (position pos) {
        super (pos) ;
    }

    public void checkType(){}
}