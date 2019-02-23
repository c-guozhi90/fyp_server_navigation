package serverNavigation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import com.alibaba.fastjson.JSON;

/*
 * Implementation of nodes operations, including navigation, updating or retrieving node data
 * */
public class NodesOperations {

	public static HashMap<String, Node> retrieveNodeData() {
		URL reqURL;
		HttpURLConnection connection;
		String JSONString = null;

		try {
			reqURL = new URL("localhost://3000/api/getAllNodes");
			connection = (HttpURLConnection) reqURL.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(60000);
			connection.connect();
			if (connection.getResponseCode() != 200)
				throw new IOException("response code error!");
			BufferedReader bis = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String temp;
			while ((temp = bis.readLine()) != null) {
				JSONString += temp;
			}
			bis.close();
			connection.disconnect();

			ArrayList<Node> nodesList = (ArrayList<Node>) JSON.parseArray(JSONString, Node.class);
			HashMap<String, Node> nodes = new HashMap<>();
			for (int idx = 0; idx < nodesList.size(); idx++) {
				Node node = nodesList.get(idx);
				nodes.put(node.getNodeName(), node);
			}
			return nodes;

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * the client can update the block state of node
	 */
	public static boolean updateNode(String nodeName) {
		// TODO update the block state of nodes both in buffer and database. Return
		// whether the operation was successful
		return true;
	}

	public static void navigation(ClientHandle clientHandle, HashMap<String, Node> nodes, String currentNodeName,
			String targetNodeName, int curDepth) {
		// TODO search the path from current node towards destination
		clientHandle.tempSearchResult[curDepth] = currentNodeName;
		if (currentNodeName.equals(targetNodeName)) {
			if (clientHandle.depth == 0) {
				clientHandle.depth = curDepth;
				clientHandle.copyPath();
			} else if (curDepth < clientHandle.depth) {
				clientHandle.depth = curDepth;
				clientHandle.copyPath();
			}
			return;
		}
		if (curDepth > 20)
			// it is meaningless to get too deep
			return; 

		Node currentNode = nodes.get(currentNodeName);
		String[] connectedNodeNames = currentNode.getConnectedNodeNames();
		for (int idx = 0; idx < connectedNodeNames.length; idx++) {
			Node connectedNode = nodes.get(connectedNodeNames[idx]);
			if (!connectedNode.isBlocked() && !connectedNode.isVisited()) {
				currentNode.setVisited(true);
				navigation(clientHandle, nodes, connectedNodeNames[idx], targetNodeName, curDepth + 1);
			}
		}
		currentNode.setVisited(false);
	}
}
