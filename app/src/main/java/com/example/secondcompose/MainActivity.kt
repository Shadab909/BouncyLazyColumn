package com.example.secondcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.secondcompose.ui.theme.SecondComposeTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecondComposeTheme {
                Content()
            }
        }
    }
}

@Composable
fun Content() {
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnBoarding){
        OnboardingScreen { shouldShowOnBoarding = false }
    }else{
        GreetingsColumn()
    }

}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Surface {
        Column(modifier = Modifier.fillMaxSize(),
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Welcome to Greeter")
                Button(
                    onClick =  onContinueClicked ,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(text = "Continue")
                }
        }
    }
}

@Composable
fun GreetingsColumn(names : List<String> = List(100){"$it"}) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp) ){
        items(names){name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    var expanded by rememberSaveable{ mutableStateOf(false)}

    Surface(color = MaterialTheme.colors.primary,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )){
                Text(text = "Hello " )
                Text(text = name , style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
                if (expanded) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                    )
                }
            }
            IconButton(
                onClick = { expanded = !expanded }
            ) {
                if (expanded){
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_up_24),
                        contentDescription = "Show less" )
                }else{
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),
                        contentDescription = "Show more" )
                }

            }
        }
    }
}

@Preview(showBackground = true , heightDp = 400)
@Composable
fun DefaultPreview() {
    SecondComposeTheme {
       GreetingsColumn()
    }
}