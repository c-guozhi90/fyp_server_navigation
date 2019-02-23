package serverNavigation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/*
 * logic flow and operations handling for server
 * */
public class ServerControl {

	public static HashMap<String, Node> nodes = NodesOperations.retrieveNodeData();
	private static ServerSocket server;

	public static void main(String[] args) {
		if (nodes == null)
			System.exit(1);

		while (true) {
			try {
				server = new ServerSocket(3001);
				Socket client = server.accept();
				ClientHandle newOperation = new ClientHandle(client);
				newOperation.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
