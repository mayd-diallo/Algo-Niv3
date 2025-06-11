/*M’afficher une liste de ville , Paris, Londres, Madrid, Berlin, Lisbonne, me les afficher sous forme de liste.

Créer une méthode permettant de chercher une ville dans la liste, si il ne trouve pas la ville entrer en Scanner, afficher “Cette ville ne figure pas sur la liste”.

Créer une méthode qui permet d’ajouter une ville dans votre liste.



Pseudo code et code Java demandé

1. Afficher une liste de villes
   - Paris
   - Londres
   - Madrid
   - Berlin
   - Lisbonne

2. Méthode de recherche de ville
   - Prendre une entrée utilisateur
   - Vérifier si la ville existe dans la liste
   - Si oui, afficher "Ville trouvée"
   - Si non, afficher "Cette ville ne figure pas sur la liste"

3. Méthode d'ajout de ville
   - Prendre une entrée utilisateur pour la nouvelle ville
   - Ajouter la ville à la liste existante
   - Afficher la liste mise à jour

VARIABLES :
   - listeVilles : Liste<String>
   - scanner : Scanner
   - inputUtilisateur : String
 */

import java.util.ArrayList;
import java.util.Scanner;

public class GestionVilles {
    private static ArrayList<String> villes = new ArrayList<>();

    public static void main(String[] args) {
        // Initialisation de la liste des villes
        villes.add("Paris");
        villes.add("Londres");
        villes.add("Madrid");
        villes.add("Berlin");
        villes.add("Lisbonne");

        Scanner scanner = new Scanner(System.in);

        // Affichage du menu
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Afficher la liste des villes");
            System.out.println("2. Rechercher une ville");
            System.out.println("3. Ajouter une ville");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option: ");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Pour consommer le retour à la ligne

            switch (choix) {
                case 1:
                    afficherVilles();
                    break;
                case 2:
                    rechercherVille(scanner);
                    break;
                case 3:
                    ajouterVille(scanner);
                    break;
                case 4:
                    System.out.println("Au revoir!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Option invalide!");
            }
        }
    }

    // Méthode pour afficher la liste des villes
    public static void afficherVilles() {
        System.out.println("\nListe des villes:");
        for (String ville : villes) {
            System.out.println("- " + ville);
        }
    }

    // Méthode pour rechercher une ville
    public static void rechercherVille(Scanner scanner) {
        System.out.print("\nEntrez le nom d'une ville à rechercher: ");
        String villeRecherchee = scanner.nextLine();

        if (villes.contains(villeRecherchee)) {
            System.out.println("La ville '" + villeRecherchee + "' figure dans la liste.");
        } else {
            System.out.println("Cette ville ne figure pas sur la liste.");
        }
    }

    // Méthode pour ajouter une ville
    public static void ajouterVille(Scanner scanner) {
        System.out.print("\nEntrez le nom de la ville à ajouter: ");
        String nouvelleVille = scanner.nextLine();

        if (!villes.contains(nouvelleVille)) {
            villes.add(nouvelleVille);
            System.out.println("La ville '" + nouvelleVille + "' a été ajoutée à la liste.");
            afficherVilles();
        } else {
            System.out.println("Cette ville est déjà dans la liste.");
        }
    }
}