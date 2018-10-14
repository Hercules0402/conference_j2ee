# Travaux Pratiques Initiation à JEE

## Petite Histoire
### Qu'est ce que JEE ?  
Java Entreprise Edition est une spécification pour la plate forme Java initialement détenue par Oracle.

Souhaitant se désengager de cette spécification afin de se concentrer sur le langage et la Java Standard Edition, Oracle cède à la fondation Eclipse la spécification qui suite à un sondage auprès des développeurs portera le nom de Jakarta Eentrprise Edition et non plus Java EE.
 
 ### Que Contient JEE
 Comme on l'a dit JEE est un ensemble d'API qui ne sont pas contenu dans le standard JSE.
 
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
 - Un environnement de développement : eclipse, netbeans, intellij avec un support vers le déploiement automatisé vers le serveur JEE que nous utiliserons
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
 
 ### Exercice 1 : Squelette applicatif
 - Créer une application jee-with-ejb from scratch avec maven.
 L'application devra etre un ear contenant à minima un war avec une JSP index.jsp "hello world" dont le contexte racine d'appel sera /jee
 - Créer un environnement d'execution vers Wildfly
 - Déployer l'application sous WildFly
 - Accéder à  votre application via http://localhost:8080/jee
 - Analyser les différences de structure entre chaque archive
 
 ### Exercice 2 : Un additionneur via HTTP
 - Créer un additionneur de deux entiers qui en POST execute la somme des entiers en paramètres et en GET renvoie le dernier résultat qu'il a calculé ou undefined si rien n'est calculé.

### Exercice 3 : L'injection façon JEE avec CDI
#### Qu 'est ce que CDI ?
CDI (Context Dependency Injection) est une spécification destinée à standardiser les injections de dépendances et de contextes au sein de la plate forme java et particulièrement JEE.

Le mécanisme d'injection est fait comme pour Spring par l'utilisation d'annotation :
- @Inject permet d'injecter une dépendance (@Autowired de spring)
- @Named correspond au @Qualifier de Spring (doit être présent aussi sur la classe que l'on veut injecter avec le même nom) par défaut le nom appliquer est l'équivalent camelCase de la classe.
- Différents scopes : @RequestScoped (scope http), @SessionScoped (lié à la session HTTP) @ApplicationScope (lié à l'application) @Dependent (crée une nouvelle instance pour l'injection en cours, équivalent au scope prototype de Spring)

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
Une calculatrice effectue des opérations arithmétiques qui prennent en paramètre deux opérandes et leur applique un opérateur.

Un opération est donc composer de trois élément :
- 2 paramètres
- 1 opérandes.

Son code pourrait ressembler à ça :

```java
public class Calculatrice {
    public static void main(String... args){
        Operande operande = new AddOperande();
        operande.execute(leftParam, rightParam);
    }
}
```
