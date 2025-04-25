package process;

import mobile.Animal;
import mobile.Cat;
import mobile.Dog;

public class UserActionManager {

    // Récompenser le chat avec une friandise
    public void recompenseChatAvecFriandise(Animal chat) {
        if (chat instanceof Cat) {
            // Code pour récompenser le chat (par exemple, ajouter un événement dans le journal)
            System.out.println("Le chat reçoit une friandise.");
        }
    }

    // Récompenser le chien avec une friandise
    public void recompenseChienAvecFriandise(Animal chien) {
        if (chien instanceof Dog) {
            // Code pour récompenser le chien (par exemple, ajouter un événement dans le journal)
            System.out.println("Le chien reçoit une friandise.");
        }
    }

    // Réprimander verbalement le chat : dire "Non!"
    public void reprimandeChatVerbalement(Animal chat) {
        if (chat instanceof Cat) {
            // Code pour réprimander le chat
            System.out.println("Non! Le chat est réprimandé.");
        }
    }

    // Réprimander verbalement le chien : dire "Non!"
    public void reprimandeChienVerbalement(Animal chien) {
        if (chien instanceof Dog) {
            // Code pour réprimander le chien
            System.out.println("Non! Le chien est réprimandé.");
        }
    }

    // Prendre le chat dans ses bras
    public void prendreChatDansSesBras(Animal chat) {
        if (chat instanceof Cat) {
            // Code pour prendre le chat dans les bras
            System.out.println("Le chat est pris dans les bras.");
        }
    }

    // Récompenser le chien par des caresses
    public void recompenseChienParCaresses(Animal chien) {
        if (chien instanceof Dog) {
            // Code pour récompenser le chien par des caresses
            System.out.println("Le chien reçoit des caresses.");
        }
    }

    // Utiliser un pointeur laser
    public void utiliserPointeurLaser(Animal chat) {
        if (chat instanceof Cat) {
            // Code pour utiliser un pointeur laser avec le chat
            System.out.println("Le chat joue avec un pointeur laser.");
        }
    }

    // Demander au chien de s'asseoir
    public void demanderChienDeSAsseoir(Animal chien) {
        if (chien instanceof Dog) {
            // Code pour demander au chien de s'asseoir
            System.out.println("Le chien s'assoit.");
        }
    }

    // Placer un objet attirant pour le chat pour l'inciter à jouer à un endroit précis
    public void placerObjetAttirantPourChat(Animal chat, String emplacement) {
        if (chat instanceof Cat) {
            // Code pour placer un objet attirant pour le chat à un endroit spécifique
            System.out.println("L'objet attirant est placé à " + emplacement + " pour le chat.");
        }
    }

    // Demander au chien de donner la patte
    public void demanderChienDeDonnerLaPatte(Animal chien) {
        if (chien instanceof Dog) {
            // Code pour demander au chien de donner la patte
            System.out.println("Le chien donne la patte.");
        }
    }
}
