package com.aniruddhambarkar.trackdsteps.health.weight


import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class WeightService @Inject constructor(var healthConnectClient: HealthConnectClient) : IWeightService{
    override suspend fun getWeight() : WeightDetailsState {
        val start = LocalDateTime.now().minusDays(30)
        val end = LocalDateTime.now()

//        readAllWeights()

        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                WeightRecord::class,
                timeRangeFilter = TimeRangeFilter.between(start, end),
                ascendingOrder = false,
                pageSize = 10
        )
        )

        val weights = response.records.sortedBy { it.time }

        var  weight  =  WeightDetail(0.0)
        val records = response.records.sortedByDescending { it.time }
        if(records.isEmpty()) {
            val state = WeightDetailsState.WeightDetailsError("No records found")
            weight = WeightDetail(0.0)
            return state
        }
        val record = records.get(0)
        weight = WeightDetail(record.weight.inKilograms)
        if(records.size>2){
            weight.lastWeight =records.get(1).weight.inKilograms
            weight.lastWeightRecord =WeightDetailsRecord( records.get(1).weight.inKilograms).also {
                it.recordTime = records.get(1).time.epochSecond
            }
        }
        return WeightDetailsState.WeightDetailsSuccess(weight)
    }

    suspend fun readAllWeights(): List<WeightRecord> {
        val records = mutableListOf<WeightRecord>()
        var pageToken: String? = null

        do {
            val response = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    recordType = WeightRecord::class,
                    timeRangeFilter = TimeRangeFilter.after(Instant.now().minus(30, ChronoUnit.DAYS)),
                    pageToken = pageToken,
                    dataOriginFilter = emptySet()
                )
            )
            records += response.records
            pageToken = response.pageToken
        } while (pageToken != null)

        return records.sortedBy { it.time }
    }
}