package user

interface UserRepository {
    fun load(): User
    fun save(user: User)
}