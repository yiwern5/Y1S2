import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.PriorityQueue;
public class MazeSolver implements IMazeSolver {
	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int EMPTY_SPACE = 0;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(
			Room::getNorthWall,
			Room::getEastWall,
			Room::getWestWall,
			Room::getSouthWall
	);
	private static final int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 0, 1 }, // East
			{ 0, -1 }, // West
			{ 1, 0 } // South
	};
	static class queueNode implements Comparable<queueNode> {
		int row;
		int col;
		int currDist;
		public queueNode(int row, int col) {
			this.row = row;
			this.col = col;
		}
		@Override
		public int compareTo(queueNode node) {
			if (this.currDist < node.currDist) {
				return -1;
			} else if (this.currDist > node.currDist) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	class nodeComparator implements Comparator<queueNode> {
		public nodeComparator() {
		}
		@Override
		public int compare(queueNode node1, queueNode node2) {
			return Integer.compare(node1.currDist, node2.currDist);
		}
	}
	private Maze maze;
	boolean solved;
	boolean[][] visited;
	PriorityQueue<queueNode> queue;
	int[][] distOfNodes;
	int endRow;
	int endCol;
	int sRow;
	int sCol;
	public MazeSolver() {
		// TODO: Initialize variables.
		this.maze = null;
		this.solved = false;
	}
	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		this.solved = false;
		this.visited = new boolean[this.maze.getRows()][this.maze.getColumns()];
		this.queue = new PriorityQueue<>(new nodeComparator());
		this.distOfNodes = new int[this.maze.getRows()][this.maze.getColumns()];
	}
	//Dijkstra's algorithm
	public void findMin(queueNode source) {
		source.currDist = 0;
		distOfNodes[source.row][source.col] = 0;
		this.queue.add(source);
		visited[source.row][source.col] = true;
		while(!queue.isEmpty()) {
			queueNode leastDistNode = queue.remove();
			if (leastDistNode.row == this.endRow && leastDistNode.col == this.endCol) {
				this.solved = true;
			}
			neighbours(leastDistNode);
		}
	}
	public void findMinBonus(queueNode source) {
		source.currDist = 0;
		distOfNodes[source.row][source.col] = 0;
		this.queue.add(source);
		visited[source.row][source.col] = true;
		while(!queue.isEmpty()) {
			queueNode leastDistNode = queue.remove();
			if (leastDistNode.row == this.endRow && leastDistNode.col == this.endCol) {
				this.solved = true;
			}
			neighboursBonus(leastDistNode);
		}
	}
	public void neighbours(queueNode node) {
		int edgeDistance;
		int currRow = node.row;
		int currCol = node.col;
		visited[currRow][currCol] = true;
		Room currRoom = this.maze.getRoom(currRow, currCol);
		if (currRoom.getNorthWall() != TRUE_WALL && !visited[currRow + DELTAS[0][0]][currCol + DELTAS[0][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[0][0]][currCol + DELTAS[0][1]];
			edgeDistance = currRoom.getNorthWall();
			if (edgeDistance == 0) {
				edgeDistance++;
			}
			queueNode currentNode = new queueNode(currRow + DELTAS[0][0], currCol + DELTAS[0][1]);
			if (node.currDist + edgeDistance < currNodeDistance) {
				if (queue.contains(currentNode)) {
					currentNode.currDist = node.currDist + edgeDistance;
					distOfNodes[currentNode.row][currentNode.col] = node.currDist + edgeDistance;
				} else {
					queueNode newNode = new queueNode(currRow + DELTAS[0][0], currCol + DELTAS[0][1]);
					newNode.currDist = node.currDist + edgeDistance;
					distOfNodes[newNode.row][newNode.col] = node.currDist + edgeDistance;
					this.queue.add(newNode);
				}
			}
		}
		if (currRoom.getEastWall() != TRUE_WALL && !visited[currRow + DELTAS[1][0]][currCol + DELTAS[1][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[1][0]][currCol + DELTAS[1][1]];
			edgeDistance = currRoom.getEastWall();
			if (edgeDistance == 0) {
				edgeDistance++;
			}
			queueNode currentNode = new queueNode(currRow + DELTAS[1][0], currCol + DELTAS[1][1]);
			if (node.currDist + edgeDistance < currNodeDistance) {
				if (queue.contains(currentNode)) {
					currentNode.currDist = node.currDist + edgeDistance;
					distOfNodes[currentNode.row][currentNode.col] = node.currDist + edgeDistance;
				} else {
					queueNode newNode = new queueNode(currRow + DELTAS[1][0], currCol + DELTAS[1][1]);
					newNode.currDist = node.currDist + edgeDistance;
					distOfNodes[newNode.row][newNode.col] = node.currDist + edgeDistance;
					this.queue.add(newNode);
				}
			}
		}
		if (currRoom.getWestWall() != TRUE_WALL && !visited[currRow + DELTAS[2][0]][currCol + DELTAS[2][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[2][0]][currCol + DELTAS[2][1]];
			edgeDistance = currRoom.getWestWall();
			if (edgeDistance == 0) {
				edgeDistance++;
			}
			queueNode currentNode = new queueNode(currRow + DELTAS[2][0], currCol + DELTAS[2][1]);
			if (node.currDist + edgeDistance < currNodeDistance) {
				if (queue.contains(currentNode)) {
					currentNode.currDist = node.currDist + edgeDistance;
					distOfNodes[currentNode.row][currentNode.col] = node.currDist + edgeDistance;
				} else {
					queueNode newNode = new queueNode(currRow + DELTAS[2][0], currCol + DELTAS[2][1]);
					newNode.currDist = node.currDist + edgeDistance;
					distOfNodes[newNode.row][newNode.col] = node.currDist + edgeDistance;
					this.queue.add(newNode);
				}
			}
		}
		if (currRoom.getSouthWall() != TRUE_WALL && !visited[currRow + DELTAS[3][0]][currCol + DELTAS[3][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[3][0]][currCol + DELTAS[3][1]];
			edgeDistance = currRoom.getSouthWall();
			if (edgeDistance == 0) {
				edgeDistance++;
			}
			queueNode currentNode = new queueNode(currRow + DELTAS[3][0], currCol + DELTAS[3][1]);
			if (node.currDist + edgeDistance < currNodeDistance) {
				if (queue.contains(currentNode)) {
					currentNode.currDist = node.currDist + edgeDistance;
					distOfNodes[currentNode.row][currentNode.col] = node.currDist + edgeDistance;
				} else {
					queueNode newNode = new queueNode(currRow + DELTAS[3][0], currCol + DELTAS[3][1]);
					newNode.currDist = node.currDist + edgeDistance;
					distOfNodes[newNode.row][newNode.col] = node.currDist + edgeDistance;
					this.queue.add(newNode);
				}
			}
		}
	}
	
	public void neighboursBonus(queueNode node) {
		int edgeDistance;
		int currRow = node.row;
		int currCol = node.col;
		visited[currRow][currCol] = true;
		Room currRoom = this.maze.getRoom(currRow, currCol);
		if (currRoom.getNorthWall() != Integer.MAX_VALUE && !visited[currRow + DELTAS[0][0]][currCol + DELTAS[0][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[0][0]][currCol + DELTAS[0][1]];
			edgeDistance = currRoom.getNorthWall();
			if (edgeDistance == 0) {
				edgeDistance++;
			}
			
			queueNode currentNode = new queueNode(currRow + DELTAS[1][0], currCol + DELTAS[1][1]);
			int expectedDist;
			if (currentNode.row == sRow && currentNode.col == sCol) {
				expectedDist = -1;
			} else if (edgeDistance == 1) {
				expectedDist = node.currDist + 1;
			} else if (node.currDist < edgeDistance) {
				expectedDist = edgeDistance;
			} else {
				expectedDist = node.currDist;
			}
			if (expectedDist == -1) {
				currentNode.currDist = expectedDist;
				distOfNodes[currentNode.row][currentNode.col] = expectedDist;
			} else if (expectedDist < currNodeDistance) {
				if (queue.contains(currentNode)) {
					currentNode.currDist = expectedDist;
					distOfNodes[currentNode.row][currentNode.col] = expectedDist;
				} else {
					queueNode newNode = new queueNode(currRow + DELTAS[0][0], currCol + DELTAS[0][1]);
					newNode.currDist = expectedDist;
					distOfNodes[newNode.row][newNode.col] = expectedDist;
					this.queue.add(newNode);
				}
			}
		}
		if (currRoom.getEastWall() != TRUE_WALL && !visited[currRow + DELTAS[1][0]][currCol + DELTAS[1][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[1][0]][currCol + DELTAS[1][1]];
			edgeDistance = currRoom.getEastWall();
			if (edgeDistance == 0) {
				edgeDistance++;
			}
			
			queueNode currentNode = new queueNode(currRow + DELTAS[1][0], currCol + DELTAS[1][1]);
			int expectedDist;
			if (currentNode.row == sRow && currentNode.col == sCol) {
				expectedDist = -1;
			} else if (edgeDistance == 1) {
				expectedDist = node.currDist + 1;
			} else if (node.currDist < edgeDistance) {
				expectedDist = edgeDistance;
			} else {
				expectedDist = node.currDist;
			}
			if (expectedDist == -1) {
				currentNode.currDist = expectedDist;
				distOfNodes[currentNode.row][currentNode.col] = expectedDist;
			} else if (expectedDist < currNodeDistance) {
				if (queue.contains(currentNode)) {
					currentNode.currDist = expectedDist;
					distOfNodes[currentNode.row][currentNode.col] = expectedDist;
				} else {
					queueNode newNode = new queueNode(currRow + DELTAS[1][0], currCol + DELTAS[1][1]);
					newNode.currDist = expectedDist;
					distOfNodes[newNode.row][newNode.col] = expectedDist;
					this.queue.add(newNode);
				}
			}
		}
		if (currRoom.getWestWall() != TRUE_WALL && !visited[currRow + DELTAS[2][0]][currCol + DELTAS[2][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[2][0]][currCol + DELTAS[2][1]];
			edgeDistance = currRoom.getWestWall();
			if (edgeDistance == 0) {
				edgeDistance++;
			}
			
			int expectedDist;
			queueNode currentNode = new queueNode(currRow + DELTAS[2][0], currCol + DELTAS[2][1]);
			if (currentNode.row == sRow && currentNode.col == sCol) {
				expectedDist = -1;
			} else if (edgeDistance == 1) {
				expectedDist = node.currDist + 1;
			} else if (node.currDist < edgeDistance) {
				expectedDist = edgeDistance;
			} else {
				expectedDist = node.currDist;
			}
			if (expectedDist == -1) {
				currentNode.currDist = expectedDist;
				distOfNodes[currentNode.row][currentNode.col] = expectedDist;
			} else if (expectedDist < currNodeDistance) {
				if (queue.contains(currentNode)) {
					currentNode.currDist = expectedDist;
					distOfNodes[currentNode.row][currentNode.col] = expectedDist;
				} else {
					queueNode newNode = new queueNode(currRow + DELTAS[2][0], currCol + DELTAS[2][1]);
					newNode.currDist = expectedDist;
					distOfNodes[newNode.row][newNode.col] = expectedDist;
					this.queue.add(newNode);
				}
			}
		}
		if (currRoom.getSouthWall() != TRUE_WALL && !visited[currRow + DELTAS[3][0]][currCol + DELTAS[3][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[3][0]][currCol + DELTAS[3][1]];
			edgeDistance = currRoom.getSouthWall();
			if (edgeDistance == 0) {
				edgeDistance++;
			}
			
			queueNode currentNode = new queueNode(currRow + DELTAS[1][0], currCol + DELTAS[1][1]);
			int expectedDist;
			if (currentNode.row == sRow && currentNode.col == sCol) {
				expectedDist = -1;
			} else if (edgeDistance == 1) {
				expectedDist = node.currDist + 1;
			} else if (node.currDist < edgeDistance) {
				expectedDist = edgeDistance;
			} else {
				expectedDist = node.currDist;
			}
			if (expectedDist == -1) {
				currentNode.currDist = expectedDist;
				distOfNodes[currentNode.row][currentNode.col] = expectedDist;
			} else if (expectedDist < currNodeDistance) {
				if (queue.contains(currentNode)) {
					currentNode.currDist = expectedDist;
					distOfNodes[currentNode.row][currentNode.col] = expectedDist;
				} else {
					queueNode newNode = new queueNode(currRow + DELTAS[3][0], currCol + DELTAS[3][1]);
					newNode.currDist = expectedDist;
					distOfNodes[newNode.row][newNode.col] = expectedDist;
					this.queue.add(newNode);
				}
			}
		}
	}
	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		for (int i = 0; i < this.maze.getRows(); i++) {
			for (int j = 0; j < this.maze.getColumns(); j++) {
				this.distOfNodes[i][j] = Integer.MAX_VALUE;
				this.visited[i][j] = false;
			}
		}
		this.solved = false;
		queueNode source = new queueNode(startRow, startCol);
		findMin(source);
		if (visited[endRow][endCol]) {
			return this.distOfNodes[endRow][endCol];
		} else {
			return null;
		}
	}
	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level given new rules.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		
		for (int i = 0; i < this.maze.getRows(); i++) {
			for (int j = 0; j < this.maze.getColumns(); j++) {
				this.distOfNodes[i][j] = Integer.MAX_VALUE;
				this.visited[i][j] = false;
			}
		}
		this.solved = false;
		queueNode source = new queueNode(startRow, startCol);
		
		findMinBonus(source);
		if (visited[endRow][endCol]) {
			return this.distOfNodes[endRow][endCol];
		} else {
			return null;
		}
	}
	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		// TODO: Find minimum fear level given new rules and special room.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		
		for (int i = 0; i < this.maze.getRows(); i++) {
			for (int j = 0; j < this.maze.getColumns(); j++) {
				this.distOfNodes[i][j] = Integer.MAX_VALUE;
				this.visited[i][j] = false;
			}
		}
		this.solved = false;
		queueNode source = new queueNode(startRow, startCol);
		this.sRow = sRow;
		this.sCol = sCol;
		findMinBonus(source);
		if (visited[endRow][endCol]) {
			return this.distOfNodes[endRow][endCol];
		} else {
			return null;
		}
	}
	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("haunted-maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);
			
			System.out.println(solver.bonusSearch(0, 0, 0, 3));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}