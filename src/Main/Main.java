package Main;


import MemoryAndBuffer.LoadBuffer;
import MemoryAndBuffer.RegFile;

import java.util.Timer;
import java.util.TimerTask;

public class Main{
    public static int CC = 1;
    private static ClkInterface clkInterface;
    private static Controller controller;
    private static boolean loadEx;
    public static ClkLoadHandler clkH;

    public static void main(String[] args){
        clkH = new ClkLoadHandler();
        controller = new Controller();
        clkInterface = (ClkInterface) controller;
        updateCCEverySec();
    }


    private static void updateCCEverySec() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                    if(controller.rob.isEmpty() && controller.instrQueue.isEmpty()) {
                        timer.cancel();
                        System.exit(0);
                    }
                    else {
                        clkInterface.didUpdate(CC);
                        CC++;
                        RegFile.print();
                    }
                }
            }
        , 1000, 1000);
    }

    

    interface ClkInterface {
        void didUpdate(int CC);
    }
    
    static class ClkLoadHandler implements LoadBuffer.NextCCInterface{
        @Override
    public void nextCycle(boolean loadEx) {
        Main.CC++;
        Main.loadEx = loadEx;
        clkInterface.didUpdate(Main.CC);
    }
    }
}
