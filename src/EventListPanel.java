import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events;
    private JPanel controlPanel;
    private JPanel displayPanel;
    private JComboBox<String> sortDropDown;
    private JCheckBox filterCompletedCheckBox;
    private JButton addEventButton;

    public EventListPanel() {
        events = new ArrayList<>();
        setLayout(new BorderLayout());

        // Top control panel.
        controlPanel = new JPanel();

        sortDropDown = new JComboBox<>(new String[]{
                "Sort by Date", "Sort by Date (Reverse)", "Sort by Name", "Sort by Name (Reverse)"
        });
        sortDropDown.addActionListener(e -> {
            sortEvents((String) sortDropDown.getSelectedItem());
            updateDisplayPanel();
        });
        controlPanel.add(sortDropDown);

        filterCompletedCheckBox = new JCheckBox("Hide Completed");
        filterCompletedCheckBox.addActionListener(e -> updateDisplayPanel());
        controlPanel.add(filterCompletedCheckBox);

        addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> {
            AddEventModal modal = new AddEventModal(this);
            modal.setVisible(true);
        });
        controlPanel.add(addEventButton);

        add(controlPanel, BorderLayout.NORTH);

        // Panel that holds the list of EventPanels.
        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(displayPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Add default events for demonstration.
        addDefaultEvents();
    }

    // Main method to launch the GUI.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Event Planner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new EventListPanel());
            frame.setVisible(true);
        });
    }

    // Add an event to the list and update the display.
    public void addEvent(Event event) {
        events.add(event);
        updateDisplayPanel();
    }

    private void sortEvents(String criteria) {
        switch (criteria) {
            case "Sort by Date":
                Collections.sort(events);
                break;
            case "Sort by Date (Reverse)":
                Collections.sort(events, Collections.reverseOrder());
                break;
            case "Sort by Name":
                events.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
                break;
            case "Sort by Name (Reverse)":
                events.sort((e1, e2) -> e2.getName().compareTo(e1.getName()));
                break;
        }
    }

    // Rebuilds the display panel from the events list.
    private void updateDisplayPanel() {
        displayPanel.removeAll();
        for (Event e : events) {
            if (filterCompletedCheckBox.isSelected() && (e instanceof Completable)
                    && ((Completable) e).isComplete()) {
                continue;
            }
            EventPanel eventPanel = new EventPanel(e);
            displayPanel.add(eventPanel);
        }
        displayPanel.revalidate();
        displayPanel.repaint();
    }

    // Adds some default events (one Deadline and one Meeting) for demonstration.
    private void addDefaultEvents() {
        Deadline deadline = new Deadline("Submit Report", LocalDateTime.now().plusDays(2));
        Meeting meeting = new Meeting("Project Meeting",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(1),
                "Meeting Room 1");
        addEvent(deadline);
        addEvent(meeting);
    }
}
