package com.example.frota.caixa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AtualizacaoCaixa(
	    Long id,
		
	    @NotNull (message = "modelo é obrigatório")
	    String modelo,
	    @NotNull(message = "Largura é obrigatória")
	    @Positive(message = "Largura deve ser positiva")
		Double largura,

		@NotNull(message = "Comprimento é obrigatório")
	    @Positive(message = "Comprimento deve ser positivo")
		Double comprimento,

		@NotNull(message = "Altura é obrigatória")
	    @Positive(message = "Altura deve ser positiva")
		Double altura,

		@NotBlank(message = "Material é obrigatório")
	    String material,

		
		@NotNull(message = "Limite de peso é obrigatório")
	    @Positive(message = "Limite de peso deve ser positivo")
		Double limiteDePeso


		
	) {}


