package MemoryAndBuffer;

import Instruction.Instruction;
import Main.Main;
import Main.Controller;


//TODO :: Handle Removing slots
public class LoadBuffer {
    private int size;
    private final int MAX_CAPACITY = 4;
    private int[] buffer;   // stores the addresses
    private MemoryInterface memInterface;

    public LoadBuffer(Controller controller) {
        buffer = new int[MAX_CAPACITY];
        for (int i = 0; i < MAX_CAPACITY; i++) {
            buffer[i] = 0;
        }
        size = 0;
        memInterface = controller;
    }


    /*
    *   Inserts Instruction in Load Buffer and returns Loaded Data from Memory
    * */
    public int insertInstr(Instruction loadInstr, int cc) throws Exception {
        //TODO :: Handle waiting till free slot
        if(size >= MAX_CAPACITY) {
            return -1;
        }
        else {
            // CC 1: Issuing and A = Imm
            buffer[size] = loadInstr.getImm();
            System.out.println("Load Buffer: entry #" + size + ", Address: " + buffer[size]);

            // CC 2: A = Imm + Regs[Rs2]
            nextCycle(cc);
            try {
                buffer[size] += RegFile.read(loadInstr.getRegB());
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Cannot read from Register File");
            }
            System.out.println("Load Buffer: entry #" + size + ", Address: " + buffer[size]);

            // Writing
            int loadedValue = memInterface.loadFromMem(buffer[size]);

            return loadedValue;
        }
    }

    private void nextCycle(int cc) {
        while(cc >= Main.CC) {
            continue;
        }
    }

    public interface MemoryInterface {
        int loadFromMem(int address);
    }
}
