
/*Vous êtes l’agence « Les Avengers du Dev », vous avez un client qui aimerait avoir pour son site de réservation d’avion, un outil lui permettant de lister tous ses vols avec les informations demandées. Voici sa demande pour plus d’informations

Demande du client :

Bonjour les Avengers,
Nous sommes la compagnie aérienne AIRMESS, et nous aimerions disposer d’un outil qui nous permette de lister nous-mêmes nos propres vols avec les informations suivantes :

•	Départ : Ville / Pays
•	Arrivée : Ville / Pays
•	Date et heure de départ : heures et minutes. Si la date de départ du vol est très proche d’aujourd’hui (moins d’une semaine), le prix devra automatiquement augmenter de 40 %. En revanche, si la date du vol est très éloignée (plus de 6 mois avant la date de départ), le prix devra diminuer de 40 %.
•	Durée du vol : heures et minutes
•	Date et heure d’arrivée : calculées automatiquement à partir de la durée du vol.
•	Nombre de places : minimum 80 places, maximum 200 places. Si le nombre de places est égal ou supérieur à 150, une réduction de 10 % sera appliquée sur le prix initial. Si le nombre de places est inférieur à 100, le prix augmentera de 10 %.
•	Prix initial du vol : défini à la création du vol et ajusté automatiquement en fonction de la date de départ et du nombre de places, comme décrit précédemment.

Une fois les informations saisies, j’aimerais que tous les vols soient affichés. Nous devrions pouvoir créer autant de vols que nécessaire.
Enfin, nous aimerions surtout avoir un système d’authentification pour accéder à cette application. Vous nous fournirez le login et le mot de passe
 */
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Vol {
    public String villeDepart;
    public String paysDepart;
    public String villeArrivee;
    public String paysArrivee;
    public LocalDateTime dateHeureDepart;
    public Duration dureeVol;
    public LocalDateTime dateHeureArrivee;
    public int nombrePlaces;
    public double prixInitial;
    public double prixFinal;
}

class VolUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static Vol creerVol(String villeDepart, String paysDepart,
                               String villeArrivee, String paysArrivee,
                               LocalDateTime dateHeureDepart, Duration dureeVol,
                               int nombrePlaces, double prixInitial) {
        Vol vol = new Vol();
        vol.villeDepart = villeDepart;
        vol.paysDepart = paysDepart;
        vol.villeArrivee = villeArrivee;
        vol.paysArrivee = paysArrivee;
        vol.dateHeureDepart = dateHeureDepart;
        vol.dureeVol = dureeVol;
        vol.nombrePlaces = nombrePlaces;
        vol.prixInitial = prixInitial;

        vol.dateHeureArrivee = calculerDateHeureArrivee(dateHeureDepart, dureeVol);
        vol.prixFinal = calculerPrixFinal(dateHeureDepart, nombrePlaces, prixInitial);

        return vol;
    }

    public static LocalDateTime calculerDateHeureArrivee(LocalDateTime depart, Duration duree) {
        return depart.plus(duree);
    }

    public static double calculerPrixFinal(LocalDateTime dateDepart, int places, double prixInit) {
        double prix = prixInit;
        LocalDateTime maintenant = LocalDateTime.now();
        Duration diff = Duration.between(maintenant, dateDepart);
        long joursAvantDepart = diff.toDays();

        if (!dateDepart.isBefore(maintenant)) {
            if (joursAvantDepart < 7) {
                prix *= 1.4;
            } else if (joursAvantDepart > 180) {
                prix *= 0.6;
            }
        }

        if (places >= 150) {
            prix *= 0.9;
        } else if (places < 100) {
            prix *= 1.1;
        }

        return prix;
    }

    public static void afficherVol(Vol vol) {
        System.out.println("Départ : " + vol.villeDepart + " / " + vol.paysDepart);
        System.out.println("Arrivée : " + vol.villeArrivee + " / " + vol.paysArrivee);
        System.out.println("Date et heure de départ : " + vol.dateHeureDepart.format(formatter));
        System.out.println("Durée du vol : " + vol.dureeVol.toHours() + "h " + (vol.dureeVol.toMinutes() % 60) + "min");
        System.out.println("Date et heure d'arrivée : " + vol.dateHeureArrivee.format(formatter));
        System.out.println("Nombre de places : " + vol.nombrePlaces);
        System.out.printf("Prix initial du vol : %.2f EUR%n", vol.prixInitial);
        System.out.printf("Prix final ajusté : %.2f EUR%n", vol.prixFinal);
        System.out.println();
    }
}

