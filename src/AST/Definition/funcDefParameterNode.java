package AST.Definition;

import java.util.ArrayList;

import AST.ASTNode;
import AST.ASTVisitor;
import Util.position;

public class funcDefParameterNode extends ASTNode {
    public ArrayList<parameterNode> parameters ;
    public funcDefParameterNode (position pos) {
        super (pos) ;
        parameters = new ArrayList<>();
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}