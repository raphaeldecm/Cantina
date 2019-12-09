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
import br.ifrn.edu.boot.model.Turma;
import br.ifrn.edu.boot.model.Turno;
import br.ifrn.edu.boot.service.AlunoService;
import br.ifrn.edu.boot.service.TurmaService;
import br.ifrn.edu.boot.service.TurnoService;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

	@Autowired
	private AlunoService serviceAluno;
	
	@Autowired
	private TurmaService serviceTurma;
	
	@Autowired
	private TurnoService serviceTurno;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Aluno aluno) {
		return "/aluno/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("alunos", serviceAluno.buscarTodos());
		return "/aluno/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Aluno aluno, BindingResult result, RedirectAttributes attr) throws BindException {
		if(aluno.getSaldo() == null) {
			aluno.setSaldo(BigDecimal.ZERO);
		}
		if(result.hasErrors()) {
			attr.addFlashAttribute("fail", "Aluno não cadastrado. Todos os campos deste formulário são obrigatórios.");
			return "redirect:/alunos/cadastrar";
		} else {
			try {
				serviceAluno.salvar(aluno);
				attr.addFlashAttribute("success", "Aluno cadastrado com sucesso");
				return "redirect:/alunos/cadastrar";
			} catch (ConstraintViolationException ex){
				attr.addFlashAttribute("fail", "Aluno não cadastrado."+ex.getMessage());
				return "redirect:/alunos/cadastrar";
			} catch (DataIntegrityViolationException e) {
				attr.addFlashAttribute("fail", "Aluno não cadastrado. Já existe um aluno cadastrado com este nome");
				return "redirect:/alunos/cadastrar";
			}
		}
	}
	
	@ModelAttribute("turmas")
	public List<Turma> listaDeTurmas(){
		//return departamentoService.buscarTodos();
		return serviceTurma.buscarTodos();
	}
		
	@ModelAttribute("turnos")
	public List<Turno> listaDeTurnos(){
		//return departamentoService.buscarTodos();
		return serviceTurno.buscarTodos();
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("aluno", serviceAluno.buscarPorId(id));
		return "/aluno/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(Aluno aluno, RedirectAttributes attr) {
		serviceAluno.editar(aluno);
		//Enviando alerta para página com attr
		attr.addFlashAttribute("success", "Aluno editado com sucesso.");
		return "redirect:/alunos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		
		if (serviceAluno.alunoTemCompra(id)) {
			model.addAttribute("fail", "Aluno não removido. Possui compra(s) vinculada(s).");
		} else {
			serviceAluno.excluir(id);
			model.addAttribute("success", "Aluno excluído com sucesso.");
		}
		
		return listar(model);
	}
	
	@GetMapping(value="/cadastrar/{id}/{nome}")
	public @ResponseBody ModelAndView getTurmas(@PathVariable("id") Long id, @PathVariable("nome") String nome) {
		
		Turno turno = serviceTurno.buscarPorId(id);
		List<Turma> turmas = serviceTurma.buscarTodosPorTurno(turno);
		
		ModelAndView mav = new ModelAndView("aluno/cadastro");
		
		Aluno aluno = new Aluno();
		aluno.setNome(nome);
//		mav.addObject("selecionado", turno.getId());
		mav.addObject("turmasLista", turmas);
		mav.addObject("aluno", aluno);

		return mav;
	}	
}
