package Instruction;

import java.util.LinkedList;
import java.util.Queue;

public class InstructionQueue {
    private Queue<Instruction> instrQueue;
    private int size;
    private final int MAX_CAPACITY = 4;

    public InstructionQueue() {
        size = 0;
        instrQueue = new LinkedList<Instruction>();
    }

    public boolean enqueue(Instruction instr) {
        if(size > MAX_CAPACITY)
            return false;
        else {
            instrQueue.add(instr);
            size++;
            return true;
        }
    }

    public Instruction dequeue() {
        size--;
        return instrQueue.remove();
    }

    public int getSize() {
        return size;
    }
}
