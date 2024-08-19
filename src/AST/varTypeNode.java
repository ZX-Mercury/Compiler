package AST;

import Util.position;

public class varTypeNode extends ASTNode {
    public String classIdentifier ;
    public buildin_typenameNode buildin_typename ;
    public int dim ;//0 for non-array, 1 for [], 2 for [][], etc.

    public varTypeNode(position pos, String classID, buildin_typenameNode buildin_typename, int dim) {
        super (pos) ;
        this.classIdentifier = classID ;
        this.buildin_typename = buildin_typename ;
        this.dim = dim ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}