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

The technical debt that we left 
What one item would you like to fix, and can't? Anything you write will not
be marked negatively. Classify this debt.

## Discuss a Feature or User Story that was cut/re-prioritized

When did you change the priority of a Feature or User Story? Why was it
re-prioritized? Provide a link to the Feature or User Story. This can be from any
iteration.

## Acceptance test/end-to-end

Write a discussion about an end-to-end test that you wrote. What did you test,
how did you set up the test so it was not flaky? Provide a link to that test.

## Acceptance test, untestable

What challenges did you face when creating acceptance tests? What was difficult
or impossible to test?

## Velocity/teamwork

Did your estimates get better or worse through the course? Show some
evidence of the estimates/actuals from tasks.
