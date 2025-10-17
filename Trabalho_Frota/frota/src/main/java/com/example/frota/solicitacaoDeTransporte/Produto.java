package com.example.frota.solicitacaoDeTransporte;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Produto {
	private String nomeProduto;
	private double comprimento;
	private double largura;
	private double altura;
    private double peso;
}
