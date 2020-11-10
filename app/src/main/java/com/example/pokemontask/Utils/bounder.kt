
import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.example.movieapp.utils.ApiResult

import com.mindorks.framework.mvvm.utils.Resource


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
abstract class NetworkBoundResource<CacheObj, NetworkObj> {

    fun asFlow() = flow {
        val dbValue = loadFromDb().first()
        if (shouldFetch(dbValue)) {
            val apiResponse = fetchFromNetwork()
            when (apiResponse) {
                is ApiResult.Success -> {

                    saveNetworkResult(apiResponse.value)
                    emitAll(loadFromDb().map { Resource.success(it) })
                }
                is ApiResult.GenericError -> {
                    emitAll(loadFromDb().map {Resource.error(apiResponse.errorMessage!!, it)})
                }
                is ApiResult.NetworkError->{
                    emitAll(loadFromDb().map {Resource.error(
                        "Network Error", it)})
                }
            }
        } else {

            emitAll(loadFromDb().map { Resource.success(it)
            })
        }
    }
    protected abstract suspend fun saveNetworkResult(item: NetworkObj)


    protected abstract fun shouldFetch(data: CacheObj?): Boolean


    protected abstract fun loadFromDb(): Flow<CacheObj>

    protected abstract suspend fun fetchFromNetwork(): ApiResult<NetworkObj>
}
