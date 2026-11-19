# Pipi Eventos · Android

Aplicación Android nativa para quienes organizan un evento y para artistas que desean ofrecer su show.

La administración interna se mantiene en el repositorio web [`PipiEventos`](https://github.com/Ch1405041C0/PipiEventos).

## Estándar Aldren Studio Lab

Este proyecto adopta las convenciones de [Aldren Core Template](https://github.com/Ch1405041C0/Aldren-Core-Template):

- `AGENTS.md` contiene reglas estables para el trabajo asistido.
- `.dev/docs/` se usa para borradores temporales y está fuera de Git.
- `docs/designs/` reserva especificaciones aprobadas antes de cambios de alcance relevantes.

La plantilla es un estándar de ingeniería y documentación; la base técnica de esta app es Android nativo con Kotlin y Jetpack Compose.

## Alcance inicial

- Cliente: responde un recorrido corto, una pregunta por pantalla, para definir tipo de evento, zona, invitados, fecha, horario, presupuesto e intereses.
- Cliente: recibe propuestas de artistas y lugares; puede abrir WhatsApp con una consulta precargada.
- Artista: presenta su show en tres pasos breves: contacto, características y valor orientativo.
- Artista: visualiza que su perfil queda en revisión y que la comisión de Pipi Eventos es del 30% del monto acordado.

Los datos del primer corte son demostrativos y viven en memoria. El siguiente hito será conectarlos a la misma base de datos y reglas de acceso que use el panel Admin.

## Abrir en Android Studio

1. Cloná el repositorio y elegí **Open** sobre esta carpeta.
2. Esperá la sincronización de Gradle.
3. Elegí un emulador o teléfono Android y ejecutá la app.

La versión mínima es Android 7.0 (API 24).
