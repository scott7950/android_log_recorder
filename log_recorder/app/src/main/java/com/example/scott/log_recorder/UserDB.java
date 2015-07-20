package com.example.scott.log_recorder;

/**
 * Created by Scott on 7/18/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDB extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "user";
    private static final String KEY_USERID = "userId";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    private static final String DATABASE_NAME = "db.sqlite";
    private static final int DATABASE_VERSION = 1;

    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" + KEY_USERID + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ," +
                KEY_USERNAME + " VARCHAR NOT NULL ," + KEY_PASSWORD + " VARCHAR NOT NULL);";

        db.execSQL(CREATE_USER_TABLE);

        String CREATE_USER = "INSERT INTO " + TABLE_NAME +
                "(" + KEY_USERNAME + ", " + KEY_PASSWORD + ") VALUES ('scott', '123456');";

        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Getting User Count
    public boolean getUserCount(User user) {
        boolean result = false;

        String countQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE username = '" + user.getUsername() + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int dbDataCnt = cursor.getCount();

        if(dbDataCnt == 1) {
            result = true;
        } else if(dbDataCnt == 0) {
            user.update(User.UserDataType.ErrNoData);
        } else if(dbDataCnt > 1) {
            user.update(User.UserDataType.ErrMultiData);
        }

        cursor.close();

        return result;
    }

    //Add user
    public boolean addUser(User user) {
        boolean result = false;

        if(getUserCount(user)) {
            return false;
        } else {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_USERNAME, user.getUsername());
            values.put(KEY_PASSWORD, user.getPassword());

            // Inserting Row
            long ret = db.insert(TABLE_NAME, null, values);

            if(ret == -1) {
                user.setUserType(User.UserDataType.ErrSqlExec);
                result = false;
            } else {
                user.setUserType(User.UserDataType.VALID);
                result = true;
            }
        }

        return result;
    }

    //Auth user
    public boolean authUser(User user) {
        boolean result = false;

        if(!getUserCount(user)) {
            return false;
        } else {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_USERID}, KEY_USERNAME + "=? and " + KEY_PASSWORD + "=?",
                    new String[] { user.getUsername(), user.getPassword() }, null, null, null, null);

            if(cursor.getCount() != 1) {
                user.update(User.UserDataType.ErrNoData);
                result = false;
            } else {
                cursor.moveToFirst();
                user.update(User.UserDataType.VALID);
                result = true;
            }

            cursor.close();
        }

        return result;
    }

}
