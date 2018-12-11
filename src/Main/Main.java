package Main;


import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static int CC = 1;

    public static void main(String[] args){
        updateCCEverySec();
        Controller controller = new Controller();
        controller.run();
        System.exit(0);
    }


    private static void updateCCEverySec() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                CC++;
            }
        }, 1000, 1000);
    }
}
