package Instruction;

import Main.Controller;

import java.util.ArrayList;
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

    public Instruction dequeue(ArrayList<Instruction> list, int awaitingIndex) {
        size--;
        if(size < MAX_CAPACITY && Controller.awaitingInstrIndex < list.size()) {
            instrQueue.add(list.get(awaitingIndex));
            Controller.awaitingInstrIndex++;
        }
        return instrQueue.remove();
    }

    public void dequeueAll() {
        while (!instrQueue.isEmpty()) {
            instrQueue.remove();
        }
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
