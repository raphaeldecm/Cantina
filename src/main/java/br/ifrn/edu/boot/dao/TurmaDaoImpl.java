package br.ifrn.edu.boot.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.ifrn.edu.boot.model.Turma;
import br.ifrn.edu.boot.model.Turno;

@Repository
public class TurmaDaoImpl extends AbstractDao<Turma, Long> implements TurmaDao{
	
	@SuppressWarnings("unchecked")
	protected
	final Class<Turma> entityClass = 
			(Class<Turma>) ( (ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	public List<Turma> findByTurno(Turno turno) {
		
		return entityManager
				.createQuery("from " + entityClass.getSimpleName(), entityClass)
				.getResultList();
	}	

}
