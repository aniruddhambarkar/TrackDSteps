package com.aniruddhambarkar.trackdsteps.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.aniruddhambarkar.trackdsteps.R
import com.aniruddhambarkar.trackdsteps.TrackDStepsApp
import com.aniruddhambarkar.trackdsteps.di.ApplicationComponent
import com.aniruddhambarkar.trackdsteps.home.MainActivity
import com.aniruddhambarkar.trackdsteps.login.di.LoginComponent
import com.aniruddhambarkar.trackdsteps.login.presenter.ILoginViewModel
import com.aniruddhambarkar.trackdsteps.ui.theme.PinkLight
import com.aniruddhambarkar.trackdsteps.ui.theme.PinkMid
import com.aniruddhambarkar.trackdsteps.ui.theme.TextPrimary
import com.aniruddhambarkar.trackdsteps.ui.theme.TextSecondary
import com.aniruddhambarkar.trackdsteps.ui.theme.TrackDStepsTheme
import javax.inject.Inject

class LoginActivity : ComponentActivity() {

    @Inject
    lateinit var presenter : ILoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        (applicationContext as TrackDStepsApp).appComponent.loginComponentFactory.create().inject(this)

        setContent {
            TrackDStepsTheme {
                LoginScreen(
                )
            }
        }
    }

    @Composable
    fun LoginScreen(
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(PinkLight, PinkMid)
                    )
                )
                .systemBarsPadding() // 🔑 IMPORTANT FIX
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(56.dp))

                // 🔹 Icon (no background)
                Image(
                    painter = painterResource(id = R.drawable.main_img),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.60f),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Track your fitness and stay healthy.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Log in to track steps, weight,\nworkouts and more.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextSecondary
                    )
                )

                // 🔹 Controlled spacing instead of weight(1f)
                Spacer(modifier = Modifier.height(12.dp))

                GoogleSignInButton()
            }
        }
    }

    @Composable
    fun GoogleSignInButton(

    ) {
//        val viewM = viewModel(this@LoginActivity)
        Button(
            onClick = {
                // Your action goes here (e.g., log a message, update a state)
                println("Button clicked!")
                googleSignInClicked()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(6.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google Logo",
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Sign in with Google",
                color = Color.DarkGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }

    fun googleSignInClicked(){
        presenter.login()
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }
}