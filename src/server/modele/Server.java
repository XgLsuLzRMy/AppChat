package server.modele;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(1099);
			while (true) {
				System.out.println("En attente de connexion");
				Socket socket = server.accept();
				System.out.println("Connexion avec "+socket.getInetAddress());
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				System.out.println(reader.readLine());
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
