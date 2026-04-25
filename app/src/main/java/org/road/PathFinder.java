package org.road;

import com.google.common.graph.MutableGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

public class PathFinder {
    public static List<Road> breathFirstSearch(MutableGraph<Road> roadGraph, Road start, Road end) {
        HashSet<Road> visitedNodes = new HashSet<>();
        ArrayList<Road> queue = new ArrayList<>();
        ArrayList<RoadEdge> edges = new ArrayList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Road currentNode = queue.getFirst();
            if (visitedNodes.contains(currentNode)) {
                queue.removeFirst();
                continue;
            }
            visitedNodes.add(currentNode);

            var children = roadGraph.successors(currentNode).iterator();
            while (children.hasNext()) {
                Road nextNode = children.next();
                RoadEdge currentEdge = new RoadEdge(currentNode, nextNode);

                edges.add(currentEdge);

                if (nextNode == end) {
                    break;
                }

                queue.add(nextNode);
            }

            queue.removeFirst();
        }

        // This means that there isn't a path from `start` to `end`
        if (edges.getLast().target != end) {
            return null;
        }
        
        return recoverPath(edges).reversed();
    }

    private static ArrayList<Road> recoverPath(ArrayList<RoadEdge> edges) {
        ArrayList<Road> path = new ArrayList<>();
        path.add(edges.getLast().target);
        while (true) {
            RoadEdge currentEdge = findEdgeWithTarget(edges, path.getLast());
            if (currentEdge == null) {
                break;
            }
            path.add(currentEdge.source);
        }

        return path;
    }
    
    private static RoadEdge findEdgeWithTarget(ArrayList<RoadEdge> edges, Road target) {
        for (int i = 0; i < edges.size(); i++) {
            RoadEdge currentEdge = edges.get(i);
            if (currentEdge.target == target) {
                return currentEdge;
            }
        }

        return null;
    }
}

class RoadEdge {
    Road source;
    Road target;

    RoadEdge(Road source, Road target) {
        this.source = source;
        this.target = target;
    }


}
