package app.com.example.shahed.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class IntermediateActivity1 extends ActionBarActivity {
    private String dept;
    private String semester;
    private TextView errorMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        errorMessageTextView = (TextView) findViewById(R.id.error_message_text_view);
        Spinner deptSpinner = (Spinner) findViewById(R.id.dept_spinner);
        Spinner semesterSpinner = (Spinner) findViewById(R.id.semester_spinner);
        errorMessageTextView = (TextView) findViewById(R.id.error_message_text_view);

        String[] deptItems = new String[]{"CSE", "PME", "IPE", "CEP", "EEE", "FET"};
        String[] semesterItems = new String[]{"1/1", "1/2", "2/1", "2/2", "3/1", "3/2", "4/1", "4/2"};


        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, deptItems);
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, semesterItems);

        deptSpinner.setAdapter(deptAdapter);
        semesterSpinner.setAdapter(semesterAdapter);

        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                dept = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                errorMessageTextView.setText("Error! Select option from both dropdown");
            }
        });

        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                semester = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                errorMessageTextView.setText("Error! Select option from both dropdown");
            }
        });
    }

    public void okButton1Clicked(View view) {
        Intent intent = new Intent(this, AttendanceActivity.class);
        intent.putExtra("dept", dept);
        intent.putExtra("semester", semester);
        Log.v("see", "dept: " + dept + " semester: " + semester);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intermediate1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
