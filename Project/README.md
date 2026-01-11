# Rapport de Projet : Système de Gestion Hôtelière (HMS)
## 4IIR EMSI 2025-2026

---

## 1. Page de Garde
*   **Établissement :** EMSI (École Marocaine des Sciences de l'Ingénieur)
*   **Titre du projet :** Hotel Management System (HMS) - Grand Hotel
*   **Intitulé du module :** Java Avancé / Programmation Orientée Objet (JEE)
*   **Réalisé par :** Zainab Belgas
*   **Encadré par :** Pr. Abderrahim Larhlimi
*   **Année Universitaire :** 2025-2026

---

## 2. Remerciements
Nous tenons à remercier notre professeur pour son encadrement pédagogique et ses conseils précieux tout au long de ce module de Java Avancé. Nos remerciements vont également à l'administration de l'EMSI pour les moyens techniques mis à notre disposition, ainsi qu'à toutes les personnes ayant contribué de près ou de loin à la réussite de ce projet.

---

## 3. Table des Matières
1.  Introduction Générale
2.  Partie I : Analyse et Conception
3.  Partie II : Environnement Technique
4.  Partie III : Architecture et Implémentation
5.  Partie IV : Interface Utilisateur
6.  Conclusion et Perspectives
7.  Webographie

---

## 4. Introduction Générale
*   **Contexte du projet :** Le secteur hôtelier exige aujourd'hui une réactivité et une précision sans faille. La gestion traditionnelle (papier ou Excel) atteint ses limites face aux exigences de réservation en temps réel et de suivi client.
*   **Problématique :** Comment centraliser la gestion des chambres, des clients, des factures et du personnel dans une solution logicielle unique, robuste et évolutive, tout en offrant une interface moderne ?
*   **Objectifs :**
    1.  Gérer le cycle de vie complet d'une réservation (Check-in / Check-out).
    2.  Assurer la persistance des données (Clients, Chambres, Factures).
    3.  Offrir une interface d'administration sécurisée.
    4.  Proposer une interface client "Front-office" attractive pour la réservation.

---

## 5. Partie I : Analyse et Conception

### 5.1 Spécification des besoins
*   **Besoins Fonctionnels :**
    *   Gestion CRUD (Create, Read, Update, Delete) des Clients et Chambres.
    *   Calcul automatique des factures selon la durée du séjour.
    *   Authentification sécurisée pour l'administrateur.
    *   Visualisation de l'historique et des disponibilités en temps réel.
*   **Besoins Non-Fonctionnels :**
    *   Architecture en couches (MVC/DAO) pour la maintenabilité.
    *   Utilisation de l'ORM (Hibernate) pour s'abstraire du SQL brut.
    *   Interface Responsive (Bootstrap 5) pour une consultation sur tout support.

### 5.2 Conception UML
*   **Diagramme de Classes :** (Voir Annexe `class_diagram.md`)
    *   Nos entités principales sont `Room`, `Customer`, `Bill`, `FoodItem`.
    *   Relations clés :
        *   `Customer` **1--*** `Bill` (Un client a plusieurs factures).
        *   `Room` **1--*** `Customer` (Une chambre accueille plusieurs clients dans le temps).
        *   `Customer` **\***--*** `FoodItem` (Relation Many-to-Many).

### 5.3 Conception de la Base de Données
Le projet utilise une approche **"Code First"** avec Hibernate. Les tables sont générées automatiquement à partir des classes Java.
*   **Tables principales :** `customers`, `rooms`, `bills`, `inventory`.
*   **Exemple de Structure (Table `rooms`) :**
    *   `room_no` (PK, int)
    *   `available` (boolean)
    *   `location` (varchar)

---

## 6. Partie II : Environnement Technique

*   **Langage :** Java 17 (LTS).
*   **Build Automation :** Apache Maven 3.9 (Gestion des dépendances et du cycle de vie).
*   **ORM :** Hibernate Core 6.4.0 (Implémentation de JPA).
*   **Base de Données :** H2 Database (Mode "In-Memory" pour le développement et la rapidité).
*   **Frontend :** HTML5, CSS3 (Glassmorphism), JavaScript (ES6), Bootstrap 5.3.
*   **IDE :** Visual Studio Code / IntelliJ IDEA.

**Extrait du `pom.xml` :**
```xml
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>6.4.0.Final</version>
</dependency>
```

---

## 7. Partie III : Architecture et Implémentation

### 7.1 Architecture logicielle
Le projet respecte une architecture en couches stricte pour séparer les responsabilités :

1.  **`com.hotelmanagement.entity`** : Les POJO annotés avec `@Entity` (JPA) représentant les tables.
2.  **`com.hotelmanagement.dao`** : Interfaces et implémentations pour l'accès aux données (Pattern DAO).
3.  **`com.hotelmanagement.service`** : Logique métier (ex: filtres, calculs).
4.  **`com.hotelmanagement.util`** : Gestion de la `SessionFactory` Hibernate.
5.  **`frontend/`** : L'interface utilisateur Web.

### 7.2 Design Patterns Utilisés
*   **DAO (Data Access Object) :** Nous avons créé une interface générique `GenericDAO<T>` pour uniformiser les opérations CRUD sur toutes les entités (`save`, `update`, `delete`, `findById`).
*   **Singleton :** Utilisé dans la classe `HibernateUtil` pour garantir qu'une seule instance de `SessionFactory` est créée pendant toute la durée de vie de l'application, économisant ainsi les ressources.

### 7.3 Extraits de Code Clés
**Gestion générique des données (`GenericDAO`) :**
Ce code permet de réutiliser la logique d'accès aux données pour n'importe quelle entité.
```java
// Exemple d'abstraction pour éviter la répétition de code
public interface GenericDAO<T> {
    void save(T entity);
    T findById(int id);
    List<T> findAll();
}
```

**Configuration Hibernate (`hibernate.cfg.xml`) :**
```xml
<!-- Configuration H2 et Auto-update du schéma -->
<property name="dialect">org.hibernate.dialect.H2Dialect</property>
<property name="hbm2ddl.auto">update</property>
```

---

## 8. Partie IV : Interface Utilisateur

### 8.1 Présentation
L'interface a été conçue autour du thème **"Luxe & Élégance"** (Beige/Marron) avec des technologies Web modernes.

1.  **Page d'Accueil (Landing Page) :** Présente les chambres disponibles avec un carrousel et une barre de recherche en temps réel.
2.  **Dashboard Admin :** Sécurisé par code, il permet de visualiser les statistiques (Revenu, Taux d'occupation) et de gérer les nouvelles réservations.
3.  **Système de Réservation :** Une modale interactive permet au client de choisir ses dates, calculant automatiquement le prix total du séjour.

*(Espace réservé pour vos captures d'écran : Insérez ici des images de `index.html` et `admin.html`)*

### 8.2 Scénarios de Test
*   **Test Nominal :** Un utilisateur cherche une "Suite", clique sur "Réserver", saisit ses dates. Le prix se met à jour. Après validation, l'admin voit la réservation en statut "Pending".
*   **Test Erreur :** Tentative de connexion Admin avec un mauvais code -> Acces refusé.

---

## 9. Conclusion et Perspectives
Ce projet nous a permis de mettre en pratique les concepts avancés de Java (JPA/Hibernate) et de comprendre l'importance d'une architecture logicielle propre. Nous avons réussi à livrer une application fonctionnelle couvrant le back-end et le front-end.

**Perspectives :**
*   Connecter le Front-end (JS) au Back-end (Java) via une **API REST** (Spring Boot).
*   Ajouter le paiement en ligne (Stripe).
*   Générer les factures en PDF (iText).

---

## 10. Webographie
*   Documentation Hibernate : [hibernate.org](https://hibernate.org/orm/documentation/6.0/)
*   Documentation Bootstrap 5 : [getbootstrap.com](https://getbootstrap.com)
*   Cours Java Avancé EMSI.
