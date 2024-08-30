package AST.Definition;

import AST.ASTNode;
import AST.ASTVisitor;
import Util.Type;
import Util.position;

public class parameterNode extends ASTNode {
    public Type type ;
    public String name ;

    public parameterNode (position pos, Type type, String name) {
        super (pos) ;
        this.type = type ;
        this.name = name ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}