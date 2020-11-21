package id.ac.ui.cs.mobileprogramming.yafonia.workitout;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class CountdownFragment extends Fragment {
    String[] exercise;

    public CountdownFragment(String[] exercise) {
        this.exercise = exercise;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.countdown, container, false);
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button start_timer = (Button) view.findViewById(R.id.button_start_pause);
        TextView countdown_text = (TextView) view.findViewById(R.id.countdown_text);
        TextView exercise_text = (TextView) view.findViewById(R.id.set_text);
        start_timer.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < exercise.length; i++) {
                    startTimer(countdown_text, exercise_text);
                }
            }
        }));
    }

    public void startTimer(TextView countdown_text, TextView exercise_text) {
        Handler handler = new Handler(Looper.getMainLooper());
        HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
        Thread thread = new Thread(new Runnable() {
            int second = 10;
            @Override
            public void run() {
                try {
                    for (int i = 0; i < exercise.length; i++) {
                        exercise_text.setText(exercise[i]);
                        while (second > 0) {
                            Thread.sleep(1000);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    countdown_text.setText(Integer.toString(second));
                                }
                            });
                            Log.d(TAG, Integer.toString(second));
                            second--;
                        }
                        second = 10;
                    }
                }
                catch  (Exception e) {

                }
            }
        });

        thread.start();
    }


}
