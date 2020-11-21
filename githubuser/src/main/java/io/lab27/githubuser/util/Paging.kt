package io.lab27.githubuser.util

import androidx.paging.PagingSource
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.network.api.UserApi
import retrofit2.HttpException
import java.io.IOException

class UserDataSource(
    private val userApi: UserApi,
    val query: String
) :
    PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = userApi.getUser_coroutines_p(query, currentLoadingPageKey)
            val responseData = mutableListOf<User>()
            val data = response.items ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            L.e("UserDataSource exception : ${e.message}")
            return LoadResult.Error(e)
        }
    }

}

class UserPagingSource(
    private val userApi: UserApi,
    val query: String
) : PagingSource<Int, User>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, User> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = userApi.getUser_coroutines_p(query, nextPageNumber)
            return LoadResult.Page(
                data = response.items,
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber + 1
//                nextKey = response.nextPageNumber
            )
        } catch (e: IOException) {
            // IOException for network failures.
            L.e("IOException")
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            L.e("HttpException")

            return LoadResult.Error(e)
        }
    }
}