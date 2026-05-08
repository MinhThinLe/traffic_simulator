package org.render.drawcalls;

import java.util.Comparator;

public class DrawCallComparator implements Comparator<PrimitiveDrawCall> {
    @Override
    public int compare(PrimitiveDrawCall o1, PrimitiveDrawCall o2) {
        int shapeCompare = o1.shapeType.compareTo(o2.shapeType);
        if (shapeCompare != 0) {
            return shapeCompare;
        }
        return Float.compare(o1.color.r + o1.color.g + o1.color.b, o2.color.r + o2.color.g + o2. color.b);
    }
    
}
