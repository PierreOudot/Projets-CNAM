package com.company;

import java.awt.*;

public class PointRectangle extends RectangleCB {
    //  champs correspondant aux coordonnées X et Y de chaque point du périmètre du rectangle:
    int pointX;
    int pointY;

    public PointRectangle() {
    }

    // accesseurs de la classe PointRectangle
    public int getPointX() {
        return pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public void setPointX(int pointX) {
        this.pointX = pointX;
    }

    public void setPointY(int pointY) {
        this.pointY = pointY;
    }

    //methode pour dessiner les points des cotés des rectangles si besoin
    public void dessinerPointRect(Graphics2D dessin){
        dessin.setColor(Color.BLUE);
        dessin.fillRect(getPointX(),getPointY(),1,1);
    }
}

