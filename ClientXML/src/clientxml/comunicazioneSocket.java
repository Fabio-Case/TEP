/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxml;

/**
 *
 * @author Fabio
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class comunicazioneSocket {
    private BufferedReader in;
    private BufferedWriter bw;
    private PrintWriter out;
    private List<String> input;

    public comunicazioneSocket(int port) throws UnknownHostException, IOException {
        InetAddress addr = InetAddress.getLocalHost();
        Socket socket = new Socket(addr, port);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out = new PrintWriter(bw, true);
        input = new ArrayList<>();
    }

    public String invia(String s) throws IOException {
        String str = "";
        
        out.println(str);
        s = in.readLine();
        return s;
    }
}
