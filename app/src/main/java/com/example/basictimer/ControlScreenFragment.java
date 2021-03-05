package com.example.basictimer;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ControlScreenFragment extends Fragment implements View.OnClickListener{

    Button startButton;
    Button lapButton;
    Button resetButton;
    Button nextScreen;
    TextView time;
    OnFragmentInteractionListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_control, container, false);

        //initialize buttons and textview
        startButton = (Button) view.findViewById(R.id.startButton);
        lapButton = (Button) view.findViewById(R.id.lapButton);
        resetButton = (Button) view.findViewById(R.id.resetButton);
        time = (TextView) view.findViewById(R.id.time);
        nextScreen = (Button) view.findViewById(R.id.nextButton);
        int orientation = getResources().getConfiguration().orientation;
        // Only show the view laps button if in portrait mode
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            nextScreen.setVisibility(Button.VISIBLE);
        }
        else {
            nextScreen.setVisibility(Button.INVISIBLE);
        }

        startButton.setOnClickListener(this);
        lapButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        nextScreen.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnFragmentInteractionListener){
            this.mListener= (OnFragmentInteractionListener) context;
        }else{
            throw new RuntimeException(context.toString()+" must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {

        super.onDetach();
    }

    public void onClick(View view){
        if(view.getId() == startButton.getId()){

            mListener.onButtonClicked(0);
            if(startButton.getText().equals("Start")){
                startButton.setText("Stop");
            }
            else if(startButton.getText().equals("Stop")){
                startButton.setText("Start");
            }

        }else if (view.getId() == lapButton.getId()){

            mListener.onButtonClicked(1);

        } else if(view.getId() == resetButton.getId() ){

            mListener.onButtonClicked(2);
        }
        else if(view.getId() == nextScreen.getId() ){

            mListener.onButtonClicked(3);
        }
    }

    public interface OnFragmentInteractionListener{
        void onButtonClicked(int infoID);
    }

}
