package MIR.Instruction;

public abstract class terminalInst extends Instruction {
    public terminalInst() {
        super();
    }

    @Override
    public abstract String toStr() ;
}
