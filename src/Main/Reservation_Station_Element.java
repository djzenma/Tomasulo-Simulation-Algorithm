
package Main;


public class Reservation_Station_Element {
    String operation ;
    boolean busy ; 
    Integer Vk ;
    Integer Vj ;
    Integer Qk ; 
    Integer PC ;
    Integer Qj ;
    Integer rob_indx ;
    Integer execution_start_cycle ;
    
    public Reservation_Station_Element()
    {
            PC = null ;
            execution_start_cycle= null ;
            operation = null ;
            busy = false  ; 
            Vk = null ;
            Vj = null ;
            Qk = null ; 
            Qj = null ;
            rob_indx = null ;
    }
    
      public String toString() {
        StringBuilder s = new StringBuilder();
       
            
            s.append(operation);
            s.append(' ');
            s.append(busy);
            s.append(' ');
            s.append(Vj);
            s.append(' ');
            s.append(Vk);
            s.append(' ');
            s.append(Qj);
            s.append(' ');
            s.append(Qk);
            s.append(' ');
            s.append(rob_indx);
            
        
        
        return s.toString();
    }
    
    
}
