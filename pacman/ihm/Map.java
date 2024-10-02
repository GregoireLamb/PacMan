package pacman.ihm;
import pacman.ihm.*;
import pacman.*;
import pacman.personnage.*;
//=============================== Librairies =====================================
import javax.swing.*;
import java.awt.*;
import java.text.*;
import java.awt.font.TextAttribute;

public class Map extends JPanel {
	
	// ============= Attributs ==============
	
	//Labyrinthe
	private int hauteur;
	private int largeur;
	private int[][] labyrinthe;
	
	// panneau superieur 
	private String texte1;												
	private String texte2;
	private int highScore;
	private int score;
	private int vies;
	
	//couleur de pacman
	private Color couleurPacman;
	
	//lissage de l'affichage et vitesses relative
	private int nombreDeFrame;											
	private int currentFrame = 0;
	private int numAffichage = 0;
	private int fantomeFrame = 0;
	
	// ============ Constructeur =============
	public Map(int largeur, int hauteur, int[][] labyrinthe, int highScore , int nombreDeFrame , Color couleurPacman){
		this.hauteur = hauteur;
		this.largeur = largeur;	
		this.labyrinthe = labyrinthe;
		this.highScore = highScore;
		this.nombreDeFrame = nombreDeFrame;
		this.couleurPacman = couleurPacman;
		vies = 3;
		score = 0;
	}

