package com.quillium;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        sqLiteDatabase.execSQL(DbConstants.CREATE_TEST_TABLE);

        createAnotherTable(); // Insert default values into the another table
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //upgrade table if any structure change database

        //drop table if exists
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DbConstants.TABLE_NAME_TEST_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DbConstants.TABLE_NAME_ANOTHER_TABLE);

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
        long id = db.insert(DbConstants.TABLE_NAME_TEST_TABLE,null,contentValues);

        //Close Db
        db.close();

        //return id
        return id;
    }

//    public String fetchData() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String result = null;
//
//        Cursor cursor = db.rawQuery("SELECT " + C_NEW_NAME + " FROM " + NEW_TABLE_NAME + " LIMIT 1", null);
//        if (cursor != null && cursor.moveToFirst()) {
//            result = cursor.getString(0); // Assuming data is a string in the first column
//            cursor.close();
//        }
//        db.close();
//
//        return result;
//    }

    // Method to create the new table with dummy data using constants
    public void createAnotherTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the new table doesn't exist or is empty
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DbConstants.TABLE_NAME_ANOTHER_TABLE, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();

            if (count == 0) { // Table is empty, populate with default data using constants
                ContentValues defaultValues = new ContentValues();
                defaultValues.put(DbConstants.C_SOMETHING, DbConstants.DEFAULT_SOMETHING);
                defaultValues.put(DbConstants.C_ANOTHER_COLUMN, DbConstants.DEFAULT_ANOTHER_COLUMN);

                // Insert default dummy data into the new table
                db.insert(DbConstants.TABLE_NAME_ANOTHER_TABLE, null, defaultValues);
            }
        }
        db.close();
    }
}
