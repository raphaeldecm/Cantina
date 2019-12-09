package br.ifrn.edu.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifrn.edu.boot.dao.PagamentoDao;
import br.ifrn.edu.boot.model.Pagamento;

@Service @Transactional(readOnly = false)
public class PagamentoServiceImpl implements PagamentoService{

	@Autowired
	PagamentoDao dao;

	@Override
	public void salvar(Pagamento pagamento) {
		dao.save(pagamento);
	}

	@Override
	public void editar(Pagamento pagamento) {
		dao.update(pagamento);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override @Transactional(readOnly = true)
	public Pagamento buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override @Transactional(readOnly = true)
	public List<Pagamento> buscarTodos() {
		return dao.findAll();
	}
	
}
