package br.ifrn.edu.boot.dao;

import java.util.List;

import br.ifrn.edu.boot.model.Aluno;

public interface AlunoDao {

	void save(Aluno aluno);

    void update(Aluno aluno);

    void delete(Long id);

    Aluno findById(Long id);

    List<Aluno> findAll();
	
}
