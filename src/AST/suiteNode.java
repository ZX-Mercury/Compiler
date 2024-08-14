package AST;

import Util.position;

import java.util.ArrayList;

public class suiteNode extends ASTNode {
    public ArrayList<StmtNode> statementNodes ;

    public suiteNode (position pos) {
        super (pos) ;
        statementNodes = new ArrayList<>();
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}