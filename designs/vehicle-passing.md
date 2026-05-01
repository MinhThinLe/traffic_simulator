- A vehicle with a priority higher than an arbitrary number will send a "pass
  request" to the vehicle occupying the next node if it has to wait an arbitrary
  amount of time.
- The vehicle could have properties such as impatientness or stinginess that
  affects the amount of time a vehicle would wait before sending a pass request
  and the chance that it would refuse a pass request respectively.
- The vehicle occupying the next node can decide whether or not to refuse request based on
  - Its priority.
  - It's currently waiting as well.
  - Simply because.
- The first two conditions should be override-able if the vehicle sending the
  request's priority is high enough (it's categorized as an emergency vehicle).
- the `Road` class should have an extra field named `pullOverVehicle` to
  accommodate for this feature.
- Upon accepting the pass request, the Road class should instruct its vehicle
  to pull over, giving way to the more urgent vehicle's need.
- If the vehicle is close enough to its next destination and the destination
  node is unoccupied, the vehicle could simply speed up instead of pulling over.
- After the more urgent vehicle's passed, the Road class should again, instruct
  its vehicle to move back to its main track and continue circulate.
- The pass request could be accommodated with a horn sound effect to make the
  experience more realistic. 
