package com.aniruddhambarkar.trackdsteps.health

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.health.platform.client.request.ReadDataRequest
import com.aniruddhambarkar.trackdsteps.common.BaseClass
import com.aniruddhambarkar.trackdsteps.health.models.TimeUnitString
import com.aniruddhambarkar.trackdsteps.health.models.WorkoutSummary
import com.samsung.android.sdk.health.data.HealthDataService
import com.samsung.android.sdk.health.data.permission.AccessType
import com.samsung.android.sdk.health.data.permission.Permission
import com.samsung.android.sdk.health.data.request.DataType
import com.samsung.android.sdk.health.data.request.DataTypes
import com.samsung.android.sdk.health.data.request.LocalTimeFilter
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class SamsungHealthService @Inject constructor(private val context: Context)  : IHealthDataAPIService,
    BaseClass {
    val  APP_TAG  = "SamsungHealthService"
    override suspend fun readSteps(): WorkoutSummary {
        TODO("Not yet implemented")
    }

    override suspend fun readWorkoutSummary(
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): WorkoutSummary {
        var steps = 0L
        var datastore = HealthDataService.getStore(context)
        val startTime = LocalDate.now().atStartOfDay()
        val endTime = LocalDateTime.now()
        val localTimeFilter = LocalTimeFilter.of(startTime, endTime)
        val stepsRequest = DataType.StepsType.TOTAL.requestBuilder
            .setLocalTimeFilter(localTimeFilter)
            .build()



        var response = datastore.aggregateData(stepsRequest)
        if(!response.dataList.isEmpty())
            response.dataList.forEach {
                Log.v(APP_TAG,"Samsung Step"+it.value)
                steps += (it.value ?: 0L)
            }
        Log.v(APP_TAG,"Response processing done")


        var request = DataType.ActivitySummaryType.TOTAL_DISTANCE.requestBuilder
            .setLocalTimeFilter(localTimeFilter)
            .build()
        var distance  = 0.0f
        var distanceResponse = datastore.aggregateData(request)

        if(!distanceResponse.dataList.isEmpty())
            distanceResponse.dataList.forEach {
                Log.v(APP_TAG,"Samsung distance"+it.value)
                distance = it.value?:0.0f
            }

        request = DataType.ActivitySummaryType.TOTAL_ACTIVE_CALORIES_BURNED.requestBuilder
            .setLocalTimeFilter(localTimeFilter)
            .build()
        var calories  = 0.0f
        var caloriesResponse = datastore.aggregateData(request)
        if(!caloriesResponse.dataList.isEmpty())
            caloriesResponse.dataList.forEach {
                Log.v(APP_TAG,"Samsung calories"+it.value)
                calories = it.value?:0.0f
            }

        var activeDuration : Long = 0
        var timeRequest = DataType.ActivitySummaryType.TOTAL_ACTIVE_TIME.requestBuilder
            .setLocalTimeFilter(localTimeFilter)
            .build()
        var activeDurationResponse = datastore.aggregateData(timeRequest)

        var stepsSummary = WorkoutSummary(steps).also {
            it.distance = distance.toDouble()/1000 // Convert from meter to KM
            it.calories = calories.toDouble()
        }.apply {
            this.stepsPercentage = (steps.toFloat()/10000f)
        }

        if(!activeDurationResponse.dataList.isEmpty())
            activeDurationResponse.dataList.forEach {
                Log.v(APP_TAG,"Samsung duration"+it.value)
                it.value?.also {
                    activeDuration = it.toMinutes()
                    Log.v(TAG,"Active duration in minutes : $activeDuration")
                    stepsSummary.apply {
                        timeUnit = TimeUnitString.MINUTE.timeValue
                        activeTime = activeDuration
                    }

                }
            }



        return stepsSummary
    }

    override suspend fun readExerciseSessions() {
        var steps = 0L


        var datastore = HealthDataService.getStore(context)

        val startTime = LocalDate.now().atStartOfDay()
        val endTime = LocalDateTime.now()

        val localTimeFilter = LocalTimeFilter.of(startTime, endTime)
        val stepsRequest = DataType.StepsType.TOTAL.requestBuilder
            .setLocalTimeFilter(localTimeFilter)
            .build()



        var response = datastore.aggregateData(stepsRequest)
        if(!response.dataList.isEmpty())
            response.dataList.forEach {
                Log.v(APP_TAG,"Samsung Step"+it.value)
                steps += (it.value ?: 0L)

            }
        Log.v(APP_TAG,"Response processing done")
    }

    override suspend fun readExerciseDistanceSessions(): Double {
        TODO("Not yet implemented")
    }

    override suspend fun requestPermissions( activity: Activity): Boolean {
        var healthDataStore = HealthDataService.getStore(context)
        val permissionSet = setOf(
            Permission.of(DataTypes.STEPS, AccessType.READ),
            Permission.of(DataTypes.NUTRITION, AccessType.READ),
            Permission.of(DataTypes.USER_PROFILE, AccessType.READ),
            Permission.of(DataTypes.ACTIVITY_SUMMARY, AccessType.READ)
        )
        val grantedPermissions = healthDataStore.getGrantedPermissions(permissionSet)

        if (grantedPermissions.containsAll(permissionSet)) {
            Log.i(APP_TAG, "All required permissions granted")
        } else {
           val permissions =  healthDataStore.requestPermissions(permissionSet, activity)
            if (permissions.containsAll(grantedPermissions))
                return true
            else
                return false
        }
        return false
    }

}