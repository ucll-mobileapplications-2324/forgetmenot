package be.app.forgetmenot.domain

sealed class ItemAction {
    data class Add(val item: Item) : ItemAction()
    data class Update(val item: Item) : ItemAction()
    data class Delete(val item: Item) : ItemAction()
}