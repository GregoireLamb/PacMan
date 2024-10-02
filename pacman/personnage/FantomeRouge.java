package pacman.personnage;

import pacman.personnage.*;
import pacman.*;
import pacman.chemin.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class FantomeRouge extends Fantome{
                                 
    private int positionPacmanX;
    private int positionPacmanY;

    // ============ Constructeur =============
    public FantomeRouge(Labyrinthe labyrinthe){
		super(labyrinthe, 14, 12,30);

	}
    //================== chemin le plus court jusqu'à Pacman =====================
    public List<Noeud> trajectoireBlinky(){
        List<Noeud> chemin= new ArrayList();
        chemin=cheminLePlusCourt(labyrinthe.getPacman().getPositionx(),labyrinthe.getPacman().getPositiony());
        return chemin;
    } 
    
    //================== Deplacement du fantome =====================
    public void deplacementNormal(){
        this.directionPersonnage(super.getDirectionFantome(trajectoireBlinky()));
    }
    
   
}





   
