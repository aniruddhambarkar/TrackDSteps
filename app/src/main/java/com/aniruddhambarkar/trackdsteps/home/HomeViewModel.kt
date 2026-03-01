package com.aniruddhambarkar.trackdsteps.home

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aniruddhambarkar.trackdsteps.health.IHealthDataAPIService
import com.aniruddhambarkar.trackdsteps.health.IHealthService
import com.aniruddhambarkar.trackdsteps.health.models.WorkoutSummary
import com.aniruddhambarkar.trackdsteps.health.weight.IWeightService
import com.aniruddhambarkar.trackdsteps.health.weight.WeightDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Calendar
import javax.inject.Inject


class HomeViewModel @Inject constructor(var healthService: IHealthService,
                                        private var samsungHealthService : IHealthDataAPIService,
                                        private var weightService : IWeightService
    ) :  IHomeViewModel, ViewModel() {


    private val greetingState = MutableLiveData<String>()
    var greetings : LiveData<String> = greetingState

    private val stepsState = MutableLiveData<WorkoutSummary>()
    var steps: LiveData<WorkoutSummary> = stepsState

    private  val distanceState = MutableLiveData<Double>()
    var distance: LiveData<Double> = distanceState

    private val _weightDetailsFlow = MutableStateFlow<WeightDetailsState>(WeightDetailsState.WeightDetailsLoading(""))
    val weightState =_weightDetailsFlow.asStateFlow()


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

            val dayStart = LocalDateTime.now()
            val timeStartOfDay = dayStart.withHour(0).withMinute(0).withSecond(0)
            val timeNow = LocalDateTime.now().plusDays(1)
            val summary = samsungHealthService.readWorkoutSummary(timeStartOfDay,timeNow)
            stepsState.postValue(summary)
        }

        viewModelScope.launch {
            val count = healthService.readExerciseSessions()
            Log.v("MainActivity","${count}")
        }

        viewModelScope.launch {
            val distance = healthService.readExerciseDistanceSessions()
            Log.v("MainActivity distance","${distance}")
            distanceState.postValue(distance)
        }
        loadDetails()

    }

    override fun loadDetails() {
        viewModelScope.launch {
            samsungHealthService.readExerciseSessions()
        }
        loadWeightDetails()
    }

    override fun requestPermissions(activity: Activity) {
        viewModelScope.launch {
            samsungHealthService.requestPermissions(activity)
        }
        loadStepDetails()
    }

    override fun loadStepDetails() {
        viewModelScope.launch {
            samsungHealthService.readExerciseSessions()
        }
    }

    override fun loadWeightDetails() {
        viewModelScope.launch {
            val weightDetails = weightService.getWeight()
            _weightDetailsFlow.emit(weightDetails)
        }
    }

}

interface IHomeViewModel {
    fun loadGreetings()
    fun loadStepsDetails()
    fun loadDetails()
    fun requestPermissions(activity: Activity)
    fun loadStepDetails()
    fun loadWeightDetails()
}