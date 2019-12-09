package br.ifrn.edu.boot.controller.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.ifrn.edu.boot.model.Aluno;
import br.ifrn.edu.boot.service.AlunoService;

@Component
public class StringToAlunoConverter implements Converter<String, Aluno>{

	@Autowired
	private AlunoService serviceAluno;
	
	@Override
	public Aluno convert(String text) {
		// TODO Auto-generated method stub
		if(text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return serviceAluno.buscarPorId(id);
	}

}
