package johnfatso.seriestimer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;

public class TimerScreen extends AppCompatActivity {

    private int cycleCount;                                                                         //the total number of cycles
    private ArrayList<Integer> secondsArrayList;                                                    //an array list to store the timer items in seconds

    private String timerName;
    private boolean isBound;
    private TimerService timerService;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimerService.LocalBinder binder=(TimerService.LocalBinder) service;
            isBound=true;
            timerService=binder.getService();
            Log.v("JJT","binding done");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound=false;
            Log.v("JJT","binding undone");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_screen);
        secondsArrayList=new ArrayList<>();

        //this block would be activated if the activity is starting back after onStop or other callbacks
        if(savedInstanceState!=null){
            timerName=savedInstanceState.getString(DatabaseHelper.DB_NAME);
            cycleCount=savedInstanceState.getInt(DatabaseHelper.DB_CYCLE_COUNT);
            secondsArrayList=savedInstanceState.getIntegerArrayList(DatabaseHelper.DB_CYCLE_DESCRIPTION);
            isBound=savedInstanceState.getBoolean("WAS_BOUND");

            Intent serviceIntent=new Intent(this, TimerService.class);
            serviceIntent.putExtra(DatabaseHelper.DB_CYCLE_COUNT, cycleCount);
            serviceIntent.putExtra(DatabaseHelper.DB_NAME, timerName);
            serviceIntent.putIntegerArrayListExtra(DatabaseHelper.DB_CYCLE_DESCRIPTION, secondsArrayList);
            if(!isBound) startService(serviceIntent);
            bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        }
        //this block should be activated when the activity is called using an intent
        else {
            Intent intent=getIntent();
            timerName=intent.getStringExtra(DatabaseHelper.DB_NAME);
            cycleCount=intent.getIntExtra(DatabaseHelper.DB_CYCLE_COUNT,0);
            intListToArrayList(intent.getIntArrayExtra(DatabaseHelper.DB_CYCLE_DESCRIPTION),secondsArrayList);

            Intent serviceIntent=new Intent(this, TimerService.class);
            serviceIntent.putExtra(DatabaseHelper.DB_CYCLE_COUNT, cycleCount);
            serviceIntent.putExtra(DatabaseHelper.DB_NAME, timerName);
            serviceIntent.putIntegerArrayListExtra(DatabaseHelper.DB_CYCLE_DESCRIPTION, secondsArrayList);
            startService(serviceIntent);
            bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        }

    }


    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        if(isBound) unbindService(serviceConnection);
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(DatabaseHelper.DB_NAME, timerName);
        savedInstanceState.putInt(DatabaseHelper.DB_CYCLE_COUNT, cycleCount);
        savedInstanceState.putIntegerArrayList(DatabaseHelper.DB_CYCLE_DESCRIPTION, secondsArrayList);
        savedInstanceState.putBoolean("WAS_BOUND",isBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    /**
     * function to be called when the Pause button is pressed
     * The pause button is set invisible
     * the play button shall be set visible
     * @param view
     */

    public void onPauseButtonPressed(View view){
        ((ImageView)(findViewById(R.id.pauseButton))).setVisibility(View.INVISIBLE);
        ((ImageView)(findViewById(R.id.playButton))).setVisibility(View.VISIBLE);
        timerService.onPauseRequest();
    }

    /**
     * function shall be called when the Play button is pressed
     * Play button shall be set invisible
     * Pause button shall be set visible
     * @param view
     */
    public void onPlayButtonPressed(View view){
        ((ImageView)(findViewById(R.id.pauseButton))).setVisibility(View.VISIBLE);
        ((ImageView)(findViewById(R.id.playButton))).setVisibility(View.INVISIBLE);
        timerService.onPlayRequest();
    }

    /**
     * the function  consolidates the timer seconds into an int array and returns the same
     * @return int list of timer seconds
     */
    private int[] arrayListToIntList(ArrayList<Integer> arrayList){
        int[] list=new int[arrayList.size()];
        for(Integer integer:arrayList){
            list[arrayList.indexOf(integer)]=integer;
        }
        return list;
    }

    /**
     * reads the int list of seconds and stores the same into an array list
     * @param list int array
     */
    private void intListToArrayList(int[] list, ArrayList<Integer> arrayList){
        for(int integer: list){
            arrayList.add(integer);
        }
    }

    private void createCompleteTimerDescription(int cycleCount, ArrayList<Integer> arrayList){
        for (int i=0;i<cycleCount;i++){
            for(Integer integer: arrayList){
                //completeCycleDescription.add(integer);
            }
        }
    }

}
