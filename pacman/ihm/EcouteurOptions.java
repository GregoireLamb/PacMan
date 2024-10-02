package pacman.ihm;
import pacman.ihm.*;
import pacman.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

//gère l'entrée dans le menu Options, à l'appui sur le bouton Options

public class EcouteurOptions implements ActionListener{
	
	private FenetreMenu fen;
    
        public EcouteurOptions(FenetreMenu fen){
            this.fen=fen;
        }
         public void actionPerformed(ActionEvent e){
            fen.options();
            
        }
}

