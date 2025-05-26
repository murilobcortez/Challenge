package com.challenge.shopping.fruits.presentation.fruitslist.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.challenge.R
import com.challenge.shopping.fruits.domain.model.Fruit


@Composable
fun FruitList(
    fruits: List<Fruit>,
    onFruitClick: (Fruit) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.systemBars),
        state = scrollState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = fruits,
            key = { it.id }
        ) { fruitItem ->

            Surface(
                modifier = modifier
                    .clickable(
                        onClick = {
                            onFruitClick(fruitItem)
                        }
                    ),
                color = Color.White
            ){
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    Box(
                        modifier = Modifier
                            .height(64.dp)
                            .width(64.dp),
                        contentAlignment = Alignment.Center
                    ){
                        if (fruitItem.image != null) {
                            if(fruitItem.image.width == 1 && fruitItem.image.height == 1){
                                Image(
                                    painter = painterResource(R.drawable.ic_image_not_found),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            } else {
                                Image(
                                    bitmap = fruitItem.image,
                                    contentDescription = fruitItem.name,
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            }
                        } else {
                            CircularProgressIndicator(modifier = Modifier.size(32.dp))
                        }
                    }
                    Text(
                        text = fruitItem.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                    )
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.5f),
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp)
                )
            }
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    }
}