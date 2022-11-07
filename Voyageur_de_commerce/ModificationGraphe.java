package com.company;

public interface ModificationGraphe {

    //on utilisera ces methodes pour modifier le graphe au besoin  et plus facilement,
    // elles sont en default pour en definir les paramètres. Le corps des méthodes sera définit en les surchargeant
    //pour ajouter un point
    default void ajoutPoint(String nom){
    }
    //pour ajouter une route
    default void ajoutRoute(String nomA, String nomB, int distance){

    }

    default void copieRoute(PointDePassage pointDePassage){
        for (int i=0;i<pointDePassage.getListeRoute().size();i++){
            if(!pointDePassage.getListeRoute().get(i).isParcouru()){
                String nomA=pointDePassage.getListeRoute().get(i).getPointA().getNom();
                String nomB=pointDePassage.getListeRoute().get(i).getPointB().getNom();
                int distance=pointDePassage.getListeRoute().get(i).getDistance();
                ajoutRoute(nomA,nomB,distance);
                pointDePassage.getListeRoute().get(i).setParcouru(true);//de cette manière on évite d'ajouter les routes
                //en doublon au fur et à mesure qu'on parcours les points de passage
            }
        }
    }

    default void razParcouruRoute(PointDePassage pointDePassage){
        for (int i=0;i<pointDePassage.getListeRoute().size();i++){
            pointDePassage.getListeRoute().get(i).setParcouru(false);
        }
    }
}
