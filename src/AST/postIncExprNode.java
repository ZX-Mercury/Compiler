package AST;

import Util.position;

public class postIncExprNode extends ExpressionNode {
    public enum postIncOperator {
        PlusPlus, MinusMinus
    }

    public postIncOperator postIncOp;
    public ExpressionNode expression ;

    public postIncExprNode (position pos, postIncOperator postIncOp, ExpressionNode expression) {
        super (pos) ;
        this.postIncOp = postIncOp ;
        this.expression = expression ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}