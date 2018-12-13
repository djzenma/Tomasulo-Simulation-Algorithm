    package Main;

import Instruction.Instruction;
import Instruction.InstructionQueue;
import MemoryAndBuffer.LoadBuffer;
import MemoryAndBuffer.Memory;


import java.util.ArrayList;
import MemoryAndBuffer.RegFile;
import java.util.Iterator;
import java.util.function.Consumer;



public class Controller implements LoadBuffer.MemoryInterface, Main.ClkInterface{
    private ArrayList<Instruction> instrsList;
    private InstructionQueue instrQueue;
    private LoadBuffer loadBuffer;
    private Memory memory;
    private ROB rob;
    private Reservation_Station rs;
    private int clockCycle;
 
    public Controller(){
        loadBuffer = new LoadBuffer(this);
        memory = new Memory();
        rob = new ROB();
        rs = new Reservation_Station();

        instrsList = Utils.fillArray();
        instrQueue = new InstructionQueue();
        int pc = 0;
        for (Instruction i: instrsList) {
            instrQueue.enqueue(i, pc);
            pc += 1;
        }
        clockCycle = 0;
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
    public boolean memoryLoadDone(int rob_index, int data) {
        //System.out.println("HNNA HNNA HNNA");
        rs.update (rob_index,  data);
        return rob.set_value(rob_index, data , null);
    }

    @Override
    public boolean storeInMem(int rob_index, int address, int data) {
        try {
             return rob.set_value(rob_index, data , address);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // always @ posedge clk
    @Override
    public void didUpdate(int CC) {
        
        /*
        *   Issue
        */
        if(CC % 2 == 1) {
            clockCycle++;
            System.out.println("Cycle " + clockCycle);
        }
            
        
        Instruction instr1 = null;
        
            instr1 = instrQueue.peek();
        //System.out.println("Inst" + instr.getName());
        if(rob.check() && rs.check(instr1)) {
            if(instr1.getName().equals(Instruction.LW) || instr1.getName().equals(Instruction.SW)) {
                if(instr1.getName().equals(Instruction.LW) && loadBuffer.loadIsFree()) {
                    Instruction deqIns = instrQueue.dequeue();
                    loadBufferLogic(deqIns);
                   // rob.enqueue(deqIns) ;
                    //fetch(deqIns);
                }
                else if(instr1.getName().equals(Instruction.SW) && loadBuffer.storeIsFree()) {
                    Instruction deqIns = instrQueue.dequeue();
                    loadBufferLogic(deqIns);
                    //rob.enqueue(deqIns) ;
                    //fetch(deqIns);
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
            //nextCycle(CC);

        }
        
            execute(instr1);
            Integer pcIn = rob.commit(memory);
            System.out.println (pcIn);
            
            boolean found = false;
            Instruction branchedInstr = null;
            if(pcIn != null) {
                // Branch
                rob.flush();
                if(instrQueue.searchForPc(pcIn)) {
                    while(!instrQueue.isEmpty() && !found) {
                        branchedInstr = instrQueue.peek();
                        if(branchedInstr.getPc() == pcIn) 
                            found = true;
                        else
                            instrQueue.dequeue();
                    }
                }        
            }
            
            
            Iterator it = rob.iterator();
           Iterator itRs = rs.iterator();
           it.forEachRemaining(new Consumer() {
                @Override
                public void accept(Object t) {
                    System.out.println("ROB: " + t.toString());
                }
            });
           itRs.forEachRemaining(new Consumer() {
                @Override
                public void accept(Object t) {
                    System.out.println("RS: " + t.toString());
                }
            });
           memory.print();
    }


    private int fetch(Instruction deqIns) {
        int indx = rob.enqueue(deqIns) ;
        rs.add(deqIns, rob, indx , deqIns.getPc());
        return indx ;
    }
    
    private void execute(Instruction deqIns) {
        for(String format: Reservation_Station.formats) {
            rs.remove(format, rob, Main.CC ,deqIns.getPc()); //store start cycle in rs 
            rs.finish_execution(Main.CC, rob);
        }
    }

    private void loadBufferLogic(Instruction deqIns) {
        
        try {
            loadBuffer.insertInstr(deqIns, fetch(deqIns));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getNumInstrs() {
        return instrsList.size();
    }
    
}
