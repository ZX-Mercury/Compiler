package AST;

import Util.position;

public class preIncExprNode extends ExpressionNode {
    public enum preIncOperator {
        PlusPlus, MinusMinus
    }

    public preIncOperator preIncOp ;
    public ExpressionNode expression ;

    public preIncExprNode (position pos, preIncOperator preIncOp, ExpressionNode expression) {
        super (pos) ;
        this.preIncOp = preIncOp ;
        this.expression = expression ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}