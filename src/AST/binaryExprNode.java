package AST;

import Util.error.semanticError;
import Util.position;
import Util.Type;

public class binaryExprNode extends ExpressionNode {
    public enum binaryOperator {
        Plus, Minus, Mul, Div, Mod,
        Equal, NotEqual, Less, LessEqual, Greater, GreaterEqual,
        AndAnd, OrOr, And, Or, Caret, LeftShift, RightShift
    }

    public binaryOperator binaryOp ;
    public ExpressionNode lhs, rhs;

    public binaryExprNode (position pos, binaryOperator binaryOp, ExpressionNode lhs, ExpressionNode rhs) {
        super (pos) ;
        this.binaryOp = binaryOp;
        this.lhs = lhs ;
        this.rhs = rhs ;
    }

    //@Override
    public void checkType(){
        if(binaryOp.equals(binaryOperator.Plus)){
            if(lhs.type.btype.equals(Type.basicType.Int) && rhs.type.btype.equals(Type.basicType.Int)){
                type = new Type(Type.basicType.Bool,0,false);
            }
            else if(lhs.type.btype.equals(Type.basicType.String) && rhs.type.btype.equals(Type.basicType.String)){
                type = new Type(Type.basicType.String,0,false);
            }
            else{
                throw new semanticError("Semantic Error: type not match", pos);
            }
        }
        else if(binaryOp.equals(binaryOperator.Minus) || binaryOp.equals(binaryOperator.Mul)
                || binaryOp.equals(binaryOperator.Div) || binaryOp.equals(binaryOperator.Mod)){
            if(lhs.type.btype.equals(Type.basicType.Int) && rhs.type.btype.equals(Type.basicType.Int)){
                type = new Type(Type.basicType.Int,0,false);
            }
            else{
                throw new semanticError("Semantic Error: type not match", pos);
            }
        }
        else if(binaryOp.equals(binaryOperator.Equal) || binaryOp.equals(binaryOperator.NotEqual)) {
            if(lhs.type.btype.equals(rhs.type.btype)){
                type = new Type(Type.basicType.Bool,0,false);
            }
            else{
                throw new semanticError("Semantic Error: type not match", pos);
            }
        }
        else if(binaryOp.equals(binaryOperator.Less) || binaryOp.equals(binaryOperator.LessEqual)
                || binaryOp.equals(binaryOperator.Greater) || binaryOp.equals(binaryOperator.GreaterEqual)) {
            if(lhs.type.btype.equals(Type.basicType.Int) && rhs.type.btype.equals(Type.basicType.Int)){
                type = new Type(Type.basicType.Bool,0,false);
            }
            else{
                throw new semanticError("Semantic Error: type not match", pos);
            }
        }

        else if(binaryOp.equals(binaryOperator.AndAnd) || binaryOp.equals(binaryOperator.OrOr)){
            if(lhs.type.btype.equals(Type.basicType.Bool) && rhs.type.btype.equals(Type.basicType.Bool)){
                type = new Type(Type.basicType.Bool,0,false);
            }
            else{
                throw new semanticError("Semantic Error: type not match", pos);
            }
        }
        else if(binaryOp.equals(binaryOperator.And) || binaryOp.equals(binaryOperator.Or)
                || binaryOp.equals(binaryOperator.Caret) || binaryOp.equals(binaryOperator.LeftShift)
                || binaryOp.equals(binaryOperator.RightShift)){
            if(lhs.type.btype.equals(Type.basicType.Int) && rhs.type.btype.equals(Type.basicType.Int)){
                type = new Type(Type.basicType.Int,0,false);
            }
            else{
                throw new semanticError("Semantic Error: type not match", pos);
            }
        }
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}