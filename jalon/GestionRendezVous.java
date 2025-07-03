package jalon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GestionRendezVous {
    // Constantes pour les jours fériés (exemple pour 2023)
    static final String[] JOURS_FERIES = {
        "20230101", "20230410", "20230501", "20230508", "20230518", "20230529", "20230714", "20230815", "20231101", "20231111", "20231225"
    };
    
    // Types de consultation
    static final String[][] TYPES_CONSULTATION = {
        {"BS", "Bilan de santé", "120"},
        {"CD", "Cardiologie", "200"},
        {"VC", "Vaccinations", "0"},
        {"CM", "Certification médical", "100"},
        {"GN", "Général", "70"},
        {"SM", "Suivi médical", "60"}
    };
    
    // Liste pour stocker les rendez-vous
    static List<String[]> rendezVousList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    static SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        boolean running = true;
        
        while (running) {
            afficherMenu();
            int choix = scanner.nextInt();
            scanner.nextLine(); // consommer la nouvelle ligne
            
            switch (choix) {
                case 1:
                    ajouterRendezVous();
                    break;
                case 2:
                    afficherRendezVous();
                    break;
                case 3:
                    annulerRendezVous();
                    break;
                case 4:
                    decalerRendezVous();
                    break;
                case 5:
                    rechercherRendezVous();
                    break;
                case 6:
                    running = false;
                    System.out.println("Merci d'avoir utilisé notre système. Au revoir!");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }
    
    static void afficherMenu() {
        System.out.println("\nMenu Principal");
        System.out.println("1. Ajouter un rendez-vous");
        System.out.println("2. Afficher tous les rendez-vous");
        System.out.println("3. Annuler un rendez-vous");
        System.out.println("4. Décaler un rendez-vous");
        System.out.println("5. Rechercher un rendez-vous");
        System.out.println("6. Quitter");
        System.out.print("Votre choix: ");
    }
    
    static void ajouterRendezVous() {
        System.out.println("\nAjout d'un nouveau rendez-vous ");
        
        // Saisie des informations du client
        System.out.print("Nom du client: ");
        String nom = scanner.nextLine().toUpperCase();
        System.out.print("Prénom du client: ");
        String prenom = scanner.nextLine().toUpperCase();
        System.out.print("Âge du client: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consommer la nouvelle ligne
        
        // Affichage des types de consultation
        System.out.println("\nTypes de consultation disponibles:");
        for (String[] type : TYPES_CONSULTATION) {
            System.out.println(type[0] + " - " + type[1] + (type[2].equals("0") ? " (Gratuit)" : " (" + type[2] + "€)"));
        }
        
        System.out.print("Code de consultation: ");
        String codeConsultation = scanner.nextLine().toUpperCase();
        
        // Vérification du code de consultation
        boolean codeValide = false;
        String prixStr = "0";
        for (String[] type : TYPES_CONSULTATION) {
            if (type[0].equals(codeConsultation)) {
                codeValide = true;
                prixStr = type[2];
                break;
            }
        }
        
        if (!codeValide) {
            System.out.println("Code de consultation invalide.");
            return;
        }
        
        // Saisie de la date et heure
        System.out.print("Date du rendez-vous (JJ/MM/AAAA): ");
        String dateStr = scanner.nextLine();
        System.out.print("Heure du rendez-vous (HH:MM): ");
        String heureStr = scanner.nextLine();
        
        // Validation de la date et heure
        try {
            String dateHeureStr = dateStr.substring(6, 10) + dateStr.substring(3, 5) + dateStr.substring(0, 2) + 
                                 heureStr.substring(0, 2) + heureStr.substring(3, 5);
            Date dateRdv = dateFormat.parse(dateHeureStr);
            Date maintenant = new Date();
            
            // Vérification que la date n'est pas dans le passé
            if (dateRdv.before(maintenant)) {
                System.out.println("Impossible de prendre un rendez-vous dans le passé.");
                return;
            }
            
            // Vérification des horaires d'ouverture
            if (!estHoraireValide(dateRdv)) {
                System.out.println("Le cabinet est fermé à cette date/heure.");
                return;
            }
            
            // Vérification des conflits de rendez-vous
            if (existeDejaRendezVous(dateRdv)) {
                System.out.println("Un rendez-vous existe déjà à cette date/heure.");
                return;
            }
            
            // Génération du code de référence
            String codeReference = genererCodeReference(nom, prenom, dateHeureStr, codeConsultation);
            
            // Calcul du prix
            double prixInitial = Double.parseDouble(prixStr);
            double prixClient = calculerPrixClient(prixInitial, age);
            
            // Ajout du rendez-vous à la liste
            String[] rdv = {
                codeReference,
                nom,
                prenom,
                String.valueOf(age),
                codeConsultation,
                dateHeureStr,
                String.valueOf(prixInitial),
                String.valueOf(prixClient)
            };
            
            rendezVousList.add(rdv);
            System.out.println("Rendez-vous ajouté avec succès. Code de référence: " + codeReference);
            
        } catch (Exception e) {
            System.out.println("Format de date/heure invalide. Utilisez JJ/MM/AAAA et HH:MM.");
        }
    }
    
    static boolean estHoraireValide(Date date) {
        SimpleDateFormat jourFormat = new SimpleDateFormat("u");
        SimpleDateFormat heureFormat = new SimpleDateFormat("HHmm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        
        // Vérification du jour de la semaine (1-5 = Lundi-Vendredi)
        int jourSemaine = Integer.parseInt(jourFormat.format(date));
        if (jourSemaine > 5) return false;
        
        // Vérification des heures d'ouverture
        int heure = Integer.parseInt(heureFormat.format(date));
        if ((heure >= 800 && heure <= 1200) || (heure >= 1400 && heure <= 1700)) {
            // Vérification des jours fériés
            String dateStr = dateFormat.format(date);
            for (String jourFerie : JOURS_FERIES) {
                if (jourFerie.equals(dateStr)) return false;
            }
            return true;
        }
        return false;
    }
    
    static boolean existeDejaRendezVous(Date date) {
        for (String[] rdv : rendezVousList) {
            try {
                Date rdvDate = dateFormat.parse(rdv[5]);
                if (rdvDate.equals(date)) return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    static String genererCodeReference(String nom, String prenom, String dateHeureStr, String codeConsultation) {
        String initiales = nom.substring(0, 1) + prenom.substring(0, 1);
        return initiales + dateHeureStr + codeConsultation;
    }
    
    static double calculerPrixClient(double prixInitial, int age) {
        if (age < 18 || age >= 60) {
            return prixInitial * 0.2; // 80% pris en charge
        }
        return prixInitial * 0.4; // 60% pris en charge
    }
    
    static void afficherRendezVous() {
        System.out.println("\n Liste des rendez-vous ");
        if (rendezVousList.isEmpty()) {
            System.out.println("Aucun rendez-vous programmé.");
            return;
        }
        
        for (String[] rdv : rendezVousList) {
            afficherDetailsRendezVous(rdv);
        }
    }
    
    static void afficherDetailsRendezVous(String[] rdv) {
        try {
            Date date = dateFormat.parse(rdv[5]);
            System.out.println("\nCode de référence: " + rdv[0]);
            System.out.println("Client: " + rdv[1] + " " + rdv[2] + " (" + rdv[3] + " ans)");
            System.out.println("Type de consultation: " + rdv[4]);
            System.out.println("Date et heure: " + displayDateFormat.format(date));
            System.out.println("Prix initial: " + rdv[6] + "€");
            System.out.println("Prix pour le client: " + rdv[7] + "€");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void annulerRendezVous() {
        System.out.println("\nAnnulation d'un rendez-vous");
        System.out.print("Entrez le code de référence (ou une partie): ");
        String codeRecherche = scanner.nextLine().toUpperCase();
        
        List<String[]> resultats = new ArrayList<>();
        for (String[] rdv : rendezVousList) {
            if (rdv[0].contains(codeRecherche)) {
                resultats.add(rdv);
            }
        }
        
        if (resultats.isEmpty()) {
            System.out.println("Aucun rendez-vous trouvé.");
            return;
        }
        
        System.out.println("\nRendez-vous trouvés:");
        for (int i = 0; i < resultats.size(); i++) {
            System.out.print((i + 1) + ". ");
            afficherDetailsRendezVous(resultats.get(i));
        }
        
        System.out.print("\nQuel rendez-vous annuler? (numéro, 0 pour annuler): ");
        int choix = scanner.nextInt();
        scanner.nextLine(); // consommer la nouvelle ligne
        
        if (choix > 0 && choix <= resultats.size()) {
            String[] rdv = resultats.get(choix - 1);
            rendezVousList.remove(rdv);
            System.out.println("Rendez-vous annulé avec succès.");
        }
    }
    
    static void decalerRendezVous() {
        System.out.println("\nDécalage d'un rendez-vous ");
        System.out.print("Entrez le code de référence (ou une partie): ");
        String codeRecherche = scanner.nextLine().toUpperCase();
        
        List<String[]> resultats = new ArrayList<>();
        for (String[] rdv : rendezVousList) {
            if (rdv[0].contains(codeRecherche)) {
                resultats.add(rdv);
            }
        }
        
        if (resultats.isEmpty()) {
            System.out.println("Aucun rendez-vous trouvé.");
            return;
        }
        
        System.out.println("\nRendez-vous trouvés:");
        for (int i = 0; i < resultats.size(); i++) {
            System.out.print((i + 1) + ". ");
            afficherDetailsRendezVous(resultats.get(i));
        }
        
        System.out.print("\nQuel rendez-vous décaler? (numéro, 0 pour annuler): ");
        int choix = scanner.nextInt();
        scanner.nextLine(); // consommer la nouvelle ligne
        
        if (choix <= 0 || choix > resultats.size()) return;
        
        String[] rdv = resultats.get(choix - 1);
        
        // Saisie de la nouvelle date et heure
        System.out.print("Nouvelle date (JJ/MM/AAAA): ");
        String nouvelleDateStr = scanner.nextLine();
        System.out.print("Nouvelle heure (HH:MM): ");
        String nouvelleHeureStr = scanner.nextLine();
        
        try {
            String nouvelleDateHeureStr = nouvelleDateStr.substring(6, 10) + nouvelleDateStr.substring(3, 5) + 
                                        nouvelleDateStr.substring(0, 2) + nouvelleHeureStr.substring(0, 2) + 
                                        nouvelleHeureStr.substring(3, 5);
            Date nouvelleDate = dateFormat.parse(nouvelleDateHeureStr);
            Date maintenant = new Date();
            
            // Vérification que la date n'est pas dans le passé
            if (nouvelleDate.before(maintenant)) {
                System.out.println("Impossible de décaler un rendez-vous dans le passé.");
                return;
            }
            
            // Vérification des horaires d'ouverture
            if (!estHoraireValide(nouvelleDate)) {
                System.out.println("Le cabinet est fermé à cette date/heure.");
                return;
            }
            
            // Vérification des conflits de rendez-vous
            if (existeDejaRendezVous(nouvelleDate)) {
                System.out.println("Un rendez-vous existe déjà à cette date/heure.");
                return;
            }
            
            // Mise à jour du rendez-vous
            rdv[5] = nouvelleDateHeureStr;
            rdv[0] = genererCodeReference(rdv[1], rdv[2], nouvelleDateHeureStr, rdv[4]);
            
            System.out.println("Rendez-vous décalé avec succès. Nouveau code de référence: " + rdv[0]);
            
        } catch (Exception e) {
            System.out.println("Format de date/heure invalide. Utilisez JJ/MM/AAAA et HH:MM.");
        }
    }
    
    static void rechercherRendezVous() {
        System.out.println("\n=== Recherche de rendez-vous ===");
        System.out.println("1. Par code de référence");
        System.out.println("2. Par nom du client");
        System.out.println("3. Par type de consultation");
        System.out.println("4. Par date");
        System.out.print("Votre choix: ");
        
        int choix = scanner.nextInt();
        scanner.nextLine(); // consommer la nouvelle ligne
        
        List<String[]> resultats = new ArrayList<>();
        
        switch (choix) {
            case 1:
                System.out.print("Entrez le code de référence (ou une partie): ");
                String codeRecherche = scanner.nextLine().toUpperCase();
                for (String[] rdv : rendezVousList) {
                    if (rdv[0].contains(codeRecherche)) {
                        resultats.add(rdv);
                    }
                }
                break;
            case 2:
                System.out.print("Entrez le nom du client (ou une partie): ");
                String nomRecherche = scanner.nextLine().toUpperCase();
                for (String[] rdv : rendezVousList) {
                    if (rdv[1].contains(nomRecherche) || rdv[2].contains(nomRecherche)) {
                        resultats.add(rdv);
                    }
                }
                break;
            case 3:
                System.out.println("Types de consultation disponibles:");
                for (String[] type : TYPES_CONSULTATION) {
                    System.out.println(type[0] + " - " + type[1]);
                }
                System.out.print("Entrez le code de consultation: ");
                String codeConsultation = scanner.nextLine().toUpperCase();
                for (String[] rdv : rendezVousList) {
                    if (rdv[4].equals(codeConsultation)) {
                        resultats.add(rdv);
                    }
                }
                break;
            case 4:
                System.out.print("Entrez la date (JJ/MM/AAAA): ");
                String dateRecherche = scanner.nextLine();
                try {
                    String dateStr = dateRecherche.substring(6, 10) + dateRecherche.substring(3, 5) + dateRecherche.substring(0, 2);
                    for (String[] rdv : rendezVousList) {
                        if (rdv[5].startsWith(dateStr)) {
                            resultats.add(rdv);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Format de date invalide.");
                    return;
                }
                break;
            default:
                System.out.println("Option invalide.");
                return;
        }
        
        if (resultats.isEmpty()) {
            System.out.println("Aucun rendez-vous trouvé.");
        } else {
            System.out.println("\nRésultats de la recherche:");
            for (String[] rdv : resultats) {
                afficherDetailsRendezVous(rdv);
            }
        }
    }
}