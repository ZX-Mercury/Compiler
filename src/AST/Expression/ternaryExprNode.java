package AST.Expression;

import AST.ASTVisitor;
import Util.error.semanticError;
import Util.position;
import Util.Type;

public class ternaryExprNode extends ExpressionNode {
    public ExpressionNode condition, trueExpr, falseExpr ;

    public ternaryExprNode (position pos, ExpressionNode condition, ExpressionNode trueExpr, ExpressionNode falseExpr) {
        super (pos) ;
        this.condition = condition ;
        this.trueExpr = trueExpr ;
        this.falseExpr = falseExpr ;
    }

    @Override
    public void checkType () {
        if (!condition.type.btype.equals (Type.basicType.Bool)) {
            throw new semanticError ("Semantic Error: type not match, bool expected",condition.pos) ;
        }
        if (!trueExpr.type.btype.equals (falseExpr.type.btype)) {
            throw new semanticError ("Semantic Error: type not match", pos) ;
        }
        type = new Type (trueExpr.type) ;
    }
    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}