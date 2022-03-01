# Warehouse Inventory System (WIS)
The Warehouse Inventory System (WIS) will be used to maintain information about the contents of a user's warehouse and allow them to manage incoming and outgoing stock. The system will keep track of individual shipments into the warehouse and add them to the overall warehouse stock. It will also allow users to remove stock when sending out goods to their customers or if for some reason some warehouse stock needs to be thrown out. 

The system is designed primarily for warehouse owners and inventory management staff. It will allow them to track what goods have been ordered and add them to the current stock when the orders arrive. It will also let them remove parts of stock when goods are sent out of the warehouse to their clients. The system will also allow for notifications to be set up when certain goods (selected by the user) start to run low and need to be reordered, reducing the chance of running out of stock of a certain item. Warehouse workers could also use the system, allowing them to see what goods are coming into the warehouse and will need to be stored.

Different levels of clearance are required for the types of users in the system. Inventory managers will need to have the highest clearance which will allow them to add incoming stock and remove outgoing stock, while regular warehouse workers will only be able to read incoming and outgoing stock and should not be able to change any data on any incoming or outgoing stock as well as how much of each item the warehouse has.

This system will be used purely to keep records of stock and does not directly communicate with other applications for the actual ordering process. It will need to store the names of the type of stock as well as the amount of each and optionally set up a lower limit for each stock item for which a notification will be sent if the item goes below that limit. These will need to be input by the inventory managers manually.

This system will be an improvement over other systems due to its centralized nature and will allow regular warehouse workers to be kept up to date on incoming stock added by managers. It will be superior to the old paper-based ledger systems which are harder to look up specific data in, and other spreadsheet applications which are often hard to read at a glance.

This project will be deemed successful if firstly, both inventory managers and workers prefer this system over other contemporary options, and secondly, if the system can consistently keep track of all necessary inventory and order data without any errors. The first point can be decided via survey, and the second will be decided via vigorous testing throughout the creation of the project to ensure data is stored and modified correctly.

## Branching Strategy
We are following a strategy similiar to the Git branching strategy. The two main branches to be aware of are:

1. The `main` branch. 
	- Where fully working releases for each iteration will be. 
2. The `development` branch.
	- Where current development is being done. 

In addition to these two main branches, each new feature will have their own branch that is created from the `development` branch. The specific names of these branches are determined by the team working on it. Once that branch has its work completed, it will then be merged into the 'development' branch and deleted.