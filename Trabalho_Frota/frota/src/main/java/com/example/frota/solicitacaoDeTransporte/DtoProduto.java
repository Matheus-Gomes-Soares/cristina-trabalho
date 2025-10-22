package com.example.frota.solicitacaoDeTransporte;

import com.example.frota.caixa.CaixasFiltro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DtoProduto(
	
			    @NotBlank(message = "Modelo é obrigatório") 
			    String nomeProduto,
			    
			    @NotNull(message = "Peso é obrigatória")
			    Double peso,
			    @NotNull(message = "Largura é obrigatória")
			    @Positive(message = "Largura deve ser positiva")
				Double largura,

				@NotNull(message = "Comprimento é obrigatório")
			    @Positive(message = "Comprimento deve ser positivo")
				Double comprimento,

				@NotNull(message = "Altura é obrigatória")
			    @Positive(message = "Altura deve ser positiva")
				Double altura
				)  implements CaixasFiltro {

}
