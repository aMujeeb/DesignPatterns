import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

//Provide a simple interface to a complex functionality

class ComplexSystemStore(private val filePath : String) {
    private val cache: HashMap<String, String>

    init {
        println("Reading data from fiel : $filePath")
        cache = HashMap()

        //populate cache
    }

    fun store(key : String, value: String) {
        cache[key] = value
    }

    fun read(key : String) = cache[key] ?: ""

    fun commiting() = println("Storing cached data to file $filePath")
}

data class User(val login : String)

class UserRepository {
    private val systemPreferences = ComplexSystemStore("/data/cat/persian/file.txt")

    fun save(user : User) {
        systemPreferences.store("USER_KEY", user.login)
        systemPreferences.commiting()
    }

    fun findFirst() : User = User(systemPreferences.read("USER_KEY"))
}

class TestFacade {

    @Test
    fun testFacade() {
        val userRepo = UserRepository()
        val user = User("pun")
        userRepo.save(user)

        val retrievedUser = userRepo.findFirst()

        Assertions.assertThat(retrievedUser.login).isEqualTo("pun")
    }
}