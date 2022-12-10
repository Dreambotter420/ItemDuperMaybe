package item.duper.maybe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JEditorPane;
import javax.swing.DropMode;
import java.awt.TextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;

public class CopyOfGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CopyOfGUI frame = new CopyOfGUI();
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
	public CopyOfGUI() {
		setTitle("Item Duper (Maybe)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setBackground(new Color(128, 128, 128));
		textArea.setText("accountName1\r\naccountName2\r\netc");
		textArea.setBounds(44, 64, 380, 160);
		contentPane.add(textArea);
		
		JLabel lblNewLabel = new JLabel("Here you can input the names of accounts to trade.");
		lblNewLabel.setBounds(44, 11, 359, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblTheSyntaxIs = new JLabel("The syntax is one in-game username per line.");
		lblTheSyntaxIs.setBounds(44, 25, 359, 14);
		contentPane.add(lblTheSyntaxIs);
		
		JLabel lblPleaseKeepThe = new JLabel("Please keep the order of names the same");
		lblPleaseKeepThe.setBounds(44, 44, 359, 14);
		contentPane.add(lblPleaseKeepThe);
		
		JButton btnStartButton = new JButton("Start Maybe Duping");
		btnStartButton.setBounds(10, 230, 414, 23);
		contentPane.add(btnStartButton);
	}
}
