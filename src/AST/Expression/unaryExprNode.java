package AST.Expression;

import AST.ASTVisitor;
import Util.position;
import Util.Type;
import Util.error.semanticError;

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
    public void checkType() {
        if (unaryOp.equals(unaryOperator.Plus) || unaryOp.equals(unaryOperator.Minus)) {
            if (!expression.type.btype.equals(Type.basicType.Int)) {
                throw new semanticError("Semantic Error: type not match", pos);
            }
            type = new Type(Type.basicType.Int, 0, false);
        }
        else if (unaryOp.equals(unaryOperator.Not)) {
            if (!expression.type.btype.equals(Type.basicType.Bool)) {
                throw new semanticError("Semantic Error: type not match", pos);
            }
            type = new Type(Type.basicType.Bool, 0, false);
        }
        else if (unaryOp.equals(unaryOperator.Tilde)) {
            if (!expression.type.btype.equals(Type.basicType.Int)) {
                throw new semanticError("Semantic Error: type not match", pos);
            }
            type = new Type(Type.basicType.Int, 0, false);
        }
    }
    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}