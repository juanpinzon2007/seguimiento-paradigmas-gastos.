# Sistema de Seguimiento de Gastos - Comparativa de Paradigmas

Proyecto colaborativo para implementar y analizar un sistema de seguimiento de gastos bajo diferentes paradigmas de programación, aplicando una estrategia estricta de ramas (*Git Branching Model*) y normas de colaboración.

---

## 1. Paradigmas a Desarrollar

El sistema explora las características, fortalezas y diferencias de implementación en los siguientes 5 paradigmas:

1. **Paradigma Estructurado**: Enfoque procedimental, funciones y control de flujo algorítmico secuencial/cíclico.
2. **Paradigma Orientado a Objetos (POO)**: Clases, encapsulamiento, abstracción y responsabilidades (`GestorGastos`, `Gasto`).
3. **Paradigma Dirigido por Eventos**: Emisión y captura asíncrona de eventos, manejadores (*event listeners / handlers*).
4. **Paradigma Declarativo**: Enfoque basado en qué resultado se desea en lugar del cómo (consultas SQL relacionales, filtros en motor).
5. **Paradigma Funcional**: Funciones puras, inmutabilidad, composición y operadores de alto orden (`map`, `filter`, `reduce`).

---

## 2. Estrategia de Ramas (Git Branching Model)

Este repositorio sigue un modelo de ramas estructurado:

* **`main`**: Código totalmente estable, probado y listo para entrega final.
* **`develop`**: Rama base de integración donde convergen las características finalizadas.
* **Feature Branches**: Cada paradigma debe desarrollarse en su propia rama independiente creada a partir de `develop`:
  * `feature/paradigma-estructurado`
  * `feature/paradigma-poo`
  * `feature/paradigma-eventos`
  * `feature/paradigma-declarativo` *(Esta rama)*
  * `feature/paradigma-funcional`

---

## 3. Normas de Colaboración y Reparto de Trabajo

* **Commits individuales**: Todos los miembros del equipo deben registrar contribuciones en el historial de Git.
* **Mensajes de commit estandarizados**: Aplicar formato claro, imperativo y bajo la convención de Conventional Commits:
  * `feat: agrega clase GestorGastos en POO`
  * `fix: corrige sintaxis SQL en busqueda por concepto`
  * `docs: actualiza respuestas de analisis en README`
  * `test: agrega pruebas para calculo de gastos por categoria`
* **Pull Requests (PR)**: La integración de cada rama `feature/*` hacia `develop` debe realizarse obligatoriamente mediante un Pull Request en GitHub hacia `develop`, requiriendo la revisión y aprobación (*Approve*) de al menos otro integrante del equipo antes del Merge.

---

## 4. Implementación en esta Rama: Paradigma Declarativo (Java + MySQL)

En esta rama (`feature/paradigma-declarativo`) se implementa el sistema de control de gastos aplicando los principios formales del **Paradigma Declarativo** mediante **Java 17**, **JDBC** y el motor de base de datos relacional **MySQL**.

### 4.1. Fundamentos Teóricos del Paradigma Declarativo
A diferencia del paradigma imperativo (donde el programador detalla paso a paso las instrucciones mecánicas: **cómo** resolver el problema con bucles `for`/`while`, contadores y comparaciones manuales), el **Paradigma Declarativo** se enfoca en **expresar explícitamente QUÉ datos se desean obtener o modificar**, delegando completamente al motor de ejecución subyacente la lógica del **CÓMO** iterar, filtrar, indexar, agregar y optimizar la búsqueda física.

### 4.2. Comparativa: Declarativo vs. Imperativo

| Aspecto | Paradigma Imperativo | Paradigma Declarativo (Esta Rama) |
| :--- | :--- | :--- |
| **Cálculo del Total** | Bucle `for` con variable acumuladora: `total += gasto.getMonto()` | Función de agregación SQL: `SELECT SUM(monto) FROM gastos` |
| **Mayor Valor** | Recorrido lineal con condicional: `if (gasto.getMonto() > max) max = gasto.getMonto()` | Subconsulta relacional: `WHERE monto = (SELECT MAX(monto) FROM gastos)` |
| **Filtrado por Umbral** | Bucle `for` con `if (g.getMonto() > umbral) lista.add(g)` en memoria | Predicado relacional en motor: `WHERE monto > ?` |
| **Búsqueda por Subcadena** | Recorrido iterativo con `concepto.contains(palabra)` | Operador relacional: `WHERE LOWER(concepto) LIKE LOWER(?)` |
| **Inserción de Registros** | Gestión explícita de estructuras en memoria o punteros | Declaración de inserción de tupla: `INSERT INTO gastos (...)` |

