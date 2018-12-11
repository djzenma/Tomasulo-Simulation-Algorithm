package MemoryAndBuffer;

public class RegFile {
    private static float [] rf;
    private static final int CAPACITY = 7;

    static 
    {
        rf = new float[8];
        for (int i=0; i<rf.length; i++) 
            rf[i] = (float)0;
    }
            
        
    

    public static boolean write(int regNum, float data  ) {
        if(regNum == 0 || regNum > CAPACITY || data > (float)65535) // maximum number 16 bits
            return false;

        rf[regNum] = data;
        return true;
    }

    public static float read(int regNum) {
        if(regNum > CAPACITY || regNum < 0)
            System.out.println("Register number not in range!");
        return rf[regNum];
    }
    
     
}
