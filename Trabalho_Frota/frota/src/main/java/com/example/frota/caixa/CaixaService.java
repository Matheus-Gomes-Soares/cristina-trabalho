package com.example.frota.caixa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.frota.solicitacaoDeTransporte.AtualizacaoSolicitacaoDeTransporte;
import com.example.frota.solicitacaoDeTransporte.DtoProduto;

import jakarta.persistence.EntityNotFoundException;


@Service
public class CaixaService {
	@Autowired
	private CaixaRepository caixaRepository;
	
	public Caixa salvar(Caixa caixa) {
		return caixaRepository.save(caixa);
	}
	
	public List<Caixa> procurarTodos(){
		return caixaRepository.findAll(Sort.by("modelo").ascending());
	}
	
	public List<Caixa> procurarCorrespondentes(CaixasFiltro dto){
		List<Caixa> todas = caixaRepository.findAll(Sort.by("modelo").ascending());
		List<Caixa> correspondente = new ArrayList();
		if (dto.altura() == null || dto.largura() == null || dto.comprimento() == null) 
		return null;	
		
		for(Caixa caixa: todas) {
			if(caixa.getAltura() > dto.altura() && caixa.getComprimento() > dto.comprimento() && caixa.getLargura() > dto.largura()
					&& caixa.getLimiteDePeso() > dto.peso()) {
				correspondente.add(caixa);
			}
		}
		return correspondente;
				
	}
	
	public List<Caixa> filtrarCaixas(DtoProduto filtro) {
		System.out.println("tentou listar");
        return caixaRepository.findAll().stream()
            .filter(c -> c.getComprimento() >= filtro.comprimento()
                      && c.getLargura() >= filtro.largura()
                      && c.getAltura() >= filtro.altura()
                      && c.getLimiteDePeso() >= filtro.peso())
            .toList();
    }
	
	public List<Caixa> filtrarCaixas(AtualizacaoSolicitacaoDeTransporte filtro) {
		System.out.println("tentou listar");
        return caixaRepository.findAll().stream()
            .filter(c -> c.getComprimento() >= filtro.comprimento()
                      && c.getLargura() >= filtro.largura()
                      && c.getAltura() >= filtro.altura()
                      && c.getLimiteDePeso() >= filtro.peso())
            .toList();
    }
	
	public void apagarPorId (Long id) {
		caixaRepository.deleteById(id);
	}
	public Optional<Caixa> procurarPorId( Long id) {
		return caixaRepository.findById(id);
	}
	public void atualizarCaixa(AtualizacaoCaixa dados) {
	    Caixa caixa = caixaRepository.findById(dados.id())
	        .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada"));
	    caixa.atualizarInformacoes(dados);
	}
	
//	public Caixa salvarOuAtualizar(AtualizacaoCaixa dto) {
//	    if (dto.id() == null) {
//	        return caixaRepository.save(new Caixa(dto));
//	    } else {
//	        Caixa caixa = caixaRepository.findById(dto.id())
//	            .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada"));
//	        caixa.atualizarInformacoes(dto);
//	        return caixa;
//	    }
//	}
	
	public Caixa salvarOuAtualizar(AtualizacaoCaixa dto) {
	    if (dto.id() == null) {
	        return caixaRepository.save(new Caixa(dto));
	    } else {
	    	System.out.println("entra no else, afinal é atualização e está com dto " + dto);
	        Caixa caixa = caixaRepository.findById(dto.id())
	            .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada"));
	        caixa.atualizarInformacoes(dto);
	        return caixaRepository.save(caixa);
	    }
	}
}
