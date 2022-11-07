package com.company;

import java.awt.*;

public class PointSphere extends Sphere{
    // les champs suivants correspondent aux coordonnées X et Y de chaque point du périmètre:
    private int pointX;
    private int pointY;

    // le constructeur de la class PointSphere permet d'initialiser les champs précédents en fonction de l'instance de
    // de Sphere(et au final de Balle) à laquelle ils appartiennent
    public PointSphere(Balle balle) {
        setPointX(balle);
        setPointY(balle);
    }

    // on calcule les coordonnées du Point sur le périmètre de la sphère:
    public void setPointX(Balle balle) {
        this.pointX = (int)(balle.getPositionCentreX()+(balle.getRayon()*Math.cos(balle.getAngle())));
    }

    public void setPointY(Balle balle) {
        this.pointY = (int)(balle.getPositionCentreY()+(balle.getRayon()*Math.sin(balle.getAngle())));
    }

    // on récupère la position du Point sur le périmètre:
    public int getPointX() {
        return pointX;
    }

    public int getPointY() {
        return pointY;
    }

    //méthode pour dessiner le point, utilisée dans une autre méthode de la classe Sphere: (contrôle)
    public void dessinerPointSphere(Graphics2D dessin){
        dessin.setColor(Color.BLUE);
        dessin.fillRect(getPointX(),getPointY(),4,4);
    }
}

