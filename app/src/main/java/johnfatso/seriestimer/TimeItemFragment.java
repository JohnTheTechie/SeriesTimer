package johnfatso.seriestimer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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

    private String timerLabel;
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
    // TODO: Rename and change types and number of parameters
    public static TimeItemFragment newInstance(String timerLabel) {
        TimeItemFragment fragment = new TimeItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TIMER_LABEL, timerLabel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            timerLabel = getArguments().getString(ARG_TIMER_LABEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        localView=inflater.inflate(R.layout.fragment_time_item, container, false);
        TextView labelTextView= localView.findViewById(R.id.timerLabel);
        labelTextView.setText(this.timerLabel);
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

    /**
     * User defined functions are to be defined here
     * the following functions are going to be used for fetching the time stamps
     */

    public long getTimerTime(){
        long timerTime;
        String minuteValue=((EditText)localView.findViewById(R.id.minuteValue)).getText().toString();
        String secondValue=((EditText)localView.findViewById(R.id.secondValue)).getText().toString();
        timerTime=(Integer.parseInt(minuteValue)*60+Integer.parseInt(secondValue))*1000;
        return timerTime;
    }

    public void setTimerTime(long milliseconds){
        int minute,seconds;
        seconds=(int)(milliseconds/1000)%60;
        minute=(int)(milliseconds/1000)/60;
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
        void onFragmentInteraction();
    }


}
