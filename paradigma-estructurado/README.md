# Paradigma Estructurado - Control de Gastos Personales

Implementación en Java del Sistema de Control de Gastos Personales usando el
**paradigma de programación estructurado**.

## 1. ¿Qué es el paradigma estructurado?

La programación estructurada organiza un programa usando solo tres formas de
control de flujo, sin saltos desordenados (`goto`):

| Estructura   | Qué hace                                  | En este proyecto                |
|--------------|-------------------------------------------|---------------------------------|
| Secuencia    | Las instrucciones se ejecutan en orden    | Cada método avanza paso a paso  |
| Selección    | Decide un camino según una condición      | `if`, `else if`, `else`         |
| Iteración    | Repite un bloque mientras sea necesario   | `while` y `for`                 |

Además, el programa se divide en **procedimientos** (métodos `static`) pequeños,
cada uno con una sola tarea, que se comunican mediante **parámetros** y
**valores de retorno**.

### Cómo se aplica aquí

- **Una sola clase `Aplicacion`**, solo porque Java exige una clase para ejecutar.
  No se modelan objetos: no existen clases `Gasto` ni `GestorGastos`.
- **Datos en arreglos paralelos**: la posición `i` de los tres arreglos
  representa el mismo gasto.

  ```java
  static String[] conceptos = new String[100];
  static double[] montos = new double[100];
  static String[] categorias = new String[100];
  static int cantidadGastos = 0;
  ```
- **Un método `static` por funcionalidad**: `registrarGasto`, `mostrarGastos`,
  `calcularTotal`, `mostrarGastoMayor`, `filtrarPorMonto`, `buscarPorConcepto`
  y `mostrarMenu`.
- **Menú con `while`** que se repite hasta que el usuario elige `0`.
- No usa POO, herencia, interfaces, streams, lambdas, bases de datos ni
  librerías externas.

## 2. Funcionalidades

1. Registrar gasto (concepto, monto y categoría).
2. Mostrar gastos.
3. Calcular gasto total.
4. Mostrar gasto de mayor valor.
5. Filtrar gastos superiores a un monto.
6. Buscar gasto por concepto (coincidencia parcial, sin distinguir mayúsculas).

## 3. Estructura de la carpeta

```
paradigma-estructurado/
├── README.md
├── ejecutar.bat
├── src/
│   ├── main/java/edu/gastos/estructurado/Aplicacion.java
│   └── test/java/edu/gastos/estructurado/PruebaGastos.java
└── docs/
    └── consulta-bibliografica-estructurado.md
```

## 4. Cómo compilar y ejecutar

Requiere JDK 8 o superior. Desde la carpeta `paradigma-estructurado`:

```bash
javac -d out src/main/java/edu/gastos/estructurado/Aplicacion.java
java -cp out edu.gastos.estructurado.Aplicacion
```

En Windows también se puede usar `ejecutar.bat`.

## 5. Cómo ejecutar las pruebas

```bash
javac -d out src/main/java/edu/gastos/estructurado/Aplicacion.java src/test/java/edu/gastos/estructurado/PruebaGastos.java
java -cp out edu.gastos.estructurado.PruebaGastos
```

O con `ejecutar.bat test` en Windows. Las pruebas no usan JUnit: son un
programa sencillo que verifica resultados e imprime `OK` o `FALLO`.

## 6. Bibliografía

Ver [docs/consulta-bibliografica-estructurado.md](docs/consulta-bibliografica-estructurado.md).
