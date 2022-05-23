package com.cannonballapps.minder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cannonballapps.minder.ui.theme.MinderTheme

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

        }
        RemindersListLayout()
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

// TODO border color
// TODO extract hardcoded dimens
@Composable
fun RemindersListItem(item: ReminderItem) {
    Surface(
        Modifier
            .fillMaxWidth(),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colors.primaryVariant,
            // TODO color
        ),
        shape = RoundedCornerShape(15),
    ) {
        Column {
            // Reminder name
            Row {
                Text(
                    item.name,
                    modifier = Modifier.padding(all = 8.dp),
                )
            }
            // TODO active toggle
            // TODO days of the week
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

@Preview(
    showBackground = true,
    name = "Reminders List Item",
    widthDp = 400,
)
@Composable
fun PreviewRemindersListItem() {
    MinderTheme {
        RemindersListItem(
            ReminderItem("Drink Water"),
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

data class ReminderItem(val name: String)

val sampleList = {
    val items = mutableListOf<ReminderItem>()
    for (i in 0..400) {
        items.add(ReminderItem("Item $i"))
    }
    items
}
