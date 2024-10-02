package pacman.ihm;
import pacman.ihm.*;
import pacman.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

// gère l'appui sur le bouton Quitter
public class EcouteurQuitter implements ActionListener {
	
	private FenetreMenu fen;
    
        public EcouteurQuitter(FenetreMenu fen){
            this.fen=fen;
        }
         public void actionPerformed(ActionEvent e){
            fen.quitter();
            
        }
}

