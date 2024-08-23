package be.app.forgetmenot.presentation.screen.context

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import be.app.forgetmenot.domain.Context
import be.app.forgetmenot.domain.ContextAction
import be.app.forgetmenot.domain.Item
import be.app.forgetmenot.domain.ItemAction
import be.app.forgetmenot.domain.RequestState
import be.app.forgetmenot.presentation.components.ErrorScreen
import be.app.forgetmenot.presentation.components.ItemView

const val DEFAULT_CONTEXT_NAME = "Enter the Context Name"

data class ContextScreen(val context: Context? = null) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<ContextViewModel>()
        var currentName by remember { mutableStateOf(context?.name ?: DEFAULT_CONTEXT_NAME) }
        var showDialog by remember { mutableStateOf(false) }
        var newItemName by remember { mutableStateOf("") }
        val items by viewModel.items.collectAsState(initial = RequestState.Idle)

        LaunchedEffect(context) {
            context?.let {
                viewModel.loadItems(it._id)
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        BasicTextField(
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize
                            ),
                            singleLine = true,
                            value = currentName,
                            onValueChange = { currentName = it }
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back Arrow"
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                if (currentName.isNotEmpty() && currentName != DEFAULT_CONTEXT_NAME) {
                    FloatingActionButton(
                        onClick = {
                            if (context != null) {
                                viewModel.setAction(
                                    action = ContextAction.Update(
                                        Context().apply {
                                            _id = context._id
                                            name = currentName
                                        }
                                    )
                                )
                            } else {
                                viewModel.setAction(
                                    action = ContextAction.Add(
                                        Context().apply {
                                            name = currentName
                                        }
                                    )
                                )
                            }
                            navigator.pop()
                        },
                        shape = RoundedCornerShape(size = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Checkmark Icon"
                        )
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 24.dp)
                    .padding(
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding()
                    )
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                DisplayItems(
                    items = items,
                    onDelete = { item ->
                        viewModel.setItemAction(ItemAction.Delete(item))
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { showDialog = true }) {
                    Text("Add Item")
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Add New Item") },
                        text = {
                            BasicTextField(
                                value = newItemName,
                                onValueChange = { newItemName = it },
                                textStyle = TextStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                                )
                            )
                        },
                        confirmButton = {
                            Button(onClick = {
                                if (newItemName.isNotEmpty()) {
                                    viewModel.setItemAction(
                                        ItemAction.Add(
                                            Item().apply {
                                                name = newItemName
                                                contextId = context?._id
                                            }
                                        )
                                    )
                                    newItemName = ""
                                    showDialog = false
                                }
                            }) {
                                Text("Add")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayItems(
    modifier: Modifier = Modifier,
    items: RequestState<List<Item>>,
    onSelect: ((Item) -> Unit)? = null,
    onDelete: ((Item) -> Unit)? = null
) {
    var showDialog by remember { mutableStateOf(false) }
    var itemToDelete: Item? by remember { mutableStateOf(null) }
    if (showDialog) {
        AlertDialog(
            title = {
                Text(text = "Delete", fontSize = MaterialTheme.typography.titleLarge.fontSize)
            },
            text = {
                Text(
                    text = "Are you sure you want to delete ${itemToDelete!!.name}?",
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            },
            confirmButton = {
                Button(onClick = {
                    onDelete?.invoke(itemToDelete!!)
                    showDialog = false
                    itemToDelete = null
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    itemToDelete = null
                    showDialog = false
                }) {
                    Text(text = "Cancel")
                }
            },
            onDismissRequest = {
                itemToDelete = null
                showDialog = false
            }
        )
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Items",
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(12.dp))
        items.DisplayResult(
            onLoading = { CircularProgressIndicator() },
            onError = { ErrorScreen(message = it) },
            onSuccess = { itemList ->
                if (itemList.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.padding(horizontal = 24.dp)) {
                        items(itemList) { item ->
                            ItemView(
                                item = item,
                                onSelect = { onSelect?.invoke(item) },
                                onDelete = {
                                    itemToDelete = item
                                    showDialog = true
                                }
                            )
                        }
                    }
                } else {
                    Text(
                        text = "No items available",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    )
                }
            }
        )
    }
}