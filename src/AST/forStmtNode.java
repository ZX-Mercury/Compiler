package AST;

import Util.position;

public class forStmtNode extends LoopStmtNode {
    public forInitNode forInit ;
    public ExpressionNode forCondition, forIncr ;

    public StmtNode statement ;

    public forStmtNode (position pos, forInitNode forInit, ExpressionNode forCondition,
                        ExpressionNode forIncr, StmtNode statement) {
        super (pos) ;
        this.forInit = forInit ;
        this.forCondition = forCondition ;
        this.forIncr = forIncr ;
        this.statement = statement ;
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}