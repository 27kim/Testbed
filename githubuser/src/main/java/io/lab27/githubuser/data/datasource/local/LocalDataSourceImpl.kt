package io.lab27.githubuser.data.datasource.local

import android.util.Log
import androidx.lifecycle.LiveData
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.datasource.UserDataBase
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LocalDataSourceImpl(private val db: UserDataBase) : LocalDataSource {
    override fun queryAllUsers() = db.userDao().queryAllUsers()
    override suspend fun queryAllUsers_c(): List<User> = db.userDao().queryAllUsers_c()

    override fun addFavorite(user: User) {
        Completable.fromAction {
            db.userDao().insert(user)
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {Log.i("insertUser","onComplete")},
                {e -> Log.e("insertUser", "${e.message}")}
            )
    }

    override fun deleteFavorite(user: User) {
        Completable.fromAction {
            db.userDao().delete(user)
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {Log.i("deleteUser","onComplete")},
                {e -> Log.e("deleteUser", "${e.message}")}
            )
    }
}