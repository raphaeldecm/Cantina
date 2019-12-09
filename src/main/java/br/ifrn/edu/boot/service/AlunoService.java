package br.ifrn.edu.boot.service;

import java.util.List;

import br.ifrn.edu.boot.model.Aluno;
import br.ifrn.edu.boot.model.Turma;

public interface AlunoService {

	void salvar(Aluno aluno);
	
	void editar(Aluno aluno);
	
	void excluir(Long id);
	
	Aluno buscarPorId(Long id);
	
	List<Aluno> buscarTodos();

	boolean alunoTemCompra(Long id);
	
	public List<Aluno> buscarTodosPorTurma(Turma turma);
	
}
