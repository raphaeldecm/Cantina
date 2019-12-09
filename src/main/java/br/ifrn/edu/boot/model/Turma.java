package br.ifrn.edu.boot.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TURMAS")
public class Turma extends AbstractEntity<Long> {

	
	@Column(name = "nome", nullable = false, length = 60)
	private String nome;

	@OneToMany(mappedBy = "turma")
	private List<Aluno> alunos;

	@ManyToOne
	@JoinColumn(name = "turno_id_fk")
	private Turno turno;
	
	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
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
