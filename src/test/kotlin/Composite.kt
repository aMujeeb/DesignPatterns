import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

//Compose objects into Tree structures
//Works when the core functionality can be represented as a tree
//Manipulate many objects as a single on Eg : a Computer is consists of a lot of objects

open class Equipment (
    open val price : Int,
    val name : String
)


open class Composite(name: String) : Equipment(0, name) {
    private val equipments = ArrayList<Equipment>()

    override val price: Int
        get() = equipments.sumOf { it.price }

    fun addEquipment(equipment: Equipment) = apply {
        equipments.add(equipment)
    }
}

class Computer : Composite("PC")
class Processor : Equipment(1000, "Processor")
class HardDrive : Equipment(1400, "HardDrive")
class Memory : Equipment(1200, "Memory")

class TestComposite {
    @Test
    fun testComposite() {
        val computer = Computer()
        val processor = Processor()
        val hardDrive = HardDrive()
        val memory = Memory()
        computer.addEquipment(processor).addEquipment(hardDrive).addEquipment(memory)

        Assertions.assertThat(computer.price).isEqualTo(3600)
    }
}