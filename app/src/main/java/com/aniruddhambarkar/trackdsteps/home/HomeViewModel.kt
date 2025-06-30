package com.aniruddhambarkar.trackdsteps.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aniruddhambarkar.trackdsteps.health.HealthService
import com.aniruddhambarkar.trackdsteps.health.IHealthService
import com.aniruddhambarkar.trackdsteps.health.models.StepsSummary
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


class HomeViewModel @Inject constructor(var healthService: IHealthService) :  IHomeViewModel, ViewModel() {


    private val greetingState = MutableLiveData<String>()
    var greetings : LiveData<String> = greetingState

    private val stepsState = MutableLiveData<StepsSummary>()
    var steps: LiveData<StepsSummary> = stepsState

//    LiveEvent<String>()


    override fun loadGreetings() {
        // Load greeting
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        val afternoonSplit = 12
        val eveningSplit = 17
        var greeting =  when {
            currentHour in afternoonSplit..eveningSplit -> {
               "Good Afternoon!"
            }
            currentHour >= eveningSplit -> {
                "Good Evening!"
            }
            else -> {
                "Good Morning!"
            }
        }

        greetingState.postValue(greeting)
    }

    override fun loadStepsDetails() {
        viewModelScope.launch {
            val count = healthService.readSteps()
            stepsState.postValue(count)
        }

        viewModelScope.launch {
            val count = healthService.readExerciseSessions()
        }

    }

}

interface IHomeViewModel {
    fun loadGreetings()
    fun loadStepsDetails()
}