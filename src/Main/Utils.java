package Main;

import Instruction.Instruction;
import Instruction.InstructionQueue;

import java.util.ArrayList;

public class Utils {
    public static ArrayList<Instruction> fillArray() {
        Instruction addi = null;
        Instruction sub = null;
        Instruction lw = null;
        Instruction sub2 = null;
        Instruction mul = null;
        Instruction mul2 = null;
        Instruction nand = null;
        Instruction add = null;

        try {
            //lw = new Instruction(Instruction.LW, Instruction.FORMAT_LW_SW , 3 , 0, 4);
            add = new Instruction(Instruction.ADD, Instruction.FORMAT_ARITHMETIC, 2, 1, 0);
            addi = new Instruction(Instruction.ADDI, Instruction.FORMAT_ARITHMETIC_IMM, 4, 0, 2);
           //addi = new Instruction(Instruction.ADDI, Instruction.FORMAT_ARITHMETIC_IMM, 2, 1, 2);
            sub = new Instruction(Instruction.SUB, Instruction.FORMAT_ARITHMETIC, 2, 3, 1);
            sub = new Instruction(Instruction.SUB, Instruction.FORMAT_ARITHMETIC, 3, 3, 4);
            mul = new Instruction(Instruction.MUL, Instruction.FORMAT_ARITHMETIC, 5, 3, 3);
            mul2 = new Instruction(Instruction.MUL, Instruction.FORMAT_ARITHMETIC, 6, 3, 3);
            nand = new Instruction(Instruction.NAND, Instruction.FORMAT_ARITHMETIC, 6, 1, 3);
            //nand = new Instruction(Instruction.NAND, Instruction.FORMAT_ARITHMETIC, 6, 5, 6);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Instruction> instrsList = new ArrayList<Instruction>(){};
        
        //instrsList.add(lw);
        
        instrsList.add(add);
        instrsList.add(mul2);
        instrsList.add(nand);
        instrsList.add(addi);
        instrsList.add(sub);
        instrsList.add(add);
        instrsList.add(addi);
        instrsList.add(addi);
        instrsList.add(addi);
        instrsList.add(addi);

       // instrsList.add(add);
        //instrsList.add(nand);
        
       // instrsList.add(addi);
       // instrsList.add(sub);
        

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
}
