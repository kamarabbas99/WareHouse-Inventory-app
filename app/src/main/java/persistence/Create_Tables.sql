CREATE TABLE Items (
	ItemID INTEGER NOT NULL,
	Name varchar(255) NOT NULL,
	Description varchar(255),
	QuantityMetric varchar(255) NOT NULL DEFAULT 'unit',
	PRIMARY KEY (ItemID)
);

CREATE TABLE Accounts (
	AccountID INTEGER NOT NULL,
	Username varchar(255) NOT NULL,
	AccountPassword varchar(255) NOT NULL DEFAULT '1234',
	Company varchar(255) DEFAULT 'University of Manitoba',
	DateCreated datetime DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (AccountID)
);
	
CREATE TABLE Inventories (
	InventoryID INTEGER NOT NULL,
	Name varchar(255) NOT NULL,
	Company varchar(255) NOT NULL DEFAULT 'University of Manitoba',
	DateCreated datetime DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (InventoryID)
);

CREATE TABLE InventoryManager (
	ItemID INTEGER NOT NULL,
	InventoryID INTEGER NOT NULL,
	Quantity INTEGER NOT NULL,
	LowThreshold INTEGER DEFAULT 0,
	PRIMARY KEY (ItemID, InventoryID),
	FOREIGN KEY (ItemID) REFERENCES Items,
	FOREIGN KEY (InventoryID) REFERENCES Inventories
);

CREATE TABLE Transactions (
	AccountID INTEGER NOT NULL,
	ItemID INTEGER NOT NULL,
	InventoryID INTEGER NOT NULL,
	TransactionID INTEGER NOT NULL,
	Quantity INTEGER NOT NULL,
	Type varchar(255) NOT NULL,
	Timestamp datetime DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (AccountID, InventoryID, ItemID, TransactionID),
	FOREIGN KEY (AccountID) REFERENCES Accounts,
	FOREIGN KEY (InventoryID) REFERENCES Inventories,
	FOREIGN KEY (ItemID) REFERENCES Items
);