package com.example.frota.solicitacaoDeTransporte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
            dto = new AtualizacaoSolicitacaoDeTransporte(0L, "", 0.0, 0.0, 0.0, 0.0, 0L);
        }
        model.addAttribute("solicitacaoDeTransporte", dto);
        model.addAttribute("caixas", caixaService.procurarTodos());
        return "solicitacaoDeTransporte/formulario";
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
	                     BindingResult result,
	                     RedirectAttributes redirectAttributes,
	                     Model model) {
	    if (result.hasErrors()) {
	        return "solicitacaoDeTransporte/formulario";
	    }

	    try {
	    	System.out.println("DTO recebido " + dto);
	        SolicitacaoDeTransporte solicitacaoDeTransporteSalva = solicitacaoDeTransporteService.salvarOuAtualizar(dto);
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
