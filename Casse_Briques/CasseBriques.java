package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;

import java.util.ArrayList;

public class CasseBriques extends JFrame implements KeyListener/*, ActionListener*/ {
    // on initialise les booléens qui seront utilisés pour le déplacement de la barre
    private boolean flecheGauche=false;
    private boolean flecheDroite=false;


    // on initialise l'arraylist qui accueuillera les briques à détruire
    private ArrayList<Briques>listeBriques=new ArrayList<>();
    // on passe la variable brique en variable générale

    //on initialise l'instance d'Attributs attributs ici pour qu'elle soit visible des keylisteners
    Attributs attributs=null;

    // on initialise les différents boutons, permet les réglages dans le constructeur
    JButton nouvelEssai;//permet de remettre à zero la partie en cas de défaite de victoire
    JButton musiqueOn;//relance la musique
    JButton musiqueOff;//pause la musique



    public CasseBriques(){
        setSize(501,508);//une partie des éléments etait masqué (500*500)
        // sous windows.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        // change la strategie de rafraichissemnt
        createBufferStrategy(2);
        setIgnoreRepaint(true);
        addKeyListener(this);
        requestFocus();
        setFocusable(true);
        // Precision sur les boutons:ils sont là, fonctionnent et apparaissent quand on passe la souris dessus, mais impossible de les
        // faire s'afficher de manière permanante sur les Graphics2D( cf Precisions)
        // Ils apparaissent correctement en fin de partie (si la souris passe dessus)
        nouvelEssai=new JButton("TRY AGAIN");// définit au passage le texte du bouton
        nouvelEssai.setBounds(200,350,100,50);
        nouvelEssai.addActionListener(e ->attributs.setTryAgain(true));// l'expression lambda evite d'avoir à implémenter
        // une méthode actionListener. Définit l'action du bouton une fois cliqué.
        nouvelEssai.setFocusable(false);//evite l'apparition du rectangle entourant le texte du bouton
        nouvelEssai.setFont(new Font("Courier",Font.PLAIN,12));
        nouvelEssai.setBorder(BorderFactory.createEtchedBorder());//donne un effet de relief au bouton
        nouvelEssai.setForeground(Color.GREEN);//definit la couleur du premier plan (donc du text du bouton)
        nouvelEssai.setBackground(Color.MAGENTA);//definit la couleur du bouton
        musiqueOn=new JButton("ON");
        musiqueOn.setBounds(400,2,30,15);
        musiqueOn.addActionListener(e->attributs.boucleMusicale());
        musiqueOn.setFocusable(false);
        musiqueOn.setFont(new Font("Courier",Font.PLAIN,10));
        musiqueOn.setBorder(BorderFactory.createEtchedBorder());
        musiqueOn.setForeground(Color.GREEN);
        musiqueOn.setBackground(Color.GRAY);
        musiqueOff=new JButton("OFF");
        musiqueOff.setBounds(435,2,30,15);
        musiqueOff.addActionListener(e->attributs.stopMusique());
        musiqueOff.setFocusable(false);
        musiqueOff.setFont(new Font("Courier",Font.PLAIN,10));
        musiqueOff.setBorder(BorderFactory.createEtchedBorder());
        musiqueOff.setForeground(Color.RED);
        musiqueOff.setBackground(Color.GRAY);


        initialisationJeu();
    }

