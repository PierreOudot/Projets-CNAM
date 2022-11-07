package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PointDePassage {

    // on initialise les variables générales
    private ArrayList<Route>listeRoute=new ArrayList<>(); //stock toutes les routes menant/partant du point de passage
    private String nom; //nom du point de passage
    private boolean  parcouru=false; //booléen permettant de définir si le point de passage a été emprunté, faux par défaut
    private Integer degre=null;//permettra de determiner le degré du sommet en fonction du nombre des sommets auxquels
    //il est relié.

    // On stock tous les  points de passage voisins du point concerné. On utilise un hashset car on ne peut avoir n* la
    // même instance de la classe qu'il stock, utile en cas de multigbraphe (si plusieurs routes relient deux sommets),
    // le degré du sommet concerné ne varie pas pour autant (une route!=1sommet dans ce cas).
    // on utilisera ce Hashset pour détecter les impasses (sommets de degré 1) dans l'algorithme de Prim
    HashSet<PointDePassage>sommetsVoisins=new HashSet<>();
    // on utilisera l'aaraylist suivant pour y copier le contenu du hashset et accéder à chaque éléments via leur index
    ArrayList<PointDePassage>voisins;

    // constructeur de la classe. on intègre le nom l'arraylist de route sera rempli par la suite
    public PointDePassage(String nom) {
        this.nom = nom;
    }

    // On rempli sommetsVoisin et on calcule le degré d'un Point de Passage (=sommet) donné du hasmap représentant le graphe
    public void voisinsEtDegre(){
        //pour chaque route on récupère le sommet voisin du sommet concerné, celui dont le nom est différent.
        for (int i=0;i<this.getListeRoute().size();i++){
            String nomPoint=this.getNom();
            if(this.getListeRoute().get(i).getPointA().getNom().equals(nomPoint)){
                this.getSommetsVoisins().add(getListeRoute().get(i).getPointB());
            }else{
                this.getSommetsVoisins().add(getListeRoute().get(i).getPointA());
            }
        }

        //le gegré correspond au nombres de sommets voisins, donc à la taille de sommetsVoisin
        this.degre=sommetsVoisins.size();
        //on place le hashset à copier en paramètre du constructeur d'Arraylist pour obtenir un arrayList copie du Hashset
        voisins= new ArrayList<>(this.getSommetsVoisins());
    }

    //les accesseurs:
    public ArrayList<Route> getListeRoute() {
        return listeRoute;
    }

    public void setListeRoute(ArrayList<Route> listeRoute) {
        this.listeRoute = listeRoute;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isParcouru() {
        return parcouru;
    }

    public void setParcouru(boolean parcouru) {
        this.parcouru = parcouru;
    }

    public Integer getDegre() {
        return degre;
    }

    public void setDegre(Integer degre) {
        this.degre = degre;
    }

    public HashSet<PointDePassage> getSommetsVoisins() {
        return sommetsVoisins;
    }

    public void setSommetsVoisins(HashSet<PointDePassage> sommetsVoisins) {
        this.sommetsVoisins = sommetsVoisins;
    }

    public ArrayList<PointDePassage> getVoisins() {
        return voisins;
    }

    public void setVoisins(ArrayList<PointDePassage> voisins) {
        this.voisins = voisins;
    }
}
