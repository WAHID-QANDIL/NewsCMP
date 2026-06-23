package com.wahid.newscmp.presentation.screen.component



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.composables.icons.lucide.Bookmark
import com.composables.icons.lucide.Lucide
import com.composables.icons.materialsymbols.MaterialSymbols
import com.composables.icons.materialsymbols.outlined.Bookmark
import com.wahid.newscmp.domain.model.Article
import com.wahid.newscmp.utils.Colors.ColorAccent
import com.wahid.newscmp.utils.Colors.ColorBreaking
import com.wahid.newscmp.utils.Colors.ColorSurface


@Composable
fun FeaturedCard(
    article: Article,
    onClick: () -> Unit,
    onBookmark: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onClick),
        shape  = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ColorSurface),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Background image
            AsyncImage(
                model              = article.urlToImage,
                contentDescription = article.title,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier.fillMaxSize()
            )

            // Gradient scrim for text legibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.00f to Color.Transparent,
                                0.45f to Color(0x550F0F16),
                                1.00f to Color(0xEE0F0F16),
                            )
                        )
                    )
            )

            // FEATURED pill — top start
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart),
                shape = RoundedCornerShape(4.dp),
                color = ColorBreaking,
            ) {
                Text(
                    text          = "FEATURED",
                    color         = Color.White,
                    fontSize      = 9.sp,
                    fontWeight    = FontWeight.Bold,
                    letterSpacing = 1.2.sp,
                    modifier      = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }

            // Bookmark icon — top end
            IconButton(
                onClick  = onBookmark,
                modifier = Modifier.align(Alignment.TopEnd),
            ) {
                Icon(
                    imageVector        = if (article.isFavorite) Lucide.Bookmark else MaterialSymbols.Outlined.Bookmark,
                    contentDescription = if (article.isFavorite) "Remove bookmark" else "Add bookmark",
                    tint               = if (article.isFavorite) ColorAccent else Color.White,
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 12.dp, end = 48.dp, bottom = 12.dp),
            ) {
                Spacer(Modifier.height(5.dp))
                Text(
                    text       = article.title,
                    color      = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 17.sp,
                    lineHeight = 23.sp,
                    maxLines   = 2,
                    overflow   = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
