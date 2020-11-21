package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.net.Uri;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    private ProgramListFragment programListFragment = new ProgramListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, programListFragment)
                .addToBackStack("task_list")
                .commit();
    }
}
