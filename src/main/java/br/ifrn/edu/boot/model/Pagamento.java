package br.ifrn.edu.boot.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "PAGAMENTOS")
public class Pagamento extends AbstractEntity<Long>{

	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	@Column(name = "valor_pagamento", nullable = false, columnDefinition = "DECIMAL(7,2) DEFAULT 0.00")
	private BigDecimal valor;
	
	@Column(name= "data_pagamento", nullable = false, columnDefinition = "DATE")
	private Date dataPagamento;
	
	@ManyToOne
	@JoinColumn(name = "aluno_id_fk")
	private Aluno aluno;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	
	
}
