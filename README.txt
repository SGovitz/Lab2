Chosen Patterns and Advantages
Factory Method encapsulates event creation behind an EventFactory interface, letting the UI (addEventModal) ask for an event without knowing its concrete class. This decouples modal-dialog logic from constructors and makes it trivial to add new event types.
Strategy encapsulates each sorting algorithm in its own SortStrategy class (e.g. SortByDateAscending, SortByNameDescending), removing the big switch-statement in EventListPanel and making it easy to add or change sorting behavior at runtime.

Implementation
An EventFactory interface was introduced alongside two concrete factories: DeadlineFactory and MeetingFactory. In addEventModal.java, a Map<JRadioButton,EventFactory> is populated in initComponents(), and addEvent() simply looks up the selected factory and calls createEvent(...).
Similarly, a SortStrategy interface was created with four implementations (SortByDateAscending, SortByDateDescending, SortByNameAscending, SortByNameDescending). In EventListPanel.java, a Map<String,SortStrategy> is initialized in the constructor, the combo-box is built from its keySet, and its addActionListener invokes the chosen strategy’s sort(events) before refreshing the display.

Implemented Changes
Created EventFactory, DeadlineFactory, MeetingFactory in src/ and refactored addEventModal.java to use factory lookup instead of new Deadline(...)/new Meeting(...).
Created SortStrategy plus four concrete classes and refactored EventListPanel.java to remove its old sortEvents(...) method and wire the strategy map directly to the combo-box selection.

Repository
https://github.com/SGovitz/Lab2