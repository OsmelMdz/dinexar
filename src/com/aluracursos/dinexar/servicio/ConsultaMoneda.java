package com.aluracursos.dinexar.util;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ConsultaMoneda {

    private static final String API_KEY = "6a41b669292dad8037785994"; // Reemplaza con tu clave

    public static Map<String, Double> obtenerTasas(String monedaBase) {
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + monedaBase;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Respuesta no válida de la API: " + response.statusCode());
            }

            Gson gson = new Gson();
            Map<?, ?> json = gson.fromJson(response.body(), Map.class);
            return (Map<String, Double>) json.get("conversion_rates");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al consultar tasas: " + e.getMessage());
        }
    }

    public static Map<String, String> obtenerDivisasDisponibles() {
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/codes";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error al obtener las divisas: " + response.statusCode());
            }

            Gson gson = new Gson();
            Map<String, Object> resultado = gson.fromJson(response.body(), Map.class);
            List<List<String>> lista = (List<List<String>>) resultado.get("supported_codes");

            return lista.stream()
                    .collect(Collectors.toMap(
                            item -> item.get(0),
                            item -> item.get(1),
                            (v1, v2) -> v1,
                            LinkedHashMap::new
                    ));

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al consultar divisas: " + e.getMessage());
        }
    }
}
