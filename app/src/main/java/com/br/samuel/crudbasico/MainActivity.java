package com.br.samuel.crudbasico;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.br.samuel.crudbasico.Banco.BancoController;
import com.br.samuel.crudbasico.Banco.CriaBanco;

public class MainActivity extends AppCompatActivity {

    public Button btnCadastrar, btnRecarregar;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Esconder actionBar
        getSupportActionBar().hide();

        /*##########################INICIO DA LISTA############################*/
        //Pegamos o metodo listarpessoa la no controller
        BancoController bancoController = new BancoController(getBaseContext());
        cursor = bancoController.listarPessoas();


        //Pegamos array tanto dos campos , como os ids das activity
        String[] nomeCampos = new String[] {CriaBanco.ID, CriaBanco.NOME, CriaBanco.TELEFONE, CriaBanco.EMAIL};
        int[] idViews = new int[] {R.id.idPessoa, R.id.nomePessoa, R.id.phonePessoa, R.id.mailPessoa};


        //Criamos um adapter
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),R.layout.adapter_pessoa,cursor,nomeCampos,idViews, 0);


        //Recuperamos o listview
        ListView listaPessoa = (ListView)findViewById(R.id.listaPessoa);

        //Setamos o adapter no listview
        listaPessoa.setAdapter(adaptador);

        /*########################FIM DA LISTA####################################*/

        //Ao clicar em algum item da lista
        listaPessoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.ID));
                Intent intent = new Intent(MainActivity.this, UpdateDelete.class);
                intent.putExtra("codigo", codigo);
                startActivity(intent);
            }
        });

        //Chamando botão cadastrar
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnRecarregar = (Button) findViewById(R.id.btnRecarregar);

        //Ao clicar no botão cadastrar, é redirecionado a página insert sem finalizar esta página
        btnCadastrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Cadastrar", Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, Insert.class);
                startActivity(i);
            }
        });

        //Recarregar página
        btnRecarregar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = getIntent();
                finish();
                startActivity(i);
            }
        });

    }

}
