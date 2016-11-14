package app.com.example.shahed.attendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.com.example.shahed.attendance.AttendanceContract.DeptEntry;
import app.com.example.shahed.attendance.AttendanceContract.DeptSemesterEntry;
import app.com.example.shahed.attendance.AttendanceContract.MainEntry;
import app.com.example.shahed.attendance.AttendanceContract.SemesterEntry;

/**
 * Created by shahed on 12-Jun-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "sh6.db";
    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;
    private final String LOG_TAG = DatabaseHelper.class.getSimpleName();


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations. A location consists of the string supplied in
        // the location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_TABLE_DEPT = "CREATE TABLE " + DeptEntry.TABLE_DEPT + " (" +
                DeptEntry.COLUMN_DEPT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DeptEntry.COLUMN_DEPT_NAME + " TEXT);";         //column name er pore space diye then type dite hobe (marksTEXT)

        final String SQL_CREATE_TABLE_SEMESTER = "CREATE TABLE " + SemesterEntry.TABLE_SEMESTER + " (" +
                SemesterEntry.COLUMN_SEMESTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SemesterEntry.COLUMN_SEMESTER_NAME + " TEXT);";

        final String SQL_CREATE_TABLE_DEPT_SEMESTER = "CREATE TABLE " + DeptSemesterEntry.TABLE_DEPT_SEMESTER + " (" +
                DeptSemesterEntry.COLUMN_DEPT_SEMESTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DeptSemesterEntry.COLUMN_DEPT_ID + " INTEGER NOT NULL, " +
                DeptSemesterEntry.COLUMN_SEMESTER_ID + " INTEGER NOT NULL, " +

                "FOREIGN KEY (" + DeptSemesterEntry.COLUMN_DEPT_ID + ") REFERENCES " +
                DeptEntry.TABLE_DEPT + " (" + DeptEntry.COLUMN_DEPT_ID + "), " +

                "FOREIGN KEY (" + DeptSemesterEntry.COLUMN_SEMESTER_ID + ") REFERENCES " +
                SemesterEntry.TABLE_SEMESTER + " (" + SemesterEntry.COLUMN_SEMESTER_ID + "), " +

                "UNIQUE (" + DeptSemesterEntry.COLUMN_DEPT_ID + ", " +
                DeptSemesterEntry.COLUMN_SEMESTER_ID + ") ON CONFLICT IGNORE);";

        final String SQL_CREATE_TABLE_MAIN = "CREATE TABLE " + MainEntry.TABLE_MAIN + " (" +
                MainEntry.COLUMN_MAIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MainEntry.COLUMN_DEPT_SEMESTER_ID + " INTEGER NOT NULL, " +
                MainEntry.COLUMN_DATE + " TEXT, " +
                MainEntry.COLUMN_ROLL_NO + " INTEGER NOT NULL, " +
                MainEntry.COLUMN_ATTENDANCE_TYPE + " INTEGER NOT NULL, " +

                "FOREIGN KEY (" + MainEntry.COLUMN_DEPT_SEMESTER_ID + ") REFERENCES " +
                DeptSemesterEntry.TABLE_DEPT_SEMESTER + " (" + DeptSemesterEntry.COLUMN_DEPT_SEMESTER_ID + "));";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_DEPT);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_SEMESTER);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_DEPT_SEMESTER);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MAIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("drop table if exists " + DeptEntry.TABLE_DEPT);
        sqLiteDatabase.execSQL("drop table if exists " + SemesterEntry.TABLE_SEMESTER);
        sqLiteDatabase.execSQL("drop table if exists " + DeptSemesterEntry.TABLE_DEPT_SEMESTER);
        sqLiteDatabase.execSQL("drop table if exists " + MainEntry.TABLE_MAIN);

        onCreate(sqLiteDatabase);
    }

    public Cursor queryExecute(String tableName, String[] retrievingColumns, String selectionColumns, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.query(tableName,
                retrievingColumns,
                selectionColumns,
                selectionArgs,
                null,
                null,
                null,
                null);
    }

    public void insertData(String deptName, String semesterName, boolean[] itemChecked) {

        String dept_id = "", semester_id = "", dept_semester_id = "";

        //retrieve dept_id if deptName found in deptTable otherwise insert the depName and then retrieve the dept_id
        Cursor returnedCursor = queryExecute(DeptEntry.TABLE_DEPT,
                new String[]{DeptEntry.COLUMN_DEPT_ID},
                DeptEntry.COLUMN_DEPT_NAME + "=?",
                new String[]{deptName});
//        Log.v(LOG_TAG, "deptName: " + deptName
//                + " table: " + DeptEntry.TABLE_DEPT
//                + "COLUMN_DEPT_ID: " + DeptEntry.COLUMN_DEPT_ID
//                + "COLUMN_DEPT_NAME: " + DeptEntry.COLUMN_DEPT_NAME);

//        Log.v(LOG_TAG, "values of getCount(): " + returnedCursor.getCount());

        if (returnedCursor.getCount() != 0) {
            while (returnedCursor.moveToNext())
                dept_id = returnedCursor.getString(0);
            Log.v(LOG_TAG, "dept_id: " + dept_id);
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DeptEntry.COLUMN_DEPT_NAME, deptName);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            long dept = sqLiteDatabase.insert(DeptEntry.TABLE_DEPT, null, contentValues);
            Log.v(LOG_TAG, "dept long value is: " + dept);

            returnedCursor = queryExecute(DeptEntry.TABLE_DEPT,
                    new String[]{DeptEntry.COLUMN_DEPT_ID},
                    DeptEntry.COLUMN_DEPT_NAME + "=?",
                    new String[]{deptName});
            while (returnedCursor.moveToNext())
                dept_id = returnedCursor.getString(0);
        }

        //retrieve semester_id if semesterName found in semesterTable otherwise 
        // insert the semesterName and then retrieve the semester_id
        returnedCursor = queryExecute(SemesterEntry.TABLE_SEMESTER,
                new String[]{SemesterEntry.COLUMN_SEMESTER_ID},
                SemesterEntry.COLUMN_SEMESTER_NAME + "=?",
                new String[]{semesterName});
        if (returnedCursor.getCount() != 0) {
            while (returnedCursor.moveToNext())
                semester_id = returnedCursor.getString(0);
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SemesterEntry.COLUMN_SEMESTER_NAME, semesterName);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            long sem = sqLiteDatabase.insert(SemesterEntry.TABLE_SEMESTER, null, contentValues);
            Log.v(LOG_TAG, "sem long value  is: " + sem);


            returnedCursor = queryExecute(SemesterEntry.TABLE_SEMESTER,
                    new String[]{SemesterEntry.COLUMN_SEMESTER_ID},
                    SemesterEntry.COLUMN_SEMESTER_NAME + "=?",
                    new String[]{semesterName});
            while (returnedCursor.moveToNext())
                semester_id = returnedCursor.getString(0);
        }


        //retrieve dept_semester_id if dept_id and semester_id found in dept_semester otherwise insert
        // the dept_id and semester_id and then retrieve the dept_semester_id
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursorResult = sqLiteDatabase.query(DeptSemesterEntry.TABLE_DEPT_SEMESTER,
                new String[]{DeptSemesterEntry.COLUMN_DEPT_SEMESTER_ID},
                DeptSemesterEntry.COLUMN_DEPT_ID + "=? and " + DeptSemesterEntry.COLUMN_SEMESTER_ID + "=?",
                new String[]{dept_id, semester_id},
                null,
                null,
                null,
                null);

        if (cursorResult.getCount() != 0) {
            while (cursorResult.moveToNext())
                dept_semester_id = cursorResult.getString(0);
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DeptSemesterEntry.COLUMN_DEPT_ID, dept_id);
            contentValues.put(DeptSemesterEntry.COLUMN_SEMESTER_ID, semester_id);

            sqLiteDatabase.insert(DeptSemesterEntry.TABLE_DEPT_SEMESTER, null, contentValues);

            cursorResult = sqLiteDatabase.query(DeptSemesterEntry.TABLE_DEPT_SEMESTER,
                    new String[]{DeptSemesterEntry.COLUMN_DEPT_SEMESTER_ID},
                    DeptSemesterEntry.COLUMN_DEPT_ID + "=? and " + DeptSemesterEntry.COLUMN_SEMESTER_ID + "=?",
                    new String[]{dept_id, semester_id},
                    null,
                    null,
                    null,
                    null);
            while (cursorResult.moveToNext())
                dept_semester_id = cursorResult.getString(0);
        }


        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        for (int i = 0; i < 80; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MainEntry.COLUMN_DEPT_SEMESTER_ID, dept_semester_id);
            contentValues.put(MainEntry.COLUMN_DATE, date);
            contentValues.put(MainEntry.COLUMN_ROLL_NO, i + 1); // roll no from 1-10
            int attendanceType; // 0 for absent and 1 for present
            if (itemChecked[i])
                attendanceType = 1;
            else
                attendanceType = 0;
            contentValues.put(MainEntry.COLUMN_ATTENDANCE_TYPE, attendanceType);

            sqLiteDatabase.insert(MainEntry.TABLE_MAIN, null, contentValues);
            //this insert() method  return the row id of the newly inserted row
        }
    }


    public Cursor getDeptData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("select*from " + DeptEntry.TABLE_DEPT, null);
    }

    public Cursor getSemesterData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("select*from " + SemesterEntry.TABLE_SEMESTER, null);
    }

    public Cursor getDeptSemesterData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("select*from " + DeptSemesterEntry.TABLE_DEPT_SEMESTER, null);
    }

    public Cursor getMainData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("select*from " + MainEntry.TABLE_MAIN, null);
    }

    public void joinQuery() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select " + DeptSemesterEntry.COLUMN_DEPT_SEMESTER_ID +
                " from " + DeptSemesterEntry.TABLE_DEPT_SEMESTER + " INNER JOIN " + SemesterEntry.TABLE_SEMESTER +
                " ON " + SemesterEntry.TABLE_SEMESTER + "." + SemesterEntry.COLUMN_SEMESTER_ID + " = " +
                DeptSemesterEntry.TABLE_DEPT_SEMESTER + "." + DeptSemesterEntry.COLUMN_SEMESTER_ID +
                " and " + SemesterEntry.COLUMN_SEMESTER_NAME + " =?"
                , new String[]{"3/2"});
        while (cursor.moveToNext())
            Log.v(LOG_TAG, "from join query dept_semester id of 3/2: " + cursor.getString(0));
    }

    // return a arrayList object of present count
    public ArrayList<Integer> getAllCount(String deptName, String semesterName) {

//        joinQuery();//test join query

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String dept_id = "", semester_id = "", dept_semester_id = "";
        ArrayList<Integer> countArrayList = new ArrayList<>();


        Cursor returnedCursor = queryExecute(DeptEntry.TABLE_DEPT,
                new String[]{DeptEntry.COLUMN_DEPT_ID},
                DeptEntry.COLUMN_DEPT_NAME + "=?",
                new String[]{deptName});

        if (returnedCursor.getCount() != 0) {
            while (returnedCursor.moveToNext())
                dept_id = returnedCursor.getString(0);
            Log.v(LOG_TAG, "dept_id: " + dept_id);
        } else {
            Log.v(LOG_TAG, "inside else");
            return countArrayList;
        }

        returnedCursor = queryExecute(SemesterEntry.TABLE_SEMESTER,
                new String[]{SemesterEntry.COLUMN_SEMESTER_ID},
                SemesterEntry.COLUMN_SEMESTER_NAME + "=?",
                new String[]{semesterName});
        if (returnedCursor.getCount() != 0) {
            while (returnedCursor.moveToNext())
                semester_id = returnedCursor.getString(0);
        } else {
            return countArrayList;
        }

        returnedCursor = sqLiteDatabase.query(DeptSemesterEntry.TABLE_DEPT_SEMESTER,
                new String[]{DeptSemesterEntry.COLUMN_DEPT_SEMESTER_ID},
                DeptSemesterEntry.COLUMN_DEPT_ID + "=? and " + DeptSemesterEntry.COLUMN_SEMESTER_ID + "=?",
                new String[]{dept_id, semester_id},
                null,
                null,
                null,
                null);

        if (returnedCursor.getCount() != 0) {
            while (returnedCursor.moveToNext())
                dept_semester_id = returnedCursor.getString(0);
        } else {
            return countArrayList;
        }

        int c = 0;
        // total class held for a specific dept and semester
        final String SQL_STATEMENT = "select count(*) from " + MainEntry.TABLE_MAIN
                + " where " + MainEntry.COLUMN_DEPT_SEMESTER_ID
                + "=? and " + MainEntry.COLUMN_ROLL_NO + "=?";
        returnedCursor = sqLiteDatabase.rawQuery(SQL_STATEMENT, new String[]{dept_semester_id, "1"});
        if (returnedCursor.getCount() != 0) {
            if (returnedCursor.moveToNext())
                c = returnedCursor.getInt(0);
            countArrayList.add(c);
        }

        // count attendance for every roll no where attendance type=1 or present
        final String SQL_STATEMENT2 = "select count(*) from " + MainEntry.TABLE_MAIN
                + " where " + MainEntry.COLUMN_DEPT_SEMESTER_ID
                + "=? and " + MainEntry.COLUMN_ROLL_NO
                + "=? and " + MainEntry.COLUMN_ATTENDANCE_TYPE + "=?";
        for (int i = 1; i <= 80; i++) {
            returnedCursor = sqLiteDatabase.rawQuery(SQL_STATEMENT2, new String[]{dept_semester_id, Integer.toString(i), "1"});
            if (returnedCursor.getCount() != 0) {
                if (returnedCursor.moveToNext()) {
                    c = returnedCursor.getInt(0);
                    countArrayList.add(c);
                }
            }
        }
        return countArrayList;
    }
}
