package com.example.basictimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ControlScreenFragment.OnFragmentInteractionListener {

    ControlScreenFragment controlScreenFragment;
    ListScreenFragment listScreenFragment;
    MyAsyncTask myAsyncTask;
    int count;
    int lapCount;
    String currentLaps;
    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controlScreenFragment = (ControlScreenFragment) getSupportFragmentManager().findFragmentById(R.id.controlFrag);
        listScreenFragment = (ListScreenFragment) getSupportFragmentManager().findFragmentById(R.id.listFragment);
        myAsyncTask = new MyAsyncTask();
        count = 0;
        lapCount = 1;
        currentLaps = "";
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save the needed information to restore the state when the direction of the phone is changed.
        super.onSaveInstanceState(outState);
        outState.putInt("counter", count);
        outState.putString("laptimes", currentLaps);
        outState.putInt("lapcounter", lapCount);
        outState.putString("status", controlScreenFragment.startButton.getText().toString());
        outState.putString("time", currentTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the information that was seen on screen before the rotation.
        super.onRestoreInstanceState(savedInstanceState);
        int currCount = savedInstanceState.getInt("counter");
        String currLaps = savedInstanceState.getString("laptimes");
        int currLapCount = savedInstanceState.getInt("lapcounter");
        this.currentLaps = currLaps;
        this.count = currCount;
        this.lapCount = currLapCount;
        String currTime = savedInstanceState.getString("time");
        String status = savedInstanceState.getString("status");
        if (listScreenFragment != null && listScreenFragment.isInLayout()) { // Landscape
            listScreenFragment.times.setText(currLaps);
        }
        if (status.equals("Stop")) {
            myAsyncTask.execute(1000000000);
            controlScreenFragment.time.setText(currTime);
            controlScreenFragment.startButton.setText("Stop");
        }
    }

    @Override
    protected void onDestroy() {
        //checking if asynctask is still runnning
        if (myAsyncTask != null && myAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            //cancel the task before destroying activity
            myAsyncTask.cancel(true);
            myAsyncTask = null;
        }
        super.onDestroy();
    }

    @Override
    public void onButtonClicked(int infoID) {
        if (infoID == 2) { // Reset Button
            if (listScreenFragment != null && listScreenFragment.isInLayout()) { // Landscape Mode
                boolean check = false;
                myAsyncTask.cancel(check);
                myAsyncTask = new MyAsyncTask();
                count = 0;
                controlScreenFragment.time.setText("00:00:00");
                controlScreenFragment.startButton.setText("Start");
                listScreenFragment.times.setText("");
                currentLaps = "";
                lapCount = 1;
            } else { //for portrait mode
                boolean check = false;
                myAsyncTask.cancel(check);
                myAsyncTask = new MyAsyncTask();
                count = 0;
                controlScreenFragment.time.setText("00:00:00");
                controlScreenFragment.startButton.setText("Start");
                lapCount = 1;
                currentLaps = "";
            }
        } else if (infoID == 0) { //Start / Stop Button
            if (myAsyncTask.getStatus() != AsyncTask.Status.RUNNING) {
                myAsyncTask = new MyAsyncTask();
                //passing in a large number as the limit and executing the task
                myAsyncTask.execute(1000000000);
            } else {
                boolean check = false;
                myAsyncTask.cancel(check);
            }
        } else if (infoID == 3) { // next button.
            if (listScreenFragment != null && listScreenFragment.isInLayout()) { // Landscape Mode
            } else { // Portrait mode
                Intent intent = new Intent(this, ListActivity.class);
                intent.putExtra("listinfo", currentLaps);
                startActivity(intent);
            }
        } else { // User clicked the lap button. infoID == 1
            if (listScreenFragment != null && listScreenFragment.isInLayout()) { // Landscape Mode
                String currentText = currentLaps;
                int currentTime = count;
                int hours = currentTime / 3600;
                if (currentTime >= 3600) currentTime = currentTime - (hours * 3600);
                int minutes = currentTime / 60;
                if (currentTime >= 60) currentTime = currentTime - (minutes * 60);
                String formatted = String.format("%02d:%02d:%02d", hours, minutes, currentTime);
                String updatedText = currentText + '\n' + lapCount + ". " + formatted;
                listScreenFragment.times.setText(updatedText);
                currentLaps = updatedText;
                lapCount++;
            } else { // Portrait mode
                int currentTime = count;
                int hours = currentTime / 3600;
                if (currentTime >= 3600) currentTime = currentTime - (hours * 3600);
                int minutes = currentTime / 60;
                if (currentTime >= 60) currentTime = currentTime - (minutes * 60);
                String formatted = String.format("%02d:%02d:%02d", hours, minutes, currentTime);
                String updatedText = currentLaps + '\n' + lapCount + ". " + formatted;
                currentLaps = updatedText;
                lapCount++;
            }
        }
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            while (count < params[0]) {
                try {
                    //checking if the asynctask has been cancelled, end loop if so
                    if (isCancelled()) break;

                    Thread.sleep(1000);

                    count++;

                    //send count to onProgressUpdate to update UI
                    publishProgress(count);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //setting count to 0 and setting textview to 0 after doInBackground finishes running
            count = 0;
            controlScreenFragment.time.setText("00:00:00");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Update the text showing the running timer.
            super.onProgressUpdate(values);
            int temp = values[0];
            int hours = temp / 3600;
            if (temp >= 3600) temp = temp - (hours * 3600);
            int minutes = temp / 60;
            if (temp >= 60) temp = temp - (minutes * 60);
            String formatted = String.format("%02d:%02d:%02d", hours, minutes, temp);
            currentTime = formatted;
            controlScreenFragment.time.setText(formatted);
        }
    }
}