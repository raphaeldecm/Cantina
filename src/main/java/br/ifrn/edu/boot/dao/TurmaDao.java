package br.ifrn.edu.boot.dao;

import java.util.List;

import br.ifrn.edu.boot.model.Turma;
import br.ifrn.edu.boot.model.Turno;

public interface TurmaDao {

	void save(Turma turma);

    void update(Turma turma);

    void delete(Long id);

    Turma findById(Long id);

    List<Turma> findAll();

	List<Turma> findByTurno(Turno turno);
	
}
