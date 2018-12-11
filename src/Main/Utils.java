package Main;

import Instruction.Instruction;
import Instruction.InstructionQueue;

import java.util.ArrayList;

public class Utils {
    public static ArrayList<Instruction> fillArray() {
        Instruction add = null;
        Instruction sub = null;
        try {
            add = new Instruction(Instruction.ADD, Instruction.FORMAT_ARITHMETIC, 2, 1, 0);
            sub = new Instruction(Instruction.SUB, Instruction.FORMAT_ARITHMETIC, 2, 2, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Instruction> instrsList = new ArrayList<Instruction>(){};
        instrsList.add(add);
        instrsList.add(sub);

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
