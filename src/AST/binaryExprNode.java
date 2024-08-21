package AST;

import Util.position;

public class binaryExprNode extends ExpressionNode {
    public enum binaryOperator {
        Plus, Minus, Mul, Div, Mod,
        Equal, NotEqual, Less, LessEqual, Greater, GreaterEqual,
        AndAnd, OrOr, And, Or, Caret, LeftShift, RightShift,
        Assign, Dot
    }

    public binaryOperator binaryOp ;
    public ExpressionNode lhs, rhs;

    public binaryExprNode (position pos, binaryOperator binaryOp, ExpressionNode lhs, ExpressionNode rhs) {
        super (pos) ;
        this.binaryOp = binaryOp;
        this.lhs = lhs ;
        this.rhs = rhs ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}