import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class EventListPanel extends JPanel {
    private final List<Event> events;
    private JPanel controlPanel;
    private JPanel displayPanel;
    private JComboBox<String> sortDropDown;
    private JCheckBox filterCompletedCheckBox;
    private JButton addEventButton;
    private Map<String, SortStrategy> sortStrategies;

    public EventListPanel() {
        events = new ArrayList<>();
        setLayout(new BorderLayout());

        // Top control panel
        controlPanel = new JPanel();

        // Initialize sorting strategies
        sortStrategies = new LinkedHashMap<>();
        sortStrategies.put("Sort by Date", new SortByDateAscending());
        sortStrategies.put("Sort by Date (Reverse)", new SortByDateDescending());
        sortStrategies.put("Sort by Name", new SortByNameAscending());
        sortStrategies.put("Sort by Name (Reverse)", new SortByNameDescending());

        // Create combo box from strategy keys
        sortDropDown = new JComboBox<>(sortStrategies.keySet().toArray(new String[0]));
        sortDropDown.addActionListener(e -> {
            SortStrategy strategy = sortStrategies.get(sortDropDown.getSelectedItem());
            strategy.sort(events);
            updateDisplayPanel();
        });
        controlPanel.add(sortDropDown);

        // Filter completed events checkbox
        filterCompletedCheckBox = new JCheckBox("Hide Completed");
        filterCompletedCheckBox.addActionListener(e -> updateDisplayPanel());
        controlPanel.add(filterCompletedCheckBox);

        // Add Event button
        addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> {
            addEventModal modal = new addEventModal(this);
            modal.setVisible(true);
        });
        controlPanel.add(addEventButton);

        add(controlPanel, BorderLayout.NORTH);

        // Display panel inside a scroll pane
        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(displayPanel);
        add(scrollPane, BorderLayout.CENTER);

        addDefaultEvents();
    }

    // Launch the GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Event Planner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new EventListPanel());
            frame.setVisible(true);
        });
    }

    // Add an event and refresh
    public void addEvent(Event event) {
        events.add(event);
        updateDisplayPanel();
    }

    // Refresh the display panel
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

    // Add sample events
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
