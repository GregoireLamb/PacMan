package pacman.personnage;

import pacman.personnage.*;
import pacman.*;
import pacman.chemin.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Fantome extends Personnage {
	
    // ============= Attributs ==============
    
	private int skinInit;
    private int compteurFuite;
    
	
    
    // ============= Constructeur ==============
    
	public Fantome(Labyrinthe labyrinthe, int positionx , int positiony , int skin){
		super(labyrinthe, positionx , positiony , skin);
		skinInit = skin;
        compteurFuite=0;
	}
	
    // ============= Donne la manière de se déplacer (des fantomes) ==============
	public void deplacementFantome(){
		if(phaseDeJeu == 0){                       						//jeu normal : couleurs des fantomes normales
			setSkin(skinInit);                                          //couleurs des fantomes normales
			deplacementNormal();                                        //se déplacent selon leur algorithme propre
		
        }else if(phaseDeJeu == 1){                						//une grosse bille est mangée par Pacman 
			setSkin(100+skinInit);                                      //fantomes bleus foncés
            deplacementFuite();                                         //tentent de fuire Pacman
		
        }else if(phaseDeJeu == 2){                						//annonce que la fin de la phase de fuite est proche
			setSkin(200+skinInit);                                      //fantomes blancs
            deplacementFuite(); 										//continuent à fuir
                                                  
		}else if(phaseDeJeu == 3){                					    //le fantome a été mangé et il rentre a sa position initiale 
			setSkin(300+skinInit);										//Affichage des yeux 
			retourMaison();
			
			if(positionx == getPositionInitialeX() && positiony == getPositionInitialeY()){ //Si le fantome est a nouveau a sa position de départ, il retourne chasser le pac man 
				phaseDeJeu = 0;
			}
		}
	}
	
    // ============= Donne la direction suivante à prendre par le fantome, lorsque son algorithme de déplacement utilise une recherche de chemin ==============
    public char getDirectionFantome(List<Noeud> list){ //prend en attribut le chemin trouvé, sous forme d'une liste de noeuds, noeuds représentant les cases
        char touche='t';
        if (list.size() >= 1) {
          
            if(list.get(0).x==super.positionx+1){
                touche = 's';
            }
            else if (list.get(0).x==super.positionx-1){
                touche = 'z';
            }
            else if(list.get(0).y==super.positiony+1){
                touche = 'd';
            }
            else if(list.get(0).y==super.positiony-1){
                touche = 'q';
            }
        }
        return touche;
    }
    // ============= cherche le chemin le plus court entre deux points, grâce à la classe RechercheChemin ==============
    public List<Noeud> cheminLePlusCourt(int x, int y){
        List<Noeud> chemin =new ArrayList<>();
        
        rechercheChemin rc = new rechercheChemin(super.labyrinthe.getLabyrinthe(), super.positionx, super.positiony, false);
        chemin=rc.cheminVers(x,y);
        chemin.remove(0);
        return chemin;
    }
    
    // ============= deplacement propre à chaque fantome ==============
    public void deplacementNormal(){ //methode redéfinie par chacun des fantomes
	}
    
    // ============= fuite des fantomes : ils prennent une direction qui n'est pas celle du chemin le plus court ==============
    public void deplacementFuite(){
        List<Noeud> chemin=new ArrayList<>();
            if(compteurFuite%4==0){ //recalcule tous les 4 tours (évite les bugg)
                chemin=cheminLePlusCourt(labyrinthe.getPacman().getPositionx(),labyrinthe.getPacman().getPositiony());
            }
        compteurFuite++;
        directionPersonnage(getOppTouche(getDirectionFantome(chemin)));
    }
    
    // ============= fantomes rentrent à la maison ==============
    public void retourMaison(){
        List<Noeud> chemin = new ArrayList<>();
        rechercheChemin rc = new rechercheChemin(super.labyrinthe.getLabyrinthe(), positionx, positiony, false);
		chemin=rc.cheminVers(getPositionInitialeX(),getPositionInitialeY());
		if(chemin.size()>1){
			chemin.remove(0);
		}
		directionPersonnage(getDirectionFantome(chemin));
    }
        
    // ============= renvoie une direction autre que celle du chemin le plus court ==============
    public char getOppTouche(char c){
		int c2=convertInt(c);
		int dir=5;
		
		int z=getValeurCase('z');
		int q=getValeurCase('q');
		int s=getValeurCase('s');
		int d=getValeurCase('d');
		int[]tabPos={z,q,s,d};
		for(int i=0;i<tabPos.length;i++){
			if(tabPos[i]!=1 && i!=c2){
				dir=i;
			}
		}
		char dir1=convertChar(dir);
		
		return dir1;		
	}
    
    // ============= conversion char en int ==============
	public int convertInt(char c){
		int c2=4;
		if(c=='z'){
			c2=0;
		}if(c=='q'){
			c2=1;
		}if(c=='s'){
			c2=2;
		}if(c=='d'){
			c2=3;
		}
		return c2;
	}
    
    // ============= conversion int en char ==============
	public char convertChar(int c){
		char c2='z';
		if(c==0){
			c2='z';
		}if(c==1){
			c2='q';
		}if(c==2){
			c2='s';
		}if(c==3){
			c2='d';
		}
		return c2;
	}

    public int nbCaseLibre(){
		int i=0;
		if(getValeurCase('z')!=1){
			i++;
		}
        if(getValeurCase('q')!=1){
			i++;
		}
        if(getValeurCase('s')!=1){
			i++;
		}
        if(getValeurCase('d')!=1){
			i++;
		}
		return i;
	}
	public int getCasesLibres(){
		int val=0;
		if(getValeurCase('z')!=1 && getValeurCase('s')!=1){
			val =1;
		}
        if(getValeurCase('q')!=1 && getValeurCase('d')!=1){
			val =2;
		}
        if(getValeurCase('z')!=1 && getValeurCase('q')!=1){ 
			val =3;
		}
        if(getValeurCase('q')!=1 && getValeurCase('s')!=1){ 
			val =4;
		}
        if(getValeurCase('s')!=1 && getValeurCase('d')!=1){ 
			val =5;
		}
        if(getValeurCase('d')!=1 && getValeurCase('z')!=1){ 
			val =6;
		}
		return val;
	}
    
    // ============= Permet de donner une direction aléatoire ==============
	public char getRandomDir(){  
		int j=(int)(Math.random()*4);
		char dir='z';
		if(j==0){
			dir='z';
		}if(j==1){
			dir='q';
		}if(j==2){
			dir='s';
		}if(j==3){
			dir='d';
		}
		return dir;
	}

}






