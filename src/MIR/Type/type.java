package MIR.Type;

import Util.Type;

public abstract class type {
    public static type toIRType(Type type) {
        if (type.btype == Type.basicType.Void) {
            return new voidType();
        }
        if (type.btype == Type.basicType.Null) {
            return new ptrType();
        }
        type baseType;
        switch (type.btype) {
            case Type.basicType.Int:
                baseType = new intType(32);
                break;
            case Type.basicType.Bool:
                baseType = new intType(1);
                break;
            case Type.basicType.String:
                baseType = new ptrType(new intType(8));
                break;
            default:
                baseType = null;     //need to be implemented
        }
        if (type.dim > 0) {
            return new ptrType(baseType, type.dim);
        }
        return baseType;
    }
}
