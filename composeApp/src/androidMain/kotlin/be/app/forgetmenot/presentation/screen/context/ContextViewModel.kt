package be.app.forgetmenot.presentation.screen.context

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import be.app.forgetmenot.data.MongoDB
import be.app.forgetmenot.domain.ContextAction
import be.app.forgetmenot.domain.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContextViewModel(
    private val mongoDB: MongoDB
): ScreenModel {

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
}