package org.acme.movimientos.dtos;

import java.util.List;

public class MovimientosList {
	public MovimientosList() {
		super();
		// TODO Auto-generated constructor stub
	}

	private List<MovimientosDto> movimientos;

	public  List<MovimientosDto> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos( List<MovimientosDto> movimientos) {
		this.movimientos = movimientos;
	}
}
