package AST.Expression;

import AST.ASTVisitor;
import Util.position;
import Util.Type;
import Util.error.semanticError;

public class arrayExprNode extends ExpressionNode {
    public ExpressionNode arrayIdentifier, arrayIndex ;

    public arrayExprNode (position pos, ExpressionNode arrayIdentifier, ExpressionNode arrayIndex) {
        super (pos) ;
        this.arrayIdentifier = arrayIdentifier ;
        this.arrayIndex = arrayIndex ;
    }

    @Override
    public void checkType () {
        if (arrayIdentifier.type.dim == 0) {
            throw new semanticError ("Semantic Error: not an array", pos) ;
        }
        if (!arrayIndex.type.btype.equals (Type.basicType.Int)) {
            throw new semanticError ("Semantic Error: array index should be int", pos) ;
        }
        type = new Type (arrayIdentifier.type.btype, arrayIdentifier.type.dim - 1, true) ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}