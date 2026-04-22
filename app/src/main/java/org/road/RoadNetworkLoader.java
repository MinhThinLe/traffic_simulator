package org.road;

import java.util.HashMap;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import com.badlogic.gdx.math.Vector2;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.GraphBuilder;

public class RoadNetworkLoader {
    public static RoadNetwork readFromFile(String filePath) {
        Document document = readDocument(filePath);
        MutableGraph<Road> roadGraph = GraphBuilder.directed().build();

        // Read nodes from the file
        NodeList nodes = document.getElementsByTagName("node");
        HashMap<String, Road> roadMap = new HashMap<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node currentNode = nodes.item(i);

            NamedNodeMap attributes = currentNode.getAttributes();
            // nodes should only have 1 ID
            assert attributes.getLength() == 1;

            Road parsedRoadNode = readRoadNode(currentNode);

            roadMap.put(attributes.item(0).getTextContent(), parsedRoadNode);
            roadGraph.addNode(parsedRoadNode);
        }

        // Read edges from the file
        NodeList edges = document.getElementsByTagName("edge");
        ArrayList<Edge> edgeList = readEdgeList(edges);
        
        for (int i = 0; i < edgeList.size(); i++) {
            Edge currentEdge = edgeList.get(i);

            Road from = roadMap.get(currentEdge.source);
            Road to = roadMap.get(currentEdge.target);
    
            roadGraph.putEdge(from, to);
        }

        return new RoadNetwork(roadGraph);
    }

    private static Document readDocument(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);

            return document;
        } catch (Exception e) {}

        // This should be unreachable
        return null;
    }

    private static Road readRoadNode(Node node) {
        HashMap<String, String> attributes = readChildrenAttributeMap(node);

        float x = Float.parseFloat(attributes.get("x"));
        float y = Float.parseFloat(attributes.get("y"));
        
        Road roadNode = new Road(new Vector2(x, y));
        return roadNode;
    }

    private static HashMap<String, String> readChildrenAttributeMap(Node node) {
        HashMap<String, String> attributeMap = new HashMap<>();
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node currentNode = childNodes.item(i);
            NamedNodeMap attributes = currentNode.getAttributes();

            if (attributes == null) {
                continue;
            }

            assert attributes.getLength() == 1;
            Node attribute = attributes.item(0);

            attributeMap.put(attribute.getTextContent(), currentNode.getTextContent());
        }

        return attributeMap;
    }

    private static ArrayList<Edge> readEdgeList(NodeList edges) {
        ArrayList<Edge> edgeList = new ArrayList<>();
        for (int i = 0; i < edges.getLength(); i++) {
            Node currentEdge = edges.item(i);

            NamedNodeMap attributes = currentEdge.getAttributes();
            
            String from = null;
            String to = null;
            for (int j = 0; j < attributes.getLength(); j++) {
                Node currentAttribute = attributes.item(j);

                if (currentAttribute.getNodeName() == "source") {
                    from = currentAttribute.getTextContent();
                }
                if (currentAttribute.getNodeName() == "target") {
                    to = currentAttribute.getTextContent();
                }
            }

            if (from == null || to == null) {
                continue;
            }

            Edge edge = new Edge(from, to);
            edgeList.add(edge);
        }

        return edgeList;
    }
}

class Edge {
    String source;
    String target;

    Edge(String source, String target) {
        this.source = source;
        this.target = target;
    }
}
