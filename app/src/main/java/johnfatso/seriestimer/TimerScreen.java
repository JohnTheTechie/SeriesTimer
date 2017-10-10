package johnfatso.seriestimer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class TimerScreen extends AppCompatActivity {

   /* private final String TIMER_ITEM_STORAGE="TIMER_ITEM_STORAGE";
    private final String TIMER_ITEM_COMPLETE_STORAGE="TIMER_ITEM_COMPLETE_STORAGE";
    //private final String TIMER_NAME="TIMER_NAME";
    private final String TIMER_CYCLE_COUNT="TIMER_CYCLE_COUNT";
    private final String TIMER_CURRENT_CYCLE="TIMER_CURRENT_CYCLE";
    private final String TIMER_CURRENT_TIMER="TIMER_CURRENT_TIMER";
    private final String TIMER_REMAINING_TIME="TIMER_REMAINING_TIME";
    private final String TIMER_IS_RUNNING="TIMER_IS_RUNNING";*/

    private int cycleCount;                                                                         //the total number of cycles
    private ArrayList<Integer> secondsArrayList;                                                    //an array list to store the timer items in seconds
    private ArrayList<Integer> completeCycleDescription;

    private int currentCycle;                                                                       //the cycle currently being run
    private int currentTimer;
    private int remainingTimeInSeconds;                                                             //the remaining time in seconds
    private boolean isRunning;                                                                      //to store if the timer is running or not
    private boolean runFlag;
    private Handler handler;

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
        completeCycleDescription=new ArrayList<>();

        //this block would be activated if the activity is starting back after onStop or other callbacks
        if(savedInstanceState!=null){
            /*cycleCount=savedInstanceState.getInt(TIMER_CYCLE_COUNT);
            currentCycle=savedInstanceState.getInt(TIMER_CURRENT_CYCLE);
            currentTimer=savedInstanceState.getInt(TIMER_CURRENT_TIMER);
            remainingTimeInSeconds=savedInstanceState.getInt(TIMER_REMAINING_TIME);
            isRunning=savedInstanceState.getBoolean(TIMER_IS_RUNNING);
            intListToArrayList(savedInstanceState.getIntArray(TIMER_ITEM_STORAGE),secondsArrayList);
            intListToArrayList(savedInstanceState.getIntArray(TIMER_ITEM_COMPLETE_STORAGE),completeCycleDescription);*/
            //runFlag

        }
        //this block should be activated when the activity is called using an intent
        else {
            Intent intent=getIntent();
            cycleCount=intent.getIntExtra(DatabaseHelper.DB_CYCLE_COUNT,0);
            intListToArrayList(intent.getIntArrayExtra(DatabaseHelper.DB_CYCLE_DESCRIPTION),secondsArrayList);
            createCompleteTimerDescription(cycleCount,secondsArrayList);
            currentCycle=1;
            remainingTimeInSeconds=secondsArrayList.get(0);
            isRunning=true;

            Intent serviceIntent=new Intent(this, TimerService.class);
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
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        /*savedInstanceState.putInt(TIMER_CURRENT_CYCLE,currentCycle);
        savedInstanceState.putInt(TIMER_CURRENT_TIMER,currentTimer);
        savedInstanceState.putInt(TIMER_CYCLE_COUNT,cycleCount);
        savedInstanceState.putInt(TIMER_REMAINING_TIME,remainingTimeInSeconds);
        savedInstanceState.putBoolean(TIMER_IS_RUNNING,isRunning);
        savedInstanceState.putIntArray(TIMER_ITEM_STORAGE,arrayListToIntList(secondsArrayList));
        savedInstanceState.putIntArray(TIMER_ITEM_COMPLETE_STORAGE,arrayListToIntList(completeCycleDescription));*/
        //savedInstanceState.put
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
        isRunning=false;
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
        isRunning=true;
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
                completeCycleDescription.add(integer);
            }
        }
    }




}
