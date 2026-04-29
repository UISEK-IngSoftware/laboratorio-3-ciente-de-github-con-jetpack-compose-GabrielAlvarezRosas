# Laboratorio Android. Cliente de GitHub

## Datos  del estudiante
- Gabriel Alvarez Rosas
- Ing. en Informática

## Descripción del Proyecto
Este proyecto es un ejercicio de laboratorio para estudiantes donde se implementará una aplicación Android que simula un cliente de GitHub utilizando Jetpack Compose. La aplicación se centrará en la implementación de interfaces modernas y componentes declarativos, sin integración con la API REST de GitHub en esta fase.

## Funcionalidades Principales

### 1. Lista de Repositorios
- Implementación de una `LazyColumn` para mostrar una lista de repositorios
- Cada repositorio se mostrará en un `Composable` reutilizable
- La lista será estática (hardcoded) para este ejercicio
- Se implementará un diseño personalizado para cada ítem del repositorio usando Composables

### 2. Formulario de Proyecto
- Interfaz para ingresar datos de un nuevo proyecto usando Composables
- Campos incluidos:
  - Nombre del proyecto
  - Descripción del proyecto
- El formulario será únicamente visual (sin funcionalidad de guardado)
- Uso de `TextField` o `OutlinedTextField` para la entrada de datos

## Objetivos de Aprendizaje
- Implementación de `LazyColumn` en Jetpack Compose
- Creación de funciones `@Composable` reutilizables
- Diseño de interfaces declarativas con Compose
- Estructuración de una aplicación Android moderna
- Manejo de estado en Compose
- Aplicación de Material Design 3 con Compose

## Notas Importantes
- Este es un ejercicio de práctica enfocado en la UI con Jetpack Compose
- No se implementará conexión con la API de GitHub
- Los datos mostrados serán estáticos (hardcoded)
- El formulario será solo para demostración de Composables

## Composables Utilizados
- `Text`: Para mostrar textos como nombres y descripciones de repositorios
- `Column` y `Row`: Como contenedores para organizar los elementos de forma vertical u horizontal
- `Image` o `AsyncImage`: Para mostrar avatares o iconos de repositorios
- `TextField` / `OutlinedTextField`: Para la entrada de texto en el formulario de creación de proyecto
- `LazyColumn`: Para mostrar listas eficientes de repositorios
- `Card`: Para envolver cada ítem de repositorio con elevación y bordes
- `Button`: Para acciones del usuario

## Tecnologías Utilizadas
- Kotlin
- Android SDK
- Jetpack Compose
- Compose UI
- Material Design 3 (Material You)
- Navigation Compose (opcional)
