package com.example.mysootheapp

import android.content.pm.ActivityInfo.WindowLayout
import android.os.Bundle
import android.view.Window
import android.view.WindowMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mysootheapp.ui.theme.MySootheAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MySootheAppTheme {
                // A surface container using the 'background' color from the theme
                MySoothAppPortrait()
            }
        }
    }
}



@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(Modifier.padding(horizontal = 16.dp))
        HomeSection(title = R.string.align_your_body) {
            AlignYourBodyRow()
        }
        HomeSection(title = R.string.favorite_collections) {
            FavoriteCollectionGrid()
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(text = stringResource(id = title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
            )
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    TextField(value = "",
        onValueChange = {},
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null )
        },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text(stringResource(id = R.string.placeHolderSearch))
        }
        )
}

@Composable
fun AlignYourBodyElement(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = null,
            modifier = modifier
                .size(88.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(id = text),
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium
            )
    }
}

@Composable
fun FavoriteCollectionCard(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    ) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(255.dp)
        ) {
            Image(painter = painterResource(id = drawable),
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = stringResource(id = text),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
                )
        }
    }
}


@Composable
fun AlignYourBodyRow(modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
        items(alignYourBodyData) {item ->
            AlignYourBodyElement(item.drawable, item.text)

        }
    }
}

@Composable
fun FavoriteCollectionGrid(modifier: Modifier = Modifier) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = modifier.height(168.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
        items(favoriteCollectionsData) {item->
            FavoriteCollectionCard(item.drawable, item.text, Modifier.height(80.dp))
        }
    }
}


@Composable
fun SootheBottomNavigation(modifier: Modifier = Modifier) {
    NavigationBar(modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
        NavigationBarItem(selected = true,
            onClick = { /*TODO*/ },
            icon = {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            },
            label = {
                Text(text = stringResource(id = R.string.bottom_navigation_home))
            }
        )

        NavigationBarItem(selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
            },
            label = {
                Text(text = stringResource(id = R.string.bottom_navigation_profile))
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySoothAppPortrait() {
    MySootheAppTheme {
        Scaffold(
            bottomBar = { SootheBottomNavigation()}
        ){paddingValues ->
        HomeScreen(modifier = Modifier.padding(paddingValues))
        }
    }
}


@Composable
fun SoothNavigationRail(modifier: Modifier = Modifier) {
    NavigationRail(
        modifier = modifier.padding(start = 8.dp, end = 8.dp),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationRailItem(selected = true,
                onClick = { /*TODO*/ },
                icon = {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = R.string.bottom_navigation_home))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            NavigationRailItem(selected = false,
                onClick = { /*TODO*/ },
                icon = {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = R.string.bottom_navigation_profile))
                }
            )
        }
    }
}


@Composable
fun MySoothAppLandscape() {
    MySootheAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Row {
                SoothNavigationRail()
                HomeScreen()
            }
        }
    }
}

private val alignYourBodyData = listOf(
    R.drawable.ab1_inversions to R.string.ab1_inversions,
    R.drawable.ab2_quick_yoga to R.string.ab2_quick_yoga,
    R.drawable.ab3_stretching to R.string.ab3_stretching,
    R.drawable.ab4_tabata to R.string.ab4_tabata,
    R.drawable.ab5_hiit to R.string.ab5_hiit,
    R.drawable.ab6_pre_natal_yoga to R.string.ab6_pre_natal_yoga
).map { DrawableStringPair(it.first, it.second) }

private val favoriteCollectionsData = listOf(
    R.drawable.fc1_short_mantras to R.string.fc1_short_mantras,
    R.drawable.fc2_nature_meditations to R.string.fc2_nature_meditations,
    R.drawable.fc3_stress_and_anxiety to R.string.fc3_stress_and_anxiety,
    R.drawable.fc4_self_massage to R.string.fc4_self_massage,
    R.drawable.fc5_overwhelmed to R.string.fc5_overwhelmed,
    R.drawable.fc6_nightly_wind_down to R.string.fc6_nightly_wind_down
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)



@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    MySootheAppTheme {
        SearchBar()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun BodyElementPreview() {
    MySootheAppTheme {
        AlignYourBodyElement(
            drawable = R.drawable.ab1_inversions,
            text = R.string.ab1_inversions,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun CollectionCardPreview() {
    MySootheAppTheme {
        FavoriteCollectionCard(
            modifier = Modifier.padding(8.dp),
            drawable = R.drawable.fc2_nature_meditations,
            text = R.string.fc2_nature_meditations
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BodyRowPreview() {
    MySootheAppTheme {
        AlignYourBodyRow(modifier = Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CollectionGridPreview() {
    MySootheAppTheme {
        FavoriteCollectionGrid()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun HomeSectionPreview() {
    MySootheAppTheme {
        HomeSection(title = R.string.align_your_body) {
            AlignYourBodyRow()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE, heightDp = 180)
@Composable
fun HomeScreenPreview() {
    MySootheAppTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun BottomNavigationPreview() {
    MySootheAppTheme {
        SootheBottomNavigation()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun MySoothPortraitPreview() {
    MySootheAppTheme {
        MySoothAppPortrait()
    }
}

@Preview(showBackground = true)
@Composable
fun SoothNavigationRailPreview() {
    MySootheAppTheme {
        SoothNavigationRail()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE, heightDp = 320)
@Composable
fun MySoothLandscapePreview() {
    MySootheAppTheme {
        MySoothAppLandscape()
    }
}


