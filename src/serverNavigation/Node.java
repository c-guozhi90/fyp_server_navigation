package serverNavigation;

public class Node {
	private String nodeName;
	private float[] coordinates; // [x,y,z]
	private boolean blocked;
	private String[] connectedNodeNames;
	private boolean visited = false;

	public Node(String nodeName, float[] coordinates, boolean blocked, String[] connectedNode) {
		super();
		this.nodeName = nodeName;
		this.coordinates = coordinates;
		this.blocked = blocked;
		this.connectedNodeNames = connectedNode;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public float[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(float[] coordinates) {
		this.coordinates = coordinates;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public String[] getConnectedNodeNames() {
		return connectedNodeNames;
	}

	public void setConnectedNode(String[] connectedNode) {
		this.connectedNodeNames = connectedNode;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

}
