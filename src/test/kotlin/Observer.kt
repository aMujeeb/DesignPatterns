import org.junit.jupiter.api.Test
import java.io.File

//Define a subscription mechanism
//Notify multiple objects simultaneously

interface IEventListener {
    fun update(eventType : String?, file : File)
}

class EventManager(vararg operations : String) {
    private var listeners = hashMapOf<String, ArrayList<IEventListener>>()

    init {
        for(operation in operations) {
            listeners[operation] = ArrayList()
        }
    }

    fun subscribe(eventType: String?, listener: IEventListener) {
        val users = listeners[eventType]
        users?.add(listener)
    }

    fun unSubscribe(eventType: String?, listener: IEventListener) {
        val users = listeners[eventType]
        users?.remove(listener)
    }

    fun notify(eventType: String?, file : File?) {
        val users = listeners[eventType]
        users?.let {
            for(listener in it){
                if (file != null) {
                    listener.update(eventType, file)
                }
            }
        }
    }
}

class Editor {
    var events : EventManager = EventManager("open", "save")

    private var file: File? = null

    fun openFile(filePath : String) {
        file = File(filePath)
        events.notify("open", file)
    }

    fun saveFile() {
        if(file != null) {
            events.notify("save", file)
        }
    }
}

class EmailNotificationListener(private val email : String) : IEventListener {
    override fun update(eventType: String?, file: File) {
        println("Email sent to $email Someone has performed $eventType with the file ${file.name}" )
    }
}

class LogOpenListener(private val fileName : String) : IEventListener {
    override fun update(eventType: String?, file: File) {
        println("Save to log $fileName Someone has performed $eventType with the file ${file.name}" )
    }
}

class ObserverTest {
    @Test
    fun testObserver() {
        val editor = Editor()

        val logListener = LogOpenListener("path/log/file.txt")
        val emailListener = EmailNotificationListener("wewewe@efe.com")

        editor.events.subscribe("open", logListener)
        editor.events.subscribe("open", emailListener)
        editor.events.subscribe("save", emailListener)

        editor.openFile("text.file")
        editor.saveFile()
    }
}