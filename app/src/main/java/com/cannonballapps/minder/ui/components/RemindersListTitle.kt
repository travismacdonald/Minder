package com.cannonballapps.minder.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cannonballapps.minder.R
import com.cannonballapps.minder.ui.theme.MinderTheme

@Composable
fun RemindersListTitleLayout() {
    Row(
        horizontalArrangement = Arrangement.Center

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

@Preview(showBackground = true)
@Composable
fun PreviewRemindersListTitle() {
    MinderTheme {
        RemindersListLayout()
    }
}
