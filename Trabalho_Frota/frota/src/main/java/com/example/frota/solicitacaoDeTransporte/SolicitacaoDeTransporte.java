package com.example.frota.solicitacaoDeTransporte;

import java.util.List;

import com.example.frota.caixa.Caixa;
import com.example.frota.caminhao.Caminhao;
import com.example.frota.marca.Marca;

import jakarta.persistence.Column;
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
	@OneToOne
	@JoinColumn(name="produto_id")
	private Produto produto;
	@ManyToOne
	@JoinColumn(name="caixa_id")
	private Caixa caixa;
	private double cargaMaxima;
	private int ano;
	
	
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
