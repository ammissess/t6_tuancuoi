package com.example.lapshopcompose

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IntroScreen(onClick = {
                startActivity(Intent(this,MainActivity::class.java))
            })
        }

    }
}

@Composable
@Preview
fun IntroScreen(onClick:()->Unit={}){
    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.intro_logo),
            contentDescription = null,
            modifier = androidx.compose.ui.Modifier
                .padding(top = 48.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Fit

        )

        Spacer(modifier = androidx.compose.ui.Modifier.height(32.dp))

        Text(text = stringResource(id =R.string.intro_title ),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = androidx.compose.ui.Modifier.height(32.dp))

        Text(text = stringResource(id = R.string.intro_sub_title),

            modifier = androidx.compose.ui.Modifier.padding(top = 16.dp),
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp

        )

        Button(onClick = {onClick()},
            modifier = androidx.compose.ui.Modifier
                .padding(
                    horizontal = 32.dp, vertical = 16.dp
                )
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor =
            colorResource(id = R.color.purple)
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.letgetstrated),
                color = Color.White,
                fontSize = 18.sp,
            )
        }
        Text(
            modifier = androidx.compose.ui.Modifier.padding(top = 16.dp),
            text = stringResource(id = R.string.signin),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )

    }
}