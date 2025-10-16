package com.example.frota.caixa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import com.example.frota.caminhao.CaminhaoMapper;
import com.example.frota.caminhao.CaminhaoRepository;
import com.example.frota.marca.MarcaService;

@Service
public class CaixaService {
	@Autowired
	private CaixaRepository caixaRepository;
	
	@Autowired
	private CaixaMapper caixaMapper;
	
	public Caixa salvarOuAtualizar(AtualizacaoCaixa dto) {
        if (dto.id() != null) {
            // atualizando Busca existente e atualiza
            Caixa existente = caixaRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada com ID: " + dto.id()));
            return caixaRepository.save(existente);
        } else {
            // criando Nova Caixa
        	 Caixa novaCaixa = caixaMapper.toEntityFromAtualizacao(dto);
             
             return caixaRepository.save(novaCaixa);
            
           
        }
    }
	
	public List<Caixa> procurarTodas(){
		return caixaRepository.findAll();
	}
	public void apagarPorId (Long id) {
		caixaRepository.deleteById(id);
	}
	
	public Optional<Caixa> procurarPorId(Long id) {
	    return caixaRepository.findById(id);
	}
}
