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
import br.ifrn.edu.boot.model.Pagamento;
import br.ifrn.edu.boot.service.AlunoService;
import br.ifrn.edu.boot.service.PagamentoService;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {

	@Autowired
	private PagamentoService servicePagamento;
	
	@Autowired
	private AlunoService serviceAluno;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Pagamento pagamento) {
		return "/pagamento/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("pagamentos", servicePagamento.buscarTodos());
		return "/pagamento/lista";
	}
	
	@ModelAttribute("alunos")
	public List<Aluno> listaDeAlunos(){
		return serviceAluno.buscarTodos();
	}
	
	@GetMapping("/buscarAlunoPorId/{id}")
	public @ResponseBody ModelAndView buscarAluno(@PathVariable("id") Long id, Pagamento pagamento) {
		
		Aluno aluno = serviceAluno.buscarPorId(id);
		
		ModelAndView mav = new ModelAndView("pagamento/cadastro");
		
		mav.addObject("aluno", aluno);
		
		return mav;
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Pagamento pagamento, BindingResult result, 
			RedirectAttributes attr) throws BindException {

		if(pagamento.getValor() == null) {
			attr.addFlashAttribute("fail", "Erro ao cadastrar pagamento. Campo valor");
			System.out.println(pagamento.getValor()+"TESTE");
			return "redirect:/pagamentos/cadastrar";
		} else if(result.hasErrors()) {
			attr.addFlashAttribute("fail", "Erro ao cadastrar pagamento."+result.toString());
			return "redirect:/pagamentos/cadastrar";
		} else {
			try {
				
				BigDecimal saldoAtual = pagamento.getAluno().getSaldo();
				saldoAtual = saldoAtual.add(pagamento.getValor());		

				Aluno aluno = serviceAluno.buscarPorId(pagamento.getId());
				
				aluno.setSaldo(saldoAtual);
				
				pagamento.setId(null);
				servicePagamento.salvar(pagamento);
				serviceAluno.editar(aluno);
				attr.addFlashAttribute("success", "Pagamento cadastrada com sucesso");
				return "redirect:/pagamentos/cadastrar";
			} catch (ConstraintViolationException ex){
				attr.addFlashAttribute("fail", "Erro ao cadastrar pagamento.");
				return "redirect:/pagamentos/cadastrar";
			} catch(DataIntegrityViolationException e) {
				attr.addFlashAttribute("fail", "Erro ao cadastrar pagamento.");
				return "redirect:/pagamentos/cadastrar";
			}
		}
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("pagamento", servicePagamento.buscarPorId(id));
		return "/pagamento/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(Pagamento pagamento, RedirectAttributes attr) {
		servicePagamento.editar(pagamento);
		attr.addFlashAttribute("success", "Pagamento editado com sucesso.");
		return "redirect:/pagamentos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		
		Pagamento pagamento = servicePagamento.buscarPorId(id);
		
		Aluno aluno = serviceAluno.buscarPorId(pagamento.getAluno().getId());
		
		BigDecimal saldoAtual = pagamento.getAluno().getSaldo();
		saldoAtual = saldoAtual.add(pagamento.getValor());
		
		aluno.setSaldo(saldoAtual);	
		serviceAluno.editar(aluno);
		
		servicePagamento.excluir(id);
		model.addAttribute("success", "Pagamento excluído com sucesso.");
		return listar(model);
	}
	
}
