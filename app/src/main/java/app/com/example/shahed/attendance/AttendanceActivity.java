package app.com.example.shahed.attendance;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AttendanceActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    //    ArrayList<String> stringArrayList1;
    private CustomAdapter customAdapter;
    private final String LOG_TAG = AttendanceActivity.class.getSimpleName();
    private String dept;
    private String semester;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attedance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        dept = bundle.getString("dept");
        semester = bundle.getString("semester");

        listView = (ListView) findViewById(R.id.listView1);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        stringArrayList1 = new ArrayList<String>();

        String[] rollNoString = {
                " 1", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", " 10",
                " 11", " 12", " 13", " 14", " 15", " 16", " 17", " 18", " 19", " 20",
                " 21", " 22", " 23", " 24", " 25", " 26", " 27", " 28", " 29", " 30",
                " 31", " 32", " 33", " 34", " 35", " 36", " 37", " 38", " 39", " 40",
                " 41", " 42", " 43", " 44", " 45", " 46", " 47", " 48", " 49", " 50",
                " 51", " 52", " 53", " 54", " 55", " 56", " 57", " 58", " 59", " 60",
                " 61", " 62", " 63", " 64", " 65", " 66", " 67", " 68", " 69", " 70",
                " 71", " 72", " 73", " 74", " 75", " 76", " 77", " 78", " 79", " 80"
        };

        ArrayList<String> stringArrayList2 = new ArrayList<String>(
                Arrays.asList(rollNoString));

        customAdapter = new CustomAdapter(this, stringArrayList2);

        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attendance, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        // TODO Auto-generated method stub
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox1);
        TextView tv = (TextView) v.findViewById(R.id.textView1);
        String string = (String) parent.getItemAtPosition(position);
//        Log.v(LOG_TAG, "inside onItemClick a");
        checkBox.performClick();
//        Log.v(LOG_TAG, "inside onItemClick b");

//        if (checkBox.isChecked()) {
//            stringArrayList1.add(string);
//            Log.v(LOG_TAG," inside onItemClick if");
//        } else {
//            Log.v(LOG_TAG," inside onItemClick else");
//            stringArrayList1.remove(string);
//        }
    }

    public void submitButtonClicked(View view) {
//        Log.v(LOG_TAG,"inside submitButtonClicked");
//        for (int i = 0; i < stringArrayList1.size(); i++)
//            Log.v(LOG_TAG, stringArrayList1.get(i));

        boolean[] itemChecked = customAdapter.getItemCheckedArray();
        for (int i = 0; i < itemChecked.length; i++)
            Log.v(LOG_TAG, " see: " + itemChecked[i]);

        //insert into database
        databaseHelper.insertData(dept, semester, itemChecked);
        Toast.makeText(this, "data inserted successfully :)", Toast.LENGTH_LONG).show();
        dept="";
        semester="";
    }
}
