package be.app.forgetmenot.data

import be.app.forgetmenot.domain.Context
import be.app.forgetmenot.domain.Item
import be.app.forgetmenot.domain.RequestState
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class MongoDB {
    private var realm: Realm? = null

    init {
        configureTheRealm()
    }

    private fun configureTheRealm() {
        if (realm == null || realm!!.isClosed()) {
            val config = RealmConfiguration.Builder(
                schema = setOf(Context::class, Item::class)
            )
                .compactOnLaunch()
                .build()
            realm = Realm.open(config)
        }
    }

    // Context-related methods
    fun readContexts(): Flow<RequestState<List<Context>>> {
        return realm?.query<Context>()
            ?.asFlow()
            ?.map { result -> RequestState.Success(data = result.list) }
            ?: flow { RequestState.Error(message = "Realm is not available.") }
    }

    fun readItems(contextId: ObjectId): Flow<RequestState<List<Item>>> {
        return realm?.query<Item>("contextId == $0", contextId)
            ?.asFlow()
            ?.map { result -> RequestState.Success(data = result.list) }
            ?: flow { RequestState.Error(message = "Realm is not available.") }
    }

    suspend fun getContextById(contextId: ObjectId): Context? {
        return realm?.query<Context>("_id == $0", contextId)?.first()?.find()
    }

    suspend fun addContext(context: Context) {
        realm?.write { copyToRealm(context) }
    }

    suspend fun updateContext(context: Context) {
        realm?.write {
            try {
                val queriedContext = query<Context>("_id == $0", context._id)
                    .first()
                    .find()
                queriedContext?.let {
                    findLatest(it)?.let { currentContext ->
                        currentContext.name = context.name
                    }
                }
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    suspend fun deleteContext(context: Context) {
        realm?.write {
            try {
                val queriedContext = query<Context>("_id == $0", context._id)
                    .first()
                    .find()
                queriedContext?.let {
                    findLatest(it)?.let { currentContext ->
                        delete(currentContext)
                    }
                }
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    suspend fun addItem(item: Item) {
        realm?.write { copyToRealm(item) }
    }

    suspend fun deleteItem(item: Item) {
        realm?.write {
            try {
                val queriedItem = query<Item>("_id == $0", item._id)
                    .first()
                    .find()
                queriedItem?.let {
                    findLatest(it)?.let { currentItem ->
                        delete(currentItem)
                    }
                }
            } catch (e: Exception) {
                println(e)
            }
        }
    }


}