import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TriStateCheckbox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import org.close_all.project.ui.vectors.Cancel
import org.close_all.project.ui.vectors.Refresh

@Composable
fun ControlHeader(
    checked: Boolean?,
    loading: Boolean,
    onCheckboxChange: () -> Unit,
    shutDownClicked: () -> Unit,
    loadClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().size(45.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            var state = when (checked) {
                null -> ToggleableState.Indeterminate
                true -> ToggleableState.On
                false -> ToggleableState.Off
            }

            TriStateCheckbox(
                state = state,
                modifier = Modifier.size(20.dp).padding(3.dp),
                onClick = {
                    state = when (checked) {
                        null -> ToggleableState.Indeterminate
                        true -> ToggleableState.Off
                        false -> ToggleableState.On
                    }
                    onCheckboxChange()
                }
            )


            Box(
                modifier = Modifier.size(45.dp),
                contentAlignment = Alignment.Center
            ) {
                if ((checked == true || checked == null) && !loading) {
                    IconButton(
                        onClick = shutDownClicked,
                    ) {
                        Icon(
                            imageVector = Cancel,
                            contentDescription = "Shutdown",
                            tint = MaterialTheme.colors.error,
                        )
                    }
                } else if (loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(25.dp),
                    )
                } else {
                    IconButton(
                        onClick = loadClicked,
                    ) {
                        Icon(
                            imageVector = Refresh,
                            contentDescription = "refresh",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(25.dp),
                        )
                    }
                }
            }

        }
        Box(
            modifier = Modifier.size(45.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(

                onClick = { }
            ) {
                Icon(
                    tint = MaterialTheme.colors.onBackground,
                    imageVector = Icons.Default.Settings, // Use the "more options" icon
                    contentDescription = "Options" // Accessibility description
                )
            }
        }


    }
}

