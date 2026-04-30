Example definition of the TrafficLight class

```java
enum TrafficLightMode {
    FULL_COUNT_DOWN,    // always displays its count down timer
    NO_COUNT_DOWN,      // never displays its count down timer
    LAST_TEN_SECONDS,   // only displays its count down timer when it has 10 seconds or less remaining
}

class TrafficLight {
    ArrayList<Road> memberNodes;    // Member nodes of this traffic light block
    ArrayList<Road> ingressNodes;   // Nodes from which a vehicle can enter this traffic light block
    Timer countDownTimer;           // Traffic light count down timer
    int permittedNodeIndex;         // Index of the node permitted to enter the block
}
```

- Vehicles in member nodes can freely move from one to another (if the graph
  structure allows it)
- Vehicles from outside must wait for their turn to enter the block (unless its
  priority is greater than an arbitrary value, then and only then can it run
  a red light)
- Every member nodes of the cluster must hold a reference to a `TrafficLight`
  of the appropriate group so as to aid when deciding which vehicles can or
  cannot enter.

Defining a traffic light cluster from a .graphml file

- To be compatible with the traffic light extension, a .graphml file must
  contain the following data collumns
    - `traffic_light_cluster_id`
    - `traffic_light_type` preferrably int, so as to allow for easy conversion
      to the `TrafficLightMode` enum
    - `traffic_light_duration` int or float, this defines the duration for the
      internal timer 
    - It should be noted that while every member nodes of the cluster must have
      the same, non-null value for the `traffic_light_cluster_id` property. The
      other two properties are entirely optional. It is up to the implementer
      to decide which node's data takes precedence.
