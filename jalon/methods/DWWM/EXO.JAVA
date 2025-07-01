import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class InventaireAlimentaire {
    // Tableau des types d'aliments
    private static final String[] TYPES_ALIMENTS = {
        "Viande", "Légume", "Féculant", "Fruit", "Laitage", 
        "Poisson", "Dessert", "Pâtisserie", "Boulangerie"
    };
    
    // Liste pour stocker les aliments
    private static ArrayList<String> noms = new ArrayList<>();
    private static ArrayList<String> types = new ArrayList<>();
    private static ArrayList<Date> datesFabrication = new ArrayList<>();
    private static ArrayList<Date> datesPeremption = new ArrayList<>();
    private static ArrayList<Double> prix = new ArrayList<>();
    private static ArrayList<Boolean> consommables = new ArrayList<>();
    
    // Format de date
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public static void main(String[] args) {
        initialiserExemples();
        afficherMenuPrincipal();
    }
    
    private static void initialiserExemples() {
        // Ajout d'exemples comme dans le résultat attendu
        ajouterAliment("Spaghetti Panzini", "Féculant", creerDate(24, 6, 2025), creerDate(27, 6, 2025), 1.12);
        ajouterAliment("Riz Uncle Ben's", "Féculant", creerDate(24, 6, 2025), creerDate(30, 6, 2025), 1.30);
        ajouterAliment("Saucisse de Francfort", "Viande", creerDate(23, 6, 2025), creerDate(23, 6, 2025), 2.50);
    }
    
    private static Date creerDate(int jour, int mois, int annee) {
        try {
            return dateFormat.parse(jour + "/" + mois + "/" + annee);
        } catch (Exception e) {
            return new Date(); // Retourne la date actuelle en cas d'erreur
        }
    }
    
    private static void afficherMenuPrincipal() {
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
    
    private static void afficherInventaire() {
        System.out.println("\n=== INVENTAIRE ALIMENTAIRE ===");
        if (noms.isEmpty()) {
            System.out.println("Aucun article dans l'inventaire.");
            return;
        }
        
        Date aujourdhui = new Date();
        
        for (int i = 0; i < noms.size(); i++) {
            System.out.println("(" + i + ") Nom : " + noms.get(i));
            System.out.println("Type : " + types.get(i) + 
                             " | Date de fabrication : " + dateFormat.format(datesFabrication.get(i)) + 
                             " | Date de péremption : " + dateFormat.format(datesPeremption.get(i)));
            
            // Vérifier si consommable
            boolean perime = aujourdhui.after(datesPeremption.get(i));
            boolean perimeBientot = estPerimeBientot(datesPeremption.get(i), aujourdhui);
            
            if (perime) {
                System.out.println("Consommable : Périmé");
                System.out.println("Prix : Aucun (Article périmé)");
            } else if (perimeBientot) {
                System.out.println("Consommable (Périmé bientôt !!!)");
                double prixReduit = prix.get(i) * 0.8; // 20% de réduction
                System.out.println("Prix : " + String.format("%.2f", prixReduit) + "€ (Après réduction de 20%)");
            } else {
                System.out.println("Consommable : Oui");
                System.out.println("Prix : " + String.format("%.2f", prix.get(i)) + "€");
            }
            
            System.out.println();
        }
    }
    
    private static boolean estPerimeBientot(Date datePeremption, Date aujourdhui) {
        long diff = datePeremption.getTime() - aujourdhui.getTime();
        long joursRestants = diff / (1000 * 60 * 60 * 24);
        return joursRestants <= 3 && joursRestants >= 0;
    }
    
    private static void ajouterNouvelArticle(Scanner scanner) {
        System.out.println("\n--- Ajout d'un nouvel article ---");
        
        System.out.print("Nom de l'article : ");
        String nom = scanner.nextLine();
        
        System.out.println("Types disponibles :");
        for (int i = 0; i < TYPES_ALIMENTS.length; i++) {
            System.out.println((i+1) + ". " + TYPES_ALIMENTS[i]);
        }
        System.out.print("Choisissez le type (numéro) : ");
        int typeIndex = Integer.parseInt(scanner.nextLine()) - 1;
        String type = TYPES_ALIMENTS[typeIndex];
        
        System.out.print("Jour de fabrication (jj) : ");
        int jjFab = Integer.parseInt(scanner.nextLine());
        System.out.print("Mois de fabrication (mm) : ");
        int mmFab = Integer.parseInt(scanner.nextLine());
        System.out.print("Année de fabrication (aaaa) : ");
        int aaaaFab = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Jour de péremption (jj) : ");
        int jjPer = Integer.parseInt(scanner.nextLine());
        System.out.print("Mois de péremption (mm) : ");
        int mmPer = Integer.parseInt(scanner.nextLine());
        System.out.print("Année de péremption (aaaa) : ");
        int aaaaPer = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Prix de base : ");
        double prixBase = Double.parseDouble(scanner.nextLine());
        
        Date dateFab = creerDate(jjFab, mmFab, aaaaFab);
        Date datePer = creerDate(jjPer, mmPer, aaaaPer);
        
        ajouterAliment(nom, type, dateFab, datePer, prixBase);
        
        System.out.println("Article ajouté avec succès !");
    }
    
    private static void ajouterAliment(String nom, String type, Date dateFab, Date datePer, double prixBase) {
        noms.add(nom);
        types.add(type);
        datesFabrication.add(dateFab);
        datesPeremption.add(datePer);
        prix.add(prixBase);
        
        // On ne met pas à jour consommable ici, car cela dépend de la date actuelle
        // et sera vérifié à chaque affichage
    }
    
    private static void supprimerArticle(Scanner scanner) {
        if (noms.isEmpty()) {
            System.out.println("L'inventaire est vide, aucun article à supprimer.");
            return;
        }
        
        afficherInventaire();
        System.out.print("Entrez le numéro de l'article à supprimer : ");
        int index = Integer.parseInt(scanner.nextLine());
        
        if (index >= 0 && index < noms.size()) {
            noms.remove(index);
            types.remove(index);
            datesFabrication.remove(index);
            datesPeremption.remove(index);
            prix.remove(index);
            System.out.println("Article supprimé avec succès.");
        } else {
            System.out.println("Numéro invalide.");
        }
    }
    
    private static void chercherArticle(Scanner scanner) {
        System.out.print("Entrez le nom (ou partie du nom) de l'article à chercher : ");
        String recherche = scanner.nextLine().toLowerCase();
        
        System.out.println("\n--- Résultats de la recherche ---");
        boolean trouve = false;
        Date aujourdhui = new Date();
        
        for (int i = 0; i < noms.size(); i++) {
            if (noms.get(i).toLowerCase().contains(recherche)) {
                trouve = true;
                System.out.println("(" + i + ") Nom : " + noms.get(i));
                System.out.println("Type : " + types.get(i));
                System.out.println("Date de fabrication : " + dateFormat.format(datesFabrication.get(i)));
                System.out.println("Date de péremption : " + dateFormat.format(datesPeremption.get(i)));
                
                boolean perime = aujourdhui.after(datesPeremption.get(i));
                boolean perimeBientot = estPerimeBientot(datesPeremption.get(i), aujourdhui);
                
                if (perime) {
                    System.out.println("Statut : Périmé");
                } else if (perimeBientot) {
                    System.out.println("Statut : Périmé bientôt !");
                } else {
                    System.out.println("Statut : Consommable");
                }
                
                System.out.println();
            }
        }
        
        if (!trouve) {
            System.out.println("Aucun article trouvé avec ce nom.");
        }
    }
}

