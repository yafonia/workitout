package id.ac.ui.cs.mobileprogramming.yafonia.workitout;


import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.CalendarContract;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.ac.ui.cs.mobileprogramming.yafonia.workitout.databinding.AddToCalendarBinding;
import id.ac.ui.cs.mobileprogramming.yafonia.workitout.databinding.FragmentDetailsBinding;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddToCalendarFragment extends Fragment {

    public AddToCalendarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.add_to_calendar, container, false);
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText pick_date_time = (EditText) view.findViewById(R.id.date_time_input);
        TextView exercise_name = (TextView) view.findViewById(R.id.program_name);
        Button add_to_calendar = (Button) view.findViewById(R.id.button_add_to_calendar);
        pick_date_time.setInputType(InputType.TYPE_NULL);

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getSelected().observe(getViewLifecycleOwner(), item -> {
            exercise_name.setText(item.getName());
        });

        pick_date_time.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(pick_date_time);
            }
        }));

        add_to_calendar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int callbackId = 42;
                checkPermission(callbackId, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR);
                String date = pick_date_time.getText().toString();
                add_event(date);
            }
        }));
    }

    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {

            @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR,year);
                    calendar.set(Calendar.MONTH,month);
                    calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                    TimePickerDialog.OnTimeSetListener timeSetListener= new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                            calendar.set(Calendar.MINUTE,minute);

                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd HH:mm");

                            date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                            Log.d(TAG, "gettime: " + calendar.getTime());
                        }
                    };

                    new TimePickerDialog(getActivity(),timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
                }
            };

            new DatePickerDialog(getActivity(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

        }

     private void add_event(String date_time_value) {
         SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
         long milliseconds = convertDateToLong(date_time_value);
         viewModel.getSelected().observe(getViewLifecycleOwner(), item -> {
             QueryHandlerCalendar.insertEvent(getActivity(), milliseconds,
                     milliseconds + 600*1000, item.getName() + " " + getString(R.string.exercise_program));
         });

    }

    private void checkPermission(int callbackId, String... permissionsId) {
        boolean permissions = true;
        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(getActivity(), p) == PackageManager.PERMISSION_GRANTED;
        }

        if (!permissions)
            ActivityCompat.requestPermissions(getActivity(), permissionsId, callbackId);
    }

    private long convertDateToLong(String date_time_value) {
        SimpleDateFormat f = new SimpleDateFormat("yy-MM-dd HH:mm");
        long milliseconds = Calendar.getInstance().getTimeInMillis();
        try {
            Date d = f.parse(date_time_value);
            milliseconds = d.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
}

