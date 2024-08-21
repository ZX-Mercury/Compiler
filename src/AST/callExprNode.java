package AST;
import Util.position;

public class callExprNode extends ExpressionNode {
    public ExpressionNode functionIdentifier ;
    public expressionListNode expressionList ;

    public callExprNode (position pos, ExpressionNode functionIdentifier, expressionListNode expressionList) {
        super (pos) ;
        this.functionIdentifier = functionIdentifier ;
        this.expressionList = expressionList ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}