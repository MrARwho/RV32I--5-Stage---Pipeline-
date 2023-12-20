package Single_Cycle
import chisel3 . _
import chisel3 . util . _
import chisel3 . util . experimental . loadMemoryFromFile
import scala . io .Source._


class SyncInsMem ( initFile : String ) extends Module {
  val io = IO(new Bundle {
    val addr = Decoupled(UInt(32.W))
    val inst = Flipped(Decoupled(UInt(32.W)))
  })

  val imem = SyncReadMem(1024, UInt(32.W))
  loadMemoryFromFile(imem, initFile)
  io.inst := imem(io.addr >> 2)

}