package org.road;

import com.google.common.graph.MutableGraph;

import java.util.*;

public class PathFinder {
    public static List<Road> breathFirstSearch(MutableGraph<Road> roadGraph, Road start, Road end) {
        Set<Road> visitedNodes = new HashSet<>();
        List<Road> queue = new ArrayList<>();
        List<RoadEdge> edges = new ArrayList<>();
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
                    return recoverPath(edges).reversed();
                }

                queue.add(nextNode);
            }

            queue.removeFirst();
        }

        return null;
    }

    private static List<Road> recoverPath(List<RoadEdge> edges) {
        List<Road> path = new ArrayList<>();
        path.add(edges.getLast().target());
        while (true) {
            RoadEdge currentEdge = findEdgeWithTarget(edges, path.getLast());
            if (currentEdge == null) {
                break;
            }
            path.add(currentEdge.source());
        }

        return path;
    }

    private static RoadEdge findEdgeWithTarget(List<RoadEdge> edges, Road target) {
        for (int i = 0; i < edges.size(); i++) {
            RoadEdge currentEdge = edges.get(i);
            if (currentEdge.target() == target) {
                return currentEdge;
            }
        }

        return null;
    }
}

record RoadEdge(Road source, Road target) {
    RoadEdge {
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);
    }
}
