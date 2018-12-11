package Main;

import Instruction.Instruction;
import Instruction.InstructionQueue;
import MemoryAndBuffer.LoadBuffer;
import MemoryAndBuffer.Memory;
<<<<<<< HEAD
import Main.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
=======
import MemoryAndBuffer.RegFile;

import java.util.ArrayList;
>>>>>>> ca6bac890c2908c3ce20617f94d8070ee5613db8

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
        instrQueue.addAll(instrsList);
        
        /*try {
            Instruction lw = new Instruction(Instruction.LW, Instruction.FORMAT_LW_SW, 1, 2, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
=======
        for (Instruction i: instrsList) {
            instrQueue.enqueue(i);
        }
>>>>>>> ca6bac890c2908c3ce20617f94d8070ee5613db8

        try {
            loadBuffer.insertInstr(instrQueue.dequeue());
        } catch (Exception e) {
            e.printStackTrace();
        }
<<<<<<< HEAD
     
=======
>>>>>>> ca6bac890c2908c3ce20617f94d8070ee5613db8

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
