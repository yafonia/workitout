package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class TimezoneBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
            Toast.makeText(context, "Timezone's changed! Be sure when you add your exercise program to your calendar", Toast.LENGTH_SHORT).show();
        }
    }


}
