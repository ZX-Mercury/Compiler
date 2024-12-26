package MIR.Value;

import MIR.Type.*;

public class varLocal extends value {
    public String name;
    private static int cnt = 0;

    public varLocal(String name, type type) {
        super(type);
        this.name = name;
    }

    public static varLocal newvarlocal(type type) {
        return new varLocal(String.format("%d", cnt++), type);
    }

    @Override
    public String toStr() {
        return "%" + name;
    }
}
