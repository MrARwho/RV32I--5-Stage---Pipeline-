package Single_Cycle
import chisel3._
import org.scalatest._
import chiseltest._

class PipelineTester extends FreeSpec with ChiselScalatestTester {
  "Pipeline Test" in {
    test(new TopCore_5stage) { c =>
      c.clock.step()
      c.io.executeout.expect(0.U)
      c.io.memout.expect(0.U)
      c.io.fetchout.expect("b0".U)
    }
  }
}