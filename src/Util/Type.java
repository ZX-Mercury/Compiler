package Util;

import java.util.ArrayList;

public class Type {
    public enum basicType {
        Int, Bool, String, Void, Class, Function, This, Null
    }
    public basicType btype ;
    public String Identifier ;
    public int dim ;
    public boolean isLeftValue ;
    public Type functionReturnType ;
    public ArrayList<Type> functionParameters ;

    public Type (basicType _type, int _dim, boolean _isLeftValue) {
        btype = _type ;
        dim = _dim ;
        isLeftValue = _isLeftValue ;
    }

    public Type (String _identifier, int _dim, boolean _isLeftValue) { //class
        btype = basicType.Class ;
        Identifier = _identifier ;
        dim = _dim ;
        isLeftValue = _isLeftValue ;
    }

    public Type (String _identifier, Type returnType, ArrayList<Type> parameters) { //function
        btype = basicType.Function ;
        Identifier = _identifier ;
        functionReturnType = returnType ;
        functionParameters = parameters ;
        dim = 0 ;
        isLeftValue = false ;
    }

    public Type (Type _type) {
        btype = _type.btype ;
        Identifier = _type.Identifier ;
        dim = _type.dim ;
        isLeftValue = _type.isLeftValue ;
        if (_type.functionReturnType != null)
            functionReturnType = new Type (_type.functionReturnType) ;
        else
            functionReturnType = null ;
        if (_type.functionParameters != null)
            functionParameters = new ArrayList<>(_type.functionParameters) ;
        else
            functionParameters = null ;
    }
}
/*
public boolean cmptype (Type a, Type b) {
    if (a.dim != b.dim) return false ;
    if (a.btype != b.btype) return false ;
    if (a.btype == basicType.Class) {
        if (!a.Identifier.equals (b.Identifier)) return false ;
    }
    if (a.btype == basicType.Function) {
        if (!a.functionReturnType.cmptype (b.functionReturnType)) return false ;
        if (a.functionParameters.size() != b.functionParameters.size()) return false ;
        for (int i = 0; i < a.functionParameters.size(); ++ i)
            if (!a.functionParameters.get(i).cmptype (b.functionParameters.get(i))) return false ;
    }
    return true ;
}*/
