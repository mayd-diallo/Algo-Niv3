package jalon.methods.DWWM;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class InventaireAlimentaire {
     // Tableau des types d'aliments
    public static final String[] TYPES_ALIMENTS = {
        "Viande", "Légume", "Féculant", "Fruit", "Laitage", 
        "Poisson", "Dessert", "Pâtisserie", "Boulangerie"
    };  // Liste pour stocker les aliments
  
    public static ArrayList<String> produitsList = new ArrayList<>();
    
    // Format de date
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args){
          public static void initialiserExemples() {
        // Ajout d'exemples comme dans le résultat attendu
        ajouterAliment("Spaghetti Panzini", "Féculant", creerDate(24, 6, 2025), creerDate(27, 6, 2025), 1.12);
        ajouterAliment("Riz Uncle Ben's", "Féculant", creerDate(24, 6, 2025), creerDate(30, 6, 2025), 1.30);
        ajouterAliment("Saucisse de Francfort", "Viande", creerDate(23, 6, 2025), creerDate(23, 6, 2025), 2.50);
    }
    
    }
     public static Date creerDate(int jour, int mois, int annee) {
        try {
            return dateFormat.parse(jour + "/" + mois + "/" + annee);
        } catch (Exception e) {
            return new Date(); // Retourne la date actuelle en cas d'erreur
        }
    }
     
    public static void afficherMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        String choix;
        
        do {
            afficherInventaire();
            System.out.println("\nVoulez-vous ?");
            System.out.println("(A) Ajouter un nouvel article");
            System.out.println("(B) Supprimer un article");
            System.out.println("(Y) Chercher un article");
            System.out.println("(X) Quitter");
            System.out.print("Votre choix : ");
            
            choix = scanner.nextLine().toUpperCase();
            
            switch (choix) {
                case "A":
                    ajouterNouvelArticle(scanner);
                    break;
                case "B":
                    supprimerArticle(scanner);
                    break;
                case "Y":
                    chercherArticle(scanner);
                    break;
                case "X":
                    System.out.println("Merci au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (!choix.equals("X"));
        
        scanner.close();
    }
}