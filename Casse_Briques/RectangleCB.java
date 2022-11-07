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

abstract class RectangleCB extends Sprite{
    // champs pour déterminer la largeur et la hauteur de tous les objets qui hériteront de la classe RectangleCB
    int largeur;
    int hauteur;

    // initialisation des arryalists qui stockeront les pooints de chaque côté du rectangle
    ArrayList<PointRectangle>coteHaut=new ArrayList<>();
    ArrayList<PointRectangle>coteBas=new ArrayList<>();
    ArrayList<PointRectangle>coteGauche=new ArrayList<>();
    ArrayList<PointRectangle>coteDroit=new ArrayList<>();



    //constructeur qui sera précisé dans les classes filles
    public RectangleCB() {
    }

    //methode dessiner pour les rectangles


    @Override
    public void dessiner(Graphics2D dessin) {
        super.dessiner(dessin);
        dessin.fillRect(getPositionX(),getPositionY(),getLargeur(),getHauteur());
    }

    // Les methodes qui permettent de remplir chaque arrayList, on ajoute la largeur ou la hauteur selon le coté,
    // pour obteni rla gamme de coordonnées
    // ce méthodes ne sont pas dans les constructeur correspondant car l'objet doit déjà être instancié pour qu'elles fonctionnent
    public void remplirCoteHaut(){
        PointRectangle point;
        for (int i=0;i<getLargeur()+1;i++){
            point=new PointRectangle();
            point.setPointX(getPositionX()+i);
            point.setPointY(getPositionY());
            coteHaut.add(point);
        }
    }

    public void remplirCoteBas(){
        PointRectangle point;
        for (int i=0;i<getLargeur()+1;i++){
            point=new PointRectangle();
            point.setPointX(getPositionX()+i);
            point.setPointY(getPositionY()+getHauteur());
            coteBas.add(point);
        }
    }

    public void remplirCoteGauche(){
        PointRectangle point;
        for (int i=0;i<getHauteur()+1;i++){
            point=new PointRectangle();
            point.setPointX(getPositionX());
            point.setPointY(getPositionY()+i);
            coteGauche.add(point);
        }
    }

    public void remplirCoteDroit(){
        PointRectangle point;
        for (int i=0;i<getHauteur()+1;i++){
            point=new PointRectangle();
            point.setPointX(getPositionX()+getLargeur());
            point.setPointY(getPositionY()+i);
            coteDroit.add(point);
        }
    }

    // les methodes qui permettent de mettre à jour les coordonnées des éléments de chaque arrayList
    // il seront utilisés surtout par la barre, sans savoir comment ce programme va évoluer, les methodes sont complète au cas où.

    public void majCoteHaut(){
        for (int i=0;i<getCoteHaut().size();i++){
            getCoteHaut().get(i).setPointX(getPositionX()+i);
            getCoteHaut().get(i).setPointY(getPositionY());
        }
    }

    public void majCoteBas(){
        for (int i=0;i<getCoteBas().size();i++){
            getCoteBas().get(i).setPointX(getPositionX()+i);
            getCoteBas().get(i).setPointY(getPositionY()+getHauteur());
        }
    }

    public void majCoteGauche(){
        for (int i=0;i<getCoteGauche().size();i++){
            getCoteGauche().get(i).setPointX(getPositionX());
            getCoteGauche().get(i).setPointY(getPositionY()+i);
        }
    }

    public void majCoteDroit(){
        for (int i=0;i<getCoteDroit().size();i++){
            getCoteDroit().get(i).setPointX(getPositionX()+getLargeur());
            getCoteDroit().get(i).setPointY(getPositionY()+i);
        }
    }



    // les accesseurs pour les champs Largeur et hauteur et les differents arraylists
    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public ArrayList<PointRectangle> getCoteHaut() {
        return coteHaut;
    }

    public ArrayList<PointRectangle> getCoteBas() {
        return coteBas;
    }

    public ArrayList<PointRectangle> getCoteGauche() {
        return coteGauche;
    }

    public ArrayList<PointRectangle> getCoteDroit() {
        return coteDroit;
    }

    public void setCoteHaut(ArrayList<PointRectangle> coteHaut) {
        this.coteHaut = coteHaut;
    }

    public void setCoteBas(ArrayList<PointRectangle> coteBas) {
        this.coteBas = coteBas;
    }

    public void setCoteGauche(ArrayList<PointRectangle> coteGauche) {
        this.coteGauche = coteGauche;
    }

    public void setCoteDroit(ArrayList<PointRectangle> coteDroit) {
        this.coteDroit = coteDroit;
    }

    // idem que pour la classe Sphere, obliger de surcharger les méthodes de Graphics et Graphics2D.
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
