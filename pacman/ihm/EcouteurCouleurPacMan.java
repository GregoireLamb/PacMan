package pacman.ihm;
import pacman.ihm.*;
import pacman.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.Color;


// permet aux boutons du menu Option/Régler la couleur de fixer une couleur à PacMan

public class EcouteurCouleurPacMan implements ActionListener{
	
    private FenetreMenu fen;
    private Color couleurPacman;
    
    public EcouteurCouleurPacMan(FenetreMenu fen, Color couleurPacman){ //attributs : une FenetreMenu, une couleur
        this.fen=fen;
        this.couleurPacman=couleurPacman;
    }
    public void actionPerformed(ActionEvent e){ //définit la couleur de pacman à l'appui sur le bouton
        fen.setCouleurPacman(couleurPacman);
    
    }
    
}

