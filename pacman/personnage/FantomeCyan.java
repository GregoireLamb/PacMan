package pacman.personnage;

import pacman.personnage.*;
import pacman.*;
import pacman.chemin.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class FantomeCyan extends Fantome{
	
	//================== Attributs =====================
	private int skin = 50; //instanciation des attributs
	private char dir;
	
	public FantomeCyan(Labyrinthe labyrinthe){
		super(labyrinthe, 14 ,14, 50);     //set position de depart
	}
	//================== Deplacement du fantome =====================
	
	public void deplacementNormal(){  //permet au fantome cyan de se déplacer de manière aléatoire
		char dir='c';
		dir=dirAutorise();    //valeur aléatoire de la direction
		
		if(nbCaseLibre()>=3){     		// si le nombre de direction possible autour du pac man est superieure ou egal à 3
			directionPersonnage(dir);		//déplacer fantome
		}
		
		if(nbCaseLibre()==2){					// si le nombre de direction possible autour du pac man est egal à 2
			if (getCasesLibres()==1){			//si la configuration est un couloir vertical alors en le fanntome doit aller tout droit
				if(getIndDir()==1){
					dir='z';
				}if(getIndDir()==2){
					dir='s';
				}
			}
			if (getCasesLibres()==2){			//si la configuration est un couloir vertical alors en le fanntome doit aller tout droit
				if(getIndDir()==3){
					dir='d';
				}if(getIndDir()==4){
					dir='q';
				}
			}
			if (getCasesLibres()==3){			//si la configuration est un couloir vertical alors en le fanntome doit aller tout droit
				if(getIndDir()==3){
					dir='z';
				}if(getIndDir()==2){
					dir='q';
				}
			}
			if (getCasesLibres()==3){			//si la configuration est un couloir vertical alors en le fanntome doit aller tout droit
				if(getIndDir()==4){
					dir='z';
				}if(getIndDir()==2){
					dir='q';
				}
			}
			if (getCasesLibres()==4){			//si la configuration est un couloir vertical alors en le fanntome doit aller tout droit
				if(getIndDir()==3){
					dir='s';
				}if(getIndDir()==1){
					dir='q';
				}
			}
			if (getCasesLibres()==5){			//si la configuration est un couloir vertical alors en le fanntome doit aller tout droit
				if(getIndDir()==1){
					dir='d';
				}if(getIndDir()==4){
					dir='s';
				}
			}
			if (getCasesLibres()==6){			//si la configuration est un couloir vertical alors en le fanntome doit aller tout droit
				if(getIndDir()==2){
					dir='d';
				}if(getIndDir()==4){
					dir='z';
				}
			}
			
		}
        directionPersonnage(dir);
    }
	
	//================== Méthodes =====================
	
	public void directionDebut(){			//permet au fantome de sortir du carré au début ou après une mort
		for(int i=0;i<3;i++){
			directionPersonnage('z');
		}
	}
	public char dirAutorise(){			//permet de retourne un char aléatoire sauf celui de la dernière direction exemple si ancienne direction = 'z' alors n eretournera pas 's'
		char dir='a';
		if(getIndDir()==0){
			dir=getRandomDir();
		}
		if(getIndDir()==1){
			while(dir!='z' && dir!='q' && dir!='d'){
				dir=getRandomDir();
			}
		}if(getIndDir()==2){
			while(dir!='s' && dir!='q' && dir!='d'){
				dir=getRandomDir();
			}
		}if(getIndDir()==3){
			while(dir!='z' && dir!='q' && dir!='s'){
				dir=getRandomDir();
			}
		}if(getIndDir()==4){
			while(dir!='z' && dir!='s' && dir!='d'){
				dir=getRandomDir();
			}
		}
		return dir;		
	}

}
			
