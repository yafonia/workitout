package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.app.Notification;
import android.view.View;

import static id.ac.ui.cs.mobileprogramming.yafonia.workitout.Notification.CHANNEL_1_ID;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class CountdownFragment extends Fragment {
    String[] exercises;
    String program_name;
    private OpenGLView openGLView;
    public static volatile int second = 10;
    HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
    Handler timerHandler;
    Runnable task;
    public static volatile boolean isExecuted = false;
    MainActivity activity;
    int indexCurrentProgram = 0;
    private NotificationManagerCompat notificationManager;

    static{
        System.loadLibrary("decrement-lib");
    }

    public static native int decrement(int num);

    public static synchronized int getSecond() {
        return second;
    }

    public static synchronized void decrementSecond() {

        second = decrement(second);
    }

    public CountdownFragment(String[] exercise, String program_name) {

        this.exercises = exercise;
        this.program_name = program_name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.countdown, container, false);
        activity = (MainActivity) requireActivity();
        isExecuted = true;
        handlerThread.start();
        timerHandler = new Handler(handlerThread.getLooper());

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView countdown_text = (TextView) view.findViewById(R.id.countdown_text);
        TextView exercise_text = (TextView) view.findViewById(R.id.set_text);
        Button stop_button = (Button) view.findViewById(R.id.stop);
        Button start_button = (Button) view.findViewById(R.id.start_from_beginning);
        openGLView = view.findViewById(R.id.openGLView);
        startTimer(countdown_text, exercise_text);
        activity.startService(new Intent(activity, MediaPlayerService.class));
        notificationManager = NotificationManagerCompat.from(getActivity());

        stop_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                isExecuted = false;
                second = 10;
                indexCurrentProgram = 0;
                activity.stopService(new Intent(activity, MediaPlayerService.class));
            }
        });

        start_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                isExecuted = true;
                startTimer(countdown_text, exercise_text);
                activity.startService(new Intent(activity, MediaPlayerService.class));
            }
        });

        activity.backButtonDisabled = true;
    }

    public void startTimer(TextView countdown_text, TextView exercise_text) {
        task = new Runnable() {
            @Override
            public void run() {
                if (isExecuted) {
                    if (indexCurrentProgram < exercises.length) {
                        exercise_text.post(new Runnable() {

                            @Override
                            public void run() {
                                exercise_text.setText(exercises[indexCurrentProgram]);
                            }
                        });
                        while (second > 0 && isExecuted) {
                            try {
                                Thread.sleep(1000);
                                decrementSecond();
                            } catch (InterruptedException e) {
                                Log.d(TAG, "run: test");
                            }
                            countdown_text.post(new Runnable() {

                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    countdown_text.setText(Integer.toString(getSecond()));
                                }
                            });
                        }
                        second = 10;
                        indexCurrentProgram++;
                        timerHandler.post(this);
                    }
                    else{
                        sendOnChannel1(getView());
                        activity.stopService(new Intent(activity, MediaPlayerService.class));
                    }
                    activity.backButtonDisabled = false;
                }
            }
        };
        timerHandler.post(task);
    }

    public void sendOnChannel1(View v) {
        String title = getString(R.string.finish);
        String message = getString(R.string.congratulations) + " " + program_name + "!";
        Notification notification = new NotificationCompat.Builder(activity, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_thumb_up_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setChannelId(CHANNEL_1_ID)
                .build();

        notificationManager.notify(1, notification);

    }

    @Override
    public void onResume() {
        super.onResume();
        openGLView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        openGLView.onPause();
    }
}









