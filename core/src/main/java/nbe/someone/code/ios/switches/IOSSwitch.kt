package nbe.someone.code.ios.switches

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp

@Composable
public fun IOSSwitch(
    checkedState: State<Boolean>,
    onClick: (() -> Unit)?,
    config: IOSSwitchConfig? = null,
) {
    val globalConfig = LocalIOSSwitchConfig.current

    val combineConfig = globalConfig.combine(config)

    val checkedThumbColorRes = combineConfig.checkedThumbColorRes
        ?: throw IOSSwitchException("checkedThumbColorRes")
    val checkedThumbColor = colorResource(checkedThumbColorRes)

    val checkedTrackColorRes = combineConfig.checkedTrackColorRes
        ?: throw IOSSwitchException("checkedTrackColorRes")
    val checkedTrackColor = colorResource(checkedTrackColorRes)

    val normalThumbColorRes = combineConfig.normalThumbColorRes
        ?: throw IOSSwitchException("normalThumbColorRes")
    val normalThumbColor = colorResource(normalThumbColorRes)

    val normalTrackColorRes = combineConfig.normalTrackColorRes
        ?: throw IOSSwitchException("normalTrackColorRes")
    val normalTrackColor = colorResource(normalTrackColorRes)

    val width = combineConfig.width ?: throw IOSSwitchException("width")
    val trackHeight = combineConfig.trackHeight ?: throw IOSSwitchException("trackHeight")
    val trackPadding = combineConfig.trackPadding ?: throw IOSSwitchException("trackPadding")

    IOSSwitch(
        checkedState = checkedState,
        onClick = onClick,
        checkedThumbColor = checkedThumbColor,
        checkedTrackColor = checkedTrackColor,
        normalThumbColor = normalThumbColor,
        normalTrackColor = normalTrackColor,
        width = width,
        trackPadding = trackPadding,
        trackHeight = trackHeight,
    )
}

@Composable
private fun IOSSwitch(
    checkedState: State<Boolean>,
    onClick: (() -> Unit)?,
    checkedThumbColor: Color,
    checkedTrackColor: Color,
    normalThumbColor: Color,
    normalTrackColor: Color,
    width: Dp,
    trackHeight: Dp,
    trackPadding: Dp,
) {
    val checked = checkedState.value

    if (trackHeight <= trackPadding * 2) {
        throw IllegalArgumentException("Track height must be greater than twice the track padding.")
    }

    val thumbSize = trackHeight - trackPadding * 2

    if (width <= trackHeight) {
        throw IllegalArgumentException("Width must be greater than track height.")
    }

    val thumbPaddingStart = remember(checked) {
        if (checkedState.value) {
            width - trackPadding - thumbSize
        } else {
            trackPadding
        }
    }

    val thumbColor = remember(checked) {
        if (checked) checkedThumbColor else normalThumbColor
    }

    val trackColor = remember(checked) {
        if (checked) checkedTrackColor else normalTrackColor
    }

    Box(
        modifier = Modifier
            .width(width)
            .height(trackHeight)
            .clip(CircleShape)
            .clickable(onClick = { onClick?.invoke() }, enabled = onClick != null),
        contentAlignment = Alignment.CenterStart,
    ) {
        val thumbPaddingStartState = animateDpAsState(
            targetValue = thumbPaddingStart,
            animationSpec = tween(),
            label = "ThumbPadding",
        )

        val thumbColorState = animateColorAsState(
            targetValue = thumbColor,
            animationSpec = tween(),
            label = "ThumbColor",
        )

        val trackColorState = animateColorAsState(
            targetValue = trackColor,
            animationSpec = tween(),
            label = "TrackColor",
        )

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(trackColorState.value),
        )

        Spacer(
            modifier = Modifier
                .offset(x = thumbPaddingStartState.value)
                .size(thumbSize)
                .clip(CircleShape)
                .background(thumbColorState.value),
        )
    }
}
