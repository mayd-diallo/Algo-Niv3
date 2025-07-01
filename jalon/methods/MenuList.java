package jalon.methods;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuList {


    public static final String EURO = "\u20AC";

    public static void displayMenu() {
        System.out.println("(1). Ajouter un produit");
        System.out.println("(2). Supprimer un produit");
        System.out.println("(3). Rechercher un produit par ID");
        System.out.println("(4). Afficher la liste des produits");
        System.out.println("(5). Quitter");
    }


    public static void addProduct(ArrayList<String> list, String name, String[] type, String date, double price, boolean isOnSale, Scanner clavier) {
       
        // Code pour ajouter un produit dans la liste

        name = addProductName(list, name, clavier);

        String typeName = addProductType(list, type, clavier);
        date = addProductDate(list, date, clavier);
        price = addProductPrice(list, price, clavier);
        isOnSale = addProductSale(list, isOnSale, clavier);
        String discount = "";
        String onSaleString = isOnSale ? "Oui" : "Non"; /* On récupère si le produit est en solde */

        if (isOnSale) {
            price *= 0.6; // Appliquer la réduction de 40%
            discount = "(Réduction de 40% appliquée)"; 
        } else if (isOlderThanFourMonths(date) && !isOnSale) {
            price *= 0.9; // Appliquer la réduction de 10%
            discount = "(Réduction de 10% appliquée)";
        }

        String productDetails = "[ ID: " + (list.size() + 1) + ", Nom: " + name + ", Type: " + typeName + ", Date: " + date + ", Prix: " + price + " euros" + discount + ", En solde: " + onSaleString + " ]";

        list.add(productDetails);
        System.out.println("Produit ajouté : " + productDetails);



    }

    public static String addProductName(ArrayList<String> list, String name, Scanner clavier) {
        // Implémentez la logique pour ajouter un produit avec son nom
        // Par exemple, vous pouvez créer une nouvelle entrée dans la liste avec le nom du produit
       System.out.println("Nom du produit : ");
        name = clavier.nextLine();
        System.out.println("Nom du produit choisi : " + name);
        return name;
    }

        public static double addProductPrice(ArrayList<String> list, double price, Scanner clavier) {
        // Implémentez la logique pour ajouter un produit avec son nom
        // Par exemple, vous pouvez créer une nouvelle entrée dans la liste avec le nom du produit
        addProductPrice:while (true) {
        try {
        // Demande à l'utilisateur de saisir le prix du produit
       System.out.println("Prix du produit : ");
        price = clavier.nextDouble();
        System.out.println("Prix du produit choisi : " + price);
        return price;

        } catch (Exception e) {

            Exceptioner.TxtException(e,list.size());
            continue addProductPrice; // Recommencer la saisie du prix

        }
    }
        }

        public static boolean addProductSale(ArrayList<String> list, boolean isOnSale, Scanner clavier) {
        // Implémentez la logique pour ajouter un produit avec son solde
        // Par exemple, vous pouvez créer une nouvelle entrée dans la liste avec le solde du produit
        addProductSale:while (true) {
        
        try {
            System.out.println("En solde (true/false) : ");
            isOnSale = clavier.nextBoolean();
            System.out.println("En solde (Oui ou Non) : " + (isOnSale ? "Oui" : "Non"));
            return isOnSale;

        } catch (InputMismatchException e) {

            Exceptioner.TxtException(e,list.size());
            continue addProductSale; // Recommencer la saisie du solde
        }
        }  

        
    }

   

    public static String addProductType(ArrayList<String> list, String []type, Scanner clavier) {
        // Implémentez la logique pour ajouter un produit avec son type
        // Par exemple, vous pouvez créer une nouvelle entrée dans la liste avec le type du produit
               String typeName = "";
       addProductType:while (true) {
        try {
        System.out.println("Type de produit :");
        for (int i = 0; i < type.length; i++) {
            System.out.println("(" + (i + 1) + "). " + type[i]);
        }
            int typeChoice = clavier.nextInt();
            typeName = type[typeChoice - 1];
                     System.out.println("Type de produit choisi : " + typeName);

                     clavier.nextLine();
        } catch (Exception e) {

            Exceptioner.TxtException(e,type.length);
            clavier.nextLine();
            continue addProductType; // Recommencer la saisie du type

         }


        return typeName;
        }

        
    }


        public static String addProductDate(ArrayList<String> list, String date, Scanner clavier) {
        // Implémentez la logique pour ajouter une date de stockage
        // Par exemple, vous pouvez créer une nouvelle entrée dans la liste avec la date du produit
       addDate:while (true) { // Boucle pour recommencer la saisie de la date en cas d'erreur
        try {

            System.out.println("Date de stockage (DD/MM/YYYY) : ");
            date = clavier.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(date, formatter); /* On test si le format est bon */
            System.out.println("Date de stockage choisie : " + date);

        } catch (Exception e) {

                        Exceptioner.TxtException(e,list.size());
            continue addDate; // Recommencer la saisie de la date

        }

        return date;
        }

        
    }

    public static void removeProduct(ArrayList<String> list, Scanner clavier) {
        // Implémentez la logique pour supprimer un produit de la liste
        // Par exemple, vous pouvez utiliser productId pour identifier le produit à supprimer
       removeProduct:while (true) { // Boucle pour recommencer la saisie de la date en cas d'erreur
        int productId = 0;
        try {

            if (list.isEmpty()) {
                System.out.println("La liste des produits est vide. Aucun produit à supprimer, veuillez ajouter d'abord un produit (1).");
                break removeProduct; // Sort de la méthode si la liste est vide
            }

            System.out.println("Entrez l'ID du produit à supprimer : ");
            productId = clavier.nextInt();
            
            String removedProduct = list.remove(productId - 1);
            System.out.println("Produit supprimé : " + removedProduct);
            

        } catch (Exception e) {

            Exceptioner.TxtException(e,list.size());

            clavier.nextLine(); // Pour consommer la nouvelle ligne restante
            continue removeProduct;
        }

    }



    }

    public static void searchProductById(ArrayList<String> list, Scanner clavier) {
        // Implémentez la logique pour rechercher un produit par son ID
        // Par exemple, vous pouvez afficher les détails du produit correspondant à l'ID
        searchProductById:while (true) { // Boucle pour recommencer la saisie de l'ID en cas d'erreur
        int productId = 0;
        try {

            System.out.println("Entrez l'ID du produit à rechercher : ");
            productId = clavier.nextInt();

            String productDetails = list.get(productId - 1);
            System.out.println("Détails du produit : " + productDetails);
            break searchProductById; // Sort de la méthode si le produit est trouvé 

        } catch (Exception e) {

            Exceptioner.TxtException(e,list.size());
            clavier.nextLine(); // Pour consommer la nouvelle ligne restante
            continue searchProductById;
        }

    }

    }

    public static void displayAllProducts(ArrayList<String> list, Scanner clavier) {
        // Implémentez la logique pour afficher tous les produits de la liste
        // Par exemple, vous pouvez parcourir la liste et afficher les détails de chaque produit
        if (list.isEmpty()) {
            System.out.println("La liste des produits est vide. Aucun produit à afficher, veuillez ajouter d'abord un produit (1).");
        } else {
            System.out.println("Liste des produits :");
            for (String product : list) {
                System.out.println(product);
            }
        }
    }

    public static boolean isOlderThanFourMonths(String date) {
        // Implémentez la logique pour vérifier si la date est plus ancienne que 4 mois
        // Par exemple, vous pouvez utiliser la classe LocalDate pour comparer les dates

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate storageDate = LocalDate.parse(date, formatter); /* On récupère la date String on l'a converti au format LocalDate */
        LocalDate fourMonthsAgo = LocalDate.now().minusMonths(4); /* On récupère la date d'il y a 4 mois */
        return storageDate.isBefore(fourMonthsAgo); /* On vérifie si la date de stockage est avant la date d'il y a 4 mois */
    }

}
