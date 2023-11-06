package com.quillium;

public class DbConstants {

    // database or db name
    public static final String DATABASE_NAME = "QUILLIUM_DB";

    //database version
    public static final int DATABASE_VERSION = 1;

    // table name
    public static final String TABLE_NAME = "TEST_TABLE";


    // table column or field name
    public static final String C_ID = "ID";
    public static final String C_STUDENT_ID = "STUDENT_ID";
    public static final String C_HSC_ROLL = "HSC_ROLL";
    public static final String C_DOB = "DOB";
    public static final String C_STUDENT_MAIL = "STUDENT_MAIL";
    public static final String C_OTP = "OTP";
    public static final String C_PASSWORD = "PASSWORD";


    // query tor create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_STUDENT_ID + " TEXT, "
            + C_HSC_ROLL + " TEXT, "
            + C_DOB + " TEXT, "
            + C_STUDENT_MAIL + " TEXT, "
            + C_OTP + " TEXT, "
            +C_PASSWORD + " TEXT"
            + " );";
}
