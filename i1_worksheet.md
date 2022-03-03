Iteration 1 Worksheet
=====================

Adding a feature
-----------------
Feature: VIEW INVENTORY

We are making a Warehouse Inventory Tracker. A core component of an inventory tracker is the ability to be able to see the inventory stored inside of the warehouse and so this was one of the first features we thought of implementing in our project. This feature can be split into multiple smaller steps. The user can view individual items in the inventory, view the whole inventory, and view the inventory by category/tag.

Connected Features:
- https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/4

Connected User Stories:
- https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/20
- https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/19

Merged requests:
- https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/merge_requests/2

Exceptional code
----------------

Our application will require the user to sign in before doing anything in the inventory. In order to make sure that an account is made with an appropriate username and password, we use NullExceptions in the constructor for the Account class. This can be seen in the [Account](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/blob/development/app/src/main/java/objects/Account.java) class and the [AccountTest](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/blob/development/app/src/test/java/objects/AccountTest.java) class. 

Similarly the "company" field in an Account object could be set to null. We decided to check for null and set it to "default" instead of throwing an exception because we do not consider it as a required field.

Branching
----------

Our branching strategy is listed [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/blob/main/README.md) in the README file.

A screen of a successful feature merge is found [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/blob/development/it1-branch-strategy-example.PNG). In this picture, the stock-view branch was merged into our development branch. This event is highlight in yellow on the picture.


SOLID
-----

The issue created for the violation of a SOLID principal is [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-15/simple-forum/-/issues/16).


Agile Planning
--------------

Once we learned about how the UI layer operates in android studio, we changed the logic layer to add in a method to return an array of every item object in the system. This makes displaying the objects dynamically on the screen easier, but as we did not really know about how the UI systems are setup in android studio we had to change on the fly. We also ended up pushing the app colour pallete and the app dropdown features to iteration 2, as they were small low priority features that were not necessary to the functionality of the current iteration. We also had to push back the tasks related to viewing individual stock items and all of their related data at once as we ran low on time and it was not direclty relevent to the main UI feature of this iteration.
https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/28
https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/29
https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/34
https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/33

In addition to some of the UI features, we were not able to implement any adding or removing of Items from an inventory. We therefore decided to push these back to the next iteration. The following links are the issues we decided to push back: 
https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/6
https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/7
https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/8
https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/10
https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/11
https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/13
