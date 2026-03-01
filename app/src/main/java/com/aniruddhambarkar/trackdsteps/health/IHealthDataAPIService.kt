package com.aniruddhambarkar.trackdsteps.health

import android.app.Activity
import com.aniruddhambarkar.trackdsteps.health.models.WorkoutSummary
import java.time.LocalDateTime

interface IHealthDataAPIService {
    suspend fun readSteps() : WorkoutSummary
    suspend fun readWorkoutSummary(
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): WorkoutSummary
    suspend fun readExerciseSessions()
    suspend fun readExerciseDistanceSessions(): Double
    suspend fun requestPermissions( activity: Activity) : Boolean
}