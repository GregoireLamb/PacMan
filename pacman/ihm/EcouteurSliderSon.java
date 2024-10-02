package pacman.ihm;
import pacman.ihm.*;
import pacman.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;

// gère le son
public class EcouteurSliderSon implements ChangeListener{
	
	private FenetreMenu fen;
	
    
	public EcouteurSliderSon(FenetreMenu fen){
		this.fen=fen;
	}
        
	public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int vol = (int)source.getValue();
            fen.setSliderVol(vol);
            fen.setVolumeGlobal(vol);
		}
	}
}






