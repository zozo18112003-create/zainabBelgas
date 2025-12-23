package ma.emsi.test;

import ma.emsi.entities.Client;
import ma.emsi.entities.Commande;
import ma.emsi.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe de test pour démontrer les opérations CRUD avec Hibernate ORM.
 */
public class MainTest {

    public static void main(String[] args) {

        System.out.println("=== Démarrage des tests ORM ===\n");

        // Test 1 : Création (Create)
        testCreate();

        // Test 2 : Lecture (Read)
        testRead();

        // Test 3 : Lecture avec relation (Read with Join)
        testReadWithCommandes();

        // Test 4 : Mise à jour (Update)
        testUpdate();

        // Test 5 : Suppression (Delete)
        testDelete();

        // Fermeture propre
        HibernateUtil.shutdown();
        System.out.println("\n=== Fin des tests ===");
    }

    /**
     * Test 1 : Création d'un client et ses commandes
     */
    private static void testCreate() {
        System.out.println("--- Test CREATE ---");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // Création d'un nouveau client
            Client client = new Client("Imane El Amrani", "imane.elamrani@emsi.ma");

            // Création de 2 commandes
            Commande cmd1 = new Commande(LocalDate.now(), new BigDecimal("550.00"));
            Commande cmd2 = new Commande(LocalDate.now().plusDays(2), new BigDecimal("1200.00"));

            // Association bidirectionnelle
            client.addCommande(cmd1);
            client.addCommande(cmd2);

            // Sauvegarde (cascade propagera aux commandes)
            session.save(client);

            tx.commit();
            System.out.println("✓ Client créé avec ID: " + client.getId());

        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            System.err.println("✗ Erreur lors de la création: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Test 2 : Lecture d'un client par ID
     */
    private static void testRead() {
        System.out.println("\n--- Test READ ---");
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // Récupération du client ID=1
            Client client = session.get(Client.class, 1L);

            if (client != null) {
                System.out.println("✓ Client trouvé: " + client);
            } else {
                System.out.println("✗ Aucun client avec ID=1");
            }

        } finally {
            session.close();
        }
    }

    /**
     * Test 3 : Lecture avec chargement des commandes (relation 1:N)
     */
    private static void testReadWithCommandes() {
        System.out.println("\n--- Test READ avec COMMANDES ---");
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // HQL avec JOIN FETCH pour éviter lazy loading exception
            String hql = "FROM Client c LEFT JOIN FETCH c.commandes WHERE c.id = :id";
            Query<Client> query = session.createQuery(hql, Client.class);
            query.setParameter("id", 1L);

            Client client = query.uniqueResult();

            if (client != null) {
                System.out.println("✓ Client: " + client.getNom());
                System.out.println("  Commandes associées:");
                for (Commande cmd : client.getCommandes()) {
                    System.out.println("    - " + cmd);
                }
            }

        } finally {
            session.close();
        }
    }

    /**
     * Test 4 : Mise à jour d'un client
     */
    private static void testUpdate() {
        System.out.println("\n--- Test UPDATE ---");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // Récupération et modification
            Client client = session.get(Client.class, 1L);
            if (client != null) {
                String oldEmail = client.getEmail();
                client.setEmail("nouveau.email@emsi.ma");

                // Pas besoin d'appeler update() : Hibernate détecte les changements
                tx.commit();
                System.out.println("✓ Email modifié: " + oldEmail + " → " + client.getEmail());
            }

        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            System.err.println("✗ Erreur lors de la mise à jour: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Test 5 : Suppression d'une commande
     */
    private static void testDelete() {
        System.out.println("\n--- Test DELETE ---");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // Suppression de la commande ID=1
            Commande commande = session.get(Commande.class, 1L);
            if (commande != null) {
                session.delete(commande);
                tx.commit();
                System.out.println("✓ Commande supprimée: " + commande.getId());
            }

        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            System.err.println("✗ Erreur lors de la suppression: " + e.getMessage());
        } finally {
            session.close();
        }
    }
}
