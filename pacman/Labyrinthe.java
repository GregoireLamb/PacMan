package pacman;
import pacman.*;
import pacman.ihm.*;
import pacman.personnage.*;
import pacman.musique.*;
import java.awt.Color;

public class Labyrinthe extends Thread {
	
	//================== Attributs =====================
	
	//Labyrinthe
	private int[][] labyrintheInitial ={{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},	//Labyrinthe de départ
									 {1,7,7,7,7,7,7,7,7,7,7,7,7,1,1,7,7,7,7,7,7,7,7,7,7,7,7,1},
									 {1,7,1,1,1,1,7,1,1,1,1,1,7,1,1,7,1,1,1,1,1,7,1,1,1,1,7,1},							
									 {1,8,1,1,1,1,7,1,1,1,1,1,7,1,1,7,1,1,1,1,1,7,1,1,1,1,8,1},
									 {1,7,1,1,1,1,7,1,1,1,1,1,7,1,1,7,1,1,1,1,1,7,1,1,1,1,7,1},
									 {1,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,1},
									 {1,7,1,1,1,1,7,1,1,7,1,1,1,1,1,1,1,1,7,1,1,7,1,1,1,1,7,1},
									 {1,7,1,1,1,1,7,1,1,7,1,1,1,1,1,1,1,1,7,1,1,7,1,1,1,1,7,1},
									 {1,7,7,7,7,7,7,1,1,7,7,7,7,1,1,7,7,7,7,1,1,7,7,7,7,7,7,1},
									 {1,1,1,1,1,1,7,1,1,1,1,1,0,1,1,0,1,1,1,1,1,7,1,1,1,1,1,1},
									 {0,0,0,0,0,1,7,1,1,1,1,1,0,1,1,0,1,1,1,1,1,7,1,0,0,0,0,0},
									 {0,0,0,0,0,1,7,1,1,0,0,0,0,0,0,0,0,0,0,1,1,7,1,0,0,0,0,0},
									 {0,0,0,0,0,1,7,1,1,0,1,1,9,9,9,9,1,1,0,1,1,7,1,0,0,0,0,0},
									 {1,1,1,1,1,1,7,1,1,0,1,0,0,0,0,0,0,1,0,1,1,7,1,1,1,1,1,1},
									 {2,0,0,0,0,0,7,0,0,0,1,0,30,40,50,60,0,1,0,0,0,7,0,0,0,0,0,3},
									 {1,1,1,1,1,1,7,1,1,0,1,0,0,0,0,0,0,1,0,1,1,7,1,1,1,1,1,1},
									 {0,0,0,0,0,1,7,1,1,0,1,1,1,1,1,1,1,1,0,1,1,7,1,0,0,0,0,0},
									 {0,0,0,0,0,1,7,1,1,0,0,0,0,0,0,0,0,0,0,1,1,7,1,0,0,0,0,0},
									 {0,0,0,0,0,1,7,1,1,0,1,1,1,1,1,1,1,1,0,1,1,7,1,0,0,0,0,0},
									 {1,1,1,1,1,1,7,1,1,0,1,1,1,1,1,1,1,1,0,1,1,7,1,1,1,1,1,1},
									 {1,7,7,7,7,7,7,7,7,7,7,7,7,1,1,7,7,7,7,7,7,7,7,7,7,7,7,1},
									 {1,7,1,1,1,1,7,1,1,1,1,1,7,1,1,7,1,1,1,1,1,7,1,1,1,1,7,1},
									 {1,7,1,1,1,1,7,1,1,1,1,1,7,1,1,7,1,1,1,1,1,7,1,1,1,1,7,1},
									 {1,8,7,7,1,1,7,7,7,7,7,7,7,7,20,7,7,7,7,7,7,7,1,1,7,7,8,1},
									 {1,1,1,7,1,1,7,1,1,7,1,1,1,1,1,1,1,1,7,1,1,7,1,1,7,1,1,1},
									 {1,1,1,7,1,1,7,1,1,7,1,1,1,1,1,1,1,1,7,1,1,7,1,1,7,1,1,1},
									 {1,7,7,7,7,7,7,1,1,7,7,7,7,1,1,7,7,7,7,1,1,7,7,7,7,7,7,1},
									 {1,7,1,1,1,1,1,1,1,1,1,1,7,1,1,7,1,1,1,1,1,1,1,1,1,1,7,1},
									 {1,7,1,1,1,1,1,1,1,1,1,1,7,1,1,7,1,1,1,1,1,1,1,1,1,1,7,1},
									 {1,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,1},
									 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
	
	private int[][] labyrinthe = new int[31][28]; 
    private int positionCoinx;
    private int positionCoiny;
    
    //Affichage et fenêtre menu
    private FenetreMenu fenetreDuMenu;
    private Affichage affichage; 
    //Attributs de score
	private int score = 0;
    private static int highScore;
	private int nombreDeVie;
	private int compteBille;
	private int previousCompteBille;
	
	//Pacman
	private Pacman pacman;
	private Color couleurPacman;
	private char pacManDeplacement;
	private int positionX;
	private int positionY;
	private char dir;
	
	//Fantômes
	private FantomeCyan cyan;
	private FantomeRouge rouge;
	private FantomeOrange orange;
	private FantomeRose rose;
	
	protected int caseSousCyan = 0;
	protected int caseSousRouge = 0;
	protected int caseSousOrange = 0;
	protected int caseSousRose = 0;
    protected int caseSousPacMan = 0;
    //Audio
	private SimpleAudioPlayer audioPlayer1;
	private SimpleAudioPlayer  audioPlayer2;
	private int delaiMusiqueDIntro = 0;
	
	//Jeu
	private int timerGrosseBille = -1; //permet de passer d'une phase a une autre 
	private Boolean enJeu = true; 
    
    
    //================== Constructeur =====================
    
    public Labyrinthe(int highScore, FenetreMenu fenetreDuMenu, Color couleurPacman){
		this.highScore = highScore;
		this.fenetreDuMenu = fenetreDuMenu;
        this.couleurPacman=couleurPacman;
		
		for(int i=0 ; i<labyrintheInitial.length ; i++){ //Initialisation du labyrinthe
			for(int j=0 ; j<labyrintheInitial[i].length ; j++){
				labyrinthe[i][j] = labyrintheInitial[i][j];
			}
		}
		
		pacman=new Pacman(this);
        score=0;
        nombreDeVie = 3;
        affichage = new Affichage(labyrinthe , couleurPacman);
        cyan=new FantomeCyan(this);
		rouge=new FantomeRouge(this);
		rose=new FantomeRose(this);
		orange=new FantomeOrange(this);
		
    
		
	}
	
	
	//================== Méthode principale (jeu) =====================
	
	public void run(){
		
		if(!fenetreDuMenu.getStateSon()){
			fenetreDuMenu.setVolumeGlobal(0);
		}
		affichage.mettreToutAJour(labyrinthe , score , highScore , nombreDeVie);
        compteBille = 0;
        previousCompteBille=compteBille;
        signalTouche() ;												//permet d attendre que le joueur appuie sur une touche pour lancer le jeu
		
		setMusic("pacman_beginning.wav");
		audioPlayer1.setVolume(fenetreDuMenu.getVolumeGlobal()/2.0);
		audioPlayer1.play();
	
		if(fenetreDuMenu.getVolumeGlobal() !=0){
			while(delaiMusiqueDIntro < 3000){
				if(affichage.getGameState()){
					try {
						Thread.sleep(1);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
					}
				}else{signalTouche();}
				delaiMusiqueDIntro = delaiMusiqueDIntro + 1;
			}
		}

		stop1();
	
		setMusic1("pacman_remix.wav");
	
		 audioPlayer2.setVolume((fenetreDuMenu.getVolumeGlobal())/2.0);
		 audioPlayer2.play();
		
		
		while (enJeu == true){
			
			if(affichage.getTouche() == 'p'){
				signalTouche();
			}
						
            pacman.directionPersonnage(affichage.getTouche());
            
            positionCoinx=pacman.getPositionCoin(pacman.getPositionx(),pacman.getPositiony())[0];
            positionCoiny=pacman.getPositionCoin(pacman.getPositionx(),pacman.getPositiony())[1];
			
            
            cyan.deplacementFantome();
            rouge.deplacementFantome();
			rose.deplacementFantome();
			orange.deplacementFantome();
						
			affichage.miseAJourLabyEtScore(labyrinthe,score , 1);
			
			pacman.directionPersonnage(affichage.getTouche());
			affichage.miseAJourLabyEtScore(labyrinthe,score , 2);
			
			
			if(compteBille >= 245){										// fin du niveau 
				finDuNiveau();
				
			}
			
			if(timerGrosseBille >= 0){ 									// Effet d'une grosse bille
				timerGrosseBille++;
				if(timerGrosseBille > 15){ 
					grosseBillePhase2();
				}
				if(timerGrosseBille >25){
					grosseBilleFin();
					timerGrosseBille = -1; 
				}
			}
			stop1();
			if(!affichage.getGameState()){
				this.finDuJeu();
				enJeu=false;
			}
			
		}
	}
	
	//================== Attente d'une nouvelle touche =====================
	
	public void  signalTouche(){										//permet d attendre que le joueur appuie sur une nouvelle touche, met pause
		if(audioPlayer1!=null){
			stop1();			
		}
		char toucheAvant = affichage.getTouche() ; 				
		while(affichage.getTouche() == toucheAvant){
			try {
				Thread.sleep(1);
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
			if(!affichage.getGameState() && enJeu == true){		
				if(highScore <= score){
					highScore = score;
				}
				fenetreDuMenu.setHighScore(highScore);
				fenetreDuMenu.setScoreDernierePartie(score);

				if( audioPlayer2!=null){
					stop2();
				}


				fenetreDuMenu.setMusic("pacman_generique.wav");
				fenetreDuMenu.setSliderVol(fenetreDuMenu.getVolumeGlobal());
				fenetreDuMenu.getAudioPlayer().play(); 
				enJeu = false; 				
			}
		}
	}
	
	
	//================== Musique =====================
	
	private void setMusic(String music){
		if( audioPlayer1!=null){
			stop1();
		}
		
		try
        {
		 audioPlayer1 =  new SimpleAudioPlayer(music);
		}
		catch (Exception ex)  
        { 
            System.out.println("Error with playing sound."); 
            ex.printStackTrace(); 
        }
	}
	
	private void setMusic1(String music){
		try
        {
		 audioPlayer2 =  new SimpleAudioPlayer(music);
		}
		catch (Exception ex)  
        { 
            System.out.println("Error with playing sound."); 
            ex.printStackTrace(); 
        }
	}
	private void stop1(){
		try{
			 audioPlayer1.stop();
		}
		catch (Exception ex)  
			{ 
				System.out.println("Error with playing sound."); 
				ex.printStackTrace(); 
			}
    }
    private void stop2(){
	try{
		 audioPlayer2.stop();
	}
	catch (Exception ex)  
        { 
            System.out.println("Error with playing sound."); 
            ex.printStackTrace(); 
        }
    }
	
	//=============== Lorsque PAC MAN mange une grose bille ==================
	public void mangeGrosseBille(){
		timerGrosseBille = 0; 											//on lance le chrono car l'effet disparait 
		
		rouge.setPhaseDeJeu(1);											//On chage le comportement des fantomes 
		cyan.setPhaseDeJeu(1);
		orange.setPhaseDeJeu(1);
		rose.setPhaseDeJeu(1);
		
	}
	
	//=============== Lorsque l'effet grose bille s'estompe ==================
	public void grosseBillePhase2(){
		
		if(rouge.getPhaseDeJeu() ==1){ 									// Si le fantome n'a pas été mangé, il devient blanc 
			rouge.setPhaseDeJeu(2);
		}
		if(cyan.getPhaseDeJeu() ==1){ 									// Si le fantome n'a pas été mangé, il devient blanc 
			cyan.setPhaseDeJeu(2);
		}
		if(rose.getPhaseDeJeu() ==1){ 									// Si le fantome n'a pas été mangé, il devient blanc 
			rose.setPhaseDeJeu(2);
		}
		if(orange.getPhaseDeJeu() ==1){ 								// Si le fantome n'a pas été mangé, il devient blanc 
			orange.setPhaseDeJeu(2);
		}
	}
	
	//=============== Lorsque l'effet grose bille se fini ==================
	public void grosseBilleFin(){
		
		if(rouge.getPhaseDeJeu() ==2){ 									// Si le fantome n'a pas été mangé, il redevient normal 
			rouge.setPhaseDeJeu(0);
		}
		if(cyan.getPhaseDeJeu() ==2){ 									// Si le fantome n'a pas été mangé, il redevient normal 
			cyan.setPhaseDeJeu(0);
		}
		if(rose.getPhaseDeJeu() ==2){ 									// Si le fantome n'a pas été mangé, il redevient normal
			rose.setPhaseDeJeu(0);
		}
		if(orange.getPhaseDeJeu() ==2){ 								// Si le fantome n'a pas été mangé, il redevient normal
			orange.setPhaseDeJeu(0);
		}
	}
	
	//============== PAC MAN rencontre un fantome ==========================
	public void rencontrePacMan(int skinDuFantome, int phaseDeJeuFantome){
		
		while(skinDuFantome >= 100){									// permet de reconnaitre le fantome en question
			skinDuFantome = skinDuFantome -100;
		}
		
		if(skinDuFantome >= 30){ 										// Pac man ne se mange pas lui meme
		
			if (phaseDeJeuFantome == -1){									// si la phase de jeu du fantome n'est pas renseignée on le retrouve
				if(skinDuFantome == 30 ){
					phaseDeJeuFantome = rouge.getPhaseDeJeu();
				}
				if(skinDuFantome == 40 ){
					phaseDeJeuFantome = rose.getPhaseDeJeu();
				}
				if(skinDuFantome == 50 ){
					phaseDeJeuFantome = cyan.getPhaseDeJeu();
				}
				if(skinDuFantome == 60 ){
					phaseDeJeuFantome = orange.getPhaseDeJeu();
				}
			}
					
			if(phaseDeJeuFantome == 0){ //En jeu normal 
				
				nombreDeVie--;
				setMusic("pacman_death.wav");
				 audioPlayer1.setVolume(fenetreDuMenu.getVolumeGlobal()/2.0);
				  audioPlayer1.play();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
				stop1();
				
				
				if(nombreDeVie <= 0){
					finDuJeu(); //Game Over
				}
				
				mettreLesPersonnagesALeurPositionInitiale();
				affichage.mettreToutAJour(labyrinthe , score , highScore , nombreDeVie);
				signalTouche();		//permet d attendre que le joueur appuie sur une touche pour lancer le jeu
				
			}else if(phaseDeJeuFantome > 0 ){ 	//pac man mange le fantome / ou le fantome a deja ete mangé 
				
				if(phaseDeJeuFantome != 3){
					score = score +200; 
				}
				
				setValeurLaby(pacman.getPositionx() , pacman.getPositiony() ,0); // on efface pac man pour etre sur de le remettre a la bonne place  
				
				if(skinDuFantome == 30){
					setValeurLaby(rouge.getPositionx() , rouge.getPositiony() ,20+pacman.getIndDir()); // on réécrit pacman
					pacman.setPositionx(rouge.getPositionx());
					pacman.setPositiony(rouge.getPositiony());
					rouge.setPhaseDeJeu(3);									// On passe en fantome en phase 3 
					if(caseSousRouge == 7){score = score + 10; compteBille++;}//si le fantome mangé est sur une petite bille
					if(caseSousRouge == 8){score = score + 50; compteBille++; timerGrosseBille = 0 ; mangeGrosseBille(); }//si le fantome mangé est sur une grosse bille //RELANCER LE TIMER
					caseSousRouge = 0;
				}else if(skinDuFantome == 40){
					setValeurLaby(rose.getPositionx() , rose.getPositiony() ,20+pacman.getIndDir());
					pacman.setPositionx(rose.getPositionx());
					pacman.setPositiony(rose.getPositiony());
					rose.setPhaseDeJeu(3);
					if(caseSousRose == 7){score = score + 10; compteBille++;}//si le fantome mangé est sur une petite bille
					if(caseSousRose == 8){score = score + 50; compteBille++; timerGrosseBille = 0 ; mangeGrosseBille(); }//si le fantome mangé est sur une grosse bille
					caseSousRose = 0;
				}else if(skinDuFantome == 50){					
					setValeurLaby(cyan.getPositionx() , cyan.getPositiony() ,20+pacman.getIndDir());				
					pacman.setPositionx(cyan.getPositionx());
					pacman.setPositiony(cyan.getPositiony());
					cyan.setPhaseDeJeu(3);
					if(caseSousCyan == 7){score = score + 10; compteBille++;}//si le fantome mangé est sur une petite bille
					if(caseSousCyan == 8){score = score + 50; compteBille++; timerGrosseBille = 0 ; mangeGrosseBille(); }//si le fantome mangé est sur une grosse bille				
					caseSousCyan = 0;
				}else if(skinDuFantome == 60){	
					setValeurLaby(orange.getPositionx() , orange.getPositiony() ,20+pacman.getIndDir());	
					pacman.setPositionx(orange.getPositionx());
					pacman.setPositiony(orange.getPositiony());
					orange.setPhaseDeJeu(3);
					if(caseSousOrange == 7){score = score + 10; compteBille++;}//si le fantome mangé est sur une petite bille
					if(caseSousOrange == 8){score = score + 50; compteBille++; timerGrosseBille = 0 ; mangeGrosseBille(); }//si le fantome mangé est sur une grosse bille				
					caseSousOrange = 0;
				}
			}
		}
	}
	
	
	//============================ fin du jeu =================================
	public void finDuJeu(){
		affichage.mettreToutAJour(labyrinthe,score,highScore,nombreDeVie);
		signalTouche();
		
		if(highScore <= score){
			highScore = score;
		}
		fenetreDuMenu.setHighScore(highScore);
		fenetreDuMenu.setScoreDernierePartie(score);
		
		stop2();
		
		
		fenetreDuMenu.setMusic("pacman_generique.wav");
		fenetreDuMenu.setSliderVol(fenetreDuMenu.getVolumeGlobal());
		fenetreDuMenu.getAudioPlayer().play(); 
		
		nombreDeVie = 0;
		System.out.println("Fin du jeu game over");
		affichage.dispose();
		
		
	}

	//============================ fin d'un niveau =================================	
	public void finDuNiveau(){
	
		System.out.println("Fin du niveau");
		mettreLesPersonnagesALeurPositionInitiale();
		for(int i=0 ; i<labyrintheInitial.length ; i++){
			for(int j=0 ; j<labyrintheInitial[i].length ; j++){
				labyrinthe[i][j] = labyrintheInitial[i][j];
			}
		}

		affichage.mettreToutAJour(labyrinthe , score , highScore , nombreDeVie);
		compteBille = 0;
		affichage.augmenterLaVitesseDeJeu();
		timerGrosseBille = -1;
		setFantomePhase0(); 
		signalTouche();
		
	}
	
	//================= Set Fantome en phase 0 ==============================
	public void setFantomePhase0(){
		
		rouge.setPhaseDeJeu(0);
		cyan.setPhaseDeJeu(0);
		rose.setPhaseDeJeu(0);
		orange.setPhaseDeJeu(0);
		
	}
	
	
	//================ Renvoie la valeur d'une case du laby =======================
    
    public int getValeurLaby(int positionX, int positionY) { // Permet d'obtenir la valeur stockée dans une case 
        int valeur = labyrinthe[positionX][positionY];
        return valeur;
    }
    
    
	//================== Modifie la case recouverte par un personnage ============================
    public void setCaseRecouverte(int skin, int valeur){
		while(skin >= 100){
			skin =skin -100;
		}
		//on ne teste pas 20 car la case sous pac man reste toujours 0 
		if(skin == 30){
			caseSousRouge = valeur;
		}
		else if(skin == 40){
			caseSousRose = valeur;
		}
		else if(skin == 50){
			caseSousCyan = valeur;
		}
		else if(skin == 60){
			caseSousOrange = valeur;
		}
	}
	
	
	//================== Renvoie la case recouverte par un personnage ============================
	public int getCaseRecouvertePar(int skinInconnu){
		int personnage = 0;
		
		while(skinInconnu >= 100){
			skinInconnu =skinInconnu -100;
		}
		
		while (skinInconnu >= 10){	//on recupere le chiffre qui designe le personnage dans son skin 
			personnage++;
			skinInconnu = skinInconnu - 10;
		}
		//on ne teste pas 2 car la case sous pac man reste toujours 0 
		if(personnage == 2){return 0;}
		else if(personnage == 3){
			return caseSousRouge;
		}
		else if(personnage == 4){
			return caseSousRose;
		}
		else if(personnage == 5){
			return caseSousCyan;
		}
		else if(personnage == 6){
			return caseSousOrange;
		}
		return -1;
	}
	
	
	//======= efface et remettre tout les personnages a leur position initiale =======================
	public void mettreLesPersonnagesALeurPositionInitiale(){
		 
		pacman.setPositionx(pacman.getPositionInitialeX());
		pacman.setPositiony(pacman.getPositionInitialeY());
		labyrinthe[pacman.getPositionInitialeX()][pacman.getPositionInitialeY()]=20;

		setValeurLaby(cyan.getPositionx() , cyan.getPositiony() , caseSousCyan); //on efface le fantome 
		caseSousCyan = 0; //on remet la case sous lui a 0
		cyan.setPositionx(cyan.getPositionInitialeX());
		cyan.setPositiony(cyan.getPositionInitialeY());
		labyrinthe[cyan.getPositionInitialeX()][cyan.getPositionInitialeY()] = 50;
		
		
		setValeurLaby(rouge.getPositionx() , rouge.getPositiony() , caseSousRouge); //on efface le fantome 
		caseSousRouge = 0; //on remet la case sous lui a 0
		rouge.setPositionx(rouge.getPositionInitialeX());
		rouge.setPositiony(rouge.getPositionInitialeY());
		labyrinthe[rouge.getPositionInitialeX()][rouge.getPositionInitialeY()] = 30;
		
		setValeurLaby(orange.getPositionx() , orange.getPositiony() , caseSousOrange); //on efface le fantome 
		caseSousOrange = 0; //on remet la case sous lui a 0
		orange.setPositionx(orange.getPositionInitialeX());
		orange.setPositiony(orange.getPositionInitialeY());
		labyrinthe[orange.getPositionInitialeX()][orange.getPositionInitialeY()] = 60;
		
		setValeurLaby(rose.getPositionx() , rose.getPositiony() , caseSousRose); //on efface le fantome 
		caseSousRose = 0; //on remet la case sous lui a 0
		rose.setPositionx(rose.getPositionInitialeX());
		rose.setPositiony(rose.getPositionInitialeY());
		labyrinthe[rose.getPositionInitialeX()][rose.getPositionInitialeY()] = 40;
	}
	
	//================== Méthodes simples ============================
	public void setCompteBillePlusUn(){
		compteBille++;
		stop1();	
		setMusic("pacman_chomp.wav");
		 audioPlayer1.setVolume(fenetreDuMenu.getVolumeGlobal()/2.0);
		 audioPlayer1.play();
				/*try {
			Thread.sleep(10);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}*/
	

				//audioPlayer.pause();		
	}
    
    public int getScore(){
        return score;
    }
    public void setScore(int nouveauScore){
        score = nouveauScore;
    }
    
    public int getBestScore(){
        return highScore;
    }
	
	public int positionCoinx(){
		return positionCoinx;
	}
	
	public int positionCoiny(){
		return positionCoiny;
	}
	
	public int[][] getLabyrinthe(){
		return labyrinthe;
	}
	
	public Pacman getPacman(){
		return pacman;
	}
	
	public void setValeurLaby(int positionX , int positionY, int valeur){
		labyrinthe[positionX][positionY] = valeur;
	}
	
	public char getTouche2(){
		char touche=affichage.getTouche();
		return touche;
	}

}

