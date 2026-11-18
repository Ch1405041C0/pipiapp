# Pipi Eventos Android · MVP v0

**Estado:** alcance inicial confirmado el 18 de noviembre de 2026.
**Plataforma:** Android nativo, Kotlin + Jetpack Compose.
**Panel interno:** web separada en [`PipiEventos`](https://github.com/Ch1405041C0/PipiEventos).

## Objetivo

Permitir que una persona arme un evento y consulte propuestas de artistas o lugares, mientras que un artista pueda postular su show para revisión del equipo administrativo.

## Roles y límites

| Rol | Puede hacer en esta app | No corresponde a esta app |
| --- | --- | --- |
| Cliente | Definir evento, ver matches, abrir WhatsApp para consultar. | Aprobar artistas, liquidar comisiones, administrar reservas. |
| Artista | Completar perfil, tipo/duración de show, zona, precio y WhatsApp. | Publicarse sin revisión ni administrar cobros. |
| Admin | — | Opera exclusivamente en la web Admin. |

## Flujo cliente

1. Elige “Quiero armar un evento”.
2. Informa tipo de evento, zona, invitados, día, horario, presupuesto y experiencias buscadas.
3. Ve tarjetas de artistas y un lugar sugerido.
4. Toca una tarjeta para abrir WhatsApp con un mensaje de consulta precargado.

## Flujo artista

1. Elige “Quiero ofrecer mi show”.
2. Completa nombre, WhatsApp, zona, tipo, duración, precio y descripción.
3. Envía la propuesta para revisión.
4. Ve el estado pendiente y la regla comercial: Pipi Eventos descuenta el 30% del importe acordado con el cliente.

## Datos del primer corte

Las propuestas, resultados y estados son demostrativos y viven en memoria. No se almacenan datos personales ni se procesan pagos en esta versión.

## Próximo corte

Conectar la app y la web Admin a una misma base de datos, agregar autenticación por rol y persistir solicitudes, perfiles, eventos, matches y estados de reserva.

## Criterios verificables

- El cliente puede recorrer el flujo hasta abrir el selector de WhatsApp con un texto prellenado.
- El artista no puede enviar el perfil si faltan nombre, WhatsApp o precio.
- La app deja explícita la comisión del 30% para el artista.
- No existe entrada Admin dentro de la app móvil.
