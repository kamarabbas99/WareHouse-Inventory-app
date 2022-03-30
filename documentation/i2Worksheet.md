Iteration 2 Worksheet
=====================

Paying off Technical Debt
-----------------


SOLID
----------------



Retrospective
----------



Design Patterns
-----
In our [DatabaseManager class](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/blob/development/app/src/main/java/database/DatabaseManager.java), we used the Singleton design pattern to ensure that the class could only be instantiated once. In this class we also used the Singleton pattern to enforce only a single instance of all the Persistence classes.


Iteration 1 Feedback Fixes
--------------
UI Not showing up correctly issue [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/66)
This was flagged as the UI elements, specifically text, would not show up correctly on smaller screens. This was due to it only being tested for the Android Nexus 7 tablet. This was refactored by adding some wrap_content tags for the bounds of boxes and changing the constraints to change with screen size. The commit is [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/38d1622e1a27739ce9e22acae946d256f5037075), [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/52becb3e849f3c720c9f4401dc6fd4e6b3f635f1) and [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/58a0b2e8d332b55674d22c4da1cc1e9c5fb8cff5)
