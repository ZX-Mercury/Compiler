package AST;

import Util.position;
import Util.Type;

public class buildin_typenameNode extends ASTNode {
    public Type.basicType bType ;

    public buildin_typenameNode (position pos, Type.basicType _Type) {
        super (pos) ;
        bType = _Type ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}