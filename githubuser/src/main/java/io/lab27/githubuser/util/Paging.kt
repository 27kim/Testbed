package io.lab27.githubuser.util

import androidx.paging.PagingSource
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.datasource.remote.RemoteDataSource
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    val backend: RemoteDataSource,
    val query: String
) : PagingSource<Int, User>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, User> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = backend.getUser_coroutines_p(query, nextPageNumber)
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