public class Main {
    private static final String LOGIN = "avengers";
    private static final String PASSWORD = "assemble";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!authentification(sc)) {
            System.out.println("Accès refusé.");
            sc.close();
            return;
        }

        List<Vol> listeVols = new ArrayList<>();
        System.out.println("Bienvenue chez Les Avengers du Dev - AIRMESS");

        boolean continuer = true;
        while (continuer) {
            System.out.println("\nMenu :");
            System.out.println("1 - Ajouter un vol");
            System.out.println("2 - Afficher tous les vols");
            System.out.println("3 - Quitter");
            System.out.print("Choix : ");
            int choix = Integer.parseInt(sc.nextLine());

            switch (choix) {
                case 1:
                    Vol vol = saisirVol(sc);
                    if (vol != null) {
                        listeVols.add(vol);
                        System.out.println("Vol ajouté avec succès !");
                    }
                    break;
                case 2:
                    if (listeVols.isEmpty()) {
                        System.out.println("Aucun vol à afficher.");
                    } else {
                        for (Vol v : listeVols) {
                            VolUtils.afficherVol(v);
                        }
                    }
                    break;
                case 3:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }

        System.out.println("Au revoir !");
        sc.close();
    }

    private static boolean authentification(Scanner sc) {
        System.out.println("Veuillez vous authentifier :");
        System.out.print("Login : ");
        String login = sc.nextLine();
        System.out.print("Mot de passe : ");
        String password = sc.nextLine();

        return LOGIN.equals(login) && PASSWORD.equals(password);
    }

    private static Vol saisirVol(Scanner sc) {
        try {
            System.out.print("Ville de départ : ");
            String villeDepart = sc.nextLine();

            System.out.print("Pays de départ : ");
            String paysDepart = sc.nextLine();

            System.out.print("Ville d'arrivée : ");
            String villeArrivee = sc.nextLine();

            System.out.print("Pays d'arrivée : ");
            String paysArrivee = sc.nextLine();

            System.out.print("Date et heure de départ (dd/MM/yyyy HH:mm) : ");
            String dateStr = sc.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime dateHeureDepart = LocalDateTime.parse(dateStr, formatter);

            System.out.print("Durée du vol (heures) : ");
            long heures = Long.parseLong(sc.nextLine());
            System.out.print("Durée du vol (minutes) : ");
            long minutes = Long.parseLong(sc.nextLine());
            Duration dureeVol = Duration.ofHours(heures).plusMinutes(minutes);

            System.out.print("Nombre de places (80 à 200) : ");
            int nombrePlaces = Integer.parseInt(sc.nextLine());
            if (nombrePlaces < 80 || nombrePlaces > 200) {
                System.out.println("Le nombre de places doit être entre 80 et 200.");
                return null;
            }

            System.out.print("Prix initial du vol : ");
            double prixInitial = Double.parseDouble(sc.nextLine());
            if (prixInitial <= 0) {
                System.out.println("Le prix initial doit être positif.");
                return null;
            }

            return VolUtils.creerVol(villeDepart, paysDepart, villeArrivee, paysArrivee,
                    dateHeureDepart, dureeVol, nombrePlaces, prixInitial);

        } catch (Exception e) {
            System.out.println("Erreur lors de la saisie : " + e.getMessage());
            return null;
        }
    }
}