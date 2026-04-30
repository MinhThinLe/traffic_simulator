package org.road;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import org.w3c.dom.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RoadNetworkLoader {
    public static RoadNetwork readFromStream(InputStream XMLStream) {
        Document document = readDocument(XMLStream);
        MutableGraph<Road> roadGraph = GraphBuilder.directed().build();

        // Read nodes from the file
        NodeList nodes = document.getElementsByTagName("node");
        HashMap<Integer, Road> roadMap = new HashMap<>();

        ArrayList<Road> sources = new ArrayList<>();
        ArrayList<Road> sinks = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node currentNode = nodes.item(i);

            Road road = readRoadNode(currentNode);

            switch (road.getNodeType()) {
                case SOURCE_NODE:
                    sources.add(road);
                    break;
                case SINK_NODE:
                    sinks.add(road);
                    break;
                default:
                    break;
            }

            roadMap.put(road.getId(), road);
            roadGraph.addNode(road);
        }

        // Read edges from the file
        NodeList edges = document.getElementsByTagName("edge");
        ArrayList<ParserEdge> edgeList = readEdgeList(edges);

        for (int i = 0; i < edgeList.size(); i++) {
            ParserEdge currentEdge = edgeList.get(i);

            Road from = roadMap.get(currentEdge.source);
            Road to = roadMap.get(currentEdge.target);

            roadGraph.putEdge(from, to);
        }

        return new RoadNetwork(roadGraph, sources, sinks);
    }

    private static Document readDocument(InputStream XMLStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(XMLStream);

            return document;
        } catch (Exception e) {
        }

        // This should be unreachable
        return null;
    }

    private static Road readRoadNode(Node node) {
        getNodeId(node);
        HashMap<String, String> attributes = readChildrenAttributeMap(node);

        int id = getNodeId(node);
        float scalar = Road.RADIUS / Float.parseFloat(attributes.get("size"));
        float x = Float.parseFloat(attributes.get("x")) * scalar;
        float y = Float.parseFloat(attributes.get("y")) * scalar;
        NodeType nodeType = extractNodeType(attributes);

        return new Road(x, y, nodeType, id);
    }

    private static final int SOURCE_NODE = 1;
    private static final int SINK_NODE = -1;

    private static NodeType extractNodeType(HashMap<String, String> attributes) {
        NodeType nodeType = NodeType.NORMAL_NODE;
        if (attributes.containsKey("node_type")) {
            int type = Integer.parseInt(attributes.get("node_type"));

            if (type == SINK_NODE) {
                nodeType = NodeType.SINK_NODE;
            }
            if (type == SOURCE_NODE) {
                nodeType = NodeType.SOURCE_NODE;
            }
        }

        return nodeType;
    }

    private static int getNodeId(Node node) {
        var attributes = node.getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            if (attributes.item(i).getNodeName() == "id") {
                return Integer.parseInt(attributes.item(i).getNodeValue());
            }
        }

        return -1;
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

    private static ArrayList<ParserEdge> readEdgeList(NodeList edges) {
        ArrayList<ParserEdge> edgeList = new ArrayList<>();
        for (int i = 0; i < edges.getLength(); i++) {
            Node currentEdge = edges.item(i);

            NamedNodeMap attributes = currentEdge.getAttributes();

            int from = -1;
            int to = -1;
            for (int j = 0; j < attributes.getLength(); j++) {
                Node currentAttribute = attributes.item(j);

                if (currentAttribute.getNodeName() == "source") {
                    from = Integer.parseInt(currentAttribute.getTextContent());
                }
                if (currentAttribute.getNodeName() == "target") {
                    to = Integer.parseInt(currentAttribute.getTextContent());
                }
            }

            if (from == -1 || to == -1) {
                continue;
            }

            ParserEdge edge = new ParserEdge(from, to);
            edgeList.add(edge);
        }

        return edgeList;
    }
}

class ParserEdge {
    int source;
    int target;

    ParserEdge(int source, int target) {
        this.source = source;
        this.target = target;
    }
}
