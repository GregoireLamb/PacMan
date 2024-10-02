package pacman.personnage;

import pacman.personnage.*;
import pacman.*;
import pacman.chemin.*;

public class Personnage{ 												//Squelette de tous les personnages (pacman et fantome)
	
	//================== Attributs =====================
	private String nom;
	protected int skin;
	protected Labyrinthe labyrinthe;
	private final int positionInitialeX ;
    private final int positionInitialeY;
	protected int positionx ;  
    protected int positiony ; 
    private boolean deplacementPossible;
    private int valeurCase;
    protected int indicatifDirection = 0;
    private boolean murEnFace=false;
    protected boolean recoitUneTouche;
    protected int phaseDeJeu = 0; 										//décris le comportement des fantomes, 0:normal (et pacman) 1:bleu  2:blanc  3:"yeux"
    
    // ============ Constructeur =============
	public Personnage(Labyrinthe labyrinthe, int positionx , int positiony , int skin){
		this.nom=nom;
		this.labyrinthe=labyrinthe;
		this.positionInitialeX = positionx;
		this.positionx = positionx;
		this.positionInitialeY = positiony;
		this.positiony = positiony;
		this.skin = skin;
	}
	
	 // ============ Deplacement des personnage dans le labyrinthe ==================
	public void directionPersonnage(char touche){       				// pour déplacer, on demande une touche qui correspond à la direction voulue
		int valeurCase = getValeurCase(touche); 						//On regarde la valeur de la case ou l on souhaite aller (methode getValeurCase() )
		
		
		// == On va agir en fonction de ce qu'il y a dans la case où l'on souhaite se rendre : == 
		if(valeurCase == -1){											//erreur (pas de -1 dans le tableau en théorie
		}
		else if(valeurCase == 0 || (valeurCase > 29 && skin > 29) || (valeurCase == 9 && ((skin !=20 && touche != 's') || skin > 300))){//case vide ou barrière franchissable
			murEnFace = false;
			labyrinthe.setValeurLaby(getPositionx(), getPositiony(), labyrinthe.getCaseRecouvertePar(skin));//on réécrit la case sur laquelle le personnage se situe avant le déplacement 
			deplacer(touche, valeurCase);								//on change la position du personnage et on garde en mémoire la caleur de la case sur laquelle on se trouve pour pouvoir la réécrire lorsqu'e l'on en partira
			labyrinthe.setValeurLaby(getPositionx(), getPositiony() ,(skin+indicatifDirection));//On met a jour le tableau de jeu 
		}
		else if(valeurCase == 1 || (valeurCase == 9 &&  (skin == 20  || (skin !=20 && touche == 's' & skin < 299)))){	//mur et barriere pour pacman ou fantome sans permission
			if(murEnFace == false){										//si le personnage n'est pas a l'arret
				murEnFace = true;										//On note que le personnage tente de traverser un élément qu'il n a pas le droit de traverser
				if(indicatifDirection == 0){}							//On change son déplacement pour qu'il continue dans son sens initial
				else if(indicatifDirection == 1){touche = 'z';}
				else if (indicatifDirection == 2){touche = 's';}
				else if (indicatifDirection == 3){touche = 'd';}
				else if (indicatifDirection == 4){touche = 'q';}
				directionPersonnage(touche);

			}else{labyrinthe.setValeurLaby(getPositionx(), getPositiony() ,(skin));}//s'il y a vraiment mur en face, on arrete le personnage
		}
		else if(valeurCase == 2){										//teleporteur de gauche a droite 
			murEnFace = false;
			labyrinthe.setValeurLaby(14, 0 ,2);
			labyrinthe.setValeurLaby(14, 1 ,0); 						
			setPositionx(14);
			setPositiony(26);
			labyrinthe.setValeurLaby(getPositionx(), getPositiony() , (skin+indicatifDirection));
		}
		else if(valeurCase == 3){										//teleporteur de droite a gauche 
			murEnFace = false;
			labyrinthe.setValeurLaby(14, 27 ,3);
			labyrinthe.setValeurLaby(14, 26 ,0);
			setPositionx(14);
			setPositiony(1);
			labyrinthe.setValeurLaby(getPositionx(), getPositiony() , (skin+indicatifDirection));
		}
		else if(valeurCase == 7){										// petite bille  
			murEnFace = false;											//le deplacement est le meme qu'en cas de vide 
			labyrinthe.setValeurLaby(getPositionx(), getPositiony(), labyrinthe.getCaseRecouvertePar(skin));
			deplacer(touche, valeurCase);		
			labyrinthe.setValeurLaby(getPositionx(), getPositiony() , (skin+indicatifDirection));
			if(skin == 20){												// Si c'est pacman qui est sur la petite bille (si c'est un fantome on ne fait rien)
				labyrinthe.setScore(labyrinthe.getScore()+10);			// le score augemente
				
				labyrinthe.setCompteBillePlusUn();						// Son d'une bille  
				
			}
		}
		else if(valeurCase == 8){										// grosse bille 8  
			murEnFace = false;											//le deplacement est le meme qu'en cas de vide
			labyrinthe.setValeurLaby(getPositionx(), getPositiony(), labyrinthe.getCaseRecouvertePar(skin));
			deplacer(touche, valeurCase);			
			labyrinthe.setValeurLaby(getPositionx(), getPositiony() , (skin+indicatifDirection));
			if(skin == 20){												//si pacman mange une grosse bille (si c'est un fantome on ne fait rien)
				labyrinthe.mangeGrosseBille();
				labyrinthe.setScore(labyrinthe.getScore()+50);
				labyrinthe.setCompteBillePlusUn();
			}
		}
		else if(valeurCase >= 20 && valeurCase <= 25 ){					// rencontre PAC-MAN
			murEnFace = false;											//le deplacement est le meme qu'en cas de vide 
			labyrinthe.setValeurLaby(getPositionx(), getPositiony(), labyrinthe.getCaseRecouvertePar(skin));	
			deplacer(touche , valeurCase);			
			labyrinthe.setValeurLaby(getPositionx(), getPositiony() ,(skin+indicatifDirection));
			
			labyrinthe.rencontrePacMan(skin, phaseDeJeu );				//On traite la rencontre dans labyrinthe 
		}
		
		else if (valeurCase > 29 && skin == 20){						//Rencontre PACMAN fantome
			murEnFace = false;
																		//On cherche a savoir de quel fantome il s'agit
			int valeurExacteCase = valeurCase; 							// evite d ecraser valeurCase
			int indicateurDirectionCase=0 ; 
			int indicateurSensOppose = -1;
			int skinDuFantome = 0;
			
			while(valeurExacteCase >= 100){
				valeurExacteCase = valeurExacteCase - 100 ;
				skinDuFantome = skinDuFantome + 100;
			}
			
			while(valeurExacteCase >= 10){
				valeurExacteCase = valeurExacteCase - 10 ;
				skinDuFantome= skinDuFantome + 10;
			}
			while(valeurExacteCase > 0){
				valeurExacteCase--;
				indicateurDirectionCase++;
			}
			
			labyrinthe.setValeurLaby(getPositionx(), getPositiony(), labyrinthe.getCaseRecouvertePar(skin));
			labyrinthe.rencontrePacMan(skinDuFantome, -1);			//On traite la rencontre dans labyrinthe, -1 signifit que c'est pacman qui est allé sur le fantome 
				
		}
    }
    
