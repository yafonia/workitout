package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import java.util.TimeZone;

public class QueryHandlerCalendar extends AsyncQueryHandler {
    private static final String TAG = "QueryHandler";

    // Projection arrays
    private static final String[] CALENDAR_PROJECTION = new String[]
            {
                    CalendarContract.Calendars._ID
            };

    // The indices for the projection array above.
    private static final int CALENDAR_ID_INDEX = 0;

    private static final int CALENDAR = 0;
    private static final int EVENT = 1;
    private static final int REMINDER = 2;

    private static QueryHandlerCalendar queryHandler;

    // QueryHandler
    public QueryHandlerCalendar(ContentResolver resolver) {
        super(resolver);
    }

    // insertEvent
    public static void insertEvent(Context context, long startTime,
                                   long endTime, String title) {
        ContentResolver resolver = context.getContentResolver();

        if (queryHandler == null)
            queryHandler = new QueryHandlerCalendar(resolver);

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startTime);
        values.put(CalendarContract.Events.DTEND, endTime);
        values.put(CalendarContract.Events.TITLE, title);
        values.put(CalendarContract.Events.ALL_DAY, false);

        if (BuildConfig.DEBUG)
            Log.d(TAG, "Calendar query start");

        queryHandler.startQuery(CALENDAR, values, CalendarContract.Calendars.CONTENT_URI,
                CALENDAR_PROJECTION, null, null, null);
    }

    @Override
    public void onQueryComplete(int token, Object object, Cursor cursor)
    {
        // Use the cursor to move through the returned records
        cursor.moveToFirst();

        // Get the field values
        long calendarID = cursor.getLong(CALENDAR_ID_INDEX);

        if (BuildConfig.DEBUG)
            Log.d(TAG, "Calendar query complete " + calendarID);

        ContentValues values = (ContentValues) object;
        values.put(CalendarContract.Events.CALENDAR_ID, calendarID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE,
                TimeZone.getDefault().getDisplayName());

        startInsert(EVENT, null, CalendarContract.Events.CONTENT_URI, values);
    }

    @Override
    public void onInsertComplete(int token, Object object, Uri uri)
    {
        if (uri != null)
        {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "Insert complete " + uri.getLastPathSegment());

            switch (token)
            {
                case EVENT:
                    long eventID = Long.parseLong(uri.getLastPathSegment());
                    ContentValues values = new ContentValues();
                    values.put(CalendarContract.Reminders.MINUTES, 10);
                    values.put(CalendarContract.Reminders.EVENT_ID, eventID);
                    values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                    startInsert(REMINDER, null, CalendarContract.Reminders.CONTENT_URI, values);
                    break;
            }
        }
    }


}