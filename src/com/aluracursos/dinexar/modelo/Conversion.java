package com.aluracursos.dinexar.servicio;

import java.time.LocalDateTime;

public class Conversion {
    private final String origen;
    private final String destino;
    private final double monto;
    private final double resultado;
    private final LocalDateTime timestamp;

    public Conversion(String origen, String destino, double monto, double resultado) {
        this.origen = origen;
        this.destino = destino;
        this.monto = monto;
        this.resultado = resultado;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("%s → %s: %.2f → %.2f (Realizado el %s)",
                origen, destino, monto, resultado, timestamp);
    }
}

