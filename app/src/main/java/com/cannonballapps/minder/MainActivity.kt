package com.cannonballapps.minder

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
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
        shape = RoundedCornerShape(size = 10.dp),
        color = if (item.isActive) MaterialTheme.colors.background else MaterialTheme.colors.secondaryVariant,
    ) {

        Row(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(horizontal = 20.dp, vertical = 20.dp), // TODO extract these two
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {

                // Reminder name
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val circleColor = if (item.isActive) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                    Canvas(modifier = Modifier.size(8.dp)) {
                        drawCircle(color = circleColor)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        ReminderDay.formatReminderDays(item.activeDays),
                        color = MaterialTheme.colors.secondary,
                        fontFamily = latoFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                    )
                }

                Text(
                    // TODO format time
                    "${item.startTime} - ${item.endTime}",
                    fontFamily = latoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 28.sp,
                )

                Text(
                    "${item.name}",
                    fontFamily = latoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                )
                // TODO time range
                // TODO active toggle
                // TODO days of the week
            }

            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End,
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

                // Ensure that the icon is aligned to the layout, without including the accessibility padding
                val buttonSize = 28.dp
                val iconOffset = max(48.dp - buttonSize, 0.dp) / 2
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.absoluteOffset(x = iconOffset, y = iconOffset),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                        contentDescription = "",
                        modifier = Modifier.size(buttonSize),
                        tint = MaterialTheme.colors.secondary,
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
                activeDays = getReminderDays(),
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
                activeDays = getReminderDays(),
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
    var activeDays: List<ReminderDay>,
) {
    fun formatTimeRange() : String {

    }
}

enum class ReminderDay(val shortName: String, var isActive: Boolean = true) {
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

    companion object {
        fun formatReminderDays(days: List<ReminderDay>): String {
            return days.filter { it.isActive }.map { it.shortName }.joinToString(separator = ", ")
        }
    }
}

fun getReminderDays() : List<ReminderDay> {
    return ReminderDay.values().toMutableList()
}

val sampleList = {
    val items = mutableListOf<ReminderItem>()
    for (i in 0..400) {
//        items.add(ReminderItem("Item $i"))
    }
    items
}

val latoFontFamily = FontFamily(
    Font(R.font.lato, FontWeight.Normal),
    Font(R.font.lato_bold, FontWeight.Bold),
)