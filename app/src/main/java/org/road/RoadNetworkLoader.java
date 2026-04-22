package org.road;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import com.badlogic.gdx.math.Vector2;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.GraphBuilder;

public class RoadNetworkLoader {
    public static RoadNetwork readFromFile(String filePath) {
        Document document = readDocument(filePath);
        var nodes = document.getElementsByTagName("node");
        
        MutableGraph<Road> roadGraph = GraphBuilder.directed().build();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node currentNode = nodes.item(i);
            roadGraph.addNode(readRoadNode(currentNode));
        }
        return new RoadNetwork(roadGraph);
    }

    private static Document readDocument(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);

            return document;
        } catch (Exception e) {
            System.out.println("For fucks sake");
            System.exit(1);
        }

        // This should be unreachable
        return null;
    }

    private static Road readRoadNode(Node node) {
        HashMap<String, String> attributes = readAttributesMap(node);
        System.out.println(attributes);

        float x = Float.parseFloat(attributes.get("x"));
        float y = Float.parseFloat(attributes.get("y"));
        
        Road roadNode = new Road(new Vector2(x, y));
        return roadNode;
    }

    private static HashMap<String, String> readAttributesMap(Node node) {
        HashMap<String, String> attributeMap = new HashMap<>();
        var childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            var currentNode = childNodes.item(i);
            var attributes = currentNode.getAttributes();

            if (attributes == null) {
                continue;
            }

            assert attributes.getLength() == 1;
            var attribute = attributes.item(0);

            attributeMap.put(attribute.getTextContent(), currentNode.getTextContent());
        }

        return attributeMap;
    }
}
