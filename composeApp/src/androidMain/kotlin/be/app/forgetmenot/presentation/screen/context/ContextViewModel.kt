package be.app.forgetmenot.presentation.screen.context

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import be.app.forgetmenot.data.MongoDB
import be.app.forgetmenot.domain.ContextAction
import be.app.forgetmenot.domain.Context
import be.app.forgetmenot.domain.ItemAction
import be.app.forgetmenot.domain.Item
import be.app.forgetmenot.domain.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class ContextViewModel(
    private val mongoDB: MongoDB
): ScreenModel {

    private val _items = MutableStateFlow<RequestState<List<Item>>>(RequestState.Idle)
    val items: StateFlow<RequestState<List<Item>>> = _items

    fun loadItems(contextId: ObjectId) {
        screenModelScope.launch(Dispatchers.Main) {
            _items.value = RequestState.Loading
            mongoDB.readItems(contextId).collectLatest {
                _items.value = it
            }
        }
    }

    fun setAction(action: ContextAction) {
        when (action) {
            is ContextAction.Add -> {
                addContext(action.context)
            }

            is ContextAction.Update -> {
                updateContext(action.context)
            }

            else -> {}
        }
    }

    fun setItemAction(action: ItemAction) {
        when (action) {
            is ItemAction.Add -> addItem(action.item)
            //is ItemAction.Update -> updateItem(action.item)
            is ItemAction.Delete -> deleteItem(action.item)
        }
    }

    private fun addContext(context: Context) {
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.addContext(context)
        }
    }

    private fun updateContext(context: Context) {
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.updateContext(context)
        }
    }

    private fun addItem(item: Item) {
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.addItem(item)
        }
    }

    /*private fun updateItem(item: Item) {
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.updateItem(item)
        }
    }*/

    private fun deleteItem(item: Item) {
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.deleteItem(item)
        }
    }
}