package br.ifrn.edu.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ifrn.edu.boot.model.Turma;
import br.ifrn.edu.boot.model.Turno;
import br.ifrn.edu.boot.service.TurmaService;
import br.ifrn.edu.boot.service.TurnoService;

@Controller
@RequestMapping("/turnos")
public class TurnoController {

	@Autowired
	private TurnoService serviceTurno;
	
	@Autowired
	private TurmaService serviceTurma;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Turno turno) {
		return "/turno/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("turnos", serviceTurno.buscarTodos());
		return "/turno/lista";
	}
	
	@GetMapping("/listarTurmas/{id}")
	public String listarTurmas(@PathVariable("id") Long id, ModelMap model) {
		
		Turno turno = serviceTurno.buscarPorId(id);
		List<Turma> turmas = serviceTurma.buscarTodosPorTurno(turno);
		List<Turno> turnos = serviceTurno.buscarTodos();
		
		model.addAttribute("turnos", turnos);
		model.addAttribute("turnoSelecionado", turno);
		model.addAttribute("turmas", turmas);
		return "/turno/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(Turno turno, RedirectAttributes attr) {
		serviceTurno.salvar(turno);
		attr.addFlashAttribute("success", "Turno cadastrado com sucesso");
		return "redirect:/turnos/cadastrar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("turno", serviceTurno.buscarPorId(id));
		return "/turno/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(Turno turno, RedirectAttributes attr) {
		serviceTurno.editar(turno);
		//Enviando alerta para página com attr
		attr.addFlashAttribute("success", "Turno editado com sucesso.");
		return "redirect:/turnos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		
		if (serviceTurno.turnoTemTurma(id)) {
			model.addAttribute("fail", "Turno não removido. Possui turma(s) vinculada(s).");
		} else {
			serviceTurno.excluir(id);
			model.addAttribute("success", "Turno excluído com sucesso.");
		}
		
		return listar(model);
	}
	
}
