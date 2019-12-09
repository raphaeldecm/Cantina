package br.ifrn.edu.boot.dao;

import java.util.List;

import br.ifrn.edu.boot.model.Pagamento;

public interface PagamentoDao {
	
	void save(Pagamento pagamento);

    void update(Pagamento pagamento);

    void delete(Long id);

    Pagamento findById(Long id);

    List<Pagamento> findAll();

}
