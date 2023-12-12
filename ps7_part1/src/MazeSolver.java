import java.util.*;
public class MazeSolver implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};
	private Maze maze;
	private boolean solved;
	private boolean[][] visited;
	private List<Integer> step;
	public MazeSolver() {
		// TODO: Initialize variables.
		this.solved = false;
		this.maze = null;
	}
	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		this.solved = false;
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
	}
	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		solved = false;
		step = new ArrayList<>();
		// set all visited flag to false during initialisation
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}
		// the markPos will be replaced by the end point and used to trace back to the start point
		// via the parents to mark the shortest path
		Pair markPos = null;
		int counter = 0;
		int result = 0;
		Pair startPos = new Pair(startRow, startCol);
		startPos.setParent(null);
		Queue<Pair> currFrontier = new LinkedList<>();
		currFrontier.add(startPos);
		
		while (!currFrontier.isEmpty()) {
			int numOfRooms = currFrontier.size();
			step.add(counter, numOfRooms);
			Queue<Pair> nextFrontier = new LinkedList<>();
			for (Pair pos : currFrontier) {
				// reached
				if (pos.x == endRow && pos.y == endCol) {
					solved = true;
					markPos = pos;
					result = counter;
				}
				// visit and mark each room as visited, if room has no wall in that direction,
				// add the room in that direction to next frontier and mark that room as visited, set the parent of
				// new room to the current room
				visited[pos.x][pos.y] = true;
				Room currRoom = maze.getRoom(pos.x, pos.y);
				if (!currRoom.hasNorthWall() && !visited[pos.x + DELTAS[NORTH][0]][pos.y + DELTAS[NORTH][1]]) {
					Pair northPos = new Pair(pos.x + DELTAS[NORTH][0], pos.y + DELTAS[NORTH][1]);
					nextFrontier.add(northPos);
					northPos.setParent(pos);
					visited[northPos.x][northPos.y] = true;
				}
				if (!currRoom.hasSouthWall() && !visited[pos.x + DELTAS[SOUTH][0]][pos.y + DELTAS[SOUTH][1]]) {
					Pair southPos = new Pair(pos.x + DELTAS[SOUTH][0], pos.y + DELTAS[SOUTH][1]);
					nextFrontier.add(southPos);
					southPos.setParent(pos);
					visited[southPos.x][southPos.y] = true;
				}
				if (!currRoom.hasEastWall() && !visited[pos.x + DELTAS[EAST][0]][pos.y + DELTAS[EAST][1]]) {
					Pair eastPos = new Pair(pos.x + DELTAS[EAST][0], pos.y + DELTAS[EAST][1]);
					nextFrontier.add(eastPos);
					eastPos.setParent(pos);
					visited[eastPos.x][eastPos.y] = true;
				}
				if (!currRoom.hasWestWall() && !visited[pos.x + DELTAS[WEST][0]][pos.y + DELTAS[WEST][1]]) {
					Pair westPos = new Pair(pos.x + DELTAS[WEST][0], pos.y + DELTAS[WEST][1]);
					nextFrontier.add(westPos);
					westPos.setParent(pos);
					visited[westPos.x][westPos.y] = true;
				}
			}
			currFrontier = nextFrontier;
			counter++;
		}
		if (solved) {
			maze.getRoom(startPos.x, startPos.y).onPath = true;
			while (markPos.parent != null) {
				maze.getRoom(markPos.x, markPos.y).onPath = true;
				markPos = markPos.parent;
			}
			return result;
		} else {
			return null;
		}
	}
	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		if (k > step.size() - 1) {
			return 0;
		} else {
			return step.get(k);
		}
	}
	public static void main(String[] args) {
		// Do remember to remove any references to ImprovedMazePrinter before submitting
		// your code!
		try {
			Maze maze = Maze.readMaze("maze-empty.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);
			System.out.println(solver.pathSearch(0, 0, 3, 3));
			MazePrinter.printMaze(maze);
			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}