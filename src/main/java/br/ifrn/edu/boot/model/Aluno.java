package br.ifrn.edu.boot.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@SuppressWarnings("serial")
@Entity
@Table(name = "ALUNOS")
public class Aluno extends AbstractEntity<Long>{

	@NotBlank(message = "Informe um nome.")
	@Size(max = 255, min = 3, message = "O nome deve estar entre {min} e {max} caracteres.")
	@Column(name = "nome", nullable = false, unique = true)
	private String nome;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "turma_id_fk")
	private Turma turma;
	
	@NotNull(message = "Selecione um turno.")
	@ManyToOne
	@JoinColumn(name = "turno_id_fk")
	private Turno turno;
	
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	@Column(nullable = false, columnDefinition = "DECIMAL(7,2) DEFAULT 0.00")
	private BigDecimal saldo;
	
	@Column(nullable = false, columnDefinition="tinyint(1) default 1")
	private boolean enabled;
	
	@OneToMany(mappedBy = "aluno")
	private List<Pagamento> pagamentos;
	
	@OneToMany(mappedBy = "aluno")
	private List<Compra> compras;

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}
	
	
}
