import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Dialog to add new events via Factory Method
 */
public class addEventModal extends JDialog {
    private final EventListPanel eventListPanel;
    private JRadioButton deadlineRadio;
    private JRadioButton meetingRadio;
    private JTextField nameField;
    private JTextField startField;
    private JTextField endField;
    private JTextField locationField;
    private JButton addButton;
    private JButton cancelButton;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Map of radio buttons to their factories
    private Map<JRadioButton, EventFactory> factories;

    public addEventModal(EventListPanel eventListPanel) {
        this.eventListPanel = eventListPanel;
        setTitle("Add Event");
        setModal(true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Type selection
        JPanel typePanel = new JPanel();
        deadlineRadio = new JRadioButton("Deadline", true);
        meetingRadio = new JRadioButton("Meeting");
        ButtonGroup group = new ButtonGroup();
        group.add(deadlineRadio);
        group.add(meetingRadio);
        typePanel.add(deadlineRadio);
        typePanel.add(meetingRadio);
        add(typePanel, BorderLayout.NORTH);

        // Initialize factories
        factories = new HashMap<>();
        factories.put(deadlineRadio, new DeadlineFactory());
        factories.put(meetingRadio, new MeetingFactory());

        // Form inputs
        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField(); formPanel.add(nameField);
        formPanel.add(new JLabel("Start (yyyy-MM-dd HH:mm):"));
        startField = new JTextField(); formPanel.add(startField);
        formPanel.add(new JLabel("End (for Meeting):"));
        endField = new JTextField(); formPanel.add(endField);
        formPanel.add(new JLabel("Location (for Meeting):"));
        locationField = new JTextField(); formPanel.add(locationField);
        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        addButton.addActionListener(e -> addEvent());
        cancelButton.addActionListener(e -> dispose());
        deadlineRadio.addActionListener(e -> {
            endField.setEnabled(false);
            locationField.setEnabled(false);
        });
        meetingRadio.addActionListener(e -> {
            endField.setEnabled(true);
            locationField.setEnabled(true);
        });

        // Initial state
        endField.setEnabled(false);
        locationField.setEnabled(false);
    }

    private void addEvent() {
        String name = nameField.getText().trim();
        String startStr = startField.getText().trim();
        if (name.isEmpty() || startStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Start time are required.");
            return;
        }
        try {
            LocalDateTime startDateTime = LocalDateTime.parse(startStr, formatter);
            EventFactory factory = deadlineRadio.isSelected()
                    ? factories.get(deadlineRadio)
                    : factories.get(meetingRadio);
            LocalDateTime endDateTime = null;
            String location = null;
            if (meetingRadio.isSelected()) {
                String endStr = endField.getText().trim();
                location = locationField.getText().trim();
                if (endStr.isEmpty() || location.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "End time and Location are required for a meeting.");
                    return;
                }
                endDateTime = LocalDateTime.parse(endStr, formatter);
            }
            Event e = factory.createEvent(name, startDateTime, endDateTime, location);
            eventListPanel.addEvent(e);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error parsing date/time. Please use format yyyy-MM-dd HH:mm");
        }
    }
}

