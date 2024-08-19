package AST;

import Util.position;

public class newSizeNode extends ASTNode {
    public ExpressionNode expression ;

    public newSizeNode (position pos, ExpressionNode expression) {
        super (pos) ;
        this.expression = expression ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }

}
