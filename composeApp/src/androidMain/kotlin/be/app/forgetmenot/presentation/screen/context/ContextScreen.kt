package be.app.forgetmenot.presentation.screen.context

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import be.app.forgetmenot.domain.Context
import be.app.forgetmenot.domain.ContextAction

const val DEFAULT_CONTEXT_NAME = "Enter the Context Name"

data class ContextScreen(val context: Context? = null) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<ContextViewModel>()
        var currentName by remember {
            mutableStateOf(context?.name ?: DEFAULT_CONTEXT_NAME)
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
                if (currentName.isNotEmpty()) {
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
            BasicTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 24.dp)
                    .padding(
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding()
                    ),
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                value = currentName,
                onValueChange = { name -> currentName = name }
            )
        }
    }
}