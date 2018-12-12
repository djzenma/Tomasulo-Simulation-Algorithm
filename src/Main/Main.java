package Main;


import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static int CC = 1;
    private static ClkInterface clkInterface;
    private static Controller controller;

    public static void main(String[] args){
        controller = new Controller();
        clkInterface = (ClkInterface) controller;
        updateCCEverySec();
    }


    private static void updateCCEverySec() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                    CC++;
                    clkInterface.didUpdate();
                }
            }
        , 1000, 1000);
    }
    
    interface ClkInterface {
        void didUpdate();
    }
}
