package com.peregrinoti.entity;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class Revista {

	private Long id;

	private String nome;

	private Date ano;

	private Caixa caixa;

	private TipoColecao tipoColecao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public TipoColecao getTipoColecao() {
		return tipoColecao;
	}

	public void setTipoColecao(TipoColecao tipoColecao) {
		this.tipoColecao = tipoColecao;
	}
	
	@Override
	public String toString() {
		return this.nome;
	}

	public String getAnoFormatado() {
		DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return this.ano.toLocalDate().format(dataFormatada).toString();
	}
}
