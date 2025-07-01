package jalon.cda;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import jalon.methods.MenuList;

/*
 * Bonjour les Avengers, 

Nous sommes le site ecommerce ElectroDepot, nous aurions besoin d’un programme qui nous permettrait de gérer notre stock de produit, l’afficher sous forme de liste avec : 


N°id du produit : Qui se crée automatiquement en incrémentation 

Nom du produit : le nom 

Type de produit : TV/Electro-ménager/Micro-Informatique/Audio/  

Date de stockage du produit : Date de création du produit, (si la date dépasse 4 mois, alors on applique une réduction de 10%, mais attention ça ne veut pas dire qu’il est soldé) 

Solde : Si le produit est soldé alors il aura une réduction de 40% de son prix (Les soldes ne sont pas cumulatifs avec les réductions précédemment évoqués) 

Prix : Le prix de base du produit 

 

On veut gérer cette liste en ajoutant ou supprimant nos produits. On peut aussi rechercher un produit en fonction de son ID 
 */

public class ExoJalon {


    static Scanner scanner = new Scanner(System.in, "Cp852");

    public static void main(String[] args) {

        ArrayList<String> productList = new ArrayList();
        int productId = 0;
        String [] productType= {"TV", "Electro-ménager", "Micro-Informatique", "Audio"};
        String productName="";
        String productDate="";
        double productPrice=0.0;
        boolean isOnSale = false;
        int option = 0;

        boolean continueAdding = true;

        main:while (continueAdding) {

                MenuList.displayMenu();
                System.out.print("Choisissez une option : ");

                try{
                option = scanner.nextInt();
                scanner.nextLine(); // Pour consommer la nouvelle ligne restante
                } catch (InputMismatchException e) {

                System.out.print("Erreur : Saisie incorrecte. Veuillez entrer un nombre.");
                scanner.nextLine(); // Pour consommer la nouvelle ligne restante

                } 

                switch (option) {
                    case 1:
                        // Ajouter un produit
                            System.out.println("Ajout d'un nouveau produit :");
                            MenuList.addProduct(productList, productName, productType, productDate, productPrice, isOnSale, scanner);

                        break;
                    case 2:

                        if(productList.isEmpty()) {

                            System.out.println("La liste des produits est vide. Aucun produit à supprimer, veuiller ajouter d'abord un produit (1).");

                        }else{
                         // Supprimer un produit
                        MenuList.removeProduct(productList, scanner);
                        }
  
                        break;
                    case 3:
                        // Rechercher un produit par ID
                        if(productList.isEmpty()) {

                            System.out.println("La liste des produits est vide. Aucun produit à rechercher, veuiller ajouter d'abord un produit (1).");
                        }else{
                            MenuList.searchProductById(productList, scanner);
                        }
                        break;
                    case 4:

                            MenuList.displayAllProducts(productList, scanner);
                        
                        break;
                    case 5:
                        continueAdding = false;
                        System.out.println("Merci pour votre visite, au revoir.");
                        break main; // Sort de la boucle principale et termine le programme
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            }

        }

    



}
