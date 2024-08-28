package AST;

import Util.position;
import Util.Type;

public class atomExprNode extends ExpressionNode {
    public enum primaryType {
        This, Null, Int, Bool, String, Identifier, Fmtstring
    }

    public primaryType pritype ;
    public String identifier ;

    public atomExprNode(position pos, primaryType pritype, String identifier) {
        super (pos) ;
        this.pritype = pritype ;
        this.identifier = identifier ;
    }

    @Override
    public void checkType() {
        switch (pritype) {
            case This:
                type = new Type(Type.basicType.This, 0, true) ;
                break ;
            case Null:
                type = new Type(Type.basicType.Null, 0, false) ;
                break ;
            case Int:
                type = new Type(Type.basicType.Int, 0, false) ;
                break ;
            case Bool:
                type = new Type(Type.basicType.Bool, 0, false) ;
                break ;
            case String, Fmtstring:
                type = new Type(Type.basicType.String, 0, false) ;
                break ;
            case Identifier:
                type = new Type(identifier, 0, true) ;
                break ;
        }
    }
    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}