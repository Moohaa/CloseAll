package org.close_all.project.ui


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun appRowItem(
    name: String,
    onCheckboxChange: (Boolean) -> Unit,
    onButtonClick: () -> Unit,
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State for the checkbox
    val isChecked = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 1. Checkbox
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                onCheckboxChange(it)
            }
        )

        // 2. Icon
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Icon",
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp)
        )

        // 3. Text (Name)
        Text(
            text = name,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

        // 4. Button
        Button(
            onClick = onButtonClick,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text("Action")
        }

        // 5. Options Icon
        IconButton(
            onClick = onOptionsClick
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Options"
            )
        }
    }
}