package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    var currentStep by remember { mutableIntStateOf(1) }

    var squeezeCount by remember { mutableIntStateOf(0) }
    LemonadeTheme {
        Scaffold(
            topBar = ({
                CenterAlignedTopAppBar(
                    title = { Text(text = "Lemonade", fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            })
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentStep) {
                    1 -> {
                        LemonadeButton(
                            textLabelResourceId = R.string.lemon_tap,
                            drawableResourceId = R.drawable.lemon_tree,
                            contentDescriptionResourceId = R.string.lemon_tree_content_description,
                            onImageClick = {
                                currentStep = 2
                                squeezeCount = (2..4).random()
                            })
                    }
                    2 -> {
                        LemonadeButton(
                            textLabelResourceId = R.string.lemon_tap,
                            drawableResourceId = R.drawable.lemon_squeeze,
                            contentDescriptionResourceId = R.string.lemon_content_description,
                            onImageClick = {
                                squeezeCount --
                                if(squeezeCount == 0)
                                    currentStep = 3
                            })
                    }
                    3 -> {
                        LemonadeButton(
                            textLabelResourceId = R.string.lemon_drink,
                            drawableResourceId = R.drawable.lemon_drink,
                            contentDescriptionResourceId = R.string.lemonade_content_description,
                            onImageClick = { currentStep = 4})
                    }
                    4 -> {
                        LemonadeButton(
                            textLabelResourceId = R.string.lemon_empty,
                            drawableResourceId = R.drawable.lemon_restart,
                            contentDescriptionResourceId = R.string.empty_glass_content_description,
                            onImageClick = {
                                currentStep = 1
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LemonadeButton(
    textLabelResourceId: Int,
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = onImageClick,
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius))
            ) {
                Image(
                    painter = painterResource(drawableResourceId),
                    contentDescription = stringResource(
                        textLabelResourceId
                    ),
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.button_image_width))
                        .height(
                            dimensionResource(R.dimen.button_image_height)
                        )
                        .padding(dimensionResource(R.dimen.button_interior_padding))
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(contentDescriptionResourceId),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
