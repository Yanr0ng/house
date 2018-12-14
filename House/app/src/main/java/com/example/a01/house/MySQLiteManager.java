package com.example.a01.house;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteManager extends SQLiteOpenHelper {

    private final static int DB_VERSION = 1; // 資料庫版本
    private final static String DB_NAME = "MySQLite.db"; //資料庫名稱，
    private final static String Cuser_TABLE= "Cuser_TABLE";
    private final static String COL1="cid";
    private final static String COL2 = "cuserid"; //欄位名稱
    private final static String COL3= "cname"; //欄位名稱
    private final static String COL4 = "cphone"; //欄位名稱


    public MySQLiteManager (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 每次使用將會檢查是否有無資料表，若無，則會建立資料表


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + Cuser_TABLE+ " ("
                + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "  //這個屬性可以讓每次新增一筆資料，自動累加
                + COL2+" TEXT ,"
                + COL3+ " TEXT, "
                + COL4 + " TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS "+Cuser_TABLE);
        onCreate(db);

    }
    public boolean insterInfo(String cname, String cphone, String cuserid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, cname);
        contentValues.put(COL3, cuserid);
        contentValues.put(COL4, cphone);

        long result = db.insert(Cuser_TABLE, null, contentValues);
        if (result==-1){
            return  false;
        }else {
            return true;
        }
    }
    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Cuser_TABLE,null);
        return res;
    }

}
