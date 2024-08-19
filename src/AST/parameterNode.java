package AST;

import Util.position;

public class parameterNode extends ASTNode {
    public varTypeNode type ;
    public String name ;

    public parameterNode (position pos, varTypeNode type, String name) {
        super (pos) ;
        this.type = type ;
        this.name = name ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}