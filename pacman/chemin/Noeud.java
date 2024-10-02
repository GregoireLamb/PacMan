package pacman.chemin;
import pacman.*;
import pacman.ihm.*;
import pacman.personnage.*;

public class Noeud implements Comparable {
    
    //================== Attributs =====================
    public Noeud parent;
    public int x, y;
    public double g;
    public double h;
    
    //================== Constructeur =====================
    public Noeud(Noeud parent, int xpos, int ypos, double g, double h) {
        this.parent = parent;
        this.x = xpos;
        this.y = ypos;
        this.g = g; // cout 
        this.h = h; //cout heuristique
    }
    
    //compare deux objets
    public int compareTo(Object o) {
       Noeud that = (Noeud) o;
       return (int)((this.g + this.h) - (that.g + that.h));
    }
}


