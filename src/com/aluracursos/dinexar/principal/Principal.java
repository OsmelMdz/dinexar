package com.aluracursos.dinexar.principal;

import com.aluracursos.dinexar.servicio.ConsultaMoneda;
import com.aluracursos.dinexar.modelo.Conversion;

import java.util.*;

public class Principal {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Conversion> historialConversiones = new ArrayList<>();

    private static final Map<String, String> nombresMonedas;

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
    }

    private static final Map<String, String> nombreAcodigo = new HashMap<>();

    static {
        for (Map.Entry<String, String> entry : nombresMonedas.entrySet()) {
            nombreAcodigo.put(entry.getValue().toLowerCase(), entry.getKey());
        }
    }

    public static void main(String[] args) {
        mostrarEncabezado();

        boolean continuar = true;

        while (continuar) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1) Realizar conversión");
            System.out.println("2) Ver historial de conversiones");
            System.out.print("Ingrese el número correspondiente: ");
            int opcion = scanner.nextInt();

            if (opcion == 1) {
                String origen = leerMoneda("origen");
                String destino = leerMoneda("destino");
                procesarConversion(origen, destino);
            } else if (opcion == 2) {
                mostrarHistorial();
            }

            System.out.print("\n¿Desea realizar otra operación? (s/n): ");
            String respuesta = scanner.next().trim().toLowerCase();
            continuar = respuesta.equals("s");
        }

        System.out.println("\nGracias por usar Dinexar. ¡Hasta la próxima!");
    }

    private static void mostrarEncabezado() {
        String titulo = "CONVERSOR DE MONEDAS DINEXAR";
        String borde = "=".repeat(titulo.length() + 10);
        System.out.println(borde);
        System.out.printf("%" + (borde.length() / 2 + titulo.length() / 2) + "s%n", titulo);
        System.out.println(borde);
    }

    private static void imprimirSeccion(String titulo) {
        String borde = "-".repeat(titulo.length() + 4);
        System.out.println("\n" + borde);
        System.out.println("| " + titulo + " |");
        System.out.println(borde);
    }

    private static void mostrarOpciones(List<String> opciones) {
        for (int i = 0; i < opciones.size(); i++) {
            System.out.printf("%2d) %s%n", i + 1, opciones.get(i));
        }
    }

    private static void mostrarHistorial() {
        if (historialConversiones.isEmpty()) {
            System.out.println("No hay conversiones registradas.");
        } else {
            System.out.println("\nHistorial de conversiones:");
            for (Conversion conversion : historialConversiones) {
                System.out.println(conversion);
            }
        }
    }

    private static int leerEntero(String mensaje, int min, int max) {
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

    private static double leerDecimal(String mensaje) {
        double valor;
        do {
            System.out.print(mensaje);
            while (!scanner.hasNextDouble()) {
                System.out.print("Entrada no válida. " + mensaje);
                scanner.next();
            }
            valor = scanner.nextDouble();
            if (valor <= 0) System.out.println("Debe ingresar un número mayor que cero.");
        } while (valor <= 0);
        return valor;
    }

    private static String leerMoneda(String tipo) {
        imprimirSeccion("Moneda de " + tipo);

        List<String> opcionesComunes = new ArrayList<>(nombresMonedas.values());
        mostrarOpciones(opcionesComunes);

        System.out.println("\n12) Ver todas las divisas disponibles");

        int seleccion = leerEntero("Ingrese el número correspondiente: ", 1, 12);

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

            System.out.println("\nSeleccione una divisa de la siguiente lista:");
            List<String> divisasDisponiblesList = new ArrayList<>(divisasFiltradas.values());
            mostrarOpciones(divisasDisponiblesList);
            int seleccionDivisa = leerEntero("Ingrese el número correspondiente: ", 1, divisasDisponiblesList.size());
            monedaSeleccionada = divisasDisponiblesList.get(seleccionDivisa - 1);

            codigoMoneda = divisasFiltradas.entrySet().stream()
                    .filter(e -> e.getValue().equalsIgnoreCase(monedaSeleccionada))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);
        }

        if (codigoMoneda == null) {
            System.out.println("La moneda seleccionada no es válida.");
            return null;
        }

        return codigoMoneda;
    }

    private static void procesarConversion(String origen, String destino) {
        if (origen == null || destino == null) {
            System.out.println("Una de las monedas seleccionadas no es válida.");
            return;
        }

        System.out.printf("\nHas seleccionado: %s → %s%n", origen, destino);
        double monto = leerDecimal("Ingrese el monto a convertir: ");

        try {
            Map<String, Double> tasas = ConsultaMoneda.obtenerTasas(origen);
            if (tasas.containsKey(destino)) {
                double tasa = tasas.get(destino);
                double convertido = monto * tasa;
                System.out.printf("Tasa actual: 1 %s = %.4f %s%n", origen, tasa, destino);
                System.out.printf("Resultado: %.2f %s = %.2f %s%n", monto, origen, convertido, destino);
                historialConversiones.add(new Conversion(origen, destino, monto, convertido));
            } else {
                System.out.println("No se encontró la tasa de cambio para la moneda seleccionada.");
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al obtener las tasas de cambio: " + e.getMessage());
        }
    }
}
