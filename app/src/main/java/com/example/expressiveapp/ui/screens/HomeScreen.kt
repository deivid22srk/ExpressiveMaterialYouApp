package com.example.expressiveapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

// ---- Dados de exemplo ----
data class Task(
    val id: Int,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val color: Color
)

private val sampleTasks = listOf(
    Task(1, "Design Sprint", "Revisar protótipos do Figma", Icons.Outlined.DesignServices, Color(0xFF6750A4)),
    Task(2, "Reunião equipe", "Daily às 09:30", Icons.Outlined.Groups, Color(0xFF4A6741)),
    Task(3, "Estudos Compose", "Material You 3 avançado", Icons.Outlined.MenuBook, Color(0xFF7D5260)),
    Task(4, "Academia", "Treino cardio 30min", Icons.Outlined.FitnessCenter, Color(0xFF0842A0)),
    Task(5, "Ler artigo", "State hoisting no Compose", Icons.Outlined.Article, Color(0xFF6D4C41)),
    Task(6, "Meditar", "10 minutos de mindfulness", Icons.Outlined.SelfImprovement, Color(0xFF4A6572))
)

// Cards de categoria com gradientes expressivos
data class CategoryCard(
    val name: String,
    val icon: ImageVector,
    val gradient: List<Color>,
    val taskCount: Int
)

private val categories = listOf(
    CategoryCard("Trabalho", Icons.Outlined.Work, listOf(Color(0xFF6750A4), Color(0xFF9A82DB)), 3),
    CategoryCard("Pessoal", Icons.Outlined.Favorite, listOf(Color(0xFF7D5260), Color(0xFFB58392)), 4),
    CategoryCard("Saúde", Icons.Outlined.FavoriteBorder, listOf(Color(0xFF4A6741), Color(0xFF7BA36E)), 2),
    CategoryCard("Estudos", Icons.Outlined.School, listOf(Color(0xFF0842A0), Color(0xFF4A7DD0)), 5)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onToggleTheme: () -> Unit = {},
    isDarkTheme: Boolean = false
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var fabExtended by remember { mutableStateOf(true) }
    val fabAnimationProgress by animateFloatAsState(
        targetValue = if (fabExtended) 1f else 0f,
        animationSpec = tween(300)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Olá, Dev! 👋",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Hoje é dia de criar algo incrível",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    // Botão de notificações com badge
                    BadgedBox(
                        badge = {
                            Badge {
                                Text("3")
                            }
                        }
                    ) {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Notificações"
                            )
                        }
                    }
                    // Botão de alternar tema
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                            contentDescription = "Alternar tema"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
                    label = { Text("Início") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.CalendarMonth, contentDescription = null) },
                    label = { Text("Agenda") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.BarChart, contentDescription = null) },
                    label = { Text("Analytics") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                    label = { Text("Perfil") },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 }
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showBottomSheet = true },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = null
                    )
                },
                text = {
                    AnimatedVisibility(
                        visible = fabExtended,
                        enter = fadeIn() + expandHorizontally(),
                        exit = fadeOut() + shrinkHorizontally()
                    ) {
                        Text("Nova Tarefa")
                    }
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                expanded = fabExtended,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .shadow(12.dp, RoundedCornerShape(16.dp))
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // === Seção de saudação ===
            item {
                Spacer(modifier = Modifier.height(4.dp))
                // Cards de categorias em horizontal
                Text(
                    text = "Categorias",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            // Grid de categorias
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    categories.chunked(2).forEach { rowCategories ->
                        rowCategories.forEach { category ->
                            CategoryCardItem(
                                category = category,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // === Seção de progresso ===
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Progresso Hoje",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ProgressCard()
            }

            // === Seção de tarefas ===
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tarefas de Hoje",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { }) {
                        Text("Ver todas")
                        Icon(Icons.Outlined.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                }
            }

            // Lista de tarefas
            items(sampleTasks) { task ->
                TaskCard(task = task)
            }

            // Espaço extra para o FAB
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }

    // Bottom Sheet
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp
        ) {
            BottomSheetContent(
                onCreateTask = { showBottomSheet = false }
            )
        }
    }
}

@Composable
fun CategoryCardItem(
    category: CategoryCard,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(category.gradient),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(12.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Column {
                Icon(
                    imageVector = category.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = category.name,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${category.taskCount} tarefas",
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun ProgressCard() {
    val progress = 0.65f // 65% concluído

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "6 / 9 tarefas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "${(progress * 100).roundToInt()}%",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Barra de progresso linear
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(value = "3", label = "Pendentes", color = MaterialTheme.colorScheme.tertiary)
                StatItem(value = "6", label = "Concluídas", color = MaterialTheme.colorScheme.primary)
                StatItem(value = "2", label = "Em andamento", color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun StatItem(value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun TaskCard(task: Task) {
    var isChecked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isChecked) 2.dp else 4.dp,
                shape = RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isChecked)
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
            else
                MaterialTheme.colorScheme.surface
        ),
        onClick = { isChecked = !isChecked }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox circular customizado
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = task.color,
                    uncheckedColor = MaterialTheme.colorScheme.outline,
                    checkmarkColor = MaterialTheme.colorScheme.surface
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Ícone do card com fundo circular
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(task.color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = task.icon,
                    contentDescription = null,
                    tint = task.color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isChecked)
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    else
                        MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )
                Text(
                    text = task.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }

            // Menu de mais opções
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "Mais opções",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun BottomSheetContent(onCreateTask: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Nova Tarefa",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título da tarefa") },
            leadingIcon = {
                Icon(Icons.Outlined.TaskAlt, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descrição (opcional)") },
            leadingIcon = {
                Icon(Icons.Outlined.Description, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            minLines = 3,
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onCreateTask,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Cancelar")
            }

            Button(
                onClick = onCreateTask,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                enabled = title.isNotBlank()
            ) {
                Icon(Icons.Outlined.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Criar")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
