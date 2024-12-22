package MIR.Type;

public class ptrType extends type {
    public type basetp;
    public int dim;

    public ptrType() {
        basetp = new voidType();
        dim = 1;
    }

    public ptrType(type basetp) {
        if (basetp instanceof ptrType t) {
            this.basetp = t.basetp;
            this.dim = t.dim + 1;
        }
        else {
            this.basetp = basetp;
            this.dim = 1;
        }
    }

    public ptrType(type basetp, int dim) {
        if (basetp instanceof ptrType t) {
            this.basetp = t.basetp;
            this.dim = t.dim + dim;
        }
        else {
            this.basetp = basetp;
            this.dim = dim;
        }
    }
    public type deref() {
        return dim == 1 ? basetp : new ptrType(basetp, dim - 1);
    }

    @Override
    public String toStr() {
        return basetp.toString() + "*".repeat(dim);
    }
}
