package com.aniruddhambarkar.trackdsteps.health.weight

interface IWeightService {
    suspend fun getWeight() : WeightDetailsState
}