# Class
Item

## Fields
- int id
- string name
- string description
- int quantity
- string qtyMetric 
	- e.g "pkg", "unit".
- int lowThreshold

## Public Methods
- Item(**all fields**)
	- load the info from the database
	- may have overloads:
		a. null constructor for default values.
- bool IsEmpty()
	- is quantity zero?
- bool IsLow()
	- have a threshold.
- int GetID()
- string GetName()
- string GetDescription()
- int GetQuantity()
- string GetQtyMetric()
- int GetLowThreshold()
