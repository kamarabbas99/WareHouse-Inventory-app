Iteration 2 Worksheet
=====================

Paying off Technical Debt
-----------------
1. We had reckless-deliberate technical debt on our logic layer by creating a class that would only work with the fake database. This was reckless-deliberate because we did not know how the database layer would actually work at the time and used the `InventoryManager` class as a temporary solution. We knew at the time that it would have to be redone and the following [commit](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/8a01b96e26372b8c7e5a4de65d8fa23b309ddc82) show us completely removing this class and replacing it with several other classes that actually work with the existing database.
2. Another case of technical debt was when the creation of the ItemPersistence which was initially designed to modify multiple tables in the database at once. This would be classified as reckless-inadvertent technical debt because at the time we had thought that it would make sense. After writing the entire class and doing some retrospection, we found that it made more sense to modify each table individually from separate classes. The following commits show us paying off this technical debt.
	- [Created InventoryManagerPersistence class](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/83ec76b33d87508057919aeaf86c97fff8e8586e).
	- [Reimplemented ItemPersistence class to only interact with the Items table](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/48974f2e69d976858a2ab307f2a3b3b4e9525ec0).
	- [Implement InventoryManagerPersistence class to handle modifications to the InventoryManagers table](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/d53b445786ab6e5ba4b8afb45b4dbf69f5a4a16f).

SOLID
----------------



Retrospective
----------



Design Patterns
-----
In our [DatabaseManager class](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/blob/development/app/src/main/java/database/DatabaseManager.java), we used the Singleton design pattern to ensure that the class could only be instantiated once. In this class we also used the Singleton pattern to enforce that only a single instance of each of the Persistence classes could be created.


Iteration 1 Feedback Fixes
--------------
UI Not showing up correctly issue [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/66)
This was flagged as the UI elements, specifically text, would not show up correctly on smaller screens. This was due to it only being tested for the Android Nexus 7 tablet. This was refactored by adding some wrap_content tags for the bounds of boxes and changing the constraints to change with screen size. The commit is [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/38d1622e1a27739ce9e22acae946d256f5037075), [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/52becb3e849f3c720c9f4401dc6fd4e6b3f635f1) and [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/58a0b2e8d332b55674d22c4da1cc1e9c5fb8cff5)
