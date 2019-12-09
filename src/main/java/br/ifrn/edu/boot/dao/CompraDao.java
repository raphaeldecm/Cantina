package br.ifrn.edu.boot.dao;

import java.util.List;

//import br.ifrn.edu.boot.model.Aluno;
import br.ifrn.edu.boot.model.Compra;

public interface CompraDao {

	void save(Compra compra);

    void update(Compra compra);

    void delete(Long id);

    Compra findById(Long id);

    List<Compra> findAll();
    
    //List<Compra> findCompras(Aluno aluno);
	
}
