package com.wahid.newscmp.presentation.screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Forward
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.wahid.newscmp.utils.Colors.ColorAccent
import com.wahid.newscmp.utils.Colors.ColorDivider
import com.wahid.newscmp.utils.Colors.ColorSurface
import com.wahid.newscmp.utils.Colors.ColorTextPrimary

@Composable
fun AppSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    onSearch: (String) -> Unit = {},
    onClearClick: () -> Unit = { onQueryChange("") }
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        readOnly = readOnly,
        singleLine = true,
        placeholder = {
            Text(placeholder)
        },
        leadingIcon = {
            Icon(
                imageVector = Lucide.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (query.isNotBlank()) {
                IconButton(
                    onClick = onClearClick
                ) {
                    Icon(
                        imageVector = Lucide.Forward,
                        contentDescription = "Clear Search"
                    )
                }
            }
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(query)
            }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = ColorSurface,
            unfocusedContainerColor = ColorSurface,

            focusedBorderColor = ColorAccent,
            unfocusedBorderColor = ColorDivider,

            focusedTextColor = ColorTextPrimary,
            unfocusedTextColor = ColorTextPrimary,

            cursorColor = ColorAccent
        ),
        shape = RoundedCornerShape(16.dp)
    )
}