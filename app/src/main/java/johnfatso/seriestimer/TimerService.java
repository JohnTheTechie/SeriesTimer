package johnfatso.seriestimer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class TimerService extends Service {

    private final IBinder binder=new LocalBinder();

    private Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage){

        }
    };




    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Toast.makeText(this, "timer begun", Toast.LENGTH_SHORT).show();
        //Todo:implement code to start the timer
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    String test(){
        return "###########################success";
    }

    public class LocalBinder extends Binder{
        TimerService getService(){
            return TimerService.this;
        }
    }



}
