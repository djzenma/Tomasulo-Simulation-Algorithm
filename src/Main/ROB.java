/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Instruction.Instruction;
import MemoryAndBuffer.RegFile;
import java.util.Iterator;
import java.util.NoSuchElementException;

public  class ROB implements Iterable {
    static ROB_NODE first;    // beginning of queue
    static ROB_NODE last;     // end of queue
    static int n;  // number of elements on queue
    static int last_index = -1 ;

   

    // helper linked list class
   
    /**
     * Initializes an empty queue.
     */
    public  ROB() {
        first = null;
        last  = null;
        n = 0;
    }

    /**
     * Returns true if this queue is empty.
     *
     * @return {@code true} if this queue is empty; {@code false} otherwise
     */
    public   boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public  int size() {
        return n;
    }
    
     public  boolean check() {
         if (n<6)
        return true;
         else 
        return false ;
    }

    /**
     * Returns the item least recently added to this queue.
     *
     * @return the item least recently added to this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public   ROB_NODE peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first;
    }

    /**
     * Adds the item to this queue.
     *
     * @param  item the item to add
     */
    public  int enqueue(Instruction inst) {
        if (n <6)
        {
            ROB_NODE oldlast = last;
            last = new ROB_NODE ();
            last.dest = inst.getRegA();
            last.index = ++ last_index ;
            last.previous = oldlast; 
            last.type = inst.getName();
            if (isEmpty()) first = last;
            else  oldlast.next = last;
            n++  ;
            return last_index ;
        }
        else 
        {
            System.out.println ("FULL ROB !!");
            return -1 ;
        }
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     *
     * @return the item on this queue that was least recently added
     * @throws NoSuchElementException if this queue is empty
     */
    public  ROB_NODE  dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        ROB_NODE item = first;
        first = first.next;
        n--;
        if (isEmpty()) last = null;   // to avoid loitering
        return item;
    }

    public int find_dest(int reg) 
    { 
        System.out.println("REG" + reg);
         ROB_NODE current = last ;
       while (current != null) 
       {
            ROB_NODE item = current;
            current = current.previous;
            if (item.dest == reg)
            return item.index;
       }
       return -1 ;
    }
        public boolean is_ready(int indx )  
        { 
            ROB_NODE current = first  ;
            while (current != null)
             {
                    if (current.index == indx)
                        return current.ready ;
                    current = current.next ;
             }         
            System.out.println ("ROB Error");
            return false ;
            
        }
        
        public Float get_value (int indx )
        {
            ROB_NODE current = first  ;
            while (current != null)
             {
                    if (current.index == indx)
                        return current.value ;
                    current = current.next ;
             }   
            System.out.println("ROB ERROR");
            return null ;
        }
        
        public boolean set_value (int indx , Float value )
        {
            ROB_NODE current = first  ;
            while (current != null)
             {
                    if (current.index == indx)
                    {
                     current.value = value  ;
                     current.ready = true ;
                     return true ;
                    }
                    current = current.next ;
             } 
            return false ;
        }
    /**
     * Returns a string representation of this queue.
     *
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public  String toString() {
        StringBuilder s = new StringBuilder();
        ROB_NODE  pointer = first ;
        while ( pointer != null)
        {
            String item = pointer.type ;
            s.append(item);
            item = String.valueOf(pointer.dest);
            s.append(item);
            s.append(' ');
            pointer = pointer.next  ;
        }
        
        return s.toString();
    }
    public void commit ()
    {
        if (first.ready == true)
        {
            RegFile.write(first.dest, first.value);
        }
        first= first.next ;
        n-- ;
    }
    
 public  Iterator iterator()  {
        return new ListIterator(first);  
    } }