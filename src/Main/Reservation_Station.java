
package Main;


import Instruction.Instruction;
import MemoryAndBuffer.RegFile;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;


public class Reservation_Station implements Iterable{
Reservation_Station_Element [] LW ; 
int LW_counter =0 ;
Reservation_Station_Element [] SW ;
int SW_counter =0 ;
Reservation_Station_Element [] JMP_JALR_RET ;
int JMP_JALR_RET_counter =0 ;
Reservation_Station_Element [] BEQ  ;
int BEQ_counter =0 ;
Reservation_Station_Element []ADD_SUB_ADDI ; 
int ADD_SUB_ADDI_counter =0 ;
Reservation_Station_Element [] NAND; 
int NAND_counter =0 ;
Reservation_Station_Element [] MUL ;
int MUL_counter =0 ;

public Reservation_Station ()
{
      LW = new Reservation_Station_Element [2]; 
      for (int i =0; i<2 ; i++ )
      LW[i] = new Reservation_Station_Element ();
      SW = new Reservation_Station_Element [2];
      for (int i =0; i<2 ; i++ )
      SW[i] = new Reservation_Station_Element ();
      JMP_JALR_RET  = new Reservation_Station_Element [3];
      for (int i =0; i<3 ; i++ )
      JMP_JALR_RET[i] = new Reservation_Station_Element ();
      BEQ  = new Reservation_Station_Element [2]; 
      for (int i =0; i<2 ; i++ )
      BEQ[i] = new Reservation_Station_Element ();
      ADD_SUB_ADDI  = new Reservation_Station_Element [3]; 
      for (int i =0; i<3 ; i++ )
      ADD_SUB_ADDI[i] = new Reservation_Station_Element ();
      NAND =  new Reservation_Station_Element [1]; 
      for (int i =0; i<1 ; i++ )
      NAND[i] = new Reservation_Station_Element ();
      MUL =  new Reservation_Station_Element [2];
      for (int i =0; i<2 ; i++ )
      MUL[i] = new Reservation_Station_Element ();
      
      
}

public boolean add (Instruction inst , ROB rob , int rob_ind)
{
    int y ; 
 switch (inst.getName())
        {
        case Instruction.LW : 
        {
            
            y = empty_index (LW);
            
            if (y != -1)
            {
                LW[y].operation =  inst.getName() ;
                LW[y].busy =  true ;
                
                
                LW[y].Qj= null ;
                LW[y].Vj= null ;
                LW[y].Qk= null;
                LW[y].Vk= null;
                LW[y].rob_indx = rob_ind ;
                
                LW_counter++ ;
                System.out.println(inst.getName() + " inst added!! ");
                return true ;
            }
            else 
             {
                        
                System.out.println(inst.getName() + " failed to add ");
                return false ;
            }
        }
        
        case Instruction.SW :
        {
            y =empty_index (SW);
           
            if (y != -1)
            {
                SW[y].operation =  inst.getName() ;
                SW[y].busy = true ;
               
                SW[y].Vj= null;
                SW[y].Qj= null ;
                SW[y].Vk= null;
                SW[y].Qk= null;
                SW[y].rob_indx = rob_ind ;
                SW_counter++ ;
                System.out.println(inst.getName() + " inst added!! ");
                return true ;
            }
            else 
             {
                        
                System.out.println(inst.getName() + " failed to add ");
                return false ;
                    }
        }
        case Instruction.JMP:
        {
            y =empty_index (JMP_JALR_RET);
            
                if (y != -1)
                {
                    JMP_JALR_RET[y].operation =  inst.getName() ;
                    JMP_JALR_RET[y].busy =  true ;
                    JMP_JALR_RET_counter ++ ;
                    JMP_JALR_RET[y].Qj = null ;
                    JMP_JALR_RET[y].Qk= null ;
                    JMP_JALR_RET[y].Vj= null ;
                    JMP_JALR_RET[y].Vk= null ;
                    JMP_JALR_RET[y].rob_indx = rob_ind ;
                     System.out.println(inst.getName() + " inst added!! ");
                    
                    return true ;
                }
                else 
                 {
                        
                System.out.println(inst.getName() + " failed to add ");
                return false ;
                    }
                 
        }
        case Instruction.JALR:
        {
            y =empty_index (JMP_JALR_RET);
            
                if (y != -1)
                {
                    JMP_JALR_RET[y].operation =  inst.getName() ;
                    JMP_JALR_RET[y].busy =  true ;
                    
                    
                    JMP_JALR_RET[y].Qj= null ;
                    JMP_JALR_RET[y].Vj= null ;
                    JMP_JALR_RET[y].Qk= null ;
                    JMP_JALR_RET[y].Vk=  null ;
                    JMP_JALR_RET[y].rob_indx = rob_ind ;
                
                
                    JMP_JALR_RET_counter++ ;
                    System.out.println(inst.getName() + " inst added!! ");
                    
                    return true ;
                }
                else 
                 {
                        
                System.out.println(inst.getName() + " failed to add ");
                return false ;
                    }
        }
        
         case Instruction.RET : 
         {
             y =empty_index (JMP_JALR_RET);
             
                if (y != -1)
                {
                    JMP_JALR_RET[y].operation =  inst.getName() ;
                    JMP_JALR_RET[y].busy =  true ;
                    
                    JMP_JALR_RET[y].Qj= null ;
                    JMP_JALR_RET[y].Vj= null ;
                    JMP_JALR_RET[y].Qk= null ;
                    JMP_JALR_RET[y].Vk=  null ;
                    JMP_JALR_RET[y].rob_indx = rob_ind ;
                
                    JMP_JALR_RET_counter++ ;
                    System.out.println(inst.getName() + " inst added!! ");
                    
                    return true ;
                }
                else 
                    {
                        
                System.out.println(inst.getName() + " failed to add ");
                return false ;
                    }
         }
        case Instruction.BEQ : 
        {
            y =empty_index (BEQ);
            
            if (y != -1)
            {
                BEQ[y].operation =  inst.getName() ;
                BEQ[y].busy =  true ;
                BEQ[y].rob_indx = rob_ind ;
                 
                int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegB());
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        BEQ[y].Vj= RegFile.read(inst.getRegB());
                        BEQ[y].Qj= null ;
                    }
                    else //in ROB 
                    {
                        if( rob.is_ready(rob_indx)) // if rob entry is available not in queue
                        {
                            BEQ[y].Vj= rob.get_value(rob_indx);
                            BEQ[y].Qj= null ;
                        }
                        else 
                        {
                             BEQ[y].Qj = rob_indx ;
                             BEQ[y].Vj= null ;
                        }
                            
                    }
                     
                
                    rob_indx = rob.find_dest(inst.getRegC());
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        BEQ[y].Vk= RegFile.read(inst.getRegC());
                        BEQ[y].Qk= null ;
                    }
                    else //in ROB 
                    {
                        if( rob.is_ready(rob_indx)) // if rob entry is available not in queue
                        {
                            BEQ[y].Vk= rob.get_value(rob_indx);
                            BEQ[y].Qk= null ;
                        }
                        else 
                        {
                             BEQ[y].Qk = rob_indx ;
                             BEQ[y].Vk= null ;
                        }
                
                    }
                BEQ_counter++ ;
                System.out.println(inst.getName() + " inst added!! ");
                return true ;
            }
            else 
             {
                        
                System.out.println(inst.getName() + " failed to add ");
                return false ;
             }
        }
        case Instruction.ADD:
        {
            y = empty_index (ADD_SUB_ADDI);
            
                if (y != -1)
                {
                    ADD_SUB_ADDI[y].operation =  inst.getName() ;
                    ADD_SUB_ADDI[y].busy =  true;
                    ADD_SUB_ADDI[y].rob_indx = rob_ind ;
                    
                    int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegB());
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        ADD_SUB_ADDI[y].Vj= RegFile.read(inst.getRegB());
                        ADD_SUB_ADDI[y].Qj= null ;
                    }
                    else //in ROB 
                    {
                        if( rob.is_ready(rob_indx)) // if rob entry is available not in queue
                        {
                            ADD_SUB_ADDI[y].Vj= rob.get_value(rob_indx);
                            ADD_SUB_ADDI[y].Qj= null ;
                        }
                        else 
                        {
                             ADD_SUB_ADDI[y].Qj = rob_indx ;
                             ADD_SUB_ADDI[y].Vj= null ;
                        }
                            
                    }
                     
                
                    rob_indx = rob.find_dest(inst.getRegC());
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        ADD_SUB_ADDI[y].Vk= RegFile.read(inst.getRegC());
                        ADD_SUB_ADDI[y].Qk= null ;
                    }
                    else //in ROB 
                    {
                        if( rob.is_ready(rob_indx)) // if rob entry is available not in queue
                        {
                            ADD_SUB_ADDI[y].Vk= rob.get_value(rob_indx);
                            ADD_SUB_ADDI[y].Qk= null ;
                        }
                        else 
                        {
                             ADD_SUB_ADDI[y].Qk = rob_indx ;
                             ADD_SUB_ADDI[y].Vk = null ;
                        }
                            
                    }
                    
                    ADD_SUB_ADDI_counter++ ;
                    System.out.println(inst.getName() + " inst added!! ");
                    
                    return true ;
                }
                else 
                 {
                        
                System.out.println(inst.getName() + " failed to add ");
                return false ;
                    }
        }
         case Instruction.ADDI:
        {
            y = empty_index (ADD_SUB_ADDI);
            
                if (y != -1)
                {
                    ADD_SUB_ADDI[y].operation =  inst.getName() ;
                    ADD_SUB_ADDI[y].busy =  true;
                     ADD_SUB_ADDI[y].rob_indx = rob_ind ;
                    
                int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegB());
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        ADD_SUB_ADDI[y].Vj= RegFile.read(inst.getRegB());
                        ADD_SUB_ADDI[y].Qj= null ;
                    }
                    else //in ROB 
                    {
                        if( rob.is_ready(rob_indx)) // if rob entry is available not in queue
                        {
                            ADD_SUB_ADDI[y].Vj= rob.get_value(rob_indx);
                            ADD_SUB_ADDI[y].Qj= null ;
                        }
                        else 
                        {
                             ADD_SUB_ADDI[y].Qj = rob_indx ;
                             ADD_SUB_ADDI[y].Vj= null ;
                        }
                            
                    }
                
                
                    ADD_SUB_ADDI[y].Vk= (float)inst.getRegC();
                
                
                    ADD_SUB_ADDI_counter++ ;
                    System.out.println(inst.getName() + " inst added!! ");
                    return true ;
                }
                else 
                 {
                        
                System.out.println(inst.getName() + " failed to add ");
                return false ;
                    }
        }
        case Instruction.SUB :
        {
            y = empty_index (ADD_SUB_ADDI);
            
                if (y != -1)
                {
                    ADD_SUB_ADDI[y].operation =  inst.getName() ;
                    ADD_SUB_ADDI[y].busy =  true;
                     ADD_SUB_ADDI[y].rob_indx = rob_ind ;
                    
                     int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegB());
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        ADD_SUB_ADDI[y].Vj= RegFile.read(inst.getRegB());
                        ADD_SUB_ADDI[y].Qj= null ;
                    }
                    else //in ROB 
                    {
                        if( rob.is_ready(rob_indx)) // if rob entry is available not in queue
                        {
                            ADD_SUB_ADDI[y].Vj= rob.get_value(rob_indx);
                            ADD_SUB_ADDI[y].Qj= null ;
                        }
                        else 
                        {
                             ADD_SUB_ADDI[y].Qj = rob_indx ;
                             ADD_SUB_ADDI[y].Vj= null ;
                        }
                            
                    }
                     
                
                    rob_indx = rob.find_dest(inst.getRegC());
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        ADD_SUB_ADDI[y].Vk= RegFile.read(inst.getRegC());
                        ADD_SUB_ADDI[y].Qk= null ;
                    }
                    else //in ROB 
                    {
                        if( rob.is_ready(rob_indx)) // if rob entry is available not in queue
                        {
                            ADD_SUB_ADDI[y].Vk= rob.get_value(rob_indx);
                            ADD_SUB_ADDI[y].Qk= null ;
                        }
                        else 
                        {
                             ADD_SUB_ADDI[y].Qk = rob_indx ;
                             ADD_SUB_ADDI[y].Vk= null ;
                        }
                            
                    }
                
                    ADD_SUB_ADDI_counter++ ;
                    System.out.println(inst.getName() + " inst added!! ");
                    
                    return true ;
                }
                else 
                 {
                        
                System.out.println(inst.getName() + " failed to add ");
                return false ;
                    }
        }
        case Instruction.NAND : 
        {
            y = empty_index (NAND);
            
                if (y != -1)
                {
                    NAND[y].operation =  inst.getName() ;
                    NAND[y].busy =  true;
                     NAND[y].rob_indx = rob_ind ;
                    
                     int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegB());
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        NAND[y].Vj= RegFile.read(inst.getRegB());
                        NAND[y].Qj= null ;
                    }
                    else //in ROB 
                    {
                        if( rob.is_ready(rob_indx)) // if rob entry is available not in queue
                        {
                            NAND[y].Vj= rob.get_value(rob_indx);
                            NAND[y].Qj= null ;
                        }
                        else 
                        {
                             NAND[y].Qj = rob_indx ;
                             NAND[y].Vj= null ;
                        }
                            
                    }
                     
                
                    rob_indx = rob.find_dest(inst.getRegC());
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        NAND[y].Vk= RegFile.read(inst.getRegC());
                        NAND[y].Qk= null ;
                    }
                    else //in ROB 
                    {
                        if( rob.is_ready(rob_indx)) // if rob entry is available not in queue
                        {
                            NAND[y].Vk= rob.get_value(rob_indx);
                            NAND[y].Qk= null ;
                        }
                        else 
                        {
                             NAND[y].Qk = rob_indx ;
                             NAND[y].Vk= null ;
                        }
                            
                    }
                
                    NAND_counter++ ;
                    System.out.println(inst.getName() + " inst added!! ");
                    
                    return true ;
                }
                else 
                 {
                        
                System.out.println(inst.getName() + " failed to add ");
                return false ;
                    }
        }
        case Instruction.MUL : 
          {
            y = empty_index (MUL);
            
                if (y != -1)
                {
                    MUL[y].operation =  inst.getName() ;
                    MUL[y].busy =  true;
                    MUL[y].rob_indx = rob_ind ;
                     int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegB());
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        MUL[y].Vj= RegFile.read(inst.getRegB());
                        MUL[y].Qj= null ;
                    }
                    else //in ROB 
                    {
                        if( rob.is_ready(rob_indx)) // if rob entry is available not in queue
                        {
                            MUL[y].Vj= rob.get_value(rob_indx);
                            MUL[y].Qj= null ;
                        }
                        else 
                        {
                             MUL[y].Qj = rob_indx ;
                             MUL[y].Vj= null ;
                        }
                            
                    }
                     
                
                    rob_indx = rob.find_dest(inst.getRegC());
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        MUL[y].Vk= RegFile.read(inst.getRegC());
                        MUL[y].Qk= null ;
                    }
                    else //in ROB 
                    {
                        if( rob.is_ready(rob_indx)) // if rob entry is available not in queue
                        {
                            MUL[y].Vk= rob.get_value(rob_indx);
                            MUL[y].Qk= null ;
                        }
                        else 
                        {
                             MUL[y].Qk = rob_indx ;
                             MUL[y].Vk= null ;
                        }
                            
                    }
                
                    MUL_counter++ ;
                    System.out.println(inst.getName() + " inst added!! ");
                    
                    return true ;
                }
                else 
                 {
                        
                System.out.println(inst.getName() + " failed to add ");
                return false ;
                    }
        }
        default :
             {
                        
                System.out.println(inst.getName() + " failed to add ");
                return false ;
              }

        }   
}
    public boolean remove (String type, ROB rob , int CC)
{
 
  int k = get_ready(type); //retrieves an inst with ready operands !!
  if (k != -1)
  {
     switch (type)
        {
        case "LW" : {
           
            
            System.out.println("an " + type + " is executing  ");
            try {
            LW[k].Qj =null;
            LW[k].Qk = null ;
            LW[k].Vj = null ;
            LW[k].Vk = null ;
            LW[k].busy = false  ;
            LW[k].operation = null ;
            LW[k].rob_indx = null ;
            return true ;
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Out Of Bounds !!");
                return false ;
            }
           
        } 
        case "SW" : 
        {
            System.out.println("an " + type + " is executing  ");
        
            try {
            SW[k].Qj =null;
            SW[k].Qk = null ;
            SW[k].Vj = null ;
            SW[k].Vk = null ;
            SW[k].busy = false  ;
            SW[k].operation = null ;
            SW[k].rob_indx = null ;

            return true ;}
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Out Of Bounds !!");
                return false ;
            }
        }
        case "JMP_JALR_RET" :
            {
            
            System.out.println("an " + type + " is executing  ");
             try {
            JMP_JALR_RET[k].Qj =null;
            JMP_JALR_RET[k].Qk = null ;
            JMP_JALR_RET[k].Vj = null ;
            JMP_JALR_RET[k].Vk = null ;
            JMP_JALR_RET[k].busy = false  ;
            JMP_JALR_RET[k].operation = null ;
             return true ;}
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Out Of Bounds !!");
                return false ;
            }
        }
       
        case "BEQ" :  
        {
            System.out.println("an " + type + " is executing  ");
            try {
            BEQ[k].Qj =null;
            BEQ[k].Qk = null ;
            BEQ[k].Vj = null ;
            BEQ[k].Vk = null ;
            BEQ[k].busy = false  ;
            BEQ[k].operation = null ;
            BEQ[k].rob_indx = null ;

            return true ;}
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Out Of Bounds !!");
                return false ;
            }
        }
        case "ADD_SUB_ADDI" :
            {
            //float result = execute(ADD_SUB_ADDI[k] , CC) ;
            //rob.set_value(ADD_SUB_ADDI[k].rob_indx, result);
            //update (ADD_SUB_ADDI[k].rob_indx  ,  result);
                ADD_SUB_ADDI[k].execution_start_cycle = CC;
            System.out.println("an " + type + " is executing  ");
            
             try {
            ADD_SUB_ADDI[k].Qj =null;
            ADD_SUB_ADDI[k].Qk = null ;
            ADD_SUB_ADDI[k].Vj = null ;
            ADD_SUB_ADDI[k].Vk = null ;
            ADD_SUB_ADDI[k].busy = false  ;
            ADD_SUB_ADDI[k].operation = null ;
            ADD_SUB_ADDI[k].rob_indx = null ;

             return true  ;}
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Out Of Bounds !!");
                return false ;
            }
        }
       
        case "NAND" :  
        {
            //float result = execute(NAND[k] , CC) ;
            //rob.set_value(NAND[k].rob_indx, result);
            //update (NAND[k].rob_indx  ,  result);
            NAND[k].execution_start_cycle = CC ;
            System.out.println("an " + type + " is executing  ");
            try {
            NAND[k].Qj =null;
            NAND[k].Qk = null ;
            NAND[k].Vj = null ;
            NAND[k].Vk = null ;
            NAND[k].busy = false  ;
            NAND[k].operation = null ;
            NAND[k].rob_indx = null ;

            return true ;}
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Out Of Bounds !!");
                return false ;
            }
        }
        case "MUL" : 
        {
            //float result = execute(MUL[k], CC ) ;
            //rob.set_value(MUL[k].rob_indx, result);
            //update (MUL[k].rob_indx  ,  result);
            MUL[k].execution_start_cycle = CC ;
            System.out.println("an " + type + " is executing  ");
            try {
            MUL[k].Qj =null;
            MUL[k].Qk = null ;
            MUL[k].Vj = null ;
            MUL[k].Vk = null ;
            MUL[k].busy = false  ;
            MUL[k].operation = null ;
            MUL[k].rob_indx = null ;
            return true ;}
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Out Of Bounds !!");
                return false ;
            }
        }
        default :
        return false ;

         
     }   
                
  }
  else
  {
      System.out.println ("No Ready Instructions !!");
      return false ;
  }
          
}


