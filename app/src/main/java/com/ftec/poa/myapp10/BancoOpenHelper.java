package com.ftec.poa.myapp10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoOpenHelper extends SQLiteOpenHelper {

    public BancoOpenHelper(Context context) {
        super(context, "listadecompras.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String comando = "CREATE TABLE itens (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descricao TEXT)";
        db.execSQL(comando);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
