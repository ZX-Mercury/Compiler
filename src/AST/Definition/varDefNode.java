package AST.Definition;

import java.util.ArrayList;

import AST.ASTNode;
import AST.ASTVisitor;
//import AST.varTypeNode;
import Util.Type;
import Util.position;

public class varDefNode extends ASTNode {
    public Type type;
    public ArrayList<varDeclareNode> varDeclarations ;

    public varDefNode (position pos, Type type) {
        super (pos) ;
        this.type = type ;
        this.varDeclarations = new ArrayList<>();
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}