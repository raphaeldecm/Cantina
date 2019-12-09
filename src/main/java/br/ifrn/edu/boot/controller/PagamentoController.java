package br.ifrn.edu.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ifrn.edu.boot.model.Pagamento;
import br.ifrn.edu.boot.service.PagamentoService;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {

	@Autowired
	private PagamentoService servicePagamento;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Pagamento pagamento) {
		return "/pagamento/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("pagamentos", servicePagamento.buscarTodos());
		return "/pagamento/lista";
	}
}
