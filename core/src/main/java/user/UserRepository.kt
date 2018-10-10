package user

interface UserRepository {
    fun load(callback: (User?) -> Unit)
    fun save(user: User)
}