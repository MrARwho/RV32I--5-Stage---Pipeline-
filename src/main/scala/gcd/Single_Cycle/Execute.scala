package Single_Cycle
import chisel3 . _
import chisel3 . util . _
import lab_4.ALU1



trait Config{
  val WLEN = 32
  val ALUOP_SIG_LEN = 4
}

class Execute extends Module with Config {
  val io = IO(new Bundle {
    val in_A = Input(UInt(WLEN.W))
    val in_B = Input(UInt(WLEN.W))
    //val alu_Op = Input(UInt(ALUOP_SIG_LEN.W))
    val out = Output(UInt(WLEN.W))
//    val in_A = Input(UInt(32.W))
//    val in_B = Input(UInt(32.W))
    val fun3 = Input(UInt(4.W)) // btypefunc in
//    val doBranch = Output(Bool())
    val isBtype = Input(Bool())
    val pcin = Input(UInt(32.W))
    val pcout = Output(UInt(32.W))
    //val sum = Output(UInt(WLEN.W))

    //inputs from decode forwarded
    val Imm = Input(UInt(32.W))
    val Instype = Input(Bool()) //Immidiate / Register select
    val RegWriteout = Output(Bool())
    val RegWritein = Input(Bool())
    val MemWritein = Input(Bool())
    val MemWriteout = Output(Bool())
    val funcin = Input(UInt(5.W))
    val wbselectin = Input(UInt(2.W))
    val wbselectout = Output(UInt(2.W))
    val aluselect = Input(Bool()) // 1 when S type to perform addition / else 0
    val lengthselectin = Input(UInt(2.W)) //select store length
    val lengthselectout = Output(UInt(2.W))

    val pcselec = Input(Bool())
    val pcselecout = Output(Bool())
    //val btype = Input(Bool())
    val jump = Input(Bool())
    val readmemin = Input(Bool())
    val readmemout = Output(Bool())

    val RDin = Input(UInt(5.W))
    val RDout = Output(UInt(5.W))
    val in_Bout = Output(UInt(WLEN.W))

    val fwd_a_sel = Input(UInt(2.W))
    val fwd_b_sel = Input(UInt(2.W))

    val exec_Din = Input(UInt(32.W))
    val mem_wb_Din = Input(UInt(32.W))




  })
  //io.doBranch := false.B
  io.pcout := 0.U
  io.RegWriteout := false.B
  io.MemWriteout := false.B
  io.lengthselectout := 0.U
  io.readmemout := false.B
  io.wbselectout := 0.U
  //io.RDout := 0.U
  io.in_Bout := 0.U

  io.pcselecout := io.pcselec
  io.in_Bout:= io.in_B

  io.RDout:=io.RDin
  io.RegWriteout:=io.RegWritein

  io.wbselectout := io.wbselectin
  io.MemWriteout := io.MemWritein
  io.lengthselectout := io.lengthselectin
  io.readmemout := io.readmemin
  io.pcout := io.pcin





  val Alu = Module(new ALU1)
  val BALU = Module(new BranchALU)

  io.in_A :=
  io.in_B := MuxCase(io.in_B, Array(
    (io.fwd_a_sel === 1.U) -> io.exec_Din,
    (io.fwd_a_sel === 2.U) -> io.mem_wb_Din)))

  Alu.io.in_A := Mux(BALU.io.doBranch || io.jump, io.pcin, MuxCase(io.in_A, Array(
    (io.fwd_a_sel === 1.U) -> io.exec_Din,
    (io.fwd_a_sel === 2.U) -> io.mem_wb_Din)))
  Alu.io.in_B := Mux(!io.Instype, io.Imm, MuxCase(io.in_B, Array(
    (io.fwd_b_sel === 1.U) -> io.exec_Din,
    (io.fwd_b_sel === 2.U) -> io.mem_wb_Din)))

//  Alu.io.in_A := io.in_A
//  Alu.io.in_B := io.in_B
  BALU.io.in_A := io.in_A
  BALU.io.in_B := io.in_B


  //Alu.io.alu_Op := io.alu_Op
  Alu.io.alu_Op := Mux(io.aluselect,0.U,io.funcin)
  BALU.io.fun3 := io.fun3
  io.out := Alu.io.out
  //io.doBranch := BALU.io.doBranch
  BALU.io.isBtype := io.isBtype


}
