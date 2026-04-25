package org.road;

import com.badlogic.gdx.math.Vector2;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PathFinderTest {
    @Test
    void testBFS() {
        MutableGraph<Road> roadGraph = GraphBuilder.directed().build();

        Road roadNode1 = new Road(Vector2.Zero);
        Road roadNode2 = new Road(Vector2.Zero);
        Road roadNode3 = new Road(Vector2.Zero);
        Road roadNode4 = new Road(Vector2.Zero);
        Road roadNode5 = new Road(Vector2.Zero);
    
        roadGraph.addNode(roadNode1);
        roadGraph.addNode(roadNode2);
        roadGraph.addNode(roadNode3);
        roadGraph.addNode(roadNode4);
        roadGraph.addNode(roadNode5);

        roadGraph.putEdge(roadNode1, roadNode2);
        roadGraph.putEdge(roadNode1, roadNode4);
        roadGraph.putEdge(roadNode2, roadNode3);
        roadGraph.putEdge(roadNode4, roadNode5);
        roadGraph.putEdge(roadNode5, roadNode2);

        List<Road> result = PathFinder.breathFirstSearch(roadGraph, roadNode1, roadNode5);

        ArrayList<Road> expected = new ArrayList<>();
        expected.add(roadNode1);
        expected.add(roadNode4);
        expected.add(roadNode5);

        assert expected.get(0) == result.get(0);
        assert expected.get(1) == result.get(1);
        assert expected.get(2) == result.get(2);
    }

}
