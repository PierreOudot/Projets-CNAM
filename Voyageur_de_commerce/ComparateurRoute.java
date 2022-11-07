package com.company;

import java.util.Comparator;

public class ComparateurRoute implements Comparator<Route> {
    //on va utiliser cette methode de la classe collection pour trier les routes dans chaque listeRoute dans un ordre croissant
    //ça nous permttra de selectionner la route à la distance minimale pour chaque listeRoute.
    //on implémente et redéfinit la méthode caompare
    @Override
    public int compare(Route o1, Route o2) {
        if(o1.getDistance()== o2.getDistance()){
            return 0;
        }else if(o1.getDistance()>o2.getDistance()){
            return 1;
        }else{
            return -1;
        }
    }
}
