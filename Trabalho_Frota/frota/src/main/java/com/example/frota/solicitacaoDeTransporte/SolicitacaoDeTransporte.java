package com.example.frota.solicitacaoDeTransporte;

import java.util.List;

import com.example.frota.caixa.Caixa;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
@Table(name = "solicitacaoDeTransporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class SolicitacaoDeTransporte {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "solicitacaoDeTransporte_id")
	private Long id;
	@Embedded
	private Produto produto = new Produto();
	@ManyToOne
	@JoinColumn(name="caixa_id")
	private Caixa caixa;
	private String origemCep;
	private String origemRua;
	private String origemNumero;
	
	private String destinoCep;
	private String destinoRua;
	private String destinoNumero;
	private double valorFrete;
	
	public SolicitacaoDeTransporte(CadastroSolicitacaoDeTransporte dados, Caixa caixa) {
		this.produto.setNomeProduto(dados.nomeProduto());
		this.produto.setComprimento(dados.comprimento());
		this.produto.setLargura(dados.largura());
		this.produto.setAltura(dados.altura());
		this.produto.setPeso(dados.peso());
		this.caixa = caixa;
		this.origemCep = dados.origemCep();
		this.origemNumero = dados.origemNumero();
		this.origemRua = dados.origemRua();
		this.destinoCep = dados.destinoCep();
		this.destinoRua = dados.destinoRua();
		this.destinoNumero = dados.destinoNumero();
		this.valorFrete = dados.valorFrete();
	}
	
	public SolicitacaoDeTransporte(AtualizacaoSolicitacaoDeTransporte dados, Caixa caixa) {
		this.produto.setNomeProduto(dados.nomeProduto());
		this.produto.setComprimento(dados.comprimento());
		this.produto.setLargura(dados.largura());
		this.produto.setAltura(dados.altura());
		this.produto.setPeso(dados.peso());
		this.caixa = caixa;
		this.origemCep = dados.origemCep();
		this.origemNumero = dados.origemNumero();
		this.origemRua = dados.origemRua();
		this.destinoCep = dados.destinoCep();
		this.destinoRua = dados.destinoRua();
		this.destinoNumero = dados.destinoNumero();
		this.valorFrete = dados.valorFrete();
	}
	
	public void atualizarInformacoes(AtualizacaoSolicitacaoDeTransporte dados,Caixa caixa) {
		if(dados.nomeProduto()!= null)
			this.produto.setNomeProduto(dados.nomeProduto());
		if(dados.comprimento()!= null)
			this.produto.setComprimento(dados.comprimento());
		if(dados.largura()!= null)
			this.produto.setLargura(dados.largura());
		if(dados.altura()!= null)
			this.produto.setAltura(dados.altura());
		if(dados.peso()!= null)
			this.produto.setPeso(dados.peso());
		if(caixa!= null)
			this.caixa = caixa;
		if(dados.origemCep()!= null)
		this.origemCep = dados.origemCep();
		if(dados.origemNumero()!= null)
		this.origemNumero = dados.origemNumero();
		if(dados.origemRua()!= null)
		this.origemRua = dados.origemRua();
		if(dados.destinoCep()!= null)
		this.destinoCep = dados.destinoCep();
		if(dados.destinoRua()!= null)
		this.destinoRua = dados.destinoRua();
		if(dados.destinoNumero()!= null)
		this.destinoNumero = dados.destinoNumero();
		if(dados.valorFrete()!= null)
			this.valorFrete = dados.valorFrete();
	}
	
	
	private List<Caixa> buscarCaixasDisponiveis(List<Caixa> caixas, Produto produto){
		List<Caixa> caixasDisponiveis = null;
		for(Caixa caixa : caixas) {
			if(caixa.getLargura() > produto.getLargura() 
				&& caixa.getAltura() > produto.getAltura()
				&& caixa.getComprimento()>produto.getComprimento()) {
				caixasDisponiveis.add(caixa);
			}
		}
		return caixasDisponiveis;
	}
	
	public double calcularFrete(double metros, double reais, double centavos) {
		
		double resultado = 0;
		double maiorPeso = (this.caixa.calculoMetragem()> this.produto.getPeso())? this.caixa.calculoMetragem(): this.produto.getPeso();
		
		
		double pedagio = reais + (centavos * 0.01);
		resultado+= maiorPeso * 0.5;
		System.out.println("resultado considereando peso " + resultado);
		resultado+= pedagio;
		System.out.println("resultado considereando pedagio " + resultado + "pedagio = " + pedagio);
		
		resultado += metros * 0.002;
		System.out.println("resultado considerando distancia de " + metros + " = " + resultado);
		return resultado;
	}
	
	
}
