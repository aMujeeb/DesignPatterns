import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

interface DataSource

class DataBaseDataSource : DataSource
class NetWorkDataSource : DataSource

abstract class DataSourceFactory {
    abstract fun makeDataSource() : DataSource

    companion object {
        inline fun <reified T : DataSource> createFactory(): DataSourceFactory =
            when (T::class) {
                DataBaseDataSource::class -> DataBaseFactory()
                NetWorkDataSource::class -> NetworkFactory()
                else -> throw IllegalArgumentException()
            }
    }
}

class NetworkFactory : DataSourceFactory() {
    override fun makeDataSource() = NetWorkDataSource()

}

class DataBaseFactory : DataSourceFactory() {
    override fun makeDataSource() = DataBaseDataSource()

}

class AbstractFactoryTest() {
    @Test
    fun abstractFactoryTest() {
        val dataSourceFactory = DataSourceFactory.createFactory<DataBaseDataSource>()
        val dataSource = dataSourceFactory.makeDataSource()

        println("Created Datasource :$dataSource")

        Assertions.assertThat(dataSource).isInstanceOf(DataBaseDataSource::class.java)
    }
}