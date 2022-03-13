INSERT INTO Items (Name, Description) 
VALUES
('ELDEN RING', 'Top game on Steam.'),
('Read Dead Redemption 2', 'Another top game on Steam.'),
('Core Keeper', 'Another top game on Steam.'),
('Grand Theft Auto V: Premium Edition', 'Another top game on Steam.'),
('WWE 2K22', 'Another top game on Steam.'),
('Dying Light 2 Stay Human', 'Another top game on Steam.'),
('Dread Hunger', 'Another top game on Steam.'),
('CS:GO Prime Status Upgrade', 'Another top game on Steam.'),
('Ready or Not', 'Another top game on Steam.'),
('Fall Guys: Ultimate Knockout', 'Another top game on Steam.');

INSERT INTO Accounts (Username, AccountPassword)
VALUES 
('mcquarrc', '1234'),
('lannona', '1234'),
('bhasinm', '1234'),
('saiyedka', '1234'),
('waraichy', '1234');

INSERT INTO Inventories (Name)
VALUES
('Games Inventory'),
('Hardware Inventory'),
('Restaurant Inventory'),
('School Inventory');

INSERT INTO InventoryManager (ItemID, InventoryID, Quantity)
VALUES
(1, 1, 10),
(2, 1, 15),
(3, 1, 20),
(4, 1, 25),
(5, 1, 30),
(6, 1, 35),
(7, 1, 40),
(8, 1, 45),
(9, 1, 50);

INSERT INTO Transactions (AccountID, ItemID, InventoryID, TransactionID, Quantity, Type)
VALUES
(1, 1, 1, 1, 10, 'add'),
(2, 2, 1, 2,  15, 'add'),
(3, 3, 1, 3,  20, 'add'),
(4, 4, 1, 4, 25, 'add'),
(5, 5, 1, 5, 30, 'add'),
(1, 6, 1, 1, 35, 'add'),
(2, 7, 1, 2, 40, 'add'),
(3, 8, 1, 3, 45, 'add'),
(4, 9, 1, 4, 50, 'add');