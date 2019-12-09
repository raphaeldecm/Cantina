package br.ifrn.edu.boot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifrn.edu.boot.dao.TurmaDao;
import br.ifrn.edu.boot.model.Turma;
import br.ifrn.edu.boot.model.Turno;

@Service @Transactional(readOnly = false)
public class TurmaServiceImpl implements TurmaService{

	@Autowired
	private TurmaDao dao;
	
	@Override
	public void salvar(Turma turma) {
		dao.save(turma);
	}

	@Override
	public void editar(Turma turma) {
		dao.update(turma);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override @Transactional(readOnly = true)
	public Turma buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override @Transactional(readOnly = true)
	public List<Turma> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public boolean turmaTemAluno(Long id) {
		if(buscarPorId(id).getAlunos().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public List<Turma> buscarPorTurno(Turno turno) {
		// TODO Auto-generated method stub
		return dao.findByTurno(turno);
	}

	@Override
	public List<Turma> buscarTodosPorTurno(Turno turno) {
		List<Turma> turmas = dao.findAll();
		Long idTurno = turno.getId();
		List<Turma> turmasTurno = new ArrayList<Turma>();
		
		for(Object o: turmas) {
			if(((Turma)o).getTurno().getId() == idTurno) {
				turmasTurno.add((Turma) o);
			}
		}
		return turmasTurno;
	}

}
