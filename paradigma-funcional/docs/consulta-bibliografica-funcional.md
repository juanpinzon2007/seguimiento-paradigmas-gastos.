# Consulta bibliográfica: programación funcional en Java

## Resumen

La programación funcional expresa el procesamiento como transformaciones de valores mediante funciones. En este proyecto las operaciones del dominio reciben una lista y devuelven un valor o una lista nueva, sin cambiar la colección recibida ni depender de estado global.

La API `Stream` de Java permite formar tuberías de operaciones. `map` transforma cada gasto en su monto, `filter` selecciona los gastos que cumplen un predicado, `reduce` combina los montos y `max` obtiene el mayor elemento según un comparador. Estas operaciones reemplazan ciclos explícitos y acumuladores mutables en la lógica del dominio.

Las interfaces de `java.util.function` sirven como tipos para funciones. El proyecto utiliza `Function` para transformar, `Predicate` para decidir si un gasto pertenece a un resultado, `BinaryOperator` para sumar dos montos y `Consumer` para la presentación en consola. Las expresiones lambda y las referencias a métodos proporcionan las implementaciones concretas de esas funciones.

Los `record` modelan datos con componentes finales y una representación compacta. Por eso `Gasto` se implementa como `record`; su constructor compacto añade las validaciones necesarias y los objetos no exponen operaciones de modificación.

## Aplicación concreta en el código

- `Gasto.java`: valor inmutable validado.
- `GestorFuncionalGastos.extraerMontos`: transformación con `map`.
- `GestorFuncionalGastos.calcularTotal`: composición de `map` y `reduce`.
- `GestorFuncionalGastos.filtrar`: función de orden superior que recibe un `Predicate`.
- `GestorFuncionalGastos.mayoresA` y `buscarPorConcepto`: predicados definidos con lambdas.
- `MainFuncional`: frontera con efectos de entrada y salida, separada de las funciones puras.

## Fuentes consultadas

1. Oracle. *Stream (Java SE 17 & JDK 17)*. Documentación oficial de las operaciones `map`, `filter`, `reduce`, `max` y `toList`: <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/Stream.html>
2. Oracle. *Package java.util.function (Java SE 17 & JDK 17)*. Documentación oficial de las interfaces funcionales: <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/package-summary.html>
3. Oracle. *Record Classes - Java Language Updates, Release 17*. Descripción oficial de los `record` y sus componentes finales: <https://docs.oracle.com/en/java/javase/17/language/records.html>
4. Oracle. *BigDecimal (Java SE 17 & JDK 17)*. API usada para representar y sumar montos con precisión decimal: <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigDecimal.html>

Consulta realizada el 13 de octubre de 2026.
