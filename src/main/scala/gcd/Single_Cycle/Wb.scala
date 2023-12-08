package Single_Cycle
import chisel3 . _
import chisel3 . util . _


class Wb extends Module {
  val io = IO(new Bundle {

    val wbselect = Input(UInt(2.W))
    val Aludatain = Input(UInt(32.W))
    val datamemin = Input(UInt(32.W))
    val dataOut = Output(UInt(32.W))
    val pcin = Input(UInt(32.W))
    val memwrite = Input(Bool())
    val Rd = Input(UInt(5.W))
    val Regwrite = Input(Bool())
    val Rdout = Output(UInt(5.W))
    val Regwriteout = Output(Bool())


  })


  when(io.wbselect === 1.U) {
    io.dataOut := io.Aludatain
  }
    .elsewhen(io.wbselect === 0.U) {
      io.dataOut:= io.datamemin
    }.elsewhen(io.wbselect === 2.U) {
      io.dataOut := (io.pcin + 4.U)
    }

  io.Rdout:= io.Rd
  io.Regwriteout:= io.Regwrite

}