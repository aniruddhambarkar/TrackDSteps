package com.aniruddhambarkar.trackdsteps

import android.health.connect.HealthPermissions.READ_DISTANCE
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.StepsRecord
import com.aniruddhambarkar.trackdsteps.health.models.StepsSummary
import com.aniruddhambarkar.trackdsteps.home.HomeViewModel
import com.aniruddhambarkar.trackdsteps.ui.theme.BackgroundMain
import com.aniruddhambarkar.trackdsteps.ui.theme.Pink40
import com.aniruddhambarkar.trackdsteps.ui.theme.TrackDStepsTheme
import com.aniruddhambarkar.trackdsteps.ui.theme.WHITE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    val requestPermissions =
        registerForActivityResult(requestPermissionsActivityContract()) { granted ->
            if (granted.containsAll(PERMISSIONS)) {
                // Do something with the permissions like reading the data
                viewModel.loadStepsDetails()
            } else {
                // Lack of required permissions

            }
        }

    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(this) }
    private val presenter  : HomeViewModel by  viewModels()

    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackDStepsTheme {
                Scaffold(modifier = Modifier.background(BackgroundMain).fillMaxSize(),
                    containerColor = Pink40
                ) { innerPadding ->

                    Greeting(
                        name = "Hope you're feeling awesome and healthy too!",
                        modifier = Modifier.padding(innerPadding),
                        viewModel
                    )
                }
            }
        }

        (application as TrackDStepsApp).appComponent.inject(this)

//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
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
    }

    suspend fun hasAllPermissions(permissions: Set<String>): Boolean {
        return healthConnectClient.permissionController.getGrantedPermissions().containsAll(permissions)
    }

    fun requestPermissionsActivityContract(): ActivityResultContract<Set<String>, Set<String>> {
        return PermissionController.createRequestPermissionResultContract()
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, viewModel: HomeViewModel?) {
    var greet = viewModel?.greetings?.observeAsState()?.value?:""
    val stepsData = viewModel?.steps?.observeAsState()?.value
    Column {
             Text(
                text = "$greet \n$name",
                modifier = Modifier.padding(start = 16.0.dp, top = 60.0.dp),
                fontFamily = FontFamily.Monospace,
                fontSize = 24.0.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 33.0.sp,
                color = WHITE
            )
        StepsData(stepsData)

    }
}


@Composable
fun StepsData(stepsData: StepsSummary?) {

    Card(
        elevation = CardDefaults.cardElevation( // Set elevation using CardDefaults
            defaultElevation = 8.dp
        ), // Set elevation
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundMain
        ),
    )
    {

            Text(
                text = "Steps: ${stepsData?.stepsCount}",
                modifier = Modifier.padding(16.0.dp, top = 16.0.dp),
                fontFamily = FontFamily.Monospace,
                fontSize = 20.0.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 33.0.sp,
                color = WHITE
            )
            Text(
            text = "Duration : ${stepsData?.duration?.div(60)?.div(60)} Minutes",
            modifier = Modifier.padding(16.0.dp, top = 10.0.dp),
            fontFamily = FontFamily.Monospace,
            fontSize = 16.0.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 33.0.sp,
            color = WHITE
        )
        }

    }

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrackDStepsTheme {
    }
}

val PERMISSIONS = setOf(
    HealthPermission.getReadPermission(StepsRecord::class),
    HealthPermission.getWritePermission(StepsRecord::class),
    HealthPermission.getReadPermission(ExerciseSessionRecord::class),
    HealthPermission.getWritePermission(ExerciseSessionRecord::class),
    HealthPermission.getReadPermission((DistanceRecord::class))
)