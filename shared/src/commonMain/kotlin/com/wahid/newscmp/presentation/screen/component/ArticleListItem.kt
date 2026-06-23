package com.wahid.newscmp.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.wahid.newscmp.utils.Colors.ColorChip
import com.wahid.newscmp.utils.Colors.ColorTextMuted
import com.wahid.newscmp.utils.Colors.ColorTextPrimary


@Composable
fun ArticleListItem(
    article: Article,
    onClick: () -> Unit,
    onBookmark: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment     = Alignment.Top,
    ) {
        // Left: text block
        Column(modifier = Modifier.weight(1f)) {
            SourceBadge(source = article.source, time = article.publishedAt)
            Spacer(Modifier.height(5.dp))
            Text(
                text       = article.title,
                color      = ColorTextPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize   = 15.sp,
                lineHeight = 21.sp,
                maxLines   = 3,
                overflow   = TextOverflow.Ellipsis,
            )
            article.description?.takeIf { it.isNotBlank() }?.let { desc ->
                Spacer(Modifier.height(4.dp))
                Text(
                    text       = desc,
                    color      = ColorTextMuted,
                    fontSize   = 13.sp,
                    lineHeight = 18.sp,
                    maxLines   = 2,
                    overflow   = TextOverflow.Ellipsis,
                )
            }
        }

        // Right: thumbnail + bookmark
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            AsyncImage(
                model              = article.urlToImage,
                contentDescription = null,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier
                    .size(width = 90.dp, height = 72.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ColorChip),
            )
            IconButton(
                onClick  = onBookmark,
                modifier = Modifier.size(28.dp),
            ) {
                Icon(
                    imageVector        = if (article.isFavorite) Lucide.Bookmark else MaterialSymbols.Outlined.Bookmark,
                    contentDescription = if (article.isFavorite) "Remove bookmark" else "Add bookmark",
                    tint               = if (article.isFavorite) ColorAccent else Color.White,
                    modifier           = Modifier.size(18.dp),
                )
            }
        }
    }
}