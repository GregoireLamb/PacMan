package pacman.musique;

import pacman.*;
import pacman.personnage.*;
// Java program to play an Audio 
// file using Clip Object 
import java.io.File; 
import java.io.IOException; 
import java.util.Scanner; 

//différent package intégrer à java pour controllrer l'audio
import javax.sound.sampled.*;						
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
import javax.sound.sampled.FloatControl; //controle des commandes du PC

public class SimpleAudioPlayer{ 
  
    // to store current position 
    private Long currentFrame; 
    private Clip clip; 				//créartion d'un clip, c'est à dire la musique
    private FloatControl volCtrl;
    private BooleanControl mute;
    private Line lineIn;
    private Mixer mixer; 
    // current status of clip 
    private String status; 
      
    private AudioInputStream audioInputStream; 
    private static String filePath; 
  
    // constructor to initialize streams and clip 
    public SimpleAudioPlayer(String filePath) 
        throws UnsupportedAudioFileException, 
        IOException, LineUnavailableException  
    { 
		this.filePath=filePath;
        //create AudioInputStream object 
        audioInputStream =  AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());  
          
        // create clip reference 
        clip = AudioSystem.getClip();
          
        // open audioInputStream to the clip 
        clip.open(audioInputStream); 
          
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
        volCtrl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN); //récupère le controle du volume de l'ordi
        mute = (BooleanControl)clip.getControl(BooleanControl.Type.MUTE);		// de même pour le mute
    }
    
    public void setVolume(double vol) {				//permet de regler le volume d'une musique
		float dB= (float)(20*Math.log(vol/100));	// permet de passer d'une échelle logarithmique à une echelle linéaire son de 0 à 100
		if (volCtrl!= null){
			volCtrl.setValue(dB);     
		}
	}
	public void setMute(boolean on_off){			//permet de mute une musique
		mute.setValue(on_off);
	}
	
    //permet de choisir ce que l'on veut faire du clip
    private void gotoChoice(int c) 
            throws IOException, LineUnavailableException, UnsupportedAudioFileException { //permet d'éviter les errueurs
        switch (c)  
        { 
            case 1: 
                pause(); 
                break; 
            case 2: 
                resumeAudio(); 
                break; 
            case 3: 
                restart(); 
                break; 
            case 4: 
                stop(); 
                break; 
            case 5: 
                System.out.println("Enter time (" + 0 +  
                ", " + clip.getMicrosecondLength() + ")"); 
                Scanner sc = new Scanner(System.in); 
                long c1 = sc.nextLong(); 
                jump(c1); 
                break; 
      
        } 
      
    } 
      
    // Method to play the audio 
    public void play()  
    { 
        //start the clip 
        clip.start(); 
          
        status = "play"; 
    } 
      
    // Method to pause the audio 
    public void pause()  
    { 
        if (status.equals("paused"))  
        { 
            System.out.println("audio is already paused"); 
            return; 
        } 
        this.currentFrame =  
        this.clip.getMicrosecondPosition(); 
        clip.stop(); 
        status = "paused"; 
    } 
      
    // Method to resume the audio 
    public void resumeAudio() throws UnsupportedAudioFileException, 
                                IOException, LineUnavailableException  
    { 
        if (status.equals("play"))  
        { 
            System.out.println("Audio is already "+ 
            "being played"); 
            return; 
        } 
        clip.close(); 
        resetAudioStream(); 
        clip.setMicrosecondPosition(currentFrame); 
        this.play(); 
    } 
      
    // Method to restart the audio 
    public void restart() throws IOException, LineUnavailableException, 
                                            UnsupportedAudioFileException  
    { 
        clip.stop(); 
        clip.close(); 
        resetAudioStream(); 
        currentFrame = 0L; 
        clip.setMicrosecondPosition(0); 
        this.play(); 
    } 
      
    // Method to stop the audio 
    public void stop() throws UnsupportedAudioFileException, 
    IOException, LineUnavailableException  
    { 
        currentFrame = 0L; 
        clip.stop(); 
        clip.close();
    } 
      
    // Method to jump over a specific part 
    public void jump(long c) throws UnsupportedAudioFileException, IOException, 
                                                        LineUnavailableException  
    { 
        if (c > 0 && c < clip.getMicrosecondLength())  
        { 
            clip.stop(); 
            clip.close(); 
            resetAudioStream(); 
            currentFrame = c; 
            clip.setMicrosecondPosition(c); 
            this.play(); 
        } 
    } 
      
    // Method to reset audio stream 
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, 
                                            LineUnavailableException  
    { 
        audioInputStream = AudioSystem.getAudioInputStream( 
        new File(filePath).getAbsoluteFile()); 
        clip.open(audioInputStream); 
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
    } 
  
} 
