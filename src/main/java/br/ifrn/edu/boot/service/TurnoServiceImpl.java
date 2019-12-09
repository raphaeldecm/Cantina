package br.ifrn.edu.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifrn.edu.boot.dao.TurnoDao;
import br.ifrn.edu.boot.model.Turno;

@Service @Transactional(readOnly = false)
public class TurnoServiceImpl implements TurnoService{

	@Autowired
	private TurnoDao dao;
	
	@Override
	public void salvar(Turno turno) {
		dao.save(turno);
	}

	@Override
	public void editar(Turno turno) {
		dao.update(turno);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override @Transactional(readOnly = true)
	public Turno buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override @Transactional(readOnly = true)
	public List<Turno> buscarTodos() {
		return dao.findAll();
	}

	@Override @Transactional(readOnly = true)
	public boolean turnoTemTurma(Long id) {
		if(buscarPorId(id).getTurmas().isEmpty()) {
			return false;
		}
		return true;
	}

}
