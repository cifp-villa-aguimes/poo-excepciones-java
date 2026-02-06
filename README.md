# 📚 Sistema de Biblioteca - Manejo de Excepciones en Java

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![POO](https://img.shields.io/badge/POO-Excepciones-blue?style=for-the-badge)
![Nivel](https://img.shields.io/badge/Nivel-1º%20DAW%20%2F%20DAM-green?style=for-the-badge)

_Un proyecto educativo para aprender el manejo de excepciones en Programación Orientada a Objetos_

</div>

---

> 📖 **¿Buscas la teoría?** Consulta el archivo **[TEORIA.md](TEORIA.md)** para ver los conceptos explicados paso a paso.

---

## 🎯 ¿Qué vas a aprender?

Este proyecto simula un **sistema de préstamo de libros de una biblioteca** donde aprenderás a:

| Concepto                      | Descripción                                               |
| ----------------------------- | --------------------------------------------------------- |
| ✅ Excepciones estándar       | Usar `IllegalArgumentException` y `NullPointerException`  |
| ✅ Excepciones personalizadas | Crear tus propias excepciones desde cero (de menos a más) |
| ✅ Bloques try-catch          | Capturar y manejar errores de forma elegante              |
| ✅ Multi-catch                | Capturar varias excepciones en un solo bloque (Java 7+)   |
| ✅ Buenas prácticas           | Validar datos, documentar código y usar `toString()`      |

---

## 🏗️ Estructura del Proyecto

```
📦 POO Excepciones
 ┣ 📂 src/edu/damw
 ┃ ┣ 📂 app
 ┃ ┃ ┗ 📜 AppMain.java          ← 🚀 Punto de entrada (main)
 ┃ ┣ 📂 controller
 ┃ ┃ ┗ 📜 GestorUsuarios.java   ← 🔧 Gestiona la lista de usuarios
 ┃ ┣ 📂 exceptions
 ┃ ┃ ┣ 📜 UsuarioNoEncontradoException.java      ← ⭐ Nivel básico
 ┃ ┃ ┣ 📜 LibroNoDisponibleException.java        ← ⭐⭐ Nivel intermedio
 ┃ ┃ ┗ 📜 LimitePrestamosExcedidoException.java  ← ⭐⭐⭐ Nivel avanzado
 ┃ ┗ 📂 model
 ┃   ┣ 📜 EstadoLibro.java      ← 🏷️ Enum con los estados del libro
 ┃   ┣ 📜 Libro.java            ← 📖 Clase que representa un libro
 ┃   ┗ 📜 Usuario.java          ← 👤 Clase que representa un usuario
 ┗ 📜 README.md
```

---

## 🎮 ¿Cómo funciona el sistema?

### 📖 Los Libros

Cada libro tiene un **título** y un **estado**:

- 🟢 `DISPONIBLE` - Listo para préstamo
- 🔴 `PRESTADO` - Alguien lo tiene
- 🟡 `RESERVADO` - Reservado para otro usuario

### 👤 Los Usuarios

- Pueden **pedir prestados** libros (máximo 3)
- Pueden **devolver** libros
- Se valida que nombre y edad sean correctos

### ⚠️ ¿Qué puede salir mal?

| Situación                         | Excepción que se lanza             | Tipo      |
| --------------------------------- | ---------------------------------- | --------- |
| Usuario pide un 4º libro          | `LimitePrestamosExcedidoException` | Checked   |
| El libro ya está prestado         | `LibroNoDisponibleException`       | Checked   |
| Se busca un usuario que no existe | `UsuarioNoEncontradoException`     | Checked   |
| Nombre vacío o edad negativa      | `IllegalArgumentException`         | Unchecked |
| Título del libro vacío            | `IllegalArgumentException`         | Unchecked |
| Se intenta agregar usuario `null` | `NullPointerException`             | Unchecked |

---

## 🌟 Las 3 Excepciones Personalizadas

### ⭐ Nivel Básico - `UsuarioNoEncontradoException`

> La más sencilla. Solo envía un mensaje personalizado.

```java
public class UsuarioNoEncontradoException extends Exception {
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);  // Llamamos al constructor de Exception con el mensaje
    }
}
```

**💡 ¿Cuándo se usa?** Cuando buscamos un usuario por nombre y no existe.

---

### ⭐⭐ Nivel Intermedio - `LibroNoDisponibleException`

> Guarda información extra (título y estado del libro) y sugiere una alternativa.

```java
public class LibroNoDisponibleException extends Exception {
    private String tituloLibro;   // Guardamos datos extra
    private String estadoLibro;

    public LibroNoDisponibleException(String tituloLibro, String estadoLibro) {
        super("El libro '" + tituloLibro + "' no está disponible. Estado: " + estadoLibro);
        this.tituloLibro = tituloLibro;
        this.estadoLibro = estadoLibro;
    }

    // Método extra que sugiere qué hacer
    public String sugerirAlternativa() {
        return estadoLibro.equalsIgnoreCase("PRESTADO")
            ? "💡 Sugerencia: Espera a que el libro sea devuelto o intenta reservarlo."
            : "💡 Sugerencia: Consulta con la biblioteca sobre su disponibilidad.";
    }
}
```

**💡 ¿Cuándo se usa?** Cuando alguien quiere un libro que no está disponible.

---

### ⭐⭐⭐ Nivel Avanzado - `LimitePrestamosExcedidoException`

> La más completa. Guarda datos, tiene constantes y sobreescribe `getMessage()`.

```java
public class LimitePrestamosExcedidoException extends Exception {
    private String usuario;
    private int librosPrestados;
    private static final int LIMITE_PRESTAMOS = 3;  // Constante

    public LimitePrestamosExcedidoException(String usuario, int librosPrestados) {
        super("Usuario " + usuario + " ha intentado pedir más de " + LIMITE_PRESTAMOS + " libros.");
        this.usuario = usuario;
        this.librosPrestados = librosPrestados;
    }

    // Sobreescribimos getMessage() para dar más información
    @Override
    public String getMessage() {
        return super.getMessage() +
               "\n📚 Libros prestados actualmente: " + librosPrestados +
               "\n" + sugerirSolucion();
    }

    public String sugerirSolucion() {
        return "💡 Sugerencia: Devuelve algún libro antes de intentar un nuevo préstamo.";
    }
}
```

**💡 ¿Cuándo se usa?** Cuando un usuario ya tiene 3 libros y quiere otro.

---

## 🔍 Uso de Excepciones Estándar de Java

No siempre necesitas crear excepciones personalizadas. Java ya tiene muchas que puedes usar:

### `IllegalArgumentException` en `Usuario.java` y `Libro.java`

```java
// En Usuario.java - validamos nombre y edad
public Usuario(String nombre, int edad) {
    if (nombre == null || nombre.trim().isEmpty()) {
        throw new IllegalArgumentException("El nombre no puede estar vacío.");
    }
    if (edad < 0) {
        throw new IllegalArgumentException("La edad no puede ser negativa.");
    }
    this.nombre = nombre;
    this.edad = edad;
}

// En Libro.java - validamos el título
public Libro(String titulo) {
    if (titulo == null || titulo.trim().isEmpty()) {
        throw new IllegalArgumentException("El título del libro no puede estar vacío.");
    }
    this.titulo = titulo;
}
```

### `NullPointerException` en `GestorUsuarios.java`

```java
public void agregarUsuario(Usuario usuario) {
    // ✅ Validación usando excepción estándar de Java (Unchecked)
    if (usuario == null) {
        throw new NullPointerException("El usuario no puede ser nulo.");
    }
    usuarios.add(usuario);
}
```

---

## 🚀 Ejecutar el Proyecto

### Desde la terminal

```bash
# 1. Compilar
javac -d bin src/edu/damw/**/*.java

# 2. Ejecutar
java -cp bin edu.damw.app.AppMain
```

### Desde VS Code

1. Abre el archivo `AppMain.java`
2. Haz clic en ▶️ **Run** que aparece encima del `main`

---

## 📊 Salida Esperada

Al ejecutar el programa verás algo como esto:

```
╔══════════════════════════════════════════════════════════════╗
║     📚 SISTEMA DE BIBLIOTECA - MANEJO DE EXCEPCIONES 📚     ║
╚══════════════════════════════════════════════════════════════╝

📖 === PARTE 1: CREACIÓN DE LIBROS ===
✅ 📖 Libro: 'El Señor de los Anillos' | Estado: DISPONIBLE
✅ 📖 Libro: 'Cien años de soledad' | Estado: DISPONIBLE
✅ 📖 Libro: 'Don Quijote de la Mancha' | Estado: DISPONIBLE
✅ 📖 Libro: 'El Principito' | Estado: DISPONIBLE
❌ Error al crear libro: El título del libro no puede estar vacío.

👤 === PARTE 2: CREACIÓN DE USUARIOS ===
Probando validación con IllegalArgumentException...

❌ Error al crear usuario: El nombre no puede estar vacío.
❌ Error al crear usuario: La edad no puede ser negativa.
✅ Usuario creado: 👤 Usuario: Juan | Edad: 25 | Libros prestados: 0

📚 === PARTE 3: PRÉSTAMO DE LIBROS ===
Probando LibroNoDisponibleException y LimitePrestamosExcedidoException...

✅ Libro prestado con éxito. Total libros actuales: 1
   Juan ha pedido prestado: 'El Señor de los Anillos'

❌ Error: El libro 'El Señor de los Anillos' no está disponible. Estado actual: PRESTADO
💡 Sugerencia: Espera a que el libro sea devuelto o intenta reservarlo.

✅ Libro prestado con éxito. Total libros actuales: 2
   Juan ha pedido prestado: 'Cien años de soledad'

✅ Libro prestado con éxito. Total libros actuales: 3
   Juan ha pedido prestado: 'Don Quijote de la Mancha'

❌ Error: Usuario Juan ha intentado pedir prestado más de 3 libros.
📚 Libros prestados actualmente: 3
💡 Sugerencia: Devuelve algún libro antes de intentar un nuevo préstamo.

📥 === PARTE 4: DEVOLUCIÓN DE LIBROS ===
✅ Libro devuelto con éxito. Libros restantes: 2
   Juan ha devuelto: 'El Señor de los Anillos'
...

🔧 === PARTE 5: GESTIÓN DE USUARIOS ===
❌ Error: El usuario no puede ser nulo.
✅ Usuario 'Juan' agregado al sistema.
✅ Usuario 'María' agregado al sistema.
✅ Usuario 'Carlos' agregado al sistema.

📋 === LISTA DE USUARIOS ===
   👤 Usuario: Juan | Edad: 25 | Libros prestados: 0
   👤 Usuario: María | Edad: 30 | Libros prestados: 0
   👤 Usuario: Carlos | Edad: 22 | Libros prestados: 0
============================

🔍 === PARTE 6: BÚSQUEDA DE USUARIOS ===
✅ Usuario encontrado: 👤 Usuario: Juan | Edad: 25 | Libros prestados: 0

❌ Error: Usuario con nombre 'Pedro' no encontrado.

╔══════════════════════════════════════════════════════════════╗
║              ✅ FIN DE LA DEMOSTRACIÓN ✅                    ║
╚══════════════════════════════════════════════════════════════╝
```

---

## 📝 Resumen de Conceptos

| Concepto            | ¿Qué es?                                               | Ejemplo en el proyecto                              |
| ------------------- | ------------------------------------------------------ | --------------------------------------------------- |
| `try-catch`         | Captura excepciones para que el programa no se detenga | `AppMain.java`                                      |
| `multi-catch`       | Captura varias excepciones en un solo bloque           | `catch (Excepcion1 \| Excepcion2 e)`                |
| `throw`             | Lanza una excepción cuando algo va mal                 | `Usuario.java`, `Libro.java`, `GestorUsuarios.java` |
| `throws`            | Indica que un método puede lanzar una excepción        | `prestarLibro()`, `buscarUsuarioPorNombre()`        |
| `extends Exception` | Crea una excepción personalizada (Checked)             | Todas las excepciones en `/exceptions`              |
| `getMessage()`      | Obtiene el mensaje de error                            | Sobreescrito en `LimitePrestamosExcedidoException`  |
| `toString()`        | Representación en texto de un objeto                   | `Usuario.java`, `Libro.java`                        |

---

## 🧠 ¿Checked o Unchecked?

| Tipo             | ¿Se obliga a manejarla?        | Hereda de...       | Ejemplo en el proyecto                             |
| ---------------- | ------------------------------ | ------------------ | -------------------------------------------------- |
| **Checked** ✅   | Sí, con `try-catch` o `throws` | `Exception`        | Nuestras 3 excepciones personalizadas              |
| **Unchecked** ❌ | No, pero es recomendable       | `RuntimeException` | `IllegalArgumentException`, `NullPointerException` |

---

## 🔑 Buenas Prácticas Aplicadas

| Práctica                      | Descripción                             | Dónde se aplica                                    |
| ----------------------------- | --------------------------------------- | -------------------------------------------------- |
| **Validar en constructores**  | Comprobar datos antes de crear objetos  | `Usuario`, `Libro`                                 |
| **Usar excepciones estándar** | No crear excepciones innecesarias       | `IllegalArgumentException`, `NullPointerException` |
| **Documentar con Javadoc**    | Comentarios `/** */` explicando métodos | Todos los archivos                                 |
| **Implementar `toString()`**  | Facilita mostrar información del objeto | `Usuario`, `Libro`                                 |
| **Mensajes descriptivos**     | Excepciones con información útil        | Todas las excepciones                              |

---

## 💪 Ejercicios Propuestos

1. **Básico**: Añade una excepción `EdadInsuficienteException` que se lance si un menor de 12 años intenta pedir un libro.

2. **Intermedio**: Crea un método `reservarLibro()` que lance `LibroNoDisponibleException` si el libro ya está reservado.

3. **Avanzado**: Implementa un sistema de multas con una excepción `MultaPendienteException` que guarde el importe de la multa.

---

## 📚 Material de Apoyo

| Recurso                       | Descripción                                                                 |
| ----------------------------- | --------------------------------------------------------------------------- |
| 📄 **[TEORIA.md](TEORIA.md)** | Teoría completa de excepciones en Java (Checked, Unchecked, personalizadas) |
| 🎓 **CAMPUS**                 | Contenido adicional y ejercicios en la plataforma del curso                 |

---

<div align="center">

**¿Dudas?** Pregunta en clase o en el foro del CAMPUS 🙋‍♂️

_Hecho con ❤️ para 1º DAW / DAM - Programación_

</div>
