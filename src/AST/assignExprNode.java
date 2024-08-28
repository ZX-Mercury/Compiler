package AST;

import Util.position;
import Util.Type;
import Util.error.semanticError;

public class assignExprNode extends ExpressionNode {
    public ExpressionNode lhs, rhs;

    public assignExprNode(position pos, ExpressionNode lhs, ExpressionNode rhs) {
        super(pos);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void checkType() {
        if(!lhs.type.equals(rhs.type)){
            throw new semanticError("Semantic Error: type not match", pos);
        }
        if(lhs.type.dim != rhs.type.dim){
            throw new semanticError("Semantic Error: dimension not match", pos);
        }
        type = lhs.type;
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}