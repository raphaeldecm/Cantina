package br.ifrn.edu.boot.controller.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.ifrn.edu.boot.model.Turno;
import br.ifrn.edu.boot.service.TurnoService;

@Component
public class StringToTurnoConverter implements Converter<String, Turno>{

	@Autowired
	private TurnoService service;
	
	@Override
	public Turno convert(String text) {
		// TODO Auto-generated method stub
		if(text.isEmpty()) {
			return null;
		} 
		Long id = Long.valueOf(text);
		return service.buscarPorId(id);
	}

}
