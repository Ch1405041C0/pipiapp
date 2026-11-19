package com.pipieventos.app.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

private val PipiPurple = Color(0xFF6425A6)
private val PipiLilac = Color(0xFFF1E5FF)
private val PipiInk = Color(0xFF22152D)
private val PipiBackground = Color(0xFFFCF9FF)
private val PipiGreen = Color(0xFF1D7A57)

private enum class AppScreen {
    WELCOME,
    EVENT_PLANNER,
    MATCHES,
    ARTIST_PROFILE,
    ARTIST_STATUS,
}

private data class EventDraft(
    val eventType: String = "Cumpleaños",
    val zone: String = "CABA · GBA Norte",
    val guests: Int = 20,
    val date: String = "Sábado 22 de noviembre",
    val time: String = "21:00",
    val budget: String = "250000",
    val services: List<String> = listOf("Stand up"),
)

private data class Provider(
    val emoji: String,
    val name: String,
    val category: String,
    val detail: String,
    val price: Int,
)

private val featuredProviders = listOf(
    Provider("😂", "Sofi Correa", "Stand up", "45 min · CABA y GBA Norte", 120000),
    Provider("🎸", "Ritmo Fuego", "Música en vivo", "Set acústico · 60 min", 180000),
    Provider("💃", "Milena Rouge", "Burlesque", "Show escénico · 30 min", 145000),
)

@Composable
fun PipiEventosApp() {
    var screen by rememberSaveable { mutableStateOf(AppScreen.WELCOME) }
    var eventDraft by remember { mutableStateOf(EventDraft()) }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = PipiPurple,
            onPrimary = Color.White,
            secondary = PipiGreen,
            surface = Color.White,
            background = PipiBackground,
            onBackground = PipiInk,
        ),
    ) {
        when (screen) {
            AppScreen.WELCOME -> WelcomeScreen(
                onCustomer = { screen = AppScreen.EVENT_PLANNER },
                onArtist = { screen = AppScreen.ARTIST_PROFILE },
            )

            AppScreen.EVENT_PLANNER -> EventPlannerScreen(
                onBack = { screen = AppScreen.WELCOME },
                onMatches = {
                    eventDraft = it
                    screen = AppScreen.MATCHES
                },
            )

            AppScreen.MATCHES -> MatchesScreen(
                draft = eventDraft,
                onBack = { screen = AppScreen.EVENT_PLANNER },
                onNewPlan = { screen = AppScreen.EVENT_PLANNER },
            )

            AppScreen.ARTIST_PROFILE -> ArtistProfileScreen(
                onBack = { screen = AppScreen.WELCOME },
                onSubmitted = { screen = AppScreen.ARTIST_STATUS },
            )

            AppScreen.ARTIST_STATUS -> ArtistStatusScreen(
                onBackToStart = { screen = AppScreen.WELCOME },
            )
        }
    }
}