private int empty_index (Reservation_Station_Element [] arr)
{
for (int i=0 ;i< arr.length ; i++)
{
   
    if (arr[i].busy == false)
        return i ; 
}

return -1 ;
}
 
public void finish_execution (int CC , ROB rob)
{
    float result ;
     for (int i =0; i<2 ; i++ )
      if (CC - LW[i].execution_start_cycle >= 2 )
      {
      }
          
     
      for (int i =0; i<2 ; i++ )
      if (CC - SW[i].execution_start_cycle >= 2)
      {
          
      }
      
      for (int i =0; i<3 ; i++ )
      if (CC - JMP_JALR_RET[i].execution_start_cycle >= 1 )
      {
          
      }
      
      for (int i =0; i<2 ; i++ )
      if (CC - BEQ[i].execution_start_cycle >= 1 )  //CHANGE !!
      {
          result = execute(ADD_SUB_ADDI[i]) ;
      }
      
      for (int i =0; i<3 ; i++ )
      if (CC - ADD_SUB_ADDI[i].execution_start_cycle >= 2 )
      {
          result = execute(ADD_SUB_ADDI[i]) ;
          rob.set_value(ADD_SUB_ADDI[i].rob_indx, result);
          update (ADD_SUB_ADDI[i].rob_indx  ,  result);
      }
      
      for (int i =0; i<1 ; i++ )
      if (CC - NAND[i].execution_start_cycle >= 1)
      {
          result = execute(NAND[i]) ;
          rob.set_value(NAND[i].rob_indx, result);
          update (NAND[i].rob_indx  ,  result);
      }
      for (int i =0; i<2 ; i++ )
      if (CC - MUL[i].execution_start_cycle >= 8)
      {
          result = execute(MUL[i]) ;
          rob.set_value(MUL[i].rob_indx, result);
          update (MUL[i].rob_indx  ,  result);
      }
          
}

 private Float execute(Reservation_Station_Element rtrn )
         {
             Float result= null ;
             if (rtrn.operation == Instruction.ADD || rtrn.operation == Instruction.ADDI)
             {
                 result =  rtrn.Vj + rtrn.Vk;
             
             }
                
             else
             if (rtrn.operation == Instruction.SUB)
             {
             result =  rtrn.Vj + rtrn.Vk ;
             }
             else
             if (rtrn.operation == Instruction.MUL)
             {
             result = rtrn.Vj * rtrn.Vk ;
             }
             else
             if (rtrn.operation == Instruction.NAND)
             {
             result =  (float) ~(Float.floatToIntBits(rtrn.Vj) & Float.floatToIntBits(rtrn.Vk));
             }
             else 
             if (rtrn.operation == Instruction.BEQ)
             {
             result =  rtrn.Vj - rtrn.Vk ;
             }
             
             return result ;
                     
         }
