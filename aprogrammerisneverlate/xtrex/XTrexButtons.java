import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;


public class XTrexButtons extends JPanel implements ActionListener {
	
	JButton powerBtn = new JButton("On/Off");
	JButton menuBtn = new JButton("Menu");
	JButton minusBtn = new JButton("-");
	JButton plusBtn = new JButton("+");
	JButton selectBtn = new JButton("Select");
	
	XTrexButtonsBehaviour xtbb;
	
	XTrexButtons(XTrexButtonsBehaviour xtbb) {
		super(new BorderLayout());
		
		this.xtbb = xtbb;
		
		JToolBar xTrekButtons = new JToolBar("XTrex Buttons");
		
		powerBtn.setToolTipText("Turn the XTrex on or off");
		
		
		menuBtn.setToolTipText("Return to the main menu");
		
		powerBtn.addActionListener(this);
		menuBtn.addActionListener(this);
		minusBtn.addActionListener(this);
		plusBtn.addActionListener(this);
		selectBtn.addActionListener(this);
		
		xTrekButtons.add(powerBtn);
		xTrekButtons.add(menuBtn);
		xTrekButtons.add(minusBtn);
		xTrekButtons.add(plusBtn);
		xTrekButtons.add(selectBtn);
		
		add(xTrekButtons, BorderLayout.PAGE_START);
	}

	public void actionPerformed(ActionEvent e) {
		JButton sourceBtn = (JButton) e.getSource();
		if (sourceBtn.equals(powerBtn)) {
			xtbb.onPowerButtonPressed();
		} else if (sourceBtn.equals(menuBtn)) {
			xtbb.onMenuButtonPressed();
		} else if (sourceBtn.equals(minusBtn)) {
			xtbb.onMinusButtonPressed();
		} else if (sourceBtn.equals(plusBtn)) {
			xtbb.onPlusButtonPressed();
		} else if (sourceBtn.equals(selectBtn)) {
			xtbb.onSelectButtonPressed();
		}
	}
}
