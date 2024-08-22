// ItemViewModel
package be.app.forgetmenot.presentation.screen.item

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import be.app.forgetmenot.data.MongoDB
import be.app.forgetmenot.domain.Item
import be.app.forgetmenot.domain.ItemAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*class ItemViewModel(
    private val mongoDB: MongoDB
): ScreenModel {

    fun setAction(action: ItemAction) {
        when (action) {
            is ItemAction.Add -> {
                addItem(action.item)
            }

            is ItemAction.Update -> {
                updateItem(action.item)
            }

            is ItemAction.Delete -> {
                deleteItem(action.item)
            }

            is ItemAction.SetName -> TODO()
        }
    }

    private fun addItem(item: Item) {
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.addItem(item)
        }
    }

    private fun updateItem(item: Item) {
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.updateItem(item)
        }
    }

    private fun deleteItem(item: Item) {
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.deleteItem(item)
        }
    }
}*/