package Main;

import Instruction.Instruction;
import MemoryAndBuffer.LoadBuffer;
import MemoryAndBuffer.Memory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Controller implements LoadBuffer.MemoryInterface{
    ArrayList<Instruction> instrsList;
    Queue<Instruction> instrQueue;
    LoadBuffer loadBuffer;
    Memory memory;

    public Controller(){
        instrsList = Utils.fillArray();
        instrQueue = new LinkedList<Instruction>();
        loadBuffer = new LoadBuffer(this);
        memory = new Memory();
    }

    public void run() {
        instrQueue.addAll(instrsList);

        try {
            int loadedData = loadBuffer.insertInstr(instrQueue.remove(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Float.

        Utils.DeQueueAll(instrQueue, true);
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
}
