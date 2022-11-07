package com.company;

import javax.sound.sampled.*;
import java.io.*;

public class Attributs {
    // cette classe permettra de gérer divers éléments comme le nombre de vies, le nombres de briques détruites
    // le game over, try again et les sons.
    private int nbrVies;
    private int briquesDétruites;
    private int score;
    private boolean gameOver;
    private boolean lancement;
    private boolean start;
    private boolean tryAgain;
    private boolean victoire;

    // on initialise les différents Clip
    private Clip musiqueJeu;
    private Clip lancementBalle;
    private Clip collisionParoie;
    private Clip collisionBarre;
    private Clip collisionBrique;
    private Clip destructionBrique;
    private Clip lave;
    private Clip perdu;
    private Clip gagne;

    // on stock tout les chemin pour les fichiers audio sources (facilité d'écriture et évolution du code)
    private String cheminmusique="com/company/sons/Brick Morty.wav";
    private String cheminParoie= "com/company/sons/CollisionParoies.wav";
    private String cheminLave= "com/company/sons/BainLave.aiff";
    private String cheminBarre= "com/company/sons/CollisionBarre.wav";
    private String cheminBrique= "com/company/sons/CollisionBriques.wav";
    private String cheminExplosion= "com/company/sons/ExplosionBrique.wav";
    private String cheminGameOver= "com/company/sons/GameOver.wav";
    private String cheminVictoire= "com/company/sons/Victoire.wav";
    private String cheminLancement= "com/company/sons/TirLancement.wav";

    public Attributs() {
        setNbrVies(3);
        setBriquesDétruites(0);
        setScore(0);
        setGameOver(false);
        setStart(false);
        setTryAgain(false);
        setVictoire(false);
    }

    // gestion de l'audio:
    // methodes pour préparer la lecture du  fichier audio
    // j'ai prévu un jeu de methodes pour chaque pour faciliter l'écriture dans les autres classes

