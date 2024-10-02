package pacman.ihm;
import pacman.ihm.*;
import pacman.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

// gère l'appui sur le bouton Crédits

public class EcouteurCredits implements ActionListener {
	
	private FenetreMenu fen;
    
        public EcouteurCredits(FenetreMenu fen){
            this.fen=fen;
        }
         public void actionPerformed(ActionEvent e){
            fen.credits();
            
        }
}

