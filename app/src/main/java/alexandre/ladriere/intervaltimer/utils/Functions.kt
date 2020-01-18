package alexandre.ladriere.intervaltimer.utils

/**
 * Return a pair (minutes, seconds) in int format from a string of type "xx:yy", where xx are the minutes and yy the seconds
 */
fun getTimeFromStr(timeStr: String): Pair<Int, Int> {
    val tmpList = timeStr.split(":")
    val minutes = tmpList[0].toInt()
    val seconds = tmpList[1].toInt()
    return Pair(minutes, seconds)
}

/**
 * Convert seconds to minutes/seconds (ex: 80 will return (1, 20))
 */
fun convertSecondsToMinutes(sec: Int): Pair<Int, Int> {
    val minutes = sec / 60
    val seconds = sec % 60
    return Pair(minutes, seconds)
}

/**
 * Convert minutes/seconds to seconds (ex: (1, 20) will return 80)
 */
fun convertMinutesToSeconds(min: Int, sec: Int): Int {
    return min * 60 + sec
}