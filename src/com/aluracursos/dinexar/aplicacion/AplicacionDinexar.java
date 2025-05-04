package com.aluracursos.dinexar.aplicacion;

import com.aluracursos.dinexar.servicio.ConsultaMoneda;
import com.aluracursos.dinexar.modelo.Conversion;
import com.aluracursos.dinexar.util.Monedas;

import java.util.*;

public class AplicacionDinexar {
    private final Scanner scanner = new Scanner(System.in);
    private final List<Conversion> historialConversiones = new ArrayList<>();

    public void iniciar() {
        mostrarEncabezado();

        boolean continuar = true;
        while (continuar) {
            try {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1) Realizar conversión");
                System.out.println("2) Ver historial de conversiones");
                System.out.println("3) Salir");
                System.out.print("Ingrese el número correspondiente: ");

                int opcion = leerEntero();

                switch (opcion) {
                    case 1 -> procesarConversion();
                    case 2 -> mostrarHistorial();
                    case 3 -> {
                        continuar = false;
                        break;
                    }
                    default -> System.out.println("Opción no válida. Por favor, seleccione 1, 2 o 3.");
                }

                if (continuar && opcion != 3) {
                    System.out.print("\n¿Desea realizar otra operación? (s/n): ");
                    String respuesta = scanner.nextLine().trim().toLowerCase();
                    while (!respuesta.equals("s") && !respuesta.equals("n")) {
                        System.out.print("Respuesta inválida. Por favor ingrese 's' para sí o 'n' para no: ");
                        respuesta = scanner.nextLine().trim().toLowerCase();
                    }
                    continuar = respuesta.equals("s");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese solo números enteros para las opciones del menú.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                scanner.nextLine();
            }
        }

        System.out.println("\nGracias por usar Dinexar. ¡Hasta la próxima!");
    }

    private void mostrarEncabezado() {
        String titulo = "CONVERSOR DE MONEDAS DINEXAR";
        String borde = "=".repeat(titulo.length() + 10);
        System.out.println(borde);
        System.out.printf("%" + (borde.length() / 2 + titulo.length() / 2) + "s%n", titulo);
        System.out.println(borde);
    }

    private void mostrarHistorial() {
        if (historialConversiones.isEmpty()) {
            System.out.println("No hay conversiones registradas.");
        } else {
            System.out.println("\nHistorial de conversiones:");
            historialConversiones.forEach(System.out::println);
        }
    }

    private void procesarConversion() {
        String origen = Monedas.leerMoneda(scanner, "origen");
        if (origen == null) return;

        String destino;
        do {
            destino = Monedas.leerMoneda(scanner, "destino");
            if (destino == null) return;

            if (origen.equals(destino)) {
                System.out.println("La moneda de origen y destino no pueden ser la misma. Por favor, elija una diferente.");
            }
        } while (origen.equals(destino));

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

    private int leerEntero() {
        while (true) {
            try {
                String entrada = scanner.nextLine().trim();
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Ingrese un número entero: ");
            }
        }
    }

    private double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            scanner.nextLine();
            String entrada = scanner.nextLine().trim().replace(",", ".");
            System.out.println("Entrada recibida: " + entrada);
            try {
                double valor = Double.parseDouble(entrada);
                if (valor <= 0) {
                    System.out.println("Debe ingresar un número mayor que cero.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intente nuevamente con un número decimal válido.");
            }
        }
    }
}