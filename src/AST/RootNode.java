package AST;

import Util.error.position;

import java.util.ArrayList;

public class RootNode extends ASTNode {
    public FnRootNode fn;
    public ArrayList<structDefNode> strDefs = new ArrayList<>();

    public RootNode(position pos, FnRootNode fn) {
        super(pos);
        this.fn = fn;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}