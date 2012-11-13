package gui;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class WeaponPanel extends JPanel{
	private JCheckBox zat, glitter, antiPlastic, reaver, ronan, glamdring, dalek, drone, mjolnir;
	
	public WeaponPanel () {
		zat = new JCheckBox("ZAT'NI'KATEL");
		glitter = new JCheckBox("Glitter Gun");
		antiPlastic = new JCheckBox("Anti-Plastic");
		ronan = new JCheckBox("Ronan Dex");
		drone = new JCheckBox("Ancient Drone");
		mjolnir = new JCheckBox("Mjolnir");
		add(zat);
		add(glitter);
		add(antiPlastic);
		add(ronan);
		add(drone);
		add(mjolnir);
		setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		setLayout(new GridLayout(0, 2));
	}

}
