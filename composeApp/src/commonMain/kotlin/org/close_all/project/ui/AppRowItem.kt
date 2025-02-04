package org.close_all.project.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import org.close_all.project.data.App
import org.close_all.project.utils.TimeFormatUtils

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
        horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.height(40.dp)
    ) {
        Checkbox(
            checked = app.checked,
            modifier = Modifier.size(20.dp),
            onCheckedChange = {
                onCheckboxChange()
            }
        )

        Text(
            color = MaterialTheme.colors.onBackground,
            text = app.name,
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 40.dp)
        )

        Text(
            color = MaterialTheme.colors.onBackground,
            text = TimeFormatUtils.getTimeAgo(app.startTime),
            modifier = Modifier.padding(horizontal = 7.dp),
            fontWeight = FontWeight.Light,
            fontSize = TextUnit(14f, TextUnitType.Sp)
        )

//        IconButton(
//            onClick = shutDownClicked,
//        ) {
//            Icon(
//                imageVector = Cancel,
//                contentDescription = "Shutdown",
//                tint = Color.Red,
//            )
//        }
    }
}

