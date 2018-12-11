    package Main;

import Instruction.Instruction;
import Instruction.InstructionQueue;
import MemoryAndBuffer.LoadBuffer;
import MemoryAndBuffer.Memory;

<<<<<<< HEAD
import MemoryAndBuffer.RegFile;

import java.util.ArrayList;

=======
import java.util.ArrayList;
import MemoryAndBuffer.RegFile;

>>>>>>> d08eca792fa3344639d72a2f7ed096ccce9c43b7
public class Controller implements LoadBuffer.MemoryInterface{
    private ArrayList<Instruction> instrsList;
    private InstructionQueue instrQueue;
    private LoadBuffer loadBuffer;
    private Memory memory;

    public Controller(){
        instrsList = Utils.fillArray();
        instrQueue = new InstructionQueue();
        loadBuffer = new LoadBuffer(this);
        memory = new Memory();
    }

    public void run() {
<<<<<<< HEAD
        instrsList.forEach((i) -> {
            instrQueue.enqueue(i);
        });
=======
        for (Instruction i: instrsList) {
            instrQueue.enqueue(i);
        }
>>>>>>> d08eca792fa3344639d72a2f7ed096ccce9c43b7

        try {
            loadBuffer.insertInstr(instrQueue.dequeue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int loadFromMem(int address) {
        try {
            return memory.read(address);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean memoryLoadDone(int regDestination, int data) {
        return RegFile.write(regDestination, data);
    }

    @Override
    public boolean storeInMem(int address, int data) {
        try {
            memory.write(address, data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
