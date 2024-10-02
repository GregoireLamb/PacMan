package pacman.ihm;
import pacman.ihm.*;
import pacman.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

//gère l'appui sur le bouton Score
public class EcouteurScore implements ActionListener{
	
	private FenetreMenu fen;
    
        public EcouteurScore(FenetreMenu fen){
            this.fen=fen;
        }
         public void actionPerformed(ActionEvent e){
            fen.score();
            
        }
}

