package pacman.chemin;
import pacman.*;
import pacman.ihm.*;
import pacman.personnage.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
 
public class rechercheChemin {
    
    //================== Attributs =====================
    private final List<Noeud> ouvert;  // constitue une file d'attente prioritaire --> si elle est vide, il
                                    // n'y a aucun chemin du nœud initial au nœud d'arrivée
    private final List<Noeud> ferme;// constitue une liste de sauvegarde des noeuds vérifiés lorsque l'algorithme cherche 
                                    // (permet reconstruction du chemin en utilisant une partie des données stockées)
    private final List<Noeud> chemin;  // contiendra le chemin final trouvé par A*
    private final int[][] laby;     // labyrinthe dans lequel on cherche un chemin 
    private Noeud now;               // noeud sur lequel on se trouve à un instant t
    private final int xstart;       
    private final int ystart;       // coordonnées de la position de départ
    private int xend, yend;         //                               d'arrivée
    private final boolean diag;     // gère déplacement en diagonale
    
 
    //================== Constructeur =====================
    public rechercheChemin(int[][] laby, int xstart, int ystart, boolean diag) {
        this.ouvert = new ArrayList<>();
        this.ferme = new ArrayList<>();
        this.chemin = new ArrayList<>();
        this.laby = laby;
        this.now = new Noeud(null, xstart, ystart, 0, 0); // on se place à la position initiale
        this.xstart = xstart;
        this.ystart = ystart;
        this.diag = diag;
    }
    
    /*
    ** trouve un chemin vers xend/yend ou renvoie null
    **
    ** @param (int) xend coordoonnées de la positon finale recherchée
    ** @param (int) yend
    ** @renvoie (List<Node> | null) le chemin
    */
    public List<Noeud> cheminVers(int xend, int yend) {
        this.xend = xend;
        this.yend = yend;
        this.ferme.add(this.now);      // on ajoute le noeud où l'on est maintenant à la liste des noeuds vérifiés
        ajouterVoisinAListeOpen();        // on ajoute les noeuds voisins à la file d'attente prioritaire
        while (this.now.x != this.xend || this.now.y != this.yend) {    // tant qu'on est pas en position finale 
            
            if (this.ouvert.isEmpty()) {  // si la liste d'attente prioritaire est vide, alors il n'y a pas de chemin possible vers
                                        // la position finale recherchée
                return null;
            }
            
            this.now = this.ouvert.get(0); /* liste d'attente prioritaire n'est pas vide, alors on choisi le premier noeud 
                                            *(en raison du fonctionnement d'une file d'attente, le nœud à l'heuristique
                                            * la plus basse se trouve en premiere position)
                                            */
            this.ouvert.remove(0);        // on le retire de la file d'attente prioritaire
            this.ferme.add(this.now);  // on l'ajoute à la liste des noeuds vérifiés
            ajouterVoisinAListeOpen();    // on charge les noeuds voisins de notre position et on les ajoute à la file d'attente
        }   // à la fin du while, on a trouvé le chemin le plus court, par ajout successif de noeuds voisins, jusqu'à la position finale
        
        
        this.chemin.add(0, this.now);     // on ajoute 
        while (this.now.x != this.xstart || this.now.y != this.ystart) {// tant qu'on est pas remonté jusqu'au départ
            this.now = this.now.parent;                                 // on remonte noeud après noeud de l'arrivée jusqu'au départ
            this.chemin.add(0, this.now);                                 /* en incrémentant à chaque tour la liste qui va représenter
                                                                         * le chemin */
        }
        return this.chemin; // retourne la route trouvée qui est la plus courte entre le point de départ et celui d'arrivée
    }
    
    
    /*
    **Cherche un noeud dans une List<> donnée
    **
    ** @return (bool) NeighborInListFound 
    * retourne vrai si la liste contient des noeuds
    */
    private static boolean trouverVoisinDansLaListe(List<Noeud> array, Noeud node) { 
        return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
    }
    
    
    
    /*
    ** calcule la distance entre this.now (position actuelle) et xend/yend (position finale visée)
    **
    ** retourne (int) distance
    */
    private double distance(int dx, int dy) {
        if (this.diag) { // si les mouvement en diagonale sont autorisés
            return Math.hypot(this.now.x + dx - this.xend, this.now.y + dy - this.yend); // retourne l'hypothenuse
        } else {
            return Math.abs(this.now.x + dx - this.xend) + Math.abs(this.now.y + dy - this.yend); // sinon retourne "Manhattan distance"
        }
    }
    
    
    
    private void ajouterVoisinAListeOpen() {  
        /* tant que le noeud considéré n'est pas le noeud d'arrivée, de nouveaux les noeuds contigus (et admissibles)
         * à ce noeuds sont ajoutés à la file d'attente prioritaire pour chacun, est calculé son coût (somme du coût 
         * de son parent et du coût nécessaire pour atteindre ce nouveau noeud)
         */
        
        Noeud node;
        for (int x = -1; x <= 1; x++) {               // à partir du noeud (case) considéré(e), on considère les huit cases qui lui
            for (int y = -1; y <= 1; y++) {           // sont tangentes
                if (!this.diag && x != 0 && y != 0) { // si le mouvement en diagonale n'est pas autorisé et que l'on ne considère pas le
                                                      // noeud lui même
                    continue;                         // passe un tour de boucle
                }
                node = new Noeud(this.now, this.now.x + x, this.now.y + y, this.now.g, this.distance(x, y));
                if ((x != 0 || y != 0) // si on considère bien les noeuds contigus
                    && this.now.x + x >= 0 && this.now.x + x < this.laby.length // si l'on est bien sur le labyrinthe et pas hors frontières
                    && this.now.y + y >= 0 && this.now.y + y < this.laby[0].length
                    && this.laby[this.now.x + x][this.now.y + y] != 1 // si l'on ne rencontre pas un mur
                    && !trouverVoisinDansLaListe(this.ouvert, node) && !trouverVoisinDansLaListe(this.ferme, node)) {    /* si la file d'attente prioritaire et la
                                                                                                             * liste de sauvegarde des noeuds vérifiés
                                                                                                             * ne contiennent pas déjà ce noeud
                                                                                                             */
                        node.g = node.parent.g + 1.; // on augmente le cout de 1 (déplacement horizontal/vertical)
                        node.g += laby[this.now.x + x][this.now.y + y]; // ajoute le cout du mouvement vers cette case
 
                
                        this.ouvert.add(node); // ajoute le noeud à la file d'attente prioritaire
                        
                }
            }
        }
        Collections.sort(this.ouvert);// trie les noeuds de la file d'attente prioritaire (pour s'assurer que le noeud au coût heuristique
                                    // le plus bas soit bien en première position
    }
}
