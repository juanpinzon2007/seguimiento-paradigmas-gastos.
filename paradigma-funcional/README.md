# Sistema de Control de Gastos - Paradigma Funcional en Java

Implementación independiente de las seis funcionalidades de la actividad mediante Java 17 y su biblioteca estándar. La solución mantiene separada la interacción por consola de las operaciones funcionales para que la lógica pueda probarse sin entrada, salida ni estado global.

## Requisitos

- JDK 17 o superior.
- No requiere Maven, Gradle ni dependencias externas.

Comprueba la instalación con:

```powershell
java -version
javac -version
```

## Funcionalidades

| N.º | Funcionalidad | Implementación |
| --- | --- | --- |
| 1 | Registrar un gasto | Crea una lista nueva con `Stream.concat`; no modifica la original. |
| 2 | Mostrar el listado | Recorre una copia inmutable mediante `forEach` y un `Consumer<Gasto>`. |
| 3 | Calcular el total | Transforma con `map(Gasto::monto)` y acumula con `reduce(BigDecimal.ZERO, BigDecimal::add)`. |
| 4 | Mostrar el mayor gasto | Usa `stream().max()` y `Comparator.comparing(Gasto::monto)`. |
| 5 | Filtrar por monto mínimo | Usa `filter()` con una lambda y comparación estricta `>`. |
| 6 | Buscar por concepto | Usa `filter()`, `contains()` y normalización con `Locale.ROOT`; devuelve todas las coincidencias. |

## Aplicación del paradigma funcional

### Inmutabilidad

- `Gasto` es un `record`: sus componentes son finales y no tiene métodos mutadores.
- El constructor valida y normaliza concepto, monto y categoría.
- `List.of()`, `List.copyOf()` y `Stream.toList()` producen las colecciones utilizadas por el dominio.
- `registrar`, `filtrar` y `buscarPorConcepto` devuelven listas nuevas; nunca llaman `add`, `remove`, `set` o `clear` sobre los datos recibidos.

### Funciones puras

Los métodos estáticos de `GestorFuncionalGastos` dependen únicamente de sus parámetros, no leen variables globales, no imprimen y no modifican las entradas. Por ejemplo, `calcularTotal(gastos)` devuelve siempre el mismo resultado cuando recibe la misma lista.

La entrada y salida está aislada en `MainFuncional`. El único ciclo `while` controla el menú interactivo; no se usa para transformar, buscar, filtrar ni sumar gastos.

### Funciones de orden superior y Streams

- `Function<Gasto, BigDecimal>` representa la transformación de un gasto a su monto.
- `Predicate<Gasto>` representa los criterios enviados a `filtrar`, que es una función de orden superior porque recibe otra función.
- `BinaryOperator<BigDecimal>` representa la suma utilizada por `reduce`.
- `Consumer<Gasto>` se usa únicamente para presentar los resultados en consola.
- Se incluyen lambdas reales para el umbral, la búsqueda y el formato de salida.
- Se usan referencias a métodos: `Gasto::monto` y `BigDecimal::add`.

## Estructura

```text
paradigma-funcional/
├── ejecutar.bat
├── probar.bat
├── README.md
├── docs/
│   └── consulta-bibliografica-funcional.md
└── src/
    ├── main/java/edu/gastos/funcional/
    │   ├── Gasto.java
    │   ├── GestorFuncionalGastos.java
    │   └── MainFuncional.java
    └── test/java/edu/gastos/funcional/
        └── PruebaGestorFuncionalGastos.java
```

## Ejecución

En Windows, desde la carpeta `paradigma-funcional`:

```powershell
.\ejecutar.bat
```

También se puede compilar manualmente desde la raíz del repositorio:

```powershell
javac -encoding UTF-8 -d paradigma-funcional\out\main paradigma-funcional\src\main\java\edu\gastos\funcional\*.java
java -cp paradigma-funcional\out\main edu.gastos.funcional.MainFuncional
```

En Linux o macOS:

```bash
mkdir -p paradigma-funcional/out/main
javac -encoding UTF-8 -d paradigma-funcional/out/main paradigma-funcional/src/main/java/edu/gastos/funcional/*.java
java -cp paradigma-funcional/out/main edu.gastos.funcional.MainFuncional
```

## Pruebas

En Windows, desde `paradigma-funcional`:

```powershell
.\probar.bat
```

Ejecución manual desde la raíz:

```powershell
javac -encoding UTF-8 -d paradigma-funcional\out\test paradigma-funcional\src\main\java\edu\gastos\funcional\*.java paradigma-funcional\src\test\java\edu\gastos\funcional\*.java
java -cp paradigma-funcional\out\test edu.gastos.funcional.PruebaGestorFuncionalGastos
```

La prueba realiza 29 verificaciones e incluye todos los escenarios solicitados: registros, listado, suma, máximo, filtros, búsqueda parcial y vacía, total sin datos, inmutabilidad, pureza y validaciones.

## Consulta bibliográfica

El resumen y las fuentes oficiales utilizadas están en [`docs/consulta-bibliografica-funcional.md`](docs/consulta-bibliografica-funcional.md).
