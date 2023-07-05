package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import com.example.cupcake.CupcakeScreen
import com.example.cupcake.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private lateinit var navController: TestNavHostController

@get:Rule
val composeTestRule = createAndroidComposeRule<ComponentActivity>()

@Before
fun setupCupcakeNavHost() {
    composeTestRule.setContent {
        navController = TestNavHostController(LocalContext.current).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }
        CupcakeApp(navController = navController)

    }
}

@Test
fun cupcakeNavHost_verifyStartDestination() {
    navController.assertCurrentRouteName(CupcakeScreen.Start.name)
}

@Test
fun cupcake_verifyBackNavigationNotShownOnStartOrderScreen() {
    val backText = composeTestRule.activity.getString(R.string.back_button)
    composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
}

@Test
fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorScreen() {
    composeTestRule.onNodeWithStringId(R.string.one_cupcake)
        .performClick()
    navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
}

@Test
fun cupcakeNavHost_clickBackOnFlavorScreen_navigateToStartOrderScreen(){
    navigateToFlavorScreen()
    performNavigateUp()
    navController.assertCurrentRouteName(CupcakeScreen.Start.name)
}


@Test
fun cupcakeNavHost_clickBackOnPickupScreen_navigateToFlavorScreen(){
    navigateToPickupScreen()
    performNavigateUp()
    navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
}

@Test
fun cupcakeNavHost_clickBackOnSummaryScreen_navigateToPickupScreen(){
    navigateToSummaryScreen()
    performNavigateUp()
    navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
}

@Test
fun cupcakeNavHost_clickCancelOnPickupScreen_navigateToStartOrderScreen(){
    navigateToPickupScreen()
    composeTestRule.onNodeWithStringId(R.string.cancel)
    navController.assertCurrentRouteName(CupcakeScreen.Start.name)
}


@Test
fun cupcakeNavHost_clickCancelOnSummaryScreen_navigateToStartOrderScreen(){
    navigateToSummaryScreen()
    composeTestRule.onNodeWithStringId(R.string.cancel)
    navController.assertCurrentRouteName(CupcakeScreen.Start.name)
}

@Test
fun cupcakeNavHost_clickCancelOnFlavorcreen_navigateToStartOrderScreen(){
    navigateToFlavorScreen()
    composeTestRule.onNodeWithStringId(R.string.cancel)
    navController.assertCurrentRouteName(CupcakeScreen.Start.name)
}

@Test
fun cupcakeNavHost_clickNextOnFlavorScreen_navigateToPickupScreen(){
    navigateToFlavorScreen()
    composeTestRule.onNodeWithStringId(R.string.next)
        .performClick()
    navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
}

@Test
fun cupcakeNavHost_clickNextOnPickupScreen_navigateToSummaryScreen(){
    navigateToPickupScreen()
    composeTestRule.onNodeWithStringId(R.string.next)
        .performClick()
    navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
}


private fun navigateToFlavorScreen() {
    composeTestRule.onNodeWithStringId(R.string.one_cupcake)
        .performClick()
    composeTestRule.onNodeWithStringId(R.string.chocolate)
        .performClick()
}

private fun navigateToPickupScreen() {
    navigateToFlavorScreen()
    composeTestRule.onNodeWithStringId(R.string.next)
        .performClick()
}

private fun navigateToSummaryScreen() {
    navigateToPickupScreen()
    composeTestRule.onNodeWithText(getFormattedDate())
        .performClick()
    composeTestRule.onNodeWithStringId(R.string.next)
        .performClick()
}

private fun getFormattedDate(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, 1)
    val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
    return formatter.format(calendar.time)
}

private fun performNavigateUp() {
    val backText = composeTestRule.activity.getString(R.string.back_button)
    composeTestRule.onNodeWithContentDescription(backText)
        .performClick()
}
