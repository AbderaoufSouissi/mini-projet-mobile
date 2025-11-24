package com.smartexpense.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "smartexpense.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_EXPENSES = "expenses";

    // Users Table Columns
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_SECURITY_QUESTION = "security_question";
    public static final String COLUMN_USER_SECURITY_ANSWER = "security_answer";

    // Expenses Table Columns
    public static final String COLUMN_EXPENSE_ID = "id";
    public static final String COLUMN_EXPENSE_USER_ID = "user_id";
    public static final String COLUMN_EXPENSE_AMOUNT = "amount";
    public static final String COLUMN_EXPENSE_CATEGORY = "category";
    public static final String COLUMN_EXPENSE_DESCRIPTION = "description";
    public static final String COLUMN_EXPENSE_DATE = "date";

    // Create Users Table SQL
    private static final String CREATE_TABLE_USERS =
        "CREATE TABLE " + TABLE_USERS + "(" +
        COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        COLUMN_USER_USERNAME + " TEXT," +
        COLUMN_USER_EMAIL + " TEXT UNIQUE," +
        COLUMN_USER_PASSWORD + " TEXT," +
        COLUMN_USER_SECURITY_QUESTION + " TEXT," +
        COLUMN_USER_SECURITY_ANSWER + " TEXT" +
        ")";

    // Create Expenses Table SQL
    private static final String CREATE_TABLE_EXPENSES =
        "CREATE TABLE " + TABLE_EXPENSES + "(" +
        COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        COLUMN_EXPENSE_USER_ID + " INTEGER," +
        COLUMN_EXPENSE_AMOUNT + " REAL," +
        COLUMN_EXPENSE_CATEGORY + " TEXT," +
        COLUMN_EXPENSE_DESCRIPTION + " TEXT," +
        COLUMN_EXPENSE_DATE + " INTEGER," +
        "FOREIGN KEY(" + COLUMN_EXPENSE_USER_ID + ") REFERENCES " +
        TABLE_USERS + "(" + COLUMN_USER_ID + ")" +
        ")";

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_EXPENSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}

