package pacman.ihm;
import pacman.ihm.*;
import pacman.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
public class EcouteurJouer implements ActionListener{ 
	
//gère l'appui sur le bouton jouer
    private FenetreMenu fen;
    
        public EcouteurJouer(FenetreMenu fen){
            this.fen=fen;
        }
        
        public void actionPerformed(ActionEvent e){
            fen.lancerJeu();
            
        }
    
}
