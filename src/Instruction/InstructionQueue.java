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

    public boolean enqueue(Instruction instr, int pc) {
        if(size > MAX_CAPACITY)
            return false;
        else {
            instr.setPc(pc);
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
    
    public Instruction peek() {
        return instrQueue.peek();
    }
    
    public boolean searchForPc(int pc) {
        for(Instruction i: instrQueue) {
            if(i.getPc() == pc)
                return true;
        }
        return false;
    }
    
    public  boolean isEmpty() {
        return instrQueue.isEmpty();
    }
}
