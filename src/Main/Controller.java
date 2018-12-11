package Main;

import Instruction.Instruction;
import MemoryAndBuffer.LoadBuffer;
import MemoryAndBuffer.Memory;
import Main.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        /*try {
            Instruction lw = new Instruction(Instruction.LW, Instruction.FORMAT_LW_SW, 1, 2, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        try {
            int loadedData = loadBuffer.insertInstr(instrQueue.remove(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
     

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
