package gui;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class WeaponGuessPanel extends JPanel{
	private JComboBox weaponGuess;
	
	public WeaponGuessPanel () {
		weaponGuess = new JComboBox();
		weaponGuess.addItem("Unsure");
		weaponGuess.addItem("ZAT'NI'KATEL");
		weaponGuess.addItem("Glitter Gun");
		weaponGuess.addItem("Anti-Plastic");
		weaponGuess.addItem("Ronan Dex");
		weaponGuess.addItem("Ancient Drone");
		weaponGuess.addItem("Mjolnir");
		add(weaponGuess);
		setBorder(new TitledBorder (new EtchedBorder(), "Suspected Weapon"));
		setLayout(new GridLayout(1, 1));
	}

}