package user

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPreferencesUserRepository(private val context: Context) : UserRepository {

    private val preferences = context.getSharedPreferences("User", MODE_PRIVATE)
    private val id = "user_id_key"
    private val email = "user_email_key"
    private val name = "user_name_key"
    private val photo = "user_photoURL_key"

    override fun load(callback: (User?) -> Unit) {
        val id = preferences.getString(id, "")
        val email = preferences.getString(email, "")
        val name = preferences.getString(name, "")
        val photo = preferences.getString(photo, "")
        val user = User(id, email, name, photo)
        callback(user)
    }

    override fun save(user: User) {
        with (preferences.edit()) {
            putString(id, user.id)
            putString(email, user.email)
            putString(name, user.name)
            putString(photo, user.photoURL)
            commit()
        }
    }
}