package Main;
import Instruction.Instruction;
import MemoryAndBuffer.RegFile;
import java.util.Iterator;


public class Main {
    
    public static int CC = 1;
 
    public static void main(String[] args){
        //Controller controller = new Controller();
        //controller.run();
        Instruction [] inst = new  Instruction [7];
       try {
            inst[0] = new Instruction(Instruction.ADD, Instruction.FORMAT_ARITHMETIC, 1 , 2, 0);
            inst[1] = new Instruction(Instruction.SUB, Instruction.FORMAT_ARITHMETIC, 2, 1, 4);
            inst[2] = new Instruction(Instruction.ADDI, Instruction.FORMAT_ARITHMETIC, 3, 2, 22);
            inst[3] = new Instruction(Instruction.ADD, Instruction.FORMAT_ARITHMETIC, 4, 2, 2);


        } catch (Exception ex) {
            ex.printStackTrace();}
        
        
       RegFile.write(2, (float)2);
        
        ROB rob = new ROB();
        Reservation_Station rs = new Reservation_Station ();

        for ( int i=0 ;i< 4 ;i++)
        {
            int x = rob.enqueue(inst[i]) ;
            rs.add(inst[i],rob, x);
        }
        for (int i =0 ;i<4 ;i++)
        {
            System.out.println (inst[i].getName()+ " " +inst[i].getRegA() + " " + inst[i].getRegB() + " " + inst[i].getRegC() + "\n");
        }
       // System.out.println (rob);
       Iterator it = rob.iterator() ;
       while (it.hasNext())
           System.out.println (it.next());
      
       
       
       
        rs.remove("ADD_SUB_ADDI", rob , CC) ;
     
        Iterator it3 = rob.iterator() ;
       while (it3.hasNext())
           System.out.println (it3.next());
       
       rs.remove("ADD_SUB_ADDI", rob, CC) ;
       
       Iterator it4 = rs.iterator();
       while (it4.hasNext())
       System.out.println (it4.next());
      
    }
    }
