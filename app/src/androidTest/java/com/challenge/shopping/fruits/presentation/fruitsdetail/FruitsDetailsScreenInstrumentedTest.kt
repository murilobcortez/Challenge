package com.challenge.shopping.fruits.presentation.fruitsdetail

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.challenge.R
import com.challenge.shopping.fruits.presentation.stub.TestStub
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FruitsDetailScreenInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun fruitDetailScreen_showsFruitDetails_whenFruitIsPresent() {
        // Given
        val fruit = TestStub.generateFruit()
        val state = FruitsDetailState(fruit = fruit, isAddedToCart = false)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val addToCartText = context.getString(R.string.fruit_detail_screen_add_to_cart)

        // When
        composeTestRule.setContent {
            FruitsDetailScreen(
                state = state,
                onAction = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText("Banana").assertIsDisplayed()
        composeTestRule.onNodeWithText("Musaceae").assertIsDisplayed()
        composeTestRule.onNodeWithText("Zingiberales").assertIsDisplayed()
        composeTestRule.onNodeWithText("Musa").assertIsDisplayed()
        composeTestRule.onNodeWithText("$1.99").assertIsDisplayed()

        composeTestRule.onNodeWithText(addToCartText).assertIsDisplayed()
    }

    @Test
    fun fruitDetailScreen_showsNotIdentifiedText_whenFruitIsNull() {
        // Given
        val state = FruitsDetailState(fruit = null, isAddedToCart = false)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val fruitNotIdentifiedText = context.getString(
            R.string.fruit_detail_screen_fruit_not_identified
        )

        // When
        composeTestRule.setContent {
            FruitsDetailScreen(
                state = state,
                onAction = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText(fruitNotIdentifiedText).assertIsDisplayed()
    }
}