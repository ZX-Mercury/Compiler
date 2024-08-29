package Util;

import Util.error.semanticError;
import Util.position;
import Util.Type;
import AST.functypenameNode;

import java.util.HashSet;
import java.util.HashMap;

public class Scope {

    public HashMap<String, Type> members;
    public boolean isLoop, isFunction, isClass;
    public functypenameNode fucRetType;
    private Scope parentScope;


    public Scope(Scope parentScope) {
        members = new HashMap<>();
        isLoop =  parentScope==null? false : parentScope.isLoop;
        isFunction = parentScope==null? false : parentScope.isFunction;
        isClass = parentScope==null? false : parentScope.isClass;
        fucRetType = parentScope==null? null : parentScope.fucRetType;
        this.parentScope = parentScope;
    }

    public Scope parentScope() {
        return parentScope;
    }

    public void defineVariable(String name, Type t, position pos) {
        if (members.containsKey(name))
            throw new semanticError("member redefine", pos);
        members.put(name, t);
    }

    public boolean containsVariable(String name, boolean lookUpon) {
        if (members.containsKey(name)) return true;
        else if (parentScope != null && lookUpon)
            return parentScope.containsVariable(name, true);
        else return false;
    }

    public Type getType(String name, boolean lookUpon) {
        if (members.containsKey(name)) return members.get(name);
        else if (parentScope != null && lookUpon)
            return parentScope.getType(name, true);
        return null;
    }
}