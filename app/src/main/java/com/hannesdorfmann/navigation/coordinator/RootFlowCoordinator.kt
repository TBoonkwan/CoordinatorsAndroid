package com.hannesdorfmann.navigation.coordinator

import com.hannesdorfmann.navigation.domain.user.AuthenticatedUser
import com.hannesdorfmann.navigation.domain.user.NotAuthenticated
import com.hannesdorfmann.navigation.domain.user.Usermanager
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class RootFlowCoordinator(
        private val usermanager: Usermanager,
        private val navigator: Navigator
) {


    lateinit var loginFlowCoordinator: LoginFlowCoordinator
    lateinit var onboardingCoordinator: OnboardingFlowCoordinator
    lateinit var newsFlowCoordinator: NewsFlowCoordinator

    init {
        usermanager.currentUser.delay(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            when (it) {
                is NotAuthenticated -> loginFlowCoordinator.start()
                is AuthenticatedUser -> newsFlowCoordinator.start()
            }
        }
    }

    fun onboardingCompleted() {
        newsFlowCoordinator.start()
    }
}