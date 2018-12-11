package Main;

import Instruction.Instruction;
import Instruction.InstructionQueue;

import java.util.ArrayList;

public class Utils {
    public static ArrayList<Instruction> fillArray() {
        Instruction lw = null;
        try {
            lw = new Instruction(Instruction.LW, Instruction.FORMAT_LW_SW, 2, 1, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Instruction> instrsList = new ArrayList<Instruction>(){};
        instrsList.add(lw);

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
