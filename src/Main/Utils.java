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
        Instruction sub3 = null;
        try {
            //lw = new Instruction(Instruction.SW, Instruction.FORMAT_LW_SW , 3 , 0, 4);
            addi = new Instruction(Instruction.ADDI, Instruction.FORMAT_ARITHMETIC_IMM, 1, 0, 2);
            sub = new Instruction(Instruction.SUB, Instruction.FORMAT_ARITHMETIC, 0, 0, 0);
            sub2 = new Instruction(Instruction.MUL, Instruction.FORMAT_ARITHMETIC, 0, 0, 0);
            sub3 = new Instruction(Instruction.MUL, Instruction.FORMAT_ARITHMETIC, 0, 0, 0);
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Instruction> instrsList = new ArrayList<Instruction>(){};
        
       // instrsList.add(lw);
        instrsList.add(addi);
        instrsList.add(sub);
        instrsList.add(sub2);
        instrsList.add(sub3);
      
        

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
