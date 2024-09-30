package AST.Expression;

import AST.ASTNode;
import MIR.IREntity.entity;
import Util.position;
import Util.Type;

abstract public class ExpressionNode extends ASTNode {
    public Type type ;
    public entity val ;
    public ExpressionNode (position pos) {
        super (pos) ;
    }

    public void checkType(){}
}