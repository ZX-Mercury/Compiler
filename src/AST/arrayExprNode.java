package AST;

import Util.position;

public class arrayExprNode extends ExpressionNode {
    public ExpressionNode arrayIdentifier, arrayIndex ;

    public arrayExprNode (position pos, ExpressionNode arrayIdentifier, ExpressionNode arrayIndex) {
        super (pos) ;
        this.arrayIdentifier = arrayIdentifier ;
        this.arrayIndex = arrayIndex ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}