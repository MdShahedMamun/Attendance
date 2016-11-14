package app.com.example.shahed.attendance;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class IntermediateActivity2 extends ActionBarActivity {
    private DatabaseHelper databaseHelper;
    private String dept;
    private String semester;
    private TextView errorMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);

        Spinner deptSpinner = (Spinner) findViewById(R.id.dept_spinner);
        Spinner semesterSpinner = (Spinner) findViewById(R.id.semester_spinner);
        errorMessageTextView=(TextView)findViewById(R.id.error_message_text_view);

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

    public void okButton2Clicked(View view) {
//
//        Cursor cursorResult = databaseHelper.getDeptData();
//
//        if (cursorResult.getCount() == 0) {
//            showMessage("Error", "Nothing found");
//        }
//
//        StringBuffer stringBuffer = new StringBuffer();
//        while (cursorResult.moveToNext()) {
//            stringBuffer.append("dept_id: " + cursorResult.getString(0) + "\n");
//            stringBuffer.append("dept_name: " + cursorResult.getString(1) + "\n\n");
//        }
//        showMessage("Dept Data", stringBuffer.toString());
//
//        cursorResult = databaseHelper.getSemesterData();
//        if (cursorResult.getCount() == 0) {
//            showMessage("Error", "Nothing found");
//        }
//
//        while (cursorResult.moveToNext()) {
//            stringBuffer.append("semester_id: " + cursorResult.getString(0) + "\n");
//            stringBuffer.append("semester_name: " + cursorResult.getString(1) + "\n\n");
//        }
//        showMessage("Semester Data", stringBuffer.toString());
//
//        cursorResult = databaseHelper.getDeptSemesterData();
//        if (cursorResult.getCount() == 0) {
//            showMessage("Error", "Nothing found");
//        }
//
//        while (cursorResult.moveToNext()) {
//            stringBuffer.append("dept_semester_id: " + cursorResult.getString(0) + "\n");
//            stringBuffer.append("dept_id: " + cursorResult.getString(1) + "\n");
//            stringBuffer.append("semester_id: " + cursorResult.getString(2) + "\n\n");
//        }
//        showMessage("DeptSemester Data", stringBuffer.toString());
//
//        cursorResult = databaseHelper.getMainData();
//        if (cursorResult.getCount() == 0) {
//            showMessage("Error", "Nothing found");
//        }
//
//        while (cursorResult.moveToNext()) {
//            stringBuffer.append("main_id: " + cursorResult.getString(0) + "\n");
//            stringBuffer.append("dept_semester_id: " + cursorResult.getString(1) + "\n");
//            stringBuffer.append("date: " + cursorResult.getString(2) + "\n");
//            stringBuffer.append("rollNo: " + cursorResult.getString(3) + "\n");
//            stringBuffer.append("attendanceType: " + cursorResult.getString(4) + "\n\n");
//        }
//        showMessage("Main Data", stringBuffer.toString());
//    }

        // show show data from database for specified dept/semester
        ArrayList<Integer> countArrayList = databaseHelper.getAllCount(dept, semester);
        if (countArrayList.size() == 0) {
            showMessage("Error", "Nothing found for given dept and semester!!");
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            int total, percentage;
            total = countArrayList.get(0);
//            stringBuffer.append("total: "+total);
            for (int i = 1; i <= 80; i++) {
                percentage = countArrayList.get(i) * 100 / total;
                stringBuffer.append("Roll No: " + Integer.toString(i)
//                        +"  countFromArrayList: "+countArrayList.get(i)
                        + "    percentage: " + Integer.toString(percentage) + "%\n");
            }
            showMessage("Attendance Result", stringBuffer.toString());
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intermediate2, menu);
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
