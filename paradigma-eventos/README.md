# Sistema de gastos personales: paradigma orientado a eventos (Java)

Esta es la **implementación independiente** del paradigma orientado a eventos solicitada en la actividad de seguimiento. Utiliza **Java Swing** (sin librerías externas) y no modifica las implementaciones de los demás integrantes.

## Requisitos de la guía y funciones implementadas

| Requisito funcional | Acción en la interfaz |
| --- | --- |
| 1. Registrar un gasto (concepto, monto, categoría) | `Registrar gasto` |
| 2. Mostrar todos los gastos | `Mostrar todos` |
| 3. Calcular total acumulado | `Calcular total` |
| 4. Mostrar el gasto de mayor monto | `Mayor gasto` |
| 5. Filtrar gastos superiores a un monto | `Filtrar por monto mínimo` |
| 6. Buscar gastos por subcadena del concepto | `Buscar por concepto` |

La tabla muestra los resultados de las consultas. El monto se calcula con `BigDecimal`, que evita errores de precisión típicos de `double` al sumar dinero. Se valida que el concepto no esté vacío, que el monto del gasto sea positivo y que tenga hasta dos decimales. Para los filtros se permite un umbral cero. La búsqueda ignora las diferencias entre mayúsculas y minúsculas.

**Importante:** Los datos permanecen únicamente en memoria mientras la ventana está abierta; la guía no exige guardar gastos en disco o en una base de datos. Para ingresar cantidades no utilices separadores de miles: `25000`, `25000.50` o `25000,50` son válidos.

## Ejecución

**Necesitas:** JDK 11 o superior instalado (`java -version` y `javac -version`).

Desde esta carpeta, en Windows puedes ejecutar el archivo `ejecutar.bat`. También puedes usar los siguientes comandos de PowerShell:

```powershell
mkdir out -Force
javac -encoding UTF-8 -d out src\main\java\edu\gastos\eventos\*.java
java -cp out edu.gastos.eventos.Aplicacion
```

En Linux o macOS:

```bash
mkdir -p out
javac -encoding UTF-8 -d out src/main/java/edu/gastos/eventos/*.java
java -cp out edu.gastos.eventos.Aplicacion
```

La aplicación requiere un escritorio con entorno gráfico; no se puede utilizar en una terminal de servidor sin pantalla.

## Pruebas

Compila el código y las pruebas (no necesitas JUnit ni instalar dependencias):

Windows, desde esta carpeta:

```powershell
javac -encoding UTF-8 -d out src\main\java\edu\gastos\eventos\*.java
javac -encoding UTF-8 -cp out -d out src\test\java\edu\gastos\eventos\*.java
java -ea -cp out edu.gastos.eventos.PruebaRegistroGastos
java -ea -cp out edu.gastos.eventos.PruebaInterfazEventos
```

Linux o macOS:

```bash
javac -encoding UTF-8 -d out src/main/java/edu/gastos/eventos/*.java
javac -encoding UTF-8 -cp out -d out src/test/java/edu/gastos/eventos/*.java
java -ea -cp out edu.gastos.eventos.PruebaRegistroGastos
java -ea -cp out edu.gastos.eventos.PruebaInterfazEventos
```

`PruebaInterfazEventos` crea una ventana y activa los botones con `doClick()`, por lo que requiere un entorno gráfico. En un servidor Linux sin pantalla se puede ejecutar mediante `xvfb-run -a java -ea -cp out edu.gastos.eventos.PruebaInterfazEventos` si Xvfb está instalado.

## ¿Dónde está el paradigma orientado a eventos?

- `Aplicacion.java` inicia Swing en el **Event Dispatch Thread (EDT)** con `SwingUtilities.invokeLater`.
- `VentanaGastos.java` crea la interfaz y los controles (`JButton`, `JTextField`, `JTable`).
- `ControladorGastos.java` asocia cada botón a un **listener** con `addActionListener`. La expresión lambda es un **callback** que invoca el método correspondiente al producirse el clic. La acción del usuario determina qué operación ocurre y cuándo.
- `RegistroGastos.java` contiene las operaciones sobre la lista de gastos y `Gasto.java` representa cada gasto. Esta separación permite probar el comportamiento sin abrir ventanas.

Ejemplo real del controlador:

```java
vista.botonRegistrar.addActionListener(evento -> registrar());
```

Cuando se hace clic en `Registrar gasto`, Swing genera y despacha el evento y ejecuta ese callback. A diferencia de un menú secuencial en consola, el sistema espera acciones del usuario. **Los listeners de Swing se ejecutan en el EDT:** no debe confundirse este hecho con afirmar que cada callback es asíncrono. Este proyecto no necesita crear hilos adicionales para sus operaciones breves.

## Soporte bibliográfico

Lee [`docs/consulta-bibliografica-eventos.md`](docs/consulta-bibliografica-eventos.md), fundamentado en el capítulo 7 (pp. 207–219) del libro *El gran libro de Node.js* de Ulises Gascón González (2025). El libro explica los eventos en Node.js; los detalles sobre `ActionListener` y Swing son **la adaptación técnica hecha para Java**, no un ejemplo presentado por ese libro.
