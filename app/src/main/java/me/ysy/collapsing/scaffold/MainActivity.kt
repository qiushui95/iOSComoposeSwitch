package me.ysy.collapsing.scaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nbe.someone.code.ios.switches.IOSSwitch
import nbe.someone.code.ios.switches.IOSSwitchConfig
import nbe.someone.code.ios.switches.LocalIOSSwitchConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CPMainPage()
        }
    }
}

private fun defaultConfig(): IOSSwitchConfig {
    return IOSSwitchConfig(
        width = 60.dp,
        trackHeight = 30.dp,
        trackPadding = 2.dp,
        checkedThumbColorRes = R.color.colorSwitchThumb,
        checkedTrackColorRes = R.color.colorSwitchTrackChecked,
        normalThumbColorRes = R.color.colorSwitchThumb,
        normalTrackColorRes = R.color.colorSwitchTrackNormal,
    )
}

@Composable
private fun CPMainPage() {
    val isCheckedState = remember {
        mutableStateOf(false)
    }

    val onClick = remember(key1 = isCheckedState) {
        { isCheckedState.value = isCheckedState.value.not() }
    }

    CompositionLocalProvider(LocalIOSSwitchConfig provides defaultConfig()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            IOSSwitch(checkedState = isCheckedState, onClick = onClick)
            IOSSwitch(
                checkedState = isCheckedState,
                onClick = onClick,
                IOSSwitchConfig.remember(checkedTrackColorRes = R.color.purple_700),
            )
            IOSSwitch(
                checkedState = isCheckedState,
                onClick = onClick,
                IOSSwitchConfig.remember(trackHeight = 40.dp),
            )
            IOSSwitch(
                checkedState = isCheckedState,
                onClick = onClick,
                IOSSwitchConfig.remember(trackPadding = 3.dp),
            )
            IOSSwitch(
                checkedState = isCheckedState,
                onClick = onClick,
                IOSSwitchConfig.remember(checkedThumbColorRes = R.color.purple_200),
            )
        }
    }
}
