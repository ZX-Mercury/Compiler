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

    public void checkType() {
        for(ExpressionNode expr : exprList) {
            if(expr.type.btype!=Type.basicType.Int) {
                throw new semanticError("new array expr should have int type", pos);
            }
        }
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
