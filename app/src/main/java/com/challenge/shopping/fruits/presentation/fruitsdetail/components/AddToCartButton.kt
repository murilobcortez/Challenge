package com.challenge.shopping.fruits.presentation.fruitsdetail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.challenge.R
import com.challenge.shopping.fruits.common.presentation.Red

@Composable
internal fun AddToCartButton(
    isAddedToCart: Boolean,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Red
        ),
        onClick = onClick,
        modifier = Modifier
            .padding(top = 32.dp)
    ) {
        Text(
            text = if (isAddedToCart) {
                stringResource(R.string.fruit_detail_screen_remove_from_cart)
            } else {
                stringResource(R.string.fruit_detail_screen_add_to_cart)
            },
            modifier = Modifier.padding(end = 8.dp)
        )
        Icon(
            imageVector = if (isAddedToCart) {
                Icons.Filled.ShoppingCart
            } else {
                Icons.Outlined.ShoppingCart
            },
            tint = Color.White,
            contentDescription = if (isAddedToCart) {
                stringResource(R.string.fruit_detail_screen_remove_from_cart)
            } else {
                stringResource(R.string.fruit_detail_screen_add_to_cart)
            }
        )
    }
}