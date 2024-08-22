package be.app.forgetmenot.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import be.app.forgetmenot.data.MongoDB
import be.app.forgetmenot.domain.Context
import be.app.forgetmenot.domain.RequestState
import be.app.forgetmenot.domain.ContextAction
import be.app.forgetmenot.domain.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

typealias MutableContexts = MutableState<RequestState<List<Context>>>
typealias Contexts = MutableState<RequestState<List<Context>>>

class HomeViewModel(private val mongoDB: MongoDB) : ScreenModel {
    private var _contexts: MutableContexts = mutableStateOf(RequestState.Idle)
    val contexts: Contexts = _contexts

    init {
        _contexts.value = RequestState.Loading
        screenModelScope.launch(Dispatchers.Main) {
            delay(500)
            mongoDB.readContexts().collectLatest {
                _contexts.value = it
            }
        }
    }

    fun setContextAction(action: ContextAction) {
        when (action) {
            is ContextAction.Add -> {
                addContext(action.context)
            }
            is ContextAction.Update -> {
                updateContext(action.context)
            }
            is ContextAction.Delete -> {
                deleteContext(action.context)
            }

           // is ContextAction.AddItem -> TODO()
            //is ContextAction.RemoveItem -> TODO()
        }
    }

    private fun addContext(context: Context) {
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.addContext(context)
            refreshContexts()
        }
    }

    private fun updateContext(context: Context) {
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.updateContext(context)
            refreshContexts()
        }
    }

    private fun deleteContext(context: Context) {
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.deleteContext(context)
            refreshContexts()
        }
    }

    private fun refreshContexts() {
        screenModelScope.launch(Dispatchers.Main) {
            mongoDB.readContexts().collectLatest {
                _contexts.value = it
            }
        }
    }

   /* private fun addItemToContext(contextId: ObjectId, item: Item) {
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.addItem(item)
            val context = mongoDB.getContextById(contextId)
            context?.let {
                it.items.add(item)
                mongoDB.updateContext(it)
                refreshContexts()
            }
        }
    }

    private fun removeItemFromContext(contextId: ObjectId, item: Item) {
        screenModelScope.launch(Dispatchers.IO) {
            mongoDB.deleteItem(item)
            val context = mongoDB.getContextById(contextId)
            context?.let {
                it.items.remove(item)
                mongoDB.updateContext(it)
                refreshContexts()
            }
        }
    }*/
}