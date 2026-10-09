package com.example.simuladordeinvestimentos

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat
import java.util.Locale

class ResultadoActivity : AppCompatActivity() {


    lateinit var valorInvestido : TextView
    lateinit var valorAculuado : TextView
    lateinit var lucroObtido : TextView
    lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        valorInvestido= findViewById(R.id.txtTotalInvestido)
        valorAculuado= findViewById(R.id.txtValorAcumulado)
        lucroObtido= findViewById(R.id.txtLucroObtido)
        toolbar= findViewById(R.id.resultado_toolbar)


        val valorIncial = intent.getDoubleExtra("valorInicial",0.0)
        val aporteMensal= intent.getDoubleExtra("aporteMensal",0.0)
        val taxaJuros = intent.getDoubleExtra("taxaJuros",0.0)
        val tempoAnos= intent.getDoubleExtra("tempoAnos",0.0)

        // função para mostrar toolbar na tela
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)




        //Calculando e pegando o último mês (o resultado final)
        val listaMensal = calcularEvolucaoMensal(valorIncial, aporteMensal,taxaJuros,tempoAnos)
        val ultimo = listaMensal.last()

        // 4. Formatador de dinheiro no padrão brasileiro
        val formatoMoeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))

        // preenchendo a tela
        valorInvestido.text= formatoMoeda.format(ultimo.totalInvestido)
        valorAculuado.text = formatoMoeda.format(ultimo.saldo)
        lucroObtido.text= formatoMoeda.format(ultimo.totalJuros)


        val listaAnual = extrairEvolucaoAnual(listaMensal)

        supportFragmentManager.beginTransaction()
            .replace(R.id.containerResumo, ResumoFragment.newInstance(ArrayList(listaAnual)))
            .commit()





    }
    // função volta para tela anterior
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}