package com.aluracursos.dinexar.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm:ss");
        String formattedTimestamp = timestamp.format(formatter);

        return String.format("Conversión de %s = %.2f → %s = %.2f (Realizado el %s)",
                origen, monto, destino, resultado, formattedTimestamp);
    }
}