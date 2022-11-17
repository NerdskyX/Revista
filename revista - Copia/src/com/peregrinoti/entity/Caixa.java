package com.peregrinoti.entity;

public class Caixa {

	private Long id;

	private String cor;

	private String codigoEtiqueta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getCodigoEtiqueta() {
		return codigoEtiqueta;
	}

	public void setCodigoEtiqueta(String codigoEtiqueta) {
		this.codigoEtiqueta = codigoEtiqueta;
	}
	
	@Override
	public String toString() {
		return this.cor;
	}
}
