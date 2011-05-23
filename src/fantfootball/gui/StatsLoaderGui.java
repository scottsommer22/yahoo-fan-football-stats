package fantfootball.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import fantfootball.stats.YahooStatsSaver;
import fantfootball.stats.settings.Settings;

public class StatsLoaderGui extends JFrame {

    private static final long serialVersionUID = 1L;

    private YahooStatsSaver controller = new YahooStatsSaver();

    private JLabel leagueKeyLabel = new JLabel("League key:");
    private JTextField leagueKey = new JTextField();
    private JLabel weekLabel = new JLabel("Weeks:");
    private JLabel weekSplitLabel = new JLabel("to");
    private JTextField fromWeek = new JTextField(3);
    private JTextField toWeek = new JTextField(3);
    private JLabel locationLabel = new JLabel("File:");
    private JTextField location = new JTextField();
    private JLabel cookieLabel = new JLabel("Cookie:");
    private JTextArea cookie = new JTextArea(40, 20);
    private JLabel seperator = new JLabel();
    private JButton saveToFile = new JButton("Save to file");

    public StatsLoaderGui() {
        super("Yahoo FF Stats");

        initialize();
    }

    private void initialize() {

        cookie.setLineWrap(true);
        cookie.setWrapStyleWord(true);

        // Add action listener to button
        saveToFile.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                getData();
            }
        });

        loadSettings();

    }

    private void getData() {
        String league = leagueKey.getText();
        String cookieValue = cookie.getText();
        int fromWeekValue = Integer.parseInt(fromWeek.getText());
        int toWeekValue = Integer.parseInt(toWeek.getText());
        String fileName = location.getText();

        controller.saveYahooPlayerStats(new Settings(league, cookieValue, fromWeekValue, toWeekValue, fileName));
    }

    public void addComponentsToFrame() {

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(leagueKeyLabel).addComponent(weekLabel).addComponent(
                        locationLabel).addComponent(cookieLabel).addComponent(seperator)).addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(leagueKey).addGroup(
                        layout.createSequentialGroup().addComponent(fromWeek).addComponent(weekSplitLabel).addComponent(toWeek)).addComponent(
                        location).addComponent(cookie).addGroup(layout.createSequentialGroup().addComponent(saveToFile))));

        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(leagueKeyLabel).addComponent(leagueKey)).addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(weekLabel).addComponent(fromWeek)
                        .addComponent(weekSplitLabel).addComponent(toWeek)).addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(locationLabel).addComponent(location)).addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(cookieLabel).addComponent(cookie)).addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)).addComponent(seperator).addComponent(saveToFile));

    }

    private void loadSettings() {
        Settings settings = controller.getSettings();
        leagueKey.setText(settings.getLeague());
        cookie.setText(settings.getCookie());
        fromWeek.setText("" + settings.getFromWeek());
        toWeek.setText("" + settings.getToWeek());
        location.setText(settings.getLocation());
    }

    public static void createAndShow() {
        StatsLoaderGui frame = new StatsLoaderGui();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add content to the window.
        frame.addComponentsToFrame();

        // Display the window.
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StatsLoaderGui.createAndShow();
            }
        });
    }

}
