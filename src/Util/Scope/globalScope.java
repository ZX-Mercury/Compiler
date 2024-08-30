
package Util.Scope;

import AST.*;

import AST.Definition.classDefNode;
import AST.Definition.funcDefNode;
import AST.Definition.funcDefParameterNode;
import AST.Definition.parameterNode;
import Util.Type;
import Util.error.semanticError;
import Util.position;

import java.util.HashMap;

public class globalScope extends Scope {
    public HashMap<String, funcDefNode> funcMember = new HashMap<>();
    public HashMap<String, classDefNode> classMember = new HashMap<>();

    public void addFunc(funcDefNode it){
        if(funcMember.containsKey(it.name))
            throw new semanticError("Re-definition", it.pos);
        funcMember.put(it.name, it);
    }
    public void addClass(classDefNode it){
        if(classMember.containsKey(it.name))
            throw new semanticError("Re-definition", it.pos);
        classMember.put(it.name, it);
    }
    public funcDefNode getFunc(String name){
        return funcMember.get(name);
    }
    public classDefNode getClass(String name){
        return classMember.get(name);
    }

    public globalScope(Scope parentScope) {
        super(parentScope);
        addBuiltinFunc();
    }
    private void addBuiltinFunc() {

        /*funcDefParameterNode para_print = new funcDefParameterNode(null);
        //para_print.parameters.add(new parameterNode(null,new Type(Type.basicType.String,0,false)),);
        functypenameNode fcty_print = new functypenameNode (null,true);
        funcDefNode tmp = new funcDefNode(null, fcty_print,
                "print",para_print,null);

        funcMember.put("print", tmp);*/

//        ArrayList<paraDef> para3 = new ArrayList<>();
//        para3.add(new paraDef(new DataType("string"), "str"));
//        funcDefNode print = new funcDefNode(null, "print", new DataType("void"), para3);
//        funcMember.put(print.funcName, print);

/*
函数：void print(string str);
函数：void println(string str);
函数：void printInt(int n);
函数：void printlnInt(int n);
函数：string getString();
函数：int getInt();
函数：string toString(int i);
         */

    }

    private HashMap<String, Type> types = new HashMap<>();
    //private HashMap<String, Type> functions = new HashMap<>();
    public void addType(String name, Type t, position pos) {
        if (types.containsKey(name))
            throw new semanticError("multiple definition of " + name, pos);
        types.put(name, t);
    }
    public Type getTypeFromName(String name, position pos) {
        if (types.containsKey(name)) return types.get(name);
        throw new semanticError("no such type: " + name, pos);
    }
}
