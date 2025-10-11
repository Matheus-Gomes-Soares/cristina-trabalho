package com.example.frota.caixa;

import jakarta.validation.constraints.NotBlank;

public record CadastroCaixa(
		@NotBlank
		double largura,
		double comprimento,
		double altura,
        String material,
        double limiteDePeso,

		int ano) {

}

