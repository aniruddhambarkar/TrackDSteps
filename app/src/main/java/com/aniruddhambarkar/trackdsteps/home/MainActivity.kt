package com.aniruddhambarkar.trackdsteps.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.WeightRecord
import com.aniruddhambarkar.trackdsteps.R
import com.aniruddhambarkar.trackdsteps.TrackDStepsApp
import com.aniruddhambarkar.trackdsteps.common.strings.DASHBOARD_MESSAGE
import com.aniruddhambarkar.trackdsteps.common.views.StepsFontStyle
import com.aniruddhambarkar.trackdsteps.common.views.StepsView
import com.aniruddhambarkar.trackdsteps.health.models.WorkoutSummary
import com.aniruddhambarkar.trackdsteps.health.weight.WeightDetail
import com.aniruddhambarkar.trackdsteps.health.weight.WeightDetailsRecord
import com.aniruddhambarkar.trackdsteps.health.weight.WeightDetailsState
import com.aniruddhambarkar.trackdsteps.ui.theme.CardSurface
import com.aniruddhambarkar.trackdsteps.ui.theme.CardSurfaceAlpha
import com.aniruddhambarkar.trackdsteps.ui.theme.HeaderGradient
import com.aniruddhambarkar.trackdsteps.ui.theme.MainBackground
import com.aniruddhambarkar.trackdsteps.ui.theme.PrimaryGradient
import com.aniruddhambarkar.trackdsteps.ui.theme.ProgressCardBackground
import com.aniruddhambarkar.trackdsteps.ui.theme.ProgressGradient
import com.aniruddhambarkar.trackdsteps.ui.theme.SoftBackgroundGradient
import com.aniruddhambarkar.trackdsteps.ui.theme.StepsProgressLinearGradient
import com.aniruddhambarkar.trackdsteps.ui.theme.TextPrimary
import com.aniruddhambarkar.trackdsteps.ui.theme.TrackDStepsTheme
import com.aniruddhambarkar.trackdsteps.ui.theme.UiColors
import com.aniruddhambarkar.trackdsteps.ui.theme.WHITE
import com.aniruddhambarkar.trackdsteps.ui.theme.stepsCardGradient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


class MainActivity : ComponentActivity() {

    val requestPermissions =
        registerForActivityResult(requestPermissionsActivityContract()) { granted ->
            if (granted.containsAll(PERMISSIONS)) {
                // Do something with the permissions like reading the data
                viewModel.loadStepsDetails()
            } else {
                // Lack of required permissions
                Log.v("MainActivity","Required permissions are missing")
            }
        }

    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(this) }

    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackDStepsTheme {
                Scaffold(modifier = Modifier
                    .background(PrimaryGradient)
                    .fillMaxSize().fillMaxHeight(),
                ) { innerPadding ->
                    Box(Modifier.background(MainBackground).fillMaxHeight()) {
                        HomeScreen(
                            name = "Hope you're feeling awesome and healthy too!",
                            modifier = Modifier.padding(innerPadding),
                            viewModel
                        )
                    }
                }
            }
        }

        (application as TrackDStepsApp).appComponent.inject(this)
        viewModel.loadGreetings()
        viewModel.greetings.observe(this) {
        }


        CoroutineScope(Dispatchers.IO).launch {
            if (!hasAllPermissions(PERMISSIONS)) {
                requestPermissions.launch(PERMISSIONS)
            }else{
                viewModel.loadStepsDetails()
            }
        }
        viewModel.requestPermissions(this@MainActivity)

    }

    suspend fun hasAllPermissions(permissions: Set<String>): Boolean {
        return healthConnectClient.permissionController.getGrantedPermissions().containsAll(permissions)
    }

    fun requestPermissionsActivityContract(): ActivityResultContract<Set<String>, Set<String>> {
        return PermissionController.createRequestPermissionResultContract()
    }

}

