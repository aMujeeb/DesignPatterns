import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

//Convert the interface of a class into another interface the client needs

data class DisplayDataType(val index: Float, val data: String)

class DataDisplay() {
    fun displayData(data: DisplayDataType) {
        println("Data is Displayed :${data.index}")
    }
}


//Pattern related Code

data class DataBaseData(val position: Int, val amount: Int)
class DataBaseDataGenerator {
    fun generateData(): List<DataBaseData> {
        return listOf(
            DataBaseData(2, 20),
            DataBaseData(3, 17),
            DataBaseData(4, 25)
        )
    }
}

interface IDatabaseDataConverter {
    fun convertData(data: List<DataBaseData>): List<DisplayDataType>
}

class DataDisplayAdapter(val display : DataDisplay) : IDatabaseDataConverter {
    override fun convertData(data: List<DataBaseData>): List<DisplayDataType> {
        return data.map { it ->
            DisplayDataType(it.position.toFloat(), it.amount.toString())
        }
    }
}

class AdapterTest {

    @Test
    fun adapterTest() {
        val generator = DataBaseDataGenerator()
        val generatedData = generator.generateData()
        val adapter = DataDisplayAdapter(DataDisplay())
        val convertData = adapter.convertData(generatedData)

        Assertions.assertThat(convertData.size).isEqualTo(3)
        Assertions.assertThat(convertData[1].data).isEqualTo("17")
    }
}