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
    private final String TIMER_NAME="TIMER_NAME";
    private final String TIMER_CYCLE_COUNT="TIMER_CYCLE_COUNT";
    private final String TIMER_CURRENT_CYCLE="TIMER_CURRENT_CYCLE";
    private final String TIMER_IS_RUNNING="TIMER_IS_RUNNING";

    private int cycleCount;                                                                         //the total number of cycles
    private ArrayList<Integer> secondsArrayList;                                                    //an array list to store the timer items in seconds

    private int currentCycle;                                                                       //the cycle currently being run
    private int remainingTimeInSeconds;                                                             //the remaining time in seconds
    private boolean isRunning;                                                                      //to store if the timer is running or not


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_screen);

        //this block would be activated if the activity is starting back after onStop or other callbacks
        if(savedInstanceState!=null){
            cycleCount=savedInstanceState.getInt(TIMER_CYCLE_COUNT);
            ((TextView)findViewById(R.id.cycleCountText)).setText(""+cycleCount);

            byte[] byteArray=savedInstanceState.getByteArray(TIMER_ITEM_STORAGE);
            int[] intArray=ConversionManager.convertByteArrayToIntArray(byteArray);//issue is there in this function
            for(int milliseconds : intArray){

            }

        }
        //this block should be activated when the activity is called using an intent
        else {
            Intent intent=getIntent();
            ((TextView)findViewById(R.id.cycleCountText)).setText(String.format(Locale.getDefault(),"%d",intent.getIntExtra(DatabaseHelper.DB_CYCLE_COUNT,0)));
            cycleCount=intent.getIntExtra(DatabaseHelper.DB_CYCLE_COUNT,0);

            byte[] byteArray=intent.getByteArrayExtra(DatabaseHelper.DB_CYCLE_DESCRIPTION);
            int[] intArray=ConversionManager.convertByteArrayToIntArray(byteArray);
            for(int milliseconds : intArray){

            }

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
}
