package pacman.personnage;

import pacman.personnage.*;
import pacman.*;
import pacman.chemin.*;
import java.util.*;
public class FantomeOrange extends Fantome{
	
	//================== Attributs =====================
	private int skin = 60;
	private static LinkedList<String> code;		//code tapé en cours
	private static LinkedList<String> codeRef;	//code référence à taper pour que le fantôme orange se mette à fuir
	private String lettre;
	
	//Itération permettant de faire fuir le fantôme pendant un ceratin temps
    private int time;
	
	//================== Constructeur =====================
	public FantomeOrange(Labyrinthe labyrinthe){
		super(labyrinthe,14,15, 60 );
		code=new LinkedList<String>();
		codeRef=new LinkedList<String>();
		//initialition des codes
		code.addFirst("a");
		code.addFirst("a");
		code.addFirst("a");
		codeRef.addFirst("z");
		codeRef.addFirst("q");
		codeRef.addFirst("s");
		String lettre="z";
		time=0;
	}
	
	//================== chemin le plus court jusqu'au coin en face de Pacman =====================
	public List<Noeud> trajectoireOrange(){
    // on récupère la position de Pac Man et celle de Blinky en parcourant le tableau
        List<Noeud> chemin= new ArrayList();
        chemin=cheminLePlusCourt(labyrinthe.getPacman().getPositionx(),labyrinthe.getPacman().getPositiony());
        return chemin;
    }
    
    //================== Deplacement du fantome =====================
    public void deplacementNormal(){
		
		updateList(labyrinthe.getTouche2());
		if(code.equals(codeRef) || (time<3 && time>0)){
			deplacementFuite();
			time++;
		}
		else{
        directionPersonnage(getDirectionFantome(trajectoireOrange()));
        time=0;
		}
    }
    
    //================== Méthodes =====================
    
    //permet d'actualiser le code à chaue fois que l'on déplace le pacman
    public void updateList(char c){
		if(Character.toString(c)!=lettre){
			lettre=Character.toString(c);
			code.removeLast();
			code.addFirst(lettre);
		}
	}
}
