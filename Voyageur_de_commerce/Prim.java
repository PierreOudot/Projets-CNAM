package com.company;

import java.awt.geom.Point2D;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Prim{

    // on cré un arraylistqui accueillera l'arbre couvrant de poids minimum (ACPM), pour conserver l'odre de parcours des
    // points de passage
    // resultant de l'algorithm de Prim. Il contiendra lespoints
    // du graphe dont on aura suprrimé les arretes. de cette manière, on altère pas le graphe initial, et on peut générer
    //plusieurs ACPM, en changeant le point de départ, en générant plusieurs instances de la classe Prim.

    private ArrayList<PointDePassage> acpm=new ArrayList<>();
    //le hashmap suivant permettra de copier le hashmap initialement créé dans la classe Graphe, pour ne pas l'altérer
    // En utilisant le hashset prim comme base pour créer l'arbre couvrant de poids minimal, on pourra modifier les attributs
    // de chaque point. On évite d'avoir à créer de nouvelles copies distinctes des objets du graphe initial en permanence
    // en fonction de nos besoins
    private HashMap<String,PointDePassage>prim=new HashMap<>();

    //on utilisera un arrayliste pour stocker toutes les routes qui peuvent être parcourue depuis les points se trouvant
    // dans l'arbre de poids minimal pour trouver celle dont la distance est la plus faible à chaque itération permettant
    // de créer l'arbre de poids minimal
    private ArrayList<Route> routesPossibles=new ArrayList<>();

    //
    public Prim(Graphe graphe) {

    }

    //algorithme de Prim, la méthode prend en paramètre le hashmap qui stockera l'ACMP, si jamais on veut comparer les
    // les arbres obtenus avec différents points de départ
    public void algoPrim(Graphe graphe){
        // on copie le hashhmap initial de Graphe, qui servira de base à l'algorithm.
        creationPrim(graphe);

        // on va choisir un point au hazard dans le hashmap, grace à un tableau de string(les nom des points)
        String[] nomPoints =new String[getPrim().size()];
        // on utilise la variable suivante pour gérer les indexes du tableau dans la boucle
        int index=0;
        // et la méthode iterator pour le remplir
        Iterator<PointDePassage> iterateur = prim.values().iterator();
        while(iterateur.hasNext()){
            String nom=iterateur.next().getNom();
            nomPoints[index]=nom;
            index++;
        }

        // on attribue une valeur aléatoire à index pour sortir la String correspondante du tableau
        int max=nomPoints.length;
        index=(int)(Math.random()*max);//on obtient bien une valeur comprise entre 0->nomPoints.length-1

        // on recupère le nom du point de départ de l'algorithme dans le tableau
        String pointDeDepart=nomPoints[index];

        // on attribue le point de passage correspondant dans prim comme point de départ à l'algorithme
        PointDePassage pointParcouru=prim.get(pointDeDepart);

        // on cré l'arbre couvrant de poids minimal à partir du point de départ
        creationACPM(pointParcouru);


        // on affiche l'arbre couvrant de poids minimal
        afficheAcpm(acpm);
        // on supprime les routes non parcourues pour obtenir l'abre de poids minimal definitif
        suppressionRoutes(getAcpm());
        // on redefinit les voisins de chaque point de l'arbre obtenu et leur degré, en vu du couplage parfait
        for(PointDePassage pointDePassage:getAcpm()){
            // on nettoie les collections sommetsVoisins et voisins pour chaque point
            pointDePassage.getSommetsVoisins().clear();
            pointDePassage.getVoisins().clear();
            // on met les collections à jour
            pointDePassage.voisinsEtDegre();
        }
    }

    // on clone les points de passage du hashmap initial et on les ajoute dans le hashmap sur le lequel sera appliqué
    // l'algoritme de Prim
    public void creationPrim(Graphe graphe){
        // on cré d'abord les copie des points de passage du hashmap initial
        Iterator<PointDePassage> iterateur = graphe.getGrapheInital().values().iterator();
        while (iterateur.hasNext()) {
            String clone=iterateur.next().getNom();
            prim.put(clone,new PointDePassage(clone));
        }
        // on duplique ensuite les routes et on les ajoutes dans liste route du hashmap prim
        graphe.getGrapheInital().forEach((String,PointDePassage)->copieRoute(PointDePassage));
        // on remet à zero (false) parcouru pour chaque route, on retrouve le graphe dans son état initial, il pourra être
        // réutilisé par les autres algorithmes si besoin
        graphe.getGrapheInital().forEach((String,PointDePassage)->razParcouruRoute(PointDePassage));
        // on calcul le degré de chaque point et on rempli ses collections de voisisns
        prim.forEach((String,PointDePassage)->PointDePassage.voisinsEtDegre());
    }

    //La methode recursive qui permettra de parcourir les points de passage et de créer l'ACPM (récursive)
    // au fur et à mesure qu'un point de passage est ajouté dans l'arbre de poids minimal, on le retire du hashmap Prim
    public void creationACPM(PointDePassage pointParcouru){
        // on ajoute le point (soit le point de départ, soit le point de destination de la dernière route parcourue)
        // dans l'acpm
        getAcpm().add(pointParcouru);
        // on le marque comme parcouru
        pointParcouru.setParcouru(true);
        // on extrait son nom pour le retirer de Prim
        String nomPointParcouru=pointParcouru.getNom();
        getPrim().remove(nomPointParcouru);
        // on nettoie routesPossibles pour s'adapter à son évolution à chaque itération.
        getRoutesPossibles().clear();
        // on répète l'opération jusqu'a ce que tous les points soient ajouté dans l'ACPM et que Prim soit vide
       while(getPrim().size()>0){
           // on récupère les routes non parcourues des points se trouvant dans l'acpm pour trouver la plus failbe distance
           for(PointDePassage pointAcpm:getAcpm()){
               for(int i=0;i<pointAcpm.getListeRoute().size();i++){
                   if(!pointAcpm.getListeRoute().get(i).isParcouru()){
                       getRoutesPossibles().add(pointAcpm.getListeRoute().get(i));
                   }
               }
           }
           // on trie les routes dans routesPossibles de manière croissante. Celle dont la distance est la plus faible se trouvera
           // à l'index 0. Si les routes les plus courtes sont d'égales distance, on choisi la première de l'arraylist. Si les autres
           // mènent à des sommets (différents) qui ne sont pas encore dans l'ACPM, elles seront ajoutées plus tard,
           // tant qu'elles sont encore les routes les plus courtes.
           Collections.sort(getRoutesPossibles(), new ComparateurRoute());

           // on utilise un booléen pour controler la boucle de parcours des routes, au cas où la route la plus courte
           // relie deux points déjà dans l'arbre
           boolean croissanceArbre=false;
           // on utilise une variable pour parcourir les routes de routesPossibles par leur index, depuis la plus courte (0)
           int index=0;
           // on cherche la route la plus courtes partant d'un point de l'ACPM vers un point qui n'y est pas encore
           while(!croissanceArbre){
               // on récupère les  points aux extrémité de la route en cours d'étude pour vérifier si ils sont dans
               // l'acpm
               PointDePassage extremiteA=getRoutesPossibles().get(index).getPointA();
               PointDePassage extremiteB=getRoutesPossibles().get(index).getPointB();
               //on cherche le point qui n'est pas dans l'acpm et on l'attribue à pointParcouru pour la prochaine itération
               if(getAcpm().contains(extremiteA) && !getAcpm().contains(extremiteB)){
                   //on récupère le nom du point ainsi identifié pour pouvoir y accéder dans prim via sa clé
                   String nomB= extremiteB.getNom();
                   pointParcouru= getPrim().get(nomB);
                   //on marque la route comme parcourue
                   getRoutesPossibles().get(index).setParcouru(true);
                   // on sort de la boucle while
                   croissanceArbre=true;
               }else if(!getAcpm().contains(extremiteA) && getAcpm().contains(extremiteB)){
                   String nomA= extremiteA.getNom();
                   pointParcouru= getPrim().get(nomA);
                   getRoutesPossibles().get(index).setParcouru(true);
                   croissanceArbre=true;
               }else{
                   // aucun des cas précédents n'est valable, c'est que la route relie deux points déjà dans l'abre.
                   // on incrémente l'index jusqu'a trouver la prochaine route la plus courte qui conduit à un point extérieur
                   // de l'arbre
                   index++;
               }
           }
           // on rappelle la récursive pour réitérer l'opération
           creationACPM(pointParcouru);
       }
        // on supprime les routes non parcourues pour obtenir l'abre de poids minimal definitif
        suppressionRoutes(getAcpm());
        // on redefinit les voisins de chaque point de l'arbre obtenu et leur degré, en vu du couplage parfait
        for(PointDePassage pointDePassage:getAcpm()){
            // on nettoie les collections sommetsVoisins et voisins pour chaque point
            pointDePassage.getSommetsVoisins().clear();
            pointDePassage.getVoisins().clear();
            // on met les collections à jour
            pointDePassage.voisinsEtDegre();
        }
    }

    // parcourir un graphe de la classe Graphe et cloner les routes de ses points
    public void copieRoute(PointDePassage pointDePassage){
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

    // permet de remettre Parcouru à false pour chaque route dans le graphe initial
    public void razParcouruRoute(PointDePassage pointDePassage){
        for (int i=0;i<pointDePassage.getListeRoute().size();i++){
            pointDePassage.getListeRoute().get(i).setParcouru(false);
        }
    }

    // pour ajouter les routes, la methode de l'interface me renvoit une NPE à chaque fois, je verrai plus tard
    public void ajoutRoute(String pointA, String pointB, int distance) {
        Route route = new Route(prim.get(pointA), prim.get(pointB), distance);
        prim.get(pointA).getListeRoute().add(route);
        prim.get(pointB).getListeRoute().add(route);
    }

    // suppression des routes non parcourue de l'acpm
    public void suppressionRoutes(ArrayList<PointDePassage>acpm){
        // on va retirer la route non parcourue de listeroute de chacun des sommets qu'elle relie
        for(int i=0;i<acpm.size();i++){
            for(int j =0;j<acpm.get(i).getListeRoute().size();j++){
                if(!acpm.get(i).getListeRoute().get(j).isParcouru()){
                    acpm.get(i).getListeRoute().remove(acpm.get(i).getListeRoute().get(j));
                }
            }
        }
    }

    // affiche l'acpm
    public void afficheAcpm(ArrayList<PointDePassage>acpm){
        for (int i =0;i< acpm.size();i++){
            for(Route route:acpm.get(i).getListeRoute()){
    // si la route n'a pas déjà été affichée, on affiche le nom du point de départ , la distance de la route, et le nom du
                // point d'arrivée
                if(!route.isEstAffichee()&& route.isParcouru()){
                    if(route.getPointA().getNom()==acpm.get(i).getNom()){
                        System.out.println(acpm.get(i).getNom()+"--"+route.getDistance()+"->"+route.getPointB().getNom());
                    }else{
                        System.out.println(acpm.get(i).getNom()+"--"+route.getDistance()+"->"+route.getPointA().getNom());
                    }
                    route.setEstAffichee(true);
                }
            }
        }
    }


    // les accesseurs
    public ArrayList<PointDePassage> getAcpm() {
        return acpm;
    }

    public void setAcpm(ArrayList<PointDePassage> acpm) {
        this.acpm = acpm;
    }

    public HashMap<String, PointDePassage> getPrim() {
        return prim;
    }

    public void setPrim(HashMap<String, PointDePassage> prim) {
        this.prim = prim;
    }

    public ArrayList<Route> getRoutesPossibles() {
        return routesPossibles;
    }

    public void setRoutesPossibles(ArrayList<Route> routesPossibles) {
        this.routesPossibles = routesPossibles;
    }
}
