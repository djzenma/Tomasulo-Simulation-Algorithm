package MemoryAndBuffer;

import Instruction.Instruction;
import Main.Main;
import Main.Controller;


public class LoadBuffer {
<<<<<<< HEAD
    private int size;
    private final int MAX_CAPACITY = 4;
    private String [] buffer;   // stores the addresses
=======

    private int load_size;
    private int store_size;
    private final int LOAD_CAPACITY = 2;
    private final int STORE_CAPACITY = 2;
    private final int MAX_CAPACITY = LOAD_CAPACITY + STORE_CAPACITY;

    private int[] buffer;   // stores the addresses
>>>>>>> ca6bac890c2908c3ce20617f94d8070ee5613db8
    private MemoryInterface memInterface;

    public LoadBuffer(Controller controller) {
        buffer = new String [MAX_CAPACITY];
        for (int i = 0; i < MAX_CAPACITY; i++) {
            buffer[i] = "0";
        }
        load_size = 0;
        store_size = 0;
        memInterface = controller;
    }


    /*
    *   Inserts Instruction in Load Buffer and returns Loaded Data from Memory
    * */
    public boolean insertInstr(Instruction instr) throws Exception {
        if( !freeSlot(instr) ) {
            return false;
        }
        else {
<<<<<<< HEAD
            // CC 1: Issuing and A = Imm
            buffer[size] = String.valueOf(loadInstr.getImm());
            System.out.println("Load Buffer: entry #" + size + ", Address: " + buffer[size]);
=======
            int index;

            if(instr.getName().equals(Instruction.LW)) {
                index = getFreeLoadSlot(load_size);
                load_size++;
            }
            else {
                index = getFreeStoreSlot(store_size);
                store_size++;
            }

            // Computing Address for Load or Store

            // CC 1: Issuing
            // Starting Computing Address:
            // A = Imm
            buffer[index] = instr.getImm();
            System.out.println("Load Buffer: entry #" + index + ", Address: " + buffer[index]);
>>>>>>> ca6bac890c2908c3ce20617f94d8070ee5613db8

            // CC 2: A = Imm + Regs[Rs2]
            nextCycle(Main.CC);
            try {
                buffer[index] += RegFile.read(instr.getRegB());
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Cannot read from Register File");
            }
            System.out.println("Load Buffer: entry #" + index + ", Address: " + buffer[index]);

<<<<<<< HEAD
            // Writing
            int loadedValue = memInterface.loadFromMem(Integer.parseInt(buffer[size]));

            return loadedValue;
=======
            // CC 3: Writing
            nextCycle(Main.CC);
            // Case Load
            if(instr.getName().equals(Instruction.LW)) {
                // Free the slot
                load_size--;
                // Load from Memory
                int loadedValue = memInterface.loadFromMem(buffer[index]);
                if(loadedValue == -1)
                    return false;
                // Callback once done
                else
                    return memInterface.memoryLoadDone(instr.getRegA(), loadedValue);
            }
            // Case Store
            else {
                // Free the slot
                store_size--;
                // Store in Memory
                return memInterface.storeInMem(buffer[index], instr.getRegA());
            }
>>>>>>> ca6bac890c2908c3ce20617f94d8070ee5613db8
        }
    }

    private int getFreeStoreSlot(int stSize) {
        return stSize + 2;
    }

    private int getFreeLoadSlot(int ldSize) {
        return ldSize;
    }

    private boolean freeSlot(Instruction instr) {
        if( (instr.getName().equals(Instruction.LW) && load_size >= LOAD_CAPACITY) ||
                (instr.getName().equals(Instruction.SW) && store_size >= STORE_CAPACITY))
            return false;
        else
            return true;
    }

    private void nextCycle(int cc) {
        while(cc >= Main.CC) {
            continue;
        }
    }

    public interface MemoryInterface {
        // Load Interface
        int loadFromMem(int address);
        boolean memoryLoadDone(int regDestination, int data);

        // Store Interface
        boolean storeInMem(int address, int data);
    }
}
