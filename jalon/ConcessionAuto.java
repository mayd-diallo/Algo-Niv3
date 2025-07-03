package jalon;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConcessionAuto {
    // Données des voitures
    static String[] marques = {"Volkswagen", "Audi", "Porsche", "Lamborghini"};
    static String[][] modeles = {
        {"Polo", "Tiguan", "Golf"},
        {"A3", "Q5", "A4"},
        {"Macan", "911 Carrera"},
        {"Huracán", "Aventador"}
    };
    static double[][] prix = {
        {23000, 36000, 29000},
        {34000, 54000, 43000},
        {70000, 120000},
        {260000, 520000}
    };
    static String[] couleurs = {"Blanc", "Noir", "Rouge", "Bleu"};
    static int[] supplementsCouleur = {500, 0, 1000, 2000};
    
    // Liste pour stocker les voitures
    static ArrayList<String[]> voitures = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;
        
        while (continuer) {
            afficherMenuPrincipal();
            int choix = scanner.nextInt();
            scanner.nextLine(); // Pour consommer le retour à la ligne
            
            switch (choix) {
                case 0:
                    continuer = false;
                    System.out.println("Au revoir !");
                    break;
                case 1:
                    ajouterVoiture(scanner);
                    break;
                case 2:
                    supprimerVoiture(scanner);
                    break;
                case 3:
                    rechercherVoiture(scanner);
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
        scanner.close();
    }
    
    static void afficherMenuPrincipal() {
        System.out.println("\nBienvenu dans l'inventaire de votre concession :");
        
        if (voitures.isEmpty()) {
            System.out.println("-Vous n'avez aucune voiture à vendre dans votre concession, veuillez en ajouter une (1)");
        } else {
            for (String[] voiture : voitures) {
                afficherVoiture(voiture);
            }
        }
        
        System.out.println("\n(0)Quitter");
        System.out.println("(1) Ajouter une voiture");
        System.out.println("(2) Supprimer une voiture");
        System.out.println("(3) Rechercher une voiture");
        System.out.print("Votre choix : ");
    }
    
    static void ajouterVoiture(Scanner scanner) {
        System.out.println("\nVous voulez ajouter une voiture, très bien");
        
        // Choix de la marque
        System.out.println("Quel est sa marque, choisissez son numero");
        for (int i = 0; i < marques.length; i++) {
            System.out.println((i+1) + " - " + marques[i]);
        }
        int choixMarque = scanner.nextInt() - 1;
        scanner.nextLine(); // Pour consommer le retour à la ligne
        
        if (choixMarque < 0 || choixMarque >= marques.length) {
            System.out.println("Choix invalide");
            return;
        }
        
        String marque = marques[choixMarque];
        System.out.println("Vous avez choisi la marque : " + marque);
        
        // Choix du modèle
        System.out.println("Quel est son modèle, choisissez son numéro");
        for (int i = 0; i < modeles[choixMarque].length; i++) {
            System.out.println((i+1) + " - " + modeles[choixMarque][i]);
        }
        int choixModele = scanner.nextInt() - 1;
        scanner.nextLine(); // Pour consommer le retour à la ligne
        
        if (choixModele < 0 || choixModele >= modeles[choixMarque].length) {
            System.out.println("Choix invalide");
            return;
        }
        
        String modele = modeles[choixMarque][choixModele];
        System.out.println("Vous avez choisi le modèle : " + modele);
        
        // Neuf ou occasion
        System.out.println("Est-il neuf ? (true/false)");
        boolean neuf = scanner.nextBoolean();
        scanner.nextLine(); // Pour consommer le retour à la ligne
        
        int kilometrage = 0;
        double reduction = 0;
        
        if (!neuf) {
            System.out.println("Cette voiture est en occasion (soit -10%)");
            System.out.println("Quel est son kilométrage (en km) ?");
            kilometrage = scanner.nextInt();
            scanner.nextLine(); // Pour consommer le retour à la ligne
            
            if (kilometrage >= 200000) {
                reduction = 0.5;
                System.out.println("La voiture a " + kilometrage + "km au compteur : (réduction de -50%)");
            } else if (kilometrage >= 100000) {
                reduction = 0.25;
                System.out.println("La voiture a " + kilometrage + "km au compteur : (réduction de -25%)");
            } else {
                reduction = 0.1;
                System.out.println("La voiture a " + kilometrage + "km au compteur : (réduction de -10%)");
            }
        }
        
        // Couleur
        System.out.println("Quel est sa couleur :");
        for (int i = 0; i < couleurs.length; i++) {
            System.out.println((i+1) + " - " + couleurs[i] + 
                             (supplementsCouleur[i] > 0 ? " (+" + supplementsCouleur[i] + " euros)" : " (gratuit)"));
        }
        int choixCouleur = scanner.nextInt() - 1;
        scanner.nextLine(); // Pour consommer le retour à la ligne
        
        if (choixCouleur < 0 || choixCouleur >= couleurs.length) {
            System.out.println("Choix invalide");
            return;
        }
        
        String couleur = couleurs[choixCouleur];
        int supplement = supplementsCouleur[choixCouleur];
        System.out.println("Vous avez choisi la couleur " + couleur.toLowerCase() + ", +" + supplement + " euros sur le prix");
        
        // Calcul du prix
        double prixInitial = prix[choixMarque][choixModele];
        double prixFinal = prixInitial + supplement;
        
        if (!neuf) {
            prixFinal *= (1 - reduction);
        }
        
        // Génération du code
        LocalDateTime maintenant = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = maintenant.format(formatter);
        
        String code = marque.substring(0, 2).toUpperCase() + 
                      modele.substring(0, 2).toUpperCase() + 
                      "-" + date;
        
        // Création de la voiture
        String[] voiture = {
            code,
            marque,
            modele,
            neuf ? "oui" : "non",
            Integer.toString(kilometrage),
            couleur,
            String.format("%.0f", prixFinal)
        };
        
        voitures.add(voiture);
        
        // Affichage de la voiture ajoutée
        System.out.print("[");
        afficherVoiture(voiture);
        System.out.println("]");
        
        // Demander si on veut ajouter une autre voiture
        System.out.println("Voulez-vous ajouter une autre voiture (true/false)?");
        boolean ajouterAutre = scanner.nextBoolean();
        scanner.nextLine(); // Pour consommer le retour à la ligne
        
        if (ajouterAutre) {
            ajouterVoiture(scanner);
        }
    }
    
    static void supprimerVoiture(Scanner scanner) {
        if (voitures.isEmpty()) {
            System.out.println("Il n'y a aucune voiture à supprimer.");
            return;
        }
        
        System.out.println("Quelle voiture voulez-vous supprimer ? (entrez son numéro)");
        for (int i = 0; i < voitures.size(); i++) {
            System.out.print("(" + (i+1) + ") ");
            afficherVoiture(voitures.get(i));
        }
        
        int choix = scanner.nextInt() - 1;
        scanner.nextLine(); // Pour consommer le retour à la ligne
        
        if (choix >= 0 && choix < voitures.size()) {
            System.out.print("Voiture supprimée : ");
            afficherVoiture(voitures.get(choix));
            voitures.remove(choix);
        } else {
            System.out.println("Numéro invalide.");
        }
    }
    
    static void rechercherVoiture(Scanner scanner) {
        if (voitures.isEmpty()) {
            System.out.println("Il n'y a aucune voiture à rechercher.");
            return;
        }
        
        System.out.println("Rechercher par :");
        System.out.println("(1) Marque");
        System.out.println("(2) Modèle");
        System.out.println("(3) Kilométrage maximum");
        System.out.println("(4) Couleur");
        System.out.print("Votre choix : ");
        
        int critere = scanner.nextInt();
        scanner.nextLine(); // Pour consommer le retour à la ligne
        
        boolean trouve = false;
        
        switch (critere) {
            case 1:
                System.out.println("Entrez la marque à rechercher :");
                for (int i = 0; i < marques.length; i++) {
                    System.out.println((i+1) + " - " + marques[i]);
                }
                int choixMarque = scanner.nextInt() - 1;
                scanner.nextLine(); // Pour consommer le retour à la ligne
                
                if (choixMarque >= 0 && choixMarque < marques.length) {
                    String marque = marques[choixMarque];
                    for (String[] voiture : voitures) {
                        if (voiture[1].equals(marque)) {
                            afficherVoiture(voiture);
                            trouve = true;
                        }
                    }
                }
                break;
                
            case 2:
                System.out.print("Entrez le modèle à rechercher : ");
                String modeleRecherche = scanner.nextLine();
                for (String[] voiture : voitures) {
                    if (voiture[2].equalsIgnoreCase(modeleRecherche)) {
                        afficherVoiture(voiture);
                        trouve = true;
                    }
                }
                break;
                
            case 3:
                System.out.print("Entrez le kilométrage maximum : ");
                int kmMax = scanner.nextInt();
                scanner.nextLine(); // Pour consommer le retour à la ligne
                for (String[] voiture : voitures) {
                    if (!voiture[3].equals("oui") && Integer.parseInt(voiture[4]) <= kmMax) {
                        afficherVoiture(voiture);
                        trouve = true;
                    }
                }
                break;
                
            case 4:
                System.out.println("Entrez la couleur à rechercher :");
                for (int i = 0; i < couleurs.length; i++) {
                    System.out.println((i+1) + " - " + couleurs[i]);
                }
                int choixCouleur = scanner.nextInt() - 1;
                scanner.nextLine(); // Pour consommer le retour à la ligne
                
                if (choixCouleur >= 0 && choixCouleur < couleurs.length) {
                    String couleur = couleurs[choixCouleur];
                    for (String[] voiture : voitures) {
                        if (voiture[5].equals(couleur)) {
                            afficherVoiture(voiture);
                            trouve = true;
                        }
                    }
                }
                break;
                
            default:
                System.out.println("Critère invalide.");
        }
        
        if (!trouve) {
            System.out.println("Aucune voiture trouvée avec ces critères.");
        }
    }
    
    static void afficherVoiture(String[] voiture) {
        System.out.println("code : " + voiture[0] + 
                         " | marque : " + voiture[1] + 
                         " | modele : " + voiture[2] + 
                         " | Neuf : " + voiture[3] + 
                         " | Kilométrage : " + voiture[4] + 
                         " | Couleur : " + voiture[5] + 
                         " | Prix total : " + voiture[6] + " €");
    }
}
