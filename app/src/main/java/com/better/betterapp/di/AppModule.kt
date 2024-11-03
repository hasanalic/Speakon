package com.better.betterapp.di

import com.better.betterapp.feature_home.data.repository.HomeRepositoryImp
import com.better.betterapp.feature_home.data.repository.LeaderboardRepositoryImp
import com.better.betterapp.feature_home.data.repository.ProfileRepositoryImp
import com.better.betterapp.feature_home.domain.repository.HomeRepository
import com.better.betterapp.feature_home.domain.repository.LeaderboardRepository
import com.better.betterapp.feature_home.domain.repository.ProfileRepository
import com.better.betterapp.feature_home.domain.use_cases.GetDailyTopicUseCase
import com.better.betterapp.feature_home.domain.use_cases.GetProfileUseCase
import com.better.betterapp.feature_home.domain.use_cases.GetSpeakingPostsUseCase
import com.better.betterapp.feature_home.domain.use_cases.GetUserPostsUseCase
import com.better.betterapp.feature_home.domain.use_cases.GetUsersUseCase
import com.better.betterapp.feature_login.data.repository.LoginRepositoryImp
import com.better.betterapp.feature_login.domain.repository.LoginRepository
import com.better.betterapp.feature_home.domain.use_cases.HomeUseCases
import com.better.betterapp.feature_login.domain.use_cases.LoginUseCases
import com.better.betterapp.feature_login.domain.use_cases.RegisterUserUseCase
import com.better.betterapp.feature_speaking.data.repository.SpeakingRepositoryImp
import com.better.betterapp.feature_speaking.domain.repository.SpeakingRepository
import com.better.betterapp.feature_speaking.domain.use_cases.CorrectTextUseCase
import com.better.betterapp.feature_speaking.domain.use_cases.PublishSpeakingUseCase
import com.better.betterapp.feature_speaking.domain.use_cases.SpeakingUseCases
import com.better.betterapp.feature_speaking_detail.data.repository.SpeakingDetailRepositoryImp
import com.better.betterapp.feature_speaking_detail.domain.repository.SpeakingDetailRepository
import com.better.betterapp.feature_speaking_detail.domain.use_cases.GetSpeakingDetailUseCase
import com.better.betterapp.feature_speaking_detail.domain.use_cases.SpeakingDetailUseCases
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
    fun provideSpeakingDetailRepository(db: FirebaseFirestore): SpeakingDetailRepository {
        return SpeakingDetailRepositoryImp(db)
    }

    @Provides
    @Singleton
    fun provideLeaderboardRepository(db: FirebaseFirestore): LeaderboardRepository {
        return LeaderboardRepositoryImp(db)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(db: FirebaseFirestore): ProfileRepository {
        return ProfileRepositoryImp(db)
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
    fun provideHomeUseCases(homeRepository: HomeRepository, leaderboardRepository: LeaderboardRepository, profileRepository: ProfileRepository): HomeUseCases {
        return HomeUseCases(
            getSpeakingPostsUseCase = GetSpeakingPostsUseCase(homeRepository),
            getDailyTopicUseCase = GetDailyTopicUseCase(homeRepository),
            getUsersUseCase = GetUsersUseCase(leaderboardRepository),
            getProfileUseCase = GetProfileUseCase(profileRepository),
            getUsersPostsUseCase = GetUserPostsUseCase(profileRepository)
        )
    }

    @Provides
    @Singleton
    fun provideSpeakingDetailUseCases(speakingDetailRepository: SpeakingDetailRepository): SpeakingDetailUseCases {
        return SpeakingDetailUseCases(
            getSpeakingDetailUseCase = GetSpeakingDetailUseCase(speakingDetailRepository)
        )
    }

    @Provides
    @Singleton
    fun provideSpeakingUseCases(speakingRepository: SpeakingRepository): SpeakingUseCases {
        return SpeakingUseCases(
            correctTextUseCase = CorrectTextUseCase(speakingRepository),
            publishSpeakingUseCase = PublishSpeakingUseCase(speakingRepository)
        )
    }
}