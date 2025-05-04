# Curso de Practicando con Java - Challenge Conversor de Monedas

Este repositorio contiene el proyecto **Dinexar**, desarrollado como parte del curso **"Practicando con Java: Challenge Conversor de Monedas"** impartido por **Génesys Rondón** a través de **Alura Latam**.

Durante este curso, apliqué habilidades fundamentales para el desarrollo de aplicaciones Java que consumen APIs, manejan estructuras de datos dinámicas y brindan una experiencia de usuario intuitiva y robusta.

## Descripción

**Dinexar** es un conversor de divisas que permite al usuario ingresar un monto en su moneda local y obtener el equivalente en otra divisa, utilizando la **API ExchangeRate-API** para obtener las tasas de cambio actualizadas en tiempo real.

El programa muestra inicialmente las 10 divisas más comerciales (incluyendo el Peso Mexicano) para facilitar la selección, aunque también puede accederse a todas las divisas disponibles a través de un menú dinámico.

### Funcionalidades:
- Selección interactiva de monedas de origen y destino.
- Conversión precisa utilizando tasas oficiales.
- Listado completo de monedas con sus nombres en español.
- Manejo de errores para entradas inválidas y problemas de conexión.
- Persistencia opcional de resultados en consola o archivo (en futuras versiones).

## **Estructura del Proyecto**
```plaintext
dinexar-java/
│── src/                        # Código fuente del proyecto
│   └── com/
│       └── aluracursos/         # Paquete base del proyecto
│           └── dinexar/         # Subpaquete específico de la aplicación
│               ├── modelo/      # Contiene las clases modelo para el historial
│               │   └── Conversion.java
│               ├── servicio/    # Contiene las clases de servicios
│               │   └── ConsultaMoneda.java
│               ├── util/        # Funciones y utilidades generales
│               │   └── Monedas.java
│               ├── aplicacion/  # Contiene la clase principal de la aplicación
│               │   └── AplicacionDinexar.java
│               └── main/        # Contiene la clase principal de ejecución
│                   └── Principal.java
│── .gitignore                  # Archivos y directorios ignorados por Git
│── README.md                    # Documentación del proyecto
```

## Tecnologías utilizadas
- **Java**: Lenguaje de programación principal.
- **JDK 17.0.6**: Versión del Java Development Kit utilizada.
- **IntelliJ IDEA**: Entorno de desarrollo integrado (IDE).
- **ExchangeRate-API**: Servicio web para consultar tasas de cambio de divisas.
- **HTTP Client (Java)**: Cliente para consumir la API.
- **Map y List**: Para el manejo de colecciones dinámicas y asociaciones clave-valor.

## Requisitos
- Tener instalado [Java JDK 17.0.6](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
- IntelliJ IDEA u otro IDE compatible.
- Conexión a internet para consultar la API.
- (Opcional) Dependencias externas si decides ampliar la funcionalidad.

## Cómo ejecutar el proyecto

1. Clona el repositorio en tu equipo local.
2. Abre el proyecto con tu IDE Java preferido.
3. Ejecuta la clase `Principal.java` ubicada en el paquete `main`.
4. Sigue las instrucciones en consola para seleccionar divisas y realizar conversiones.
5. Ingresa el monto a convertir y obtén el resultado en tiempo real.

> Nota: Si introduces una divisa no disponible o un valor inválido, el sistema te lo indicará mediante un mensaje claro.

## Lo aprendido en este curso
- Consumo de APIs con `HttpClient` y procesamiento de respuestas.
- Manipulación de colecciones (`Map`, `List`) para gestionar datos dinámicos.
- Separación lógica en capas (modelo, servicio, utilidad y presentación).
- Validación de entrada del usuario y manejo de excepciones.
- Diseño de una interfaz interactiva en consola, amigable para el usuario.

## Instructor
**Génesys Rondón**  
Ingeniera de Sistemas, especializada en desarrollo web Back End.  
Con experiencia en Java, C#, C++, JavaScript, Node.js, Spring y ASP.NET Core.  
Amante de los gatos, los videojuegos y la literatura clásica.  
LinkedIn: https://www.linkedin.com/in/genesysrondon914762182/  
GitHub: https://github.com/genesysrm
