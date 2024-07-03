package nbe.someone.code.ios.switches

private fun buildErrorMessage(configKey: String): String {
    val sb = StringBuilder()

    sb.append("未发现${configKey}的配置值")
    sb.append(",请通过IOSSwitch(State<Boolean>,(() -> Unit)?,IOSSwitchConfig?)传入配置")
    sb.append(",或者在LocalIOSSwitchConfig中提供默认值")

    return sb.toString()
}

public class IOSSwitchException(configKey: String) : Throwable(buildErrorMessage(configKey))
