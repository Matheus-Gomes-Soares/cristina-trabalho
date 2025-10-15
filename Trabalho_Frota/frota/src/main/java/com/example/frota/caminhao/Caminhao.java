package com.example.frota.caminhao;

import com.example.frota.marca.Marca;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "caminhao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Caminhao {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "caminhao_id")
	private Long id;
	private String modelo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "marca_id", referencedColumnName = "marca_id")
	private Marca marca;
	private String placa;
	private double cargaMaxima;
	private int ano;
	// novos atributos referentes a tarefa
	private double comprimento;
	private double largura;
	private double altura;
	//metragemCubica é de fato necessária porque essa infeliz é apenas um cálculo
	private double metragemCubica;
	private final double fatorCubagem = 300; // Fator de cubagem para transporte rodoviário: 300 kg/m³.


	public Caminhao(CadastroCaminhao dados, Marca marca) {
		this.modelo = dados.modelo();
		this.placa = dados.placa();
		this.cargaMaxima = dados.cargaMaxima();
		this.marca = marca;
		this.ano= dados.ano();
		this.metragemCubica = calculoMetragem();
	}
	public Caminhao(AtualizacaoCaminhao dados, Marca marca) {
		this.modelo = dados.modelo();
		this.placa = dados.placa();
		this.cargaMaxima = dados.cargaMaxima();
		this.marca = marca;
		this.ano= dados.ano();
		//novos na atualização
		this.comprimento = dados.comprimento();
		this.altura = dados.altura();
		this.largura = dados.largura();
		this.metragemCubica = calculoMetragem();
	
	}
	
	public void atualizarInformacoes(AtualizacaoCaminhao dados, Marca marca) {
		if (dados.modelo() != null )
			this.modelo = dados.modelo();
		if (dados.placa() != null)
			this.placa =dados.placa();
		if (dados.cargaMaxima() != 0)
			this.cargaMaxima = dados.cargaMaxima();
		if (marca != null)
			this.marca = marca;
		if (dados.ano() != 0)
			this.ano = dados.ano();
		this.metragemCubica = calculoMetragem();
	}

	
	public double calculoMetragem(){
		double resultado = this.altura * this.largura * this.comprimento * this.fatorCubagem;
		return resultado;
	}

}
