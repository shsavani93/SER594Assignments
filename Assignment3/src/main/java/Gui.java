import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author kambhampativenkatasuryashandilya ASU ID: 1219412519
 * @author Sneha Savani ASU ID: 1220549876
 * <p>
 * This class presents a Graphical User interface to the user where the user can enter the attributes of a car
 * and the model tells what type of car is it.
 */
public class Gui implements ActionListener {
    int[] inputValues = new int[6];
    JFrame mainFrame = new JFrame();
    JPanel panel = new JPanel();
    JPanel HeadingPanel = new JPanel();
    JPanel resultsPanel = new JPanel();
    JButton RunPredictionButton = new JButton("Run Prediction!");

    public static void main(String[] args) {
        Gui gui = new Gui();
    }

    public final HashMap<String, Integer> map = new HashMap<>();

    /**
     * Constructs the GUI and sets the attributes of the elements on the GUI.
     */
    public Gui() {

        map.put("Low", 2);
        map.put("Small", 3);
        map.put("Medium", 4);
        map.put("5AndMore", 5);
        map.put("More", 6);
        map.put("Big", 7);
        map.put("High", 8);
        map.put("VeryHigh", 9);

        JLabel heading = new JLabel("Car Classification Application", SwingConstants.CENTER);

        heading.setFont(new Font("Serif", Font.PLAIN, 60));
        heading.setForeground(Color.WHITE);

        JLabel description = new JLabel("Please enter the values for the below mentioned attributes and the model " +
                "will evaluate if the car is Acceptable/ Unaccaptable/good/very good", SwingConstants.CENTER);

        description.setFont(new Font("Serif", Font.PLAIN, 25));
        description.setForeground(Color.WHITE);

        JButton reset = new JButton("RESET");
        reset.addActionListener(this);

        JButton exit = new JButton("EXIT");
        exit.addActionListener(this);

        JPanel innerHeadingPanel = new JPanel();
        innerHeadingPanel.add(reset);
        innerHeadingPanel.add(exit);


        HeadingPanel.setLayout(new GridLayout(3, 0));

        panel.setLayout(new GridLayout(7, 2));
        HeadingPanel.setBackground(Color.GRAY);
        resultsPanel.setBackground(Color.YELLOW);

        JLabel buy = new JLabel("Buying cost: ");
        JLabel maint = new JLabel("Maintenance Complexity: ");
        JLabel doors = new JLabel("Number of doors:  ");
        JLabel persons = new JLabel("people capacity: ");
        JLabel leg = new JLabel("leg and boot space: ");
        JLabel safety = new JLabel("safety index: ");

        buy.setFont(new Font("Serif", Font.PLAIN, 20));
        maint.setFont(new Font("Serif", Font.PLAIN, 20));
        doors.setFont(new Font("Serif", Font.PLAIN, 20));
        persons.setFont(new Font("Serif", Font.PLAIN, 20));
        leg.setFont(new Font("Serif", Font.PLAIN, 20));
        safety.setFont(new Font("Serif", Font.PLAIN, 20));

        JMenuBar buyMenu = new JMenuBar();
        JMenuBar maintMenu = new JMenuBar();
        JMenuBar doorsMenu = new JMenuBar();
        JMenuBar personsMenu = new JMenuBar();
        JMenuBar legMenu = new JMenuBar();
        JMenuBar safetyMenu = new JMenuBar();

        JMenu buyMenuHeader = new JMenu("Please select one");
        JMenu mainMenuHeader = new JMenu("Please select one");
        JMenu doorsMenuHeader = new JMenu("Please select one");
        JMenu personMenuHeader = new JMenu("Please select one");
        JMenu legMenuHeader = new JMenu("Please select one");
        JMenu safetyMenuHeader = new JMenu("Please select one");

        JMenuItem lowCost = new JMenuItem("Low Cost");
        JMenuItem medCost = new JMenuItem("Medium Cost");
        JMenuItem highCost = new JMenuItem("High Cost");
        JMenuItem veryHighCost = new JMenuItem("VeryHigh Cost");

        JMenuItem lowMaint = new JMenuItem("Low Maintenance");
        JMenuItem medMaint = new JMenuItem("Medium Maintenance");
        JMenuItem highMaint = new JMenuItem("High maintenance");
        JMenuItem veryHighMaint = new JMenuItem("VeryHigh Maintenance");


        JMenuItem lowDoors = new JMenuItem("Low Doors");
        JMenuItem smallDoors = new JMenuItem("Small Doors");
        JMenuItem mediumDoors = new JMenuItem("Medium Doors");
        JMenuItem fiveAndMoreDoors = new JMenuItem("5AndMore Doors");


        JMenuItem lowCapacity = new JMenuItem("Low Capacity");
        JMenuItem medCapacity = new JMenuItem("Medium Capacity");
        JMenuItem moreCapacity = new JMenuItem("More Capacity");

        JMenuItem smallBoot = new JMenuItem("Small Boot");
        JMenuItem mediumBoot = new JMenuItem("Medium Boot");
        JMenuItem bigBoot = new JMenuItem("Big Boot");

        JMenuItem lowSafety = new JMenuItem("Low Safety");
        JMenuItem medSafety = new JMenuItem("Medium Safety");
        JMenuItem highSafety = new JMenuItem("High Safety");

        lowCost.addActionListener(this);
        medCost.addActionListener(this);
        highCost.addActionListener(this);
        veryHighCost.addActionListener(this);

        lowMaint.addActionListener(this);
        medMaint.addActionListener(this);
        highMaint.addActionListener(this);
        veryHighMaint.addActionListener(this);

        lowDoors.addActionListener(this);
        smallDoors.addActionListener(this);
        mediumDoors.addActionListener(this);
        fiveAndMoreDoors.addActionListener(this);

        lowCapacity.addActionListener(this);
        medCapacity.addActionListener(this);
        moreCapacity.addActionListener(this);

        smallBoot.addActionListener(this);
        mediumBoot.addActionListener(this);
        bigBoot.addActionListener(this);

        lowSafety.addActionListener(this);
        medSafety.addActionListener(this);
        highSafety.addActionListener(this);

        buyMenuHeader.add(lowCost);
        buyMenuHeader.add(medCost);
        buyMenuHeader.add(highCost);
        buyMenuHeader.add(veryHighCost);

        mainMenuHeader.add(lowMaint);
        mainMenuHeader.add(medMaint);
        mainMenuHeader.add(highMaint);
        mainMenuHeader.add(veryHighMaint);

        doorsMenuHeader.add(lowDoors);
        doorsMenuHeader.add(mediumDoors);
        doorsMenuHeader.add(smallDoors);
        doorsMenuHeader.add(fiveAndMoreDoors);

        personMenuHeader.add(lowCapacity);
        personMenuHeader.add(medCapacity);
        personMenuHeader.add(moreCapacity);

        legMenuHeader.add(smallBoot);
        legMenuHeader.add(mediumBoot);
        legMenuHeader.add(bigBoot);

        safetyMenuHeader.add(lowSafety);
        safetyMenuHeader.add(medSafety);
        safetyMenuHeader.add(highSafety);

        buyMenu.add(buyMenuHeader);
        maintMenu.add(mainMenuHeader);
        doorsMenu.add(doorsMenuHeader);
        personsMenu.add(personMenuHeader);
        legMenu.add(legMenuHeader);
        safetyMenu.add(safetyMenuHeader);

        HeadingPanel.add(heading);
        HeadingPanel.add(description);
        HeadingPanel.add(innerHeadingPanel);

        panel.add(maint);
        panel.add(maintMenu);
        panel.add(doors);
        panel.add(doorsMenu);
        panel.add(persons);
        panel.add(personsMenu);
        panel.add(leg);
        panel.add(legMenu);
        panel.add(safety);
        panel.add(safetyMenu);
        panel.add(buy);
        panel.add(buyMenu);

        resultsPanel.add(RunPredictionButton);
        RunPredictionButton.addActionListener(this);

        mainFrame.setLayout(new GridLayout(3, 0));
        mainFrame.add(HeadingPanel);
        mainFrame.add(panel);
        mainFrame.add(resultsPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    /**
     * This receives the input values entered by the user and sends them for post processing.
     *
     * @param e The Action that triggred this.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource().getClass());
        String[] input = e.getActionCommand().split(" ");
        if (e.getSource().getClass().toString().equals("class javax.swing.JMenuItem")) {
            switch (input[1]) {
                case "Cost" : inputValues[0] = map.get(input[0]);
                case "Maintenance" : inputValues[1] = map.get(input[0]);
                case "Doors" : inputValues[2] = map.get(input[0]);
                case "Capacity" : inputValues[3] = map.get(input[0]);
                case "Boot" : inputValues[4] = map.get(input[0]);
                case "Safety" : inputValues[5] = map.get(input[0]);
            }
            System.out.println("values are : ");
            for (Integer iterator : inputValues) {
                System.out.print(iterator);
            }

        } else if (e.getSource().getClass().toString().equals("class javax.swing.JButton") && e.getActionCommand().equals("Run Prediction!")) {
            boolean flag = false;
            for (Integer iterator : inputValues) {
                if (iterator == 0) {
                    JLabel label = new JLabel("Please give values to all the attributes");
                    label.setFont(new Font("Serif", Font.PLAIN, 40));
                    label.setForeground(Color.RED);
                    resultsPanel.add(label);
                    resultsPanel.repaint();
                    mainFrame.repaint();
                    mainFrame.setVisible(true);
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                resultsPanel.removeAll();
                JLabel label = new JLabel("Classification Running... Results will be available in a while");
                label.setForeground(Color.DARK_GRAY);
                resultsPanel.add(label);
                resultsPanel.repaint();
                mainFrame.repaint();
                mainFrame.setVisible(true);

                System.out.println("sending the following values for classification");
                for (Integer iterator : inputValues)
                    System.out.println(iterator);

                Model m = new Model();
                try {
                    m.model();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                int result = m.getOutput(inputValues);
                if (result == 0) {
                    resultsPanel.removeAll();
                    resultsPanel.setBackground(Color.RED);
                    JLabel label2 = new JLabel("The car is Unacceptable");
                    label2.setFont(new Font("Serif", Font.PLAIN, 60));
                    label2.setForeground(Color.WHITE);
                    resultsPanel.add(label2);
                    resultsPanel.repaint();
                    mainFrame.repaint();
                    mainFrame.setVisible(true);

                } else if (result == 1) {

                    JLabel label3 = new JLabel("The car is Just Acceptable");
                    resultsPanel.removeAll();
                    resultsPanel.setBackground(Color.CYAN);
                    label3.setForeground(Color.BLACK);
                    label3.setFont(new Font("Serif", Font.PLAIN, 60));
                    resultsPanel.add(label3);
                    resultsPanel.repaint();
                    mainFrame.repaint();
                    mainFrame.setVisible(true);

                } else if (result == 2) {
                    resultsPanel.removeAll();
                    JLabel label4 = new JLabel("The car is good !!!");
                    label4.setForeground(Color.WHITE);
                    resultsPanel.setBackground(Color.GREEN);
                    label4.setFont(new Font("Serif", Font.PLAIN, 60));
                    resultsPanel.add(label4);
                    resultsPanel.repaint();
                    mainFrame.repaint();
                    mainFrame.setVisible(true);

                } else {
                    resultsPanel.removeAll();
                    JLabel label5 = new JLabel("The Car is Very Good. Go for It!!!!");
                    label5.setForeground(Color.WHITE);
                    label5.setFont(new Font("Serif", Font.PLAIN, 60));
                    resultsPanel.setBackground(Color.GREEN);
                    resultsPanel.add(label5);
                    resultsPanel.repaint();
                    mainFrame.repaint();
                    mainFrame.setVisible(true);

                }
            }
        } else if (e.getSource().getClass().toString().equals("class javax.swing.JButton") && e.getActionCommand().equals("RESET")) {
            panel.repaint();
            resultsPanel.setBackground(Color.YELLOW);
            resultsPanel.removeAll();
            resultsPanel.add(RunPredictionButton);
            panel.repaint();
            resultsPanel.repaint();
            mainFrame.repaint();
            mainFrame.setVisible(true);
            Arrays.fill(inputValues, 0);
        } else if (e.getSource().getClass().toString().equals("class javax.swing.JButton") && e.getActionCommand().equals("EXIT")) {
            System.exit(0);
        }
    }
}
