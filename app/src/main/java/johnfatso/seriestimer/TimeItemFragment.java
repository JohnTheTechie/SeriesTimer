package johnfatso.seriestimer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimeItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimeItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class TimeItemFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TIMER_LABEL = "param1";
    private static final String ARG_TIMER_SECONDS = "param2";


    private String timerLabel;
    private int minutes, seconds;
    private View localView;
    private OnFragmentInteractionListener mListener;

    public TimeItemFragment() {
        // Required empty public constructor
    }

    /**
     * This function shall be used for creating a new fragment instance
     *
     * @param timerLabel the string to be set as the label of the timer.
     * @return A new instance of fragment TimeItemFragment.
     */

    public static TimeItemFragment newInstance(String timerLabel) {
        TimeItemFragment fragment = new TimeItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TIMER_LABEL, timerLabel);
        args.putInt(ARG_TIMER_SECONDS,0);
        fragment.setArguments(args);
        return fragment;
    }

    public static TimeItemFragment newInstance(String timerLabel, int numberOfSeconds) {
        TimeItemFragment fragment = new TimeItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TIMER_LABEL, timerLabel);
        args.putInt(ARG_TIMER_SECONDS,numberOfSeconds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            timerLabel = getArguments().getString(ARG_TIMER_LABEL);
            this.minutes=getArguments().getInt(ARG_TIMER_SECONDS)/60;
            this.seconds=getArguments().getInt(ARG_TIMER_SECONDS)%60;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        localView=inflater.inflate(R.layout.fragment_time_item, container, false);
        TextView labelTextView= localView.findViewById(R.id.timerLabel);
        final EditText minuteView=localView.findViewById(R.id.minuteValue);
        final EditText secondView=localView.findViewById(R.id.secondValue);
        labelTextView.setText(this.timerLabel);
        minuteView.setText(""+this.minutes);
        secondView.setText(""+this.seconds);

        minuteView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if((!hasFocus)&&((view.getId()==R.id.minuteValue))){
                    Log.v("JJT","FocusChange caught minute "+hasFocus);
                    String minuteString=((EditText)localView.findViewById(R.id.minuteValue)).getText().toString();
                    minutes=Integer.parseInt(minuteString);
                }
                else if(hasFocus){
                    //mListener.onViewFocused(getActivity(),minuteView);
                }
            }
        });

        secondView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if((!hasFocus)&&((view.getId()==R.id.secondValue))){
                    String secondString=((EditText)localView.findViewById(R.id.secondValue)).getText().toString();
                    seconds=Integer.parseInt(secondString);
                    checkSecondValue();
                    Log.v("JJT","FocusChange caught second "+hasFocus+" "+seconds);

                }
                else if(hasFocus){
                   // mListener.onViewFocused(getActivity(),secondView);
                }
            }
        });
        return localView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mListener=(OnFragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    private void checkSecondValue(){
        if(seconds>=60){
            seconds=59;
            ((EditText)localView.findViewById(R.id.secondValue)).setText(String.format(Locale.getDefault(),"%d",seconds));
        }
    }

    /**
     * User defined functions are to be defined here
     * the following functions are going to be used for fetching the time stamps
     */

    public int getTimerTime(){
        int timerTime;
        String minuteValue=((EditText)localView.findViewById(R.id.minuteValue)).getText().toString();
        String secondValue=((EditText)localView.findViewById(R.id.secondValue)).getText().toString();
        timerTime=(Integer.parseInt(minuteValue)*60+Integer.parseInt(secondValue));
        return timerTime;
    }

    public void setTimerTime(int numberOfSeconds){
        int minute,seconds;
        seconds=numberOfSeconds%60;
        minute=numberOfSeconds/60;
        ((EditText)localView.findViewById(R.id.minuteValue)).setText(""+minute);
        ((EditText)localView.findViewById(R.id.secondValue)).setText(""+seconds);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onViewFocused(Activity activity, View view);
        void onFragmentInteraction();
    }


}
