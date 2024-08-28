package AST;

import Util.position;
import Util.Type;
import Util.error.semanticError;

import java.util.ArrayList;

public class arrayLiteralNode extends LiteralNode {
    public ArrayList<LiteralNode> elements;

    public arrayLiteralNode(position pos) {
        super(pos);
        elements = new ArrayList<>();
    }

    @Override
    public void checkType() {
        if(elements.isEmpty()){
            type=new Type((Type.basicType) null,1,false);
            return;
        }
        Type tmp=elements.getFirst().type;
        for(LiteralNode element:elements){
            if(element.type.btype!=null){
                tmp = element.type;
                break;
            }
        }
        for(LiteralNode element:elements){
            if(!element.type.equals(tmp)&&!(element.type.btype==null)){
                throw new semanticError("Semantic Error: type not match", pos);
            }
        }
        type = new Type(tmp);
    }
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
