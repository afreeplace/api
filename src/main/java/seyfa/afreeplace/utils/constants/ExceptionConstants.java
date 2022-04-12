package seyfa.afreeplace.utils.constants;

public class ExceptionConstants {

    public static String userNotFound() {
        return "L'utilisateur demandé n'existe pas";
    }

    public static String tradeNotFound() {
        return "Le trade demandé n'existe pas";
    }

    public static String addressNotFound() { return "L'adresse spécifiée est inexistante"; }
    public static String photoNotFound() { return "La photo spécifiée est inexistante"; }
    public static String categoryNotFound() { return "La categorie spécifiée est inexistante"; }
    public static String tagNotFound() { return "Le tag spécifié est inexistante"; }
    public static String formErrors() {
        return "Erreur de formulaire, veuillez vérifier vos données";
    }
    public static String passwordNotMatchs() {
        return "Les deux mots de passe ne correspondent pas.";
    }
    public static String emailAlreadyExists() {
        return "L'adress email existe déjà'.";
    }
    public static String badAvailability() {
        return "Veuillez saisir des heures valides.";
    }
    public static String dataNotFound() { return "Les données demandées ne sont pas existantes."; }
    public static String badHoursSet() { return "Les heures saisies ne sont pas valides."; }

}
