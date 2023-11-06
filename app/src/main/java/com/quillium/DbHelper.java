package com.quillium;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, DbConstants.DATABASE_NAME, null, DbConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create table on database
        sqLiteDatabase.execSQL(DbConstants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //upgrade table if any structure change database

        //drop table if exists
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DbConstants.TABLE_NAME);

        //create table again
        onCreate(sqLiteDatabase);
    }

    //Insert Function to inert data on database
    public long insertRegisterData(String student_id, String hsc_roll, String dob){
        //get writable data to write data in db
        SQLiteDatabase db = this.getWritableDatabase();

        //create ContentValue class object to save data
        ContentValues contentValues = new ContentValues();

        //id will save automatically as we write query
        contentValues.put(DbConstants.C_STUDENT_ID,student_id);
        contentValues.put(DbConstants.C_HSC_ROLL,hsc_roll);
        contentValues.put(DbConstants.C_DOB,dob);


        //Insert data in row, It will return id of record
        long id = db.insert(DbConstants.TABLE_NAME,null,contentValues);

        //Close Db
        db.close();

        //return id
        return id;
    }
}
