package com.example.frota.solicitacaoDeTransporte;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "produto")
public class Produto {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "produto_id")
	private Long id;
	private String nomeProduto;
	private double comprimento;
	private double largura;
	private double altura;
    private double peso;
}
