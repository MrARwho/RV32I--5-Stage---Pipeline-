package Single_Cycle
import chisel3._
import org.scalatest._
import chiseltest._

class PipelineTester extends FreeSpec with ChiselScalatestTester {
  "Pipeline Test" in {
    test(new TopCore_5stage) { c =>
      c.clock.step(3)
      c.io.executeout.expect(3.U)
      c.io.memout.expect(2.U)
      c.io.fetchout.expect("b00000000000000000000001010010011".U)


    }
  }
}