private int get_ready (String type)
{
    switch (type)
        {
        case "LW" : 
        {
            for (int i =  0 ;i< LW.length ; i++)
                if (LW[i].operation != null && LW[i].busy == true )
                   return i ;
            return -1 ;
        }
        
        case "SW" : 
            {
            for (int i =0 ;i< LW.length ; i++)
                if (SW[i].operation != null && SW[i].busy == true )
                   return i ;
            return -1 ;
        }
        
        case "JMP_JALR_RET" :
        {
            for (int i =0 ;i< JMP_JALR_RET.length ; i++)
                if (JMP_JALR_RET[i].operation != null && JMP_JALR_RET[i].busy == true)
                   return i ;
            return -1 ;
        }
        
        case "BEQ" :
        { 
            for (int i =0 ;i< JMP_JALR_RET.length ; i++)
                if (JMP_JALR_RET[i].operation != null && JMP_JALR_RET[i].Vj != null && JMP_JALR_RET[i].Vk != null && JMP_JALR_RET[i].busy == true)
                   return i ;
            return -1 ;
        }
        case "ADD_SUB_ADDI" :
        { for (int i =0 ;i< JMP_JALR_RET.length ; i++)
                if (ADD_SUB_ADDI[i].operation != null && ADD_SUB_ADDI[i].Vj != null && ADD_SUB_ADDI[i].Vk != null && ADD_SUB_ADDI[i].busy == true )
                   return i ;
            return -1 ;
        }
        case "NAND" :  
            { for (int i =0 ;i< NAND.length ; i++)
                if (NAND[i].operation != null && NAND[i].Vj != null && NAND[i].Vk != null && NAND[i].busy == true)
                   return i ;
            return -1 ;
        }
        case "MULT" :
             { for (int i =0 ;i< MUL.length ; i++)
                if (MUL[i].operation != null && MUL[i].Vj != null && MUL[i].Vk != null && MUL[i].busy == true )
                   return i ;
            return -1 ;
        }
        default :
        { 
                return -1 ;
        }
}
}

