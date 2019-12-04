package com.every.evebible;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BiDBHelp extends SQLiteOpenHelper {
    public BiDBHelp(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE if not exists bibles (idx int NOT NULL, cate int NOT NULL, book int NOT NULL, chapter int NOT NULL,paragraph int NOT NULL, sentence text NOT NULL, testament text NOT NULL,long_label text NOT NULL,short_label text NOT NULL)");
        db.execSQL("create table if not exists favorites (bible text not null , chapter int not null, verse int not null, sentence text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table if exists bible");
        onCreate(db);
    }
}
