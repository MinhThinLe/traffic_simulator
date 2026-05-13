package org.render.drawcalls;

import java.util.Comparator;

public class GraphicalDrawCallComparator implements Comparator<GraphicalDrawCall> {
    @Override
    public int compare(GraphicalDrawCall o1, GraphicalDrawCall o2) {
        return Float.compare(o1.z, o2.z);
    }
}
