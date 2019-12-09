package br.ifrn.edu.boot.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TURNOS")
public class Turno extends AbstractEntity<Long> {

	@Column(name = "nome", nullable = false, length = 60)
	private String nome;
	
	@OneToMany(mappedBy = "turno")
	private List<Aluno> alunos;

	@OneToMany(mappedBy = "turno")
	private List<Turma> turmas;
	
	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
	
	
}
