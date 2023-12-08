package Single_Cycle
import chisel3 . _
import chisel3 . util . _


class Fetch extends Module {
  val io = IO(new Bundle {
    val pcselect = Input(Bool())
    val aluout = Input(UInt(32.W))
    val ins = Output(UInt(32.W))
    val pcout = Output(UInt(32.W))
    val pcout4 = Output(UInt(32.W))

  })
  val insmem2 = Module(new InstMem("C:/Users/Hamza's Son/Desktop/DSA SEM2 Java project/Scala-Chisel-Learning-Journey/src/main/scala/gcd/Single_Cycle/Imem.txt"))
  val pc = RegInit(0.U(32.W))
  pc := Mux(io.pcselect, (io.aluout), pc + 4.U)

  io.pcout := pc
  io.pcout4:= pc+4.U

  insmem2.io.addr := pc
  io.ins:=insmem2.io.inst




}