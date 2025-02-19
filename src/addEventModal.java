import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class addEventModal extends JDialog {
    private EventListPanel eventListPanel;
    private JRadioButton deadlineRadio;
    private JRadioButton meetingRadio;
    private JTextField nameField;
    private JTextField startField;   // Expected format: yyyy-MM-dd HH:mm
    private JTextField endField;     // Only for meetings
    private JTextField locationField;// Only for meetings
    private JButton addButton;
    private JButton cancelButton;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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

        // Type selection panel.
        JPanel typePanel = new JPanel();
        deadlineRadio = new JRadioButton("Deadline", true);
        meetingRadio = new JRadioButton("Meeting");
        ButtonGroup group = new ButtonGroup();
        group.add(deadlineRadio);
        group.add(meetingRadio);
        typePanel.add(deadlineRadio);
        typePanel.add(meetingRadio);
        add(typePanel, BorderLayout.NORTH);

        // Form panel for event details.
        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Start (yyyy-MM-dd HH:mm):"));
        startField = new JTextField();
        formPanel.add(startField);

        formPanel.add(new JLabel("End (for Meeting):"));
        endField = new JTextField();
        formPanel.add(endField);

        formPanel.add(new JLabel("Location (for Meeting):"));
        locationField = new JTextField();
        formPanel.add(locationField);
        add(formPanel, BorderLayout.CENTER);

        // Button panel.
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions.
        addButton.addActionListener(e -> addEvent());
        cancelButton.addActionListener(e -> dispose());

        // Enable/disable meeting-specific fields.
        deadlineRadio.addActionListener(e -> {
            endField.setEnabled(false);
            locationField.setEnabled(false);
        });
        meetingRadio.addActionListener(e -> {
            endField.setEnabled(true);
            locationField.setEnabled(true);
        });
        // Initially, deadline is selected.
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
            if (deadlineRadio.isSelected()) {
                Deadline deadline = new Deadline(name, startDateTime);
                eventListPanel.addEvent(deadline);
            } else if (meetingRadio.isSelected()) {
                String endStr = endField.getText().trim();
                String location = locationField.getText().trim();
                if (endStr.isEmpty() || location.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "End time and Location are required for a meeting.");
                    return;
                }
                LocalDateTime endDateTime = LocalDateTime.parse(endStr, formatter);
                Meeting meeting = new Meeting(name, startDateTime, endDateTime, location);
                eventListPanel.addEvent(meeting);
            }
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error parsing date/time. Please use format yyyy-MM-dd HH:mm");
        }
    }
}
