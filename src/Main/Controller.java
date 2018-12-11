    package Main;

import Instruction.Instruction;
import Instruction.InstructionQueue;
import MemoryAndBuffer.LoadBuffer;
import MemoryAndBuffer.Memory;


import java.util.ArrayList;
import MemoryAndBuffer.RegFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements LoadBuffer.MemoryInterface, Main.ClkInterface{
    private ArrayList<Instruction> instrsList;
    private InstructionQueue instrQueue;
    private LoadBuffer loadBuffer;
    private Memory memory;
    private ROB rob;
    private Reservation_Station rs;

    public Controller(){
        instrsList = Utils.fillArray();
        instrQueue = new InstructionQueue();
        loadBuffer = new LoadBuffer(this);
        memory = new Memory();
        rob = new ROB();
        rs = new Reservation_Station();
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

    @Override
    public void didUpdate() {
        // always @ posedge clk
        /*
        *   Issue
        */
        Instruction instr = instrQueue.peek();
        if(rob.check() && rs.check(instr)) {
            if(instr.getName().equals(Instruction.LW) || instr.getName().equals(Instruction.SW)) {
                Instruction deqIns = instrQueue.dequeue();
                fetch(deqIns);
                try {
                    loadBuffer.insertInstr(deqIns);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else {
                Instruction deqIns = instrQueue.dequeue();
                fetch(deqIns);
            }
            /*
                * Execute and Write Back
            */
            execute();
            rob.commit();
        }
    }
    
    private void fetch(Instruction deqIns) {
        rs.add(deqIns, rob, rob.enqueue(deqIns));
    }
    
    private void execute() {
        for(String format: Reservation_Station.formats) {
            rs.remove(format, rob, Main.CC);
        }
    }
    
}
