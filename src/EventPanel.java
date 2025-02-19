import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class EventPanel extends JPanel {
    private Event event;
    private JButton completeButton;
    private JLabel infoLabel;

    public EventPanel(Event event) {
        this.event = event;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        infoLabel = new JLabel(getEventInfo());
        add(infoLabel, BorderLayout.CENTER);

        // Only show the "Complete" button if the event is completable.
        if (event instanceof Completable) {
            completeButton = new JButton("Complete");
            completeButton.addActionListener(e -> {
                ((Completable) event).complete();
                updateDisplay();
            });
            add(completeButton, BorderLayout.EAST);
        }
    }

    // Build an HTML string to display the event’s information.
    private String getEventInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder("<html>");
        sb.append("Name: ").append(event.getName()).append("<br>");
        sb.append("Time: ").append(event.getDateTime().format(formatter)).append("<br>");

        if (event instanceof Meeting) {
            Meeting m = (Meeting) event;
            sb.append("End Time: ").append(m.getEndTime().format(formatter)).append("<br>");
            sb.append("Duration (min): ").append(m.getDuration().toMinutes()).append("<br>");
            sb.append("Location: ").append(m.getLocation()).append("<br>");
        }
        if (event instanceof Completable) {
            sb.append("Completed: ").append(((Completable) event).isComplete());
        }
        sb.append("</html>");
        return sb.toString();
    }

    // Refresh the display text.
    public void updateDisplay() {
        infoLabel.setText(getEventInfo());
        revalidate();
        repaint();
    }
}

