# 🎓 Manejo de Excepciones en Java

<div align="center">

![Java](https://img.shields.io/badge/Java-Excepciones-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Teoría](https://img.shields.io/badge/Tipo-Teoría-blue?style=for-the-badge)
![DAW/DAM](https://img.shields.io/badge/1º-DAW%20%2F%20DAM-green?style=for-the-badge)

_Material teórico de la Unidad 4 - Programación [Excepciones]_

</div>

---

> 💻 **¿Quieres ver el código?** Vuelve al **[README.md](README.md)** para ver el proyecto práctico.

---

## 📋 Índice

1. [¿Qué es una excepción?](#-qué-es-una-excepción)
2. [Excepciones Checked vs Unchecked](#%EF%B8%8F-checked-vs-unchecked)
3. [Excepciones personalizadas](#%EF%B8%8F-excepciones-personalizadas)
4. [Excepciones estándar de Java](#-excepciones-estándar-de-java)
5. [Tabla de excepciones comunes](#-tabla-de-excepciones-comunes)
6. [Resumen](#-resumen)

---

## 🤔 ¿Qué es una excepción?

> Una **excepción** es un evento que ocurre durante la ejecución de un programa y que interrumpe el flujo normal del mismo.

Java proporciona un sistema robusto para manejar excepciones, lo que permite que el programa **continúe funcionando** en lugar de detenerse abruptamente.

### Estructura básica: `try-catch-finally`

```java
public class ExcepcionEjemplo {
    public static void main(String[] args) {

        try {
            // 🔄 Código que puede lanzar una excepción
            int resultado = dividir(10, 0);
            System.out.println("Resultado: " + resultado);
        } catch (ArithmeticException e) {
            // ❌ Manejo del error
            System.out.println("Error: No se puede dividir por cero.");
        } finally {
            // ✅ Siempre se ejecuta (haya o no excepción)
            System.out.println("Este bloque siempre se ejecuta.");
        }

    }

    public static int dividir(int a, int b) {
        return a / b; // ⚠️ Posible división por cero
    }
}
```

| Bloque    | ¿Qué hace?                                        |
| --------- | ------------------------------------------------- |
| `try`     | Contiene el código que puede lanzar una excepción |
| `catch`   | Captura y maneja la excepción si ocurre           |
| `finally` | Se ejecuta **siempre**, haya o no excepción       |

---

## ⚖️ Checked vs Unchecked

Java tiene dos tipos de excepciones. Es **fundamental** entender la diferencia:

### ✅ Excepciones Checked (Comprobadas)

| Característica                   | Descripción                                                   |
| -------------------------------- | ------------------------------------------------------------- |
| **¿Obliga a manejarlas?**        | Sí, con `try-catch` o `throws`                                |
| **¿Cuándo se detectan?**         | En tiempo de **compilación**                                  |
| **¿Qué pasa si no las manejas?** | ❌ El proyecto **NO compila**                                 |
| **Heredan de...**                | `Exception` (pero NO de `RuntimeException`)                   |
| **Ejemplos**                     | `IOException`, `SQLException`, `FileNotFoundException`        |
| **¿Cuándo usarlas?**             | Operaciones que pueden fallar "por fuera" (archivos, red, BD) |

**📚 En nuestro proyecto de Biblioteca:**

- `LimitePrestamosExcedidoException`
- `LibroNoDisponibleException`
- `UsuarioNoEncontradoException`

```java
// Ejemplo: Leer un archivo (puede fallar si no existe)
import java.io.*;

public class ArchivoEjemplo {

    // ⚠️ Declaramos que este método puede lanzar IOException
    public static void leerArchivo(String ruta) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        System.out.println("Primera línea: " + br.readLine());
        br.close();
    }

    public static void main(String[] args) {
        try {
            leerArchivo("archivo.txt");
        } catch (IOException e) {
            // ❌ Obligatorio manejar la excepción
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
```

---

### ❌ Excepciones Unchecked (No comprobadas)

| Característica                   | Descripción                                                               |
| -------------------------------- | ------------------------------------------------------------------------- |
| **¿Obliga a manejarlas?**        | No, pero es **recomendable**                                              |
| **¿Cuándo se detectan?**         | En tiempo de **ejecución**                                                |
| **¿Qué pasa si no las manejas?** | 💥 El programa **termina** (crash + stack trace)                          |
| **Heredan de...**                | `RuntimeException`                                                        |
| **Ejemplos**                     | `NullPointerException`, `IllegalArgumentException`, `ArithmeticException` |
| **¿Cuándo usarlas?**             | Errores de programación o datos no válidos                                |

**📚 En nuestro proyecto de Biblioteca:**

- `IllegalArgumentException` → Validar nombre y edad en `Usuario`, título en `Libro`
- `NullPointerException` → Validar que no se agregue un usuario `null` en `GestorUsuarios`

```java
public class ExcepcionArray {
    public static void main(String[] args) {
        int[] numeros = {1, 2, 3};
        System.out.println(numeros[5]); // 💥 ERROR: índice fuera de rango
    }
}
```

---

### 📊 Comparativa visual

```
                    ┌─────────────────────────────────────┐
                    │            Throwable                │
                    └─────────────────┬───────────────────┘
                                      │
              ┌───────────────────────┴───────────────────────┐
              │                                               │
    ┌─────────▼─────────┐                         ┌──────────▼──────────┐
    │      Error        │                         │      Exception      │
    │  (No manejar)     │                         │                     │
    └───────────────────┘                         └──────────┬──────────┘
                                                             │
                                      ┌──────────────────────┴──────────────────────┐
                                      │                                             │
                          ┌───────────▼───────────┐                    ┌────────────▼────────────┐
                          │  RuntimeException     │                    │   Otras Exceptions      │
                          │     (UNCHECKED)       │                    │      (CHECKED)          │
                          │  ❌ No obliga         │                    │  ✅ Obliga a manejar    │
                          └───────────────────────┘                    └─────────────────────────┘
```

> 💡 **Regla rápida:** Tanto las Checked como las Unchecked ocurren en **ejecución**. La diferencia es si Java te **obliga a tratarlas antes de compilar**.

---

## 🛠️ Excepciones personalizadas

Java nos permite **crear nuestras propias excepciones** para controlar errores específicos en nuestra lógica de negocio.

### ¿Cómo crear una excepción personalizada?

| Tipo      | Hereda de...       | ¿Obligatorio manejarla? |
| --------- | ------------------ | ----------------------- |
| Checked   | `Exception`        | ✅ Sí                   |
| Unchecked | `RuntimeException` | ❌ No                   |

### 📚 Ejemplo: Excepción Checked personalizada

```java
// Excepción personalizada que OBLIGA a ser manejada
class SaldoInsuficienteException extends Exception {

    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);  // Llamamos al constructor padre con el mensaje
    }

}

class CuentaBancaria {
    private double saldo;

    public CuentaBancaria(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // ⚠️ Declaramos que puede lanzar la excepción con "throws"
    public void retirar(double cantidad) throws SaldoInsuficienteException {
        if (cantidad > saldo) {
            // 🚨 Lanzamos la excepción con "throw"
            throw new SaldoInsuficienteException("Saldo insuficiente. Disponible: " + saldo);
        }
        saldo -= cantidad;
        System.out.println("Retiro exitoso. Saldo restante: " + saldo);
    }
}

public class Main {
    public static void main(String[] args) {
        CuentaBancaria cuenta = new CuentaBancaria(500);

        try {
            cuenta.retirar(700); // ❌ Intentamos retirar más de lo disponible
        } catch (SaldoInsuficienteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### 🔑 Palabras clave

| Palabra  | ¿Qué hace?                                           | Ejemplo                                   |
| -------- | ---------------------------------------------------- | ----------------------------------------- |
| `throw`  | **Lanza** una excepción                              | `throw new MiExcepcion("mensaje");`       |
| `throws` | **Declara** que un método puede lanzar una excepción | `public void metodo() throws MiExcepcion` |

---

## 📦 Excepciones estándar de Java

> ⚠️ **Importante:** No siempre necesitas crear excepciones personalizadas. Java ya tiene muchas que puedes usar.

### ¿Cuándo usar excepciones estándar?

- ✅ Cuando el error es **genérico** y ampliamente utilizado
- ✅ Para validar valores inválidos, nulos, división por cero...
- ❌ **Evita** crear excepciones personalizadas innecesarias

---

### `IllegalArgumentException`

> Se usa cuando un **argumento pasado a un método es inválido**.

**📚 En nuestro proyecto:** Validamos nombre y edad en `Usuario.java` y título en `Libro.java`

```java
class Persona {
    private String nombre;
    private int edad;

    public Persona(String nombre, int edad) {
        // ❌ Si el nombre está vacío → lanzamos excepción
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        // ❌ Si la edad es negativa → lanzamos excepción
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa.");
        }
        // ✅ Si todo está bien, guardamos los datos
        this.nombre = nombre;
        this.edad = edad;
    }
}
```

---

### `NullPointerException`

> Se usa cuando se intenta **acceder a un objeto que es `null`**.

**📚 En nuestro proyecto:** Validamos que no se agregue un usuario `null` en `GestorUsuarios.java`

```java
public class Coche {
    private String marca;
    private String modelo;

    public Coche(String marca, String modelo) {
        // ❌ Si marca o modelo son null → lanzamos excepción
        if (marca == null || modelo == null) {
            throw new NullPointerException("La marca y el modelo no pueden ser nulos.");
        }
        this.marca = marca;
        this.modelo = modelo;
    }

    public void mostrarDetalles() {
        System.out.println("Marca: " + marca + ", Modelo: " + modelo);
    }

    public static void main(String[] args) {

        try {
            Coche coche1 = new Coche(null, "Corolla"); // ❌ Provoca NullPointerException
            coche1.mostrarDetalles();
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            Coche coche2 = new Coche("Toyota", "Corolla"); // ✅ Correcto
            coche2.mostrarDetalles();
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
```

---

## 📋 Tabla de excepciones comunes

| Excepción                        | Descripción                                          | Ejemplo de uso                                      |
| -------------------------------- | ---------------------------------------------------- | --------------------------------------------------- |
| `IllegalArgumentException`       | Argumento pasado a un método es **inválido**         | Validar que la edad no sea negativa                 |
| `NullPointerException`           | Se intenta acceder a un objeto que es **`null`**     | Acceder a un método de un objeto no inicializado    |
| `IndexOutOfBoundsException`      | Índice **fuera de los límites** de una lista o array | `lista.get(10)` en una lista de 5 elementos         |
| `ArrayIndexOutOfBoundsException` | Versión específica para **arrays**                   | `numeros[10]` en un array de 5 elementos            |
| `NumberFormatException`          | Formato de número **no válido** al convertir String  | `Integer.parseInt("abc");`                          |
| `ArithmeticException`            | Operación matemática **inválida**                    | `int resultado = 10 / 0;`                           |
| `UnsupportedOperationException`  | Método **no soportado** o implementado               | Modificar una lista inmutable                       |
| `IllegalStateException`          | El **estado del objeto** no permite la operación     | Retirar dinero de una cuenta no activada            |
| `ClassCastException`             | Conversión a un **tipo incompatible**                | `Object obj = "Hola"; Integer num = (Integer) obj;` |

---

## 📝 Resumen

| Concepto                       | Descripción                                                | Ejemplo                                            |
| ------------------------------ | ---------------------------------------------------------- | -------------------------------------------------- |
| `try-catch-finally`            | Maneja excepciones para evitar que el programa se detenga  | Capturar `ArithmeticException`                     |
| `try-with-resources`           | Cierra automáticamente recursos como archivos o conexiones | `BufferedReader` en lectura de archivos            |
| **Checked Exceptions**         | Excepciones que **deben manejarse** obligatoriamente       | `IOException`, `SQLException`                      |
| **Unchecked Exceptions**       | Se detectan en tiempo de ejecución, **no obligatorias**    | `NullPointerException`, `IllegalArgumentException` |
| **Excepciones personalizadas** | Crear excepciones específicas extendiendo `Exception`      | `LimitePrestamosExcedidoException`                 |

---

## 🔗 Relación con el Proyecto

Este contenido teórico se aplica en el proyecto **📚 Sistema de Biblioteca - Manejo de Excepciones**:

| Concepto              | Aplicación en el proyecto                                                                        |
| --------------------- | ------------------------------------------------------------------------------------------------ |
| Excepciones Checked   | `LimitePrestamosExcedidoException`, `LibroNoDisponibleException`, `UsuarioNoEncontradoException` |
| Excepciones Unchecked | `IllegalArgumentException` en `Usuario` y `Libro`, `NullPointerException` en `GestorUsuarios`    |
| `try-catch`           | Manejo de errores en `AppMain.java`                                                              |
| `multi-catch`         | `catch (Excepcion1 \| Excepcion2 e)` en préstamos de libros                                      |
| `throw`               | Lanzar excepciones en constructores y métodos                                                    |
| `throws`              | Declarar excepciones en `prestarLibro()`, `buscarUsuarioPorNombre()`                             |

---

<div align="center">

📖 **Consulta el código del proyecto para ver estos conceptos en acción**

_Material de apoyo para 1º DAW / DAM - Programación_

</div>
