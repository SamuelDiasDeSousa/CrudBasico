package com.br.samuel.crudbasico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.samuel.crudbasico.Banco.BancoController;
import com.br.samuel.crudbasico.Util.Mask;

public class Insert extends AppCompatActivity {

    //definindo os atributos
    private EditText edtName, edtPhone, edtMail;
    private Button btnInsert;
    private TextWatcher phoneMask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        //identificando cada campo do layout
        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtMail = (EditText) findViewById(R.id.edtMail);

        btnInsert = (Button) findViewById(R.id.btnInsert);

        //criando uma mascara de telefone no edittext do telefone
        phoneMask = Mask.insert("(##)#####-####", edtPhone);
        //adicionando a mascara no editText
        edtPhone.addTextChangedListener(phoneMask);

        //Clique do botão inserir
        btnInsert.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                //Se validarPessoa for false prosseguimos na inserção dos dados de pessoa
                if(!validarPessoa(edtName, edtPhone, edtMail)){
                    //objeto bancocontroller, a classe BancoController é responsavel pelos crud
                    BancoController bancoController = new BancoController(getBaseContext());

                    //variaveis recebem os valores digitados no EditText
                    String nome = edtName.getText().toString();
                    String telefone = edtPhone.getText().toString();
                    String email = edtMail.getText().toString();

                    String resultado = bancoController.inserirDados(nome, telefone, email);

                    //mostrar mensagem ao inserir registro
                    Toast.makeText(Insert.this, resultado, Toast.LENGTH_LONG).show();

                    //Vai pra página inicial
                    Intent i = new Intent(Insert.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }

        });
        //Seta o titulo da barra superior
        getSupportActionBar().setTitle("Cadastro de Pessoa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Habilita a barra
        getSupportActionBar().setHomeButtonEnabled(true);//Habilita o botao de voltar

    }

    /**
     * Validar se todos os campos estão validos para inserção
     * OBSERVAÇÃO: Aqui o mais correto seria criar uma classe Pessoa com os devidos atributos de pessoa
     * e depois adicionar os dados destes campos em um metodo set, e assim inserir no banco,
     * mas criei aqui de uma forma mais simples para os alunos entender mais facil como funciona
     * o insert do sqlite
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
                Intent i = new Intent(Insert.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }
}
