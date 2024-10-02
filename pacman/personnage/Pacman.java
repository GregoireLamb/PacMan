package pacman.personnage;

import pacman.personnage.*;
import pacman.*;
public class Pacman extends Personnage{

	private int skin = 20;
	private int positionCoinx;
    private int positionCoiny;
    
	public Pacman(Labyrinthe labyrinthe){
		super(labyrinthe, 23,14,20);
	}
    
    public int[] getPositionCoin(int x, int y){
        int[]positionCoin = new int [2];
        positionCoinx=1;
        positionCoiny=1;
        int z=0;
        
            if(this.getIndDir()==1){ // Pacman va vers le nord, on cherche le premier mur qu'il a devant lui
                while(labyrinthe.getValeurLaby(this.positionx-z, this.positiony)!=1
                        && labyrinthe.getValeurLaby(this.positionx-z, this.positiony)!=2
                        &&labyrinthe.getValeurLaby(this.positionx-z, this.positiony)!=3){
                    z++;
                     }
                positionCoinx=this.positionx-(z-1);
                positionCoiny=this.positiony;
            }
            
            if(this.getIndDir()==2){// Pacman va vers le sud, on cherche le premier mur qu'il a devant lui
                while(labyrinthe.getValeurLaby(this.positionx+z, this.positiony)!=1
                && labyrinthe.getValeurLaby(this.positionx+z, this.positiony)!=2
                        &&labyrinthe.getValeurLaby(this.positionx+z, this.positiony)!=3){
                    z++;
                }
                positionCoinx=this.positionx+(z-1);
                positionCoiny=this.positiony;
            }
                
            if(this.getIndDir()==3){// Pacman va vers l'est, on cherche le premier mur qu'il a devant lui
                while(labyrinthe.getValeurLaby(this.positionx, this.positiony+z)!=1
                        && labyrinthe.getValeurLaby(this.positionx, this.positiony+z)!=2
                        &&labyrinthe.getValeurLaby(this.positionx, this.positiony+z)!=3){
                    z++;
                }
                positionCoinx=this.positionx;
                positionCoiny=this.positiony+(z-1);
            }
            
            if(this.getIndDir()==4){// Pacman va vers l'ouest, on cherche le premier mur qu'il a devant lui
                while(labyrinthe.getValeurLaby(this.positionx, this.positiony-z)!=1
                && labyrinthe.getValeurLaby(this.positionx, this.positiony-z)!=2
                        &&labyrinthe.getValeurLaby(this.positionx, this.positiony-z)!=3){
                    z++;
                }
                positionCoinx=this.positionx;
                positionCoiny=this.positiony-(z-1);
            }
        positionCoin[0]=positionCoinx;
        positionCoin[1]=positionCoiny;
        
        return positionCoin;
    }
}
