package onlinemarketing.net.sudanjobnet.helper;

/**
 * Created by muawia.ibrahim on 3/9/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



import java.util.ArrayList;
import java.util.HashMap;

import onlinemarketing.net.sudanjobnet.Model.JobItems;


public class SqlHandler {

    public static final String ID = "_id";

    public static final String TAG_PID = "pid";

    public  static final String DATBASE_NAME = "job_DB";
    public  static final String TABLE_NAME = "jobTab";
    public  static final String TABLE_NAME_Learn = "job_learn";
    public  static final int DATABASE_VERSION = 1;
    public static final String TAG_PID_learn = "pid_learn";
    public 	static final String TAG_TITLE_LEARN= "title_learn";
    public  static final String TAG_COMPANY_NAME_LEARN = "company_name_learn";
    public  static final String TAG_CLOSING_LEARN= "closing_learn";
    public 	static final String TAG_TITLE = "title";
    public  static final String TAG_COMPANY_NAME = "company_name";
    public  static final String TAG_CLOSING= "closing";
    public  static final String TAG_Posting= "posted_on";
    public  static final String TAG_BACKGROUND= "background";
    public  static final String TAG_PAGER = "logo";
    public  static final String TAG_responsibilities = "responsibilities";
    public  static final String TAG_qualification = "qualification";
    public  static final String TAG_footer = "footer";
    public  static final String TAG_city = "city";
    private DbHelper ourHelper;
    private final Context ourCTX;
    public SQLiteDatabase ourDb;

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATBASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create the database using SQL
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT ," + TAG_PID + " TEXT ,"
                    + TAG_TITLE + " TEXT , " + TAG_COMPANY_NAME + " TEXT , " + TAG_CLOSING + " TEXT , " + TAG_city + " TEXT , " + TAG_footer+ " TEXT , " + TAG_PAGER + " TEXT);");

            //	db.execSQL("CREATE TABLE " + TABLE_NAME1 + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," + TAG_PID + " TEXT," + TAG_LOGO + " TEXT," + TAG_SPONSOR + " TEXT);");
            db.execSQL("CREATE TABLE " + TABLE_NAME_Learn + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," + TAG_PID_learn + " TEXT,"
                    + TAG_TITLE_LEARN + " TEXT, " + TAG_COMPANY_NAME_LEARN + " TEXT , " + TAG_CLOSING_LEARN + " TEXT);");



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // what happens when the database is upgraded
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            onCreate(db);

        }

    }

    public SqlHandler(Context c) {
        ourCTX = c;
    }

    public SqlHandler open() throws SQLException {
        ourHelper = new DbHelper(ourCTX);
        ourDb = ourHelper.getWritableDatabase();
        return this;
    }
    public SqlHandler open_read() throws SQLException {
        ourHelper = new DbHelper(ourCTX);
        ourDb = ourHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    public void FillData(String pid, String title, String company_name, String closing,String pager) {
        open();

        ContentValues cv = new ContentValues();

        cv.put(TAG_PID, pid);
        cv.put(TAG_TITLE, title);
        cv.put(TAG_COMPANY_NAME, company_name);
        cv.put(TAG_CLOSING, closing);
        cv.put(TAG_PAGER, pager);


        if (!isAlreadyInserted(pid)){
            long insert=ourDb.insert(TABLE_NAME, null, cv);
            Log.e("DB", "insterted"+insert);
        }
        else{
            long update=ourDb.update(TABLE_NAME, cv, TAG_PID + " = " + pid, null);
            Log.e("DB", "update"+update)	;
        }
        ourDb.close();

    }
    private boolean isAlreadyInserted(String pid) {

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + TAG_PID + " = " + pid;
            cursor = ourDb.rawQuery(selectQuery, null);
            return cursor != null && cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

            if (cursor != null)
                cursor.close();
        }
    }
    public void FillData2(String pid, String title, String company_name, String closing, String posted_on) {
        open();

        ContentValues cv = new ContentValues();

        cv.put(TAG_PID, pid);
        cv.put(TAG_TITLE, title);
        cv.put(TAG_COMPANY_NAME, company_name);
        cv.put(TAG_CLOSING, closing);
        cv.put(TAG_Posting,posted_on);



        if (!isAlreadyInserted(pid)){
            long insert=ourDb.insert(TABLE_NAME_Learn, null, cv);
            Log.e("DB", "insterted"+insert);
        }
        else{
            long update=ourDb.update(TABLE_NAME_Learn, cv, TAG_PID_learn + " = " + pid, null);
            Log.e("DB", "update"+update)	;
        }
        ourDb.close();

    }



    public ArrayList<HashMap<String, String>> getData1() {
        // read from Db
        ArrayList<HashMap<String, String>> jobList = new ArrayList<HashMap<String, String>>();
        open();
        Cursor c = ourDb.rawQuery("select * from " + TABLE_NAME, null);
        int id = c.getColumnIndex(ID);
        int pid = c.getColumnIndex(TAG_PID);
        int title = c.getColumnIndex(TAG_TITLE);
        int company_name = c.getColumnIndex(TAG_COMPANY_NAME);
        int closing = c.getColumnIndex(TAG_CLOSING);
        int pager =c.getColumnIndex(TAG_PAGER);

        Log.e("DB", "cusror count" + c.getCount());
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(ID, "" + c.getInt(id));
                map.put(TAG_PID, c.getString(pid));
                map.put(TAG_TITLE, c.getString(title));
                map.put(TAG_COMPANY_NAME, c.getString(company_name));
                map.put(TAG_CLOSING, c.getString(closing));
                map.put(TAG_PAGER, c.getString(pager));
                jobList.add(map);
                c.moveToNext();
            }
        }

        if (c != null)
            c.close();

        ourDb.close();

        return jobList;
    }



    public void updateData(String pid, String title1, String companyname, String closing1, String background1, String city1, String qualification1 , String responsibilities1 ,String footer1) {
        open();

        ContentValues cv = new ContentValues();

        cv.put(TAG_PID, pid);
        cv.put(TAG_TITLE, title1);
        cv.put(TAG_COMPANY_NAME, companyname);
        cv.put(TAG_CLOSING, closing1);
        cv.put(TAG_BACKGROUND, background1);
        cv.put(TAG_city, city1);
        cv.put(TAG_qualification, qualification1);
        cv.put(TAG_responsibilities, responsibilities1);
        cv.put(TAG_footer, footer1);
        if (!isAlreadyInserted(pid))
            ourDb.insert(TABLE_NAME, null, cv);
        else
            ourDb.update(TABLE_NAME, cv, TAG_PID + " = " + pid, null);
        ourDb.close();
    }




    public HashMap<String, String> getData(String pidData) {
        open();
        HashMap<String, String> map = new HashMap<String, String>();
        Cursor c = ourDb.rawQuery("select * from " + TABLE_NAME+ " WHERE "+TAG_PID+" = "+pidData , null);
        int id = c.getColumnIndex(ID);
        int pid = c.getColumnIndex(TAG_PID);
        int name = c.getColumnIndex(TAG_TITLE);
        int image = c.getColumnIndex(TAG_COMPANY_NAME);
        int closing = c.getColumnIndex(TAG_CLOSING);
        int pager =c.getColumnIndex(TAG_PAGER);
        int city =c.getColumnIndex(TAG_city);
        int qualification =c.getColumnIndex(TAG_qualification);
        int respons =c.getColumnIndex(TAG_responsibilities);
        int footer =c.getColumnIndex(TAG_footer);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();

            map.put(ID, "" + c.getInt(id));
            map.put(TAG_PID, c.getString(pid));
            map.put(TAG_TITLE, c.getString(name));
            map.put(TAG_COMPANY_NAME, c.getString(image));
            map.put(TAG_CLOSING, c.getString(closing));
            map.put(TAG_PAGER, c.getString(pager));
            map.put(TAG_city,c.getString(city));
            map.put(TAG_qualification, c.getString(qualification));
            map.put(TAG_responsibilities,c.getString(respons));
            map.put(TAG_footer, c.getString(footer));

        }

        if (c != null)
            c.close();

        ourDb.close();

        return map;
    }

    public ArrayList<JobItems> getJobData() {
        // read from Db
        ArrayList<JobItems> jobList = new ArrayList<>();
        open();
        Cursor c = ourDb.rawQuery("select * from " + TABLE_NAME, null);
        int id = c.getColumnIndex(ID);
        int pid = c.getColumnIndex(TAG_PID);
        int title = c.getColumnIndex(TAG_TITLE);
        int company_name = c.getColumnIndex(TAG_COMPANY_NAME);
        int closing = c.getColumnIndex(TAG_CLOSING);
        int clogo =c.getColumnIndex(TAG_PAGER);
        int pager =c.getColumnIndex(TAG_PAGER);

        Log.e("DB", "cusror count" + c.getCount());
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
               JobItems jobItems= new JobItems();
                jobItems.setPid(c.getString(pid));
                jobItems.setTitle(c.getString(title));
                jobItems.setCompany_name(c.getString(company_name));
                jobItems.setClosing(c.getString(closing));
                jobItems.setClogo(c.getString(clogo));
               // jobList.add(id, String.valueOf(c.getInt(id)));
              //  jobList.add(pid, c.getString(pid));
              //  jobList.add(title, c.getString(title));
              //  jobList.add(company_name, c.getString(company_name));
              //  jobList.add(closing, c.getString(closing));
              //  map.put(ID, "" + c.getInt(id));
              //  map.put(TAG_PID, c.getString(pid));
               // map.put(TAG_TITLE, c.getString(title));
               // map.put(TAG_COMPANY_NAME, c.getString(company_name));
               // map.put(TAG_CLOSING, c.getString(closing));
             //   map.put(TAG_PAGER, c.getString(pager));
                jobList.add(jobItems);
                c.moveToNext();
            }
        }

        if (c != null)
            c.close();

        ourDb.close();

        return jobList;
    }

    public void FillDetails(String pid, String title, String company_name, String closing,String city,String footer) {
        open();

        ContentValues cv = new ContentValues();

       cv.put(TAG_PID,pid);
        cv.put(TAG_TITLE,title);
        cv.put(TAG_COMPANY_NAME,company_name);
        cv.put(TAG_CLOSING,closing);
        cv.put(TAG_city,city);
        cv.put(TAG_footer, footer);


        if (!isAlreadyInserted(pid)){
            long insert=ourDb.insert(TABLE_NAME, null, cv);
            Log.e("DB", "insterted"+insert);
        }
        else{
            long update=ourDb.update(TABLE_NAME, cv, TAG_PID + " = " + pid, null);
            Log.e("DB", "update"+update)	;
        }
        ourDb.close();

    }
    private boolean isAlreadyInserted1(String pid) {

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + TAG_PID + " = " + pid;
            cursor = ourDb.rawQuery(selectQuery, null);
            return cursor != null && cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

            if (cursor != null)
                cursor.close();
        }
    }
    public ArrayList<JobItems> getJobDetails(String pid) {
        // read from Db
        ArrayList<JobItems> jobList = new ArrayList<>();
        open();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + TAG_PID + " = " + pid;
        Cursor c = ourDb.rawQuery(selectQuery,null);
        int id = c.getColumnIndex(ID);
       int pid1 = c.getColumnIndex(TAG_PID);
        int title = c.getColumnIndex(TAG_TITLE);
        int company_name = c.getColumnIndex(TAG_COMPANY_NAME);
        int city = c.getColumnIndex(TAG_city);
        int closing = c.getColumnIndex(TAG_CLOSING);
        int footer = c.getColumnIndex(TAG_footer);


        Log.e("DB", "cusror count" + c.getCount());
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                JobItems jobItems= new JobItems();
                jobItems.setPid(c.getString(pid1));
                jobItems.setFooter(c.getString(title));
                jobItems.setCompany_name(c.getString(company_name));
                jobItems.setCity(c.getString(city));
                jobItems.setClosing(c.getString(closing));
                jobItems.setFooter(c.getString(footer));

                // jobList.add(id, String.valueOf(c.getInt(id)));
                //  jobList.add(pid, c.getString(pid));
                //  jobList.add(title, c.getString(title));
                //  jobList.add(company_name, c.getString(company_name));
                //  jobList.add(closing, c.getString(closing));
                //  map.put(ID, "" + c.getInt(id));
                //  map.put(TAG_PID, c.getString(pid));
                // map.put(TAG_TITLE, c.getString(title));
                // map.put(TAG_COMPANY_NAME, c.getString(company_name));
                // map.put(TAG_CLOSING, c.getString(closing));
                //   map.put(TAG_PAGER, c.getString(pager));
                jobList.add(jobItems);
                c.moveToNext();
            }
        }

        if (c != null)
            c.close();

        ourDb.close();

        return jobList;
    }
}