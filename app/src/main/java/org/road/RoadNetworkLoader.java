package org.road;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import com.badlogic.gdx.math.Vector2;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.GraphBuilder;

public class RoadNetworkLoader {
    private static final int SOURCE_NODE = 1;
    private static final int SINK_NODE = -1;
    public static RoadNetwork readFromStream(InputStream XMLStream) {
        Document document = readDocument(XMLStream);
        MutableGraph<Road> roadGraph = GraphBuilder.directed().build();

        // Read nodes from the file
        NodeList nodes = document.getElementsByTagName("node");
        HashMap<String, Road> roadMap = new HashMap<>();

        ArrayList<Road> sources = new ArrayList<>();
        ArrayList<Road> sinks = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node currentNode = nodes.item(i);

            NamedNodeMap attributes = currentNode.getAttributes();
            // nodes should only have 1 ID
            assert attributes.getLength() == 1;

            RoadPackage roadPackage = readRoadNode(currentNode);
            if (roadPackage.nodeType == SOURCE_NODE) {
                sources.add(roadPackage.content);
            }
            if (roadPackage.nodeType == SINK_NODE) {
                sinks.add(roadPackage.content);
            }

            roadMap.put(attributes.item(0).getTextContent(), roadPackage.content);
            roadGraph.addNode(roadPackage.content);
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
        } catch (Exception e) {}

        // This should be unreachable
        return null;
    }

    private static RoadPackage readRoadNode(Node node) {
        HashMap<String, String> attributes = readChildrenAttributeMap(node);

        float x = Float.parseFloat(attributes.get("x"));
        float y = Float.parseFloat(attributes.get("y"));
        int nodeType = 0;
        if (attributes.containsKey("node_type")) {
            nodeType = Integer.parseInt(attributes.get("node_type"));
        }
        
        Road roadNode = new Road(new Vector2(x, y));

        return new RoadPackage(roadNode, nodeType);
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

            ParserEdge edge = new ParserEdge(from, to);
            edgeList.add(edge);
        }

        return edgeList;
    }
}

class ParserEdge {
    String source;
    String target;

    ParserEdge(String source, String target) {
        this.source = source;
        this.target = target;
    }
}

class RoadPackage {
    Road content;
    int nodeType;

    RoadPackage(Road content, int nodeType) {
        this.content = content;
        this.nodeType = nodeType;
    }
}
