# Warehouse Inventory System (WIS)
The Warehouse Inventory System (WIS) will be used to maintain information about the contents of a user's warehouse and allow them to manage incoming and outgoing stock. The system will keep track of individual shipments into the warehouse and add them to the overall warehouse stock. It will also allow users to remove stock when sending out goods to their customers or if for some reason some warehouse stock needs to be thrown out. 

The system is designed primarily for warehouse owners and inventory management staff. It will allow them to track what goods have been ordered and add them to the current stock when the orders arrive. It will also let them remove parts of stock when goods are sent out of the warehouse to their clients. The system will also allow for notifications to be set up when certain goods (selected by the user) start to run low and need to be reordered, reducing the chance of running out of stock of a certain item. Warehouse workers could also use the system, allowing them to see what goods are coming into the warehouse and will need to be stored.

Different levels of clearance are required for the types of users in the system. Inventory managers will need to have the highest clearance which will allow them to add incoming stock and remove outgoing stock, while regular warehouse workers will only be able to read incoming and outgoing stock and should not be able to change any data on any incoming or outgoing stock as well as how much of each item the warehouse has.

This system will be used purely to keep records of stock and does not directly communicate with other applications for the actual ordering process. It will need to store the names of the type of stock as well as the amount of each and optionally set up a lower limit for each stock item for which a notification will be sent if the item goes below that limit. These will need to be input by the inventory managers manually.

This system will be an improvement over other systems due to its centralized nature and will allow regular warehouse workers to be kept up to date on incoming stock added by managers. It will be superior to the old paper-based ledger systems which are harder to look up specific data in, and other spreadsheet applications which are often hard to read at a glance.

This project will be deemed successful if firstly, both inventory managers and workers prefer this system over other contemporary options, and secondly, if the system can consistently keep track of all necessary inventory and order data without any errors. The first point can be decided via survey, and the second will be decided via vigorous testing throughout the creation of the project to ensure data is stored and modified correctly.

## Features

### Account Permissions

There will be one administrator of the Inventory and all the account permission requests will be emailed to him. The app will   prompt the user to enter first name, last name and employee ID and email address. He will get the response from the administrator with the credentials of his account. This feature will take 1 day to implement.

### View Inventory

A user can view the whole inventory stored in the warehouse. They can view individual items, view all different items in the warehouse, or view different items under the same category. This feature will take 3 days to implement.

### Re-Ordering

A user can reorder an item that already exists in the warehouse. The user can reorder a specified quantity for a single item or multiple different items. This feature will take 2 days to implement.

### Order Automation 

Some of the items(selected by user) will be automatically added to the cart everyday. The user can edit those items or add new 
items and then place the order. The order wont be placed unless and until its confirmed by the user. This functionality will take two days to implement.

### Reports

There will be weekly reports on the order history. It will specify the quantity of product orders and quantity of product 
scraped(damaged items). It will be a tabular report which can be used to recognize popular items or non-popular items. This 
feature will take 2 days to implement.

## Iteration 1 User Stories

### View Inventory

1. As an inventory management worker, I want to be able to view individual items stored in the warehouse.
	- Priority: HIGH
	- Time Estimate: 1 day
2. As an inventory management worker, I want to be able to view all the items stored in the warehouse.
	- Priority: HIGH
	- Time Estimate: 1 day
3. As an inventory management worker, I want to be able to view all the items sorted by category.
	- Priority: LOW
	- Time Estimate: 1 day

### Re-Ordering

1. As an inventory management worker, I want to be able to reorder a specified quantity of a single existing item to the warehouse.
	- Priority: HIGH
	- Time Estimate: 1 day
2. As an inventory management worker, I want to be able to reorder specified quantities of multiple different existing items to the warehouse.
	- Priority: LOW
	- Time Estimate: 1 day
