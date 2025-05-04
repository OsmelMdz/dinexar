package com.aluracursos.dinexar.util;

import com.aluracursos.dinexar.servicio.ConsultaMoneda;

import java.util.*;

public class Monedas {
    private static final Map<String, String> nombresMonedas;
    private static final Map<String, String> nombreAcodigo = new HashMap<>();

    static {
        Map<String, String> monedas = Map.ofEntries(
                Map.entry("MXN", "Peso mexicano"),
                Map.entry("USD", "Dólar estadounidense"),
                Map.entry("EUR", "Euro"),
                Map.entry("JPY", "Yen japonés"),
                Map.entry("GBP", "Libra esterlina"),
                Map.entry("CNY", "Yuan chino"),
                Map.entry("CAD", "Dólar canadiense"),
                Map.entry("AUD", "Dólar australiano"),
                Map.entry("CHF", "Franco suizo"),
                Map.entry("KRW", "Won surcoreano"),
                Map.entry("BRL", "Real brasileño")
        );
        nombresMonedas = monedas.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(LinkedHashMap::new,
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                        Map::putAll);

        for (Map.Entry<String, String> entry : nombresMonedas.entrySet()) {
            nombreAcodigo.put(entry.getValue().toLowerCase(), entry.getKey());
        }
    }

    public static String leerMoneda(Scanner scanner, String tipo) {
        System.out.println("\n--- Seleccione la moneda de " + tipo + " ---");
        List<String> opcionesComunes = new ArrayList<>(nombresMonedas.values());
        for (int i = 0; i < opcionesComunes.size(); i++) {
            System.out.printf("%2d) %s%n", i + 1, opcionesComunes.get(i));
        }
        System.out.println("12) Ver todas las divisas disponibles");

        int seleccion = leerEntero(scanner, "Ingrese el número correspondiente: ", 1, 12);
        String monedaSeleccionada;
        String codigoMoneda;

        if (seleccion <= 11) {
            monedaSeleccionada = opcionesComunes.get(seleccion - 1);
            codigoMoneda = nombreAcodigo.get(monedaSeleccionada.toLowerCase());
        } else {
            Map<String, String> divisasDisponibles = ConsultaMoneda.obtenerDivisasDisponibles();
            Map<String, String> divisasFiltradas = divisasDisponibles.entrySet().stream()
                    .filter(entry -> !nombresMonedas.containsKey(entry.getKey()))
                    .sorted(Map.Entry.comparingByValue())
                    .collect(LinkedHashMap::new,
                            (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                            Map::putAll);

            List<String> lista = new ArrayList<>(divisasFiltradas.values());
            for (int i = 0; i < lista.size(); i++) {
                System.out.printf("%2d) %s%n", i + 1, lista.get(i));
            }

            int seleccionDivisa = leerEntero(scanner, "Ingrese el número correspondiente: ", 1, lista.size());
            monedaSeleccionada = lista.get(seleccionDivisa - 1);

            codigoMoneda = divisasFiltradas.entrySet().stream()
                    .filter(e -> e.getValue().equalsIgnoreCase(monedaSeleccionada))
                    .map(Map.Entry::getKey)
                    .findFirst().orElse(null);
        }

        if (codigoMoneda == null) {
            System.out.println("La moneda seleccionada no es válida.");
        }
        return codigoMoneda;
    }

    private static int leerEntero(Scanner scanner, String mensaje, int min, int max) {
        int valor;
        do {
            System.out.print(mensaje);
            while (!scanner.hasNextInt()) {
                System.out.print("Entrada no válida. " + mensaje);
                scanner.next();
            }
            valor = scanner.nextInt();
        } while (valor < min || valor > max);
        return valor;
    }
}