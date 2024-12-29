package com.example.lapshopcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.lapshopcompose.Model.CategoryModel
import com.example.lapshopcompose.Model.SliderModel
import com.example.lapshopcompose.ViewModel.MainViewModel
import com.example.lapshopcompose.ui.theme.LapShopComposeTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Indicator

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainActivityScreen()

        }
    }
}

@Composable
@Preview
fun MainActivityScreen(){
    val viewModel = MainViewModel()
    val banners = remember { mutableStateListOf<SliderModel>() }
    val categories = remember { mutableStateListOf<CategoryModel>() }
    var showBannerLoading by remember { mutableStateOf(true)}
    var showCategoryLoading by remember { mutableStateOf(true)}

    //Banner
//    LaunchedEffect(Unit) {
//        viewModel.loadBanners()
//        viewModel.banners.observeForever {
//            banners.clear()
//            banners.addAll(it)
//            showBannerLoading=false
//        }
//    }

    //test hien thi anh tinh
    LaunchedEffect(Unit) {
        banners.clear()
        banners.addAll(
            listOf(
                SliderModel("https://example.com/image1.jpg")
            )
        )
        showBannerLoading = false
    }
    //


    ConstraintLayout(modifier = Modifier.background(Color.White)) {
        val (scrollList,bottomMenu) =createRefs()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(scrollList) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }


        ) {
            item{
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Chào mừng trở lại", color = Color.Black)

                        Text(text ="MINH ND",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold)
                    }


                    Row {
                        Image(painter = painterResource(id = R.drawable.fav_icon),
                            contentDescription ="", )
                        Spacer(modifier = Modifier.width(16.dp))
                        Image(painter = painterResource(id = R.drawable.search_icon),
                            contentDescription ="", )

                    }
                }
            }

            //Banners
            item {
                if(showBannerLoading){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(200.dp),
                        contentAlignment = Alignment.Center

                    ){
                        CircularProgressIndicator()
                    }
                }else{
                    Banners(banners)
                }
            }

            item{
                SectionTitle("Catergories", "See all")
            }

            item{
                if(showCategoryLoading){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                       // contentAlignment = Alignment.Center
                    )
                }
            }
        }

    }


}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banners(banners: List<SliderModel>) {

    val testBanners = listOf(
        SliderModel("https://laptopdell.com.vn/wp-content/uploads/2022/07/laptop_lenovo_legion_s7_8.jpg"),
        SliderModel("https://laptopdell.com.vn/wp-content/uploads/2024/09/48102_laptop_asus_expertbook_b1_b1402cva_nk0104w__1_.jpg"),
        SliderModel("https://laptopdell.com.vn/wp-content/uploads/2023/03/Lenovo-Thinkpad-X1-Nano-Gen-1_1-1.png"),
        SliderModel("https://laptopdell.com.vn/wp-content/uploads/2022/08/dell-latitude-14-7430-4.jpg"),
        SliderModel("https://laptopdell.com.vn/wp-content/uploads/2022/08/Dell-Inspiron-plus-7620-3.jpg"),
    )


   // AutoSlidingCarousel(banners = banners)
    AutoSlidingCarousel(banners = testBanners)
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoSlidingCarousel(modifier: Modifier=Modifier,
                        pagerState: PagerState = remember { PagerState() },
                        banners: List<SliderModel>) {

    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    Column (modifier = modifier.fillMaxSize()) {
        HorizontalPager(count = banners.size, state = pagerState) {
            page ->
            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(banners[page].url)
                .build(),
                contentDescription =  null,
                contentScale = ContentScale.Fit,

                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                    .height(150.dp)

            )
        }

        DotIndicator(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally),
            totalDots = banners.size,
            selectedIndex = if(isDragged) pagerState.currentPage else pagerState.currentPage,
            dotSize = 8.dp
            )
    }
}

@Composable
fun DotIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex:Int,
    selectedColor: Color = colorResource(id = R.color.purple),
    unSelectedColor: Color= colorResource(id = R.color.grey),
    dotSize: Dp

){
    LazyRow (
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ){
        items(totalDots){
            index ->
            IndicatorDot(
                color = if(index == selectedIndex)selectedColor else unSelectedColor,
                size = dotSize
            )

            if(index != totalDots - 1){
                Spacer(modifier = Modifier.padding(horizontal = 2.dp ))
            }
        }
    }


}

@Composable
fun IndicatorDot(modifier: Modifier=Modifier,
                 size : Dp,
                 color: Color
                ) {
    Box(modifier = modifier
        .size(size)
        .clip(CircleShape)
        .background(color)
    )

}

@Composable
fun SectionTitle (title: String, actionText: String){

    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
    Text(
        text = title,
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
        Text(
            text = actionText,
            color = colorResource(R.color.purple)
        )
    }
}




































