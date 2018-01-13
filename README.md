Assumptions/Baselines

1) All lifts are initially on floor 1.

2) For outside user, the lift selected would be the first available lift (irrespective of lift location).

3) Once a lift is selected by a user and is in motion, that lift is not available for other users till the lift reaches it's destination.

4) Have altered the stoppage/crossing time for lifts to 2 ms/1 ms respectively.

5) All the request parameters would be input first and validated. If validation succeeds, all of them would be executed concurrently, thus simulating a multi-user environment.


Basic algorithm:

1) Application parameters are validated first. If validation succeeds, execution continues.

1) User inputs all the request parameters. All of them are validated first. If any of them fails validation, an error message is returned and execution is stopped. Else, the execution continues.

3) If user is inside the lift, find the lift the user is in and move to the destination (putting output to the console as each floor is passed or reached).

4) If user is outside the lift, pick the first available lift and move to the destination (putting output to the console as each floor is passed or reached).
