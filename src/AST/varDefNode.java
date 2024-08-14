package AST;

import java.util.ArrayList;

import Util.position;

public class varDefNode extends ASTNode {
    public typenameNode typeNode ;
    public ArrayList<varDeclareNode> varDeclarations ;

    public varDefNode (position pos, typenameNode type) {
        super (pos) ;
        this.typeNode = type ;
        this.varDeclarations = new ArrayList<>();
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}