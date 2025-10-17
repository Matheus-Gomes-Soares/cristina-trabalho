package com.example.frota.caixa;



public record CadastroCaixa(
		String modelo,
		double comprimento,
		double largura,
		double altura,
        String material,
        double limiteDePeso) {

}

