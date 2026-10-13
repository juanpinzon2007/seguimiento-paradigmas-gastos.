# Soporte de consulta bibliográfica: paradigma orientado a eventos

**Actividad:** Implementación de un algoritmo aplicando diferentes paradigmas de programación y trabajo colaborativo con Git y GitHub (Python / Java).  
**Paradigma asignado:** Orientado a eventos.  
**Libro consultado en la biblioteca institucional:** *El gran libro de Node.js: una guía moderna y completa para crear aplicaciones web eficientes con Node.js*.  
**Capítulo examinado:** 7. «Arquitectura orientada a eventos», pp. 207–219.

## Consulta y fundamentos teóricos

El capítulo explica que un **evento** es un suceso que puede producir una respuesta del programa. Se mencionan como ejemplos los clics sobre un botón, la recepción de un mensaje y el guardado de un archivo. Un sistema basado en eventos permite que distintos componentes produzcan acontecimientos o se suscriban a ellos (Gascón González, 2025, pp. 208–209).

En este enfoque pueden identificarse tres participantes. El **emisor** produce el evento; el **suscriptor** o *listener* escucha los eventos de interés; y la **función de retorno** o *callback* contiene las instrucciones que deben ejecutarse cuando el evento se dispara. El libro presenta un ejemplo del método `addEventListener` que recibe el nombre del evento y una función callback; esta función se invoca al ocurrir el clic (Gascón González, 2025, p. 209).

Como mecanismo concreto de Node.js, el texto presenta la clase `EventEmitter`, que permite registrar suscriptores y emitir eventos. Los métodos `on` y `emit` aparecen en ejemplos que muestran la relación entre emisión, escucha y reacción. También se presenta `once` para ejecutar un suscriptor solo una vez (Gascón González, 2025, pp. 211–213). El texto aclara que los suscriptores de ese ejemplo se invocan de forma **síncrona** y siguiendo su orden de registro; por tanto, orientar un programa a eventos no significa que toda función se ejecute necesariamente de modo asíncrono (Gascón González, 2025, p. 213).

El capítulo también explica la importancia de organizar los nombres de los eventos y retirar suscriptores innecesarios, por ejemplo, mediante `removeListener`, `off` y `removeAllListeners` en Node.js (Gascón González, 2025, pp. 213–214). Finalmente, muestra cómo añadir una interfaz de eventos a un módulo y cómo la emisión de eventos ayuda a separar sus responsabilidades y reducir el acoplamiento de la aplicación (Gascón González, 2025, pp. 216–218).

## Aplicación de los conceptos en Java

**Nota metodológica:** El libro está escrito para Node.js/JavaScript, no para Java Swing. Las correspondencias siguientes son **una adaptación propia al proyecto**, no afirmaciones de que el autor presente estas API de Java.

| Concepto del capítulo | Aplicación en el sistema desarrollado |
| --- | --- |
| Evento | Pulsar un botón del formulario de gastos. |
| Suscriptor / listener | `addActionListener` registra el interés del controlador por los clics. |
| Callback | La lambda `evento -> registrar()` llama al método de registro. |
| Reacción ante el evento | Se registra el gasto, se actualiza la tabla o se muestra un resultado. |
| Separación de responsabilidades | La vista muestra datos, el controlador escucha eventos y el registro procesa operaciones. |

La aplicación se implementó en **Java Swing** porque la guía de la actividad requiere un lenguaje Java o Python y una GUI interactiva. Sus eventos principales son `Registrar gasto`, `Calcular total`, `Filtrar por monto mínimo` y `Buscar por concepto`. Adicionalmente, `Mostrar todos` y `Mayor gasto` permiten cumplir las seis funcionalidades del sistema de gastos personales.

## Ejemplo de funcionamiento

Cuando el usuario ingresa `Almuerzo`, `25000` y `Alimentación` y pulsa **Registrar gasto**, Swing notifica al `ActionListener` del botón. El callback del controlador valida la información, crea el registro y actualiza la tabla. Al pulsar **Calcular total**, ocurre un evento independiente que suma los montos registrados. De este modo, el usuario controla la secuencia de ejecución mediante interacciones con la interfaz.

## Referencia bibliográfica (APA 7)

Gascón González, U. (2025). *El gran libro de Node.js: una guía moderna y completa para crear aplicaciones web eficientes con Node.js*. Marcombo.

**Evidencia de consulta:** Este resumen se elaboró a partir de las imágenes del capítulo 7, pp. 207–219, facilitadas para la actividad. Se recomienda conservar las capturas del catálogo y de la consulta bibliográfica conforme al medio de entrega que indique la docente. No se incorporan reproducciones del capítulo al repositorio.
