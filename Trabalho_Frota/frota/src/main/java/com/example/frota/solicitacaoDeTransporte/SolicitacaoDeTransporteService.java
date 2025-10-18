package com.example.frota.solicitacaoDeTransporte;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.frota.caixa.Caixa;
import com.example.frota.caixa.CaixaService;

import jakarta.persistence.EntityNotFoundException;


@Service
public class SolicitacaoDeTransporteService {
	@Autowired
	private SolicitacaoDeTransporteRepository solicitacaoDeTransporteRepository;
	
	@Autowired
	private CaixaService caixaService;
	
	public SolicitacaoDeTransporte salvar(SolicitacaoDeTransporte solicitacaoDeTransporte) {
		return solicitacaoDeTransporteRepository.save(solicitacaoDeTransporte);
	}
	public List<SolicitacaoDeTransporte> procurarTodos(){
		return solicitacaoDeTransporteRepository.findAll();
	}
	public void apagarPorId (Long id) {
		solicitacaoDeTransporteRepository.deleteById(id);
	}
	public Optional<SolicitacaoDeTransporte> procurarPorId( Long id) {
		return solicitacaoDeTransporteRepository.findById(id);
	}
	public void atualizarSolicitacaoDeTransporte(AtualizacaoSolicitacaoDeTransporte dados) {
	    SolicitacaoDeTransporte solicitacaoDeTransporte = solicitacaoDeTransporteRepository.findById(dados.id())
	        .orElseThrow(() -> new EntityNotFoundException("Solicitacao de Transporte não encontrada"));
	    Caixa caixa = caixaService.procurarPorId(dados.caixaId()).orElse(null);
	    
	    solicitacaoDeTransporte.atualizarInformacoes(dados, caixa);
	}
	
	public SolicitacaoDeTransporte salvarOuAtualizar(AtualizacaoSolicitacaoDeTransporte dto) {
		Caixa caixa = caixaService.procurarPorId(dto.caixaId()).orElse(null);
	    if (dto.id() == null) {
	    	
	        return solicitacaoDeTransporteRepository.save(new SolicitacaoDeTransporte(dto, caixa));
	    } else {
	    	System.out.println("entra no else, afinal é atualização e está com dto " + dto);
	        SolicitacaoDeTransporte solicitacaoDeTransporte = solicitacaoDeTransporteRepository.findById(dto.id())
	            .orElseThrow(() -> new EntityNotFoundException("Solicitacao de Transporte não encontrada"));
	        
	        solicitacaoDeTransporte.atualizarInformacoes(dto, caixa);
	        return solicitacaoDeTransporteRepository.save(solicitacaoDeTransporte);
	    }
	}
}
