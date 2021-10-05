/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ltm_thitracnghiem;

import com.fasterxml.jackson.databind.ObjectMapper;
import object.SqlAuth;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import object.CauHoi;
import object.Diem;
import object.SinhVien;

/**
 *
 * @author ngdanghau
 */
public class Server {
    
    private static DatagramPacket getData(DatagramSocket socket) throws IOException{
        byte[] dataPacket = new byte[1000];
        DatagramPacket packet = new DatagramPacket(dataPacket, dataPacket.length); 
        socket.receive(packet);
        return packet;
    }
    
    private static DatagramPacket sendData(String reply, InetAddress inet, int port, DatagramSocket socket) throws IOException{
        byte[] replyByte = reply.getBytes();
        DatagramPacket replyPacket = new DatagramPacket(replyByte, replyByte.length, inet, port);
        socket.send(replyPacket);
        return replyPacket;
    }
    
    public static void main(String[] args) throws SocketException, IOException {
        InetAddress inet = InetAddress.getByName("localhost"); 
        int port = 8888;
        DatagramSocket socket = new DatagramSocket(port, inet); 
        System.out.println("Server dang chay!!!");

        byte[] dataPacket; 
        DatagramPacket packet;
        String line;
        ByteArrayInputStream in;
        ObjectInputStream is;
        DBAccess db = null;
        ObjectMapper mapper = new ObjectMapper();
        int cau = 1;
        int diem = 0;
        CauHoi question = null;
        SinhVien sv = new SinhVien();
                    
        OUTER:
        while (true) {
            dataPacket = new byte[1000];
            packet = new DatagramPacket(dataPacket, dataPacket.length); 
            socket.receive(packet);
            line = new String(packet.getData(), 0, packet.getLength());
            switch (line) {
                case "connectSQL":
                    cau = 1;
                    diem = 0;
                    packet = getData(socket);
                    in = new ByteArrayInputStream(packet.getData());
                    is = new ObjectInputStream(in);
                    
                    try {
                        SqlAuth sql = (SqlAuth) is.readObject();
                        db = new DBAccess();
                        db.connect("jdbc:sqlserver://"+sql.getIp()+":"+sql.getPort()+";Database=LTM_GK;user="+sql.getUser()+";password=" + sql.getPass());
                        sendData("success", packet.getAddress(), packet.getPort(), socket);
                    } catch (ClassNotFoundException | SQLException ex) {
                        sendData(ex.getMessage(), packet.getAddress(), packet.getPort(), socket);
                    } 
                    break;
                case "laycauhoi":
                    try {
                        ResultSet rs = db.Query("SELECT TOP 1 * FROM BODE order by newid()");
                        while (rs.next()) {
                            question = new CauHoi();
                            question.setMacauhoi(rs.getInt("CAUHOI"));
                            question.setNoidung(rs.getString("NOIDUNG"));
                            question.setA(rs.getString("A"));
                            question.setB(rs.getString("B"));
                            question.setC(rs.getString("C"));
                            question.setD(rs.getString("D"));
                        }
                        sendData(mapper.writeValueAsString(question), packet.getAddress(), packet.getPort(), socket);
                    } catch (SQLException ex) {
                        sendData(ex.getMessage(), packet.getAddress(), packet.getPort(), socket);
                    }
                    break;
                case "kiemtradapan":
                    String ketqua = "";
                    packet = getData(socket);
                    int maCauHoi = question.getMacauhoi();
                    String cautraloi = new String(packet.getData(), 0, packet.getLength());
                    try {
                        ResultSet rs = db.Query("SELECT DAP_AN FROM BODE WHERE cauhoi = " + maCauHoi);
                        while (rs.next()) {
                            if(rs.getString("DAP_AN").trim().equals(cautraloi)){
                                diem += 1; 
                            }
                            if(cau == 10){
                                Date date = new Date();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
                                String dateWithSeconds = simpleDateFormat.format(date);
                                db.Update("INSERT INTO DIEM (MASV, NGAYTHI, DIEM) VALUES ('"+sv.getMaSV()+"', '"+dateWithSeconds+"' ,"+diem+")");
                                ketqua = "success|Điểm tổng kết là: "+diem;
                                System.out.println(diem);
                                diem = 0;
                                cau = 1;
                                break;
                            }
                            cau += 1;
                            ketqua = "information|" + cau;
                        }
                        sendData(ketqua, packet.getAddress(), packet.getPort(), socket);
                    } catch (SQLException ex) {
                        sendData(ex.getMessage(), packet.getAddress(), packet.getPort(), socket);
                    }
                    break;
                case "laydiemthi":
                    try {
                        ArrayList<Diem> list = new ArrayList<Diem>();
                        ResultSet rs = db.Query("SELECT * FROM DIEM WHERE MASV = '" + sv.getMaSV() + "'");
                        while( rs.next() )
                        {
                            Diem item = new Diem();
                            item.setMaSV(rs.getString("MASV"));
                            item.setNgaythi(rs.getString("NGAYTHI"));
                            item.setDiem(rs.getFloat("DIEM"));
                            list.add(item);
                        }
                        sendData(mapper.writeValueAsString(list), packet.getAddress(), packet.getPort(), socket);
                    } catch (SQLException ex) {
                        sendData(ex.getMessage(), packet.getAddress(), packet.getPort(), socket);
                    }
                    
                    break;
                case "dangnhap": // @author PHONG
                    packet = getData(socket);
                    // xu lieu du lieu duoc nhan
                    String duLieuNhanDuoc = new String( packet.getData(), 0, packet.getLength() );//    1#this game is over
                    String[] mangDuLieu = duLieuNhanDuoc.split("#");
                    
                    String taiKhoan = mangDuLieu[0];
                    String matKhau = mangDuLieu[1];
                    
                    try {
                        ResultSet rs = db.Query("SELECT * FROM SINHVIEN WHERE UserName = '" + taiKhoan + "' AND PassWord = '" + matKhau + "'");
                        if( rs.next() )
                        {
                            sv.setMaSV(rs.getString("MASV"));
                            sv.setHo(rs.getString("HO"));
                            sv.setTen(rs.getString("TEN"));
                            sv.setSdt(rs.getString("SODIENTHOAI"));
                            sendData(mapper.writeValueAsString(sv), packet.getAddress(), packet.getPort(), socket);
                        }else{
                            sendData("Tài khoản đăng nhập không tồn tại", packet.getAddress(), packet.getPort(), socket);
                        }
                    } catch (SQLException ex) {
                        sendData(ex.getMessage(), packet.getAddress(), packet.getPort(), socket);
                    }
                    break;
                case "dangky":// @author PHONG
                    packet = getData(socket);
                    
                     // xu lieu du lieu duoc nhan
                    String duLieuNhanDuocTuDangKy = new String( packet.getData(), 0, packet.getLength() );
                    String[] mangDuLieuNhanDuoc = duLieuNhanDuocTuDangKy.split("#");
                    
                    // lay du lieu ra ngoai
                    String maSinhVien = mangDuLieuNhanDuoc[0];
                    String ho = mangDuLieuNhanDuoc[1];
                    String ten = mangDuLieuNhanDuoc[2];
                    String soDienThoai = mangDuLieuNhanDuoc[3];
                    String matKhauDangKy = mangDuLieuNhanDuoc[4];
                    String matKhauXacNhan = mangDuLieuNhanDuoc[5];
                    
                    if(!matKhauDangKy.equals(matKhauXacNhan)){
                        sendData("not_match", packet.getAddress(), packet.getPort(), socket);
                        break;
                    }
                    try {
                        //kiem tra xem ma nhan vien co bi trung - neu trung thi dung lai luon
                        ResultSet rs = db.Query("SELECT * FROM SINHVIEN WHERE MASV = '" + maSinhVien + "'");
                        if( rs.next() )
                        {
                            sendData("duplicate_masv", packet.getAddress(), packet.getPort(), socket);
                            break;
                        }
                    } catch (SQLException ex) {
                        sendData(ex.getMessage(), packet.getAddress(), packet.getPort(), socket);
                    }
                    
                    try {
                        //kiem tra xem ma nhan vien co bi trung - neu trung thi dung lai luon
                        ResultSet rs = db.Query("SELECT * FROM SINHVIEN WHERE SODIENTHOAI = '" + soDienThoai +"'");
                        if( rs.next() )
                        {
                            sendData("duplicate_sdt", packet.getAddress(), packet.getPort(), socket);
                            break;
                        }
                    } catch (SQLException ex) {
                        sendData(ex.getMessage(), packet.getAddress(), packet.getPort(), socket);
                    }
                     
                    // neu ma sinh vien khong bi trung thi tiep tuc them moi vao co so du lieu
                    String cauTruyVan = "INSERT INTO SINHVIEN( MASV, HO, TEN, SODIENTHOAI, USERNAME, PASSWORD) "
                        + "VALUES( '" + maSinhVien + "' , '"+ho+"', '"+ten+"', '"+ soDienThoai +"', '"+ maSinhVien +"' , '"+matKhauDangKy+"')";
                    db.Query(cauTruyVan);
                    sendData("success", packet.getAddress(), packet.getPort(), socket);
                    break;
                case "QUIT":
                    break OUTER;
            }
        }
        
        System.out.println("Server da dung lai!!");
        socket.close();
    }
}
