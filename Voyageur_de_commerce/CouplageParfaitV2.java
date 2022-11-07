package com.company;
import java.util.*;

public class CouplageParfaitV2 implements ModificationGraphe {
    // le couplage parfait s'effectue sur les sommets impairs de l'arbre de poids minimal obtenu avec l'algorithme de Prim
    // on va donc parcourir l'ACPM pour en extraire les sommets de degré impairs, que l'on copiera dans un nouveau HashMap
    // pour accéder aux points par leur clé
    HashMap<String, PointDePassage> sommetsImpairs = new HashMap<>();

    // on stock seulement les arrêtes (routes) retenues lors du couplage parfait dans un arraylist. Etant donné que les nom du point A et
    // du point B des routes ainsi sélectionnées seront identiques aux noms de points se trouvant dans l'ACPM, les routes obtenues
    // par couplage parfait pourront être ajouté dans L'ACPM sans problème.
    ArrayList<Route> deviations = new ArrayList<>();


    // le constructeur prend en paramètre un objet Prim pour pouvoir accéder à son arbre  couvrant de poids minimal
    // et un objet Graphe pour accéder au graphe initial
    public CouplageParfaitV2(Prim prim, Graphe graphe) {
        creationSommetsImpairs(prim.getAcpm());
        prepaCouplage(graphe);
        couplage();

    }

    // on rélaise le couplage parfait en partant d'un sommet aléatoire de sommetsImpairs
    public void couplage() {
        // On part du principe qu'on ne peut effectuer le couplage parfait que sur un nombre pair de sommets
        if (getSommetsImpairs().size() % 2 == 0) {
            //on appelle la méthode couplage tant que sommetsImpais n'est pas vide
            while(getSommetsImpairs().size()>0){
                //on va choisir un point au hasard, on reprend la même méthode que pour l'algorithme de prim
                String[] nomPoints = new String[getSommetsImpairs().size()];
                System.out.println("------------------");
                System.out.println(getSommetsImpairs().size());
                System.out.println("------------------");
                // on utilise la variable suivante pour gérer les indexes du tableau dans la boucle
                int index = 0;
                // et la méthode iterator pour le remplir
                Iterator<PointDePassage> iterateur = getSommetsImpairs().values().iterator();
                while (iterateur.hasNext()) {
                    String nom = iterateur.next().getNom();
                    nomPoints[index] = nom;
                    index++;
                }
                // on attribu une valeur aléatoire à index pour sortir la String correspondante du tableau
                int max = nomPoints.length;
                index = (int) (Math.random() * max);//on obtient bien une valeur comprise entre 0->nomPoints.length-1

                // on recupère le nom du point de départ de l'algorithme dans le tableau
                String pointAléatoire = nomPoints[index];

                // on trie dans un ordre croissant listRoutes pour le point sélectionné, on choisiera la route dont la distance
                // est la plus faible
                Collections.sort(getSommetsImpairs().get(pointAléatoire).getListeRoute(), new ComparateurRoute());
                getSommetsImpairs().get(pointAléatoire).getListeRoute().forEach((Route) -> System.out.println(Route.getDistance()));

                // on trouve la route dont la distance est la plus faible et reliant le point sélectionné à un point non
                // non parcouru dans sommetsImpairs

                //on récupère le nom des sommets de la route pour identifier le point de destination
                String nomPointA=getSommetsImpairs().get(pointAléatoire).getListeRoute().get(0).getPointA().getNom();
                String nomPointB=getSommetsImpairs().get(pointAléatoire).getListeRoute().get(0).getPointB().getNom();

                //on marque ces points comme parcourus
                getSommetsImpairs().get(nomPointA).setParcouru(true);
                getSommetsImpairs().get(nomPointB).setParcouru(true);
                //on ajoute la route dans deviation
                getDeviations().add(getSommetsImpairs().get(pointAléatoire).getListeRoute().get(0));


                //on retire les routes de listeRoutes pour les points non parcouru, en utilisant un iterateur
                // qui nous permettra de modifier le hashmap en cours d'iteration
                Iterator<PointDePassage> iterateur2 = getSommetsImpairs().values().iterator();
                while (iterateur2.hasNext()) {
                    String nom = iterateur2.next().getNom();
                    if(!getSommetsImpairs().get(nom).isParcouru()){
                        // on utilise une boucle pour parcourir ListeRoutes
                        for(int i=0;i<getSommetsImpairs().get(nom).getListeRoute().size();i++){
                            //on cherche les routes contenant les points correspondant à ceux de la route ajoutée
                            // dans deviation et on les supprime.
                            String pointA=getSommetsImpairs().get(nom).getListeRoute().get(i).getPointA().getNom();
                            String pointB=getSommetsImpairs().get(nom).getListeRoute().get(i).getPointB().getNom();
                            if(pointA.equals(nomPointA)||pointB.equals(nomPointB)){
                                getSommetsImpairs().get(nom).getListeRoute().remove(getSommetsImpairs().get(nom)
                                        .getListeRoute().get(i));
                            }else if(pointA.equals(nomPointB)||pointB.equals(nomPointB)){
                                getSommetsImpairs().get(nom).getListeRoute().remove(getSommetsImpairs().get(nom)
                                        .getListeRoute().get(i));
                            }
                        }
                    }
                }

                // on supprime les points identifiés de sommetsImpairs à l'aide de l'itérateur, pour les mêmes raisons que
                // précédement

                Iterator<PointDePassage> iterateur3 = getSommetsImpairs().values().iterator();
                while (iterateur3.hasNext()) {
                    String nom=iterateur3.next().getNom();
                    if(nom.equals(nomPointA)||nom.equals(nomPointB)){
                        iterateur3.remove();
                    }
                }
                //on rappelle la méthode couplage jusqu'à ce que sommetsImpairs soit vide
                couplage();
            }
            // les attributs des routes dans déviation
            for (int i=0;i<getDeviations().size();i++){
                System.out.println("le point A de la route est");
                System.out.println(getDeviations().get(i).getPointA().getNom());
                System.out.println("le point B de la route est");
                System.out.println(getDeviations().get(i).getPointB().getNom());
                System.out.println("la distance de la route est");
                System.out.println(getDeviations().get(i).getDistance());
            }


        } else {
            System.out.println("le nombre de sommets retenus ne permet pas un couplage parfait");
        }


    }

