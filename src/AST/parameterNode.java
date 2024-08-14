package AST;

import Util.position;

public class parameterNode extends ASTNode {
    public typenameNode type ;
    public String name ;

    public parameterNode (position pos, typenameNode type, String name) {
        super (pos) ;
        this.type = type ;
        this.name = name ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}