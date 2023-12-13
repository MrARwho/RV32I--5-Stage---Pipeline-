package Single_Cycle
import chisel3 . _
import chisel3 . util . _


class Decode extends Module {
  val io = IO(new Bundle {
    val pcin = Input(UInt(32.W))
    val pcout = Output(UInt(32.W))
    val ins = Input(UInt(32.W))
    val RD = Output(UInt(5.W))
//    val Rs1 = Output(UInt(5.W))
//    val Rs2 = Output(UInt(5.W))
    val Imm = Output(UInt(32.W))
    val Instype = Output(Bool()) //Immidiate / Register select
    val RegWriteout = Output(Bool())
    val MemWrite = Output(Bool())
    val func = Output(UInt(5.W))
    val wbselect = Output(UInt(2.W))
    val aluselect = Output(Bool()) // 1 when S type to perform addition / else 0
    val lengthselect = Output(UInt(2.W)) // store length
    val dobranch = Input(Bool())
    val btypefun = Output(UInt(4.W))
    val pcselec = Output(Bool())
    val btype = Output(Bool())
    val jump = Output(Bool())
    val readmem = Output(Bool())
    //register file
    val RegWritein = Input(Bool())
    val RDin = Input(UInt(5.W))
//    val Rs1in = Input(UInt(5.W))
//    val Rs2in = Input(UInt(5.W))
    val Rs1out = Output(UInt(32.W))
    val Rs2out = Output(UInt(32.W))
    val datain = Input(UInt(32.W))


  })
  io.RD := 0.U
  io.Imm := 0.U
  io.Instype := false.B
  io.RegWriteout := false.B
  io.MemWrite := false.B
  io.func := 0.U
  io.wbselect := 0.U
  io.aluselect := false.B
  io.lengthselect := 0.U
//  io.dobranch := false.B
  io.btypefun := 0.U
  io.pcselec := false.B
  io.btype := false.B
  io.jump := false.B
  io.readmem := false.B
  io.pcout := 0.U
  io.Rs1out := 0.U
  io.Rs2out := 0.U

  val cu = Module (new CU)
  val regfile = Module (new RegisterFile)

  io.pcout := io.pcin
  cu.io.ins := io.ins
  io.RD:= cu.io.RD
  io.RegWriteout:=cu.io.RegWrite
  regfile.io.RD := io.RDin
  regfile.io.Rs2in := cu.io.Rs2
  regfile.io.Rs1in := cu.io.Rs1
  regfile.io.Wen := io.RegWritein


  io.MemWrite := cu.io.MemWrite
  io.func  := cu.io.func
  io.wbselect :=cu.io.wbselect
  io.aluselect := cu.io.aluselect  // 1 when S type to perform addition / else 0
  io.lengthselect := cu.io.lengthselect
  cu.io.dobranch := io.dobranch
  io.btypefun := cu.io.btypefun
  io.pcselec := cu.io.pcselec
  io.btype := cu.io.btype
  io.jump := cu.io.jump
  io.readmem := cu.io.readmem
  io.Imm := cu.io.Imm
  io.Instype := cu.io.Instype

  io.Rs2out := regfile.io.Rs2out
  io.Rs1out := regfile.io.Rs1out
  regfile.io.datain := io.datain


}