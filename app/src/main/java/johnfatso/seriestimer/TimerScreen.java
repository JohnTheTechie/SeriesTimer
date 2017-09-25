package johnfatso.seriestimer;

import android.content.Intent;
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

    private final String TIMER_ITEM_STORAGE="TIMER_ITEM_STORAGE";
    //private final String TIMER_NAME="TIMER_NAME";
    private final String TIMER_CYCLE_COUNT="TIMER_CYCLE_COUNT";
    private final String TIMER_CURRENT_CYCLE="TIMER_CURRENT_CYCLE";
    private final String TIMER_CURRENT_TIMER="TIMER_CURRENT_TIMER";
    private final String TIMER_REMAINING_TIME="TIMER_REMAINING_TIME";
    private final String TIMER_IS_RUNNING="TIMER_IS_RUNNING";

    private int cycleCount;                                                                         //the total number of cycles
    private ArrayList<Integer> secondsArrayList;                                                    //an array list to store the timer items in seconds

    private int currentCycle;                                                                       //the cycle currently being run
    private int currentTimer;
    private int remainingTimeInSeconds;                                                             //the remaining time in seconds
    private boolean isRunning;                                                                      //to store if the timer is running or not


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_screen);
        secondsArrayList=new ArrayList<>();

        //this block would be activated if the activity is starting back after onStop or other callbacks
        if(savedInstanceState!=null){
            cycleCount=savedInstanceState.getInt(TIMER_CYCLE_COUNT);
            currentCycle=savedInstanceState.getInt(TIMER_CURRENT_CYCLE);
            currentTimer=savedInstanceState.getInt(TIMER_CURRENT_TIMER);
            remainingTimeInSeconds=savedInstanceState.getInt(TIMER_REMAINING_TIME);
            isRunning=savedInstanceState.getBoolean(TIMER_IS_RUNNING);
            secondsArrayList(savedInstanceState.getIntArray(TIMER_ITEM_STORAGE));
        }
        //this block should be activated when the activity is called using an intent
        else {
            Intent intent=getIntent();
            cycleCount=intent.getIntExtra(DatabaseHelper.DB_CYCLE_COUNT,0);
            secondsArrayList(intent.getIntArrayExtra(DatabaseHelper.DB_CYCLE_DESCRIPTION));
            currentCycle=1;
            currentCycle=1;
            remainingTimeInSeconds=secondsArrayList.get(0);
            isRunning=true;

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
        savedInstanceState.putInt(TIMER_CURRENT_CYCLE,currentCycle);
        savedInstanceState.putInt(TIMER_CURRENT_TIMER,currentTimer);
        savedInstanceState.putInt(TIMER_CYCLE_COUNT,cycleCount);
        savedInstanceState.putInt(TIMER_REMAINING_TIME,remainingTimeInSeconds);
        savedInstanceState.putBoolean(TIMER_IS_RUNNING,isRunning);
        savedInstanceState.putIntArray(TIMER_ITEM_STORAGE,secondsArrayList());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    public void onPauseButtonPressed(View view){
        ((ImageView)(findViewById(R.id.pauseButton))).setVisibility(View.INVISIBLE);
        ((ImageView)(findViewById(R.id.playButton))).setVisibility(View.VISIBLE);
    }

    public void onPlayButtonPressed(View view){
        ((ImageView)(findViewById(R.id.pauseButton))).setVisibility(View.VISIBLE);
        ((ImageView)(findViewById(R.id.playButton))).setVisibility(View.INVISIBLE);
    }

    private int[] secondsArrayList(){
        int[] list=new int[secondsArrayList.size()];
        for(Integer integer:secondsArrayList){
            list[secondsArrayList.indexOf(integer)]=integer;
        }
        return list;
    }

    private void secondsArrayList(int[] list){
        for(int integer: list){
            secondsArrayList.add(integer);
        }
    }
}
