package br.ifrn.edu.boot.service;

import java.util.List;

import br.ifrn.edu.boot.model.Pagamento;

public interface PagamentoService {

	void salvar(Pagamento pagamento);
	
	void editar(Pagamento pagamento);
	
	void excluir(Long id);
	
	Pagamento buscarPorId(Long id);
	
	List<Pagamento> buscarTodos();
	
}
