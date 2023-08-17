package org.acme.movimientos.dtos;

import java.util.Date;

public class MovimientosDto {
	public MovimientosDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public MovimientosDto(String concepto, String fecha, String movimiento, String saldo, String referencia,
			String fechaAceptacion, String fechaLiquidacion, String emisor, String cuentaOrdenante,
			String nombreOrdenante, String receptor, String cuentaBeneficiario, String nombreBeneficiario,
			String claveRastreo, String tipoOperacion) {
		super();
		this.concepto = concepto;
		this.fecha = fecha;
		this.movimiento = movimiento;
		this.saldo = saldo;
		this.referencia = referencia;
		this.fechaAceptacion = fechaAceptacion;
		this.fechaLiquidacion = fechaLiquidacion;
		this.emisor = emisor;
		this.cuentaOrdenante = cuentaOrdenante;
		this.nombreOrdenante = nombreOrdenante;
		this.receptor = receptor;
		this.cuentaBeneficiario = cuentaBeneficiario;
		this.nombreBeneficiario = nombreBeneficiario;
		this.claveRastreo = claveRastreo;
		this.tipoOperacion = tipoOperacion;
	}


	private String concepto;//paymentSubject
	private String fecha;//date
	private String movimiento;//operationAmount
	private String saldo;//currentBalance
	private String referencia;//referenceNumber
	private String fechaAceptacion;//acceptanceDateTime
	private String fechaLiquidacion;//liquidationDateTime
	private String emisor;//payerBank
	private String cuentaOrdenante;//payerAccount
	private String nombreOrdenante;//payerName
	private String receptor;//beneficiaryBank
	private String cuentaBeneficiario;//beneficiaryAccount
	private String nombreBeneficiario;//beneficiaryName
	private String claveRastreo;//traceNumber
	private String tipoOperacion;//operationType
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	public String getSaldo() {
		return saldo;
	}
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getFechaAceptacion() {
		return fechaAceptacion;
	}
	public void setFechaAceptacion(String fechaAceptacion) {
		this.fechaAceptacion = fechaAceptacion;
	}
	public String getFechaLiquidacion() {
		return fechaLiquidacion;
	}
	public void setFechaLiquidacion(String fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	public String getCuentaOrdenante() {
		return cuentaOrdenante;
	}
	public void setCuentaOrdenante(String cuentaOrdenante) {
		this.cuentaOrdenante = cuentaOrdenante;
	}
	public String getNombreOrdenante() {
		return nombreOrdenante;
	}
	public void setNombreOrdenante(String nombreOrdenante) {
		this.nombreOrdenante = nombreOrdenante;
	}
	public String getReceptor() {
		return receptor;
	}
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}
	public String getCuentaBeneficiario() {
		return cuentaBeneficiario;
	}
	public void setCuentaBeneficiario(String cuentaBeneficiario) {
		this.cuentaBeneficiario = cuentaBeneficiario;
	}
	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}
	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}
	public String getClaveRastreo() {
		return claveRastreo;
	}
	public void setClaveRastreo(String claveRastreo) {
		this.claveRastreo = claveRastreo;
	}
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	
}
