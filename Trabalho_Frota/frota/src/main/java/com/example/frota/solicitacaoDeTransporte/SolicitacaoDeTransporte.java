package com.example.frota.solicitacaoDeTransporte;

import java.util.List;

import com.example.frota.caixa.Caixa;
import com.example.frota.caminhao.AtualizacaoCaminhao;
import com.example.frota.caminhao.CadastroCaminhao;
import com.example.frota.caminhao.Caminhao;
import com.example.frota.marca.Marca;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
	private Produto produto;
	@ManyToOne
	@JoinColumn(name="caixa_id")
	private Caixa caixa;
	
	public SolicitacaoDeTransporte(CadastroSolicitacaoDeTransporte dados, Produto produto, Caixa caixa) {
		
	
	}
	
	public SolicitacaoDeTransporte(AtualizacaoSolicitacaoDeTransporte dados, Caixa caixa) {
		this.produto.setNomeProduto(dados.nomeProduto());
		this.produto.setComprimento(dados.comprimento());
		this.produto.setLargura(dados.largura());
		this.produto.setAltura(dados.altura());
		this.produto.setPeso(dados.peso());
		this.caixa = caixa;
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
}
