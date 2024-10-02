package pacman.ihm;
import pacman.ihm.*;
import pacman.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

//gère le son de la fenêtre menu en mute ou activé

public class EcouteurMute implements ActionListener{
	
	private FenetreMenu fen;
    
        public EcouteurMute(FenetreMenu fen){
            this.fen=fen;
        }
         public void actionPerformed(ActionEvent e){
            if(fen.getStateSon()==true){
				fen.fenMute(fen.getStateSon());
				fen.setStateSon(false);
				
			}
			else if(fen.getStateSon()==false){
				fen.fenMute(fen.getStateSon());
				fen.setStateSon(true);
			}
			fen.setTextSon();	
        }
}
