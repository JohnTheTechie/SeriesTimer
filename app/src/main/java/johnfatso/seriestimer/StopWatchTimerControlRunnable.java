package johnfatso.seriestimer;

import android.os.CountDownTimer;
import android.widget.Toast;

/**
 * Created by user on 07-10-2017.
 */

public class StopWatchTimerControlRunnable implements Runnable {

    private TimerService timerService;
    private int seconds;
    private CountDownTimer countDownTimer;

    StopWatchTimerControlRunnable(final TimerService service, int seconds){
        timerService=service;
        countDownTimer=new CountDownTimer(seconds*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Toast.makeText(service.getBaseContext(), ""+millisUntilFinished ,Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onFinish() {
                Toast.makeText(service.getBaseContext(), "completed" ,Toast.LENGTH_SHORT ).show();
            }
        };
    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
         countDownTimer.start();
    }
}
