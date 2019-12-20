package br.ifrn.edu.boot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifrn.edu.boot.dao.AlunoDao;
import br.ifrn.edu.boot.model.Aluno;
import br.ifrn.edu.boot.model.Compra;
import br.ifrn.edu.boot.model.Pagamento;
import br.ifrn.edu.boot.model.Turma;

@Service @Transactional(readOnly = false)
public class AlunoServiceImpl implements AlunoService{

	@Autowired
	private AlunoDao dao;
	
	@Override
	public void salvar(Aluno aluno) {
		dao.save(aluno);
	}

	@Override
	public void editar(Aluno aluno) {
		dao.update(aluno);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override @Transactional(readOnly = true)
	public Aluno buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override @Transactional(readOnly = true)
	public List<Aluno> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public boolean alunoTemCompra(Long id) {
		if(buscarPorId(id).getCompras().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public List<Aluno> buscarTodosPorTurma(Turma turma) {
		List<Aluno> alunos = dao.findAll();
		
		Long idTurma = turma.getId();
		
		List<Aluno> alunosTurma = new ArrayList<Aluno>();
		
		for(Aluno o: alunos) {
			if(o.getTurma().getId() == idTurma) {
				alunosTurma.add(o);
			}
		}
		return alunosTurma;
	}

	@Override
	public boolean alunoTemPagamento(Long id) {
		if(buscarPorId(id).getPagamentos().isEmpty()) {
			return false;
		}
		return true;
	}
}
