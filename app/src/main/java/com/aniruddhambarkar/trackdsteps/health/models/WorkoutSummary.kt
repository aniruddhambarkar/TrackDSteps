package com.aniruddhambarkar.trackdsteps.health.models

data class WorkoutSummary (val stepsCount : Long) {
    var duration= 0L
    var distance = 0.0
    var calories = 0.0
    var activeTime = 0L
    var timeUnit =""
    var stepsPercentage = 0.0f
}


enum class TimeUnitString(val timeValue: String) {
    HOUR("hour"),
    MINUTE("minute"),
}