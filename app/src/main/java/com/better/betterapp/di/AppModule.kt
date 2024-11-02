package com.better.betterapp.di

import com.better.betterapp.feature_home.data.repository.HomeRepositoryImp
import com.better.betterapp.feature_home.domain.repository.HomeRepository
import com.better.betterapp.feature_home.domain.use_cases.GetDailyTopicUseCase
import com.better.betterapp.feature_home.domain.use_cases.GetSpeakingPostsUseCase
import com.better.betterapp.feature_login.data.repository.LoginRepositoryImp
import com.better.betterapp.feature_login.domain.repository.LoginRepository
import com.better.betterapp.feature_login.domain.use_cases.HomeUseCases
import com.better.betterapp.feature_login.domain.use_cases.LoginUseCases
import com.better.betterapp.feature_login.domain.use_cases.RegisterUserUseCase
import com.better.betterapp.feature_speaking.data.repository.SpeakingRepositoryImp
import com.better.betterapp.feature_speaking.domain.repository.SpeakingRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideLoginRepository(auth: FirebaseAuth, db: FirebaseFirestore): LoginRepository {
        return LoginRepositoryImp(auth, db)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(db: FirebaseFirestore): HomeRepository {
        return HomeRepositoryImp(db)
    }

    @Provides
    @Singleton
    fun provideSpeakingRepository(db: FirebaseFirestore): SpeakingRepository {
        return SpeakingRepositoryImp(db)
    }

    @Provides
    @Singleton
    fun provideLoginUseCases(loginRepository: LoginRepository): LoginUseCases {
        return LoginUseCases(
            registerUserUseCase = RegisterUserUseCase(loginRepository)
        )
    }

    @Provides
    @Singleton
    fun provideHomeUseCases(homeRepository: HomeRepository): HomeUseCases {
        return HomeUseCases(
            getSpeakingPostsUseCase = GetSpeakingPostsUseCase(homeRepository),
            getDailyTopicUseCase = GetDailyTopicUseCase(homeRepository)
        )
    }
}