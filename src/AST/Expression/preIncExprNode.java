package AST.Expression;

import AST.ASTVisitor;
import Util.Type;
import Util.position;
import Util.error.semanticError;

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
    public void checkType () {
        if (expression.type.btype != Type.basicType.Int) {
            throw new semanticError ("Semantic Error: integer expected", pos) ;
        }
        if(!expression.type.isLeftValue){
            throw new semanticError("not assignable", pos);
        }
        type = new Type (Type.basicType.Int, 0, true) ;
    }
    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}