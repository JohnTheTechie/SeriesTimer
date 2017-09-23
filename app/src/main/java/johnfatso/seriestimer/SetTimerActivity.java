package johnfatso.seriestimer;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Locale;

import android.util.Log;
import android.widget.TextView;

public class SetTimerActivity extends AppCompatActivity implements TimeItemFragment.OnFragmentInteractionListener{

    /**
     *declare the items needed for tracking the fragments added to the view
     */
    private final String TIMER_ITEM_STORAGE="TIMER_ITEM_STORAGE";
    private final String TIMER_NAME="TIMER_NAME";
    private final String TIMER_CYCLE_COUNT="TIMER_CYCLE_COUNT";

    int timerCountTracker;
    int cycleCount;
    ArrayList<TimeItemFragment> fragmentArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("JJT","create");
        this.timerCountTracker=0;
        setContentView(R.layout.activity_set_timer);
        //this block would be activated if the activity is starting back after onStop or other callbacks
        if(savedInstanceState!=null){
            ((EditText)findViewById(R.id.cycleName)).setText(savedInstanceState.getString(TIMER_NAME));
            cycleCount=savedInstanceState.getInt(TIMER_CYCLE_COUNT);
            ((TextView)findViewById(R.id.cycleCountText)).setText(cycleCount);

            //try{
                byte[] byteArray=savedInstanceState.getByteArray(TIMER_ITEM_STORAGE);
                Log.v("JJT","byte array recieved");
                int[] intArray=ConversionManager.convertByteArrayToIntArray(byteArray);//issue is there in this function
                Log.v("JJT","intArrayProcess success");
                for(int milliseconds : intArray){
                    Log.v("JJT","looping");
                    createAndPushTimeItemToLinearLayout(milliseconds);
                }
                Log.v("JJT","bloc ok");/*
            }catch (Exception e){
                Log.v("JJT","excepted in byteArray processing");
            }
*/
        }
        //this block should be activated when the activity is called using an intent
        else {
            Intent intent=getIntent();
            ((EditText)findViewById(R.id.cycleName)).setText(intent.getStringExtra(DatabaseHelper.DB_CYCLE_NAME));
            ((TextView)findViewById(R.id.cycleCountText)).setText(String.format(Locale.getDefault(),"%d",intent.getIntExtra(DatabaseHelper.DB_CYCLE_COUNT,0)));
            cycleCount=intent.getIntExtra(DatabaseHelper.DB_CYCLE_COUNT,0);

            byte[] byteArray=intent.getByteArrayExtra(DatabaseHelper.DB_CYCLE_DESCRIPTION);
            Log.v("JJT","byte array received");
            int[] intArray=ConversionManager.convertByteArrayToIntArray(byteArray);
            Log.v("JJT","intArrayProcess success");
            for(int milliseconds : intArray){
                Log.v("JJT","looping");
                createAndPushTimeItemToLinearLayout(milliseconds);
            }
            Log.v("JJT","bloc ok");

        }
    }



    @Override
    protected void onStart(){
        Log.v("JJT","start");
        super.onStart();
    }

    @Override
    protected void onResume(){
        Log.v("JJT","Resume");
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstance){
        savedInstance.putString(TIMER_NAME, ((EditText)findViewById(R.id.cycleName)).getText().toString());
        savedInstance.putInt(TIMER_CYCLE_COUNT,cycleCount);
        savedInstance.putByteArray(TIMER_ITEM_STORAGE,ConversionManager.convertIntArrayToByteArray(convertTimeItemFragmentArrayToIntArray(fragmentArrayList)));
        Log.v("JJT","saveInstance");
    }

    @Override
    protected void onPause(){
        Log.v("JJT","pause");
        super.onPause();
    }

    @Override
    protected void onStop(){
        Log.v("JJT","stop");
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        Log.v("JJT","destroy");
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.set_timer_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.saveToDatabase:
                Log.v("JJT","save to DATABASE triggered");
                break;

            case R.id.startTimer:
                Log.v("JJT","start timer triggered");
                break;
        }
        return true;
    }

    /**
     * function attached to the add timer button
     * the function should be able to create a new fragment, insert the same into the list view
     * it should also push the item to the List of fragments, so that the contents could be read at the end of the activity life
     */

    public void onAddTimerItemButtonPressed(View view){
        createAndPushTimeItemToLinearLayout();

        ScrollView scrollView=(ScrollView)findViewById(R.id.timerItemScrollView);
        scrollView.post(new Runnable() {

            @Override
            public void run() {
                ((ScrollView)findViewById(R.id.timerItemScrollView)).fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        /*ScrollView scrollView=(ScrollView)findViewById(R.id.timerItemScrollView);
        scrollView.pageScroll(ScrollView.FOCUS_DOWN);*/
    }

    public void onCycleCountIncrement(View view){
        cycleCount++;
        ((TextView)findViewById(R.id.cycleCountText)).setText(String.format(Locale.getDefault(),"%d",cycleCount));
    }

    public void onCycleCountDecrement(View view){
        if(cycleCount!=1){
            cycleCount--;
        }
        ((TextView)findViewById(R.id.cycleCountText)).setText(String.format(Locale.getDefault(),"%d",cycleCount));
    }

    private void createAndPushTimeItemToLinearLayout(){
        TimeItemFragment fragment=createTimeItemFragment();
        FrameLayout fragmentFrame = createTimeItemFrame(fragment);
        pushFrameToTimeItemLinearLayout(fragmentFrame);


        ScrollView scrollView=(ScrollView)findViewById(R.id.timerItemScrollView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    private void createAndPushTimeItemToLinearLayout(int totalSeconds){
        TimeItemFragment fragment=createTimeItemFragment(totalSeconds);
        FrameLayout fragmentFrame = createTimeItemFrame(fragment);
        pushFrameToTimeItemLinearLayout(fragmentFrame);
    }

    private TimeItemFragment createTimeItemFragment(){
        /*
        creation of the new instance of the fragment item
        the fragment should be pushed into the arrayList for management
        a counter is also is incremented to maintain the order of the items in the linear view
        */
        TimeItemFragment fragment=TimeItemFragment.newInstance(getString(R.string.settingSubCycle));
        timerCountTracker++;
        fragmentArrayList.add(fragment);

        return fragment;
    }

    private TimeItemFragment createTimeItemFragment(int totalSeconds){
        /*
        creation of the new instance of the fragment item
        the fragment should be pushed into the arrayList for management
        a counter is also is incremented to maintain the order of the items in the linear view
        */
        TimeItemFragment fragment=TimeItemFragment.newInstance(getString(R.string.settingSubCycle),totalSeconds);
        //fragment.setTimerTime(milliseconds);
        timerCountTracker++;
        fragmentArrayList.add(fragment);

        return fragment;
    }



    private FrameLayout createTimeItemFrame(TimeItemFragment fragment){

        /*
        a new frame is created for holding the fragment
        a new id is generated and assigned to the frame
        */
        FrameLayout fragmentFrame = new FrameLayout(this);
        fragmentFrame.setId(View.generateViewId());
        /*
        a new transaction is created and committed
        The fragment instance is pushed into the frame
         */
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(fragmentFrame.getId(), fragment);
        transaction.commit();

        return fragmentFrame;
    }

    /**
     * This function should be used to push a fragment into a frame
     * @param fragmentFrame to which the fragment should be pushed
     */
    private void pushFrameToTimeItemLinearLayout(FrameLayout fragmentFrame){

        /*
        a new transaction is created and committed
        The fragment instance is pushed into the frame
        the frame is pushed into the linear Layout
         */
        LinearLayout layout = (LinearLayout) findViewById(R.id.timeItemHolder);
        layout.addView(fragmentFrame,this.timerCountTracker-1);
    }

    private int[] convertTimeItemFragmentArrayToIntArray(ArrayList<TimeItemFragment> arrayList){
        int[] timeItemValueArray=new int[arrayList.size()];
        for(int i=0;i<arrayList.size();i++){
            timeItemValueArray[i]=arrayList.get(i).getTimerTime();
        }
        return timeItemValueArray;
    }




    public void onFragmentInteraction(){
        Log.v("JJT","Fragment listener caught an event");

    }

    public void onViewFocused(Activity activity, View view){
        Log.v("JJT","Focus listener caught an event");
        InputMethodManager inputMethodManager =(InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
    }

}

