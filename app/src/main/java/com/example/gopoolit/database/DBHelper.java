package com.example.gopoolit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gopoolit.model.ArticlesItem;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "contact";
    public static final String CONTACTS_TABLE_TITLE = "title";
    public static final String CONTACTS_COLUMN_DESC = "description";
    public static final String CONTACTS_COLUMN_URL = "url";
    public static final String CONTACTS_COLUMN_ID = "id";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + CONTACTS_TABLE_NAME + "("
                + CONTACTS_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + CONTACTS_TABLE_TITLE + " TEXT,"
                + CONTACTS_COLUMN_DESC + " TEXT,"
                + CONTACTS_COLUMN_URL + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(db);
    }

    public void insertContact(ArrayList<ArticlesItem> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < list.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(CONTACTS_TABLE_TITLE, list.get(i).getTitle());
            contentValues.put(CONTACTS_COLUMN_DESC, list.get(i).getDescription());
            contentValues.put(CONTACTS_COLUMN_URL, list.get(i).getUrlToImage());
            db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        }

        db.close();
    }

    // code to get all contacts in a list view
    public ArrayList<ArticlesItem> getAllContacts() {

        String selectQuery = "SELECT  * FROM " + CONTACTS_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<ArticlesItem> dataList = new ArrayList<>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ArticlesItem contact = new ArticlesItem();
                contact.setTitle(cursor.getString(1));
                contact.setDescription(cursor.getString(2));
                contact.setUrlToImage(cursor.getString(3));
                dataList.add(contact);
            } while (cursor.moveToNext());
        }

        return dataList;
    }


}