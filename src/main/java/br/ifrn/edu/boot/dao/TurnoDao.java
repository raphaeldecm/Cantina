package br.ifrn.edu.boot.dao;

import java.util.List;

import br.ifrn.edu.boot.model.Turno;

public interface TurnoDao {

	void save(Turno turno);

    void update(Turno turno);

    void delete(Long id);

    Turno findById(Long id);

    List<Turno> findAll();
	
}
