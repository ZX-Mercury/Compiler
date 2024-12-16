package AST.Definition;

import AST.ASTVisitor;
import AST.Statement.suiteNode;
import AST.bodyNode;
import MIR.IREntity.block;
import Util.Type;
import Util.position;

import java.util.ArrayList;

public class funcDefNode extends bodyNode {
    public Type retType;
    public String name;
    public funcDefParameterNode parameterList;
    public suiteNode suite;
    public block blk;

    public funcDefNode(position pos, Type type, String name,
                       funcDefParameterNode parameterList, suiteNode suite) {
        super(pos);
        this.retType = type;
        this.name = name;
        this.parameterList = parameterList;
        this.suite = suite;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
