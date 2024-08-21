package AST;

import Util.position;

public class ifStmtNode extends ASTNode {
    public ExpressionNode expression ;
    public StmtNode trueStatement, falseStatement ;

    public ifStmtNode (position pos, ExpressionNode expression, StmtNode trueStatement, StmtNode falseStatement) {
        super (pos) ;
        this.expression = expression ;
        this.trueStatement = trueStatement ;
        this.falseStatement = falseStatement ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}