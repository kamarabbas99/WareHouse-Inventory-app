# Iteration Worksheet 3

## What technical debt has been cleaned up

A technical debt that we had cleaned up has to do with not implementing delete() or clearDB() functions in the past iteration. 
It would be classified as prudent-deliberate technical debt because we knew that deleting the data would be needed to clean up our tests but we did not want users to be able to use them.
So we ended up choosing not to implement these methods last iteration, but ended up implementing them this iteration.
The commits where cleaned up this technical debt are the following:

1.  [Can now delete items, inventories, and accounts](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/bb3d413130bd9294d70946a3b4d869029c76ec3e).
2. [Add delete transaction method in TransactionPersistence class](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/b6e4f68e6b83e7c8f63091bc08a2aa2b2d672b9f).

Another technical debt that we cleaned up this iteration was having unique names for inventories and accounts.
It was reckless-inadvertant technical debt because we had not even think of this as being a feature, no matter how obvious it may seem to us now.
In this iteration the following commit is where we payed of this debt:

1. [Inventories now need to have unique names](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/0d192cf08c3bd98222431b955517a4a39be42747).
2. [Add unique username for accounts](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/commit/64f038a8766465ac0b7e54a57a8da016d263502c).

## What technical debt did you leave?

We completely forgot to implement unique names for our inventories, accounts and items. This meant we had to try and implement it later. Accounts and inventories were patched in, but the items database classes were too hard to modify quickly, therefore we had to leave it in. This type of debt was reckless inadvertant as we ignored design to move quickly.

## Discuss a Feature or User Story that was cut/re-prioritized

Feature: [Ordering](https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/issues/16)

This feature was cut. We were planning on making our app be able to order items from a shop page, however at the start of this iteration, we decided that this was not as important as the other features, so we cut this feature. This was not a major feature from the start and it was always lower priority than the other features.

## Acceptance test/end-to-end

Write a discussion about an end-to-end test that you wrote. What did you test,
how did you set up the test so it was not flaky? Provide a link to that test.

## Acceptance test, untestable

A challenge that we ran into when creating the acceptance tests was figuring out a way to appropriately test if a actaully feature worked.
This was specifically with the features "View Inventory", "Stock Item Information", and "Report".
Since these features only involved checking that you can view some details, no data was changed. 
To test these, we simply made sure that the user could navigate to these pages.

Another challenge that we ran into was narrowing down part of the feature that the test should test.
This happened with our "Add Inventory" feature could be interpreted differently.
We could have interpreted it as being able to create an inventory, or add quantity to an existing item, or add a new item.
We ended up testing new item creation and changing the quantity.

## Velocity/teamwork

The estimates got better through the course as we learned more about the tools and gained experience while working on the project.

Iteration 1 (approx 20% spent)
Est 1w 3h
Spent 1d 2h 22m
https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/milestones/2#tab-issues

Iteration 2 (approx 60% spent)
Est 2w 4d 3h
Spent 1w 4d 1h 20m
https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/milestones/3#tab-issues

Iteration 3 (approx 70% spent) 
Est 1w 2d 1h 50m
Spent 1w 2h
https://code.cs.umanitoba.ca/winter-2022-a02/group-14/warehouse-inventory-system/-/milestones/4#tab-issues
GitLab
Sign in
GitLab Community Edition

