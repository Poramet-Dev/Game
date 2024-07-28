import javax.swing.SwingUtilities;

import Presentations.MainView;

public class Game {

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeLater(() -> new MainView());//Call MainView
		} catch (Exception e) {
			
		}
	}

}
