package be.app.forgetmenot.domain

import org.mongodb.kbson.ObjectId

sealed class ContextAction {
    data class Add(val context: Context) : ContextAction()
    data class Update(val context: Context) : ContextAction()
    data class Delete(val context: Context) : ContextAction()
    //data class AddItem(val contextId: ObjectId, val item: Item) : ContextAction()
    //data class RemoveItem(val contextId: ObjectId, val item: Item) : ContextAction()
}