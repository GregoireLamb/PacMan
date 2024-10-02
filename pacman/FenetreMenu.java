package pacman;

import pacman.ihm.*;
import pacman.musique.*;
import pacman.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import pacman.personnage.*;

public class FenetreMenu extends JFrame{
    
    // ====== Attributs de la fenetre menu ======
    private Labyrinthe labyrinthe;
    private int scoreDernierePartie=0;
    public static int bestScore=0;
    
    //Différents Panel
    private JPanel enHaut;
    private JPanel enBas;
    private JPanel auMilieu;
    private JPanel auMilieuEnHaut;
    private JPanel auMilieuAuMilieu;
    
    //Images
    private JLabel imageEnHaut;
    private JLabel imageEnBas;
    private JLabel texteEnBas;
    
    //Instanciation des boutons
    private JButton jouer;
    private JButton scores;
    private JButton options;
    private JButton credits;
    private JButton quitter;
    private JButton boutonMute;
    private JButton bleu;
    private JButton rouge;
    private JButton jaune;
    private JButton violet;
    
    //Panel des Extras du jeu
    private JTextPane affichageScores;
    private JTextPane affichageOptions;
    private JTextPane affichageCredits;
    private JTextPane reglageSon;
    
    //Audio
    private SimpleAudioPlayer audioPlayer;	//permet de jouer une musique
    private boolean stateSon;				//permet de savoir si le son du jeu est en mute ou non
    private JSlider barDeSon;
    private int volumeGlobal;
    //couleur Pacman
    private Color couleurPacman;
    
    
    // ====== Constructeur de la fenetre menu ======
    
