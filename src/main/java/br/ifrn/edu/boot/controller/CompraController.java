package br.ifrn.edu.boot.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ifrn.edu.boot.model.Aluno;
import br.ifrn.edu.boot.model.Compra;
import br.ifrn.edu.boot.service.AlunoService;
import br.ifrn.edu.boot.service.CompraService;

@Controller
@RequestMapping("/compras")
public class CompraController {

	@Autowired
	private CompraService serviceCompra;
	
	@Autowired
	private AlunoService serviceAluno;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Compra compra) {
		return "/compra/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("compras", serviceCompra.buscarTodos());
		return "/compra/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Compra compra, BindingResult result, 
			RedirectAttributes attr) throws BindException {
		
		if(compra.getValor() == null) {
			attr.addFlashAttribute("fail", "Erro ao cadastrar compra. Campo valor");
			return "redirect:/compras/cadastrar";
		} else if(result.hasErrors()) {
			attr.addFlashAttribute("fail", "Erro ao cadastrar compra."+result.toString());
			return "redirect:/compras/cadastrar";
		} else {
			try {
				
				BigDecimal saldoAtual = compra.getAluno().getSaldo();
				saldoAtual = saldoAtual.subtract(compra.getValor());		

				Aluno aluno = serviceAluno.buscarPorId(compra.getId());
				
				aluno.setSaldo(saldoAtual);
				
				compra.setId(null);
				
				serviceCompra.salvar(compra);
				serviceAluno.editar(aluno);
				attr.addFlashAttribute("success", "Compra cadastrada com sucesso");
				return "redirect:/compras/cadastrar";
			} catch (ConstraintViolationException ex){
				attr.addFlashAttribute("fail", "Erro ao cadastrar compra.");
				return "redirect:/alunos/cadastrar";
			} catch(DataIntegrityViolationException e) {
				attr.addFlashAttribute("fail", "Erro ao cadastrar compra.");
				return "redirect:/compras/cadastrar";
			}
		}
	}
	
	@ModelAttribute("alunos")
	public List<Aluno> listaDeAlunos(){
		return serviceAluno.buscarTodos();
	}
	
	@GetMapping("/buscarAlunoPorId/{id}")
	public @ResponseBody ModelAndView buscarAluno(@PathVariable("id") Long id, Compra compra) {
		
		Aluno aluno = serviceAluno.buscarPorId(id);
		
		ModelAndView mav = new ModelAndView("compra/cadastro");
		
		mav.addObject("aluno", aluno);
		
		return mav;
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		Compra compra = serviceCompra.buscarPorId(id);
		model.addAttribute("compra", compra);
		model.addAttribute("aluno", compra.getAluno());
		model.addAttribute("saldo", compra.getAluno().getSaldo());
		return "/compra/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(Compra compra, RedirectAttributes attr) {
		serviceCompra.editar(compra);
		//Enviando alerta para página com attr
		attr.addFlashAttribute("success", "Compra editado com sucesso.");
		return "redirect:/compras/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		
		Compra compra = serviceCompra.buscarPorId(id);
		
		Aluno aluno = serviceAluno.buscarPorId(compra.getAluno().getId());
		
		BigDecimal saldoAtual = compra.getAluno().getSaldo();
		saldoAtual = saldoAtual.add(compra.getValor());
		
		aluno.setSaldo(saldoAtual);	
		serviceAluno.editar(aluno);
		
		serviceCompra.excluir(id);
		model.addAttribute("success", "Compra excluída com sucesso.");
		return listar(model);
	}
	
}
