package com.company;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Graphe implements ModificationGraphe {

    // le graphe sera représenté par un hashmap (facilité d'accès à un point de passage par son nom)
    private HashMap<String,PointDePassage> grapheInital =new HashMap<>();


    // constructeur, il appelle ma méthode d'ajout des éléments. Sera commentée lors de la restitution pour utiliser ton
    // jeu d'essais
    public Graphe() {
        creationGraphe();
    }




    // méthode pour ajouter tous les points de passage et les routes via la console ou la lecture de fichier
    public void creationGraphe(){

        // on cré une nouvelle instance de Scanner pour récupérer les commandes utilisateur
        Scanner scanner = new Scanner(System.in);
        // l'utilisateur doit d'abord remplir le Hashmap avec tous les points de passage
        System.out.println("Ajouter les points de passage: oui[o]? non[n]?");
        String choix = scanner.next();

        // on cré et ajoute, ou pas, les points de passage si la réponse est "o" (oui):
        if (choix.equals("o")) {
            // on cré une boucle pour ajouter tous les points. On initialise un booléen pour contrôler la boucle
            boolean boucle = true;
            // on initialise un booléen pour gerer l'ajout manuel des points de passage
            boolean nouveauPoint = true;


            // On demande la méthode d'ajout des points de passge. On utilise un switch pour exécuter le code
            // correspondant à la méthode choisie
            System.out.println("Ajouter les points de passage: manuellement[m]?  avec fichier[f]? ");
            choix = scanner.next();
            switch (choix) {
                case "m":
                    while (nouveauPoint) {
                        // on récupère le nom du point de passager à créer
                        System.out.println("Entrer le nom du point de passage:");
                        String nom = scanner.nextLine();

                        // on instancie le point de passage avec le nom récupéré on ajoute le point de passage dans le Hashmap
                        // avec la clé correspondante (le nom)
                        grapheInital.put(nom, new PointDePassage(nom));

                        // on propose la creation d'un autre point
                        System.out.println("Ajouter un autre point de passage: oui[o]? non[n]?");
                        String autrePoint = scanner.next();//on recupère le choix utilisateur

                        // Si la réponse est non, on sort de la boucle while
                        if (autrePoint.equals("n")) {
                            nouveauPoint = false;
                        }
                    }
                    // on sort du switch, pour éviter l'exécution du cas suivant
                    break;
                case "f":
                    // la contrainte ici est que chaque fichiers utilisé (vrai pour les routes) doit avoir la même structure
                    // on défini le chemin du fichier à lire. il est prédéfini pour l'exercice, mais je prévois tout de
                    // même de quoi le personnaliser par l'utilisateur:
                    //System.out.println("renseigner le chemin du fichier contenant les points de passage:");
                    //String cheminPoint=scanner.next();)
                    String cheminPoint = "com/company/fichiers/FranckPointsdePassage";
                    try {
                        FileInputStream fis = new FileInputStream(cheminPoint); //permets d'extraire les bytes de n'importe
                        // quel type de fichier (texte, image...)
                        InputStreamReader isr = new InputStreamReader(fis);// permet de lire n'importe quelle type de source, fichiers
                        // archives...et en l'occurence le fileInputstream précédement créé
                        BufferedReader br = new BufferedReader(isr);// cré un buffer de plus de 8000 caractères qui seront
                        // lu un à un. Permet une lecture rapide

                        // on initialise la variable String qui va accueillir chaque ligne du fichier lu
                        String ligne;

                        // on va lire chaque ligne du fichier,l'attribuer à "ligne", jusqu'à atteindre un ligne vide (nulle)
                        // Pour le fichier Points, 1 ligne=1 nom.
                        // à chaque itération, ligne=nom du point;
                        // on ajoute chaque point dans e hashmap
                        while ((ligne = br.readLine()) != null) {
                            grapheInital.put(ligne, new PointDePassage(ligne));
                        }

                    } catch (IOException e) {
                        // on gère toutes les exceptions, pas seulement filenotfound
                        e.printStackTrace();// on affiche le suivi de l'erreur
                    }
                    break;
                default:
                    System.out.println("Aucun point n'a été ajouté");
                    break;
            }
        }

        // on choisi d'ajouter, ou pas (pour y revenir plus tard, mais il faudrait stocker ce qui à été fait dans
        // des fichiers) les routes pour tous les points de passage
        System.out.println("Ajouter les routes reliant les points de passage: oui[o]? non[n]?");
        choix=scanner.next();
        if(choix.equals("o")){
            System.out.println("Ajouter les routes: manuellement[m]?  avec fichier[f]? ");
            choix = scanner.next();
            // on initialise un booléen pour gerer l'ajout manuel des routes
            boolean nouvelleRoute = true;
            switch(choix) {
                case "m":
                    while (nouvelleRoute) {
                        //  on parcours le hashmap jusqu'au dernier élément et on affiche le nom des points dans le hashmap
                        // on va créer un itérateur pour permettre de parcourir la collection. Cette methe retourne au final un tableau particulier.
                        // parceque la référence de ce tableau ne sera pas conservé par la collection, on va devoir appeller
                        // cette methode à chaque fois que ce sera nécessaire. NB l'ordre des objet du Hashmap source est random dans l'itérateur

                        Iterator<PointDePassage> iterateur = grapheInital.values().iterator();
                        while (iterateur.hasNext()) {
                            System.out.println(iterateur.next().getNom());
                        }

                        // on récupère les points A et B de la route ainsi que la distance
                        System.out.println("Entrer le nom du point A:");
                        String pointA = scanner.next();
                        System.out.println("Entrer le nom du point B:");
                        String pointB = scanner.next();
                        System.out.println("Entrer la longueur de la route:");
                        int distance = scanner.nextInt();

                        // on instancie la route avec les données récupérées, en allant chercher les point de passage
                        // correspondant avec la bonne clé (la saisie utilisateur)
                        Route route = new Route(grapheInital.get(pointA), grapheInital.get(pointB), distance);

                        // on ajoute la route dans l'arraylist de route de chaque point de passage
                        grapheInital.get(pointA).getListeRoute().add(route);
                        grapheInital.get(pointB).getListeRoute().add(route);

                        // on propose la création d'une autre route
                        System.out.println("Ajouter une autre route: oui[o]? non[n]?");
                        String autreRoute = scanner.next();//on recupère le choix utilisateur

                        // Si la réponse est non, on sort de la boucle while
                        if (autreRoute.equals("n")) {
                            nouvelleRoute = false;
                        }
                    }
                    // on sort du switch, pour éviter l'exécution du cas suivant
                    break;
                case "f":
                    //System.out.println("renseigner le chemin du fichier contenant les routes:");
                    //String cheminRoute=scanner.next();
                    // le jeu de routes proposé permet d'obtenir un graphe connexe, distances exprimées en millions de km.
                    // source:http://www.ledauphinedesenfants.ledauphine.com/rde-data-bundle/2016/pdf/20160831-12-05-26-les_distance_au_sein_du_systeme_solaire.pdf
                    String cheminRoute = "com/company/fichiers/FranckRoutes";
                    try {
                        FileInputStream fis = new FileInputStream(cheminRoute);
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(isr);

                        String ligne;

                        // Pour le fichier Routes, chaque ligne contient toutes les données nécessaires pour construire une route.
                        // Chaque ligne est agencée de la même manière: pointA pointB distance,
                        // On utilisera la methode split pour séparer les infos de chaque ligne,ParseInt pour cast la distance (initialement String avec
                        // la méthode de lecture) en int.
                        // Parcequ'elles sont séparées par des espaces, on utilisera une regex pour couper au niveau de ces derniers.
                        // les morceaux ainsi obtenu seront stockés dans un tableau pour réutiliser ces valeurs dans le constructeur.
                        // index 0=pointA, index1=pointB et index2=distance

                        while ((ligne = br.readLine()) != null) {
                            String[] donnees = ligne.split(" ");
                            Route route = new Route(grapheInital.get(donnees[0]), grapheInital.get(donnees[1]), Integer.parseInt(donnees[2]));

                            // on ajoute la route dans l'arraylist de route de chaque point de passage
                            grapheInital.get(donnees[0]).getListeRoute().add(route);
                            grapheInital.get(donnees[1]).getListeRoute().add(route);

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Aucune route n'a été ajoutée");
                    break;

            }
        }

        // on va définir les voisins et le degré de chaque point de passage avec une boucle forEach, plus lisible et efficace qu'avec l'iterateur
        // (on ne cré par de tableau)
        // dans un try-catch au cas où...
        try{
            grapheInital.forEach((String,PointDePassage)->PointDePassage.voisinsEtDegre());

        }catch(Exception e){
            System.out.println("un problème est survenu");
            e.printStackTrace();
        }
    }

    // methode pour remettre la valeur de parcouru à false pour chaque point de passage
    public void razParcouruRoute(PointDePassage pointDePassage){
        for (int i=0;i<pointDePassage.getListeRoute().size();i++){
            pointDePassage.getListeRoute().get(i).setParcouru(false);
        }
    }

    // on surcharge les methodes de l'interface ModificationGraphe


    @Override
    public void copieRoute(PointDePassage pointDePassage) {
        ModificationGraphe.super.copieRoute(pointDePassage);
    }

    @Override
    public void ajoutPoint(String nom) {
        grapheInital.put(nom, new PointDePassage(nom));
    }

    // ici on part du principe que l'ajout de route ne concerne que des sommets existant dans le hashmap
    @Override
    public void ajoutRoute(String nomA, String nomB, int distance) {
        Route route = new Route(grapheInital.get(nomA), grapheInital.get(nomB), distance);
        grapheInital.get(nomA).getListeRoute().add(route);
        grapheInital.get(nomB).getListeRoute().add(route);

    }

    // on affiche les points du Hashmap
    public void affichePoints(){
        grapheInital.forEach((String, PointDePassage)-> System.out.println(PointDePassage.getNom()));
    }

    // ta méthode d'ajout des éléments dans le graphe, adaptée à mon code
    void ajoutChemin(String pointA,String pointB,int distance){
        if(!grapheInital.containsKey(pointA)) {
            grapheInital.put(pointA, new PointDePassage(pointA));
        }

        if(!grapheInital.containsKey(pointB)) {
            grapheInital.put(pointB, new PointDePassage(pointB));
        }

        grapheInital
                .get(pointA)
                .getListeRoute().add(
                new Route(
                        grapheInital.get(pointA),
                        grapheInital.get(pointB),
                        distance
                ));

        grapheInital
                .get(pointB)
                .getListeRoute().add(
                new Route(
                        grapheInital.get(pointA),
                        grapheInital.get(pointB),
                        distance
                ));
    }

    // les accesseurs
    public HashMap<String, PointDePassage> getGrapheInital() {
        return grapheInital;
    }

    public void setGrapheInital(HashMap<String, PointDePassage> grapheInital) {
        this.grapheInital = grapheInital;
    }


}
