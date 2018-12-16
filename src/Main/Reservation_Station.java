
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
int []branch_imm  = new int [2];
int BEQ_counter =0 ;
Reservation_Station_Element []ADD_SUB_ADDI ; 
int ADD_SUB_ADDI_counter =0 ;
Reservation_Station_Element [] NAND; 
int NAND_counter =0 ;
Reservation_Station_Element [] MUL ;
int MUL_counter =0 ;

public static String[] formats= {"LW", "SW", "JMP_JALR_RET", "BEQ", "ADD_SUB_ADDI", "NAND","MUL"};

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

public void add (Instruction inst , ROB rob , int rob_ind , int PC)
{
 int y ; 
 switch (inst.getName())
        {
        case Instruction.LW : 
        {
            
                y = empty_index (LW);
                LW[y].operation =  inst.getName() ;
                LW[y].busy =  true ;
                LW[y].Qj= null ;
                LW[y].Vj= null ;
                LW[y].Qk= null;
                LW[y].PC = PC ;
                LW[y].Vk= null;
                LW[y].rob_indx = rob_ind ;
                LW_counter++ ;
                System.out.println(inst.getName() + " inst added!! ");
           break ;
        }
        
        case Instruction.SW :
        {
            y =empty_index (SW);
           
            
                SW[y].operation =  inst.getName() ;
                SW[y].busy = true ;
                SW[y].Vj= null;
                SW[y].Qj= null ;
                SW[y].Vk= null;
                SW[y].Qk= null;
                SW[y].PC = PC ;
                SW[y].rob_indx = rob_ind ;
                SW_counter++ ;
                System.out.println(inst.getName() + " inst added!! ");
            break ;
        }
        case Instruction.JMP:
        {
            y =empty_index (JMP_JALR_RET);
            
               
                    JMP_JALR_RET[y].operation =  inst.getName() ;
                    JMP_JALR_RET[y].busy =  true ;
                    JMP_JALR_RET_counter ++ ;
                    JMP_JALR_RET[y].Qj = null ;
                    JMP_JALR_RET[y].Qk= null ;
                    JMP_JALR_RET[y].PC = PC ;
                    JMP_JALR_RET[y].Vj= inst.getImm() ;
                    JMP_JALR_RET[y].Vk= null ;
                    JMP_JALR_RET[y].rob_indx = rob_ind ;
                     System.out.println(inst.getName() + " inst added!! ");
                    
                 break ;
                 
        }
        case Instruction.JALR:
        {
            y =empty_index (JMP_JALR_RET);
            
                
                    JMP_JALR_RET[y].operation =  inst.getName() ;
                    JMP_JALR_RET[y].busy =  true ;
                    JMP_JALR_RET[y].Vk= null ;
                    JMP_JALR_RET[y].Qk= null ;
                    JMP_JALR_RET[y].PC = PC ;
                    
                    int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegB(),  JMP_JALR_RET[y].rob_indx);
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        JMP_JALR_RET[y].Vj= RegFile.read(inst.getRegB());
                        JMP_JALR_RET[y].Qj= null ;
                    }
                    else //in ROB 
                    {
                        if( rob.is_ready(rob_indx)) // if rob entry is available not in queue
                        {
                            JMP_JALR_RET[y].Vj= rob.get_value(rob_indx);
                            JMP_JALR_RET[y].Qj= null ;
                        }
                        else 
                        {
                             JMP_JALR_RET[y].Qj = rob_indx ;
                             JMP_JALR_RET[y].Vj= null ;
                        }
                            
                    }
                    JMP_JALR_RET[y].rob_indx = rob_ind ;
                    JMP_JALR_RET_counter++ ;
                    System.out.println(inst.getName() + " inst added!! ");
                    
                    break ; 
        }
        
         case Instruction.RET : 
         {
             y =empty_index (JMP_JALR_RET);
             
               
                    JMP_JALR_RET[y].operation =  inst.getName() ;
                    JMP_JALR_RET[y].busy =  true ;
                    JMP_JALR_RET[y].PC = PC ;
                    
                    int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegA(), JMP_JALR_RET[y].rob_indx);
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        JMP_JALR_RET[y].Vj= RegFile.read(inst.getRegA());
                        JMP_JALR_RET[y].Qj= null ;
                    }
                    else //in ROB 
                    {
                        if( rob.is_ready(rob_indx)) // if rob entry is available not in queue
                        {
                            JMP_JALR_RET[y].Vj= rob.get_value(rob_indx);
                            JMP_JALR_RET[y].Qj= null ;
                        }
                        else 
                        {
                             JMP_JALR_RET[y].Qj = rob_indx ;
                             JMP_JALR_RET[y].Vj= null ;
                        }
                            
                    }
                    
                    JMP_JALR_RET[y].Qk= null ;
                    JMP_JALR_RET[y].Vk=  null ;
                    JMP_JALR_RET[y].rob_indx = rob_ind ;
                    JMP_JALR_RET_counter++ ;
                    System.out.println(inst.getName() + " inst added!! ");
                    
                   
                break ; 
         }
        case Instruction.BEQ : 
        {
            y =empty_index (BEQ);
            
            
                BEQ[y].operation =  inst.getName() ;
                BEQ[y].busy =  true ;
                BEQ[y].PC = PC ;
                branch_imm[y] = (int)inst.getImm() ;
                BEQ[y].rob_indx = rob_ind ;
                 
                   int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegA(), BEQ[y].rob_indx);
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        BEQ[y].Vj= RegFile.read(inst.getRegA());
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
                     
                
                    rob_indx = rob.find_dest(inst.getRegB(), BEQ[y].rob_indx);
                    if (rob_indx == -1 ) //not found in ROB
                    {
                        
                        BEQ[y].Vk= RegFile.read(inst.getRegB());
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
            
             break ;
        }
        case Instruction.ADD:
        {
            y = empty_index (ADD_SUB_ADDI);
            
               
                    ADD_SUB_ADDI[y].operation =  inst.getName() ;
                    ADD_SUB_ADDI[y].busy =  true;
                    ADD_SUB_ADDI[y].rob_indx = rob_ind ;
                    ADD_SUB_ADDI[y].PC = PC ;
                    
                    int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegB(),  ADD_SUB_ADDI[y].rob_indx);
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
                     
                
                    rob_indx = rob.find_dest(inst.getRegC(),  ADD_SUB_ADDI[y].rob_indx);
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
                    
                break ; 
        }
         case Instruction.ADDI:
        {
            y = empty_index (ADD_SUB_ADDI);
            
                
                    ADD_SUB_ADDI[y].operation =  inst.getName() ;
                    ADD_SUB_ADDI[y].busy =  true;
                    ADD_SUB_ADDI[y].rob_indx = rob_ind ;
                    ADD_SUB_ADDI[y].PC = PC ;
                    
                    int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegB(), ADD_SUB_ADDI[y].rob_indx);
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
                
                
                    ADD_SUB_ADDI[y].Vk= inst.getImm();
                
                
                    ADD_SUB_ADDI_counter++ ;
                    System.out.println(inst.getName() + " inst added!! ");
                
              break ;   
        }
        case Instruction.SUB :
        {
            y = empty_index (ADD_SUB_ADDI);
            
               
                    ADD_SUB_ADDI[y].operation =  inst.getName() ;
                    ADD_SUB_ADDI[y].busy =  true;
                    ADD_SUB_ADDI[y].rob_indx = rob_ind ;
                    ADD_SUB_ADDI[y].PC = PC ;
                    
                     int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegB(), ADD_SUB_ADDI[y].rob_indx);
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
                     
                
                    rob_indx = rob.find_dest(inst.getRegC(), ADD_SUB_ADDI[y].rob_indx);
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
                 break ;    
                   
        }
        case Instruction.NAND : 
        {
            y = empty_index (NAND);
            
                
                    NAND[y].operation =  inst.getName() ;
                    NAND[y].busy =  true;
                    NAND[y].rob_indx = rob_ind ;
                    NAND[y].PC = PC ;
                    
                     int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegB(), NAND[y].rob_indx);
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
                     
                
                    rob_indx = rob.find_dest(inst.getRegC(), NAND[y].rob_indx);
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
                    
                    break ;
        }
        case Instruction.MUL : 
          {
            y = empty_index (MUL);
            
                
                    MUL[y].operation =  inst.getName() ;
                    MUL[y].busy =  true;
                    MUL[y].rob_indx = rob_ind ;
                    MUL[y].PC = PC ;
                     int rob_indx;
                    rob_indx = rob.find_dest(inst.getRegB(), MUL[y].rob_indx);
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
                     
                
                    rob_indx = rob.find_dest(inst.getRegC(), MUL[y].rob_indx);
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
                    
                     break ;
        }
        default :
              {        
                System.out.println(inst.getName() + " failed to add ");
                 break ;
              }

        }   
}

 public void remove (String type, ROB rob , int CC , Integer PC , Integer PC2)
{

  int k = get_ready(type); //retrieves an inst with ready operands !!
  if (k != -1 )
  {
     switch (type)
        {
        case "LW" : 
        {
            if (LW[k].PC != PC && LW[k].PC != PC2 || (PC==null && PC2==null))
            {
            System.out.println("a " + LW[k].operation + " is executing  ");
            LW[k].execution_start_cycle = CC;
            }
             
            
        } 
        break ;
        case "SW" : 
        {
             if (SW[k].PC != PC &&  SW[k].PC != PC2 || (PC==null && PC2==null))
            {
            System.out.println("a " + SW[k].operation + " is executing  ");
            SW[k].execution_start_cycle = CC; //stores the cycle it started execution in
            }
             
            
        }
         break ;
        case "JMP_JALR_RET" :
            {
            if (JMP_JALR_RET[k].PC != PC && JMP_JALR_RET[k].PC != PC2 || (PC==null && PC2==null))
            {
            System.out.println("a " + JMP_JALR_RET[k].operation + " is executing  ");
            JMP_JALR_RET[k].execution_start_cycle = CC;
            }
             
        }
        break ;
        case "BEQ" :  
        {
             if (BEQ[k].PC != PC && BEQ[k].PC != PC2 || (PC==null && PC2==null))
            {
            System.out.println("a " + BEQ[k].operation + " is executing  ");
            BEQ[k].execution_start_cycle = CC;
            }
             
             
           
        }
         break ;
        case "ADD_SUB_ADDI" :
            {
            if (ADD_SUB_ADDI[k].PC != PC && ADD_SUB_ADDI[k].PC != PC2 || (PC==null && PC2==null))
            {
            ADD_SUB_ADDI[k].execution_start_cycle = CC;
            System.out.println("a " + ADD_SUB_ADDI[k].operation + " is executing  ");
            }
             
            
             
        }
        break ;
        case "NAND" :  
        {
            if (NAND[k].PC != PC && NAND[k].PC != PC2 || (PC==null && PC2==null))
            {
            NAND[k].execution_start_cycle = CC ;
            System.out.println("a " + NAND[k].operation + " is executing  ");
            }
        }
         break ;
        case "MUL" : 
        {
             if (MUL[k].PC != PC && MUL[k].PC != PC2 || (PC==null && PC2==null))
            {
            MUL[k].execution_start_cycle = CC ;
            System.out.println("a " + MUL[k].operation + " is executing  ");
            }
              
         break ;
        
     }   
     }         
  }
  else
  {
      System.out.println ("No Ready " +type + " Instructions !!");
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
 
public boolean check (Instruction instr)
{
    int y ; 
    switch (instr.getName())
    {
        case Instruction.ADD  :
        { y = empty_index (ADD_SUB_ADDI);
          if (y == -1)
              return false ;
          else
              return true;
        }
        case Instruction.SUB :
        { y = empty_index (ADD_SUB_ADDI);
          if (y == -1)
              return false ;
          else
              return true;
        }
        
        case Instruction.ADDI :
        { y = empty_index (ADD_SUB_ADDI);
          if (y == -1)
              return false ;
          else
              return true;
        }
         case Instruction.BEQ :
        { y = empty_index (BEQ);
          if (y == -1)
              return false ;
          else
              return true;
        }
         case Instruction.NAND :
        { y = empty_index (NAND);
          if (y == -1)
              return false ;
          else
              return true;
        }
         case Instruction.MUL :
        { y = empty_index (MUL);
          if (y == -1)
              return false ;
          else
              return true;
        }
         case Instruction.JMP :
        { y = empty_index (JMP_JALR_RET);
          if (y == -1)
              return false ;
          else
              return true;
        }
        
        case Instruction.JALR :
        { y = empty_index (JMP_JALR_RET);
          if (y == -1)
              return false ;
          else
              return true;
        }
        
        case Instruction.RET :
        { y = empty_index (JMP_JALR_RET);
          if (y == -1)
              return false ;
          else
              return true;
        }
        
        case Instruction.LW :
        { y = empty_index (LW);
          if (y == -1)
              return false ;
          else
              return true;
        }
        
        case Instruction.SW :
        { y = empty_index (SW);
          if (y == -1)
              return false ;
          else
              return true;
        }
        default :
        return false ;
    }
    
}

public void finish_execution (int CC , ROB rob)
{
     Integer result ;
     for (int i =0; i<2 ; i++ )
         if (LW[i].execution_start_cycle != null)
     {
      if (CC - LW[i].execution_start_cycle >= 2 )
      {
            LW[i].Qj =null;
            LW[i].Qk = null ;
            LW[i].Vj = null ;
            LW[i].Vk = null ;
            LW[i].busy = false  ;
            LW[i].execution_start_cycle = null ;
            LW[i].PC = null; 
            LW[i].operation = null ;
            LW[i].rob_indx = null ;
      }
     }
          
     
      for (int i =0; i<2 ; i++ )
       if (SW[i].execution_start_cycle != null)
     {
      if (CC - SW[i].execution_start_cycle >= 2)
      {
            SW[i].Qj =null;
            SW[i].Qk = null ;
            SW[i].Vj = null ;
            SW[i].Vk = null ;
            SW[i].busy = false  ;
            SW[i].operation = null ;
            SW[i].rob_indx = null ;
            SW[i].execution_start_cycle = null ;
            SW[i].PC = null; 
          
      }
     }
      
      for (int i =0; i<3 ; i++ )
           if (JMP_JALR_RET[i].execution_start_cycle != null)
     {
      if (CC - JMP_JALR_RET[i].execution_start_cycle >= 1 )
      {
          if (JMP_JALR_RET[i].operation == Instruction.JMP)
          {
              result = JMP_JALR_RET[i].PC + JMP_JALR_RET[i].Vj ;
              rob.set_value(JMP_JALR_RET[i].rob_indx, result , null); //write pc+imm to rob with dest pc 
          }
          else
          if (JMP_JALR_RET[i].operation == Instruction.JALR)
          {
              result = JMP_JALR_RET[i].PC + 1 ;
              rob.set_value(JMP_JALR_RET[i].rob_indx, result , JMP_JALR_RET[i].Vj); //write pc+imm to rob with dest pc 
              update (NAND[i].rob_indx  ,  result); //update reservation station
          } 
          else
              if (JMP_JALR_RET[i].operation == Instruction.RET)
          {
              result =  JMP_JALR_RET[i].Vj ;
              rob.set_value(JMP_JALR_RET[i].rob_indx, result , null); //write pc+imm to rob with dest pc 
          }
          
            JMP_JALR_RET[i].Qj =null;
            JMP_JALR_RET[i].Qk = null ;
            JMP_JALR_RET[i].Vj = null ;
            JMP_JALR_RET[i].Vk = null ;
            JMP_JALR_RET[i].busy = false  ;
            JMP_JALR_RET[i].operation = null ;
            JMP_JALR_RET[i].execution_start_cycle = null ;
            JMP_JALR_RET[i].PC = null; 
          
      }
     }
      
      for (int i =0; i<2 ; i++ )
      if (BEQ[i].execution_start_cycle != null)
     { 
      if (CC - BEQ[i].execution_start_cycle >= 1 ) 
      {
          result = execute(BEQ[i]) ;
          if (result == 0 ) //branch taken 
          {
              if (branch_imm[i] > 0 )
              {
               result = branch_imm[i] + BEQ[i].PC ; //store in branch pc pc+imm while adding !!!
               rob.set_value(BEQ[i].rob_indx, result , null);
              }
              else 
              {
                  result =  null ;
                  rob.set_value(BEQ[i].rob_indx, result , null);
              }
          }
          else
          {
              if (branch_imm[i] < 0 )
              {
               result = branch_imm[i] + BEQ[i].PC ; //store in branch pc pc+imm while adding !!!
               rob.set_value(BEQ[i].rob_indx, result , null);
              }
              else 
              {
                  result =  null ;
                  rob.set_value(BEQ[i].rob_indx, result , null);
              }
          }
              
            BEQ[i].Qj =null;
            BEQ[i].Qk = null ;
            BEQ[i].Vj = null ;
            BEQ[i].Vk = null ;
            BEQ[i].busy = false  ;
            BEQ[i].operation = null ;
            BEQ[i].rob_indx = null ;
            BEQ[i].execution_start_cycle = null ;
            BEQ[i].PC = null; 
      }
     }
      for (int i =0; i<3 ; i++ )
       if (ADD_SUB_ADDI[i].execution_start_cycle != null)
     {
      if (CC - ADD_SUB_ADDI[i].execution_start_cycle >= 2 )
      {
          System.out.println(ADD_SUB_ADDI[i].operation + "Is done Executing");
          result = execute(ADD_SUB_ADDI[i]) ;
          rob.set_value(ADD_SUB_ADDI[i].rob_indx, result , null);
          update (ADD_SUB_ADDI[i].rob_indx  ,  result);
          ADD_SUB_ADDI[i].Qj =null;
          ADD_SUB_ADDI[i].Qk = null ;
          ADD_SUB_ADDI[i].Vj = null ;
          ADD_SUB_ADDI[i].Vk = null ;
          ADD_SUB_ADDI[i].busy = false  ;
          ADD_SUB_ADDI[i].operation = null ;
          ADD_SUB_ADDI[i].rob_indx = null ;
          ADD_SUB_ADDI[i].execution_start_cycle = null ;
          ADD_SUB_ADDI[i].PC = null; 
             
      }
     }
      
      for (int i =0; i<1 ; i++ )
          if (NAND[i].execution_start_cycle != null)
     {
      if (CC - NAND[i].execution_start_cycle >= 1)
      {
          System.out.println(NAND[i].operation + "Is done Executing");
          result = execute(NAND[i]) ;
          rob.set_value(NAND[i].rob_indx, result , null);
          update (NAND[i].rob_indx  ,  result);
            NAND[i].Qj =null;
            NAND[i].Qk = null ;
            NAND[i].Vj = null ;
            NAND[i].Vk = null ;
            NAND[i].busy = false  ;
            NAND[i].operation = null ;
            NAND[i].rob_indx = null ;
            NAND[i].execution_start_cycle = null ;
            NAND[i].PC = null;
      }
     }
      for (int i =0; i<2 ; i++ )
           if (MUL[i].execution_start_cycle != null)
     {
      if (CC - MUL[i].execution_start_cycle >= 8)
      {
          System.out.println(MUL[i].operation + "Is done Executing");
          result = execute(MUL[i]) ;
          rob.set_value(MUL[i].rob_indx, result , null);
          update (MUL[i].rob_indx  ,  result);
            MUL[i].Qj =null;
            MUL[i].Qk = null ;
            MUL[i].Vj = null ;
            MUL[i].Vk = null ;
            MUL[i].busy = false  ;
            MUL[i].operation = null ;
            MUL[i].rob_indx = null ;
            MUL[i].execution_start_cycle = null ;
            MUL[i].PC = null;
      }
     }
}

 private Integer execute(Reservation_Station_Element rtrn )
         {
             Integer result= null ;
             if (rtrn.operation == Instruction.ADD || rtrn.operation == Instruction.ADDI)
             {
                 result =  rtrn.Vj + rtrn.Vk;
                 
             }
                
             else
             if (rtrn.operation == Instruction.SUB)
             {
             result =  rtrn.Vj - rtrn.Vk ;
             }
             else
             if (rtrn.operation == Instruction.MUL)
             {
             result = rtrn.Vj * rtrn.Vk ;
             }
             else
             if (rtrn.operation == Instruction.NAND)
             {
             result =  ~(rtrn.Vj & rtrn.Vk);
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
                if (LW[i].operation != null && LW[i].busy == true &&   LW[i].execution_start_cycle == null) //always ready
                   return i ;
            return -1 ;
        }
        
        case "SW" : 
            {
            for (int i =0 ;i< LW.length ; i++)
                if (SW[i].operation != null && SW[i].busy == true &&   SW[i].execution_start_cycle == null) //always ready 
                   return i ;
            return -1 ;
        }
        
        case "JMP_JALR_RET" :
        {
            for (int i =0 ;i< JMP_JALR_RET.length ; i++)
                if (JMP_JALR_RET[i].operation != null && JMP_JALR_RET[i].busy == true && JMP_JALR_RET[i].Vj != null &&   JMP_JALR_RET[i].execution_start_cycle == null)
                   return i ;
            return -1 ;
        }
        
        case "BEQ" :
        { 
            for (int i =0 ;i< BEQ.length ; i++)
                if (BEQ[i].operation != null && BEQ[i].Vj != null && BEQ[i].Vk != null && BEQ[i].busy == true &&   BEQ[i].execution_start_cycle == null)
                   return i ;
            return -1 ;
        }
        case "ADD_SUB_ADDI" :
        { for (int i =0 ;i< JMP_JALR_RET.length ; i++)
                if (ADD_SUB_ADDI[i].operation != null && ADD_SUB_ADDI[i].Vj != null && ADD_SUB_ADDI[i].Vk != null && ADD_SUB_ADDI[i].busy == true &&   ADD_SUB_ADDI[i].execution_start_cycle == null)
                   return i ;
            return -1 ;
        }
        case "NAND" :  
        { for (int i =0 ;i< NAND.length ; i++)
                if (NAND[i].operation != null && NAND[i].Vj != null && NAND[i].Vk != null && NAND[i].busy == true &&    NAND[i].execution_start_cycle == null)
                   return i ;
            return -1 ;
        }
        case "MUL" :
             { for (int i =0 ;i< MUL.length ; i++)
                if (MUL[i].operation != null && MUL[i].Vj != null && MUL[i].Vk != null && MUL[i].busy == true &&   MUL[i].execution_start_cycle == null)
                   return i ;
            return -1 ;
        }
        default :
        { 
                return -1 ;
        }
}
}

    public int getNumExecutedInstructions() {
        return (LW_counter + SW_counter + JMP_JALR_RET_counter +
                BEQ_counter + ADD_SUB_ADDI_counter + NAND_counter + MUL_counter);
    }

    public int getNumBranchInstrs() {
        return BEQ_counter;
    }


void update (Integer rob_indx  , int result )
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
            
            for (int i=0 ;i<JMP_JALR_RET.length ; i++)
            {
                if (JMP_JALR_RET[i].Qj == rob_indx)
                {
                    JMP_JALR_RET[i].Vj = result ;
                }
                if (JMP_JALR_RET[i].Qk == rob_indx)
                {
                    JMP_JALR_RET[i].Vk= result ;
                }
            }
        
       
       
            for (int i=0 ;i<ADD_SUB_ADDI.length ; i++)
            {
                if (ADD_SUB_ADDI[i].Qj == rob_indx)
                {
                    ADD_SUB_ADDI[i].Vj = result ;
                    System.out.print("Updating Result!!!!!!");
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
    @Override
    public Iterator iterator( ) {
        Iterator<Reservation_Station_Element> it ;
        String obj ;
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Reservation Station Name"); 
 	obj = in.nextLine(); 
        //String obj = "ADD_SUB_ADDI" ;
        switch (obj)
        {
        case "LW" : {it =  Arrays.asList(LW).iterator();  break;}
        case "SW" : {it = Arrays.asList(SW).iterator();  break;}
        case "JMP_JALR_RET" :{ it = Arrays.asList(JMP_JALR_RET).iterator();  break;}
        case "BEQ" : { it = Arrays.asList(BEQ).iterator();  break;}
        case "ADD_SUB_ADDI" :{ it = Arrays.asList(ADD_SUB_ADDI).iterator();  break;}
        case "NAND" :  {it = Arrays.asList(NAND).iterator();  break;}
        case "MUL" :{ it = Arrays.asList(MUL).iterator();  break;}
        default :
        { it = null ;  break;}
                
        }
        return it ;
    }

    
   
    
}
