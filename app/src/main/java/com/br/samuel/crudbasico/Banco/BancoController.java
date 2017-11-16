package com.br.samuel.crudbasico.Banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMUEL on 15/11/2017.
 *
 *
 * Aqui é responsavel por controlar os comandos DML, ou o famoso CRUD, melhor dizendo
 */

public class BancoController {

    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoController(Context context){
        banco = new CriaBanco(context);
    }

    //metodo para inserir os dados na tabela pessoas
    public String inserirDados(String nome, String telefone, String email){
        ContentValues contentValues;
        long resultado;

        //para inserir dados no banco usamos um metodo pra escrever (getWritableDatabase())
        db = banco.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(CriaBanco.NOME, nome);
        contentValues.put(CriaBanco.TELEFONE, telefone);
        contentValues.put(CriaBanco.EMAIL, email);

        //metodo do SQLite para inserir
        resultado = db.insert(CriaBanco.TABELA, null, contentValues);
        //fechando o banco
        db.close();

        //mensagem
        if (resultado ==-1){
            return "Erro ao inserir registro!";
        }else{
            return "Registro Inserido com sucesso!";
        }

    }


    public Cursor listarPessoas(){
        //inserindo nomes dos campos em um array
        String[] campos =  {banco.ID, banco.NOME, banco.TELEFONE, banco.EMAIL};

        //Vamos ler dados
        db = banco.getReadableDatabase();

        //Query usada para ler campos
        Cursor cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        //Se a query acima nao for nula, o dado encontrado fica em primeiro
        if(cursor!=null){
            cursor.moveToFirst();
        }

        //Fechamos o banco
        db.close();

        //Retornamos dado encontrado
        return cursor;
    }

    public Cursor mostrarDadosById(int id){
        String[] campos =  {banco.ID,banco.NOME,banco.TELEFONE,banco.EMAIL};
        String where = CriaBanco.ID + "=" + id;
        db = banco.getReadableDatabase();
        Cursor cursor = db.query(CriaBanco.TABELA,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public String atualizarDados(int id, String nome, String telefone, String email){
        ContentValues contentValues;
        String where;
        long resultado;

        db = banco.getWritableDatabase();

        where = CriaBanco.ID + "=" + id;

        contentValues = new ContentValues();
        contentValues.put(CriaBanco.NOME, nome);
        contentValues.put(CriaBanco.TELEFONE, telefone);
        contentValues.put(CriaBanco.EMAIL, email);

        resultado = db.update(CriaBanco.TABELA,contentValues,where,null);
        db.close();

        //mensagem
        if (resultado ==-1){
            return "Erro ao atualizar registro!";
        }else{
            return "Registro atualizado com sucesso!";
        }
    }

    public String deletarDados(int id){
        long resultado;

        String where = CriaBanco.ID + "=" + id;
        db = banco.getReadableDatabase();
        resultado = db.delete(CriaBanco.TABELA,where,null);
        db.close();

        //mensagem
        if (resultado ==-1){
            return "Erro ao deletar registro!";
        }else{
            return "Registro deletado com sucesso!";
        }
    }




}


