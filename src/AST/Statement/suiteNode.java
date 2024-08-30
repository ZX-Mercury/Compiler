package AST.Statement;

import AST.ASTNode;
import AST.ASTVisitor;
import Util.position;

import java.util.ArrayList;

public class suiteNode extends StmtNode {
    public ArrayList<ASTNode> statementNodes ;//stmtNode

    public suiteNode (position pos) {
        super (pos) ;
        statementNodes = new ArrayList<>();
    }

    @Override
    public void accept (ASTVisitor visitor) {
        visitor.visit (this) ;
    }
}