    public void deplacer(char touche , int valeurCase ){				//methode appellé qu'apres verification du deplacement possible, elle modifie les coordonnées des personnages 
			if (touche == 'z'){ 										//nord
				positionx = positionx-1;
				indicatifDirection = 1;
			}else if (touche == 'q'){  									//ouest                    
				positiony = positiony -1;
				indicatifDirection = 4 ;
			}else if (touche == 's'){    								//sud                     
					positionx = positionx+1; 
					indicatifDirection = 2;   
			}else if (touche == 'd'){     								//est                        
					positiony = positiony +1;
					indicatifDirection = 3;  
			}
		//Mise a jour de la case a réécrire en partant  
		if(skin == 20){ 
			labyrinthe.setCaseRecouverte(skin , 0);						//pacman efface tout 
		}else{
			 labyrinthe.setCaseRecouverte(skin , valeurCase);			//Sinon on garde la valeur de la case avant notre passage 
		}
		if(valeurCase >= 10){											//S'il y a un personnage deja présent sur notre case
			labyrinthe.setCaseRecouverte(skin, labyrinthe.getCaseRecouvertePar(valeurCase));	// on recupere la valeur sous l'empilement des personnages via labyrinthe	
		}
	}
	
    public int getValeurCase(char touche){ 								//Donne la valeur de la case sur laquelle on souhaite aller 
    
		valeurCase = -1;
		
		if (touche == 'z'){                          			 // si la touche est z et On vérifie que le déplacement souhaité ne rentre pas dans un mur ou un endroit interdit (porte)...  ATTENTION z corresspond a un déplacement vers le haut mais sur l axe le déplacement est négatif 
			valeurCase = labyrinthe.getValeurLaby(positionx-1,positiony);
		}else if (touche == 'q'){                             // si la touche est q// On vérifie que le déplacement souhaité ne rentre pas dans un mur 
			valeurCase = labyrinthe.getValeurLaby(positionx,positiony-1);
		}else if(touche == 's'){                              // si la touche est s// On vérifie que le déplacement souhaité ne rentre pas dans un mur 
			valeurCase = labyrinthe.getValeurLaby((positionx+1),positiony);
		}else if(touche == 'd'){                             // si la touche est d // On vérifie que le déplacement souhaité ne rentre pas dans un mur 
			valeurCase = labyrinthe.getValeurLaby(positionx,positiony+1);
		}
		return valeurCase;
	}
		
    
    

	//================================ accesseurs =================================== 
    
    public int getPositionx(){ 											//Renvoie la position en x du personnage 
        return positionx;
    }

    public int getPositiony(){ 											//Renvoie la position en y du personnage 
        return positiony;
    }
      
    public int getPositionInitialeX(){ 									//Renvoie la position initiale en x du personnage 
        return positionInitialeX;
    }

    public int getPositionInitialeY(){ 									//Renvoie la position initiale en y du personnage 
        return positionInitialeY;
	}
	
	public int getIndDir(){ 											//Renvoie l'indicatif de direction du personnage 
		return indicatifDirection;
	}	
	
	public int getPhaseDeJeu(){											//Renvoie le mode de déplacement du personnage 
		return phaseDeJeu;		
	}
	
	
	//================================ setteurs =================================== 
	
	public void setIndDir(int nb){										//Modifie l'indicatif de direction du personnage 
		indicatifDirection=nb;
	}
    
    public void setPositionx(int nouvPositionx){ 						// Modifie la poosition en x du personnage 
        positionx = nouvPositionx;
    }

    public void setPositiony(int nouvPositiony){ 						// Modifie la poosition en y du personnage 
        positiony = nouvPositiony;
    }

	
	public void setSkin(int indicatif){ 								// Modifie le skin du personnage 
		skin = indicatif;	
	}

	public void setPhaseDeJeu(int nvPhase){								// Modifie le mode de déplacement du personnage 
		phaseDeJeu = nvPhase;		
	}

}
