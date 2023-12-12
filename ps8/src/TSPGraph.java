import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
public class TSPGraph implements IApproximateTSP {
    @Override
    public void MST(TSPMap map) {
        // TODO: implement this method
        // Initialize priority queue
        TreeMapPriorityQueue<Double, Integer> pq = new TreeMapPriorityQueue<>();
        for(int i = 0; i < map.getCount(); i++) {
            pq.add(i, Double.MAX_VALUE);
        }
        //pq.decreasePriority(0, 0.0);
        // Initialise set S
        HashSet<Integer> s = new HashSet<Integer>();
        // Initialise parent hash table
        HashMap<Integer, Integer> parent = new HashMap<>();
        parent.put(0, null);
        while(!pq.isEmpty()) {
            Integer node = pq.extractMin();
            s.add(node);
            for(int i = 0; i < map.getCount(); i++) {
                double dist = map.pointDistance(i, node);
                if(!s.contains(i) && dist < pq.lookup(i)) {
                    pq.decreasePriority(i, dist);
                    parent.put(i, node);
                }
            }
        }
        for(int i = 0; i< map.getCount(); i++) {
            Integer par = parent.get(i);
            if(par != null) {
                map.setLink(i, par, false);
            }
        }
        map.redraw();
    }
    LinkedList<Integer> dfs = new LinkedList<>();
    private void DFS(TSPMap map,int node, boolean[] visited) {
        for(int i = 0; i < map.getCount(); i ++) {
            if(!visited[i] && map.getLink(i) == node) {
                visited[i] = true;
                dfs.add(i);
                DFS(map, i, visited);
            }
        }
    }
    @Override
    public void TSP(TSPMap map) {
        MST(map);
        // TODO: implement the rest of this method.
        boolean[] visited = new boolean[map.getCount()];
        for(int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }
        for (int i = 0; i < map.getCount(); i++) {
            if(!visited[i]) {
                visited[i] = true;
                dfs.add(i);
                DFS(map, i, visited);
            }
        }
        for(int i = 0; i < dfs.size() - 1; i++) {
            map.setLink(dfs.get(i),dfs.get(i + 1), false);
        }
        if(map.getCount() > 0) {
            int last = dfs.size() - 1;
            map.setLink(dfs.get(last),dfs.get(0), false);
        }
        map.redraw();
    }
    @Override
    public boolean isValidTour(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        int node = 0;
        int count = 1;
        for(int i = 0; i < map.getCount() - 1; i ++) {
            int next = map.getLink(node);
            if(next == -1) {
                return false;
            }
            if(next == 0) {
                break;
            }
            node = next;
            count++;
        }
        return (map.getLink(node) == 0 && count == map.getCount());
    }
    @Override
    public double tourDistance(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        double distance = 0;
        int node = 0;
        for(int i = 0; i < map.getCount(); i++) {
            int next = map.getLink(node);
            distance += map.pointDistance(next, node);
            node = next;
        }
        distance += map.pointDistance(node, 0);
        return distance;
    }
    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "fiftypoints.txt");
        TSPGraph graph = new TSPGraph();
        graph.MST(map);
        // graph.TSP(map);
        // System.out.println(graph.isValidTour(map));
        // System.out.println(graph.tourDistance(map));
    }
}