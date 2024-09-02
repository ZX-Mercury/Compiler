package AST.Definition;

import AST.ASTVisitor;
import AST.bodyNode;
import AST.Definition.*;
import Util.Type;
import Util.position;

import java.util.ArrayList;
import java.util.HashMap;

public class classDefNode extends bodyNode {
    public String name;
    public classConstructNode constructor;
    public HashMap<String, funcDefNode> funcList;
    public HashMap<String, varDeclareNode> varList;

    public classDefNode(position pos, String name) {
        super(pos);
        this.name = name;
        this.funcList = new HashMap<>();
        this.varList = new HashMap<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
