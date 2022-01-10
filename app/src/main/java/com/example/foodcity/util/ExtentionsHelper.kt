import android.icu.text.SimpleDateFormat
import android.util.Patterns
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


//check emil format (right or wrong )
fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
// Convert time to text
fun Long.toTime(format: String = "hh:mma") =
    SimpleDateFormat(format, Locale.US).format(this).toString()



//A function that checks if the restaurant is open or closed
private val TIME_PARSER = DateTimeFormatter.ofPattern("hh:mma", Locale.ENGLISH)
//"RestaurantDetailsFragment " open or close time of rest:
fun isRestaurantOpen(start: Long, end: Long):Boolean {
    val currentTime = LocalTime.parse(Date().time.toTime(), TIME_PARSER)
    val startTime = LocalTime.parse(start.toTime(), TIME_PARSER)
    val endTime = LocalTime.parse(end.toTime(), TIME_PARSER)
    return !(startTime.isAfter(currentTime) || endTime.isBefore(currentTime))
}