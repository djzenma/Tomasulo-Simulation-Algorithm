
package Main;

/////Struct ////
public class ROB_NODE {
 
    Integer index ;
    String type ; 
    Integer dest ;
    boolean ready ;
    Integer value ;
    Integer jalr_value2 ;
    ROB_NODE next;
    ROB_NODE previous;
    
    public ROB_NODE ()
    {
        jalr_value2 = null ;
        index= null ;
        type = null; 
        dest = null ;
        ready = false ;
        value = null ;
        next = null;
        previous = null ; 
    }
    
     public String toString() {
        StringBuilder s = new StringBuilder();
       
            s.append(index);
            s.append(' ');
            s.append(type);
            s.append(' ');
            s.append(dest);
            s.append(' ');
            s.append(ready);
            s.append(' ');
            s.append(value);
            s.append(' ');
        
        
        return s.toString();
    }
    
}
