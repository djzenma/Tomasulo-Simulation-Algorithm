package MemoryAndBuffer;

public class Memory {
    private final int CAPACITY = 65536;           // 2^16
    private final int MAX_VALUE = 2147483647;     // 2^31 - 1

    private int[] mem;


    public Memory() {
        this.mem = new int[CAPACITY];
        for (int i = 0; i < CAPACITY; i++) {
            mem[i] = 0;
        }
        mem[0] = 5;
    }

    public int read(int address) throws Exception {
        if(address > CAPACITY || address < 0)
            throw new Exception("Memory Address out of range!");
        if((address % 4) != 0)
            throw new Exception("Address must be a multiple of 4");

        return mem[address];
    }

    public boolean write(int address, int data) throws Exception {
        if(data > MAX_VALUE)
            throw new Exception("Data cannot Exceed 32 bit value");
        if(address > CAPACITY || address < 0)
            throw new Exception("Memory Address out of range!");
        mem[address] = data;
        return true;
    }
    
    public void print() {
        System.out.println("mem[4]=" + mem[4]);
    }
}
