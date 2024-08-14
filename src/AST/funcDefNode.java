package AST;

import Util.position;

public class funcDefNode extends bodyNode {
    public typenameNode type;
    public String name;
    public funcDefParameterNode parameterList;
    public suiteNode suite;

    public funcDefNode(position pos, typenameNode type, String name, funcDefParameterNode parameterList, suiteNode suite) {
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
