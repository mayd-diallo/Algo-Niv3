

/* A l'aide de boucle, tableau, exception et fonction. Me faire un tableau contenant 5 noms de villes (Vous pouvez choisir vos propres villes), créer une méthode pour afficher les villes du tableau numéroté par ligne et autre une méthode pour choisir un villes selon 

la position de sa cellule du tableau et puis affichera un message de bienvenue dans la langue du villes choisi.:

Bonjour, quelle ville voulez-vous visiter ?

0 - Paris 
1 - Londres
2 - Madrid
3 - Lisbonne
4 - Berlin

0
Bienvenue à Paris !

Voulez-vous visiter une autre ville ? (O/N)
O

1
Welcome to London !

Voulez-vous visiter une autre ville ? (O/N)
O

2
Bienvenido a Madrid !
Voulez-vous visiter une autre ville ? (O/N)
O


3
Bem-vindo a Lisboa !
Voulez-vous visiter une autre ville ? (O/N)
O

4
Willkommen in Berlin !
Voulez-vous visiter une autre ville ? (O/N)
O


5
Désolé, cette ville n'est pas sur la liste !
Voulez-vous visiter une autre ville ? (O/N)
O

Votre voyage est terminé :) !

*/
import java.util.Scanner;

public class ListeVille {
       // Tableau des villes
    private static String[] villes = {"Paris", "Londres", "Madrid", "Lisbonne", "Berlin"};
    
    // Tableau des messages de bienvenue dans les langues correspondantes
    private static String[] messages = {
        "Bienvenue à Paris !",
        "Welcome to London !",
        "Bienvenido a Madrid !",
        "Bem-vindo a Lisboa !",
        "Willkommen in Berlin !"
    };
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char choix = 'O';
        
        do {
            try {
                // Afficher les villes numérotées
                afficherVilles();
                
                System.out.println("\nBonjour, quelle ville voulez-vous visiter ?");
                
                // Lire le choix de l'utilisateur
                int index = scanner.nextInt();
                
                // Afficher le message de bienvenue
                afficherMessageBienvenue(index);
                
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Désolé, cette ville n'est pas sur la liste !");
            } catch (Exception e) {
                System.out.println("Veuillez entrer un nombre valide.");
                scanner.next(); // Pour vider le buffer en cas d'entrée invalide
                continue;
            }
            
            // Demander si l'utilisateur veut continuer
            System.out.println("\nVoulez-vous visiter une autre ville ? (O/N)");
            choix = scanner.next().charAt(0);
            
        } while (Character.toUpperCase(choix) == 'O');
        
        System.out.println("\nVotre voyage est terminé :) !");
        scanner.close();
    }
    
    // Méthode pour afficher les villes numérotées
    public static void afficherVilles() {
        for (int i = 0; i < villes.length; i++) {
            System.out.println(i + " - " + villes[i]);
        }
    }
    
    // Méthode pour afficher le message de bienvenue selon l'index
    public static void afficherMessageBienvenue(int index) {
        if (index >= 0 && index < messages.length) {
            System.out.println(messages[index]);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
