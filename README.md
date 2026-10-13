# Sistema de Seguimiento de Gastos - Comparativa de Paradigmas

Proyecto colaborativo para implementar y analizar un sistema de seguimiento de gastos bajo diferentes paradigmas de programación, aplicando una estrategia estricta de ramas (*Git Branching Model*) y normas de colaboración.

---

## 1. Paradigmas a Desarrollar

El sistema explora las características, fortalezas y diferencias de implementación en los siguientes 5 paradigmas:

1. **Paradigma Estructurado**: Enfoque procedimental, funciones y control de flujo algorítmico secuencial/cíclico.
2. **Paradigma Orientado a Objetos (POO)**: Clases, encapsulamiento, abstracción y responsabilidades (`GestorGastos`, `Gasto`).
3. **Paradigma Dirigido por Eventos**: Emisión y captura asíncrona de eventos, manejadores (*event listeners / handlers*).
4. **Paradigma Declarativo**: Enfoque basado en qué resultado se desea en lugar del cómo (consultas SQL / SQLite, filtros de datos).
5. **Paradigma Funcional**: Funciones puras, inmutabilidad, composición y operadores de alto orden (`map`, `filter`, `reduce`).

---

## 2. Estrategia de Ramas (Git Branching Model)

Este repositorio sigue un modelo de ramas estructurado:

* **`main` / `master`**: Código totalmente estable, probado y listo para entrega final.
* **`develop`**: Rama base de integración donde convergen las características finalizadas.
* **Feature Branches**: Cada paradigma debe desarrollarse en su propia rama independiente creada a partir de `develop`:
  * `feature/paradigma-estructurado`
  * `feature/paradigma-poo`
  * `feature/paradigma-eventos`
  * `feature/paradigma-declarativo`
  * `feature/paradigma-funcional`

---

## 3. Normas de Colaboración y Reparto de Trabajo

* **Commits individuales**: Todos los miembros del equipo deben registrar contribuciones en el historial de Git. *Repositorios con commits realizados por un solo integrante serán penalizados.*
* **Mensajes de commit estandarizados**: Aplicar formato claro, imperativo y bajo la convención de Conventional Commits:
  * `feat: agrega clase GestorGastos en POO`
  * `fix: corrige sintaxis SQL en busqueda por concepto`
  * `docs: actualiza respuestas de analisis en README`
* **Pull Requests (PR)**: La integración de cada rama `feature/*` hacia `develop` debe realizarse obligatoriamente mediante un Pull Request en GitHub, requiriendo la revisión y aprobación (*Approve*) de al menos otro integrante del equipo antes del Merge.
