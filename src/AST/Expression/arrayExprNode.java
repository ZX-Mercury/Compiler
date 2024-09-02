package AST.Expression;

import AST.ASTVisitor;
import Util.position;
import Util.Type;
import Util.error.semanticError;

import java.util.ArrayList;

public class arrayExprNode extends ExpressionNode {
    public ExpressionNode arrayIdentifier;
    public ArrayList<ExpressionNode> arrayIndex ;
    public String arrayName ;

    public arrayExprNode (position pos, ExpressionNode arrayIdentifier, ArrayList<ExpressionNode> arrayIndex) {
        super (pos) ;
        this.arrayIdentifier = arrayIdentifier ;
        this.arrayIndex = arrayIndex ;
        //this.arrayName = arrayName ;
    }

    @Override
    public void checkType () {
        if (arrayIdentifier.type.dim == 0) {
            throw new semanticError ("Semantic Error: not an array", pos) ;
        }
        for(ExpressionNode index : arrayIndex) {
            if (!index.type.btype.equals(Type.basicType.Int) || index.type.dim != 0) {
                throw new semanticError("Semantic Error: array index should be int", pos);
            }
        }
        type = new Type (arrayIdentifier.type.btype, arrayIdentifier.type.dim - arrayIndex.size(), true) ;
        type.Identifier = arrayIdentifier.type.Identifier ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}