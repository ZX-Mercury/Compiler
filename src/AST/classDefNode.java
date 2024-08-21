package AST;

import Util.position;

import java.util.ArrayList;

public class classDefNode extends bodyNode {
    public String name;
    public classConstructNode constructor;
    public ArrayList<funcDefNode> funcList;
    public ArrayList<varDefNode> varList;

    public classDefNode(position pos, String name) {
        super(pos);
        this.name = name;
        this.funcList = new ArrayList<>();
        this.varList = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        //visitor.visit(this);
    }
}
