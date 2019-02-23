package serverNavigation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/*
 * logic flow and operations handling for clients
 * */
public class ClientHandle extends Thread {

	public static final int BLOCK_OPERRATION = 1;
	private Socket client;
	private int operation;
	private String currentNode;
	private String targetNode;

	public ClientHandle(Socket client) throws IOException {
		this.client = client;
		DataInputStream dis = new DataInputStream(client.getInputStream());
		this.operation = dis.readInt();
		this.currentNode = dis.readUTF();
		this.targetNode = dis.readUTF();
		dis.close();
	}

	@Override
	public void run() {
		if (operation == ClientHandle.BLOCK_OPERRATION) {
			blockNode();
		} else {
			try {
				searchPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean blockNode() {

		NodesOperations.updateNode(currentNode);
		return true;
	}

	// for convenience
	public String[] searchResult = new String[1000];
	public String[] tempSearchResult = new String[1000];
	public int depth = 0;
	public int curDepth = 0;

	public void searchPath() throws IOException {
		if (!currentNode.equals(targetNode)) {
			HashMap<String, Node> nodes = (HashMap<String, Node>) ServerControl.nodes.clone();
			NodesOperations.navigation(this, nodes, currentNode, targetNode, 0);
		}
		if (searchResult[depth].equals(targetNode) /* varify if the destination was found */ ) {
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			for (int idx = 0; idx < depth; idx++) {
				dos.writeUTF(searchResult[idx]);
			}
			dos.close();
		}
	}

	public void copyPath() {
		for (int idx = 0; idx < depth; idx++) {
			searchResult[idx] = tempSearchResult[idx];
		}
	}
}
