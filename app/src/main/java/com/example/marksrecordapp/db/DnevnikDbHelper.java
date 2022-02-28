package com.example.marksrecordapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.marksrecordapp.utils.MyAppUtils;

import java.util.ArrayList;

public class DnevnikDbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "dnevnik.db";

    public DnevnikDbHelper(@Nullable Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Predmet.SQL_CREATE_TABLE_PREDMETI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static class Predmet implements BaseColumns {

        private Predmet(){}

        public static final String TABLE_NAME = "predmeti";
        public static final String COLUMN_NAZIV = "Naziv";
        public static final String COLUMN_OCENE = "Ocene";

        public static String SQL_CREATE_TABLE_PREDMETI = "CREATE TABLE "+TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_NAZIV + " TEXT UNIQUE, "+
                COLUMN_OCENE + " TEXT);";

        public static String SQL_DELETE_TABLE_PREDMETI = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    }

    //CRUD operacije


    //read SELECT
    @NonNull
    public ArrayList<String[]> getPremet(){
        ArrayList<String[]> izlaz = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                Predmet.TABLE_NAME,
                new String[]{Predmet.COLUMN_NAZIV,Predmet.COLUMN_OCENE},
                null,null,null,null,null
        );
        if(cursor.moveToFirst()){
            do {
                izlaz.add(new String[]{cursor.getString(0),cursor.getString(1)});
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return izlaz;
    }

    @Nullable
    public String[] getPredmet(String naziv){
        String[] izlaz = new String[2];
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                Predmet.TABLE_NAME,
                new String[]{Predmet.COLUMN_NAZIV,Predmet.COLUMN_OCENE},
                Predmet.COLUMN_NAZIV + " = ?",
                new String[]{naziv},
                null,null,null,null
        );
        if(cursor.moveToFirst()){
            izlaz[0]= cursor.getString(0);
            izlaz[1] = cursor.getString(1);
            cursor.close();
            db.close();
            return izlaz;
        } else {
            cursor.close();
            db.close();
            return null;
        }
    }


    //create INSERT INTO
    public void addPredmet(String naziv){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Predmet.COLUMN_NAZIV,naziv);
        values.put(Predmet.COLUMN_OCENE,"");
        db.insert(Predmet.TABLE_NAME,null,values);
        db.close();
    }

    //update UPDATE
    public boolean addOcena(String naziv, int ocena){
        if(ocena<1 || ocena>5) return false;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(
                Predmet.TABLE_NAME,
                new String[]{Predmet.COLUMN_OCENE},
                Predmet.COLUMN_NAZIV + " = ?",
                new String[]{naziv},
                null, null, null);
        String ocene_str ="";
        if(cursor.moveToFirst()){
            ocene_str = cursor.getString(0);
            int[] ocena_int = MyAppUtils.getArrayOcena(ocene_str);
            int index = MyAppUtils.findFirstIndexOfIntArray(ocena_int,0);
            ocena_int[index] = ocena;
            ocene_str = MyAppUtils.getStringArray(ocena_int);
        } else {
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        ContentValues values = new ContentValues();
        values.put(Predmet.COLUMN_OCENE,ocene_str);
        db.update(
                Predmet.TABLE_NAME,
                values,
                Predmet.COLUMN_NAZIV + " = ? ",
                new String[]{naziv}

        );
        db.close();
        return true;
    }

    //update " 4 5 3" index = 2 -> " 4 5"
    public boolean deleteOcena(String naziv, int index){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(
                Predmet.TABLE_NAME,
                new String[]{Predmet.COLUMN_OCENE},
                Predmet.COLUMN_NAZIV + " = ?",
                new String[]{naziv},
                null, null, null);
        String ocene_str ="";
        if(cursor.moveToFirst()){
            ocene_str = cursor.getString(0);
            int[] ocena_int = MyAppUtils.getArrayOcena(ocene_str);
            if(index >= ocena_int.length)  {
                cursor.close();
                db.close();
                return false;
            }
            System.arraycopy(ocena_int,index+1,ocena_int, index,ocena_int.length-index-1);
            ocene_str = MyAppUtils.getStringArray(ocena_int);
        } else {
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        ContentValues values = new ContentValues();
        values.put(Predmet.COLUMN_OCENE,ocene_str);
        db.update(
                Predmet.TABLE_NAME,
                values,
                Predmet.COLUMN_NAZIV + " = ? ",
                new String[]{naziv}

        );
        db.close();
        return true;
    }

    //DELETE FROM
    public void deletePredmet(String naziv){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Predmet.TABLE_NAME, Predmet.COLUMN_NAZIV + " = ? ", new String[]{naziv});
        db.close();
    }

}
