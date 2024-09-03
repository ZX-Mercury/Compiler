package AST.Expression;

import AST.ASTVisitor;
import Util.position;
import Util.Type;
import Util.error.semanticError;
import Util.typeCmp;
import jdk.jshell.execution.Util;

import java.util.Objects;

public class assignExprNode extends ExpressionNode {
    public ExpressionNode lhs, rhs;

    public assignExprNode(position pos, ExpressionNode lhs, ExpressionNode rhs) {
        super(pos);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void checkType() {
        if((rhs.type.btype == Type.basicType.Null)){
            if(!lhs.type.isLeftValue){
                throw new semanticError("lhs is not assignable", pos);
            }
            if(lhs.type.dim == 0 && lhs.type.btype != Type.basicType.Class){
                throw new semanticError("Type Mismatch", pos);
            }
        }
        else {
            if (!typeCmp.cmptype(lhs.type, rhs.type)) {
                throw new semanticError("Type Mismatch", pos);
            }
            if (lhs.type.dim != rhs.type.dim) {
                throw new semanticError("Type Mismatch", pos);
            }
            if (!lhs.type.isLeftValue) {
                throw new semanticError("lhs is not assignable", pos);
            }
        }
        type = lhs.type;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}