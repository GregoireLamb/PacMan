package pacman.ihm;
import pacman.ihm.*;
import pacman.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;


// gère le son
public class EcouteurAffichageWindow implements WindowListener{
	
	private Affichage aff;
    
	public EcouteurAffichageWindow(Affichage aff){
		this.aff=aff;
	}
	
	@Override
	public void windowClosing(WindowEvent event) {
		aff.setGameState(false);
		aff.dispose();
    }
    
    public void exitProcedure() {
	}
    
    @Override
    public void windowActivated(WindowEvent e){
	}
	@Override
    public void windowDeactivated(WindowEvent e){
	}
	@Override
    public void windowDeiconified(WindowEvent e){
	}
	@Override
    public void windowIconified(WindowEvent e){
	}
	@Override
    public void windowClosed(WindowEvent e){
	}
	@Override
    public void windowOpened(WindowEvent e){
	}
}
