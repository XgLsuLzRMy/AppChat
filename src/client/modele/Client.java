package client.modele;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		Socket socket = null;
		try {
			InetAddress serveur = InetAddress.getByName(args[0]);
			socket = new Socket(serveur, 1099); // port 1099
			PrintStream out = new PrintStream(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("texte a envoyer");
			String str = reader.readLine();
			out.print(str);
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
