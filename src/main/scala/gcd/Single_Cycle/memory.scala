package Single_Cycle

import chisel3._
import chisel3.util._
import firrtl.PrimOps.IncP
class memory extends Module {
  val io = IO(new Bundle {
    val Wen = Input(Bool())
    val addr = Input(UInt(32.W)) // ALU in data
    val datain = Input(UInt(32.W)) //RS2 data
    val dmemdataout = Output(UInt(32.W))
    val aludataout = Output(UInt(32.W))
    val fun3 = Input(UInt(3.W)) //lengthselect
    //val enable = Input(Bool())
    //val instype = Input(UInt(2.W))
    val wbselectin = Input(UInt(2.W))
    val wbselectout = Output(UInt(2.W))
    val readmem = Input(Bool())
    val pcin = Input(UInt(32.W))
    val pcout = Output(UInt(32.W))
    val regwritein = Input(Bool())
    val regwriteout = Output(Bool())
    val RDin = Input(UInt(5.W))
    val RDOut = Output(UInt(5.W))
    val pcselecin = Input(Bool())
    val pcselecout = Output(Bool())



  })

  val datamem = Module(new Datamem)

  io.pcout := io.pcin
  io.regwriteout:=io.regwritein
  io.RDOut:= io.RDin
  io.pcselecout := io.pcselecin

  datamem.io.datain := io.datain
  datamem.io.Wen := io.Wen
  datamem.io.addr := io.addr
  datamem.io.fun3 := io.fun3
  datamem.io.enable := io.readmem
  io.dmemdataout:= datamem.io.dataout
  io.aludataout := io.addr

  io.wbselectout := io.wbselectin




  //io.dataout := Mux(io.readmem,datamem.io.dataout,io.datain)

}



