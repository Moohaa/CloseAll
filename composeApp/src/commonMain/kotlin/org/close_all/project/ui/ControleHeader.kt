import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun ControlHeader(
    onCheckboxChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit
) {
    // State for the checkbox
    val isChecked = remember { mutableStateOf(false) }

    // State for the search input
    val searchQuery = remember { mutableStateOf("") }

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

        // 2. Search Input Field
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            placeholder = { Text("Search...") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(searchQuery.value)
                }
            ),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

       
    }
}