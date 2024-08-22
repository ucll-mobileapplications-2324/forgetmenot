// ItemView
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
import androidx.compose.ui.unit.dp
import be.app.forgetmenot.domain.Item
import forgetmenot.composeapp.generated.resources.Res
import forgetmenot.composeapp.generated.resources.delete
import forgetmenot.composeapp.generated.resources.edit
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ItemView(
    item: Item,
    onSelect: (Item) -> Unit,
    onEdit: (Item) -> Unit,
    onDelete: (Item) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(item) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.name,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize
        )
        Row {
            IconButton(onClick = { onEdit(item) }) {
                Icon(
                    painter = painterResource(Res.drawable.edit),
                    contentDescription = "Edit Item Icon"
                )
            }
            IconButton(onClick = { onDelete(item) }) {
                Icon(
                    painter = painterResource(Res.drawable.delete),
                    contentDescription = "Delete Item Icon"
                )
            }
        }
    }
}