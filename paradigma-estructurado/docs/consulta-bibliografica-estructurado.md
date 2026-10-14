# Consulta bibliográfica: paradigma estructurado

## Ideas principales

- **Teorema del programa estructurado (Böhm y Jacopini, 1966):** cualquier
  algoritmo computable puede expresarse combinando solo secuencia, selección e
  iteración.
- **Crítica al `goto` (Dijkstra, 1968):** los saltos arbitrarios dificultan
  entender y razonar sobre el programa; el flujo debe poder leerse de arriba
  hacia abajo.
- **Diseño descendente y por refinamiento (Wirth, Dahl, Dijkstra, Hoare):** un
  problema grande se divide en subproblemas, cada uno resuelto por un
  procedimiento con una tarea clara.
- **Datos y algoritmos (Wirth, 1976):** un programa es la combinación de
  estructuras de datos (aquí, arreglos) y algoritmos que las recorren.

## Aplicación al proyecto

| Concepto                | Dónde se ve en `Aplicacion.java`                         |
|-------------------------|----------------------------------------------------------|
| Secuencia               | Cuerpo de cada método                                    |
| Selección               | `if / else if / else` en validaciones y en el menú       |
| Iteración               | `for` para recorrer los arreglos, `while` para el menú   |
| Procedimientos          | Un método `static` por funcionalidad                     |
| Paso de parámetros      | Métodos auxiliares que reciben arreglos y valores        |
| Estructura de datos     | Tres arreglos paralelos y un contador global             |

## Referencias

1. Böhm, C. y Jacopini, G. (1966). *Flow diagrams, Turing machines and
   languages with only two formation rules*. Communications of the ACM, 9(5),
   366-371.
2. Dijkstra, E. W. (1968). *Go To Statement Considered Harmful*.
   Communications of the ACM, 11(3), 147-148.
3. Dahl, O.-J., Dijkstra, E. W. y Hoare, C. A. R. (1972). *Structured
   Programming*. Academic Press.
4. Wirth, N. (1976). *Algorithms + Data Structures = Programs*. Prentice-Hall.
5. Oracle. *The Java Tutorials: Language Basics (control flow statements,
   arrays)*. https://docs.oracle.com/javase/tutorial/java/nutsandbolts/
