package pacman.ihm;
import pacman.ihm.*;
import pacman.*;
import javax.swing.*;													
import java.awt.*;
import java.awt.event.*;

public class Affichage extends JFrame{  
	
	// ============= Attributs ==============
	
	//Map et labyrinthe
	private int largeurMap = 600;
	private int LongueurMap = 600;
	private int[][] labyrinthe;
	private Map map;
	
	private boolean gameState;
	
	//attributs extras (scores...)
	private int highScore = 0;
	private int nombreDeVie = 3 ;
	private int score = 0;
	private char touche;
	private Color couleurPacman;
	
	//Affichage
	private final int nombreDeFrame = 6; //Nombre d image entre 2 position 
	private int currentFrame = 0; //frame ou on est
	private int numAffichage;
	private int vitesseDeJeu = 20;
		
	// ============= Constructeur ==============
	
	public Affichage(int[][] Laby, Color couleurPacman) {				
		super("PAC - MAN");
		labyrinthe = Laby; 
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new EcouteurAffichageWindow(this));
		
		map = new Map(largeurMap,LongueurMap, labyrinthe, highScore, nombreDeFrame , couleurPacman );	//création du JPanel (map)
		this.setSize(650,725);											// Dimensions 
		this.setVisible(true);											// visible, peut se mettre a la fin 
		this.add(map);
		this.addKeyListener(new EcouteurDir(this));
		this.couleurPacman = couleurPacman;
		vitesseDeJeu = 20;
		gameState=true;
		
	}    
	
	//================== Méthodes =====================

	public void lireTouche(char nouvelleTouche){
		touche = nouvelleTouche;
	}
	
	public char getTouche(){
		return touche;
	}
	public boolean getGameState(){
		return gameState;
	}
	public void setGameState(boolean on_off){
		gameState=on_off;
	}
		
 
	public void mettreToutAJour(int[][] NouveauLaby, int nouveauScore, int nouveauHighScore, int nouveauNombreDeVie){ //mise a jour de tout les attributs
		map.setScore(nouveauScore);
		map.setHighScore(nouveauHighScore);
		map.setNombreDeVies(nouveauNombreDeVie);
		map.miseAJourMap(NouveauLaby , 0);
	}

	public void miseAJourLabyEtScore(int[][] NouveauLaby,  int nouveauScore, int numAffichage){ 	// mise a jour du laby et du score (en tps de jeux normal)
		map.setScore(nouveauScore);
		
		for(int i=0 ; i<nombreDeFrame ; i++){
			map.miseAJourMap(NouveauLaby , currentFrame, numAffichage);
			try {
				Thread.sleep(vitesseDeJeu);
				} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
			currentFrame++;
		}
		currentFrame = 0;
	}
	
	public int getNombredeVies(){
		return nombreDeVie;
	}
	public void setNombredeVies(int nbVies){
		nombreDeVie = nbVies;
		map.setNombreDeVies(nombreDeVie);
	}
	
	public void augmenterLaVitesseDeJeu(){
		vitesseDeJeu = (int)(0.8 * vitesseDeJeu);
	}
}
