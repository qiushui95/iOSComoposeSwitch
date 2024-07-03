package nbe.someone.code.ios.switches

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

public val LocalIOSSwitchConfig: ProvidableCompositionLocal<IOSSwitchConfig> = compositionLocalOf {
    error("No IOSSwitchConfig provided")
}
