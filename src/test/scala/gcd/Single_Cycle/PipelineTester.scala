package Single_Cycle
import chisel3._
import org.scalatest._
import chiseltest._

class PipelineTester extends FreeSpec with ChiselScalatestTester {
  "Pipeline Test" in {
    test(new TopCore_5stage) { c =>
      c.clock.step(3)
      c.io.out.expect(2.U)

    }
  }
}