package AST;

import Util.position;

import java.util.ArrayList;

public class newVarExprNode extends ExpressionNode {
    public String classID ;
    public buildin_typenameNode builtinType ;
    public ArrayList<newSizeNode> newSize ;

    public newVarExprNode (position pos, String classID, buildin_typenameNode builtinType) {
        super (pos) ;
        this.classID = classID ;
        this.builtinType = builtinType ;
        newSize = new ArrayList<>();
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}
