package jalon;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VenteVoitures {
    // Tableaux de données
    static String[] marques = {"Volkswagen", "Audi", "Porsche", "Lamborghini"};
    static String[][] modeles = {
        {"Polo", "Tiguan", "Golf"},        // Volkswagen
        {"A3", "Q5", "A4"},                // Audi
        {"Macan", "Cayenne"},              // Porsche
        {"Huracán", "Aventador"}           // Lamborghini
    };
    static String[] couleurs = {"Blanc", "Noir", "Rouge", "Bleu"};
    static int[] supplementsCouleur = {500, 0, 1000, 2000};
    
    // Prix de base pour chaque modèle
    static int[][] prix = {
        {23000, 36000, 29000},    // Volkswagen: Polo, Tiguan, Golf
        {34000, 54000, 43000},    // Audi: A3, Q5, A4
        {70000, 120000},          // Porsche: Macan, Cayenne
        {260000, 520000}          // Lamborghini: Huracán, Aventador
    };
    
    // Liste pour stocker les voitures
    static ArrayList<String[]> voitures = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        afficherMenuPrincipal();
    }

    static void afficherMenuPrincipal() {
        while (true) {
            System.out.println("\n GESTION DE VENTE DE VOITURES ");
            System.out.println("1. Ajouter une voiture");
            System.out.println("2. Rechercher une voiture");
            System.out.println("3. Supprimer une voiture");
            System.out.println("4. Afficher toutes les voitures");
            System.out.println("5. Quitter");
            System.out.print("Choix: ");
            
            int choix = scanner.nextInt();
            scanner.nextLine(); // Pour consommer le retour à la ligne
            
            switch (choix) {
                case 1:
                    ajouterVoiture();
                    break;
                case 2:
                    rechercherVoiture();
                    break;
                case 3:
                    supprimerVoiture();
                    break;
                case 4:
                    afficherToutesVoitures();
                    break;
                case 5:
                    System.out.println("Au revoir!");
                    return;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }

    static void ajouterVoiture() {
        System.out.println("\nAJOUTER UNE VOITURE ");
        
        // Choix de la marque
        System.out.println("Marques disponibles:");
        for (int i = 0; i < marques.length; i++) {
            System.out.println((i+1) + ". " + marques[i]);
        }
        System.out.print("Choisissez une marque (1-" + marques.length + "): ");
        int choixMarque = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (choixMarque < 0 || choixMarque >= marques.length) {
            System.out.println("Choix invalide!");
            return;
        }
        
        // Choix du modèle
        System.out.println("\nModèles disponibles pour " + marques[choixMarque] + ":");
        for (int i = 0; i < modeles[choixMarque].length; i++) {
            System.out.println((i+1) + ". " + modeles[choixMarque][i]);
        }
        System.out.print("Choisissez un modèle (1-" + modeles[choixMarque].length + "): ");
        int choixModele = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (choixModele < 0 || choixModele >= modeles[choixMarque].length) {
            System.out.println("Choix invalide!");
            return;
        }
        
        // Neuf ou occasion
        System.out.print("La voiture est-elle neuve (true/false)? ");
        boolean neuf = scanner.nextBoolean();
        scanner.nextLine();
        
        int kilometrage = 0;
        double reduction = 0;
        
        if (!neuf) {
            System.out.print("Kilométrage de la voiture: ");
            kilometrage = scanner.nextInt();
            scanner.nextLine();
            
            // Calcul de la réduction selon le kilométrage
            if (kilometrage >= 200000) {
                reduction = 0.5;
            } else if (kilometrage >= 100000) {
                reduction = 0.25;
            } else {
                reduction = 0.1;
            }
        }
        
        // Choix de la couleur
        System.out.println("\nCouleurs disponibles:");
        for (int i = 0; i < couleurs.length; i++) {
            System.out.println((i+1) + ". " + couleurs[i] + " (" + supplementsCouleur[i] + "€)");
        }
        System.out.print("Choisissez une couleur (1-" + couleurs.length + "): ");
        int choixCouleur = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (choixCouleur < 0 || choixCouleur >= couleurs.length) {
            System.out.println("Choix invalide!");
            return;
        }
        
        // Calcul du prix final
        int prixBase = prix[choixMarque][choixModele];
        int supplement = supplementsCouleur[choixCouleur];
        double prixFinal = prixBase + supplement;
        
        if (!neuf) {
            prixFinal = prixFinal * (1 - reduction);
        }
        
        // Génération du code produit
        String codeProduit = genererCodeProduit(marques[choixMarque], modeles[choixMarque][choixModele]);
        
        // Création du tableau pour stocker les infos de la voiture
        String[] voiture = new String[8];
        voiture[0] = marques[choixMarque];
        voiture[1] = modeles[choixMarque][choixModele];
        voiture[2] = String.valueOf(neuf);
        voiture[3] = String.valueOf(kilometrage);
        voiture[4] = couleurs[choixCouleur];
        voiture[5] = String.valueOf(prixBase);
        voiture[6] = String.valueOf((int)prixFinal);
        voiture[7] = codeProduit;
        
        // Ajout à la liste
        voitures.add(voiture);
        
        System.out.println("\nVoiture ajoutée avec succès!");
        afficherDetailsVoiture(voiture);
    }

    static String genererCodeProduit(String marque, String modele) {
        // Deux premières lettres de la marque
        String codeMarque = marque.substring(0, Math.min(2, marque.length())).toUpperCase();
        
        // Deux premières lettres du modèle
        String codeModele = modele.substring(0, Math.min(2, modele.length())).toUpperCase();
        
        // Date et heure actuelle
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String dateHeure = LocalDateTime.now().format(formatter);
        
        return codeMarque + codeModele + "-" + dateHeure;
    }

    static void afficherDetailsVoiture(String[] voiture) {
        System.out.println("\nDétails de la voiture:");
        System.out.println("Marque: " + voiture[0]);
        System.out.println("Modèle: " + voiture[1]);
        System.out.println("État: " + (Boolean.parseBoolean(voiture[2]) ? "Neuf" : "Occasion (" + voiture[3] + " km)"));
        System.out.println("Couleur: " + voiture[4]);
        System.out.println("Prix de base: " + voiture[5] + "€");
        System.out.println("Prix final: " + voiture[6] + "€");
        System.out.println("Code produit: " + voiture[7]);
    }

    static void rechercherVoiture() {
        System.out.println("\nRECHERCHER UNE VOITURE");
        System.out.println("1. Par marque");
        System.out.println("2. Par modèle");
        System.out.println("3. Par couleur");
        System.out.println("4. Par kilométrage max");
        System.out.println("5. Par prix max");
        System.out.print("Choix: ");
        
        int choix = scanner.nextInt();
        scanner.nextLine();
        
        switch (choix) {
            case 1:
                rechercherParMarque();
                break;
            case 2:
                rechercherParModele();
                break;
            case 3:
                rechercherParCouleur();
                break;
            case 4:
                rechercherParKilometrage();
                break;
            case 5:
                rechercherParPrix();
                break;
            default:
                System.out.println("Choix invalide!");
        }
    }

    static void rechercherParMarque() {
        System.out.println("\nMarques disponibles:");
        for (int i = 0; i < marques.length; i++) {
            System.out.println((i+1) + ". " + marques[i]);
        }
        System.out.print("Choisissez une marque (1-" + marques.length + "): ");
        int choix = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (choix < 0 || choix >= marques.length) {
            System.out.println("Choix invalide!");
            return;
        }
        
        String marqueRecherchee = marques[choix];
        boolean trouve = false;
        
        System.out.println("\nRésultats pour " + marqueRecherchee + ":");
        for (String[] voiture : voitures) {
            if (voiture[0].equals(marqueRecherchee)) {
                afficherDetailsVoiture(voiture);
                trouve = true;
            }
        }
        
        if (!trouve) {
            System.out.println("Aucune voiture trouvée pour cette marque.");
        }
    }

    static void rechercherParModele() {
        System.out.print("\nEntrez le modèle à rechercher: ");
        String modeleRecherche = scanner.nextLine();
        boolean trouve = false;
        
        System.out.println("\nRésultats pour " + modeleRecherche + ":");
        for (String[] voiture : voitures) {
            if (voiture[1].equalsIgnoreCase(modeleRecherche)) {
                afficherDetailsVoiture(voiture);
                trouve = true;
            }
        }
        
        if (!trouve) {
            System.out.println("Aucune voiture trouvée pour ce modèle.");
        }
    }

    static void rechercherParCouleur() {
        System.out.println("\nCouleurs disponibles:");
        for (int i = 0; i < couleurs.length; i++) {
            System.out.println((i+1) + ". " + couleurs[i]);
        }
        System.out.print("Choisissez une couleur (1-" + couleurs.length + "): ");
        int choix = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (choix < 0 || choix >= couleurs.length) {
            System.out.println("Choix invalide!");
            return;
        }
        
        String couleurRecherchee = couleurs[choix];
        boolean trouve = false;
        
        System.out.println("\nRésultats pour " + couleurRecherchee + ":");
        for (String[] voiture : voitures) {
            if (voiture[4].equals(couleurRecherchee)) {
                afficherDetailsVoiture(voiture);
                trouve = true;
            }
        }
        
        if (!trouve) {
            System.out.println("Aucune voiture trouvée pour cette couleur.");
        }
    }

    static void rechercherParKilometrage() {
        System.out.print("\nEntrez le kilométrage maximum: ");
        int kmMax = scanner.nextInt();
        scanner.nextLine();
        boolean trouve = false;
        
        System.out.println("\nRésultats pour kilométrage <= " + kmMax + " km:");
        for (String[] voiture : voitures) {
            if (!Boolean.parseBoolean(voiture[2])) { // Si occasion
                int km = Integer.parseInt(voiture[3]);
                if (km <= kmMax) {
                    afficherDetailsVoiture(voiture);
                    trouve = true;
                }
            }
        }
        
        if (!trouve) {
            System.out.println("Aucune voiture d'occasion trouvée avec ce kilométrage maximum.");
        }
    }

    static void rechercherParPrix() {
        System.out.print("\nEntrez le prix maximum: ");
        int prixMax = scanner.nextInt();
        scanner.nextLine();
        boolean trouve = false;
        
        System.out.println("\nRésultats pour prix <= " + prixMax + "€:");
        for (String[] voiture : voitures) {
            int prixVoiture = Integer.parseInt(voiture[6]);
            if (prixVoiture <= prixMax) {
                afficherDetailsVoiture(voiture);
                trouve = true;
            }
        }
        
        if (!trouve) {
            System.out.println("Aucune voiture trouvée dans cette fourchette de prix.");
        }
    }

    static void supprimerVoiture() {
        System.out.println("\nSUPPRIMER UNE VOITURE");
        System.out.print("Entrez le code produit de la voiture à supprimer: ");
        String codeProduit = scanner.nextLine();
        boolean trouve = false;
        
        for (int i = 0; i < voitures.size(); i++) {
            if (voitures.get(i)[7].equals(codeProduit)) {
                System.out.println("Voiture trouvée:");
                afficherDetailsVoiture(voitures.get(i));
                System.out.print("Voulez-vous vraiment supprimer cette voiture? (oui/non): ");
                String confirmation = scanner.nextLine();
                
                if (confirmation.equalsIgnoreCase("oui")) {
                    voitures.remove(i);
                    System.out.println("Voiture supprimée avec succès!");
                } else {
                    System.out.println("Suppression annulée.");
                }
                trouve = true;
                break;
            }
        }
        
        if (!trouve) {
            System.out.println("Aucune voiture trouvée avec ce code produit.");
        }
    }

    static void afficherToutesVoitures() {
        System.out.println("\nLISTE COMPLÈTE DES VOITURES ");
        
        if (voitures.isEmpty()) {
            System.out.println("Aucune voiture enregistrée pour le moment.");
            return;
        }
        
        for (int i = 0; i < voitures.size(); i++) {
            System.out.println("\nVoiture #" + (i+1));
            afficherDetailsVoiture(voitures.get(i));
        }
    }
}
