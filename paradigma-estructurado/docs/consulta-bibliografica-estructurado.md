# Consulta bibliográfica: paradigma estructurado

## 1. Datos de la consulta

| Campo                 | Detalle                                                                                                                   |
|-----------------------|---------------------------------------------------------------------------------------------------------------------------|
| Actividad             | Implementación de un algoritmo aplicando diferentes paradigmas de programación y trabajo colaborativo con Git y GitHub    |
| Lenguaje              | Java                                                                                                                      |
| Paradigma asignado    | Estructurado (orientado a estructuras de control y de datos)                                                              |
| Libro consultado      | *El gran libro de Node.js: una guía moderna y completa para crear aplicaciones web eficientes con Node.js* (biblioteca institucional) |
| Autor                 | Sebastian Springer                                                                                                        |
| Capítulo principal    | **Capítulo 4. Módulos de Node.js** (*Node.js Modules*)                                                                    |
| Capítulo de apoyo     | **Capítulo 15. Node en la línea de comandos** (*Node on the Command Line*)                                                |

> Nota: la numeración de capítulos corresponde al índice publicado de la
> obra original (*Node.js: The Comprehensive Guide*, Rheinwerk, ISBN
> 978-1-4932-2292-6 en la edición impresa de 2022). Antes de la entrega
> conviene confirmar número de capítulo y páginas en el ejemplar físico de la
> biblioteca, porque la edición en español puede variar.

## 2. Por qué estos capítulos

El libro trata sobre Node.js, no sobre programación estructurada de forma
explícita. Del índice, los capítulos que más se relacionan con el paradigma
estructurado son:

- **Capítulo 4, Módulos de Node.js:** explica cómo se divide una aplicación en
  unidades independientes (módulos) con una responsabilidad concreta, que
  exponen funciones y se reutilizan entre sí. Es la misma idea de
  **descomposición en procedimientos** de la programación estructurada.
- **Capítulo 15, Node en la línea de comandos:** trata la entrada y salida por
  consola, el equivalente a nuestro menú interactivo con `Scanner`.

Los demás capítulos (HTTP, Express, GraphQL, RxJS, streams, microservicios,
etc.) pertenecen a otros enfoques, como el dirigido por eventos, el asíncrono
y el funcional, y no se usaron para este paradigma.

## 3. Ideas del capítulo relacionadas con el paradigma estructurado

| Idea tratada en el libro (módulos)                              | Concepto estructurado equivalente                            |
|-----------------------------------------------------------------|--------------------------------------------------------------|
| Dividir la aplicación en módulos pequeños y cohesivos           | Dividir el programa en procedimientos (diseño descendente)   |
| Un módulo expone solo lo necesario y oculta el resto            | Métodos con una sola tarea y comunicación por parámetros     |
| Las funciones reciben datos y devuelven resultados              | Paso de parámetros y valores de retorno                      |
| Reutilizar un módulo desde otro                                 | Un método `static` llamado desde varios puntos del programa  |
| Código organizado para entenderlo y mantenerlo con facilidad    | Legibilidad: flujo de arriba hacia abajo, sin saltos `goto`  |

## 4. Comparación Node.js y Java en este proyecto

La misma lógica estructurada se escribe en ambos lenguajes con estructuras
equivalentes. Ejemplo: sumar los montos.

JavaScript (Node.js):

```javascript
function sumarMontos(montos, cantidad) {
  let suma = 0;
  for (let i = 0; i < cantidad; i++) {
    suma = suma + montos[i];
  }
  return suma;
}
```

Java (este proyecto, `Aplicacion.java`):

```java
public static double sumarMontos(double[] lista, int cantidad) {
    double suma = 0;
    for (int i = 0; i < cantidad; i++) {
        suma = suma + lista[i];
    }
    return suma;
}
```

| Aspecto                | Node.js / JavaScript                     | Java (este proyecto)                          |
|------------------------|------------------------------------------|-----------------------------------------------|
| Unidad de organización | Módulo (archivo con `require`/`import`)  | Clase única con métodos `static`              |
| Procedimiento          | `function`                               | `public static` método                        |
| Selección              | `if / else if / else`                    | `if / else if / else`                         |
| Iteración              | `for`, `while`                           | `for`, `while`                                |
| Entrada por consola    | Módulo `readline`                        | `Scanner`                                     |
| Datos                  | Arreglos                                 | Tres arreglos paralelos                       |

## 5. Análisis crítico

- **Node.js no es un entorno estructurado puro.** Es multiparadigma y su
  fortaleza está en lo asíncrono y los eventos, justo lo contrario del flujo
  secuencial que busca el paradigma estructurado. Por eso el libro sirve como
  apoyo para la **modularización**, no como definición del paradigma.
- **Modularización:** en nuestro proyecto la modularización es mínima a
  propósito (una clase y métodos `static`), porque la guía exige una sola
  clase. En Node.js cada funcionalidad podría estar en su propio archivo.
- **Ventaja del enfoque estructurado aquí:** el programa es fácil de seguir y
  de defender, porque cada opción del menú corresponde a un método.
- **Limitación:** con arreglos paralelos y capacidad fija (100 gastos), el
  código es simple pero no escala; con muchos datos convendría otra
  estructura, algo que cae fuera del alcance de esta actividad.

## 6. Conclusión

La consulta confirma que la descomposición en partes pequeñas, con funciones
que reciben parámetros y devuelven resultados, es una práctica compartida por
Node.js y por la programación estructurada. Aplicada al proyecto: cada
funcionalidad vive en su propio método (`registrarGasto`, `mostrarGastos`,
`calcularTotal`, `mostrarGastoMayor`, `filtrarPorMonto`,
`buscarPorConcepto`), con control de flujo basado solo en secuencia,
selección e iteración.

## 7. Referencias

1. Springer, S. (2022). *Node.js: The Comprehensive Guide*. Rheinwerk
   Publishing. ISBN 978-1-4932-2292-6. Edición en español consultada en la
   biblioteca institucional: *El gran libro de Node.js: una guía moderna y
   completa para crear aplicaciones web eficientes con Node.js*. Capítulo 4
   (Módulos de Node.js) y capítulo 15 (Node en la línea de comandos).
2. Böhm, C. y Jacopini, G. (1966). *Flow diagrams, Turing machines and
   languages with only two formation rules*. Communications of the ACM, 9(5),
   366-371.
3. Dijkstra, E. W. (1968). *Go To Statement Considered Harmful*.
   Communications of the ACM, 11(3), 147-148.
4. Dahl, O.-J., Dijkstra, E. W. y Hoare, C. A. R. (1972). *Structured
   Programming*. Academic Press.
5. Wirth, N. (1976). *Algorithms + Data Structures = Programs*. Prentice-Hall.
6. Oracle. *The Java Tutorials: Language Basics (control flow statements,
   arrays)*. https://docs.oracle.com/javase/tutorial/java/nutsandbolts/
