package com.company;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Map;

abstract class Sphere extends Sprite {
    /* On utilise la méthode Graphics2D.fill/drawOval pour obtenir une sphère. Elle sera dessinée dans un rectangle,
    (ou plutôt un carré dans ce cas) invisible. Les coordonnées X et Y correspondront au coin supérieur gauche de ce
    dernier.
    le rayon sera égal à 1/2*la hauteur= 1/2* la largeur. */

    private int largeur;
    private int hauteur;
    private int rayon;

    // les variables suivantes serviront pour la gestion des collisions avec les briques et la barre:

    // on stock les coordonnées du centre de la sphere.
    private int[] positionCentre=new int[2];

    // on utilisera une valeur d'angle, de 0 à 359 (fonctionne en degrés et non radiants, pour calculer la position
    // des points sur le perimetre de la sphère:
    private double angle;

    // on stock toutes les instances de Point dans un ArrayList, pour y accéder ultérieurement via leur index:
    // c'est la position  de ces points permettra de gérer les collisions (briques/barres) avec le périmètre de la balle
    // et non son centre.
    private ArrayList<PointSphere> listePointSphere =new ArrayList<>();

    // constructeur vide, sera précisé dans la classe balle:
    public Sphere() {
    }

    // On précise la méthode dessiner pour une sphère:
    @Override
    public void dessiner(Graphics2D dessin) {
        super.dessiner(dessin);
        dessin.fillOval(getPositionX(),getPositionY(),getLargeur(),getHauteur());
    }

    // méthode pour remplir l'arraylist avec chaque point sur le périmètre de la sphère:
    public void remplirlistePointSphere(Balle balle) {
        PointSphere point;
        balle.setAngle(0);
        for(int i=0;i<360;i++){
            point=new PointSphere(balle);
            balle.getListePointSphere().add(point);
            balle.setAngle(balle.getAngle()+1);
        }
    }

    // méthode pour modifier les coordonnées de chaque instance de Point dans l'arraylist:
    public void majListePointSphere(Balle balle){
        balle.setPositionCentreX();
        balle.setPositionCentreY();
        balle.setAngle(0);
        for(int i =0;i<360;i++){
            balle.getListePointSphere().get(i).setPointX(balle);
            balle.getListePointSphere().get(i).setPointY(balle);
            balle.setAngle(getAngle()+1);
        }
    }

    // méthode pour afficher les points du périmètre si besoin:
    public void afficherPointPerimetre(Graphics2D dessin, Balle balle){
        for(int i=0;i<360;i++){
            balle.getListePointSphere().get(i).dessinerPointSphere(dessin);
        }
    }

    // On prévoit les accesseurs pour toutes les variables.
    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public void setRayon(int rayon) {
        this.rayon = rayon;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getRayon() {
        return rayon;
    }

    public ArrayList<PointSphere> getListePointSphere() {
        return listePointSphere;
    }

    public void setListePointSphere(ArrayList<PointSphere> listePointSphere) {
        this.listePointSphere = listePointSphere;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    // on determine la position du centre de la sphere.
    public void setPositionCentreX(){
        positionCentre[0]= getPositionX()+getRayon();
    }
    public void setPositionCentreY(){
        positionCentre[1]= getPositionY()+getRayon();
    }
    public int getPositionCentreX(){
        return positionCentre[0];
    }
    public int getPositionCentreY(){
        return positionCentre[1];
    }


    // Sphere hérite de Sprite,on doit surcharger les méthodes des classes Graphics et Graphics 2D.
    @Override
    public void draw(Shape s) {

    }

    @Override
    public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        return false;
    }

    @Override
    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {

    }

    @Override
    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {

    }

    @Override
    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {

    }

    @Override
    public void drawString(String str, int x, int y) {

    }

    @Override
    public void drawString(String str, float x, float y) {

    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {

    }

    @Override
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return false;
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return false;
    }

    @Override
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return false;
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return false;
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        return false;
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
        return false;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, float x, float y) {

    }

    @Override
    public void drawGlyphVector(GlyphVector g, float x, float y) {

    }

    @Override
    public void fill(Shape s) {

    }

    @Override
    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        return false;
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        return null;
    }

    @Override
    public void setComposite(Composite comp) {

    }

    @Override
    public void setPaint(Paint paint) {

    }

    @Override
    public void setStroke(Stroke s) {

    }

    @Override
    public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {

    }

    @Override
    public Object getRenderingHint(RenderingHints.Key hintKey) {
        return null;
    }

    @Override
    public void setRenderingHints(Map<?, ?> hints) {

    }

    @Override
    public void addRenderingHints(Map<?, ?> hints) {

    }

    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }

    @Override
    public Graphics create() {
        return null;
    }

    @Override
    public void translate(int x, int y) {

    }

    @Override
    public void setPaintMode() {

    }

    @Override
    public void setXORMode(Color c1) {

    }

    @Override
    public Font getFont() {
        return null;
    }

    @Override
    public void setFont(Font font) {

    }

    @Override
    public FontMetrics getFontMetrics(Font f) {
        return null;
    }

    @Override
    public Rectangle getClipBounds() {
        return null;
    }

    @Override
    public void clipRect(int x, int y, int width, int height) {

    }

    @Override
    public void setClip(int x, int y, int width, int height) {

    }

    @Override
    public Shape getClip() {
        return null;
    }

    @Override
    public void setClip(Shape clip) {

    }

    @Override
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {

    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {

    }

    @Override
    public void fillRect(int x, int y, int width, int height) {

    }

    @Override
    public void clearRect(int x, int y, int width, int height) {

    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

    }

    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

    }

    @Override
    public void drawOval(int x, int y, int width, int height) {

    }

    @Override
    public void fillOval(int x, int y, int width, int height) {

    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

    }

    @Override
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {

    }

    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {

    }

    @Override
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {

    }

    @Override
    public void translate(double tx, double ty) {

    }

    @Override
    public void rotate(double theta) {

    }

    @Override
    public void rotate(double theta, double x, double y) {

    }

    @Override
    public void scale(double sx, double sy) {

    }

    @Override
    public void shear(double shx, double shy) {

    }

    @Override
    public void transform(AffineTransform Tx) {

    }

    @Override
    public void setTransform(AffineTransform Tx) {

    }

    @Override
    public AffineTransform getTransform() {
        return null;
    }

    @Override
    public Paint getPaint() {
        return null;
    }

    @Override
    public Composite getComposite() {
        return null;
    }

    @Override
    public void setBackground(Color color) {

    }

    @Override
    public Color getBackground() {
        return null;
    }

    @Override
    public Stroke getStroke() {
        return null;
    }

    @Override
    public void clip(Shape s) {

    }

    @Override
    public FontRenderContext getFontRenderContext() {
        return null;
    }

}
