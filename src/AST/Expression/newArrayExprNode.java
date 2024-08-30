package AST.Expression;

import AST.ASTVisitor;
import Util.position;
import Util.Type;
import Util.error.semanticError;

import java.util.ArrayList;

public class newArrayExprNode extends ExpressionNode {
    public ArrayList<ExpressionNode> exprList;
    public arrayLiteralNode arrayLiteral;

    public newArrayExprNode(position pos) {
        super(pos);
        this.exprList = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
