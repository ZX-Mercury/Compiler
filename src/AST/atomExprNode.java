package AST;

import Util.position;

public class atomExprNode extends ExpressionNode {
    public enum primaryType {
        This, Null, Int, Bool, String, Identifier, Fmtstring
    }

    public primaryType type ;
    public String identifier ;

    public atomExprNode(position pos, primaryType type, String identifier) {
        super (pos) ;
        this.type = type ;
        this.identifier = identifier ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}