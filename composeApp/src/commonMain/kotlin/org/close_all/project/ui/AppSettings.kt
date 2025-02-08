package org.close_all.project.ui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.close_all.project.state.AppState
import org.close_all.project.ui.shared.Divider
import org.close_all.project.ui.vectors.Eye


@Composable
fun AppSettings(
    onClose: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End

        ) {
            IconButton(onClick = onClose) {
                Icon(
                    Icons.Default.Close,
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = "Close"
                )
            }
        }
        Divider()

        Column(
            modifier = Modifier.padding(horizontal = 7.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    color = MaterialTheme.colors.onBackground,
                    text = "Dark Mode",
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = AppState.is_darkMode.value,
                    onCheckedChange = {
                        AppState.changeMode()
                    }
                )
            }

//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(
//                    color = MaterialTheme.colors.onBackground,
//                    text = "Include System Apps", modifier = Modifier.weight(1f)
//                )
//                Checkbox(
//                    checked = false,
//                    onCheckedChange = {}
//                )
//            }

            Column(modifier = Modifier.fillMaxWidth()) {

                var showHiddenApps by remember { mutableStateOf(false) }
                val hiddenApps by AppState.hiddenApps.collectAsState()

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        color = MaterialTheme.colors.onBackground,
                        text = "Hidden Applications", modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        showHiddenApps = !showHiddenApps
                    }) {
                        Text(
                            color = MaterialTheme.colors.primary,
                            text = "(${hiddenApps.size})"
                        )
                    }
                }

                if (showHiddenApps) {
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f)
                            .padding(vertical = 10.dp)
                            .background(
                                MaterialTheme.colors.background
                            ),
                    ) {
                        val scrollState = rememberScrollState()
                        Column(
                            modifier = Modifier.verticalScroll(scrollState)
                                .padding(vertical = 5.dp)


                        ) {
                            hiddenApps.forEach {
                                Row(
                                    modifier = Modifier.fillMaxWidth().height(35.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(
                                        modifier = Modifier.padding(
                                            0.dp
                                        ),
                                        onClick = {
                                            AppState.unHideApp(it)
                                        },
                                    ) {
                                        Icon(
                                            modifier = Modifier.padding(
                                                0.dp
                                            ),
                                            imageVector = Eye,
                                            contentDescription = "Show",
                                            tint = MaterialTheme.colors.onBackground,
                                        )
                                    }
                                    Text(
                                        text = it,
                                        color = MaterialTheme.colors.primary,
                                        fontWeight = FontWeight.ExtraLight
                                    )
                                }

                            }

                        }
                        VerticalScrollbar(
                            adapter = rememberScrollbarAdapter(scrollState),
                            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
                                .width(8.dp)
                        )
                    }
                }
            }

        }
    }


}