@Composable
fun HomeScreen(name: String, modifier: Modifier = Modifier, viewModel: HomeViewModel?) {
    var greet = viewModel?.greetings?.observeAsState()?.value?:""
    val stepsData = viewModel?.steps?.observeAsState()?.value
    val weightDetails = viewModel?.weightState?.collectAsState()
    Column {
        Column (modifier = Modifier.background(brush = HeaderGradient)) {
            Text(
                text = "Step&Pace",
                modifier = Modifier.padding(start = 16.0.dp, top = 50.0.dp).fillMaxWidth(),
                fontFamily = FontFamily.Monospace,
                fontSize = 18.0.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 20.0.sp,
                color = WHITE
            )
            Text(
                text = "Track Your Journey",
                modifier = Modifier.padding(start = 16.0.dp, bottom = 12.dp).fillMaxWidth(),
                fontFamily = FontFamily.Monospace,
                fontSize = 12.0.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 24.0.sp,
                color = WHITE
            )
        }
        Text(
                text = "$greet",
                modifier = Modifier.padding(start = 16.0.dp, top = 20.0.dp),
                fontFamily = FontFamily.Monospace,
                fontSize = 28.0.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 33.0.sp,
                color = WHITE
            )
        Text(
            text = DASHBOARD_MESSAGE,
            modifier = Modifier.padding(start = 16.0.dp, top = 4.0.dp),
            fontFamily = FontFamily.Monospace,
            fontSize = 14.0.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 12.0.sp,
            color = UiColors.Purple200
        )
        StepsData(stepsData)
        weightDetails?.value?.let {
            showWeightDetails(it)
        }

    }
}
@Composable
fun StepsData(stepsData: WorkoutSummary?) {
    Column {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ), // Set elevation
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
        )
        {
            Box(modifier = Modifier.background(brush = stepsCardGradient).padding(bottom = 16.dp)) {
                val duration = stepsData?.activeTime
                val stepsFontStyle = StepsFontStyle("Steps").also {
                    it.titleStyle = TextStyle(
                        fontSize = 38.0.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 32.0.sp,
                        color = TextPrimary
                    )
                    it.subtitleStyle = TextStyle(
                        fontSize = 16.0.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 16.0.sp,
                        color = TextPrimary
                    )
                }

                StepsView(
                    steps = stepsData?.stepsCount ?: 0,
                    progress = stepsData?.stepsPercentage ?: 0.0f,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
                    stepsTitleFontStyle = stepsFontStyle,
                )
                Text(  text = "Active Duration: $duration Minutes", modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(top = 50.dp, bottom = 10.dp),
                    fontSize = 14.0.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 16.0.sp,
                    color = TextPrimary)
                // Weight
            }
        }


            Row(modifier = Modifier.fillMaxWidth(1.0f).wrapContentHeight().padding(start = 16.dp, end = 16.dp) ) {
                val distance = String.format("%.2f", stepsData?.distance ?: 0.0)
                Card(
                    modifier = Modifier.padding(top = 8.dp, end = 8.dp, bottom = 8.dp).wrapContentHeight().weight(0.7f),
                    colors = CardDefaults.cardColors(
                        containerColor = CardSurfaceAlpha
                    )
                ) {
                    Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)) {
                        Text(
                            text = "Distance",
                            modifier = Modifier.padding(16.0.dp, top = 4.0.dp, bottom = 0.0.dp),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.0.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 16.0.sp,
                            color = WHITE
                        )
                        Text(
                            text = "$distance",
                            modifier = Modifier.padding(16.0.dp, top = 4.0.dp, bottom = 0.0.dp),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 20.0.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 26.0.sp,
                            color = WHITE
                        )
                        Text(
                            text = "KM",
                            modifier = Modifier.padding(16.0.dp, top = 4.0.dp, bottom = 0.0.dp),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.0.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 26.0.sp,
                            color = WHITE
                        )
                    }
                }
                // Column

                // Calories
                val calories = String.format("%.2f", stepsData?.calories ?: 0.0)
                Card(
                    modifier = Modifier.weight(0.7f).padding(top=8.dp, start = 8.dp, bottom = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CardSurfaceAlpha
                    )
                ){ Column {
                    Row {

                        Text(
                        text = "Calories",
                        modifier = Modifier.padding(16.0.dp, top = 4.0.dp, bottom = 0.0.dp),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.0.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 26.0.sp,
                        color = WHITE
                    )
                    Image( painter = painterResource(id = R.drawable.calories),
                           modifier = Modifier.size(10.dp).align(Alignment.CenterVertically),
                        contentDescription = "Calories")
                    }

                    Text(
                        text = "$calories",
                        modifier = Modifier.padding(16.0.dp, top = 4.0.dp, bottom = 0.0.dp),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 20.0.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 26.0.sp,
                        color = WHITE
                    )
                    Text(
                        text = "KCal",
                        modifier = Modifier.padding(16.0.dp, top = 4.0.dp, bottom = 0.0.dp),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.0.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 26.0.sp,
                        color = WHITE
                    )


                } }

            }
            }


    } // Column

