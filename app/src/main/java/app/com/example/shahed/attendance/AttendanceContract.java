package app.com.example.shahed.attendance;

import android.provider.BaseColumns;

/**
 * Created by shahed on 14-Jun-16.
 */
public class AttendanceContract {
    /**
     * Inner class that defines the table contents of the the student table
     */
    public static final class DeptEntry implements BaseColumns {

        public static final String TABLE_DEPT = "dept";

        public static final String COLUMN_DEPT_ID = "dept_id";
        public static final String COLUMN_DEPT_NAME = "dept_name";
    }

    public static final class SemesterEntry implements BaseColumns {

        public static final String TABLE_SEMESTER = "semester";

        public static final String COLUMN_SEMESTER_ID = "semester_id";
        public static final String COLUMN_SEMESTER_NAME = "semester_name";
    }

    public static final class DeptSemesterEntry implements BaseColumns {

        public static final String TABLE_DEPT_SEMESTER = "dept_semester";

        public static final String COLUMN_DEPT_SEMESTER_ID = "dept_semester_id";
        public static final String COLUMN_DEPT_ID = "dept_id";
        public static final String COLUMN_SEMESTER_ID = "semester_id";
    }

    public static final class MainEntry implements BaseColumns {

        public static final String TABLE_MAIN = "main";

        public static final String COLUMN_MAIN_ID = "main_id";
        public static final String COLUMN_DEPT_SEMESTER_ID = "dept_semester_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ROLL_NO = "roll_no";
        public static final String COLUMN_ATTENDANCE_TYPE = "attendance_type";
    }
}

