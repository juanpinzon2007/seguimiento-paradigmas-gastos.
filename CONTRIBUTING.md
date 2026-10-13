# Normas de Colaboración y Reparto de Trabajo

Este documento define el flujo de trabajo oficial para todos los integrantes del equipo en el proyecto **Seguimiento de Gastos por Paradigmas**.

---

## 1. Configuración de Identidad de Git (Commits Individuales)

> **Regla estricta:** Todos los miembros del equipo deben registrar contribuciones en el historial de Git. Repositorios con commits realizados por un solo integrante serán penalizados.

Antes de comenzar a trabajar en tu máquina local, asegúrate de configurar tu nombre y correo institucional en Git:

```bash
git config --global user.name "Tu Nombre Completo"
git config --global user.email "tu_correo@unihumboldt.edu.co"
```

O si solo deseas configurarlo para este repositorio:

```bash
git config user.name "Tu Nombre Completo"
git config user.email "tu_correo@unihumboldt.edu.co"
```

Verifica tu configuración con:
```bash
git config user.name
git config user.email
```

---

## 2. Estrategia de Ramas (*Git Branching Model*)

* **`main`**: Código totalmente estable, probado y listo para entrega final.
* **`develop`**: Rama base de integración donde convergen las características finalizadas.
* **`feature/*`**: Cada paradigma debe desarrollarse en su propia rama independiente creada a partir de `develop`:
  * `feature/paradigma-estructurado`
  * `feature/paradigma-poo`
  * `feature/paradigma-eventos`
  * `feature/paradigma-declarativo`
  * `feature/paradigma-funcional`

### Flujo de trabajo para iniciar una tarea:

1. Asegúrate de tener los últimos cambios de `develop`:
   ```bash
   git checkout develop
   git pull origin develop
   ```
2. Cámbiate a tu rama de trabajo asignada:
   ```bash
   git checkout feature/paradigma-<tu-paradigma>
   ```
   *(Si la creas de cero a partir de develop: `git checkout -b feature/paradigma-<tu-paradigma> develop`)*

---

## 3. Mensajes de Commit Estandarizados (Conventional Commits)

Los mensajes de commit deben ser claros, imperativos y seguir el estándar:

* `feat:` Para nuevas funcionalidades, clases o módulos.
  * *Ejemplo:* `feat: agrega clase GestorGastos en POO`
* `fix:` Para corrección de errores o bugs.
  * *Ejemplo:* `fix: corrige sintaxis SQL en busqueda por concepto`
* `docs:` Para cambios o respuestas en la documentación.
  * *Ejemplo:* `docs: actualiza respuestas de analisis en README`
* `test:` Para adición o corrección de pruebas unitarias.
  * *Ejemplo:* `test: agrega pruebas para calculo de gastos por categoria`
* `refactor:` Para reestructuración de código sin alterar su comportamiento externo.

---

## 4. Proceso de Integración mediante Pull Requests (PR)

> **Regla estricta:** La integración de cada rama `feature/*` hacia `develop` debe realizarse obligatoriamente mediante un Pull Request en GitHub, requiriendo la revisión y aprobación (**Approve**) de al menos otro integrante del equipo antes del Merge.

### Pasos para realizar la entrega de una rama:
1. Sube tus cambios a GitHub:
   ```bash
   git push origin feature/paradigma-<tu-paradigma>
   ```
2. Ve a GitHub y abre un nuevo **Pull Request**:
   * **Base repository/branch:** `develop`
   * **Compare branch:** `feature/paradigma-<tu-paradigma>`
3. Completa la plantilla de descripción del PR.
4. Asigna como **Reviewer** (Revisor) a al menos otro compañero del equipo.
5. El revisor debe inspeccionar los cambios en la pestaña *Files changed*, dejar observaciones si aplica, y seleccionar **Approve**.
6. Una vez aprobado, se ejecuta el **Merge pull request** hacia `develop`.
7. Ningún integrante debe hacer push directo a `develop` ni a `main`.
