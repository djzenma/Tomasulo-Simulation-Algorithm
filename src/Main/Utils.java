package Main;

import Instruction.Instruction;
import Instruction.InstructionQueue;

import java.util.ArrayList;

public class Utils {
    public static ArrayList<Instruction> fillArray() {
        Instruction instr1 = null;
        Instruction instr3 = null;
        Instruction instr4 = null;
        Instruction instr5 = null;
        Instruction instr6 = null;
        Instruction instr7 = null;

        try {
            //lw = new Instruction(Instruction.LW, Instruction.FORMAT_LW_SW , 3 , 0, 4);
            instr1 = new Instruction(Instruction.BEQ, Instruction.FORMAT_CONDITIONAL_BRANCH, 0, 0, 1);
            instr3 = new Instruction(Instruction.ADDI, Instruction.FORMAT_ARITHMETIC_IMM, 2, 1, 3);
            instr4 = new Instruction(Instruction.ADD, Instruction.FORMAT_ARITHMETIC, 5, 3, 3);
            instr5 = new Instruction(Instruction.SUB, Instruction.FORMAT_ARITHMETIC, 6, 5, 6);
            //instr6 = new Instruction(Instruction.MUL, Instruction.FORMAT_ARITHMETIC, 6, 3, 3);
            instr7 = new Instruction(Instruction.NAND, Instruction.FORMAT_ARITHMETIC, 6, 5, 6);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Instruction> instrsList = new ArrayList<Instruction>(){};

        instr1.setPc(0);
        instr3.setPc(1);
        instr4.setPc(2);
        instr5.setPc(3);
        //instr6.setPc(4);
        instr7.setPc(5);

        instrsList.add(instr1);
        instrsList.add(instr3);
        instrsList.add(instr4);
        instrsList.add(instr5);
        //instrsList.add(instr6);
        instrsList.add(instr7);

        return instrsList;
    }

    public static void DeQueueAll(InstructionQueue instrQueue, boolean consoleLog) {
        if(consoleLog) {
            for (int i = 0; i < instrQueue.getSize(); i++) {
                System.out.println(instrQueue.dequeue());
            }
        }
        else {
            for (int i = 0; i < instrQueue.getSize(); i++) {
                instrQueue.dequeue();
            }
        }
    }

    public static int getIndexByPc(int pc, ArrayList<Instruction> list) {
        for (int i=0; i<list.size(); i++) {
            if(list.get(i).getPc() == pc)
                return i;
        }
        return -1;
    }
}
