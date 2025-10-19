package com.example.frota.solicitacaoDeTransporte;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.frota.caixa.Caixa;
import com.example.frota.caixa.CaixaService;
import com.example.frota.caminhao.Caminhao;
import com.example.frota.marca.Marca;

import jakarta.persistence.EntityNotFoundException;


@Service
public class SolicitacaoDeTransporteService {
	@Autowired
	private SolicitacaoDeTransporteRepository solicitacaoDeTransporteRepository;
	
	@Autowired
	private CaixaService caixaService;
	
	@Autowired
	private SolicitacaoDeTransporteMapper solicitacaoDeTransporteMapper;
	
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
		 // Valida se a marca existe
		System.out.println("Método da service alcançado");
       Caixa caixa = caixaService.procurarPorId(dto.caixaId())
           .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada com ID: " + dto.caixaId()));
       if (dto.id() != null) {
       	System.out.println("Ele entra na edição de Solicitação");
           // atualizando Busca existente e atualiza
           SolicitacaoDeTransporte existente = solicitacaoDeTransporteRepository.findById(dto.id())
               .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada com ID: " + dto.id()));
           solicitacaoDeTransporteMapper.updateEntityFromDto(dto, existente);
           existente.setCaixa(caixa); // Atualiza a marca
           return solicitacaoDeTransporteRepository.save(existente);
       } else {
           // criando Novo caminhão
       	System.out.println("Ele entra na criação de nova Solicitação");
           SolicitacaoDeTransporte novaSolicitacao = solicitacaoDeTransporteMapper.toEntityFromAtualizacao(dto);
           novaSolicitacao.setCaixa(caixa); // Define a caixa completa
           return solicitacaoDeTransporteRepository.save(novaSolicitacao);
       }
   }
	
}
