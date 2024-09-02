
package Util.Scope;

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
            throw new semanticError("Duplicated definition:" + it.name, it.pos);
        funcMember.put(it.name, it);
    }
    public void addClass(classDefNode it){
        if(classMember.containsKey(it.name))
            throw new semanticError("Duplicated definition:" + it.name, it.pos);
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
        funcDefParameterNode para_empty = new funcDefParameterNode(null);

//        void print(string str);
        funcDefParameterNode para_print = new funcDefParameterNode(null);
        para_print.parameters.add(new parameterNode(null,new Type(Type.basicType.String,0,false),"__str"));
        Type fcty_print = new Type (Type.basicType.Void,0,false);
        funcDefNode tmp = new funcDefNode(null, fcty_print,
                "print",para_print,null);
        funcMember.put("print", tmp);

//        void println(string str);
        funcDefParameterNode para_println = new funcDefParameterNode(null);
        para_println.parameters.add(new parameterNode(null,new Type(Type.basicType.String,0,false),"__str"));
        Type fcty_println = new Type (Type.basicType.Void,0,false);
        funcDefNode tmp2 = new funcDefNode(null, fcty_println,
                "println",para_println,null);
        funcMember.put("println", tmp2);

//        void printInt(int n);
        funcDefParameterNode para_printInt = new funcDefParameterNode(null);
        para_printInt.parameters.add(new parameterNode(null,new Type(Type.basicType.Int,0,false),"__n"));
        Type fcty_printInt = new Type (Type.basicType.Void,0,false);
        funcDefNode tmp3 = new funcDefNode(null, fcty_printInt,
                "printInt",para_printInt,null);
        funcMember.put("printInt", tmp3);

//        void printlnInt(int n);
        funcDefParameterNode para_printlnInt = new funcDefParameterNode(null);
        para_printlnInt.parameters.add(new parameterNode(null,new Type(Type.basicType.Int,0,false),"__n"));
        Type fcty_printlnInt = new Type (Type.basicType.Void,0,false);
        funcDefNode tmp4 = new funcDefNode(null, fcty_printlnInt,
                "printlnInt",para_printlnInt,null);
        funcMember.put("printlnInt", tmp4);

//        string getString();
        Type fcty_getString = new Type (Type.basicType.String,0,false);
        funcDefNode tmp5 = new funcDefNode(null, fcty_getString,
                "getString", para_empty,null);
        funcMember.put("getString", tmp5);

//        int getInt();
        Type fcty_getInt = new Type (Type.basicType.Int,0,false);
        funcDefNode tmp6 = new funcDefNode(null, fcty_getInt,
                "getInt", para_empty,null);
        funcMember.put("getInt", tmp6);

//        string toString(int i);
        funcDefParameterNode para_toString = new funcDefParameterNode(null);
        para_toString.parameters.add(new parameterNode(null,new Type(Type.basicType.Int,0,false),"__i"));
        Type fcty_toString = new Type (Type.basicType.String,0,false);
        funcDefNode tmp7 = new funcDefNode(null, fcty_toString,
                "toString",para_toString,null);
        funcMember.put("toString", tmp7);

        /*//int size();
        funcDefParameterNode para_size = new funcDefParameterNode(null);
        Type fcty_size = new Type (Type.basicType.Int,0,false);
        funcDefNode tmp8 = new funcDefNode(null, fcty_size,
                "size",para_size,null);
        funcMember.put("size", tmp8);*/
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
