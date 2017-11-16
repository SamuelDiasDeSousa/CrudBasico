package com.br.samuel.crudbasico;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.samuel.crudbasico.Banco.BancoController;
import com.br.samuel.crudbasico.Banco.CriaBanco;
import com.br.samuel.crudbasico.Util.Mask;

public class UpdateDelete extends AppCompatActivity {

    private EditText edtNameUpdate, edtPhoneUpdate, edtMailUpdate;
    private TextWatcher phoneMask;
    private Button btnUpdate, btnDeletar;
    private Cursor cursor;
    private BancoController bancoController;
    private String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        codigo = this.getIntent().getStringExtra("codigo");

        bancoController = new BancoController(getBaseContext());

        edtNameUpdate = (EditText)findViewById(R.id.edtNameUpdate);
        edtPhoneUpdate = (EditText)findViewById(R.id.edtPhoneUpdate);

        //criando uma mascara de telefone no edittext do telefone
        phoneMask = Mask.insert("(##)#####-####", edtPhoneUpdate);
        //adicionando a mascara no editText
        edtPhoneUpdate.addTextChangedListener(phoneMask);

        edtMailUpdate = (EditText)findViewById(R.id.edtMailUpdate);

        cursor = bancoController.mostrarDadosById(Integer.parseInt(codigo));
        edtNameUpdate.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.NOME)));
        edtPhoneUpdate.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.TELEFONE)));
        edtMailUpdate.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.EMAIL)));

        btnUpdate = (Button)findViewById(R.id.btnUpdate);

        //Clique do botão atualizar
        btnUpdate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                //Se validarPessoa for false prosseguimos na inserção dos dados de pessoa
                if(!validarPessoa(edtNameUpdate, edtPhoneUpdate, edtMailUpdate)){
                    //objeto bancocontroller, a classe BancoController é responsavel pelos crud
                    BancoController bancoController = new BancoController(getBaseContext());

                    //variaveis recebem os valores digitados no EditText
                    String resultado = bancoController.atualizarDados(Integer.parseInt(codigo),
                            edtNameUpdate.getText().toString(),edtPhoneUpdate.getText().toString(),
                            edtMailUpdate.getText().toString());

                    //mostrar mensagem ao atualizar registro
                    Toast.makeText(UpdateDelete.this, resultado, Toast.LENGTH_LONG).show();

                    //Vai pra página inicial
                    Intent i = new Intent(UpdateDelete.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }

        });

        //clique do botão deletar
        btnDeletar = (Button)findViewById(R.id.btnDelete);
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BancoController bancoController = new BancoController(getBaseContext());
                String resultado = bancoController.deletarDados(Integer.parseInt(codigo));
                //mostrar mensagem ao deletar registro
                Toast.makeText(UpdateDelete.this, resultado, Toast.LENGTH_LONG).show();

                //Vai direto pra página inicial
                Intent intent = new Intent(UpdateDelete.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //Seta o titulo da barra superior
        getSupportActionBar().setTitle("Cadastro de Pessoa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Habilita a barra
        getSupportActionBar().setHomeButtonEnabled(true);//Habilita o botao de voltar
    }

    /**
     * Validar se todos os campos estão validos para atualização
     */
    public boolean validarPessoa(EditText edtName, EditText edtPhone, EditText edtMail){
        //Se erro for false , iremos inserir os dados no tabela
        boolean erro = false;
        //se nome for vazio ou menor que 3 caracteres = erro
        if(edtName.getText().length() < 3){
            erro = true;
            edtName.setError("Campo nome não pode ser menor que 3 caracteres!");
        }

        //validação phone
        //telefone com menos de 14 caracteres contando com a mascara = erro
        if(edtPhone.getText().length() < 14){
            erro = true;
            edtPhone.setError("Preencha o campo telefone completamente!");
        }

        //validação mail
        String mailValid = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        //email invalido = erro
        if(!edtMail.getText().toString().trim().matches(mailValid)){
            erro = true;
            edtMail.setError("Email inválido!");
        }

        return erro;
    }

    //Habilita a função de voltar , caso a outra tela nao esteja finalizada
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent i = new Intent(UpdateDelete.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }
}