    // on parcourt l'arbre couvrant de poids minimal et on copie les sommets immpairs
    public void creationSommetsImpairs(ArrayList<PointDePassage> acpm) {
        for (PointDePassage pointDePassage : acpm) {
            if (pointDePassage.getDegre() % 2 != 0) {
                String nom = pointDePassage.getNom();
                getSommetsImpairs().put(nom, new PointDePassage(nom));
            }
        }
    }

    //on copie les routes qui peuvent relier les points de passage dans sommetsImpairs depuis le graphe initial
    public void prepaCouplage(Graphe graphe) {
        //on va utiliser un itérateur pour parcourir le hashmap correspondant au graphe initial
        Iterator<PointDePassage> iterateur = graphe.getGrapheInital().values().iterator();
        while (iterateur.hasNext()) {
            String nomPoint = iterateur.next().getNom();
            //on verifie si un point ayant la même clé existe dans sommetsImpairs
            if (getSommetsImpairs().containsKey(nomPoint)) {
                // si c'est le cas, on va copier les routes on rempli les ListRoutes de chaque point
                // dans sommetsImpairs
                copieRoute(graphe.getGrapheInital().get(nomPoint));
            }
        }

        //graphe.getGrapheInital().forEach((String,PointDePassage)->copieRoute(PointDePassage));
        // on réinitialise la valeur de parcouru à false pour toutes les routes du graphe initial
        graphe.getGrapheInital().forEach((String, PointDePassage) -> razParcouruRoute(PointDePassage));
        // on determine le voisinage et le degré des points dans sommetsImpairs
        getSommetsImpairs().forEach((String, PointDePassage) -> PointDePassage.voisinsEtDegre());
    }

    // on implémente les méthodes de l'interface

    @Override
    public void ajoutPoint(String nom) {

    }

    @Override
    public void ajoutRoute(String nomA, String nomB, int distance) {
        // on ajuste la méthode pour n'ajouter que des routes reliant des points existants dans SommetsImpairs
        if (getSommetsImpairs().containsKey(nomA) && getSommetsImpairs().containsKey(nomB)) {
            Route route = new Route(getSommetsImpairs().get(nomA), getSommetsImpairs().get(nomB), distance);
            getSommetsImpairs().get(nomA).getListeRoute().add(route);
            getSommetsImpairs().get(nomB).getListeRoute().add(route);
        }

    }

    @Override
    public void copieRoute(PointDePassage pointDePassage) {
        ModificationGraphe.super.copieRoute(pointDePassage);
    }

    @Override
    public void razParcouruRoute(PointDePassage pointDePassage) {
        ModificationGraphe.super.razParcouruRoute(pointDePassage);
    }

    public HashMap<String, PointDePassage> getSommetsImpairs() {
        return sommetsImpairs;
    }

    public void setSommetsImpairs(HashMap<String, PointDePassage> sommetsImpairs) {
        this.sommetsImpairs = sommetsImpairs;
    }

    public ArrayList<Route> getDeviations() {
        return deviations;
    }

    public void setDeviations(ArrayList<Route> deviations) {
        this.deviations = deviations;
    }
}