@Composable
fun showWeightDetails(weightState : WeightDetailsState){

    Row(modifier = Modifier) {
        var weightString = ""
        var lastWeightDetails = ""
        var recordedTime = ""
        weightState.let {
            when(it){
                is WeightDetailsState.WeightDetailsError ->
                    weightString = it.errorString
                is WeightDetailsState.WeightDetailsSuccess ->
                {
                    weightString = "${String.format("%.2f", it.weight.weight?: 0.0)} Kg"
                    it.weight.lastWeightRecord?.apply {
                        val  pattern : String = "yyyy-MM-dd'T'HH:mm:ss'Z'"

                        // Create a SimpleDateFormat instance with the pattern
                        Log.v("WeightDetails","WeightDetails date $recordTime")
                         var simpleDateFormat :SimpleDateFormat =   SimpleDateFormat(pattern)
                        val format  = SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss")
//                        val date = simpleDateFormat.parse(recordTime)
                        lastWeightDetails = "${String.format("%.2f", weight)}"
                        recordTime?.run {
                            recordedTime = "Recorded at ${format.format(Date(this))}"
                        }

                    }
                }
                else -> {}
            }
        }
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ), // Set elevation
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        ){
            Box(modifier = Modifier.fillMaxWidth().
            background(brush = ProgressCardBackground)){
            ConstraintLayout(modifier = Modifier.fillMaxWidth().
                background(brush = ProgressCardBackground)
            ) {
                val (topText,lastWeight,recordTime) = createRefs()
                Text(
                    text = "Current Weight : $weightString",
                    modifier = Modifier.padding(start = 10.0.dp, top = 8.0.dp, bottom = 4.0.dp)
                        .constrainAs(topText){top.linkTo(parent.top)},
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.0.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 16.0.sp,
                    color = WHITE
                )
                Text(
                    text = "Last Weight : $lastWeightDetails",
                    modifier = Modifier.padding(start = 10.0.dp, bottom = 2.0.dp)
                        .constrainAs(lastWeight){
                            top.linkTo(topText.bottom, margin = 10.dp)
                        },
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.0.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 20.0.sp,
                    color = WHITE
                )
                Text(
                    text = "$recordedTime",
                    modifier = Modifier.padding(start = 10.0.dp, bottom = 16.0.dp, top = 2.dp)
                        .constrainAs(recordTime){
                            top.linkTo(lastWeight.bottom)
                        },
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.0.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 20.0.sp,
                    color = WHITE
                )

            }
            }
        }
    }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrackDStepsTheme {
        StepsData(WorkoutSummary(1000))
//        showWeightDetails(WeightDetailsState.WeightDetailsSuccess(WeightDetail(1000.67799).also {
//            it.lastWeightRecord  = WeightDetailsRecord(71.00).apply {
//                this.recordTime = 1771949904
//            }
//        }))
    }
}

val PERMISSIONS = setOf(
    HealthPermission.getReadPermission(StepsRecord::class),
    HealthPermission.getWritePermission(StepsRecord::class),
    HealthPermission.getReadPermission(ExerciseSessionRecord::class),
    HealthPermission.getWritePermission(ExerciseSessionRecord::class),
    HealthPermission.getReadPermission(DistanceRecord::class),
    HealthPermission.getReadPermission(WeightRecord::class),
)
