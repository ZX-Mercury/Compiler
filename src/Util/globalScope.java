package Util;

import Util.error.semanticError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class globalScope extends Scope {
    private HashMap<String, Type> types = new HashMap<>();
    //private HashMap<String, Type> functions = new HashMap<>();
    public globalScope(Scope parentScope) {
        super(parentScope);
        addType("print", new Type(new Type(Type.basicType.Void,0,false),
                new ArrayList<>(Arrays.asList(new Type(Type.basicType.String,0,false)))),
                null);
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
