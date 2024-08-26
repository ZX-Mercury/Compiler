package AST;

import java.util.ArrayList;

import Util.position;

public class RootNode extends ASTNode {
    public ArrayList<ASTNode> parts;

    public RootNode (position pos) {
        super (pos) ;
        parts = new ArrayList<>() ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}