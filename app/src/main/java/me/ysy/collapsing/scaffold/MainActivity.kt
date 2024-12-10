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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import nbe.someone.code.ios.switches.IOSSwitch
import nbe.someone.code.ios.switches.IOSSwitchColors
import nbe.someone.code.ios.switches.IOSSwitchConfig
import nbe.someone.code.ios.switches.LocalIOSSwitchColors
import nbe.someone.code.ios.switches.LocalIOSSwitchConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CPMainPage()
        }
    }
}

private fun defaultConfig(
    width: Dp = 60.dp,
    trackHeight: Dp = 30.dp,
    trackPadding: Dp = 2.dp,
): IOSSwitchConfig {
    return IOSSwitchConfig(
        width = width,
        trackHeight = trackHeight,
        trackPadding = trackPadding,
    )
}

@Composable
private fun defaultColor(
    checkedThumbColor: Color = Color(0xFFFFFFFF),
    checkedTrackColor: Color = Color(0xFF248A3E),
    normalThumbColor: Color = Color(0xFF197AD7),
    normalTrackColor: Color = Color(0xFF95969B),
): IOSSwitchColors {
    return IOSSwitchColors(
        checkedThumbColor = checkedThumbColor,
        checkedTrackColor = checkedTrackColor,
        normalThumbColor = normalThumbColor,
        normalTrackColor = normalTrackColor,
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

    CompositionLocalProvider(
        LocalIOSSwitchConfig provides defaultConfig(),
        LocalIOSSwitchColors provides defaultColor(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            IOSSwitch(checkedState = isCheckedState, onClick = onClick)

            CompositionLocalProvider(
                LocalIOSSwitchColors provides defaultColor(checkedTrackColor = Color(0xFF3700B3)),
            ) { IOSSwitch(checkedState = isCheckedState, onClick = onClick) }

            CompositionLocalProvider(
                LocalIOSSwitchConfig provides defaultConfig(trackHeight = 40.dp),
            ) { IOSSwitch(checkedState = isCheckedState, onClick = onClick) }

            CompositionLocalProvider(
                LocalIOSSwitchConfig provides defaultConfig(trackPadding = 3.dp),
            ) { IOSSwitch(checkedState = isCheckedState, onClick = onClick) }

            CompositionLocalProvider(
                LocalIOSSwitchColors provides defaultColor(checkedTrackColor = Color(0xFFBB86FC)),
            ) { IOSSwitch(checkedState = isCheckedState, onClick = onClick) }
        }
    }
}
