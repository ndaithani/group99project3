# Reflection

**What Changed?**
* Use of ArrayLists and Arrays to manage item data to allow for dynamic editing of a selection then permanent instatiation.
* Removed direct model-view interaction in order to adhere to MVC standards.
* Status normalization was instantiated in order to allow for more sta
* Indexed getters were implemented to DoublyLinkedList to create string lists.

**Does the model stand alone?**
Yes, the model has zero GUI dependencies and runs independently in a terminal via the main method. 

**What happened to your unknowns?**
We learned how to continuously update the line to keep both GUIs synchronized. We plan to explore order customization next to continually learn, however we decided that a real-time timer loop is no longer needed due to interactive barista controls.

**What doesn't work yet?**
* Null checks in the view must be implemented.
* Name validation must be implemented.
* JUnit testing for all methods with full coverage must be implemented.



# Next Steps

**What is left to finish the MVP?**
* Add validation of names and other potentially-null parameters to the view.
* Complete JUnit testing across `Item`, `Order`, and `JavaHouse` controller edge cases.
* Ensure testing is complete and fully covers the app's code.

**What will we add to Deliverable 4?**
* Order customization
  * Requires changes made to Item's fields and toString() method, JavaHouseModel's submitOrder() method, JavaHouse's handleAddItem()/handleSubmitOrder() methods, and CustomerWindow's GUI.
  * LinkedQueue, DoublyLinkedList, Order, and BaristaWindow may stay the same because none of the first three directly interact with Item's properties, only hold Orders or Items respectively. The fourth may stay the same due to it only displaying the data provided by the Items in each Order, not possibilities.

**Which stretch goals will you attempt?**
We will be attempting to integrate order customization.

**What new tests will you need?**
Tests that verify new customization methods and pricing calculations, especially validation for invalid or null customization selections.

**Who will do what?**
* **Leo Quezada-Rocha:** Implement null-checks in GUI and make aesthetic edits.
* **Sachin Nimmalapudi:** Implement testing for his methods.
* **Naomika Aithani:** Ensure integration with optimized data management.
* **Daana Doddabendigere:** Implement customization.

# Attribution & GenAI Note

| File | Team Member(s) | Contribution |
| :--- | :--- | :--- |
| `Item.java` | Naomika Aithani | Wrote class, constructors, stock guards, and getters |
| `Order.java` | Naomika Aithani | Wrote class, status constants/normalization, defensive array copying, and overloaded constructors |
| `LinkedQueue.java` | Naomika Aithani | Recycled and adapted generic linked queue implementation from prior assignment |
| `LinkedQueueTest.java` | Naomika Aithani | Wrote JUnit tests for queue operations and exception cases |
| `DoublyLinkedList.java` | Naomika Aithani | Recycled and adapted generic doubly linked list with minimal edits |
| `DoublyLinkedListTest.java` | Naomika Aithani | Wrote JUnit tests for list operations, bounds, and removal |
| `JavaHouseModel.java` | Naomika Aithani | Wrote model state management methods and a standalone main method, used AI to ensure methods were valid and structure would fit the rubric criteria |
| `JavaHouseModelTest.java` | Naomika Aithani | Wrote JUnit tests for order submission, stock limits, queue progression, and pickup |
| `CustomerWindow.java` | Leo Quezada-Rocha | Created with GenAI and looked over by Leo |
| `CustomerWindowTest.java` | Leo Quezada-Rocha | Wrote JUnit tests for cart updates, totals, clearing, and pickup rendering; updated with GenAI assistance for decoupled signatures |
| `BaristaWindow.java` | Leo Quezada-Rocha | Created with GenAI and looked over by Leo  |
| `BaristaWindowTest.java` | Leo Quezada-Rocha | Wrote Junit tests|
| `JavaHouse.java` | Daana Doddabendigere | |
| `README.md` | Naomika Aithani | Drafted Reflection, Next Steps, and Attribution |
