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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.close_all.project.data.App
import org.close_all.project.ui.vectors.Cancel

@Composable
fun appRowItem(
    app: App,
    onCheckboxChange: () -> Unit,
    shutDownClicked: () -> Unit,
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Checkbox(
            checked = app.checked,
            modifier = Modifier.size(20.dp),
            onCheckedChange = {
                onCheckboxChange()
            }
        )

        Text(
            text = app.name,
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 40.dp)
        )

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