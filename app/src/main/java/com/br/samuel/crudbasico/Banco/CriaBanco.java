package com.br.samuel.crudbasico.Banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SAMUEL on 15/11/2017.
 *
 *
 * Aqui se cria o banco, tabela(s) e atualiza o banco caso precise
 */

public class CriaBanco extends SQLiteOpenHelper {

    //Definindo o banco (DDL)
    private static final String NOME_BANCO = "banco.db";
    public static final String TABELA = "pessoas";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String TELEFONE = "telefone";
    public static final String EMAIL = "email";
    private static final int VERSAO = 1;

    //Construtor , usamos parar executar comandos DML
    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    //Criando o banco
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+"("
                + ID + " integer primary key autoincrement,"
                + NOME + " text,"
                + TELEFONE + " text,"
                + EMAIL + " text"
                +")";
        db.execSQL(sql);
    }

    //Aqui é onde acontence atualização no banco, um exemplo aqui citado é excluir a tabela pessoas se existir
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABELA);
        onCreate(db);
    }
}
