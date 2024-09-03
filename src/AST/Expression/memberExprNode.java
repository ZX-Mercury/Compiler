package AST.Expression;

import AST.ASTVisitor;
import Util.position;
import Util.Type;
import Util.error.semanticError;

public class memberExprNode extends ExpressionNode {
    public ExpressionNode expr;
    public String member;

    public memberExprNode (position pos, ExpressionNode expr, String member) {
        super (pos) ;
        this.expr = expr;
        this.member = member;
    }

    @Override
    public void checkType () {
        //Only applicable to situations where expr is not a class
        if(expr.type == null) {
            throw new semanticError("null pointer", pos);
        }
        if(expr.type.btype==Type.basicType.String) {
            if (member.equals("length") || member.equals("parseInt") || member.equals("ord")) {
                type = new Type(Type.basicType.Int, 0, false);
            } else if (member.equals("substring")) {
                type = new Type(Type.basicType.String, 0, false);
            } else {
                throw new semanticError("String has no member named " + member, pos);
            }
        } else if(expr.type.btype==Type.basicType.Int && expr.type.dim>0) {
            if(member.equals("size")) {
                type = new Type(Type.basicType.Int, 0, false);
            } else {
                throw new semanticError("Array has no member named " + member, pos);
            }
        }
        else if(expr.type.btype==Type.basicType.Int||expr.type.btype==Type.basicType.Bool) {
            throw new semanticError("Primitive type has no member named " + member, pos);
        }
    }
    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}
