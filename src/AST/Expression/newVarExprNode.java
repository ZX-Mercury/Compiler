package AST.Expression;

import AST.ASTVisitor;
import Util.position;
import Util.Type;
import Util.error.semanticError;

import java.util.ArrayList;

public class newVarExprNode extends ExpressionNode {
    public newVarExprNode (position pos) {
        super (pos) ;
    }
    /*public String classID ;
    public buildin_typenameNode builtinType ;
    public ArrayList<ExpressionNode> newSize ;//newSizeNode
    public arrayLiteralNode arrayLiteral ;

    public newVarExprNode (position pos, String classID, buildin_typenameNode builtinType) {
        super (pos) ;
        this.classID = classID ;
        this.builtinType = builtinType ;
        newSize = new ArrayList<>();
    }

    @Override
    public void checkType () {
        if (builtinType != null) {//int, bool, string etc.
            type = new Type (builtinType.bType, newSize.size()-1, false) ;
        }
        else { //class
            type = new Type (classID, newSize.size()-1, false) ;
        }
    }*/

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}
