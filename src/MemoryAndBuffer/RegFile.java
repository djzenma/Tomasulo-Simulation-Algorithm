package MemoryAndBuffer;

public class RegFile {
    private static int [] rf;
    private static final int CAPACITY = 7;

    static 
    {
        rf = new int[8];
        for (int i=0; i<rf.length; i++) 
            rf[i] = 0;
    }
            
        
    

    public static boolean write(int regNum, int data  ) {
        if(regNum == 0 || regNum > CAPACITY || data > 65535) // maximum number 16 bits
            return false;

        rf[regNum] = data;
        return true;
    }

    public static int read(int regNum) {
        if(regNum > CAPACITY || regNum < 0)
            System.out.println("Register number not in range!");
        return rf[regNum];
    }

    public static void print() {
        for (int i: rf) {
            System.out.println(i);
        }
    }
    
     
}
