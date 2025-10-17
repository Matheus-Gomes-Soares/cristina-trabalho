package com.example.frota.caixa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "caixa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Caixa {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "caixa_id")
	private Long id;
	// atributos específicos caixa
	private String modelo;
	private double comprimento;
	private double largura;
	private double altura;
    private String material;
    private double limiteDePeso;

	public Caixa(CadastroCaixa dados) {
		//novos na atualização
		this.modelo= dados.modelo();
		this.comprimento = dados.comprimento();
		this.altura = dados.altura();
		this.largura = dados.largura();
        this.material = dados.material();
        this.limiteDePeso = dados.limiteDePeso();
	}
	public Caixa(AtualizacaoCaixa dados) {
		this.modelo= dados.modelo();
		this.comprimento = dados.comprimento();
		this.altura = dados.altura();
		this.largura = dados.largura();
        this.material = dados.material();
        this.limiteDePeso = dados.limiteDePeso();
	}
	
	public void atualizarInformacoes(AtualizacaoCaixa dados) {
			if (dados.comprimento() != 0)
			this.comprimento = dados.comprimento();
		if(dados.altura() != 0)
			this.altura = dados.altura();
		if(dados.largura() != 0)
			this.largura = dados.largura();
        if(dados.material()!= null)
            this.material = dados.material();
        if(dados.limiteDePeso() != 0)
            this.limiteDePeso = dados.limiteDePeso();
	}
	
}
