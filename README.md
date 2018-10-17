# Travaux Pratiques Initiation à JEE

## Petite Histoire
### Qu'est ce que JEE ?  
Java Entreprise Edition est une spécification pour la plate forme Java initialement détenue par Oracle.

Souhaitant se désengager de cette spécification afin de se concentrer sur le langage et la Java Standard Edition, Oracle 
cède à la fondation Eclipse la spécification qui, suite à un sondage auprès des développeurs, portera le nom de Jakarta 
Entreprise Edition et non plus Java EE.
 
 ### Que Contient JEE
 Comme on l'a dit JEE est une spécification d'un ensemble d'API qui ne sont pas contenues dans le standard JSE.
 
 On trouvera :
 - les servlets
 - la spécification JMS
 - la spécification JPA
 - la spécification JTA
 - la spcification JMX
 - JAXB
 - Java RMI
 - CDI le spring de JEE
 
 Et bien d'autre encore.
 
 ## Stack technique
 Il nous faudra pour ce TP
 - Un environnement de développement avec un support vers le déploiement automatisé vers le serveur JEE que nous utiliserons
 - un JDK au moins java 8 u 161 (dernière version 8 en date au moment de la rédaction de ce support)
 - WildFly 14 
 - maven
 
 ## TP JEE
 ### Questions généralistes sur les applications web java
 Que signifient les extensions suivantes :
 jar ?
 war ?
 ear ?
 
 Quelles différences faites vous entre chaque ?
 Que contient chacune de ces archives ?
 
 ### Questions généralistes sur les conteneurs java
 Citez quelques exemples de serveurs JAVA
 
 Quelles différences faites vous entre chaque ?
 
 Qu'appelle t'on un conteneur léger ?
 
 ### Exercice 1 : Squelette applicatif
 - Cloner le dépot git de ce TP. 
 - Builder le projet avec Maven
 - Créer un environnement d'execution vers Wildfly
 - Déployer l'application sous WildFly
 - Accéder à  votre application via http://localhost:8080/jee
 - Analyser les différences de structure entre chaque archive
 
 Que sont les fichiers application.xml et web.xml ?
 
 ### Exercice 2 : Un additionneur via HTTP
 - Créer un additionneur de deux entiers qui en POST execute la somme des entiers en paramètres et en GET renvoie le 
 dernier résultat qu'il a calculé ou undefined si rien n'est calculé.

### Exercice 3 : L'injection façon JEE avec CDI
#### Qu 'est ce que CDI ?
CDI (Context Dependency Injection) est une spécification destinée à standardiser les injections de dépendances et de 
contextes au sein de la plate forme java et particulièrement JEE.

Le mécanisme d'injection est fait comme pour Spring par l'utilisation d'annotation :
- @Inject permet d'injecter une dépendance (@Autowired de spring)
- @Named correspond au @Qualifier de Spring (doit être présent aussi sur la classe que l'on veut injecter avec le même nom) 
par défaut le nom appliquer est l'équivalent camelCase de la classe.
- Différents scopes : @RequestScoped (scope http), @SessionScoped (lié à la session HTTP) @ApplicationScope (lié à 
l'application) @Dependent (crée une nouvelle instance pour l'injection en cours, équivalent au scope prototype de Spring)

Attention, tous les beans de CDI sont par défaut en scope @Dependent

Pour scanner les beans, il faut définir un fichier XML dans le répertoire META-INF d'un jar ou WEB-INF d'un war
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://xmlns.jcp.org/xml/ns/javaee"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
       http://xmlns.jcp.org/xml/ns/javaee/beans_1_1.xsd"
       bean-discovery-mode="all">
</beans>
```

#### Le soustracteur façon JEE avec CDI
Une calculatrice effectue des opérations arithmétiques qui prennent en paramètre deux opérandes et leur applique un 
opérateur.

Un opération est donc composer de trois élément :
- 2 paramètres
- 1 opérandes.

Son code pourrait ressembler à ça :

```java
public class Calculatrice {
    public static void main(String... args){
        Operateur operateur = new AddOperateur();
        operateur.execute(leftParam, rightParam);
    }
}
```

- ajouter la dépendance javaee-api à votre projet.
- créer un servlet soustracteur au norme CDI.

### Exercice 4 : Les resources JNDI
#### Qu'est ce que JNDI
JNDI est l'acronyme de Java Naming and Directory Interface.

Il s'agit de l'API d'exposition de service à destination des contextes de nommage et d'annuaire.

<img src="https://www.jmdoudoux.fr/java/dej/images/jndi001.png" />  

Pour définir une connexion à une ressource JNDI nous avons besoin de deux choses :
- une fabrique de contexte
- l'url du service à utiliser

Notez que les accès JNDI ne sont pas réservés uniquement aux applications JEE mais aussi à une application standalone.

#### Sémantique
##### Service de nommage
Un service de nommage permet d'associer un nom à une ressource ou une instance d'objet. Cette association est appelée
binding.

Un FS est un service de nommage qui associe un nom à une référence (inode) sur le disque.

##### Annuaires
Un annuaire est une extension au service de nommage en cela qu'il gère en plus des attributs supplémentaires suivant un 
protocole particulier.

Le FS est en plus d'un service de nommage un annuaire car il stoque en plus des attributs pour chaque fichier (date, droits, 
owner etc ...)

##### Contexte
Un contexte est un ensemble de binding. Il est utilisé pour l'accès à un élément contenu dans le service.

Il existe 2 type de contextes :
- le contexte racine
- les sous contexte

Dans le cas du FS, sous linux, "/" est le contexte racine, "home/" est un sous contexte du contexte racine "/"

#### De la théorie à la pratique
##### Configuration d'une datasource embarquée
Configurons une ressource JNDI Simple.

Sous jboss les ressources exportable par le serveur sont sous le context : java:jboss/exported

##### En standalone
Vous aurez besoin de la librairie suivante :
```XML
<!-- https://mvnrepository.com/artifact/org.wildfly/wildfly-naming-client -->
<dependency>
    <groupId>org.wildfly</groupId>
    <artifactId>wildfly-naming-client</artifactId>
    <version>1.0.9.Final</version>
    <scope>test</scope>
</dependency>
```

Maintenant que la ressource est créée, allons la rechercher en appelant le contexte JNDI.

Créer un Main.java qui effectue un contexte lookup via les éléments suivant :
- la classe WildFlyInitialContextFactory
- la classe InitialContext et son interface Context
- le schéma du protocole pour WildFly est "remote+http://"

##### En application JEE
Créer une nouvelle servlet HelloServlet qui injecte une référence JNDI simple et affiche son contenu.

L'injection d'une resource JNDI se fait via l'annotation @Resource

### Exercice 5 : Approche des EJB's

Les EJB sont des composants serveur, en tant que composant ils possèdent certaines caractéristiques comme la réutilisabilité, 
la possibilité de s'assembler pour batir une application etc...

Ils permettent le développement rapide d'objets métiers pour des applications distribuées, sécurisées, transactionnelles
 et portables.

IL existe plusieurs spécifications, la dernière en date est la 3.0

C'est celle que nous utiliserons pour la suite, la spécification 2.1 est obsolète et disparaitra des serveurs d'ici 2020
 des serveurs.
 
 Il existe plusieurs type d'EJB que nous allons détailler.

#### Session : Stateless / Statefull
#### Entity : La persistence en action
#### Message : pourquoi on ne traiterait pas en arrière plan

## Performance et monitoring
### Thread Dump
### Heap Dump
### Monitoring