    // preparation de la musique
    public void chargementMusique(){
        // try catch obligatoire,imposé par la classe AudioInputStream. Permet de savoir si le fichier à bien été trouvé

        try{
            File musique=new File(getCheminmusique());//on indique le fichier source
            AudioInputStream brickMorty= AudioSystem.getAudioInputStream(musique);//on cré une piste découpé en "frame"
            // (au lieu des secondes pour une écoute traditionnelle. On pourra accéder à n'importe quel frame du morceau
            // Le dernier frame lu est gardé en mémoire.La classe audiosystem permet d'accéder au système son de l'ordianteur
            setMusiqueJeu(AudioSystem.getClip());// Clip est une interface qui permet de charger et de diffuser le morceau
            // via le système son de l'ordinateur, en definissant sa durée. Ce qui permettra de naviguer dans le morceau,
            // de l'arrêter...
            getMusiqueJeu().open(brickMorty);//


        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    // preparation du choc avec les paroies
    public void chargementSonParoie(){
        try{
            File paroie=new File(getCheminParoie());
            AudioInputStream chocParoie= AudioSystem.getAudioInputStream(paroie);
            setCollisionParoie(AudioSystem.getClip());
            getCollisionParoie().open(chocParoie);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // preparation en cas de passage de la balle dans la bordure inférieure
    public void chargementSonLave(){
        try{
            File perdVie=new File(getCheminLave());
            AudioInputStream chocLave= AudioSystem.getAudioInputStream(perdVie);
            setLave(AudioSystem.getClip());
            getLave().open(chocLave);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // preparation du choc avec la barre
    public void chargementSonBarre(){
        try{
            File barre=new File(getCheminBarre());
            AudioInputStream chocBarre= AudioSystem.getAudioInputStream(barre);
            setCollisionBarre(AudioSystem.getClip());
            getCollisionBarre().open(chocBarre);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // preparation du choc avec les briques
    public void chargementSonBrique(){
        try{
            File brique=new File(getCheminBrique());
            AudioInputStream chocBrique= AudioSystem.getAudioInputStream(brique);
            setCollisionBrique(AudioSystem.getClip());
            getCollisionBrique().open(chocBrique);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // preparation de la destruction des briques
    public void chargementExplosionBrique(){
        try{
            File explosion=new File(getCheminExplosion());
            AudioInputStream ExploBrique= AudioSystem.getAudioInputStream(explosion);
            setDestructionBrique(AudioSystem.getClip());
            getDestructionBrique().open(ExploBrique);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // preparation du game over
    public void chargementGameOver(){
        try{
            File gameOver=new File(getCheminGameOver());
            AudioInputStream finPartie= AudioSystem.getAudioInputStream(gameOver);
            setPerdu(AudioSystem.getClip());
            getPerdu().open(finPartie);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // preparation de la victoire
    public void chargementVictoire(){
        try{
            File victoire=new File(getCheminVictoire());
            AudioInputStream partieGagne= AudioSystem.getAudioInputStream(victoire);
            setGagne(AudioSystem.getClip());
            getGagne().open(partieGagne);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // preparation du premier lancement de balle
    public void chargementLancement(){
        try{
            File lancement=new File(getCheminLancement());
            AudioInputStream sonLancement= AudioSystem.getAudioInputStream(lancement);
            setLancementBalle(AudioSystem.getClip());
            getLancementBalle().open(sonLancement);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // méthode pour lire la musique en boucle
    public void boucleMusicale(){
        getMusiqueJeu().setFramePosition(0);//on part du début du morceau
        getMusiqueJeu().loop(10000000);// repetition +++
    }

    //on arrête la musique
    public void stopMusique(){
        getMusiqueJeu().stop();
    }

    //lire les différents sons
    public void chocParoie(){
        getCollisionParoie().setFramePosition(0);//on remet le son au début à chaque fois, si non la lecture démarre à la fin
        getCollisionParoie().start();
    }

    public void bainLave(){
        getLave().setFramePosition(0);
        getLave().start();
    }

    public void chocBarre(){
        getCollisionBarre().setFramePosition(0);
        getCollisionBarre().start();
    }

    public void chocBrique(){
        getCollisionBrique().setFramePosition(0);
        getCollisionBrique().start();
    }

    public void destructionBrique(){
        getDestructionBrique().setFramePosition(0);
        getDestructionBrique().start();
    }

    public void gameOver(){
        getPerdu().setFramePosition(0);
        getPerdu().start();
    }

    public void gagne(){
        getGagne().setFramePosition(0);
        getGagne().start();
    }

    public void lancement(){
        getLancementBalle().setFramePosition(0);
        getLancementBalle().start();
    }


    // accesseurs
    public int getNbrVies() {
        return nbrVies;
    }

    public int getBriquesDétruites() {
        return briquesDétruites;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isLancement() {
        return lancement;
    }

    public boolean isTryAgain() {
        return tryAgain;
    }

    public boolean isVictoire() {
        return victoire;
    }

    public String getCheminmusique() {
        return cheminmusique;
    }

    public String getCheminParoie() {
        return cheminParoie;
    }

    public String getCheminLave() {
        return cheminLave;
    }

    public String getCheminBarre() {
        return cheminBarre;
    }

    public String getCheminBrique() {
        return cheminBrique;
    }

    public String getCheminExplosion() {
        return cheminExplosion;
    }

    public String getCheminGameOver() {
        return cheminGameOver;
    }

    public String getCheminVictoire() {
        return cheminVictoire;
    }

    public String getCheminLancement() {
        return cheminLancement;
    }

    public Clip getMusiqueJeu() {
        return musiqueJeu;
    }

    public Clip getLancementBalle() {
        return lancementBalle;
    }

    public Clip getCollisionParoie() {
        return collisionParoie;
    }

    public Clip getCollisionBarre() {
        return collisionBarre;
    }

    public Clip getCollisionBrique() {
        return collisionBrique;
    }

    public Clip getDestructionBrique() {
        return destructionBrique;
    }

    public Clip getLave() {
        return lave;
    }

    public Clip getPerdu() {
        return perdu;
    }

    public Clip getGagne() {
        return gagne;
    }

    public void setNbrVies(int nbrVies) {
        this.nbrVies = nbrVies;
    }

    public void setBriquesDétruites(int briquesDétruites) {
        this.briquesDétruites = briquesDétruites;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setLancement(boolean lancement) {
        this.lancement = lancement;
    }

    public void setTryAgain(boolean tryAgain) {
        this.tryAgain = tryAgain;
    }

    public void setVictoire(boolean victoire) {
        this.victoire = victoire;
    }

    public void setMusiqueJeu(Clip musiqueJeu) {
        this.musiqueJeu = musiqueJeu;
    }

    public void setLancementBalle(Clip lancementBalle) {
        this.lancementBalle = lancementBalle;
    }

    public void setCollisionParoie(Clip collisionParoie) {
        this.collisionParoie = collisionParoie;
    }

    public void setCollisionBarre(Clip collisionBarre) {
        this.collisionBarre = collisionBarre;
    }

    public void setCollisionBrique(Clip collisionBrique) {
        this.collisionBrique = collisionBrique;
    }

    public void setDestructionBrique(Clip destructionBrique) {
        this.destructionBrique = destructionBrique;
    }

    public void setLave(Clip lave) {
        this.lave = lave;
    }

    public void setPerdu(Clip perdu) {
        this.perdu = perdu;
    }

    public void setGagne(Clip gagne) {
        this.gagne = gagne;
    }

    public void setCheminmusique(String cheminmusique) {
        this.cheminmusique = cheminmusique;
    }

    public void setCheminParoie(String cheminParoie) {
        this.cheminParoie = cheminParoie;
    }

    public void setCheminLave(String cheminLave) {
        this.cheminLave = cheminLave;
    }

    public void setCheminBarre(String cheminBarre) {
        this.cheminBarre = cheminBarre;
    }

    public void setCheminBrique(String cheminBrique) {
        this.cheminBrique = cheminBrique;
    }

    public void setCheminExplosion(String cheminExplosion) {
        this.cheminExplosion = cheminExplosion;
    }

    public void setCheminGameOver(String cheminGameOver) {
        this.cheminGameOver = cheminGameOver;
    }

    public void setCheminVictoire(String cheminVictoire) {
        this.cheminVictoire = cheminVictoire;
    }

    public void setCheminLancement(String cheminLancement) {
        this.cheminLancement = cheminLancement;
    }
}
