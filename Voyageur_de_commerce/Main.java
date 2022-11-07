package com.company;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        Graphe grapheTest=new Graphe();

        // En utilisant ton jeu d'essais depuis Main, j'ai eu un comportement bizarre de ma méthode CopieRoute: elle
        // ajoutait les mêmes routes plusieurs fois. En affichant les différentes étapes en faisant tourner la méthode,
        // je me suis rendu compte que la valeur de parcouru retournait à false, pour chaque route du graphe copié, entre
        // chaque point de passage. J'ai vérifié que la methode jouant ce rôle n'était pas appellée là où elle ne le devrait pas.
        // On aurait dit que les routes, pourtant déjà parcourues, correspondaient à d'autres objets.
        // Je ne sais pas encore l'expliquer, mais je vais creuser ça. Toujours est-il que l'algorithme de Prim sortait le
        // bon arbre de poids minimal (j'ai vérifié avec mon jeu d'essais, que je te déconseille d'utiliser car il ne
        // permet pas le couplage parfait) et au final les sommets de ce dernier avaient le bon degré, malgré les multiples
        // copies des routes.
        // le problème d'ajout multiple est réglé en utilisant ma méthode.

        // C'est pour ça que j'ai choisi d'utiliser ma méthode de création du graphe initial dans le projet rendu.
        // Lorsque tu lanceras le programme
        // tu n'auras qu'à entrer successivement o,f,o,f dans la console. Désolé que cela te rallonge le temps de correction
        // (de 5 secondes tout au plus XD). Si jamais tu veux utiliser ta méthode et le jeu d'essais depuis main,
        // il te suffira de commenter l'appel de creationGraphe dans le constructeur de la class Graphe, et de décommenter le bloc suivant.

        // Merci pour ta compréhension ;)

        // NB: il se peut que tu ais à redéfinir le chemin des fichiers sources pour les points de passage et les routes
        // NB2: Je n'arrive pas à régler mon problème de doublons dans couplage parfait,alors qu'il est réglé
        // dans Prim (j'ai bien 9 routes pour Nancy après copie). L'algo Couplage Parfait donne des résultats sont aléatoires,
        // le constructeur est commenté en bas de main, libre à toi de l'utiliser. Le temps me manque donc j'arrête ici.

        /*grapheTest.ajoutChemin("Nancy","Metz",57);
        grapheTest.ajoutChemin("Nancy","Strasbourg",157);
        grapheTest.ajoutChemin("Nancy","Reims",208);
        grapheTest.ajoutChemin("Nancy","Mulhouse",189);
        grapheTest.ajoutChemin("Nancy","Troyes",247);
        grapheTest.ajoutChemin("Nancy","Colmar",147);
        grapheTest.ajoutChemin("Nancy","Sélestat",130);
        grapheTest.ajoutChemin("Nancy","Saint-Dié-des-Vosges",85);
        grapheTest.ajoutChemin("Nancy","Verdun",121);
        grapheTest.ajoutChemin("Metz","Strasbourg",163);
        grapheTest.ajoutChemin("Metz","Reims",190);
        grapheTest.ajoutChemin("Metz","Mulhouse",248);
        grapheTest.ajoutChemin("Metz","Troyes",252);
        grapheTest.ajoutChemin("Metz","Colmar",206);
        grapheTest.ajoutChemin("Metz","Sélestat",184);
        grapheTest.ajoutChemin("Metz","Saint-Dié-des-Vosges",144);
        grapheTest.ajoutChemin("Metz","Verdun",80);
        grapheTest.ajoutChemin("Strasbourg","Reims",347);
        grapheTest.ajoutChemin("Strasbourg","Mulhouse",117);
        grapheTest.ajoutChemin("Strasbourg","Troyes",409);
        grapheTest.ajoutChemin("Strasbourg","Colmar",75);
        grapheTest.ajoutChemin("Strasbourg","Sélestat",53);
        grapheTest.ajoutChemin("Strasbourg","Saint-Dié-des-Vosges",95);
        grapheTest.ajoutChemin("Strasbourg","Verdun",236);
        grapheTest.ajoutChemin("Reims","Mulhouse",433);
        grapheTest.ajoutChemin("Reims","Troyes",125);
        grapheTest.ajoutChemin("Reims","Colmar",391);
        grapheTest.ajoutChemin("Reims","Sélestat",368);
        grapheTest.ajoutChemin("Reims","Saint-Dié-des-Vosges",286);
        grapheTest.ajoutChemin("Reims","Verdun",121);
        grapheTest.ajoutChemin("Mulhouse","Troyes",391);
        grapheTest.ajoutChemin("Mulhouse","Colmar",42);
        grapheTest.ajoutChemin("Mulhouse","Sélestat",65);
        grapheTest.ajoutChemin("Mulhouse","Saint-Dié-des-Vosges",104);
        grapheTest.ajoutChemin("Mulhouse","Verdun",322);
        grapheTest.ajoutChemin("Troyes","Colmar",316);
        grapheTest.ajoutChemin("Troyes","Sélestat",320);
        grapheTest.ajoutChemin("Troyes","Saint-Dié-des-Vosges",275);
        grapheTest.ajoutChemin("Troyes","Verdun",183);
        grapheTest.ajoutChemin("Colmar","Sélestat",24);
        grapheTest.ajoutChemin("Colmar","Saint-Dié-des-Vosges",63);
        grapheTest.ajoutChemin("Colmar","Verdun",281);
        grapheTest.ajoutChemin("Sélestat","Saint-Dié-des-Vosges",45);
        grapheTest.ajoutChemin("Sélestat","Verdun",259);
        grapheTest.ajoutChemin("Saint-Dié-des-Vosges","Verdun",206);*/

        // on va créer les classes des algorithmes et appeller les bonnes methodes. Il n'y a rien à toucher ici si tu veux
        // utiliser mon ta méthode d'ajout des points/routes, ou la mienne.

        
        Prim primTest=new Prim(grapheTest);
        primTest.algoPrim(grapheTest);
        
        //CouplageParfaitV2 couplageTest=new CouplageParfaitV2(primTest,grapheTest);





    }


}
