package AST;

import Util.position;

public class ternaryExprNode extends ExpressionNode {
    public ExpressionNode condition, trueExpr, falseExpr ;

    public ternaryExprNode (position pos, ExpressionNode condition, ExpressionNode trueExpr, ExpressionNode falseExpr) {
        super (pos) ;
        this.condition = condition ;
        this.trueExpr = trueExpr ;
        this.falseExpr = falseExpr ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}