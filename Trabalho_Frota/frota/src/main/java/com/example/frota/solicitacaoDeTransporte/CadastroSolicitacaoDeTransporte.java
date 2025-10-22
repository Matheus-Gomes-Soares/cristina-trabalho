package com.example.frota.solicitacaoDeTransporte;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CadastroSolicitacaoDeTransporte(
	    @NotBlank(message = "Nome Produto é obrigatório") 
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
		Double altura, 
		
	    @NotNull(message = "Caixa é obrigatória")
	    Long caixaId,
	    
	    @NotBlank(message = "Cep de Origem é obrigatório") 
	    String origemCep,
	    
	    @NotBlank(message = "Rua de origem é obrigatória") 
	    String origemRua,
	    
	    @NotBlank(message = "Numero de origem é obrigatório") 
	    String origemNumero,
	    
	    @NotBlank(message = "Cep de Origem é obrigatório") 
	    String destinoCep,
	    
	    @NotBlank(message = "Rua de origem é obrigatória") 
	    String destinoRua,
	    
	    @NotBlank(message = "Numero de origem é obrigatório") 
	    String destinoNumero,
	    
		Double valorFrete
	    
	) {}
