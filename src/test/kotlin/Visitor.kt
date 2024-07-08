import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

//Separation between an algorithm and the object they operate on
//2 concepts: Visitor and element(visitable)
//the Element accepts visitor type objects

interface ReportElement {
    fun <R> accept(visitor : ReportVisitor<R>) : R
}

class FixedPriceContract(val costPerYear : Long) : ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class TimeAndMaterialContract(val costPerHour : Long, val hours : Long) : ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class SupportContract(val costPerMonth : Long, val hours : Long) : ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

interface ReportVisitor<out R> {
    fun visit(contract :  FixedPriceContract) : R
    fun visit(contract :  TimeAndMaterialContract) : R
    fun visit(contract :  SupportContract) : R
}

class MonthlyCostReportVisitor : ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear /12

    override fun visit(contract: TimeAndMaterialContract): Long = contract.costPerHour * contract.hours

    override fun visit(contract: SupportContract): Long = contract.costPerMonth
}

class YearlyReportVisitor : ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear

    override fun visit(contract: TimeAndMaterialContract): Long = contract.costPerHour * contract.hours

    override fun visit(contract: SupportContract): Long = contract.costPerMonth * 12
}

class VisitorTest {
    @Test
    fun testVisitor() {
        val projectAlpha = FixedPriceContract(10000)
        val projectBeta = SupportContract(5000, 100)
        val projectGamma = TimeAndMaterialContract(50, 10)
        val projectKappa = TimeAndMaterialContract(50, 10)

        val projects = arrayListOf(projectAlpha, projectBeta, projectGamma, projectKappa)

        val monthlyVisitor = MonthlyCostReportVisitor()
        val monthlyCost = projects.sumOf {
            it.accept(monthlyVisitor)
        }
        println("Monthly Cost: $monthlyCost")
        Assertions.assertThat(monthlyCost).isEqualTo(6833)

        val yearlyVisitor = YearlyReportVisitor()
        val yearlyCost = projects.sumOf {
            it.accept(yearlyVisitor)
        }
        println("Monthly Cost: $yearlyCost")
        Assertions.assertThat(yearlyCost).isEqualTo(71000)
    }
}