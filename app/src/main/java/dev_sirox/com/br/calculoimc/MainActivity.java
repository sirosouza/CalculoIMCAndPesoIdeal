package dev_sirox.com.br.calculoimcepesoideal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.edtPeso)
    EditText edtPeso;
    @Bind(R.id.txtImcResultadoTitle)
    TextView txtImcResultadoTitle;
    @Bind(R.id.edtAltura)
    EditText edtAltura;
    @Bind(R.id.imgAltura)
    ImageView imgAltura;
    @Bind(R.id.edtIdade)
    EditText edtIdade;
    @Bind(R.id.imgIdade)
    ImageView imgIdade;
    @Bind(R.id.rdButtonSexo)
    RadioGroup rdButtonSexo;
    @Bind(R.id.imgSexo)
    ImageView imgSexo;
    @Bind(R.id.txtImcResultado)
    TextView txtResultado;
    @Bind(R.id.linearIMC)
    LinearLayout linearIMC;
    @Bind(R.id.txtRsultadoDescritivo)
    TextView txtResultadoDescritivo;
    @Bind(R.id.btnCalcularImc)
    Button btnCalcular;
    @Bind(R.id.btnSobreImc)
    Button btnSobre;

    private String sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("BEM VINDO");
        alertDialog.setMessage("Informe seu peso no campo:");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        ButterKnife.bind(this);
        edtPeso.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (textView.getText().length() == 0) {
                    Toast.makeText(MainActivity.this, "Insira o campo para continuar!", Toast.LENGTH_SHORT).show();
                } else {
                    edtAltura.setVisibility(View.VISIBLE);
                    imgAltura.setVisibility(View.VISIBLE);
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Altura e Idade:");
                    alertDialog.setMessage("Informe sua altura e idade nos campos:");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                return false;
            }
        });
        edtAltura.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (textView.getText().length() == 0) {
                    Toast.makeText(MainActivity.this, "Insira o campo para continuar!", Toast.LENGTH_SHORT).show();
                } else {
                    edtIdade.setVisibility(View.VISIBLE);
                    imgIdade.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        edtIdade.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (textView.getText().length() == 0) {
                    Toast.makeText(MainActivity.this, "Insira o campo para continuar!", Toast.LENGTH_SHORT).show();
                } else {
                    rdButtonSexo.setVisibility(View.VISIBLE);
                    imgSexo.setVisibility(View.VISIBLE);
                }

                return false;
            }
        });
        rdButtonSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                btnCalcular.setVisibility(View.VISIBLE);
                btnSobre.setVisibility(View.VISIBLE);
                int id = i;

                switch (id) {
                    case R.id.rdButtonMasc: {
                        sexo = "Masculino";
                        break;
                    }
                    case R.id.rdButtonFem: {
                        sexo = "Feminino";
                        break;
                    }
                    default:
                        Toast.makeText(MainActivity.this, "Erro inesperado", Toast.LENGTH_SHORT).show();
                        break;
                }
                Toast.makeText(MainActivity.this, sexo, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void calcularImc(View v) {
        txtImcResultadoTitle.setVisibility(View.VISIBLE);
        txtResultado.setVisibility(View.VISIBLE);
        txtResultadoDescritivo.setVisibility(View.VISIBLE);

        Double peso, altura, idade;
        Double resultadoImc = 0.0;
        String condicaoSaude = "";
        String descricaoSaude = "";

        peso = Double.parseDouble(edtPeso.getText().toString());
        altura = Double.parseDouble(edtAltura.getText().toString());
        idade = Double.parseDouble(edtIdade.getText().toString());

        resultadoImc = peso / (altura * 2);
        txtResultado.setText(String.format("%.2f", resultadoImc));
        switch (sexo) {
            case "Masculino": {
                if ((idade >= 20) && (idade <= 29)) {
                    //CondicaoSaude
                    if (resultadoImc < 5.2) {
                        condicaoSaude = "Muito Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica anorexia, bulimia, osteoporose e auto consumo de massa muscular";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 5.2) && (resultadoImc <= 9.3)) {
                        condicaoSaude = "Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica transtornos digestivos, debilidade, fadiga crónica, stress, ansiedade, e difusão das hormonais";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 9.4) && (resultadoImc <= 14.01)) {
                        condicaoSaude = "Muito Bom";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 14.02) && (resultadoImc <= 17.5)) {
                        condicaoSaude = "Saudável";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 17.6) && (resultadoImc <= 22.4)) {
                        condicaoSaude = "Sobrepeso";
                        descricaoSaude = "Acima do Peso! \nImplica fadiga, problemas digestivos, problemas circulatórios, mã circulação nas pernas e varizes";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 25.5) && (resultadoImc <= 29.2)) {
                        condicaoSaude = "Gordo";
                        descricaoSaude = "Obesidade 1! \nImplica diabetes, angina de peito, enfartes, trombo flebites, arteroscleroses, embolias, alterações menstruais.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if (resultadoImc > 29.3) {
                        condicaoSaude = "Muito Gordo";
                        descricaoSaude = "Obesidade 2! \nImplica falta de ar, apneia, sonolencia, trombose pulmonar, úlceras varicosas, cancro do cólon, uterino e mamário, refluxo esofágico, discriminação social, laboral e sexual.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                }
                if ((idade >= 30) && (idade <= 39)) {
                    if (resultadoImc < 9.2) {
                        condicaoSaude = "Muito Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica anorexia, bulimia, osteoporose e auto consumo de massa muscular";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 9.3) && (resultadoImc <= 14.0)) {
                        condicaoSaude = "Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica transtornos digestivos, debilidade, fadiga crónica, stress, ansiedade, e difusão das hormonais";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 14.1) && (resultadoImc <= 17.5)) {
                        condicaoSaude = "Muito Bom";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 17.6) && (resultadoImc <= 20.6)) {
                        condicaoSaude = "Saudável";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 20.7) && (resultadoImc <= 24.2)) {
                        condicaoSaude = "Sobrepeso";
                        descricaoSaude = "Acima do Peso! \nImplica fadiga, problemas digestivos, problemas circulatórios, mã circulação nas pernas e varizes";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 24.3) && (resultadoImc <= 30.0)) {
                        condicaoSaude = "Gordo";
                        descricaoSaude = "Obesidade 1! \nImplica diabetes, angina de peito, enfartes, trombo flebites, arteroscleroses, embolias, alterações menstruais.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if (resultadoImc > 30.1) {
                        condicaoSaude = "Muito Gordo";
                        descricaoSaude = "Obesidade 2! \nImplica falta de ar, apneia, sonolencia, trombose pulmonar, úlceras varicosas, cancro do cólon, uterino e mamário, refluxo esofágico, discriminação social, laboral e sexual.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                }
                if ((idade >= 40) && (idade <= 49)) {
                    if (resultadoImc < 11.5) {
                        condicaoSaude = "Muito Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica anorexia, bulimia, osteoporose e auto consumo de massa muscular";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 11.6) && (resultadoImc <= 16.3)) {
                        condicaoSaude = "Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica transtornos digestivos, debilidade, fadiga crónica, stress, ansiedade, e difusão das hormonais";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 16.4) && (resultadoImc <= 19.6)) {
                        condicaoSaude = "Muito Bom";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 19.7) && (resultadoImc <= 22.5)) {
                        condicaoSaude = "Saudável";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 22.6) && (resultadoImc <= 26.2)) {
                        condicaoSaude = "Sobrepeso";
                        descricaoSaude = "Acima do Peso! \nImplica fadiga, problemas digestivos, problemas circulatórios, mã circulação nas pernas e varizes";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 26.3) && (resultadoImc <= 31.4)) {
                        condicaoSaude = "Gordo";
                        descricaoSaude = "Obesidade 1! \nImplica diabetes, angina de peito, enfartes, trombo flebites, arteroscleroses, embolias, alterações menstruais.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if (resultadoImc > 31.5) {
                        condicaoSaude = "Muito Gordo";
                        descricaoSaude = "Obesidade 2! \nImplica falta de ar, apneia, sonolencia, trombose pulmonar, úlceras varicosas, cancro do cólon, uterino e mamário, refluxo esofágico, discriminação social, laboral e sexual.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                }
                if ((idade >= 50) && (idade <= 59)) {
                    if (resultadoImc < 12.9) {
                        condicaoSaude = "Muito Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica anorexia, bulimia, osteoporose e auto consumo de massa muscular";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 13.0) && (resultadoImc <= 18.1)) {
                        condicaoSaude = "Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica transtornos digestivos, debilidade, fadiga crónica, stress, ansiedade, e difusão das hormonais";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 18.2) && (resultadoImc <= 21.2)) {
                        condicaoSaude = "Muito Bom";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 21.3) && (resultadoImc <= 24.2)) {
                        condicaoSaude = "Saudável";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 24.3) && (resultadoImc <= 27.6)) {
                        condicaoSaude = "Sobrepeso";
                        descricaoSaude = "Acima do Peso! \nImplica fadiga, problemas digestivos, problemas circulatórios, mã circulação nas pernas e varizes";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 27.7) && (resultadoImc <= 32.4)) {
                        condicaoSaude = "Gordo";
                        descricaoSaude = "Obesidade 1! \nImplica diabetes, angina de peito, enfartes, trombo flebites, arteroscleroses, embolias, alterações menstruais.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if (resultadoImc > 32.5) {
                        condicaoSaude = "Muito Gordo";
                        descricaoSaude = "Obesidade 2! \nImplica falta de ar, apneia, sonolencia, trombose pulmonar, úlceras varicosas, cancro do cólon, uterino e mamário, refluxo esofágico, discriminação social, laboral e sexual.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                }
                if (idade >= 60) {
                    if (resultadoImc < 13.0) {
                        condicaoSaude = "Muito Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica anorexia, bulimia, osteoporose e auto consumo de massa muscular";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 13.1) && (resultadoImc <= 18.5)) {
                        condicaoSaude = "Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica transtornos digestivos, debilidade, fadiga crónica, stress, ansiedade, e difusão das hormonais";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 18.6) && (resultadoImc <= 22.0)) {
                        condicaoSaude = "Muito Bom";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 22.1) && (resultadoImc <= 25.0)) {
                        condicaoSaude = "Saudável";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 25.1) && (resultadoImc <= 28.4)) {
                        condicaoSaude = "Sobrepeso";
                        descricaoSaude = "Acima do Peso! \nImplica fadiga, problemas digestivos, problemas circulatórios, mã circulação nas pernas e varizes";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 28.5) && (resultadoImc <= 33.5)) {
                        condicaoSaude = "Gordo";
                        descricaoSaude = "Obesidade 1! \nImplica diabetes, angina de peito, enfartes, trombo flebites, arteroscleroses, embolias, alterações menstruais.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if (resultadoImc >= 33.6) {
                        condicaoSaude = "Muito Gordo";
                        descricaoSaude = "Obesidade 2! \nImplica falta de ar, apneia, sonolencia, trombose pulmonar, úlceras varicosas, cancro do cólon, uterino e mamário, refluxo esofágico, discriminação social, laboral e sexual.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                }
                break;
            }
            case "Feminino": {
                if ((idade >= 20) && (idade <= 29)) {
                    if (resultadoImc < 10.7) {
                        condicaoSaude = "Muito Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica anorexia, bulimia, osteoporose e auto consumo de massa muscular";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 10.8) && (resultadoImc <= 17.0)) {
                        condicaoSaude = "Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica transtornos digestivos, debilidade, fadiga crónica, stress, ansiedade, e difusão das hormonais";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 17.1) && (resultadoImc <= 20.5)) {
                        condicaoSaude = "Muito Bom";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 20.6) && (resultadoImc <= 23.8)) {
                        condicaoSaude = "Saudável";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 23.9) && (resultadoImc <= 27.6)) {
                        condicaoSaude = "Sobrepeso";
                        descricaoSaude = "Acima do Peso! \nImplica fadiga, problemas digestivos, problemas circulatórios, mã circulação nas pernas e varizes";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 27.7) && (resultadoImc <= 35.5)) {
                        condicaoSaude = "Gordo";
                        descricaoSaude = "Obesidade 1! \nImplica diabetes, angina de peito, enfartes, trombo flebites, arteroscleroses, embolias, alterações menstruais.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if (resultadoImc > 35.6) {
                        condicaoSaude = "Muito Gordo";
                        descricaoSaude = "Obesidade 2! \nImplica falta de ar, apneia, sonolencia, trombose pulmonar, úlceras varicosas, cancro do cólon, uterino e mamário, refluxo esofágico, discriminação social, laboral e sexual.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                }
                if ((idade >= 30) && (idade <= 39)) {
                    if (resultadoImc < 13.3) {
                        condicaoSaude = "Muito Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica anorexia, bulimia, osteoporose e auto consumo de massa muscular";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 13.4) && (resultadoImc <= 18.0)) {
                        condicaoSaude = "Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica transtornos digestivos, debilidade, fadiga crónica, stress, ansiedade, e difusão das hormonais";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 18.1) && (resultadoImc <= 21.8)) {
                        condicaoSaude = "Muito Bom";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 21.9) && (resultadoImc <= 24.8)) {
                        condicaoSaude = "Saudável";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 24.9) && (resultadoImc <= 30.0)) {
                        condicaoSaude = "Sobrepeso";
                        descricaoSaude = "Acima do Peso! \nImplica fadiga, problemas digestivos, problemas circulatórios, mã circulação nas pernas e varizes";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 30.1) && (resultadoImc <= 35.8)) {
                        condicaoSaude = "Gordo";
                        descricaoSaude = "Obesidade 1! \nImplica diabetes, angina de peito, enfartes, trombo flebites, arteroscleroses, embolias, alterações menstruais.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if (resultadoImc > 35.9) {
                        condicaoSaude = "Muito Gordo";
                        descricaoSaude = "Obesidade 2! \nImplica falta de ar, apneia, sonolencia, trombose pulmonar, úlceras varicosas, cancro do cólon, uterino e mamário, refluxo esofágico, discriminação social, laboral e sexual.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                }
                if ((idade >= 40) && (idade <= 49)) {
                    if (resultadoImc < 16.1) {
                        condicaoSaude = "Muito Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica anorexia, bulimia, osteoporose e auto consumo de massa muscular";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 16.2) && (resultadoImc <= 21.4)) {
                        condicaoSaude = "Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica transtornos digestivos, debilidade, fadiga crónica, stress, ansiedade, e difusão das hormonais";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 21.5) && (resultadoImc <= 25.1)) {
                        condicaoSaude = "Muito Bom";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 25.2) && (resultadoImc <= 28.3)) {
                        condicaoSaude = "Saudável";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 28.4) && (resultadoImc <= 32.1)) {
                        condicaoSaude = "Sobrepeso";
                        descricaoSaude = "Acima do Peso! \nImplica fadiga, problemas digestivos, problemas circulatórios, mã circulação nas pernas e varizes";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 32.2) && (resultadoImc <= 37.7)) {
                        condicaoSaude = "Gordo";
                        descricaoSaude = "Obesidade 1! \nImplica diabetes, angina de peito, enfartes, trombo flebites, arteroscleroses, embolias, alterações menstruais.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if (resultadoImc > 37.8) {
                        condicaoSaude = "Muito Gordo";
                        descricaoSaude = "Obesidade 2! \nImplica falta de ar, apneia, sonolencia, trombose pulmonar, úlceras varicosas, cancro do cólon, uterino e mamário, refluxo esofágico, discriminação social, laboral e sexual.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                }
                if ((idade >= 50) && (idade <= 59)) {
                    if (resultadoImc < 18.8) {
                        condicaoSaude = "Muito Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica anorexia, bulimia, osteoporose e auto consumo de massa muscular";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 18.9) && (resultadoImc <= 25.1)) {
                        condicaoSaude = "Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica transtornos digestivos, debilidade, fadiga crónica, stress, ansiedade, e difusão das hormonais";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 25.2) && (resultadoImc <= 28.6)) {
                        condicaoSaude = "Muito Bom";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 28.7) && (resultadoImc <= 32.5)) {
                        condicaoSaude = "Saudável";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 32.6) && (resultadoImc <= 35.6)) {
                        condicaoSaude = "Sobrepeso";
                        descricaoSaude = "Acima do Peso! \nImplica fadiga, problemas digestivos, problemas circulatórios, mã circulação nas pernas e varizes";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 35.7) && (resultadoImc <= 39.6)) {
                        condicaoSaude = "Gordo";
                        descricaoSaude = "Obesidade 1! \nImplica diabetes, angina de peito, enfartes, trombo flebites, arteroscleroses, embolias, alterações menstruais.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if (resultadoImc > 39.7) {
                        condicaoSaude = "Muito Gordo";
                        descricaoSaude = "Obesidade 2! \nImplica falta de ar, apneia, sonolencia, trombose pulmonar, úlceras varicosas, cancro do cólon, uterino e mamário, refluxo esofágico, discriminação social, laboral e sexual.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                }
                if (idade >= 60) {
                    if (resultadoImc < 19.1) {
                        condicaoSaude = "Muito Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica anorexia, bulimia, osteoporose e auto consumo de massa muscular";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 19.2) && (resultadoImc <= 25.0)) {
                        condicaoSaude = "Magro";
                        descricaoSaude = "Abaixo do peso! \nImplica transtornos digestivos, debilidade, fadiga crónica, stress, ansiedade, e difusão das hormonais";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 25.1) && (resultadoImc <= 29.5)) {
                        condicaoSaude = "Muito Bom";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 29.6) && (resultadoImc <= 32.8)) {
                        condicaoSaude = "Saudável";
                        descricaoSaude = "Peso Normal! \nImplica estado normal, bom nivel de energia, vitalidade e boa condição física, Mantenha o seu peso e IMC";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 32.9) && (resultadoImc <= 36.7)) {
                        condicaoSaude = "Sobrepeso";
                        descricaoSaude = "Acima do Peso! \nImplica fadiga, problemas digestivos, problemas circulatórios, mã circulação nas pernas e varizes";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if ((resultadoImc >= 36.8) && (resultadoImc <= 40.4)) {
                        condicaoSaude = "Gordo";
                        descricaoSaude = "Obesidade 1! \nImplica diabetes, angina de peito, enfartes, trombo flebites, arteroscleroses, embolias, alterações menstruais.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    if (resultadoImc >= 40.5) {
                        condicaoSaude = "Muito Gordo";
                        descricaoSaude = "Obesidade 2! \nImplica falta de ar, apneia, sonolencia, trombose pulmonar, úlceras varicosas, cancro do cólon, uterino e mamário, refluxo esofágico, discriminação social, laboral e sexual.";
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        linearIMC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        txtResultadoDescritivo.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                }
                break;
            }
        }

        txtResultadoDescritivo.setText("Saúde: " + condicaoSaude + "\n" + descricaoSaude);

    }

    public void sobreImc(View v) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(getString(R.string.ImcTitle));
        alertDialog.setMessage(getString(R.string.ImcBotaoSobre));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
