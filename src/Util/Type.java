package Util;

import java.util.ArrayList;

public class Type {
    public enum basicType {
        Int, Bool, String, Void, Class, Function, This, Null
    }
    public basicType btype ;
    public String Identifier ;//class name
    public int dim ;
    public boolean isLeftValue ;
    public Type functionReturnType ;
    public ArrayList<Type> functionParameters =new ArrayList<>() ;

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

    public Type (Type returnType, ArrayList<Type> parameters) { //function
        btype = basicType.Function ;
        functionReturnType = returnType ;
        functionParameters = parameters ;
        dim = 0 ;//or returnType.dim?
        isLeftValue = false ;
    }
    /*public Type (String _identifier, Type returnType, ArrayList<Type> parameters) { //function
        btype = basicType.Function ;
        Identifier = _identifier ;
        functionReturnType = returnType ;
        functionParameters = parameters ;
        dim = 0 ;
        isLeftValue = false ;
    }*/

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

