package com.company;

public class Route {

    //on initialise les variables générales de la classe, une route connecte deux points de possage et représente une distance
    private PointDePassage pointA;
    private PointDePassage pointB;
    private int distance;
    private boolean parcouru=false;
    //on se servira de ce booléen pour afficher les routes dasn les produits des différents algorithmes
    private boolean estAffichee=false;

    //le constructeur prend en paramètre les points de passage reliés par la route et sa distance
    public Route(PointDePassage pointA, PointDePassage pointB, int distance) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.distance = distance;
    }

    //les accesseurs

    public PointDePassage getPointA() {
        return pointA;
    }

    public void setPointA(PointDePassage pointA) {
        this.pointA = pointA;
    }

    public PointDePassage getPointB() {
        return pointB;
    }

    public void setPointB(PointDePassage pointB) {
        this.pointB = pointB;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean isParcouru() {
        return parcouru;
    }

    public void setParcouru(boolean parcouru) {
        this.parcouru = parcouru;
    }

    public boolean isEstAffichee() {
        return estAffichee;
    }

    public void setEstAffichee(boolean estAffichee) {
        this.estAffichee = estAffichee;
    }
}
