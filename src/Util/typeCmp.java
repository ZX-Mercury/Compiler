package Util;

public class typeCmp {
    private typeCmp () {
        throw new RuntimeException("cannot instantiate typeCmp");
    }
    public static boolean cmptype (Type a, Type b) {
        if (a.dim != b.dim) return false ;
        if (a.btype != b.btype) return false ;
        if (a.btype == Type.basicType.Class) {
            if (!a.Identifier.equals (b.Identifier)) return false ;
        }
        /*if (a.btype == Type.basicType.Function) {
            if (!cmptype (a.functionReturnType, b.functionReturnType)) return false ;
        if (a.functionParameters.size() != b.functionParameters.size()) return false ;
        for (int i = 0; i < a.functionParameters.size(); ++ i)
            if (!a.functionParameters.get(i).cmptype (b.functionParameters.get(i))) return false ;
        }*/
        return true ;
    }
}
