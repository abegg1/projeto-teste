package com.ftec.poa.myapp10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListaDAO {

    private SQLiteDatabase bd;
    private BancoOpenHelper openHelper;

    public ListaDAO(Context contexto) {
        openHelper = new BancoOpenHelper(contexto);
    }

    public void abrir() {
        bd = openHelper.getWritableDatabase();
    }

    public void fechar() {
        bd.close();
    }

    public Item criarItem(String descricao) {
        ContentValues valores = new ContentValues();
        valores.put("descricao", descricao);
        long id = bd.insert("itens", null, valores);
        Item item = new Item();
        item.setId(id);
        item.setDescricao(descricao);
        return item;
    }

    public void removerItem(Item item) {
        long id = item.getId();
        bd.delete("itens", "_id = " + id, null);
    }
    public List<Item> lerItens() {
        List<Item> itens = new ArrayList<Item> ();
        Cursor cursor = bd.query("itens", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Item i = new Item();
            i.setId(cursor.getInt(0));
            i.setDescricao(cursor.getString(1));
            itens.add(i);
        }
        cursor.close();
        return itens;
    }
}
