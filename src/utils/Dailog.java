package utils;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Dailog {
	Component component;
	
	public Dailog(Component component) {
		this.component = component;
	}
	
	public int conFirmExitGame() {
		return JOptionPane.showConfirmDialog(component, "Do you want to exit the game?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
	}
}