	// ============ Dessin =============
	protected void paintComponent(Graphics g) {	
		super.paintComponent(g);
		double h = hauteur/30.0;
		double l = largeur/28.0;
		
				
		g.setColor(Color.BLACK);										//Fond noir
		g.fillRect(0,0,(int)largeur,(int)(34*h));

		
		g.setColor(Color.WHITE);										//Panneau suppérieur
		texte1 = (vies+" UP                     HIGH SCORE");
		if(score > highScore){
			highScore = score;
		}
		texte2 = ("   "+score + "                              " + highScore);
		Font police1 = new Font(Font.SERIF,Font.BOLD,25);
		AttributedString str_attribut1 = new AttributedString(texte1);
		AttributedString str_attribut2 = new AttributedString(texte2);
		str_attribut1.addAttribute(TextAttribute.FONT, police1);
		str_attribut2.addAttribute(TextAttribute.FONT, police1);
		g.drawString(str_attribut1.getIterator(), (int)(largeur/5), (int)(1.5*h));
		g.drawString(str_attribut2.getIterator(), (int)(largeur/5), (int)(2.5*h));
		

		
		for(int i=0 ; i<labyrinthe.length ; i++){						//Parcours du tableau, on dessine case par case 
			for(int j=0 ; j<labyrinthe[i].length ; j++){
				int indice = labyrinthe[i][j];
				
				if (indice == -1){g.setColor(Color.GREEN);//en cas d'erreur, on dessine une case verte 
					g.fillRect((int)(j*l),(int)(i*h),(int)(l+1),(int)(h+1));
				}
				
				if (indice==0 || indice==2 || indice==3){ 				//case vide ou teleporteur, il y a deja un fond noir on ne dessine rien 		
				}
				else if (indice==1) {									//Mur
					g.setColor(Color.BLUE);								
					g.fillRect((int)(j*l),(int)(i*h+3*h),(int)(l+1),(int)(h+1)); 		
				}
				else if (indice== 7) { 									//Petite bille
					g.setColor(Color.WHITE);					
					g.fillOval((int)(j*l+0.4*l),(int)(i*h+0.4*h+3*h),(int)(0.2*l),(int)(0.2*h)); 
				}
				else if (indice== 8) { 									//Grosse bille
					g.setColor(Color.YELLOW);				
					g.fillOval((int)(j*l+0.1*l),(int)(i*h+0.1*h+3*h),(int)(0.8*l),(int)(0.8*h)); 
				}
				else if (indice== 9) { 									//Barrière
					g.setColor(Color.GRAY);			
					g.fillRect((int)(j*l),(int)(i*h+0.3*h+3*h),(int)(l+2),(int)(0.6*h)); 
				}
				else if (indice > 10){									//PERSONNAGES
					
					int centaines = 0;									//découpage de l'indice pour savoir précisement ce que l'on doit dessiner 
					int dizaines = 0;			
					int unites = 0;
					boolean fantomeEnChasse = true;
					int decalageVertical= 0;
					int decalageHorizontal = 0;
					
					while (indice >= 100){
						centaines++;
						indice = indice - 100;
					}						
									
					while (indice >= 10){
						dizaines++;
						indice = indice - 10;
					}
										
					while (indice >= 1){
						unites++;
						indice = indice - 1;
					}
					if(unites == 0){									//calcul du decalage pour lisser l'affichage
					}else if(unites == 1){
						decalageVertical = (int)(+l/nombreDeFrame*(nombreDeFrame-currentFrame));
					}else if(unites ==2){
						decalageVertical = (int)(-l/nombreDeFrame*(nombreDeFrame-currentFrame));
					}else if(unites ==3){
						decalageHorizontal = (int)(-h/nombreDeFrame*(nombreDeFrame-currentFrame));
					}else if(unites ==4){
						decalageHorizontal = (int)(h/nombreDeFrame*(nombreDeFrame-currentFrame));
					}
					
					if(dizaines == 2 && centaines == 0){				// dessin d'un PAC MAN
						g.setColor(couleurPacman);	//corps	
						g.fillOval((int)(j*l+0.1*l+decalageHorizontal),(int)(i*h+0.1*h+3*h+decalageVertical),(int)(0.8*l),(int)(0.8*h)); 
							
						g.setColor(Color.BLACK);
						if(unites == 0){ //s'il est a l arret
							g.fillOval((int)(j*l+0.55*l+decalageHorizontal),(int)(i*h+0.25*h+3*h+decalageVertical),(int)(l/5),(int)(h/5)); //oeil
							g.fillOval((int)(j*l+0.25*l+decalageHorizontal),(int)(i*h+0.25*h+3*h+decalageVertical),(int)(l/5),(int)(h/5)); //oeil
							
							g.fillRect((int)(j*l+l*5/16+decalageHorizontal),(int)(h*i+h*9/16+3*h+decalageVertical),(int)(2*l/16),(int)(h*1.5/16)); // bouche 
							g.fillRect((int)(j*l+l*6/16+decalageHorizontal),(int)(h*i+h*10/16+3*h+decalageVertical),(int)(4*l/16),(int)(h*1.5/16)); 
							g.fillRect((int)(j*l+l*9.5/16+decalageHorizontal),(int)(h*i+h*9/16+3*h+decalageVertical),(int)(2*l/16),(int)(h*1.5/16)); 
						}
						if(unites == 1){	//direction nord
							g.fillOval((int)(j*l+0.25*l+decalageHorizontal),(int)(i*h+0.25*h+3*h+decalageVertical),(int)(l/5),(int)(h/5));//oeil 
							if(currentFrame<nombreDeFrame/3 || currentFrame>2*nombreDeFrame/3){	//Bouche fermée 
								g.drawLine((int)(j*l+0.5*l+decalageHorizontal),(int)(i*h+3*h+decalageVertical),(int)(j*l+0.5*l+decalageHorizontal),(int)(i*h+0.5*h+3*h+decalageVertical));
							}else{	//Bouche ouverte
								int[] sP = {(int)(j*l+0.5*l+decalageHorizontal),(int)(j*l+0.5*l+decalageHorizontal),(int)(j*l+l+decalageHorizontal)};
								int[] yP = {(int)(i*h+3*h+0.5*h+decalageVertical),(int)(i*h+3*h+decalageVertical),(int)(i*h+3*h+decalageVertical)};
								g.fillPolygon(sP, yP, 3);
							}
						}else if(unites == 2){	//direction sud
							g.fillOval((int)(j*l+0.6*l+decalageHorizontal),(int)(i*h+0.55*h+3*h+decalageVertical),(int)(l/5),(int)(h/5)); //oeil
							if(currentFrame<nombreDeFrame/3 || currentFrame>2*nombreDeFrame/3){	//Bouche fermée 
								g.drawLine((int)(j*l+0.5*l+decalageHorizontal),(int)(i*h+0.5*h+3*h+decalageVertical),(int)(j*l+0.5*l+decalageHorizontal),(int)(i*h+h+3*h+decalageVertical));
							}else{	//Bouche ouverte
								int[] sP = {(int)(j*l+0.5*l+decalageHorizontal),(int)(j*l+0.5*l+decalageHorizontal),(int)(j*l+decalageHorizontal)};
								int[] yP = {(int)(i*h+3*h+0.5*h+decalageVertical),(int)(i*h+3*h+h+3+decalageVertical),(int)(i*h+3*h+h+3+decalageVertical)};
								g.fillPolygon(sP, yP, 3);
							}
						}else if(unites == 3){	//direction est	
							g.fillOval((int)(j*l+0.6*l+decalageHorizontal),(int)(i*h+0.25*h+3*h+decalageVertical),(int)(l/5),(int)(h/5)); 	//oeil				
							
							if(currentFrame<nombreDeFrame/3 || currentFrame>2*nombreDeFrame/3){	//Bouche fermée
								g.drawLine((int)(j*l+0.5*l+decalageHorizontal),(int)(i*h+0.5*h+3*h+decalageVertical),(int)(j*l+0.9*l+decalageHorizontal),(int)(i*h+0.5*h+3*h+decalageVertical));
							}else{	//Bouche ouverte
								int[] sP = {(int)(j*l+0.5*l+decalageHorizontal),(int)(j*l+l+decalageHorizontal),(int)(j*l+l+decalageHorizontal)};
								int[] yP = {(int)(i*h+3*h+0.5*h+decalageVertical),(int)(i*h+3*h+0.5*h+decalageVertical),(int)(i*h+3*h+h+decalageVertical)};
								g.fillPolygon(sP, yP, 3);
								
							}
							}else if(unites == 4){	//direction ouest		
					
							g.fillOval((int)(j*l+0.25*l+decalageHorizontal),(int)(i*h+0.25*h+3*h+decalageVertical),(int)(l/5),(int)(h/5)); 	//oeil
							if(currentFrame<nombreDeFrame/3 || currentFrame>2*nombreDeFrame/3){	//Bouche fermée
								g.drawLine((int)(j*l+decalageHorizontal),(int)(i*h+0.5*h+3*h+decalageVertical),(int)(j*l+0.6*l+decalageHorizontal),(int)(i*h+0.5*h+3*h+decalageVertical));
							
							}else{	//Bouche ouverte
								int[] sP = {(int)(j*l+0.5*l+decalageHorizontal),(int)(j*l+decalageHorizontal),(int)(j*l+decalageHorizontal)};
								int[] yP = {(int)(i*h+3*h+0.5*h+decalageVertical),(int)(i*h+3*h+0.5*h+decalageVertical),(int)(i*h+3*h+h+decalageVertical)};
								g.fillPolygon(sP, yP, 3);
							}									
						}
					}else{												// FANTOMES
						
/* Pour que pacman se déplace deux fois plus rapidement que les fantomes, lors du premier déplacement de pacman, on ne dessine que la premiere moitié 
 * du déplacement des fantomes. Et au deuxieme deplacement de pacman on dessine l'autre moitié
 */						 		
					if(numAffichage == 1){ 								//premiere moitié de l affichage 
						if(currentFrame%2 == 0){fantomeFrame = currentFrame/2;}
						else if(currentFrame%2 == 1){fantomeFrame = (currentFrame+1)/2;}
					}
					if(numAffichage == 2){								//deuxième moitié de l'affichage 
						if(currentFrame%2 == 0){fantomeFrame = nombreDeFrame/2 + currentFrame/2;}
						else if(currentFrame%2 == 1){fantomeFrame = nombreDeFrame/2 + (currentFrame+1)/2;}
					}
							
					if(unites == 1){								//calcul du decalage pour lisser l'affichage  
						decalageVertical = (int)(+l/nombreDeFrame*(nombreDeFrame-fantomeFrame));
					}else if(unites ==2){
						decalageVertical = (int)(-l/nombreDeFrame*(nombreDeFrame-fantomeFrame));
					}else if(unites ==3){
						decalageHorizontal = (int)(-h/nombreDeFrame*(nombreDeFrame-fantomeFrame));
					}else if(unites ==4){
						decalageHorizontal = (int)(h/nombreDeFrame*(nombreDeFrame-fantomeFrame));
					}
							
					if(centaines == 0 && dizaines != 2){ 				//si le fantome est dans sa couleur normale  	
						
						if(dizaines == 3){								//choix de la couleur du fantome
							g.setColor(Color.RED);
						}else if (dizaines == 4){							
							g.setColor(Color.PINK);
						}else if (dizaines == 5){							
							g.setColor(Color.CYAN);		
						}else if (dizaines == 6){							
							g.setColor(Color.ORANGE);
						}
					}else if(centaines == 1){							// si il est bleu 								
														
						g.setColor(Color.BLUE);
						fantomeEnChasse = false ; 
									
					}else if (centaines == 2){							//S'il est blanc
							
						g.setColor(Color.WHITE);
						fantomeEnChasse = false ; 	
									
					}else if (centaines == 3){							//lorsqu'il ne reste que les yeux, le corps est "noir" invisible, on considere que le fantome est a nouveau en chasse 
							
						g.setColor(Color.BLACK);	
										
					}
					
					//dessin du corps 
					int[] xPoints = {(int)(j*l+l*2/16+decalageHorizontal),(int)(j*l+l*2/16+decalageHorizontal),(int)(j*l+l*3/16+decalageHorizontal),(int)(j*l+l*3/16+decalageHorizontal),//contour
									(int)(j*l+l*4/16+decalageHorizontal),(int)(j*l+l*4/16+decalageHorizontal),(int)(j*l+l*6/16+decalageHorizontal),(int)(j*l+l*6/16+decalageHorizontal),
									(int)(j*l+l*10/16+decalageHorizontal),(int)(j*l+l*10/16+decalageHorizontal),(int)(j*l+l*12/16+decalageHorizontal),(int)(j*l+l*12/16+decalageHorizontal),
									(int)(j*l+l*13/16+decalageHorizontal),(int)(j*l+l*13/16+decalageHorizontal),(int)(j*l+l*14/16+decalageHorizontal),(int)(j*l+l*14/16+decalageHorizontal),
									
									(int)(j*l+l*13/16+decalageHorizontal),(int)(j*l+l*13/16+decalageHorizontal),(int)(j*l+l*12/16+decalageHorizontal),(int)(j*l+l*12/16+decalageHorizontal),//pieds
									(int)(j*l+l*11/16+decalageHorizontal),(int)(j*l+l*11/16+decalageHorizontal),(int)(j*l+l*10/16+decalageHorizontal),(int)(j*l+l*10/16+decalageHorizontal),
									(int)(j*l+l*9/16+decalageHorizontal),(int)(j*l+l*9/16+decalageHorizontal),(int)(j*l+l*7/16+decalageHorizontal),(int)(j*l+l*7/16+decalageHorizontal),
									(int)(j*l+l*6/16+decalageHorizontal),(int)(j*l+l*6/16+decalageHorizontal),(int)(j*l+l*5/16+decalageHorizontal),(int)(j*l+l*5/16+decalageHorizontal),
									(int)(j*l+l*4/16+decalageHorizontal),(int)(j*l+l*4/16+decalageHorizontal),(int)(j*l+l*3/16+decalageHorizontal),(int)(j*l+l*3/16+decalageHorizontal),};
									
										
										
										
					int[] yPoints = {(int)(h*i+3*h+h/16*15+decalageVertical),(int)(h*i+3*h+h/16*5+decalageVertical),(int)(h*i+3*h+h/16*5+decalageVertical),(int)(h*i+3*h+h/16*3+decalageVertical),//contour
									(int)(h*i+3*h+h/16*3+decalageVertical),(int)(h*i+3*h+h/16*2+decalageVertical),(int)(h*i+3*h+h/16*2+decalageVertical),(int)(h*i+3*h+h/16*1+decalageVertical),
									(int)(h*i+3*h+h/16*1+decalageVertical),(int)(h*i+3*h+h/16*2+decalageVertical),(int)(h*i+3*h+h/16*2+decalageVertical),(int)(h*i+3*h+h/16*3+decalageVertical),
									(int)(h*i+3*h+h/16*3+decalageVertical),(int)(h*i+3*h+h/16*5+decalageVertical),(int)(h*i+3*h+h/16*5+decalageVertical),(int)(h*i+3*h+h/16*15+decalageVertical),

									(int)(h*i+3*h+h/16*15+decalageVertical),(int)(h*i+3*h+h/16*14+decalageVertical),(int)(h*i+3*h+h/16*14+decalageVertical),(int)(h*i+3*h+h/16*13+decalageVertical),//pieds
									(int)(h*i+3*h+h/16*13+decalageVertical),(int)(h*i+3*h+h/16*14+decalageVertical),(int)(h*i+3*h+h/16*14+decalageVertical),(int)(h*i+3*h+h/16*15+decalageVertical),
									(int)(h*i+3*h+h/16*15+decalageVertical),(int)(h*i+3*h+h/16*13+decalageVertical),(int)(h*i+3*h+h/16*13+decalageVertical),(int)(h*i+3*h+h/16*15+decalageVertical),
									(int)(h*i+3*h+h/16*15+decalageVertical),(int)(h*i+3*h+h/16*14+decalageVertical),(int)(h*i+3*h+h/16*14+decalageVertical),(int)(h*i+3*h+h/16*13+decalageVertical),
									(int)(h*i+3*h+h/16*13+decalageVertical),(int)(h*i+3*h+h/16*14+decalageVertical),(int)(h*i+3*h+h/16*14+decalageVertical),(int)(h*i+3*h+h/16*15+decalageVertical),};
										
						
					g.fillPolygon(xPoints,yPoints,36);
						
							
					if(fantomeEnChasse == true){						// dessin des puille sui le fantome est dans sa forme normale
						g.setColor(Color.WHITE);	
						g.fillRect((int)(j*l+l*5/16+decalageHorizontal),(int)(h*i+h*3/16+3*h+decalageVertical),(int)(2*l/16),(int)(h*4/16));
						g.fillRect((int)(j*l+l*10/16+decalageHorizontal),(int)(h*i+h*3/16+3*h+decalageVertical),(int)(2*l/16),(int)(h*4/16));
						g.fillRect((int)(j*l+l*4/16+decalageHorizontal),(int)(h*i+h*4/16+3*h+decalageVertical),(int)(4*l/16),(int)(h*2/16));
						g.fillRect((int)(j*l+l*9/16+decalageHorizontal),(int)(h*i+h*4/16+3*h+decalageVertical),(int)(4*l/16),(int)(h*2/16));
					}else if (fantomeEnChasse == false){
						if (centaines == 1){							//yeux et bouche du bleu
							g.setColor(Color.WHITE);		
						}else if (centaines == 2){						//yeux et bouche du blanc
							g.setColor(Color.RED);	
						}
						g.fillRect((int)(j*l+l*6/16+decalageHorizontal),(int)(h*i+h*4/16+3*h+decalageVertical),(int)(2*l/16),(int)(h*2/16)); //yeux
						g.fillRect((int)(j*l+l*9/16+decalageHorizontal),(int)(h*i+h*4/16+3*h+decalageVertical),(int)(2*l/16),(int)(h*2/16));
						
						g.fillRect((int)(j*l+l*4/16+decalageHorizontal),(int)(h*i+h*9/16+3*h+decalageVertical),(int)(2*l/16),(int)(h*1.5/16)); // bouche 
						g.fillRect((int)(j*l+l*6/16+decalageHorizontal),(int)(h*i+h*10/16+3*h+decalageVertical),(int)(2*l/16),(int)(h*1.5/16)); 
						g.fillRect((int)(j*l+l*8/16+decalageHorizontal),(int)(h*i+h*9/16+3*h+decalageVertical),(int)(2*l/16),(int)(h*1.5/16)); 
						g.fillRect((int)(j*l+l*10/16+decalageHorizontal),(int)(h*i+h*10/16+3*h+decalageVertical),(int)(2*l/16),(int)(h*1.5/16)); 
						g.fillRect((int)(j*l+l*12/16+decalageHorizontal),(int)(h*i+h*9/16+3*h+decalageVertical),(int)(2*l/16),(int)(h*1.5/16)); 
					}
						
					if(fantomeEnChasse == true){						// Si le fantome est dans sa forme normale ou s'il est en "oeil" la pupille indique la direction du déplacement 
						g.setColor(Color.BLUE);	
						if(unites == 0){				//arrete
							g.fillRect((int)(j*l+l*5/17+decalageHorizontal),(int)(h*i+h*4.5/17+3*h+decalageVertical),(int)(2*l/17),(int)(h*1.5/17));
							g.fillRect((int)(j*l+l*10/17+decalageHorizontal),(int)(h*i+h*4.5/17+3*h+decalageVertical),(int)(2*l/17),(int)(h*1.5/17));
						}else if(unites == 1){  		//nord
							g.fillRect((int)(j*l+l*5/17+decalageHorizontal),(int)(h*i+h*3/17+3*h+decalageVertical),(int)(2*l/17),(int)(h*1.5/17));
							g.fillRect((int)(j*l+l*10/17+decalageHorizontal),(int)(h*i+h*3/17+3*h+decalageVertical),(int)(2*l/17),(int)(h*1.5/17));
						}else if (unites == 2){			//sud
							g.fillRect((int)(j*l+l*5/17+decalageHorizontal),(int)(h*i+h*5.5/17+3*h+decalageVertical),(int)(2*l/17),(int)(h*1.5/17));
							g.fillRect((int)(j*l+l*10/17+decalageHorizontal),(int)(h*i+h*5.5/17+3*h+decalageVertical),(int)(2*l/17),(int)(h*1.5/17));
						}else if (unites == 3){			//est
							g.fillRect((int)(j*l+l*6/17+decalageHorizontal),(int)(h*i+h*4.5/17+3*h+decalageVertical),(int)(2*l/17),(int)(h*1.5/17));
							g.fillRect((int)(j*l+l*11/17+decalageHorizontal),(int)(h*i+h*4.5/17+3*h+decalageVertical),(int)(2*l/17),(int)(h*1.5/17));
						}else if (unites == 4){			//ouest 
							g.fillRect((int)(j*l+l*4/17+decalageHorizontal),(int)(h*i+h*4.5/17+3*h+decalageVertical),(int)(2*l/17),(int)(h*1.5/17));
							g.fillRect((int)(j*l+l*9/17+decalageHorizontal),(int)(h*i+h*4.5/17+3*h+decalageVertical),(int)(2*l/17),(int)(h*1.5/17));
						}
					}
				}//fin fantomes
			}//fin personnage
		}//fin parcours
	}//fin parcours
}//fin paint
		
	
	//============================ Methodes secondaires =======================

	
	public void setScore(int nouveauScore){
		score = nouveauScore;
		validate();
		repaint();
	}
	
	public void setHighScore(int nouveauHighScore){
		score = nouveauHighScore;
		validate();
		repaint();
	}
	
	public void setNombreDeVies(int nouveauNombreDeVie){
		vies = nouveauNombreDeVie;
		validate();
		repaint();
	}
	
	public void miseAJourMap(int[][] NouveauLaby, int FrameAcctuelle){
		labyrinthe = NouveauLaby;
		currentFrame = FrameAcctuelle;
		this.validate();
		this.repaint();
	}
	
	public void miseAJourMap(int[][] NouveauLaby, int FrameAcctuelle, int numAff){
		numAffichage = numAff ;		
		labyrinthe = NouveauLaby;
		currentFrame = FrameAcctuelle;
		this.validate();
		this.repaint();
	}
	
}
