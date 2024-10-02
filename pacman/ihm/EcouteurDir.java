package pacman.ihm;
import pacman.ihm.*;
import pacman.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class EcouteurDir implements KeyListener{
	private Affichage fen;
	private char dir;

	
	
	public EcouteurDir(Affichage fen){
		this.fen=fen;
	}
	
	//Méthode qui permet de récuperer un touche clavier
    @Override
    public void keyTyped(KeyEvent ke) {
		dir = ke.getKeyChar();
		if(dir == 'z' || dir == 'q' || dir == 's'|| dir == 'd' || dir == 'p'){
			fen.lireTouche(dir);
		}
    }
	
	/*Le mot-clé 
	 * @override est utilisé pour définir une méthode qui est héritée de la classe parente.
	 * On ne l'utilise donc que dans le cas de l'héritage. 
	 * En plaçant ce mot-clé dans le commentaire de la méthode réécrite, le compilateur vérifiera que la méthode est correctement utilisée
	 * (mêmes arguments, même type de valeur de retour) et affichera un message d'avertissement si ce n'est pas le cas.*/
	 
    @Override
    public void keyPressed(KeyEvent ke) {
     
    }
 
    @Override
    public void keyReleased(KeyEvent ke) {
      
    }
     
}
	
	
