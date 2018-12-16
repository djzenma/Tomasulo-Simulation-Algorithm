package Main;

import Instruction.Instruction;
import Instruction.InstructionQueue;
import MemoryAndBuffer.LoadBuffer;
import MemoryAndBuffer.Memory;


import java.util.ArrayList;


import java.util.Iterator;
import java.util.Scanner;
import java.util.function.Consumer;


public class Controller implements LoadBuffer.MemoryInterface, Main.ClkInterface {
    private ArrayList<Instruction> instrsList;
    public InstructionQueue instrQueue;
    private LoadBuffer loadBuffer;
    private Memory memory;
    public ROB rob;
    public Reservation_Station rs;
    private int clockCycle;
    Integer obj = null;
    private Integer pcPredict;
    private Instruction prevInstr;
    public int mispredictionNum;

    public Controller() {
        loadBuffer = new LoadBuffer(this);
        memory = new Memory();
        rob = new ROB();
        rs = new Reservation_Station();

        instrsList = Utils.fillArray();
        instrQueue = new InstructionQueue();
        int pc = 0;
        for (int i= 0; i<instrsList.size() ; i++) {
            instrQueue.enqueue(instrsList.get(i), pc);
            pc += 1;
        }
        clockCycle = 0;
        pcPredict = 0;
        prevInstr = null;
        mispredictionNum = 0;
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
        rs.update(rob_index, data);
        return rob.set_value(rob_index, data, null);
    }

    @Override
    public boolean storeInMem(int rob_index, int address, int data) {
        try {
            return rob.set_value(rob_index, data, address);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // always @ posedge clk
    @Override
    public void didUpdate(int CC) {
        if (obj == null) {
            Scanner in = new Scanner(System.in);
            System.out.println("Would You Like to Run Cycle_by_Cycle? (1/0)");
            obj = in.nextInt();
        }
        Integer pcIn = null;

        /*
         *   Issue
         */
        // if(CC % 2 == 1) {
        // clockCycle++;
        System.out.println("Cycle " + CC);
        // }


        Instruction instr1 = null;
        Instruction[] deqIns = new Instruction[2];
        for (int i = 0; i < 2; i++) {
            deqIns[i] = null;
        }


        for (int i = 0; i < 2; i++) {
            instr1 = instrQueue.peek();

            if (instr1 != null) {
                if (instr1.getPc() != pcPredict) {
                    if(prevInstr != null && prevInstr.getName().equals(Instruction.BEQ)) {
                        refillQueue(pcPredict);
                        mispredictionNum++;
                        System.out.println("misprediction " + instr1.getName());
                    }
                }
                if (rob.check() && rs.check(instr1)) {
                    //System.out.println("check " + instr1.getName());
                    if (instr1.getName().equals(Instruction.LW) || instr1.getName().equals(Instruction.SW)) {
                        if (instr1.getName().equals(Instruction.LW) && loadBuffer.loadIsFree()) {
                            deqIns[i] = instrQueue.dequeue();

                            if(instrQueue.peek() != null && instrQueue.peek().getName().equals(Instruction.BEQ))
                                prevInstr = deqIns[i];

                            loadBufferLogic(deqIns[i]);
                            // rob.enqueue(deqIns[i]) ;
                            //fetch(deqIns[i]);
                        } else if (instr1.getName().equals(Instruction.SW) && loadBuffer.storeIsFree()) {
                            deqIns[i] = instrQueue.dequeue();

                            if(instrQueue.peek() != null && instrQueue.peek().getName().equals(Instruction.BEQ))
                                prevInstr = deqIns[i];

                            loadBufferLogic(deqIns[i]);
                            //rob.enqueue(deqIns[i]) ;
                            //fetch(deqIns[i]);
                        } else {
                            // TODO::wait
                        }
                    }
                    else {
                        //System.out.println("before fetch " + instrQueue.peek().getName());
                        deqIns[i] = instrQueue.dequeue();
                        if( instrQueue.peek() != null && instrQueue.peek().getName().equals(Instruction.BEQ))
                            prevInstr = deqIns[i];

                        if (deqIns[i].getName() == Instruction.BEQ) {
                            if (deqIns[i].getImm() < 0)
                                pcPredict = deqIns[i].getPc() + deqIns[i].getImm();
                            else
                                pcPredict = deqIns[i].getPc() + 1;
                        }
                        else
                            pcPredict = deqIns[i].getPc() + 1;


                        fetch(deqIns[i]);
                    }
                }
            }

            /*
             * Execute and Write Back
             */
            //nextCycle(CC);
        }

        execute(deqIns[0], deqIns[1]);
        execute(deqIns[0], deqIns[1]);

        for (int i = 0; i < 2; i++) {
            if(!rob.isEmpty()) {
                pcIn = rob.commit(memory);

                boolean found = false;
                Instruction branchedInstr = null;
                // Branch
                if (pcIn != null) {
                    rob.flush();
                    if (instrQueue.searchForPc(pcIn)) {
                        refillQueue(pcIn);
                        /*
                        while (!instrQueue.isEmpty() || !found) {
                            branchedInstr = instrQueue.peek();
                            if (branchedInstr.getPc() == pcIn)
                                found = true;
                            else
                                instrQueue.dequeue();
                        }
                        */
                    }
                    else {
                        //rob.flush();
                        refillQueue(pcIn);
                    }
                }
            }
        }


        if (obj == 1) {
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


    }


    private int fetch(Instruction deqIns) {
        int indx = rob.enqueue(deqIns);
        rs.add(deqIns, rob, indx, deqIns.getPc());
        return indx;
    }

    private void execute(Instruction deqIns, Instruction deqIns2) {
        Integer pc = null;
        Integer pc2 = null;
        for (String format : Reservation_Station.formats) {
            if(deqIns != null)
                pc = deqIns.getPc();
            if(deqIns2 != null)
                pc2 = deqIns.getPc();
            rs.remove(format, rob, Main.CC, pc, pc2); //store start cycle in rs
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

    private void refillQueue(int index) {
        while (!instrQueue.isEmpty()) {
            instrQueue.dequeue();
        }
        for (int i = index; i < instrsList.size(); i++) {
            instrQueue.enqueue(instrsList.get(i), i);
        }
    }

}
