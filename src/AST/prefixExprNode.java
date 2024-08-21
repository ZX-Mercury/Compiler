package AST;

import Util.position;

public class prefixExprNode extends ExpressionNode {
    public enum prefixOperator {
        Plus, Minus, Not, Tilde ;
    }

    public prefixOperator prefixOp ;
    public ExpressionNode expression ;

    public prefixExprNode (position pos, prefixOperator prefixOp, ExpressionNode expression) {
        super (pos) ;
        this.prefixOp = prefixOp ;
        this.expression = expression ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}