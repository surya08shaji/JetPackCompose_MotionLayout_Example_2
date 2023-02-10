package com.example.jetpackwithmotionlayout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.*


@Preview()
@Composable
fun FoodDetail() {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene_s)
            .readBytes()
            .decodeToString()
    }

    val foodDummyData = listOf(
        FoodModel(
            "Salmon Salad",
            "One of the easiest equations for a wholesome meal is vegetables + salmon. Both are packed with nutrients like protein, healthy fats, and fiber " +
                    "that just make you feel good with every single bite, and this simple recipe is proof",
            "80cal",
            "4.5 rating",
            R.drawable.img_salmon_salad
        ),
        FoodModel(
            "BBQ Chicken Breast",
            "BBQ chicken, whether cooked on the grill or baked in the oven, is one of those dinners nobody at the table will complain about." +
                    "Unless it’s dry and overcooked. But that is NOT this recipe. " +
                    "It’s a basic anyone can easily master.",
            "90cal",
            "4.5 rating",
            R.drawable.img_chicken
        ),
        FoodModel(
            "Steamed Chicken and Rice",
            "Steaming is an ideal method for cooking boneless chicken breasts and small whole birds such as Cornish hens. It retains the flavor, " +
                    "tenderness and moisture through the use of steam. It is a healthy method of cooking because no additional fat is used.",
            "80cal",
            "4.5 rating",
            R.drawable.steamed_chicken_and_rice
        ),
        FoodModel(
            "Caesar Salad",
            "A green salad of romaine lettuce and croutons dressed with lemon juice (or lime juice), olive oil, egg," +
                    " Worcestershire sauce, anchovies, garlic, Dijon mustard, Parmesan cheese, and black pepper.",
            "70cal",
            "4.5 rating",
            R.drawable.caesar_salad
        ),
        FoodModel(
            "Wholesome Meal",
            "One of the easiest equations for a wholesome meal is vegetables + salmon. Both are packed with nutrients like protein, healthy fats, " +
                    "and fiber that just make you feel good with every single bite, and this simple recipe is proof",
            "80cal",
            "4.5 rating",
            R.drawable.wholesome_meal
        )
    )


    var animateButton by remember { mutableStateOf(false) }
    val buttonAnimationProgress by animateFloatAsState(

        targetValue = if (animateButton) 1f else 0f,

        animationSpec = tween(1000)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .layoutId("lColumn")
    ) {
        items(foodDummyData) {
            ModelItem(foodDummyData = it)
            {

            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModelItem(
    foodDummyData: FoodModel, onClick: () -> Unit
) {
    var animateButton by remember { mutableStateOf(false) }
    val buttonAnimationProgress by animateFloatAsState(

        targetValue = if (animateButton) 1f else 0f,

        animationSpec = tween(1000)
    )

    MotionLayout(
        ConstraintSet(
            """ {
                row1: {
                  width: 'spread',
                  height: 'wrap',
                  start: ['parent', 'start'],
                  top: ['parent', 'top'],
                        bottom: ['parent','bottom']
                }
            } """
        ),
        ConstraintSet(
            """ {
                row1: {
                  width: 'spread',
                  height: 300,
                  start: ['parent', 'start'],
                  top: ['parent', 'top'],
                  end: ['parent', 'end'],
                  bottom: ['parent','bottom']
                }
            } """
        ),

        progress = buttonAnimationProgress,
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(modifier = Modifier

            .layoutId("row1")
            .height(150.dp)
            .background(Color.Gray)
            .clickable { animateButton = !animateButton }) {
            MotionLayout(
                ConstraintSet(
                    """ {
                foodImage: {
                  width: 60,
                  height: 'wrap',
                  start: ['parent', 'start', 16],
                  top: ['parent', 'top'],
                        bottom: ['cardView','bottom']
                },
                  cardView: {
                  width: "spread",
                  height: 'wrap',

                  start: ['foodImage', 'end', 16],
                  end: ['parent', 'end', 16],
                 
                }
            } """
                ),
                ConstraintSet(
                    """ {
                foodImage: {
                  width: 150,
                  height: 'wrap',
                  start: ['parent', 'start', 30],
                  top: ['parent', 'top'],
                  end: ['parent', 'end', 30]

                },
              cardView: {
                  width: 'spread',
                  height: 'wrap',
                  start: ['parent', 'start', 30],
                  end: ['parent', 'end', 30],
 top: ['foodImage','bottom']
                }
            } """
                ),

                progress = buttonAnimationProgress,
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Image(
                    painter = painterResource(id = foodDummyData.imgId),
                    contentDescription = "",
                    alignment = Center,
                    modifier = Modifier
                        .layoutId("foodImage")
                        .height(150.dp)
                        .width(150.dp)
                        .padding(4.dp)

                )

                Card(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .layoutId("cardView")
                        .padding(8.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column {
                        Text(
                            text = foodDummyData.title,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier.align(CenterHorizontally)
                        )
                        Text(
                            text = foodDummyData.description,
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            modifier = Modifier
                                .height(90.dp)
                                .width(200.dp)
                                .align(CenterHorizontally)
                        )

                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.calories),
                                contentDescription = "",
                                modifier = Modifier.size(15.dp)
                            )

                            Text(
                                text = foodDummyData.calories,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily.Default,
                                color = Color.Gray,
                                fontSize = 10.sp,
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(15.dp)
                            )

                            Image(
                                painter = painterResource(id = R.drawable.ic_star),
                                contentDescription = "",
                                modifier = Modifier.size(15.dp)
                            )

                            Text(
                                text = foodDummyData.rate,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily.Default,
                                color = Color.Gray,
                                fontSize = 10.sp,
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(15.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodDetail()
}
