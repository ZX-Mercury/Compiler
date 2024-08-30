package AST.Definition;

import AST.ASTVisitor;
import AST.Statement.suiteNode;
import AST.bodyNode;
import Util.Type;
import Util.position;

public class funcDefNode extends bodyNode {
    public Type type;
    public String name;
    public funcDefParameterNode parameterList;
    public suiteNode suite;

    public funcDefNode(position pos, Type type, String name,
                       funcDefParameterNode parameterList, suiteNode suite) {
        super(pos);
        this.type = type;
        this.name = name;
        this.parameterList = parameterList;
        this.suite = suite;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
