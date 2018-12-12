package Instruction;

public class Instruction {
    private String name, format;
    private int regA, regB, regC;
    private int imm;
    private int pc;

    // All Formats
    public static final String
            FORMAT_LW_SW = "LOAD/STORE",
            FORMAT_UNCONDITIONAL_BRANCH = "UNCONDITIONAL BRANCH",
            FORMAT_CONDITIONAL_BRANCH = "CONDITIONAL BRANCH",
            FORMAT_JALR = "JALR",
            FORMAT_RET = "RET",
            FORMAT_ARITHMETIC = "ARITHMETIC INSTRS",
            FORMAT_ARITHMETIC_IMM = "ARITHMETIC Imm INSTRS";

    // All Instructions
    public static final String
            LW = "LW",
            SW = "SW",
            JMP = "JMP",
            BEQ = "BEQ",
            JALR = "JALR",
            RET = "RET",
            ADD = "ADD",
            SUB = "SUB",
            ADDI = "ADDI",
            NAND = "NAND",
            MUL = "MUL";


    /*
     *    Constructors
     * */

    // lw/sw/beq   or arith constructor
    public Instruction(String name, String format, int regA, int regB, int imm_regC) throws Exception {
        this.name = name;
        this.format = format;
        this.regA = regA;
        this.regB = regB;

        if(format.equals(FORMAT_ARITHMETIC))
            this.regC = imm_regC;

        else if(format.equals(FORMAT_LW_SW) || format.equals(FORMAT_CONDITIONAL_BRANCH) || format.equals(FORMAT_ARITHMETIC_IMM))
            this.imm = imm_regC;
        else
            throw new Exception("Exception: Please check your format");
    }

    // jmp/ret constructor
    public Instruction(String name, String format, int imm_regA) throws Exception {
        if(format.equals(FORMAT_UNCONDITIONAL_BRANCH))
            this.imm = imm_regA;
        else if(format.equals(FORMAT_RET))
            this.regA = imm_regA;
        else
            throw new Exception("Exception: Please check your format");

        this.name = name;
        this.format = format;
    }

    // jalr constructor
    public Instruction(String name, String format, int regA, int regB) throws Exception {
        if(!format.equals(FORMAT_JALR))
            throw new Exception("Exception: Format must be JALR");

        this.name = name;
        this.format = format;
        this.regA = regA;
        this.regB = regB;
    }


    /*
    *    Getters
    * */

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    public int  getRegA() {
        return regA;
    }

    public int getRegB() {
        return regB;
    }

    public int getRegC() {
        return regC;
    }

    public int getImm() {
        return imm;
    }


    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }
}
