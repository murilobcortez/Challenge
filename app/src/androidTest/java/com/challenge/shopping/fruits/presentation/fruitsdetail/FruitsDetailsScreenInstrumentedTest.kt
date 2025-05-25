package com.challenge.shopping.fruits.presentation.fruitsdetail

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.challenge.R
import com.challenge.shopping.fruits.domain.model.Fruit
import com.challenge.shopping.fruits.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FruitsDetailScreenInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun getSampleFruit() = Fruit(
        id = 1,
        name = "Banana",
        family = "Musaceae",
        order = "Zingiberales",
        genus = "Musa",
        price = "$1.99",
        image = null
    )

    @Test
    fun fruitDetailScreen_showsFruitDetails_whenFruitIsPresent() {
        val fruit = getSampleFruit()
        val state = FruitsDetailState(fruit = fruit, isAddedToCart = false)

        composeTestRule.setContent {
            FruitsDetailScreen(
                state = state,
                onAction = {}
            )
        }

        // Assert that the fruit name and details are displayed
        composeTestRule.onNodeWithText("Banana").assertIsDisplayed()
        composeTestRule.onNodeWithText("Musaceae").assertIsDisplayed()
        composeTestRule.onNodeWithText("Zingiberales").assertIsDisplayed()
        composeTestRule.onNodeWithText("Musa").assertIsDisplayed()
        composeTestRule.onNodeWithText("$1.99").assertIsDisplayed()

        // Assert that the Add to Cart button is displayed
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.fruit_detail_screen_add_to_cart)
        ).assertIsDisplayed()
    }

    @Test
    fun fruitDetailScreen_showsNotIdentifiedText_whenFruitIsNull() {
        val state = FruitsDetailState(fruit = null, isAddedToCart = false)

        composeTestRule.setContent {
            FruitsDetailScreen(
                state = state,
                onAction = {}
            )
        }

        // Assert that the "not identified" message is displayed
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.fruit_detail_screen_fruit_not_identified)
        ).assertIsDisplayed()
    }
}