package Main.Swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelController extends JFrame {

    private JPanel contentPane;
    private JTextField textStartDate;
    private JTextField textEndDate;
    private JTextField textTaskDesc;
    private JTextField textOperationCost;
    private JTextField textOperationDesc;
    private JTextField textPlanedCount;
    private JTextField textTaskInfo;
    private JTextField textOperationInfo;
    private JTextField textFactCount;

    public void mainPanelOutput() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PanelController frame = new PanelController();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public PanelController() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 640, 564);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JList listTask = new JList();
        listTask.setBounds(10, 11, 200, 250);
        contentPane.add(listTask);

        JButton btnOpenTask = new JButton("Open");
        btnOpenTask.setBounds(220, 35, 182, 23);
        contentPane.add(btnOpenTask);

        JButton btnShowClosed = new JButton("Show Opened");
        btnShowClosed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        btnShowClosed.setBounds(220, 69, 182, 23);
        contentPane.add(btnShowClosed);

        textStartDate = new JTextField();
        textStartDate.setBounds(220, 131, 86, 20);
        contentPane.add(textStartDate);
        textStartDate.setColumns(10);

        textEndDate = new JTextField();
        textEndDate.setColumns(10);
        textEndDate.setBounds(316, 131, 86, 20);
        contentPane.add(textEndDate);

        JButton btnShowBetwineTime = new JButton("Show");
        btnShowBetwineTime.setBounds(268, 162, 89, 23);
        contentPane.add(btnShowBetwineTime);

        JTextPane txtpnTimeFormat = new JTextPane();
        txtpnTimeFormat.setBackground(SystemColor.menu);
        txtpnTimeFormat.setText("DateTime form 01-01-19-12-12");
        txtpnTimeFormat.setBounds(220, 103, 182, 20);
        contentPane.add(txtpnTimeFormat);

        JList listOperations = new JList();
        listOperations.setBounds(412, 11, 200, 250);
        contentPane.add(listOperations);

        textTaskDesc = new JTextField();
        textTaskDesc.setBounds(10, 389, 182, 91);
        contentPane.add(textTaskDesc);
        textTaskDesc.setColumns(10);

        JTextPane txtpnTaskDescription = new JTextPane();
        txtpnTaskDescription.setBackground(SystemColor.menu);
        txtpnTaskDescription.setText("Task Description");
        txtpnTaskDescription.setBounds(10, 368, 86, 20);
        contentPane.add(txtpnTaskDescription);

        textOperationCost = new JTextField();
        textOperationCost.setBounds(412, 409, 86, 20);
        contentPane.add(textOperationCost);
        textOperationCost.setColumns(10);

        JTextPane txtpnTaskCost = new JTextPane();
        txtpnTaskCost.setText("Operation Cost");
        txtpnTaskCost.setBackground(SystemColor.menu);
        txtpnTaskCost.setBounds(412, 389, 86, 20);
        contentPane.add(txtpnTaskCost);

        textOperationDesc = new JTextField();
        textOperationDesc.setColumns(10);
        textOperationDesc.setBounds(220, 389, 182, 91);
        contentPane.add(textOperationDesc);

        JTextPane txtpnOperationDescription = new JTextPane();
        txtpnOperationDescription.setText("Operation Description");
        txtpnOperationDescription.setBackground(SystemColor.menu);
        txtpnOperationDescription.setBounds(220, 368, 182, 20);
        contentPane.add(txtpnOperationDescription);

        textPlanedCount = new JTextField();
        textPlanedCount.setColumns(10);
        textPlanedCount.setBounds(412, 460, 86, 20);
        contentPane.add(textPlanedCount);

        JTextPane txtpnPlaned = new JTextPane();
        txtpnPlaned.setText("Planed Count");
        txtpnPlaned.setBackground(SystemColor.menu);
        txtpnPlaned.setBounds(412, 440, 86, 20);
        contentPane.add(txtpnPlaned);

        JButton btnCreateTask = new JButton("Create Task");
        btnCreateTask.setBounds(10, 491, 105, 23);
        contentPane.add(btnCreateTask);

        JButton btnCreateOperation = new JButton("CreateOperation");
        btnCreateOperation.setBounds(125, 491, 105, 23);
        contentPane.add(btnCreateOperation);

        textTaskInfo = new JTextField();
        textTaskInfo.setBounds(10, 272, 200, 85);
        contentPane.add(textTaskInfo);
        textTaskInfo.setColumns(10);

        textOperationInfo = new JTextField();
        textOperationInfo.setColumns(10);
        textOperationInfo.setBounds(412, 272, 200, 85);
        contentPane.add(textOperationInfo);

        JButton btnOpenOperation = new JButton("Open");
        btnOpenOperation.setBounds(220, 249, 182, 23);
        contentPane.add(btnOpenOperation);

        textFactCount = new JTextField();
        textFactCount.setColumns(10);
        textFactCount.setBounds(220, 303, 182, 20);
        contentPane.add(textFactCount);

        JTextPane txtpnFactCount = new JTextPane();
        txtpnFactCount.setText("Fact Count");
        txtpnFactCount.setBackground(SystemColor.menu);
        txtpnFactCount.setBounds(220, 283, 182, 20);
        contentPane.add(txtpnFactCount);

        JButton btnClose = new JButton("Close");
        btnClose.setBounds(220, 334, 182, 23);
        contentPane.add(btnClose);

        JTextPane txtpnForTasks = new JTextPane();
        txtpnForTasks.setBackground(SystemColor.menu);
        txtpnForTasks.setText("For Tasks");
        txtpnForTasks.setBounds(278, 11, 67, 20);
        contentPane.add(txtpnForTasks);

        JTextPane textForOperations = new JTextPane();
        textForOperations.setText("ForOperations");
        textForOperations.setBackground(SystemColor.menu);
        textForOperations.setBounds(278, 218, 67, 20);
        contentPane.add(textForOperations);
    }


}
