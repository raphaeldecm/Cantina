package br.ifrn.edu.boot.service;

import java.util.List;

import br.ifrn.edu.boot.model.Compra;

public interface CompraService {

	void salvar(Compra compra);
	
	void editar(Compra compra);
	
	void excluir(Long id);
	
	Compra buscarPorId(Long id);
	
	List<Compra> buscarTodos();
	
}
