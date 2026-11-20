# Reflection
**How did your design absorb the change?**
The change made was the addition of two basic GUI's (`BaristaWindow.java` and `CustomerWindow.java`) that act as views. Because these changes were views, they only touched the controller (`JavaHouse.java`), which in turn interacted with the model classes. This change opposed the originally planned change, which was to implement customization, which would have affected the `Item.java` and `Order.java` classes.

**Did the change cross the model/view line?**
The change did not cross the model/view line; instead, the GUI's *were* the views. To ensure that no changes affected anything they shouldn't have, careful consideration was taken to ensure the view solely interacted with the controller.

**What changed from your D3 plan?**
We initially planned to implement customization in this deliverable, however we decided to take our initial views and expand them in order to create two detailed GUI's with data that syncs across them, displayed appropriately for the target audience of either. 

**What doesn't work yet?**
At this current point, all features and cases should act as intended.



# Next Steps
**What is left for the final build?**
No originally-planned MVP features are left to be completed for the final build.

**Which stretch goals are you committing to?** 
We are committing to implementing item customization and trivial edits to the GUI, mostly for aesthetic purposes.

**What will your 3-minute demo show?"** 
The lifecycle of an order in the app across both views: the customer makes an order, the barista starts the order, the customer views the order queue, the barista completes the order, and the customer removes the order from the pick-up queue using their identification.

**Who does what?**
- Naomika: Implementing item customization
- Leo: Visual edits to the GUI (`BaristaWindow.java`)
- Daana: Visual edits to the GUI (`CustomerWindow.java`)



# Attribution & GenAI Note

| File | Team Member(s) | Contribution |
| :--- | :--- | :--- |
| `Item.java` | Naomika Aithani and Daana Doddabendigere | Wrote class, constructor, and methods |
| `ItemTest.java` | Naomika Aithani | Wrote JUnit tests for list methods and exceptions |
| `Order.java` | Naomika Aithani and Daana Doddabendigere | Wrote class, constructors, and methods |
| `OrderTest.java` | Naomika Aithani | Wrote JUnit tests for list methods and exceptions |
| `LinkedQueue.java` | Naomika Aithani | Recycled and adapted generic linked queue implementation from prior assignment |
| `LinkedQueueTest.java` | Naomika Aithani | Wrote JUnit tests for queue operations and exceptions |
| `DoublyLinkedList.java` | Naomika Aithani | Recycled and adapted generic doubly linked list with minimal edits |
| `DoublyLinkedListTest.java` | Naomika Aithani | Wrote JUnit tests for list methods and exceptions |
| `JavaHouseModel.java` | Naomika Aithani | Wrote model state management methods and a standalone main method, used AI to ensure methods were valid and structure would fit the rubric criteria |
| `JavaHouseModelTest.java` | Naomika Aithani | Wrote JUnit tests for order submission, stock limits, queue progression, and pickup |
| `CustomerWindow.java` | Leo Quezada-Rocha | Created with GenAI and reviewed by Leo |
| `CustomerWindowTest.java` | Leo Quezada-Rocha | Wrote JUnit tests for cart updates, totals, clearing, and pickup rendering; updated with GenAI assistance for decoupled signatures |
| `BaristaWindow.java` | Leo Quezada-Rocha | Created with GenAI and reviewed by Leo  |
| `BaristaWindowTest.java` | Leo Quezada-Rocha | Wrote JUnit tests for updates on the order and pickup status|
| `JavaHouse.java` | Daana Doddabendigere | Wrote class, constructor, and methods and used GenAI to draft the order, queue, and pickup methods and debug compile errors |
| `README.md` | Naomika Aithani | Drafted Reflection, Next Steps, and Attribution |
