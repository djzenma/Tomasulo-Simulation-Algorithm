    package Main;

import Instruction.Instruction;
import Instruction.InstructionQueue;
import MemoryAndBuffer.LoadBuffer;
import MemoryAndBuffer.Memory;

import MemoryAndBuffer.RegFile;

import java.util.ArrayList;

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
        instrsList.forEach((i) -> {
            instrQueue.enqueue(i);
        });

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
