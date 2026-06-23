package com.wahid.newscmp.presentation.screen.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MessageCircleHeart
import com.composables.icons.lucide.Newspaper
import com.composables.icons.lucide.TrendingUp
import com.wahid.newscmp.presentation.screen.allNews.NewsTab
import com.wahid.newscmp.utils.Colors.ColorAccent
import com.wahid.newscmp.utils.Colors.ColorSurface
import com.wahid.newscmp.utils.Colors.ColorTextMuted

@Composable
fun NewsBottomBar(
    selectedTab: NewsTab,
    onTabSelected: (NewsTab) -> Unit,
) {
    NavigationBar(
        containerColor = ColorSurface,
        tonalElevation = 0.dp,
    ) {
        NewsTab.entries.forEach { tab ->
            val isSelected = tab == selectedTab
            NavigationBarItem(
                selected = isSelected,
                onClick  = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = when (tab) {
                            NewsTab.ALL_NEWS  -> Lucide.Newspaper
                            NewsTab.HEADLINES -> Lucide.TrendingUp
                            NewsTab.FAVORITES -> if (isSelected) Lucide.MessageCircleHeart else Lucide.Heart
                        },
                        contentDescription = tab.label,
                    )
                },
                label = { Text(tab.label, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = ColorAccent,
                    selectedTextColor   = ColorAccent,
                    unselectedIconColor = ColorTextMuted,
                    unselectedTextColor = ColorTextMuted,
                    indicatorColor      = Color(0xFF252540),
                ),
            )
        }
    }
}