    private Void initialisationJeu(){
        Briques brique = null;
        // on cré d'abord la barre pour générer la balle sur le centre du coté supérieur
        Barre barre= new Barre();
        barre.remplirCoteHaut();
        barre.remplirCoteDroit();
        barre.remplirCoteGauche();


        // on génère la barre
        Balle balle= new Balle(barre);
        balle.remplirlistePointSphere(balle);

        // on cré un ArrayList qui permettra de gérer un affichage multiballes.
        ArrayList<Balle> listeBalle = new ArrayList<>();
        listeBalle.add(balle);

        // on génère l'arrayList de briques à détruire (5 lignes et 10 colones de briques):
        for(int i=0; i<5;i++){
            for(int j=0;j<10;j++){
                brique=new Briques();
                brique.setPositionX(10+(j*brique.getLargeur()));
                brique.setPositionY(60+(i*brique.getHauteur()));
                brique.remplirCoteHaut();
                brique.remplirCoteBas();
                brique.remplirCoteGauche();
                brique.remplirCoteDroit();
                listeBriques.add(brique);
            }
        }

        // on initialise les différents champs de la classe attributs en créant une instance d'Attributs
        attributs=new Attributs();


        // on prepare le remplissage de rectangle avec un gradient de couleur, il est necessaire d'avoir toutes les
        // coordonnées de gradient possibles. Sympa mais ralenti le jeu une fois dans la boucle infinie sous ma distro
        // de Gnu/linux.
        GradientPaint lave;
        ArrayList<GradientPaint>bordureBas=new ArrayList<>();
        for(int i=0;i<501;i++){
            lave=new GradientPaint(i,500,Color.YELLOW,i,490,Color.RED,false);
            bordureBas.add(lave);
        }

        // on commence une boucle infinie
        while(true){

            //lance la boucle musicale
            attributs.chargementMusique();
            attributs.boucleMusicale();
            //redémarre la partie en cas de défaite, ou de victoire
            if(attributs.isTryAgain()==true){
                remove(nouvelEssai);
                attributs.setGameOver(false);
                attributs.setVictoire(false);
                attributs.setStart(false);
                attributs.setLancement(false);
                attributs.setTryAgain(false);
                attributs.setNbrVies(3);
                attributs.setScore(0);
                attributs.setBriquesDétruites(0);
                listeBriques.clear();
                for(int i=0; i<5;i++){
                    for(int j=0;j<10;j++){
                        brique=new Briques();
                        brique.setPositionX(10+(j*brique.getLargeur()));
                        brique.setPositionY(60+(i*brique.getHauteur()));
                        brique.remplirCoteHaut();
                        brique.remplirCoteBas();
                        brique.remplirCoteGauche();
                        brique.remplirCoteDroit();
                        listeBriques.add(brique);
                    }
                }
                barre.setPositionX(220);
                barre.setPositionY(482);
                barre.setLargeur(60);
                barre.setHauteur(10);
                barre.setMilieuX(250);
                barre.setMilieuY(482);
                barre.setVitesse(2);
                barre.setPremiereCollision(0);
                barre.modifMilieuHaut(barre);
                barre.modifMilieuGauche(barre);
                barre.modifMilieuDroit(barre);
                barre.majCoteHaut(barre);
                barre.majCoteGauche(barre);
                barre.majCoteDroit(barre);
                balle.setPositionX(barre.getMilieuX()-5);
                balle.setPositionY(barre.getMilieuY()-10);
                balle.setLargeur(10);
                balle.setHauteur(10);
                balle.setRayon(5);
                balle.setPositionCentreX();
                balle.setPositionCentreY();
                balle.majCoordoCentre(balle);
                balle.majListePointSphere(balle);
            }


            // arret de la boucle en cas de victoire ou de défaite
            while(attributs.isGameOver()==false&&attributs.isVictoire()==false){
                Graphics2D dessin = (Graphics2D)getBufferStrategy().getDrawGraphics();

                // effacer le dessin pour donner le mouvement et affichage des bordures de la zone de jeu:
                dessin.setColor(Color.BLACK);
                dessin.fillRect(0,0,500,500);// modifié car faisait bugger l'affichage des boutons
                //de gestion de la musqiue
                // bordure haute (chez moi la balle rentre dans l'entête de la fenêtre)
                dessin.setColor(Color.DARK_GRAY);
                dessin.fillRect(0,0,500,60);//idem
                // on affiche le nombre de vies, le nombre de briques détruites, le score et les boutons
                // de gestion de la musique en haut de la fenêtre
                dessin.setColor(Color.WHITE);
                dessin.setFont(new Font("courier",Font.PLAIN,10));
                dessin.drawString("Vie:",10,50);
                if(attributs.getNbrVies()<=1){
                    dessin.setColor(Color.RED);
                }else{
                    dessin.setColor(Color.GREEN);
                }
                dessin.setFont(new Font("courier",Font.BOLD,10));
                dessin.drawString(String.valueOf(attributs.getNbrVies()),35,50);
                dessin.setColor(Color.WHITE);
                dessin.setFont(new Font("courier",Font.PLAIN,10));
                dessin.drawString("Briques détruites:",55,50);
                dessin.setColor(Color.PINK);
                dessin.drawString(String.valueOf(attributs.getBriquesDétruites())+" /50",165,50);
                dessin.setColor(Color.WHITE);
                dessin.setFont(new Font("courier",Font.PLAIN,10));
                dessin.drawString("Score:",210,50);
                dessin.setColor(Color.BLUE);
                dessin.drawString(String.valueOf(attributs.getScore()),250,50);
                dessin.setColor(Color.WHITE);
                dessin.setFont(new Font("courier",Font.PLAIN,10));
                dessin.drawString("Musique:",350,50);
                add(musiqueOn);
                add(musiqueOff);


                // bordure gauche:
                dessin.setColor(Color.DARK_GRAY);
                dessin.fillRect(0,0,10,500);
                // bordure droite
                dessin.setColor(Color.DARK_GRAY);
                dessin.fillRect(490,0,10,500);

                // bordure bas
                // on utilise une boucle pour afficher tous les rectangles avec les bonnes cordonnées de gradient
                // (ralenti le jeu)
                for(int i=10;i<490;i++) {
                    dessin.setPaint(bordureBas.get(i));
                    dessin.fillRect(i, 490, 1, 10);
                }

                // tout le contenu à rafraichir:

                // On implémente les méthodes qui permettront le déplacement fluide de la barre grace au booléen:
                if(isFlecheGauche()==true){
                    barre.deplacementGauche();
                }else if (isFlecheDroite()==true){
                    barre.deplacementDroite();
                }

                // maj des arraylistes de points et des centre de chaque coté de la barre potentiellement en contact avec la balle
                barre.modifMilieuHaut(barre);
                barre.modifMilieuGauche(barre);
                barre.modifMilieuDroit(barre);
                barre.majCoteHaut(barre);
                barre.majCoteGauche(barre);
                barre.majCoteDroit(barre);

                // gestion des conditions de victoire et de défaite
                // ici pour éviter qu'une brique détruite n'apparaisse encore à l'écran
                if(attributs.getBriquesDétruites()==50){
                    attributs.setVictoire(true);// utilisé pour arrêter la boucle de jeu
                    attributs.chargementVictoire();// on (re)charge chaque son avant de le lire
                    attributs.gagne();// on lit le clip
                    // on affiche un texte et le score
                    dessin.setColor(Color.CYAN);
                    dessin.setFont(new Font("courier",Font.PLAIN,60));
                    dessin.drawString("GG",170,270);
                    dessin.setColor(Color.WHITE);
                    dessin.setFont(new Font("courier",Font.PLAIN,30));
                    dessin.drawString("Score:",170,350);
                    dessin.setColor(Color.BLUE);
                    dessin.drawString(String.valueOf(attributs.getScore()),260,350);
                    add(nouvelEssai);// on affiche le boutton pour recommencer une nouvelle partie

                }

                // gestion de la condition de défaite
                if(attributs.getNbrVies()==0){
                    attributs.setGameOver(true);
                    attributs.chargementGameOver();
                    attributs.gameOver();
                    dessin.setColor(Color.RED);
                    dessin.setFont(new Font("courier",Font.PLAIN,60));
                    dessin.drawString("GAME OVER",75,270);
                    dessin.setColor(Color.WHITE);
                    dessin.setFont(new Font("courier",Font.PLAIN,30));
                    dessin.drawString("Score:",170,350);
                    dessin.setColor(Color.BLUE);
                    dessin.drawString(String.valueOf(attributs.getScore()),260,350);
                    add(nouvelEssai);
                }

                // on modifie les positions X et Y de la (les) balle(s) pour qu'elle(s) se déplace(nt) sur l'écran
                //en fonction de l'état de la partie (debut ou en cours de jeu)

                //permet de modifier le comportement de la balle après la première collision
                if(barre.getPremiereCollision()==2){
                    attributs.setLancement(true);
                }

                if(attributs.isStart()==false){//la balle reste sur le milieu de la barre
                    balle.setPositionX(barre.getMilieuX()-5);
                    balle.setPositionY(barre.getMilieuY()-10);
                }else if((attributs.isLancement()==false)&&(attributs.isStart()==true)){

                    balle.setPositionY(balle.getPositionY()+balle.getVitesseVerticale());
                }else{
                    balle.setPositionY(balle.getPositionY()+balle.getVitesseVerticale());
                    balle.setPositionX(balle.getPositionX()+balle.getVitesseHorizontale());
                }

                // on met à jour les psoition du centre de la (les) balle(s) pour pouvoir mettre à jour les points du périmetre
                balle.majCoordoCentre(balle);
                balle.majListePointSphere(balle);

                // on affiche les briques qui n'ont pas été détruites
                for(int i=0; i<listeBriques.size();i++){
                    listeBriques.get(i).dessiner(dessin);
                }
                // on affiche un cadrillage pour délimiter chaque brique
                dessin.setColor(Color.BLACK);
                dessin.setStroke(new BasicStroke(1.0f));
                dessin.drawLine(10,60,489,60);
                dessin.drawLine(10,90,489,90);
                dessin.drawLine(10,120,489,120);
                dessin.drawLine(10,120,489,120);
                dessin.drawLine(10,150,489,150);
                dessin.drawLine(10,180,489,180);
                dessin.drawLine(10,210,489,210);

                dessin.drawLine(10,60,10,210);
                dessin.drawLine(58,60,58,210);
                dessin.drawLine(106,60,106,210);
                dessin.drawLine(154,60,154,210);
                dessin.drawLine(202,60,202,210);
                dessin.drawLine(250,60,250,210);
                dessin.drawLine(298,60,298,210);
                dessin.drawLine(346,60,346,210);
                dessin.drawLine(394,60,394,210);
                dessin.drawLine(442,60,442,210);
                dessin.drawLine(489,60,489,210);

                //on affiche la barre
                barre.dessiner(dessin);

                // on affiche la(les) balle(s)
                balle.dessiner(dessin);
                //listeBalle.forEach((Balle)->balle.afficherPointPerimetre(dessin, balle));// ce qui concernait la balle était
                // initialement coder de cette manière, artefact mais fonctionne. Affiche tous les objets de l'arraylist contenant
                // les points du périmètre de la balle

                // gestion de la collision avec les briques:
                // on teste en permanence si un point de l'arrayListe de points du périmètre de la balle à la même valeur
                // qu'un point du périmètre d'une brique
                // avec le côté supérieur de la brique:
                for(int i =0; i<balle.getListePointSphere().size();i++){
                    int balleX=balle.getListePointSphere().get(i).getPointX();
                    int balleY=balle.getListePointSphere().get(i).getPointY();
                    for(int j=0;j<getListeBriques().size();j++){
                        for(int k=0;k<getListeBriques().get(j).getCoteHaut().size();k++){
                            if(getListeBriques().get(j).isEstdetruite()==false){
                                int briqueX=getListeBriques().get(j).getCoteHaut().get(k).getPointX();
                                int briqueY=getListeBriques().get(j).getCoteHaut().get(k).getPointY();

                                if((balleX==briqueX)&&(balleY==briqueY)&&balle.isCollisionBriqueHaut()==false){
                                    getListeBriques().get(j).setResistance(getListeBriques().get(j).getResistance()-1);
                                    balle.inverseVitesseVerticale();
                                    //eviter les collisions multiples au sein de la boucle
                                    balle.setCollisionBarre(false);
                                    balle.setCollisionBriqueHaut(true);
                                    //on reset les autres booléens de cette catégorie à false pour gérer les rebonds de brique
                                    //à brique de la balle.
                                    balle.setCollisionBriqueBas(false);
                                    balle.setCollisionBriqueGauche(false);
                                    balle.setCollisionBriqueDroite(false);
                                    //on charge et lit le clip correspondant à l'évennement
                                    attributs.chargementSonBrique();
                                    attributs.chocBrique();
                                    // on détruit la brique si ça resistance atteint 0
                                    if(getListeBriques().get(j).getResistance()==0){
                                        getListeBriques().get(j).setEstdetruite(true);
                                        //on augmente de 1 le nombre de briques detruites de 1
                                        attributs.setBriquesDétruites(attributs.getBriquesDétruites()+1);
                                        //on augmente le score de 100 par brique détruite
                                        attributs.setScore(attributs.getScore()+100);
                                        //on charge et lit le clip correspondant
                                        attributs.chargementExplosionBrique();
                                        attributs.destructionBrique();
                                    }
                                }
                            }
                        }
                    }
                }

                // avec le côté inférieur:
                for(int i =0; i<balle.getListePointSphere().size();i++){
                    int balleX=balle.getListePointSphere().get(i).getPointX();
                    int balleY=balle.getListePointSphere().get(i).getPointY();
                    for(int j=0;j<getListeBriques().size();j++){
                        if(getListeBriques().get(j).isEstdetruite()==false){
                            for(int k=0;k<getListeBriques().get(j).getCoteBas().size();k++){
                                int briqueX=getListeBriques().get(j).getCoteBas().get(k).getPointX();
                                int briqueY=getListeBriques().get(j).getCoteBas().get(k).getPointY();

                                if((balleX==briqueX)&&(balleY==briqueY)&&balle.isCollisionBriqueBas()==false){
                                    getListeBriques().get(j).setResistance(getListeBriques().get(j).getResistance()-1);
                                    balle.inverseVitesseVerticale();
                                    balle.setCollisionBarre(false);
                                    balle.setCollisionBriqueBas(true);
                                    balle.setCollisionBriqueHaut(false);
                                    balle.setCollisionBriqueGauche(false);
                                    balle.setCollisionBriqueDroite(false);
                                    attributs.chargementSonBrique();
                                    attributs.chocBrique();
                                    if(barre.getPremiereCollision()==0){// pour avoir des direction differente entre le lancement
                                        barre.setPremiereCollision(barre.getPremiereCollision()+1);//de la partie et le jeu en cours.
                                    }
                                    if(getListeBriques().get(j).getResistance()==0){
                                        getListeBriques().get(j).setEstdetruite(true);
                                        attributs.setBriquesDétruites(attributs.getBriquesDétruites()+1);
                                        attributs.setScore(attributs.getScore()+100);
                                        attributs.chargementExplosionBrique();
                                        attributs.destructionBrique();
                                    }
                                }
                            }
                        }
                    }
                }

                // avec le côté gauche:
                for(int i =0; i<balle.getListePointSphere().size();i++){
                    int balleX=balle.getListePointSphere().get(i).getPointX();
                    int balleY=balle.getListePointSphere().get(i).getPointY();
                    for(int j=0;j<getListeBriques().size();j++){
                        if(getListeBriques().get(j).isEstdetruite()==false){
                            for(int k=0;k<getListeBriques().get(j).getCoteGauche().size();k++){
                                int briqueX=getListeBriques().get(j).getCoteGauche().get(k).getPointX();
                                int briqueY=getListeBriques().get(j).getCoteGauche().get(k).getPointY();

                                if((balleX==briqueX)&&(balleY==briqueY)&&balle.isCollisionBriqueGauche()==false){
                                    getListeBriques().get(j).setResistance(getListeBriques().get(j).getResistance()-1);
                                    balle.inverseVitesseHorizontale();
                                    balle.setCollisionBarre(false);
                                    balle.setCollisionBriqueGauche(true);
                                    balle.setCollisionBriqueBas(false);
                                    balle.setCollisionBriqueHaut(false);
                                    balle.setCollisionBriqueDroite(false);
                                    attributs.chargementSonBrique();
                                    attributs.chocBrique();
                                    if(getListeBriques().get(j).getResistance()==0){
                                        getListeBriques().get(j).setEstdetruite(true);
                                        attributs.setBriquesDétruites(attributs.getBriquesDétruites()+1);
                                        attributs.setScore(attributs.getScore()+100);
                                        attributs.chargementExplosionBrique();
                                        attributs.destructionBrique();
                                    }
                                }
                            }
                        }
                    }
                }

                // avec le côté droit:
                for(int i =0; i<balle.getListePointSphere().size();i++){
                    int balleX=balle.getListePointSphere().get(i).getPointX();
                    int balleY=balle.getListePointSphere().get(i).getPointY();
                    for(int j=0;j<getListeBriques().size();j++){
                        if(getListeBriques().get(j).isEstdetruite()==false){
                            for(int k=0;k<getListeBriques().get(j).getCoteDroit().size();k++){
                                int briqueX=getListeBriques().get(j).getCoteDroit().get(k).getPointX();
                                int briqueY=getListeBriques().get(j).getCoteDroit().get(k).getPointY();

                                if((balleX==briqueX)&&(balleY==briqueY)&&balle.isCollisionBriqueDroite()==false){
                                    getListeBriques().get(j).setResistance(getListeBriques().get(j).getResistance()-1);
                                    balle.inverseVitesseHorizontale();
                                    balle.setCollisionBarre(false);
                                    balle.setCollisionBriqueDroite(true);
                                    balle.setCollisionBriqueBas(false);
                                    balle.setCollisionBriqueHaut(false);
                                    balle.setCollisionBriqueGauche(false);
                                    attributs.chargementSonBrique();
                                    attributs.chocBrique();
                                    if(getListeBriques().get(j).getResistance()==0){
                                        getListeBriques().get(j).setEstdetruite(true);
                                        attributs.setBriquesDétruites(attributs.getBriquesDétruites()+1);
                                        attributs.setScore(attributs.getScore()+100);
                                        attributs.chargementExplosionBrique();
                                        attributs.destructionBrique();
                                    }
                                }
                            }
                        }
                    }
                }

                // gestion des collision de la balle avec les paroies de l'environnement.
                balle.collisionDroite(attributs);
                balle.collisionGauche(attributs);
                balle.collisionHaut(attributs);
                balle.collisionBas(attributs, barre);

                // gestion de la collision de la balle avec la barre
                balle.collisionBarreHaut(barre,balle, attributs);
                balle.collisionBarreGauche(barre,balle, attributs);
                balle.collisionBarreDroit(barre,balle, attributs);

                //on demande le rafraichissement de la fentre
                dessin.dispose();
                getBufferStrategy().show();

                try{
                    // thread=processus actuel
                    // on defini la fréquence de rafraichissement en incluant une pause dans l'execution de la boucle
                    Thread.sleep(1000/60);// jouer sur ce paramètre pour que le jeu soit plus dynamique à cause
                    //de la méthode de gestion des collisions (barre/briques) implémentée.
                }catch(Exception e){

                }
            }

        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // selon la fleche directionnelle enfoncée, on initialise le booléen correspondant à true
        // en utilisant le booléen pour controler le déplacement de la barre, on suprime l'impact de la répétition des touches
        // enfoncées sur la fluidité du déplacement de la barre.
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            setFlecheGauche(true);
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            setFlecheDroite(true);
        }
        if(e.getKeyCode()==KeyEvent.VK_UP){
            if(attributs.isStart()==false){
                attributs.chargementLancement();
                attributs.lancement();
            }
            attributs.setStart(true);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // une fois la touche relachée, le booléen repasse à false et la barre ne se déplace plus.
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            setFlecheGauche(false);
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            setFlecheDroite(false);
        }

    }

    // accesseurs pour la classe CasseBriques

    public boolean isFlecheGauche() {
        return flecheGauche;
    }

    public boolean isFlecheDroite() {
        return flecheDroite;
    }

    public ArrayList<Briques> getListeBriques() {
        return listeBriques;
    }

    public void setFlecheGauche(boolean flecheGauche) {
        this.flecheGauche = flecheGauche;
    }

    public void setFlecheDroite(boolean flecheDroite) {
        this.flecheDroite = flecheDroite;
    }

    //methode main pour faire tourner le programme
    public static void main(String[] args) {
        new CasseBriques();

    }

}