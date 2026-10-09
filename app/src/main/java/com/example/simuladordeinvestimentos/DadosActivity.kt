package com.example.simuladordeinvestimentos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DadosActivity : AppCompatActivity() {

    lateinit var editValorIncial: EditText
    lateinit var editAporteMensal: EditText
    lateinit var editTaxaJuros: EditText
    lateinit var editTempoAnos: EditText
    lateinit var btnCalcular: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dados)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editValorIncial= findViewById(R.id.editValorIncial)
        editAporteMensal = findViewById(R.id.editAporteMensal)
        editTaxaJuros = findViewById(R.id.editTaxaJuros)
        editTempoAnos = findViewById(R.id.editTempoAnos)
        btnCalcular = findViewById(R.id.btnCalcular)



        btnCalcular.setOnClickListener {

            val valorInicial = editValorIncial.text.toString().toDoubleOrNull()
            val aporteMensal = editAporteMensal.text.toString().toDoubleOrNull()
            val taxaJuros = editTaxaJuros.text.toString().toDoubleOrNull()
            val tempoAnos = editTempoAnos.text.toString().toDoubleOrNull()


            // validação dos campos
            if (valorInicial == null || valorInicial == 0.0){
                editValorIncial.error= "preencha os campos corretamente"
                editValorIncial.requestFocus()
                return@setOnClickListener

            }else if (aporteMensal == null || aporteMensal == 0.0){
                editAporteMensal.error="preencha os campos corretamente"
                editAporteMensal.requestFocus()
                return@setOnClickListener

            }else if (taxaJuros == null || taxaJuros == 0.0){
                editTaxaJuros.error="preencha os campos corretamente"
                editTaxaJuros.requestFocus()
                return@setOnClickListener

            }else if (tempoAnos == null || tempoAnos == 0.0){
                editTempoAnos.error="preencha os campos corretamente"
                editTempoAnos.requestFocus()
                return@setOnClickListener
            }

            val listaMensal = calcularEvolucaoMensal(valorInicial, aporteMensal,taxaJuros, tempoAnos)
            val listaAnual = extrairEvolucaoAnual(listaMensal)



            val intent = Intent(this, ResultadoActivity::class.java)
            intent.putExtra("valorInicial", valorInicial)
            intent.putExtra("aporteMensal", aporteMensal)
            intent.putExtra("taxaJuros", taxaJuros)
            intent.putExtra("tempoAnos", tempoAnos)

            startActivity(intent)

        }
    }
}