    package Main;

import Instruction.Instruction;
import Instruction.InstructionQueue;
import MemoryAndBuffer.LoadBuffer;
import MemoryAndBuffer.Memory;


import java.util.ArrayList;
import MemoryAndBuffer.RegFile;



public class Controller implements LoadBuffer.MemoryInterface, Main.ClkInterface{
    private ArrayList<Instruction> instrsList;
    private InstructionQueue instrQueue;
    private LoadBuffer loadBuffer;
    private Memory memory;
    private ROB rob;
    private Reservation_Station rs;

    public Controller(){
        loadBuffer = new LoadBuffer(this);
        memory = new Memory();
        rob = new ROB();
        rs = new Reservation_Station();

        instrsList = Utils.fillArray();
        instrQueue = new InstructionQueue();
        instrsList.forEach((i) -> instrQueue.enqueue(i));
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

    // always @ posedge clk
    @Override
    public void didUpdate() {
        /*
        *   Issue
        */
        Instruction instr = instrQueue.peek();
        if(rob.check() && rs.check(instr)) {
            if(instr.getName().equals(Instruction.LW) || instr.getName().equals(Instruction.SW)) {
                if(instr.getName().equals(Instruction.LW) && loadBuffer.loadIsFree()) {
                    loadBufferLogic();
                }
                else if(instr.getName().equals(Instruction.SW) && loadBuffer.storeIsFree()) {
                    loadBufferLogic();
                }
                else {
                    // TODO::wait
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

    private void loadBufferLogic() {
        Instruction deqIns = instrQueue.dequeue();
        fetch(deqIns);
        try {
            loadBuffer.insertInstr(deqIns);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getNumInstrs() {
        return instrsList.size();
    }
    
}
