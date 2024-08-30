package AST.Expression;

import AST.ASTVisitor;
import AST.Expression.ExpressionNode;
import Util.Type;
import Util.position;

import java.util.ArrayList;

public class callExprNode extends ExpressionNode {
    public ExpressionNode functionIdentifier ;
    public ArrayList<ExpressionNode> paraList ;

    public callExprNode (position pos, ExpressionNode functionIdentifier) {
        super (pos) ;
        this.functionIdentifier = functionIdentifier ;
        this.paraList = new ArrayList<>() ;
    }

    @Override
    public void checkType() {
        type = functionIdentifier.type ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}