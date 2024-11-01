package com.rspk.adminapp.di

import com.rspk.adminapp.firebase.FireBaseAccount
import com.rspk.adminapp.firebase.FireBaseDatabase
import com.rspk.adminapp.firebase.FireBaseStorage
import com.rspk.adminapp.firebase.impl.FireBaseAccountImpl
import com.rspk.adminapp.firebase.impl.FireBaseDatabaseImpl
import com.rspk.adminapp.firebase.impl.FireBaseStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ClassConnectModule {

    @Binds abstract fun bindFireBaseAccount(impl: FireBaseAccountImpl): FireBaseAccount

    @Binds abstract fun bindFireBaseDatabase(impl: FireBaseDatabaseImpl): FireBaseDatabase

    @Binds abstract fun bindFireBaseStorage(impl: FireBaseStorageImpl): FireBaseStorage
}