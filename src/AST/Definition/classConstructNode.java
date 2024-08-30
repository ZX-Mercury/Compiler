package AST.Definition;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.Statement.suiteNode;
import Util.position;

public class classConstructNode extends ASTNode {
    public String className;
    public suiteNode suite;

    public classConstructNode(position pos, String className, suiteNode suite) {
        super(pos);
        this.className = className;
        this.suite = suite;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