void update (Integer rob_indx  , float result)
{
    
        
            for (int i=0 ;i<BEQ.length ; i++)
            {
                if (BEQ[i].Qj == rob_indx)
                {
                    BEQ[i].Vj = result ;
                }
                if (BEQ[i].Qk == rob_indx)
                {
                    BEQ[i].Vk= result ;
                }
            }
        
       
       
            for (int i=0 ;i<ADD_SUB_ADDI.length ; i++)
            {
                if (ADD_SUB_ADDI[i].Qj == rob_indx)
                {
                    ADD_SUB_ADDI[i].Vj = result ;
                }
                if (ADD_SUB_ADDI[i].Qk == rob_indx)
                {
                    ADD_SUB_ADDI[i].Vk= result ;
                }
            }
        
         for (int i=0 ;i<NAND.length ; i++)
            {
                if (NAND[i].Qj == rob_indx)
                {
                    NAND[i].Vj = result ;
                }
                if (NAND[i].Qk == rob_indx)
                {
                    NAND[i].Vk= result ;
                }
            }
         for (int i=0 ;i<MUL.length ; i++)
            {
                if (MUL[i].Qj == rob_indx)
                {
                    MUL[i].Vj = result ;
                }
                if (MUL[i].Qk == rob_indx)
                {
                    MUL[i].Vk= result ;
                }
            }
        
}
    @ Override
    public Iterator iterator( ) {
        Iterator<Reservation_Station_Element> it ;
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter Reservation Station Name: ");
        String obj = reader.next(); // Scans the next token of the input as an int.
        reader.close();

        switch (obj)
        {
        case "LW" : {it =  Arrays.asList(LW).iterator();  break;}
        case "SW" : {it = Arrays.asList(SW).iterator();  break;}
        case "JMP_JALR_RET" :{ it = Arrays.asList(JMP_JALR_RET).iterator();  break;}
        case "BEQ" : { it = Arrays.asList(BEQ).iterator();  break;}
        case "ADD_SUB_ADDI" :{ it = Arrays.asList(ADD_SUB_ADDI).iterator();  break;}
        case "NAND" :  {it = Arrays.asList(NAND).iterator();  break;}
        case "MULT" :{ it = Arrays.asList(MUL).iterator();  break;}
        default :
        { it = null ;  break;}
                
        }
        return it ;
    }

    
   
    
}
