package com.example.simuladordeinvestimentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.NumberFormat
import java.util.Locale


class ResumoFragment: Fragment(R.layout.fragment_resumo) {

    companion object{
        fun newInstance(listaAnual: ArrayList<ResultadoAno>): ResumoFragment{
            val fragment = ResumoFragment()
            fragment.arguments = Bundle().apply {
                putSerializable("listaAnual", listaAnual)
            }

            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("UNCHECKED_CAST", "DEPRECATION")
        val listaAnual = requireArguments().getSerializable("listaAnual") as ArrayList<ResultadoAno>

        val container = view.findViewById<LinearLayout>(R.id.containerAnos)
        val formato = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))

        for (item in listaAnual){
            val linha = LayoutInflater.from(requireContext()).inflate(R.layout.item_resumo_ano,container,false)

            linha.findViewById<TextView>(R.id.tvAno).text = "Ano ${item.ano}"
            linha.findViewById<TextView>(R.id.tvSaldo).text= formato.format(item.saldo)

            container.addView(linha)
        }

    }
}