package com.example.frota.caminhao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import com.example.frota.marca.Marca;
import com.example.frota.marca.MarcaService;

@Service
public class CaixaService {
	@Autowired
	private CaixaRepository caixaRepository;
	
	public Caixa salvarOuAtualizar(AtualizacaoCaixa dto) {
        if (dto.id() != null) {
            // atualizando Busca existente e atualiza
            Caixa existente = caixaRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada com ID: " + dto.id()));
            return caixaRepository.save(existente);
        } else {
            // criando Novo caminhão
            Caixa novaCaixa = CaixaRepository.salvar()
            
            return caminhaoRepository.save(novoCaminhao);
        }
    }
	
	public List<Caminhao> procurarTodos(){
		return caminhaoRepository.findAll(Sort.by("modelo").ascending());
	}
	public void apagarPorId (Long id) {
		caminhaoRepository.deleteById(id);
	}
	
	public Optional<Caminhao> procurarPorId(Long id) {
	    return caminhaoRepository.findById(id);
	}
}
