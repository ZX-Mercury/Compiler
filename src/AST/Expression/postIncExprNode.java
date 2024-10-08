package AST.Expression;

import AST.ASTVisitor;
import Util.position;
import Util.Type;
import Util.error.semanticError;

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
    public void checkType () {
        if (expression.type.btype != Type.basicType.Int) {
            throw new semanticError ("Type Mismatch", pos) ;
        }
        if(!expression.type.isLeftValue){
            throw new semanticError("not assignable", pos);
        }
        type = new Type (Type.basicType.Int, 0, false) ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}