# Projet API Spring Boot pour la Gestion des Utilisateurs

Ce projet est une API REST développée en utilisant Spring Boot qui permet de :
- Enregistrer un utilisateur.
- Récupérer les détails d'un utilisateur enregistré.

Seuls les résidents adultes de France sont autorisés à s'enregistrer.

## Prérequis
- Java 11 ou supérieur
- Maven
## Installation
1. Clonez le dépôt :
   ```bash
   git clone <URL_DU_REPOSITORY>
   ```
2. Naviguez vers le dossier du projet :
   ```bash
   cd user-api
   ```
3. Construisez le projet avec Maven :
   ```bash
   mvn clean install
   ```
## Exécution
Pour exécuter l'application, utilisez la commande suivante :
```bash
mvn spring-boot:run
```
L'API sera accessible sur `http://localhost:8080`.

## Endpoints de l'API
1. **Enregistrer un utilisateur** :
    - **URL** : `/api/users`
    - **Méthode** : POST
    - **Corps de la requête** :
      ```json
      {
        "userName": "Jean Dupont",
        "birthDate": "2000-05-15",
        "countryOfResidence": "France",
        "phoneNumber": "0612345679",
        "gender": "homme"
      }
      ```
    - **Réponse** : 201 Created si l'utilisateur est correctement enregistré, 400 Bad Request si la validation échoue.

2. **Récupérer les détails d'un utilisateur** :
    - **URL** : `/api/users/{id}`
    - **Méthode** : GET
    - **Réponse** : 200 OK avec les détails de l'utilisateur, 404 Not Found si l'utilisateur n'existe pas.
## Console H2
Une base de données H2 embarquée est utilisée pour stocker les données des utilisateurs.
Pour accéder à la console H2, rendez-vous sur : `http://localhost:8080/h2-console`.

- **URL JDBC** : `jdbc:h2:mem:testdb`
- **Nom d'utilisateur** : `sa`
- **Mot de passe** : 

## Tests
Des tests unitaires et d'intégration sont inclus pour garantir le bon fonctionnement de l'API.
Pour exécuter les tests, utilisez la commande suivante :
```bash
mvn test
```

## Postman Collection
Une collection Postman est disponible pour tester les différents endpoints de l'API.
Vous pouvez trouver la collection dans le dossier `/postman/UserAPI_Postman_Collection.json`.

Importez la collection dans Postman et utilisez les requêtes disponibles pour interagir avec l'API.