---

### 4.3. Las 6 Funcionalidades Requeridas y su Expresión Declarativa

| # | Funcionalidad Requerida | Consulta SQL Declarativa | Justificación Declarativa |
| :---: | :--- | :--- | :--- |
| **1** | **Registrar gasto** | `INSERT INTO gastos (concepto, monto, categoria) VALUES (?, ?, ?)` | Se declara la intención de incorporar una tupla al conjunto de datos sin programar algoritmos de asignación física de disco ni memoria. |
| **2** | **Mostrar listado de gastos** | `SELECT id, concepto, monto, categoria, fecha FROM gastos ORDER BY id ASC` | Expresa la proyección de atributos deseada y el orden. El motor relacional elige el plan de escaneo de tabla. |
| **3** | **Calcular gasto total acumulado** | `SELECT COALESCE(SUM(monto), 0) AS total_acumulado, COUNT(*) AS total_registros FROM gastos` | La sumatoria y conteo son resueltos exclusivamente por el motor de base de datos (`SUM` y `COUNT`). En Java **no existe ningún bucle ni acumulador**. |
| **4** | **Mostrar el gasto de mayor valor** | `SELECT id, concepto, monto, categoria, fecha FROM gastos WHERE monto = (SELECT MAX(monto) FROM gastos)` | Mediante la función declarativa `MAX()`, el SGBD encuentra el valor máximo y retorna todos los registros que lo alcanzan, permitiendo empates naturales sin bucles en Java. |
| **5** | **Mostrar gastos mayores a un monto específico** | `SELECT id, concepto, monto, categoria, fecha FROM gastos WHERE monto > ? ORDER BY monto ASC` | El filtrado ocurre en el motor relacional antes de transmitir información, transfiriendo únicamente las tuplas que satisfacen el predicado. |
| **6** | **Buscar gasto por concepto (subcadena)** | `SELECT id, concepto, monto, categoria, fecha FROM gastos WHERE LOWER(concepto) LIKE LOWER(?) ORDER BY id ASC` | Se delega el algoritmo de coincidencia de subcadenas al operador relacional `LIKE %termino%`. |

---

### 4.4. Estructura de la Solución

```
src/
├── main/
│   ├── java/analisis/controlgastosdeclarativo/
│   │   ├── ControlGastosDeclarativoApplication.java  # Interfaz de consola con menú interactivo
│   │   ├── database/
│   │   │   └── DatabaseConnection.java               # Conexión JDBC y aprovisionamiento automático
│   │   ├── model/
│   │   │   ├── Gasto.java                            # Registro inmutable (Java record)
│   │   │   └── ResumenGastos.java                    # Contenedor inmutable para agregaciones (SUM/COUNT)
│   │   ├── repository/
│   │   │   └── GastoRepository.java                  # Implementación de las 6 consultas declarativas SQL
│   │   ├── service/
│   │   │   └── GastoService.java                     # Reglas de negocio y delegación al repositorio
│   │   └── util/
│   │       └── TablaFormatter.java                   # Formato tabular profesional y formato moneda
│   └── resources/
│       └── application.properties                    # Configuración de conexión MySQL
└── test/
    └── java/analisis/controlgastosdeclarativo/
        └── ControlGastosDeclarativoApplicationTests.java # Pruebas unitarias de las 6 funcionalidades (JUnit 5)
```

---

### 4.5. Requisitos y Ejecución

#### Requisitos:
1. **Java JDK 17** o superior.
2. **Servicio MySQL** (ej. XAMPP) activo en el puerto `3306` (usuario `root`, sin contraseña por defecto). La base de datos `control_gastos` y la tabla `gastos` son creadas automáticamente por la aplicación si no existen.

#### Comandos de Ejecución en Consola (PowerShell):
```powershell
# Compilar y ejecutar la aplicación interactiva:
$env:JAVA_HOME = "C:\Users\MI PC\.jdks\ms-17.0.18"
.\mvnw.cmd compile exec:java

# Ejecutar las pruebas automatizadas (las 6 pruebas pasan al 100%):
.\mvnw.cmd test
```

#### Ejecución en IntelliJ IDEA:
1. Abrir la carpeta del proyecto en IntelliJ IDEA.
2. Navegar a `ControlGastosDeclarativoApplication.java`.
3. Clic derecho en `main()` -> **Run 'ControlGastosDeclarativoApplication.main()'**.
