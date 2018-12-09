package Main;

import Instruction.Instruction;

import java.util.ArrayList;
import java.util.Queue;

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

    public static void DeQueueAll(Queue<Instruction> instrQueue, boolean consoleLog) {
        if(consoleLog) {
            while (!instrQueue.isEmpty()) {
                System.out.println(instrQueue.remove());
            }
        }
        else {
            while (!instrQueue.isEmpty()) {
                instrQueue.remove();
            }
        }
    }
}
