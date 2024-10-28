package com.neu.trend.dependencyinjection

import com.neu.trend.data.repository.UserRepositoryImp
import com.neu.trend.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class BindModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindUserRepository(UserRepositoryImp: UserRepositoryImp): UserRepository

}