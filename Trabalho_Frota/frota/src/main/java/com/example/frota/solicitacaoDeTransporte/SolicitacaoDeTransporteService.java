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
	

	private SolicitacaoApi api = new SolicitacaoApi();
	
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
	public SolicitacaoDeTransporte salvarOuAtualizar(AtualizacaoSolicitacaoDeTransporte dto, double valorFrete) {
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
           existente.setValorFrete(valorFrete);
           System.out.println("Esse é o valor do frete na entidade " + existente.getValorFrete());
           System.out.println("Esse é o valor do frete no dto " + valorFrete);
           return solicitacaoDeTransporteRepository.save(existente);
       } else {
           // criando Novo caminhão
           SolicitacaoDeTransporte novaSolicitacao = solicitacaoDeTransporteMapper.toEntityFromAtualizacao(dto);
           
           novaSolicitacao.setCaixa(caixa); // Define a caixa completa
           return solicitacaoDeTransporteRepository.save(novaSolicitacao);
       }
   }
	
	public double calcularFrete(AtualizacaoSolicitacaoDeTransporte dto) {
		String endOrigem = dto.origemCep() + "," + dto.origemRua() + ","+ dto.origemNumero();
		String endDestino = dto.destinoCep() + "," + dto.destinoRua() + "," +dto.destinoNumero();
		String info = new String();
		 try {
		        info = api.retornaInfoViagem(endOrigem, endDestino);
		        System.out.println(info + " essa é a info");
		        if(info.equals("")|| info == null || info.equals("{}")) {
		        	System.out.println("entrou na captura do erro do input vazio");
		        	throw new RuntimeException("Não foi possível calcular o frete. Verifique os endereços e tente novamente.");
		        }
		    } catch (Exception e) {
		        // relança com mensagem amigável para o usuário
		        throw new RuntimeException("Não foi possível calcular o frete. Verifique os endereços e tente novamente.");
		    }

		String metrosString = "";
		String reaisString= "";
		String centavosString ="";
		
		double metros =0;
		double reais =0;
		double centavos =0;
		
		if(info.contains("distanceMeters")) {
			metrosString = info.split("\"distanceMeters\":")[1].split(",")[0].trim().replaceAll("\"", "").replaceAll("'", "");;
			metros = Double.parseDouble(metrosString);
		}
		
		if(info.contains("units")) {
			reaisString = info.split("\"units\":")[1].split(",")[0].trim().replaceAll("\"", "").replaceAll("'", "");;
			reais = Double.parseDouble(reaisString);
		}
		
		if(info.contains("units")) {
				centavosString = info.split("\"units\":")[1].split(",")[0].trim().replaceAll("\"", "").replaceAll("'", "");;
				centavos = Double.parseDouble(centavosString.substring(0, 1));
		}
		
		Optional<Caixa> caixa = caixaService.procurarPorId(dto.caixaId());
		Caixa caixaNova =  caixa.orElse(null);
		SolicitacaoDeTransporte solicitacaoDeTransporte = solicitacaoDeTransporteMapper.toEntityFromAtualizacao(dto);
		double resultado = solicitacaoDeTransporte.calcularFrete(metros,reais,centavos, caixaNova);
		return resultado;
	}
	
}
