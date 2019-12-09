package br.ifrn.edu.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ifrn.edu.boot.model.Aluno;
import br.ifrn.edu.boot.model.Turma;
import br.ifrn.edu.boot.model.Turno;
import br.ifrn.edu.boot.service.AlunoService;
import br.ifrn.edu.boot.service.TurmaService;
import br.ifrn.edu.boot.service.TurnoService;

@Controller
@RequestMapping("/turmas")
public class TurmaController {

	@Autowired
	private TurmaService serviceTurma;
	
	@Autowired 
	private TurnoService serviceTurno;
	
	@Autowired
	private AlunoService serviceAluno;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Turma turma) {
		return "/turma/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("turmas", serviceTurma.buscarTodos());
		return "/turma/lista";
	}
	
	@GetMapping("/listarAlunos/{id}")
	public String listarTurmas(@PathVariable("id") Long id, ModelMap model) {
		
		Turma turma = serviceTurma.buscarPorId(id);
		
		List<Turma> turmas = serviceTurma.buscarTodos();
		
		List<Aluno> alunos= serviceAluno.buscarTodosPorTurma(turma);
		
		model.addAttribute("turmas", turmas);
		model.addAttribute("turmaSelecionada", turma);
		model.addAttribute("alunos", alunos);
		return "/turma/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(Turma turma, RedirectAttributes attr) {
		serviceTurma.salvar(turma);
		attr.addFlashAttribute("success", "Turma cadastrado com sucesso");
		return "redirect:/turmas/cadastrar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("turma", serviceTurma.buscarPorId(id));
		return "/turma/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(Turma turma, RedirectAttributes attr) {
		serviceTurma.editar(turma);
		//Enviando alerta para página com attr
		attr.addFlashAttribute("success", "Turma editada com sucesso.");
		return "redirect:/turmas/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		
		if (serviceTurma.turmaTemAluno(id)) {
			model.addAttribute("fail", "Turma não removida. Possui aluno(s) vinculado(s).");
		} else {
			serviceTurma.excluir(id);
			model.addAttribute("success", "Turma excluído com sucesso.");
		}
		
		return listar(model);
	}
	
	@ModelAttribute("turnos")
	public List<Turno> listaDeTurnos(){
		//return departamentoService.buscarTodos();
		return serviceTurno.buscarTodos();
	}
	
}
