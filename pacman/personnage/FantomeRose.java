package pacman.personnage;

import pacman.personnage.*;
import pacman.*;
import pacman.chemin.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/*Fantôme rose (Pinky) est le plus intelligent des fantômes du jeu. Il cherche à se placer devant Pacman, pour le prendre en embuscade.
 * Principe de l'algorithme : chercher le chemin le plus court jusqu'au coin en face de Pacman.
 */

public class FantomeRose extends Fantome{
	
    //================== Attributs =====================
	private int skin = 40;
    
    //================== Constructeur =====================
	public FantomeRose(Labyrinthe labyrinthe){
		super(labyrinthe,14,13,40);
	}
    
    //================== chemin le plus court jusqu'au coin en face de Pacman =====================
    public List<Noeud> trajectoirePinky(){
        List<Noeud> chemin= new ArrayList();
		chemin=cheminLePlusCourt(labyrinthe.positionCoinx(),labyrinthe.positionCoiny());
		return chemin;
    }
    
    //================== Deplacement du fantome =====================
    public void deplacementNormal(){
		
        if(positionx != labyrinthe.positionCoinx() || positiony != labyrinthe.positionCoiny()){
			directionPersonnage(super.getDirectionFantome(trajectoirePinky()));
        }else {
			labyrinthe.setValeurLaby(positionx , positiony , 40);
		}
    }
    
    
    
    
    
}

