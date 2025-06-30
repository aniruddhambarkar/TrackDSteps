package com.aniruddhambarkar.trackdsteps.health

import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.aniruddhambarkar.trackdsteps.health.models.StepsSummary
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

// TODO
//👉 Calculate step duration
//
//👉 Distinguish between walking and running
//
//👉 Estimate calories burned

class HealthService @Inject constructor(var healthConnectClient: HealthConnectClient) : IHealthService {

    override suspend fun readSteps(): StepsSummary {
        val timeNow= LocalDateTime.now()
        val dayStart = LocalDateTime.now()
        val timeStartOfDay = dayStart.withHour(0).withMinute(0).withSecond(0)
        val count = readStepsFromHealthConnect(
                timeStartOfDay,
                timeNow
            )
        return count
    }

    override suspend fun readStepsFromHealthConnect(
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): StepsSummary {
        var stepsCount = 0L
        var duration= 0L
        val response =
            healthConnectClient.readRecords(
                ReadRecordsRequest(
                    StepsRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                )
            )

        for (stepRecord in response.records) {
            stepsCount += stepRecord.count
            duration += Duration.between(stepRecord.startTime,stepRecord.endTime).toMinutes()
            var minutes = Duration.between(stepRecord.startTime,stepRecord.endTime).toMinutes()
            Log.v("HealthService","stepRecord : Origin : ${stepRecord.metadata.dataOrigin.packageName}")
            Log.v("HealthService","Start Time ${LocalDateTime.ofInstant(stepRecord.startTime, ZoneId.systemDefault())}, End Time  ${LocalDateTime.ofInstant(stepRecord.endTime, ZoneId.systemDefault())} : Step Count ${stepRecord.count}")
        }

        Log.v("FitService", "Read energy for steps ${stepsCount}")
        var summary = StepsSummary(stepsCount).also {
            it.duration = duration
        }
        return summary
    }

    override suspend fun readExerciseSessions(){

        val timeNow= LocalDateTime.now()
        val dayStart = LocalDateTime.now()
        val timeStartOfDay = dayStart.withHour(0).withMinute(0).withSecond(0)

        val exerciseSessions = healthConnectClient.readRecords(
            ReadRecordsRequest(
                recordType = DistanceRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    timeStartOfDay, // Last 24 hours
                    timeNow
                )
            )
        ).records

        val steps = healthConnectClient.readRecords(
            ReadRecordsRequest(
                recordType = StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    timeStartOfDay, // Last 24 hours
                    timeNow
                )
            )
        ).records

        steps.forEach { stepRecord ->
            val activityType = exerciseSessions.find { session ->
                stepRecord.startTime >= session.startTime &&
                        stepRecord.endTime <= session.endTime
            }?.metadata?.device?.type

            var covered = exerciseSessions.find { session ->
                stepRecord.startTime >= session.startTime &&
                        stepRecord.endTime <= session.endTime
            }?.distance

            println("activityType $activityType covered $covered")
            val activity = when (activityType) {

                ExerciseSessionRecord.EXERCISE_TYPE_WALKING -> "Walking"
                ExerciseSessionRecord.EXERCISE_TYPE_RUNNING -> "Running"
                else -> "Unknown"
            }

            println("Steps Distance: ${stepRecord.count}, Type: $activity")
        }
        var distance = 0.0;
        exerciseSessions?.forEach {
            distance += it.distance.inMeters
        }

        println("Total Distance $distance")
    }
}



interface IHealthService {
    suspend fun readSteps() : StepsSummary
    suspend fun readStepsFromHealthConnect(
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): StepsSummary
    suspend fun readExerciseSessions()
}