@Composable
private fun WelcomeScreen(
    onCustomer: () -> Unit,
    onArtist: () -> Unit,
) {
    Scaffold(containerColor = PipiBackground) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = "pipi",
                    color = PipiPurple,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = "eventos",
                    color = PipiInk,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Spacer(Modifier.height(46.dp))
                Text(
                    text = "¿Qué querés hacer hoy?",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = PipiInk,
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Elegí un camino y te guiamos paso a paso. Sin formularios eternos.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = PipiInk.copy(alpha = 0.72f),
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                RoleCard(
                    emoji = "🎉",
                    title = "Quiero armar un evento",
                    body = "Respondé unas preguntas cortas y encontrá artistas y lugares para tu plan.",
                    button = "Empezar mi plan",
                    onClick = onCustomer,
                )
                RoleCard(
                    emoji = "🎤",
                    title = "Quiero ofrecer mi show",
                    body = "Presentá tu show en tres pasos para que el equipo lo revise y lo publique.",
                    button = "Publicar mi show",
                    onClick = onArtist,
                )
            }

            Text(
                text = "La coordinación y los pagos se confirman con el equipo de Pipi Eventos.",
                style = MaterialTheme.typography.bodySmall,
                color = PipiInk.copy(alpha = 0.55f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun RoleCard(
    emoji: String,
    title: String,
    body: String,
    button: String,
    onClick: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = emoji, fontSize = 28.sp)
            Spacer(Modifier.height(10.dp))
            Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text(text = body, style = MaterialTheme.typography.bodyMedium, color = PipiInk.copy(alpha = 0.7f))
            Spacer(Modifier.height(18.dp))
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = PipiPurple),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(button)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventPlannerScreen(
    onBack: () -> Unit,
    onMatches: (EventDraft) -> Unit,
) {
    var step by rememberSaveable { mutableIntStateOf(0) }
    var eventType by rememberSaveable { mutableStateOf("Cumpleaños") }
    var zone by rememberSaveable { mutableStateOf("CABA · GBA Norte") }
    var guests by rememberSaveable { mutableIntStateOf(20) }
    var date by rememberSaveable { mutableStateOf("Sábado 22 de noviembre") }
    var time by rememberSaveable { mutableStateOf("21:00") }
    var budget by rememberSaveable { mutableStateOf("250000") }
    val selectedServices = remember { mutableStateListOf("Stand up") }
    val eventTypes = listOf("Cumpleaños", "Despedida", "After office", "Otro")
    val services = listOf("Stand up", "Comedia", "Pole dance", "Burlesque", "Música en vivo")

    val totalSteps = 6

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Armá tu evento",
                onBack = { if (step == 0) onBack() else step -= 1 },
            )
        },
        containerColor = PipiBackground,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 18.dp),
        ) {
            JourneyProgress(step = step, totalSteps = totalSteps)
            Spacer(Modifier.height(30.dp))

            when (step) {
                0 -> {
                    QuestionHeading(
                        title = "Primero: ¿qué celebrás?",
                        body = "Así afinamos el tipo de propuesta.",
                    )
                    ChipRow(options = eventTypes, selected = setOf(eventType)) { eventType = it }
                }

                1 -> {
                    QuestionHeading(
                        title = "¿En qué zona querés hacerlo?",
                        body = "Buscamos artistas y lugares que trabajen cerca.",
                    )
                    OutlinedTextField(
                        value = zone,
                        onValueChange = { zone = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Por ejemplo: Palermo, Ballester o Tigre") },
                    )
                }

                2 -> {
                    QuestionHeading(
                        title = "¿Cuántas personas van a ser?",
                        body = "Nos ayuda a filtrar shows y capacidad del lugar.",
                    )
                    Counter(value = guests, onValueChange = { guests = it })
                }

                3 -> {
                    QuestionHeading(
                        title = "¿Cuándo es el evento?",
                        body = "Con una fecha estimada ya podemos consultar disponibilidad.",
                    )
                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Día") },
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = time,
                        onValueChange = { time = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Horario estimado") },
                    )
                }

                4 -> {
                    QuestionHeading(
                        title = "¿Qué presupuesto imaginás?",
                        body = "Es orientativo; después lo confirmás con cada propuesta.",
                    )
                    OutlinedTextField(
                        value = budget,
                        onValueChange = { budget = it.filter(Char::isDigit) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        prefix = { Text("$") },
                        label = { Text("Monto en pesos") },
                    )
                }

                else -> {
                    QuestionHeading(
                        title = "¿Qué te gustaría sumar?",
                        body = "Podés elegir más de una experiencia.",
                    )
                    ChipRow(options = services, selected = selectedServices.toSet()) { item ->
                        if (item in selectedServices) selectedServices.remove(item) else selectedServices.add(item)
                    }
                }
            }

            Spacer(Modifier.height(34.dp))
            Button(
                onClick = {
                    if (step < totalSteps - 1) {
                        step += 1
                    } else {
                        onMatches(
                            EventDraft(
                                eventType = eventType,
                                zone = zone.ifBlank { "CABA · GBA Norte" },
                                guests = guests,
                                date = date.ifBlank { "A definir" },
                                time = time.ifBlank { "A definir" },
                                budget = budget.ifBlank { "0" },
                                services = selectedServices.toList(),
                            ),
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PipiPurple),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            ) {
                Text(if (step == totalSteps - 1) "Ver propuestas" else "Siguiente")
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun MatchesScreen(
    draft: EventDraft,
    onBack: () -> Unit,
    onNewPlan: () -> Unit,
) {
    val context = LocalContext.current
    val budget = draft.budget.toIntOrNull() ?: 0

    Scaffold(
        topBar = { AppTopBar(title = "Tus propuestas", onBack = onBack) },
        containerColor = PipiBackground,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 18.dp),
        ) {
            Text(
                text = "${draft.eventType} · ${draft.zone}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "${draft.guests} personas · ${draft.date} · ${draft.time}",
                style = MaterialTheme.typography.bodyMedium,
                color = PipiInk.copy(alpha = 0.7f),
            )
            if (budget > 0) {
                Text(
                    text = "Presupuesto declarado: ${formatMoney(budget)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PipiGreen,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Spacer(Modifier.height(24.dp))
            Text("Artistas que combinan con tu plan", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))

            featuredProviders.forEach { provider ->
                ProviderCard(
                    provider = provider,
                    onContact = {
                        openWhatsApp(
                            context,
                            "Hola ${provider.name}, estoy armando un ${draft.eventType.lowercase()} para ${draft.guests} personas en ${draft.zone}. ¿Tenés disponibilidad para ${draft.date} a las ${draft.time}?",
                        )
                    },
                )
                Spacer(Modifier.height(12.dp))
            }

            Spacer(Modifier.height(16.dp))
            Text("Lugar sugerido", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            VenueCard(
                onContact = {
                    openWhatsApp(
                        context,
                        "Hola, estoy buscando lugar para un ${draft.eventType.lowercase()} de ${draft.guests} personas en ${draft.zone} para ${draft.date}. ¿Me cuentan disponibilidad y opciones?",
                    )
                },
            )

            Spacer(Modifier.height(20.dp))
            FilledTonalButton(onClick = onNewPlan, modifier = Modifier.fillMaxWidth()) {
                Text("Editar mi plan")
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtistProfileScreen(
    onBack: () -> Unit,
    onSubmitted: () -> Unit,
) {
    var step by rememberSaveable { mutableIntStateOf(0) }
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var zone by rememberSaveable { mutableStateOf("CABA · GBA Norte") }
    var type by rememberSaveable { mutableStateOf("Stand up") }
    var duration by rememberSaveable { mutableStateOf("45") }
    var price by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    val showTypes = listOf("Stand up", "Comedia", "Pole dance", "Burlesque", "Música en vivo")

    val totalSteps = 3
    val canContinue = when (step) {
        0 -> name.isNotBlank() && phone.isNotBlank()
        1 -> duration.isNotBlank()
        else -> price.isNotBlank()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Publicá tu show",
                onBack = { if (step == 0) onBack() else step -= 1 },
            )
        },
        containerColor = PipiBackground,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 18.dp),
        ) {
            JourneyProgress(step = step, totalSteps = totalSteps)
            Spacer(Modifier.height(30.dp))

            when (step) {
                0 -> {
                    QuestionHeading(
                        title = "¿Cómo te presentamos?",
                        body = "Primero tus datos de contacto. No se publican sin revisión.",
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre o nombre artístico") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("WhatsApp") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                1 -> {
                    QuestionHeading(
                        title = "Contanos del show",
                        body = "Con esto sabemos qué tipo de evento te puede necesitar.",
                    )
                    OutlinedTextField(
                        value = zone,
                        onValueChange = { zone = it },
                        label = { Text("Zona donde trabajás") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.height(18.dp))
                    ChipRow(options = showTypes, selected = setOf(type)) { type = it }
                    Spacer(Modifier.height(14.dp))
                    OutlinedTextField(
                        value = duration,
                        onValueChange = { duration = it.filter(Char::isDigit) },
                        label = { Text("Duración en minutos") },
                        suffix = { Text("min") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                else -> {
                    QuestionHeading(
                        title = "Último detalle",
                        body = "El valor es orientativo; el monto final se acuerda con cada cliente.",
                    )
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it.filter(Char::isDigit) },
                        label = { Text("Valor orientativo") },
                        prefix = { Text("$") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("¿Qué tiene de especial tu show?") },
                        minLines = 4,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.height(18.dp))
                    CommissionNotice()
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    if (step < totalSteps - 1) step += 1 else onSubmitted()
                },
                enabled = canContinue,
                colors = ButtonDefaults.buttonColors(containerColor = PipiPurple),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            ) {
                Text(if (step == totalSteps - 1) "Enviar para revisión" else "Siguiente")
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun ArtistStatusScreen(onBackToStart: () -> Unit) {
    Scaffold(containerColor = PipiBackground) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .background(PipiLilac, RoundedCornerShape(28.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text("✓", fontSize = 42.sp, color = PipiPurple, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(24.dp))
            Text("Perfil recibido", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Tu propuesta quedó pendiente de revisión. Cuando esté aprobada podrá aparecer en los matches de clientes.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = PipiInk.copy(alpha = 0.72f),
            )
            Spacer(Modifier.height(20.dp))
            CommissionNotice()
            Spacer(Modifier.height(28.dp))
            Button(
                onClick = onBackToStart,
                colors = ButtonDefaults.buttonColors(containerColor = PipiPurple),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Volver al inicio")
            }
        }
    }
}

@Composable
private fun ProviderCard(provider: Provider, onContact: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(PipiLilac, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(provider.emoji, fontSize = 24.sp)
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(provider.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(provider.category, style = MaterialTheme.typography.bodyMedium, color = PipiPurple, fontWeight = FontWeight.SemiBold)
                    Text(provider.detail, style = MaterialTheme.typography.bodySmall, color = PipiInk.copy(alpha = 0.65f))
                }
            }
            Spacer(Modifier.height(14.dp))
            Text("Desde ${formatMoney(provider.price)}", fontWeight = FontWeight.Bold, color = PipiInk)
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = onContact,
                colors = ButtonDefaults.buttonColors(containerColor = PipiGreen),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Consultar por WhatsApp")
            }
        }
    }
}

@Composable
private fun VenueCard(onContact: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text("🍸", fontSize = 28.sp)
            Spacer(Modifier.height(8.dp))
            Text("Casa Sifón", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Bar para eventos · Palermo", color = PipiPurple, fontWeight = FontWeight.SemiBold)
            Text("Hasta 60 personas · Barra, sonido y espacio para show en vivo.", color = PipiInk.copy(alpha = 0.68f))
            Spacer(Modifier.height(14.dp))
            Button(
                onClick = onContact,
                colors = ButtonDefaults.buttonColors(containerColor = PipiGreen),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Consultar lugar")
            }
        }
    }
}

@Composable
private fun CommissionNotice() {
    Card(
        colors = CardDefaults.cardColors(containerColor = PipiLilac),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Comisión transparente", fontWeight = FontWeight.Bold, color = PipiPurple)
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Pipi Eventos descuenta el 30% del monto acordado con el cliente. La liquidación se confirma al cerrar cada fecha.",
                style = MaterialTheme.typography.bodyMedium,
                color = PipiInk.copy(alpha = 0.75f),
            )
        }
    }
}

@Composable
private fun JourneyProgress(step: Int, totalSteps: Int) {
    Text(
        text = "Pregunta ${step + 1} de $totalSteps",
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = PipiPurple,
    )
    Spacer(Modifier.height(8.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        repeat(totalSteps) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(5.dp)
                    .background(
                        color = if (index <= step) PipiPurple else PipiLilac,
                        shape = RoundedCornerShape(99.dp),
                    ),
            )
        }
    }
}

@Composable
private fun QuestionHeading(title: String, body: String) {
    Text(text = title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(8.dp))
    Text(text = body, style = MaterialTheme.typography.bodyLarge, color = PipiInk.copy(alpha = 0.7f))
    Spacer(Modifier.height(24.dp))
}

@Composable
private fun ChipRow(
    options: List<String>,
    selected: Set<String>,
    onToggle: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        options.forEach { option ->
            FilterChip(
                selected = option in selected,
                onClick = { onToggle(option) },
                label = { Text(option) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = PipiLilac,
                    selectedLabelColor = PipiPurple,
                ),
            )
        }
    }
}

@Composable
private fun Counter(value: Int, onValueChange: (Int) -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(onClick = { onValueChange((value - 1).coerceAtLeast(1)) }) { Text("−", fontSize = 24.sp) }
            Text("$value personas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            TextButton(onClick = { onValueChange((value + 1).coerceAtMost(300)) }) { Text("+", fontSize = 24.sp) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = { Text(title, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            TextButton(onClick = onBack) {
                Text("←", fontSize = 22.sp)
            }
        },
    )
}

private fun openWhatsApp(context: Context, message: String) {
    val whatsappIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
        setPackage("com.whatsapp")
    }

    runCatching { context.startActivity(whatsappIntent) }.getOrElse {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Enviar consulta"))
    }
}

private fun formatMoney(value: Int): String =
    NumberFormat.getCurrencyInstance(Locale("es", "AR")).apply {
        maximumFractionDigits = 0
    }.format(value)
