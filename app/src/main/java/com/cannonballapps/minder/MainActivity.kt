package com.cannonballapps.minder

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cannonballapps.minder.ui.theme.MinderTheme
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinderApp()
        }
    }
}

@Composable
fun MinderApp() {
    MinderTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            RemindersListLayout()
        }
    }
}

@Composable
fun RemindersListLayout() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        RemindersList(sampleList())
    }
}

@Composable
fun RemindersList(reminderItems: List<ReminderItem>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            RemindersListTitleLayout()
        }
        items(reminderItems) { reminder ->
            RemindersListItem(item = reminder)
        }
    }
}

@Composable
fun RemindersListTitleLayout() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        RemindersListTitle()
    }
}

@Composable
fun RemindersListTitle() {
    Text(
        text = stringResource(R.string.reminders_list_title),
        fontSize = 24.sp,
        modifier = Modifier
            .padding(vertical = 12.dp)
    )
}

// TODO make preview for inactive

// TODO trash icon
// TODO Row -> two columns with weight
// TODO border color
// TODO extract hardcoded dimens
@Composable
fun RemindersListItem(item: ReminderItem) {
    Surface(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        border = BorderStroke(
            width = 2.dp,
            color = if (item.isActive) MaterialTheme.colors.primary else MaterialTheme.colors.background,
        ),
        shape = RoundedCornerShape(15),
        color = if (item.isActive) MaterialTheme.colors.background else MaterialTheme.colors.secondaryVariant,
    ) {
        Row(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)) {
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                // Reminder name
                Row {
                    Text(
                        item.name,
                    )
                }
                Text(
                    // TODO format time
                    "${item.startTime} - ${item.endTime}"
                )
                Text(
                    "${item.name}"
                )
                // TODO time range
                // TODO active toggle
                // TODO days of the week
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
            ) {
                Switch(
                    checked = item.isActive,
                    onCheckedChange = { /* TODO */ },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colors.background,
                        checkedTrackColor = MaterialTheme.colors.primary,
                        uncheckedThumbColor = MaterialTheme.colors.background,
                        uncheckedTrackColor = MaterialTheme.colors.secondary,
                        checkedTrackAlpha = 1f,
                        uncheckedTrackAlpha = 1f,
                    )
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRemindersListTitle() {
    MinderTheme {
        RemindersListLayout()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    showBackground = true,
    name = "Reminders List Item (Active)",
    widthDp = 400,
)
@Composable
fun PreviewRemindersListItemActive() {
    MinderTheme {
        RemindersListItem(
            ReminderItem(
                name = "Drink Water",
                startTime = LocalTime.of(10, 30),
                endTime = LocalTime.of(18, 0),
                intervalInMins = 60,
                isActive = true,
                activeDays = listOf(
                    false,
                    true,
                    true,
                    true,
                    true,
                    true,
                    false,
                ),
            ),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    showBackground = true,
    name = "Reminders List Item (Inactive)",
    widthDp = 400,
)
@Composable
fun PreviewRemindersListItemInactive() {
    MinderTheme {
        RemindersListItem(
            ReminderItem(
                name = "Drink Water",
                startTime = LocalTime.of(10, 30),
                endTime = LocalTime.of(18, 0),
                intervalInMins = 60,
                isActive = false,
                activeDays = listOf(
                    false,
                    true,
                    true,
                    true,
                    true,
                    true,
                    false,
                ),
            ),
        )
    }
}

@Preview
@Composable
fun PreviewRemindersList() {
    MinderTheme {
        RemindersListLayout()
    }
}

data class ReminderItem(
    val name: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val intervalInMins: Int,
    var isActive: Boolean,
    var activeDays: List<Boolean>,
)

enum class ReminderDay(shortName: String, var isActive: Boolean = false) {
    SUNDAY(shortName = "Sun"),
    MONDAY(shortName = "Mon"),
    TUESDAY(shortName = "Tue"),
    WEDNESDAY(shortName = "Wed"),
    THURSDAY(shortName = "Thu"),
    FRIDAY(shortName = "Fri"),
    SATURDAY(shortName = "Sat");

    fun toggleIsActive() {
        this.isActive = !this.isActive
    }
}

val sampleList = {
    val items = mutableListOf<ReminderItem>()
    for (i in 0..400) {
//        items.add(ReminderItem("Item $i"))
    }
    items
}
