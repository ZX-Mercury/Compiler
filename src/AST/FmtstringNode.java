package AST;

import Util.position;
import java.util.ArrayList;

public class FmtstringNode extends ExpressionNode {
    public ArrayList<ExpressionNode> expr;
    public String head, tail;
    public ArrayList<String> middle;

    public FmtstringNode(position pos) {
        super(pos);
        this.expr = new ArrayList<>();
        this.head = "";
        this.tail = "";
        this.middle = new ArrayList<>();
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
