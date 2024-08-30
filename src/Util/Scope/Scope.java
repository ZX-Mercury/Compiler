package Util.Scope;

import Util.error.semanticError;
import Util.position;
import Util.Type;

import java.util.HashMap;

public class Scope {

    public HashMap<String, Type> members;
    public boolean isLoop;//Only check whether THIS scope is Loop.
    public String className = null;

    public Type fucRetType;
    private Scope parentScope;


    public Scope(Scope parentScope) {
        members = new HashMap<>();
        isLoop =  parentScope==null? false : parentScope.isLoop;
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

    public boolean isInLoop() {
        if(isLoop) return true;
        else if(parentScope != null) return parentScope.isInLoop();
        else return false;
    }

    public String isInClass() {
        if(className != null) return className;
        else if(parentScope != null) return parentScope.isInClass();
        else return null;
    }
}