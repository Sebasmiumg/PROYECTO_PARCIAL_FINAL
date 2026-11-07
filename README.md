# Pendientes

Prototipo Android en Kotlin para guardar las tareas del día sin cuenta ni conexión a internet.

## Funciones

- Lista principal con `RecyclerView` y estado de cada tarea.
- Pantalla separada para agregar una descripción y devolverla a la lista mediante un Intent.
- Pantalla de detalle abierta con el identificador de la tarea en un Intent.
- Completar o reabrir tareas desde la lista o desde el detalle.
- Eliminar tareas desde la lista o desde el detalle.
- Persistencia local en `SharedPreferences`, con los datos serializados como JSON.

## Abrir y ejecutar

1. Abre la carpeta `PendientesApp` en Android Studio.
2. Sincroniza Gradle y asegúrate de tener instalado Android SDK Platform 36.
3. Ejecuta la configuración `app` en un emulador o dispositivo con Android 7.0 (API 24) o posterior.

El proyecto usa JDK 17, Android Gradle Plugin 8.13.2, Kotlin 2.3.21 y RecyclerView 1.4.0. No solicita permisos de red.

## Guion breve para la demostración

1. Agrega una tarea desde «Agregar tarea» y muestra que aparece en la lista.
2. Ábrela para enseñar el detalle y la fecha de creación.
3. Márcala como completada y vuelve a la lista para mostrar el cambio visual.
4. Cierra y vuelve a abrir la app para mostrar la persistencia; después elimina una tarea.
