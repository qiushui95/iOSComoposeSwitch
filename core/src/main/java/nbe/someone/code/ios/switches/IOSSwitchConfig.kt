package nbe.someone.code.ios.switches

import androidx.annotation.ColorRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.runtime.remember as remember2

@Stable
public class IOSSwitchConfig(
    internal val width: Dp? = null,
    internal val trackHeight: Dp? = null,
    internal val trackPadding: Dp? = null,
    @ColorRes internal val checkedThumbColorRes: Int? = null,
    @ColorRes internal val checkedTrackColorRes: Int? = null,
    @ColorRes internal val normalThumbColorRes: Int? = null,
    @ColorRes internal val normalTrackColorRes: Int? = null,
) {
    public companion object {
        @Composable
        public fun remember(
            width: Dp? = null,
            trackHeight: Dp? = null,
            trackPadding: Dp? = null,
            checkedThumbColorRes: Int? = null,
            checkedTrackColorRes: Int? = null,
            normalThumbColorRes: Int? = null,
            normalTrackColorRes: Int? = null,
        ): IOSSwitchConfig = remember2(
            width,
            trackPadding,
            trackHeight,
            checkedThumbColorRes,
            checkedTrackColorRes,
            normalThumbColorRes,
            normalTrackColorRes,
        ) {
            IOSSwitchConfig(
                width = width,
                trackHeight = trackHeight,
                trackPadding = trackPadding,
                checkedThumbColorRes = checkedThumbColorRes,
                checkedTrackColorRes = checkedTrackColorRes,
                normalThumbColorRes = normalThumbColorRes,
                normalTrackColorRes = normalTrackColorRes,
            )
        }
    }

    internal fun combine(other: IOSSwitchConfig?): IOSSwitchConfig = IOSSwitchConfig(
        width = other?.width ?: width,
        trackHeight = other?.trackHeight ?: trackHeight,
        trackPadding = other?.trackPadding ?: trackPadding,
        checkedThumbColorRes = other?.checkedThumbColorRes ?: checkedThumbColorRes,
        checkedTrackColorRes = other?.checkedTrackColorRes ?: checkedTrackColorRes,
        normalThumbColorRes = other?.normalThumbColorRes ?: normalThumbColorRes,
        normalTrackColorRes = other?.normalTrackColorRes ?: normalTrackColorRes,
    )
}
