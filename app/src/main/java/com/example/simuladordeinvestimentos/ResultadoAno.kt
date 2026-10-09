package com.example.simuladordeinvestimentos

data class ResultadoAno(
    val ano: Int,
    val saldo: Double,
    val totalInvestido: Double,
    val totalJuros: Double
)


data class ResultadoMensal(
    val mes: Int,
    val saldo : Double,
    val totalInvestido: Double,
    val totalJuros: Double
)

fun calcularEvolucaoMensal(
    valorInicial : Double,
    aporteMensal: Double,
    taxaJurosAnual: Double,
    tempoAnos:Double

):List<ResultadoMensal>{

    val taxaMensal = Math.pow(1+(taxaJurosAnual/100), 1.0 /12) -1
    val meses = (tempoAnos * 12).toInt()
    var saldo = valorInicial
    var totalInvestido = valorInicial
    val evolucao = mutableListOf<ResultadoMensal>()

    for (mes in 1..meses){
        saldo += aporteMensal
        saldo *= (1+taxaMensal)
        totalInvestido += aporteMensal
        val totalJuros = saldo - totalInvestido

        evolucao.add(ResultadoMensal(mes,saldo,totalInvestido, totalJuros))
    }
    return  evolucao
}

fun extrairEvolucaoAnual(listaMensal: List<ResultadoMensal>): List<ResultadoAno> {

    val evolucaoAnual = mutableListOf<ResultadoAno>()

    for (item in listaMensal) {
        if (item.mes % 12 == 0) {
            val ano = item.mes / 12

            evolucaoAnual.add(
                ResultadoAno(
                    ano = ano,
                    saldo = item.saldo,
                    totalInvestido = item.totalInvestido,
                    totalJuros = item.totalJuros
                )
            )
        }
    }

    return evolucaoAnual
}