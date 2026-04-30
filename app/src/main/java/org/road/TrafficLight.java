package org.road;

import java.util.ArrayList;

import com.google.common.graph.MutableGraph;

enum TrafficLightType {
    FULL_COUNT_DOWN,
    NO_COUNT_DOWN,
    LAST_TEN_SECONDS
}

public class TrafficLight {
    private static final float DEFAULT_TIMER_DURATION = 5;
    private ArrayList<Road> memberNodes;
    private ArrayList<Road> ingressNodes;
    private Timer timer;
    private int permittedNodeIndex;
    private TrafficLightType type;
    private int id;

    public TrafficLight(int id) {
        this.memberNodes = new ArrayList<>();
        this.ingressNodes = new ArrayList<>();
        this.timer = new Timer(DEFAULT_TIMER_DURATION);
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setType(int type) {
        this.type = TrafficLightType.values()[type];
    }

    public void setDuration(float newDuration) {
        this.timer.setDuration(newDuration);
    }

    public void addMemberNode(Road memberNode) {
        this.memberNodes.add(memberNode);
    }

    public void addIngressNodes(MutableGraph<Road> roadGraph) {
        for (int i = 0; i < this.memberNodes.size(); i++) {
            Road currentNode = this.memberNodes.get(i);
            var predecessors = roadGraph.predecessors(currentNode).iterator();
        
            while (predecessors.hasNext()) {
                var predecessor = predecessors.next();

                if (isMember(predecessor)) {
                    continue;
                }

                ingressNodes.add(predecessor);
            }
        }
    }

    private boolean isMember(Road node) {
        for (int i = 0; i < this.memberNodes.size(); i++) {
            if (this.memberNodes.get(i) == node) {
                return true;
            }
        }

        return false;
    }
}
