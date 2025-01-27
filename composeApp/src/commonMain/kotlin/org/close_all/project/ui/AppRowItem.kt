package org.close_all.project.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.close_all.project.data.App
import org.close_all.project.ui.vectors.Cancel

@Composable
fun appRowItem(
    app: App,
    onCheckboxChange: (Boolean) -> Unit,
    shutDownClicked: () -> Unit,
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State for the checkbox
    val isChecked = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // 1. Checkbox
        Checkbox(
            checked = isChecked.value,
            modifier = Modifier.size(20.dp),
            onCheckedChange = {
                isChecked.value = it
                onCheckboxChange(it)
            }
        )

        Text(
            text = app.name,
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 40.dp)
        )

        // 4. ButtonB
        IconButton(
            onClick = shutDownClicked,
        ) {
            Icon(
                imageVector = Cancel,
                contentDescription = "Shutdown",
                tint = Color.Red,
            )
        }
    }
}