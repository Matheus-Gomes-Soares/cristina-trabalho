package com.example.frota.solicitacaoDeTransporte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.frota.caixa.Caixa;
import com.example.frota.caixa.CaixaService;
import com.example.frota.caminhao.AtualizacaoCaminhao;
import com.example.frota.caminhao.Caminhao;
import com.example.frota.caminhao.CaminhaoMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/solicitacaoDeTransporte")
public class SolicitacaoDeTransporteController {

	@Autowired
	private SolicitacaoDeTransporteService solicitacaoDeTransporteService;
	
	@Autowired
    private SolicitacaoDeTransporteMapper solicitacaoDeTransporteMapper;
	
	@Autowired
	private CaixaService caixaService;

	@GetMapping              
	public String carregaPaginaListagem(Model model){ 
		model.addAttribute("lista",solicitacaoDeTransporteService.procurarTodos() );
		return "solicitacaoDeTransporte/listagem";              
	} 
	
	@PostMapping("/formulario/calcularFrete")
	public String calcularFrete(@ModelAttribute AtualizacaoSolicitacaoDeTransporte dto,
	                            Model model,
	                            RedirectAttributes redirectAttributes) {
	    try {
	        Double freteCalculado = solicitacaoDeTransporteService.calcularFrete(dto);
	        
	        // ATUALIZA O DTO COM O VALOR DO FRETE
	        // Se seu DTO é um record, você precisa criar uma nova instância:
	        dto = new AtualizacaoSolicitacaoDeTransporte(
	        	    dto.id(),
	        	    dto.nomeProduto(),
	        	    dto.peso(),          
	        	    dto.largura(),
	        	    dto.comprimento(),
	        	    dto.altura(),
	        	    dto.caixaId(),       
	        	    dto.origemCep(),
	        	    dto.origemRua(),
	        	    dto.origemNumero(),
	        	    dto.destinoCep(),
	        	    dto.destinoRua(),
	        	    dto.destinoNumero(),
	        	    freteCalculado       
	        	);
	        
	        model.addAttribute("freteCalculado", freteCalculado);
	    } catch (RuntimeException e) {
	        model.addAttribute("error", "Erro ao calcular frete: " + e.getMessage());
	    }
	    
	    model.addAttribute("solicitacaoDeTransporte", dto);
	    model.addAttribute("caixas", caixaService.procurarCorrespondentes(dto));

	    return "solicitacaoDeTransporte/formulario";
	}

	@GetMapping("/formulario")
	public String carregaPaginaFormulario(@RequestParam(required = false) Long id, Model model) {
		AtualizacaoSolicitacaoDeTransporte dto;
        if (id != null) {
            //edição: Carrega dados existentes
        	SolicitacaoDeTransporte solicitacaoDeTransporte = solicitacaoDeTransporteService.procurarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação de transporte não encontrada"));
            dto = solicitacaoDeTransporteMapper.toAtualizacaoDto(solicitacaoDeTransporte);
        } else {
            // criação: DTO vazio
            dto = new AtualizacaoSolicitacaoDeTransporte(null, null, null, null, null, null,null, null, null, null, null, null, null, null);
        }
        model.addAttribute("solicitacaoDeTransporte", dto);
        model.addAttribute("caixas", null);
        return "solicitacaoDeTransporte/formulario";
    }
	
	@GetMapping ("/formulario/{id}")    
	public String carregaPaginaFormulario (@PathVariable("id") Long id, Model model,
			RedirectAttributes redirectAttributes) {
		AtualizacaoSolicitacaoDeTransporte dto;

		try {
			if(id != null) {
				System.out.println("entrou aqui porque o id não é nulo");
				SolicitacaoDeTransporte solicitacaoDeTransporte = solicitacaoDeTransporteService.procurarPorId(id)
						.orElseThrow(() -> new EntityNotFoundException("Solicitação de transporte não encontrada ID"));
				model.addAttribute("caixas", solicitacaoDeTransporte.getCaixa());
				//mapear caminhão para AtualizacaoCaminhao
				dto = solicitacaoDeTransporteMapper.toAtualizacaoDto(solicitacaoDeTransporte);
				model.addAttribute("solicitacaoDeTransporte", dto);
			}
			return "solicitacaoDeTransporte/formulario";
		} catch (EntityNotFoundException e) {
			//resolver erros
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/solicitacaoDeTransporte";
		}
	}
	
	
	
	@DeleteMapping
	@Transactional
	public String excluir (Long id) {
		solicitacaoDeTransporteService.apagarPorId(id);
		return "redirect:solicitacaoDeTransporte";
	}
	// Método para gravar/atualizar o formulário 
	@PostMapping
	@Transactional
	public String cadastrar (@Valid CadastroSolicitacaoDeTransporte dados) {
		Caixa caixa = caixaService.procurarPorId(dados.caixaId()).orElse(null);
		
		solicitacaoDeTransporteService.salvar(new SolicitacaoDeTransporte(dados, caixa));
		return "redirect:solicitacaoDeTransporte";
	}
	@PutMapping
	@Transactional
	public String atualizar (AtualizacaoSolicitacaoDeTransporte dados) {
		solicitacaoDeTransporteService.atualizarSolicitacaoDeTransporte(dados);
		return "redirect:solicitacaoDeTransporte";
	}
	
	@PostMapping("/salvar")
	public String salvar(@ModelAttribute("solicitacaoDeTransporte") @Valid AtualizacaoSolicitacaoDeTransporte dto,
	                     @RequestParam(value = "valorFrete", required = false) Double valorFrete,
	                     BindingResult result,
	                     RedirectAttributes redirectAttributes,
	                     Model model) {
	    System.out.println("entrou no metodo salvar do controller");
	    System.out.println("Valor do frete recebido: " + valorFrete);
	    
	    if (result.hasErrors()) {
	        result.getAllErrors().forEach(e -> System.out.println(e.toString()));
	        return "solicitacaoDeTransporte/formulario";
	    }

	    try {
	        System.out.println("DTO recebido " + dto);
	        System.out.println("Valor do frete: " + valorFrete);
	        
	        // Se necessário, você pode setar o valor do frete no DTO aqui
	        // ou passar para o service
	        SolicitacaoDeTransporte solicitacaoDeTransporteSalva = solicitacaoDeTransporteService.salvarOuAtualizar(dto, valorFrete);
	        
	        String mensagem = dto.id() != null
	            ? "SolicitacaoDeTransporte do produto'" + solicitacaoDeTransporteSalva.getProduto().getNomeProduto() + "' atualizada com sucesso!"
	            : "SolicitacaoDeTransporte do produto'" + solicitacaoDeTransporteSalva.getProduto().getNomeProduto() + "' criada com sucesso!";
	        redirectAttributes.addFlashAttribute("message", mensagem);
	        return "redirect:/solicitacaoDeTransporte";
	    } catch (EntityNotFoundException e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	        return "redirect:/solicitacaoDeTransporte/formulario" + (dto.id() != null ? "?id=" + dto.id() : "");
	    }
	}

}
