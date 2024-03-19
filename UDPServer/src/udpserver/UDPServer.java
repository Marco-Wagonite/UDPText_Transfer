/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package udpserver;

import java.io.*;
import java.net.*;

/**
 *
 * @author marco
 */
public class UDPServer {
    static final int PORT = 1234;
    private DatagramSocket socket = null;
    public UDPServer() {
        try {
            socket = new DatagramSocket(PORT);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void action() {
        InetAddress host = null;
        int port;
        String chuoi = "";
        
        try {
            System.out.println("Server is listening...");
            while(true) {
                DatagramPacket packet = receive(); //Nhận dữ liệu từ client truyền qua
                host = packet.getAddress(); //Lấy thông tin địa chỉ của client
                port = packet.getPort(); //Lấy thông tin port từ client
                
                chuoi = new String(packet.getData()).trim(); //Lấy dữ liệu của máy client
                chuoi = chuoi.toUpperCase();
                if(!chuoi.equals("")) {
                    send(chuoi, host, port);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            socket.close();
        }
    }
    
    private void send(String chuoi, InetAddress host, int port) throws IOException {
        byte[] buffer = chuoi.getBytes(); //Chuyễn chuỗi thành byte
        
        //Sau đó đưa chuỗi truyền vào gói tin gửi đi
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, host, port);
        socket.send(packet);
    }
    
    private DatagramPacket receive() throws IOException {
        byte[] buffer = new byte[65507]; //Khai báo mảng byte nhận
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return packet;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new UDPServer().action();
    }
}