    public FenetreMenu(){
        super("Pac-man");
        this.setSize(600,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stateSon=true;
        volumeGlobal=80;
        
        // ====== Instanciation des widgets de la fenetre menu ======
        
        //partie haute
        String imgUrl="PACMAN.png";
        ImageIcon iconeEnHaut = new ImageIcon(imgUrl);
        imageEnHaut= new JLabel(iconeEnHaut, JLabel.CENTER);
        
        //partie basse
        ImageIcon iconeEnBas= new ImageIcon("imageEnBas.png");
        imageEnBas= new JLabel(iconeEnBas, JLabel.CENTER);
        texteEnBas=new JLabel("texte", JLabel.CENTER);
        texteEnBas.setText("<html><body><font color='white'>            Faites votre sélection à l'aide de la souris. </body></html>" );
        
        //boutons
        jouer=new JButton("Jouer");
        scores=new JButton("Scores");
        options=new JButton("Options");
        credits=new JButton("Credits");
        quitter=new JButton("Quitter");
        boutonMute=new JButton("ON");
        bleu=new JButton("Bleu");
        rouge=new JButton("Rouge");
        jaune=new JButton("Jaune");
        violet=new JButton("Violet");
        
        //slider son
        barDeSon=new JSlider(0,100,80);
        
        //affichages de texte
        affichageScores=new JTextPane() ;
        affichageOptions=new JTextPane();
        affichageCredits=new JTextPane();
        reglageSon = new JTextPane();
        affichageScores.setBackground(Color.BLACK);
        affichageOptions.setBackground(Color.BLACK);
        affichageCredits.setBackground(Color.BLACK);
        reglageSon.setBackground(Color.BLACK);
        
        
        // ====== Organisation structurelle ======
        
        enHaut = new JPanel(new BorderLayout());
        enHaut.setBackground(Color.BLACK);
        enHaut.add(imageEnHaut, BorderLayout.NORTH);
        
        enBas = new JPanel(new BorderLayout());
        enBas.setBackground(Color.BLACK);
        enBas.add(imageEnBas, BorderLayout.SOUTH);
        enBas.add(texteEnBas, BorderLayout.CENTER);
        
        auMilieu = new JPanel(new BorderLayout());
        auMilieu.setBackground(Color.BLACK);
        
        auMilieuEnHaut = new JPanel(new FlowLayout());
        auMilieuEnHaut.setBackground(Color.BLACK);
        auMilieuEnHaut.add(jouer);
        auMilieuEnHaut.add(scores);
        auMilieuEnHaut.add(options);
        auMilieuEnHaut.add(credits);
        auMilieuEnHaut.add(quitter);
        
        auMilieuAuMilieu = new JPanel(new FlowLayout());
        auMilieuAuMilieu.setBackground(Color.BLACK);
        
        
        auMilieu.add(auMilieuEnHaut,BorderLayout.NORTH);
        auMilieu.add(auMilieuAuMilieu,BorderLayout.CENTER);
        
        
        JPanel cadrePrincipal= new JPanel(new BorderLayout());
        cadrePrincipal.add(enHaut,BorderLayout.NORTH);
        cadrePrincipal.add(enBas,BorderLayout.SOUTH);
        cadrePrincipal.add(auMilieu,BorderLayout.CENTER);
       
        
        add(cadrePrincipal);
        
        // ===== liaison bouttons <-> écouteurs =====
        jouer.addActionListener(new EcouteurJouer(this));
        scores.addActionListener(new EcouteurScore(this));
        options.addActionListener(new EcouteurOptions(this));
        credits.addActionListener(new EcouteurCredits(this));
        quitter.addActionListener(new EcouteurQuitter(this));
        
        boutonMute.addActionListener(new EcouteurMute(this));
        barDeSon.addChangeListener(new EcouteurSliderSon(this));
        
        bleu.addActionListener(new EcouteurCouleurPacMan(this,new Color(0,0,255)));
        rouge.addActionListener(new EcouteurCouleurPacMan(this,new Color(255,0,0)));
        jaune.addActionListener(new EcouteurCouleurPacMan(this,new Color(255,255,0)));
        violet.addActionListener(new EcouteurCouleurPacMan(this,new Color(226,25,230)));
        
        // ==========================================
        
        setVisible(true);
        
        setMusic("pacman_generique.wav");
        this.setSliderVol(volumeGlobal);
		audioPlayer.play(); 
		
		
        couleurPacman=new Color(255,255,0);
        
    }
    public void setStateSon(boolean st){
		stateSon=st;
	}
	public boolean getStateSon(){
		return stateSon;
	}
	public int getVolumeGlobal(){
		return volumeGlobal;
	}
	public void setVolumeGlobal(int v){
		volumeGlobal=v;
	}
    
    public void setMusic(String music){
		try
        {
		audioPlayer =  new SimpleAudioPlayer(music);
		}
		catch (Exception ex)  
        { 
            System.out.println("Error with playing sound."); 
            ex.printStackTrace(); 
        }
	}
    
    //arret de la musique
	public void fenMute(boolean on){
		audioPlayer.setMute(on);
	}
	//reglage du son de la musique
	public void setSliderVol(int vol){
		audioPlayer.setVolume(vol);
		volumeGlobal=vol;
	}
    
    //lancement du jeu, crée un Labyrinthe, arrête la musique correspondant au menu
    public void lancerJeu(){
        labyrinthe = new Labyrinthe(bestScore,this,couleurPacman);
        labyrinthe.start();
        if(audioPlayer!=null){
			audioPlayer.pause();
		}
    }
    
    //à l'appui sur bouton Score : enlève ce qui était précédement affiché, affiche le score de la dernière partie, et le meilleur score réalisé
    public void score(){
        auMilieuAuMilieu.removeAll();
        auMilieuAuMilieu.repaint();
        affichageScores.setText("  Le score de la dernière partie est : "+scoreDernierePartie+ "\n"+"Le meilleur score est pour l'instant : "+bestScore);
        affichageScores.setEditable(false);
        affichageScores.setForeground(Color.WHITE); //couleur du texte = blanc
        auMilieuAuMilieu.add(affichageScores);
    }
    
    //à l'appui sur bouton Options : enlève ce qui était précédement affiché, affiche un "menu" Option (choix de la couleur de Pacman, réglage du son)
    public void options(){
        auMilieuAuMilieu.removeAll();
        auMilieuAuMilieu.repaint();
        affichageOptions.setText("Choisissez la couleur de votre Pacman.");
        affichageOptions.setEditable(false);
        affichageOptions.setForeground(Color.WHITE);
        auMilieuAuMilieu.add(affichageOptions);
        auMilieuAuMilieu.add(bleu);
        auMilieuAuMilieu.add(rouge);
        auMilieuAuMilieu.add(jaune);
        auMilieuAuMilieu.add(violet);
        
        reglageSon.setText("Réglage du son");
        reglageSon.setEditable(false);
        reglageSon.setForeground(Color.WHITE);
        auMilieuAuMilieu.add(reglageSon);
        auMilieuAuMilieu.add(boutonMute);
        auMilieuAuMilieu.add(barDeSon);
    }
    
    //à l'appui sur bouton Crédits : enlève ce qui était précédement affiché, puis affiche les Crédits
    public void credits(){
        auMilieuAuMilieu.removeAll();
        auMilieuAuMilieu.repaint();
        affichageCredits.setText("      Ce jeu a été réalisé dans le cadre d'un projet d'informatique,"
        +"\n"+"     il a été conçu par Mathieu Reslou, Grégoire de Lambertye et Alice Launet," +
        "\n"+"      élèves ingénieurs en deuxième année à l'INSA de LYON."
        +"\n"+"\n"+"    Nous tenons à remercier Monsieur Hervé Rivano, notre professeur "+"\n"
        +"      cette année pour ses conseils et tout le temps qu'il nous a consacré."
        +"\n"+"\n"+"Sources complémentaires :"+"\n"+ "Oracle and its affiliates - Oracle Java™"
        +"\n"+"Ressources informatiques INSA Lyon (FIMI)  -   TimSC, A* search algorithm [en ligne]");
        affichageCredits.setEditable(false);
        affichageCredits.setForeground(Color.WHITE);
        auMilieuAuMilieu.add(affichageCredits);
    }
    
    //ferme la fenêtre et éteint la musique
    public void quitter(){
        audioPlayer.pause();
        dispose();
    }
    
    public void setCouleurPacman(Color couleur){
        couleurPacman=couleur;
    }
    
    public void setHighScore(int highScore){
		bestScore = highScore;
	}
	
	public void setScoreDernierePartie(int lastScore){
		scoreDernierePartie = lastScore;
	}
    public void setTextSon(){
		if(stateSon){
			boutonMute.setText("ON");
		}
		else{
			boutonMute.setText("OFF");
		}
	}
	public SimpleAudioPlayer getAudioPlayer(){
		return audioPlayer;
	}

}
