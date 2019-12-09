package br.ifrn.edu.boot.controller.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.ifrn.edu.boot.model.Turma;
import br.ifrn.edu.boot.service.TurmaService;

@Component
public class StringToTurmaConverter implements Converter<String, Turma>{

	@Autowired
	private TurmaService service;

	@Override
	public Turma convert(String text) {
		// TODO Auto-generated method stub
		if(text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return service.buscarPorId(id);
	}
}
