package com.aniruddhambarkar.trackdsteps.health.weight

data class WeightDetail(val weight: Double){
    var lastWeight : Double? = null
    var lastWeightRecord : WeightDetailsRecord? = null
}

sealed class WeightDetailsState {
    class WeightDetailsSuccess(val weight: WeightDetail) : WeightDetailsState()
    data class WeightDetailsError(val errorString: String): WeightDetailsState()
    data class WeightDetailsLoading(val message: String): WeightDetailsState()
}

data class WeightDetailsRecord(val weight : Double){
    var recordTime : Long? = null
}
