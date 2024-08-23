package be.app.forgetmenot.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import be.app.forgetmenot.domain.Context
import forgetmenot.composeapp.generated.resources.Res
import forgetmenot.composeapp.generated.resources.delete
import forgetmenot.composeapp.generated.resources.add
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ContextView(
    context: Context,
    onSelect: (Context) -> Unit,
    onDelete: (Context) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(context) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = context.name,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize
        )
       Row {
            /*IconButton(onClick = { onAddItem(context) }) {
                Icon(
                    painter = painterResource(Res.drawable.add),
                    contentDescription = "Add Item Icon"
                )
            }*/
            IconButton(onClick = { onDelete(context) }) {
                Icon(
                    painter = painterResource(Res.drawable.delete),
                    contentDescription = "Delete Context Icon"
                )
            }
        }
    }
}