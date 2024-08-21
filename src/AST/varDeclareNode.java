package AST;

import Util.position;

public class varDeclareNode extends ASTNode{
    public String name ;
    public ExpressionNode expression ;
    public Boolean isInitialized ;

    public varDeclareNode (position pos, String name) {
        super (pos) ;
        this.name = name ;
        isInitialized = false ;
    }

    public varDeclareNode (position pos, String name, ExpressionNode expression) {
        super (pos) ;
        this.name = name ;
        this.expression = expression ;
        this.isInitialized = true ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}