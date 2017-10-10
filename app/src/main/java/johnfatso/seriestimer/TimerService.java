package johnfatso.seriestimer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TimerService extends Service {

    private final IBinder binder=new LocalBinder();
    private boolean isStarted=false;
    private int cycleCount;
    private ArrayList<Integer> secondsList;

    private Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage){

        }
    };




    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Toast.makeText(this, "timer begun", Toast.LENGTH_SHORT).show();
        if(!isStarted){
            cycleCount=intent.getIntExtra(DatabaseHelper.DB_CYCLE_COUNT,1);
            secondsList=intent.getIntegerArrayListExtra(DatabaseHelper.DB_CYCLE_DESCRIPTION);
        }
        return START_STICKY;
    }

    public void onPauseRequest(){
        Log.v("JJT","Service pause triggered");
    }

    public void onPlayRequest(){
        Log.v("JJT","Service play triggered");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    public class LocalBinder extends Binder{
        TimerService getService(){
            return TimerService.this;
        }
    }



}
