package AST.Expression;

import AST.ASTVisitor;
import AST.Expression.ExpressionNode;
import Util.error.semanticError;
import Util.Type;
import Util.position;
import Util.typeCmp;
import AST.Definition.funcDefNode;

import java.util.ArrayList;

public class callExprNode extends ExpressionNode {
    funcDefNode function ;
    public ExpressionNode functionIdentifier ;
    public ArrayList<ExpressionNode> paraList ;

    public callExprNode (position pos, ExpressionNode functionIdentifier) {
        super (pos) ;
        this.functionIdentifier = functionIdentifier ;
        this.paraList = new ArrayList<>() ;
    }

    @Override
    public void checkType() {
        type = new Type(functionIdentifier.type) ;
        type.isLeftValue = false;
        /*if (type.btype!=Type.basicType.Function) {
            throw new semanticError("Undefined Identifier", pos);
        }
        if(type.functionParameters.size()!=paraList.size()) {
            throw new semanticError("Wrong Number of Parameters", pos);
        }

        for (int i = 0; i < paraList.size(); i++) {
            if (!typeCmp.cmptype(type.functionParameters.get(i), paraList.get(i).type))
                throw new semanticError("Not a parameter type", pos);
        }*/
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}