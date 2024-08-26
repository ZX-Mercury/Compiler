package AST;

import Util.position;

public class unaryExprNode extends ExpressionNode {
    public enum unaryOperator {
        Plus, Minus, Not, Tilde ;
    }

    public unaryOperator unaryOp ;
    public ExpressionNode expression ;

    public unaryExprNode(position pos, unaryOperator unaryOp, ExpressionNode expression) {
        super (pos) ;
        this.unaryOp = unaryOp ;
        this.expression = expression ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}