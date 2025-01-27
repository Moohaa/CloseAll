import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.close_all.project.ui.vectors.Cancel
import org.close_all.project.ui.vectors.Refresh

@Composable
fun ControlHeader(
    onCheckboxChange: (Boolean) -> Unit,
    shutDownClicked: () -> Unit,
    loadClicked: () -> Unit
) {
    // State for the checkbox
    val isChecked = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Checkbox(
                checked = isChecked.value,
                modifier = Modifier.size(20.dp).padding(3.dp),
                onCheckedChange = {
                    isChecked.value = it
                    onCheckboxChange(it)
                }
            )

            if (isChecked.value) {
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
        IconButton(
            onClick = loadClicked,
        ) {
            Icon(
                imageVector = Refresh,
                contentDescription = "refresh",
                tint = Color.Blue,
            )
        }


    }
}

