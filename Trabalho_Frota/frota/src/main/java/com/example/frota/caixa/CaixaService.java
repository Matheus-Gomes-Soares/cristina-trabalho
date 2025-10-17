package com.example.frota.caixa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
	        Caixa caixa = caixaRepository.findById(dto.id())
	            .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada"));
	        caixa.atualizarInformacoes(dto);
	        return caixaRepository.save(caixa);
	    }
	}
}
