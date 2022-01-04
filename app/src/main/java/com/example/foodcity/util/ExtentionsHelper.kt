import android.util.Patterns


//check emil format (yes or no )
fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}