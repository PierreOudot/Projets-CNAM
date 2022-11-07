package com.company;

import java.awt.*;

public class Barre extends RectangleCB{
    // champ definissant la vitesse de déplacement horizontale de la barre
    int vitesse;

    // champs stockant les coordonnées X et Y du milieu du coté haut de la barre, sert à régler la position initale
    // de(s) la(les) balle(s) et controler la direction de la balle au rebond.
    int milieuX;
    int milieuY;
    int milieuGX;
    int milieuGY;
    int milieuDX;
    int milieuDY;

    // cette variable n'a pour seul intérêt que d'apporter de la cohérence à la direction de la balle en de début de
    //partie, après une première collision avec le bas d'une brique
    int premiereCollision;







    public Barre() {
        setPositionX(220);
        setPositionY(482);
        setCouleur(Color.CYAN);
        setLargeur(60);
        setHauteur(10);
        setMilieuX(250);
        setMilieuY(482);
        setVitesse(2);
        setPremiereCollision(0);
    }

    // méthode pour dessiner la barre et délimiter sa médiane (un segment donc):
    @Override
    public void dessiner(Graphics2D dessin) {
        dessin.setColor(getCouleur());
        super.dessiner(dessin);
        dessin.setColor(Color.BLUE);
        dessin.drawLine(getMilieuX(),482,getMilieuX(),491);
    }

    // les méthodes de déplacement de la barre, en évitant que la barre ne traverse les paroies latérales:
    public void deplacementGauche(){
        if(getPositionX()>10){
            setPositionX(getPositionX()-getVitesse());
        }
    }

    public void deplacementDroite(){
        if(getPositionX()<(490-getLargeur())){
            setPositionX(getPositionX()+getVitesse());
        }
    }

    // on rempli l'arraylist de point pour chaque côté du périmètre
    public void remplirCoteHaut(Barre barre){
        PointRectangle point;
        for (int i=0;i<barre.getLargeur()+1;i++){
            point=new PointRectangle();
            point.setPointX(barre.getPositionX()+i);
            point.setPointY(barre.getPositionY());
            coteHaut.add(point);
        }
    }

    public void remplirCoteBas(Barre barre){
        PointRectangle point;
        for (int i=0;i<barre.getLargeur()+1;i++){
            point=new PointRectangle();
            point.setPointX(barre.getPositionX()+i);
            point.setPointY(barre.getPositionY()+barre.getHauteur());
            coteBas.add(point);
        }
    }

    public void remplirCoteGauche(Barre barre){
        PointRectangle point;
        for (int i=0;i<barre.getHauteur()+1;i++){
            point=new PointRectangle();
            point.setPointX(barre.getPositionX());
            point.setPointY(barre.getPositionY()+i);
            coteGauche.add(point);
        }
    }

    public void remplirCoteDroit(Barre barre){
        PointRectangle point;
        for (int i=0;i<barre.getHauteur()+1;i++){
            point=new PointRectangle();
            point.setPointX(barre.getPositionX()+barre.getLargeur());
            point.setPointY(barre.getPositionY()+i);
            coteDroit.add(point);
        }
    }

    // met à jour les arraylists en fonction du déplacement de la balle
    public void majCoteHaut(Barre barre){
        for (int i=0;i<barre.getCoteHaut().size();i++){
            barre.getCoteHaut().get(i).setPointX(barre.getPositionX()+i);
            barre.getCoteHaut().get(i).setPointY(barre.getPositionY());
        }
    }

    public void majCoteBas(Barre barre){
        for (int i=0;i<barre.getCoteBas().size();i++){
            barre.getCoteBas().get(i).setPointX(barre.getPositionX()+i);
            barre.getCoteBas().get(i).setPointY(barre.getPositionY()+barre.getHauteur());
        }
    }

    public void majCoteGauche(Barre barre){
        for (int i=0;i<barre.getCoteGauche().size();i++){
            barre.getCoteGauche().get(i).setPointX(barre.getPositionX());
            barre.getCoteGauche().get(i).setPointY(barre.getPositionY()+i);
        }
    }

    public void majCoteDroit(Barre barre){
        for (int i=0;i<barre.getCoteDroit().size();i++){
            barre.getCoteDroit().get(i).setPointX(barre.getPositionX()+barre.getLargeur());
            barre.getCoteDroit().get(i).setPointY(barre.getPositionY()+i);
        }
    }
    // methodes pour modifier la position du milieu du coté correspondant
    public void modifMilieuHaut(Barre barre){
        barre.setMilieuX(barre.getPositionX()+(barre.getLargeur()/2));
    }

    public void modifMilieuGauche(Barre barre){
        barre.setMilieuGX(barre.getPositionX());
        barre.setMilieuGY(barre.getPositionY()+(barre.getHauteur()/2));
    }

    public void modifMilieuDroit(Barre barre){
        barre.setMilieuDX(barre.getPositionX()+(barre.getLargeur()));
        barre.setMilieuDY(barre.getPositionY()+(barre.getHauteur()/2));
    }




    // les accesseurs pour les propriétés de Barre
    public int getVitesse() {
        return vitesse;
    }

    public int getMilieuX() {
        return milieuX;
    }

    public int getMilieuY() {
        return milieuY;
    }

    public int getMilieuGX() {
        return milieuGX;
    }

    public int getMilieuGY() {
        return milieuGY;
    }

    public int getMilieuDX() {
        return milieuDX;
    }

    public int getMilieuDY() {
        return milieuDY;
    }

    public int getPremiereCollision() {
        return premiereCollision;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public void setMilieuX(int milieuX) {
        this.milieuX = milieuX;
    }

    public void setMilieuY(int milieuY) {
        this.milieuY = milieuY;
    }

    public void setMilieuGX(int milieuGX) {
        this.milieuGX = milieuGX;
    }

    public void setMilieuGY(int milieuGY) {
        this.milieuGY = milieuGY;
    }

    public void setMilieuDX(int milieuDX) {
        this.milieuDX = milieuDX;
    }

    public void setMilieuDY(int milieuDY) {
        this.milieuDY = milieuDY;
    }

    public void setPremiereCollision(int premiereCollision) {
        this.premiereCollision = premiereCollision;
    }

    // on défini la couleur de la barre
    @Override
    public void setCouleur(Color couleur) {
        super.setCouleur(couleur);
    }

    @Override
    public Color getCouleur() {
        return super.getCouleur();
    }
}

