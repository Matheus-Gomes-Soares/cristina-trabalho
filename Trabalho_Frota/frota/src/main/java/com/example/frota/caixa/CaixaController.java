package com.example.frota.caixa;

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

import com.example.frota.caminhao.AtualizacaoCaminhao;
import com.example.frota.caminhao.Caminhao;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/caixa")
public class CaixaController {

	@Autowired
	private CaixaService caixaService;

	@GetMapping              
	public String carregaPaginaListagem(Model model){ 
		model.addAttribute("lista",caixaService.procurarTodos() );
		return "caixa/listagem";              
	} 
	
	@GetMapping("/formulario")
	public String carregaPaginaFormulario(@RequestParam(required = false) Long id, Model model) {
	    Caixa caixa = (id != null) ? caixaService.procurarPorId(id).orElse(new Caixa(null, null, 0, 0, 0, null, 0)) : new Caixa();
	    model.addAttribute("caixa", caixa);
	    return "caixa/formulario";
    }

	
	
	
	@DeleteMapping
	@Transactional
	public String excluir (Long id) {
		caixaService.apagarPorId(id);
		return "redirect:caixa";
	}
	// Método para gravar/atualizar o formulário 
	@PostMapping
	@Transactional
	public String cadastrar (@Valid CadastroCaixa dados) {
		caixaService.salvar(new Caixa(dados));
		return "redirect:caixa";
	}
	@PutMapping
	@Transactional
	public String atualizar (AtualizacaoCaixa dados) {
		caixaService.atualizarCaixa(dados);
		return "redirect:caixa";
	}
	
//	@PostMapping("/salvar")
//	public String salvar(@ModelAttribute("caixa") @Valid AtualizacaoCaixa dto,
//	                     BindingResult result,
//	                     RedirectAttributes redirectAttributes,
//	                     Model model) {
//	    if (result.hasErrors()) {
//	        // Se tiver validação, recarregue dados necessários (se houver)
//	        return "caixa/formulario";
//	    }
//
//	    try {
//	        Caixa caixaSalva = caixaService.salvarOuAtualizar(dto);
//	        String mensagem = dto.id() != null
//	            ? "Caixa '" + caixaSalva.getModelo() + "' atualizada com sucesso!"
//	            : "Caixa '" + caixaSalva.getModelo() + "' criada com sucesso!";
//	        redirectAttributes.addFlashAttribute("message", mensagem);
//	        return "redirect:/caixa";
//	    } catch (EntityNotFoundException e) {
//	        redirectAttributes.addFlashAttribute("error", e.getMessage());
//	        return "redirect:/caixa/formulario" + (dto.id() != null ? "?id=" + dto.id() : "");
//	    }
//	}
	
	@PostMapping("/salvar")
	public String salvar(@ModelAttribute("caixa") @Valid AtualizacaoCaixa dto,
	                     BindingResult result,
	                     RedirectAttributes redirectAttributes,
	                     Model model) {
	    if (result.hasErrors()) {
	        return "caixa/formulario";
	    }

	    try {
	    	System.out.println("DTO recebido " + dto);
	        Caixa caixaSalva = caixaService.salvarOuAtualizar(dto);
	        String mensagem = dto.id() != null
	            ? "Caixa '" + caixaSalva.getModelo() + "' atualizada com sucesso!"
	            : "Caixa '" + caixaSalva.getModelo() + "' criada com sucesso!";
	        redirectAttributes.addFlashAttribute("message", mensagem);
	        return "redirect:/caixa";
	    } catch (EntityNotFoundException e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	        return "redirect:/caixa/formulario" + (dto.id() != null ? "?id=" + dto.id() : "");
	    }
	}

}
