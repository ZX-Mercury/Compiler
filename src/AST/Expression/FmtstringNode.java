package AST.Expression;

import AST.ASTVisitor;

import Util.position;
import Util.Type;

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

    @Override
    public void checkType() {
        for(ExpressionNode node : expr){
            if(node.type.dim != 0){
                throw new Util.error.semanticError("Semantic Error: fmtstring cannot contain array", pos);
            }
            if(node.type.btype != Type.basicType.String
                    && node.type.btype != Type.basicType.Int
                    && node.type.btype != Type.basicType.Bool){
                throw new Util.error.semanticError("Semantic Error: fmtstring cannot contain this type", pos);
            }
        }
        type = new Type(Type.basicType.String, 0, false);
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
