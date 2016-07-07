package vn.fstyle.realmandroiddemo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vn.fstyle.realmandroiddemo.models.TestRealmObj;

/**
 * DatabaseHandler.
 *
 * @author DaoLQ
 */
public class SQLiteHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "testSqlite";

    private static final String TABLE_RECORDS = "records";

    private static final String KEY_ID = "id";
    private static final String KEY_VALUE = "name";

    private SQLiteDatabase mDatabase;

    private long mTimeStart;
    private long mTimeEnd;


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECORDS_TABLE = "CREATE TABLE " + TABLE_RECORDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_VALUE + " TEXT" + ")";
        db.execSQL(CREATE_RECORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
        onCreate(db);
    }

    public void beginTransaction() {
        mTimeStart = getTimeCurrent();
        mDatabase = this.getWritableDatabase();
    }

    public void closeTransaction() {
        mDatabase.close();
        mTimeEnd = getTimeCurrent();
    }

    private long getTimeCurrent() {
        return System.currentTimeMillis();
    }

    public long getDuration() {
        return mTimeEnd - mTimeStart;
    }

    public void insert(TestRealmObj obj) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, obj.getId());
        values.put(KEY_VALUE, obj.getName());

        mDatabase.insert(TABLE_RECORDS, null, values);
    }

    public int setlect() {
        int i = 0;
        Cursor c = mDatabase.rawQuery("SELECT * FROM " + DATABASE_NAME, null);
        c.moveToFirst();
        if (!c.moveToFirst()) {
            return 0;
        }
        do {
            i++;
        } while (c.moveToNext());
        c.close();
        return i;
    }

    public void update(TestRealmObj obj) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, obj.getId());
        values.put(KEY_VALUE, obj.getName());

        mDatabase.update(TABLE_RECORDS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(obj.getId())});
    }

    public void delete(TestRealmObj obj) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, obj.getId());
        values.put(KEY_VALUE, obj.getName());

        mDatabase.delete(TABLE_RECORDS, KEY_ID + " = ?",
                new String[]{String.valueOf(obj.getId())});
    }
}
