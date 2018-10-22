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
 - un JDK au moins java 8 u 191 (dernière version 8 en date au moment de la rédaction de ce support)
 - WildFly 14 
 - maven
 
 ## TP JEE
 ### Questions généralistes sur les archives java
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

Ici nous allons utiliser les Servlets en version 4.0.1.

Tout peut se faire en annotation, regardez l'annotation @WebServlet

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

Une opération est donc composer de trois éléments :
- 2 paramètres
- 1 opérandes.

Son code pourrait ressembler à ça :

```java
public class Calculatrice {
    public static void main(String... args){
        Operation operation = new AddOperation();
        operateur.execute(leftParam, rightParam);
    }
}
```

- ajouter la dépendance javaee-api à votre projet.
```xml
<!-- https://mvnrepository.com/artifact/javax/javaee-api -->
        <dependency>
            <groupId>javax</groupId>
            <artifactId>javaee-api</artifactId>
            <version>8.0</version>
            <scope>provided</scope>
        </dependency>
```
- créer un servlet soustracteur au norme CDI.
- pourquoi la dépendance est en scope provided ? et non en compile ?

### Exercice 4 : Les resources JNDI
#### Qu'est ce que JNDI
JNDI est l'acronyme de Java Naming and Directory Interface.

Il s'agit de l'API d'exposition de service à destination des contextes de nommage et d'annuaire.

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

IL existe plusieurs spécifications, la dernière en date est la 3.1

C'est celle que nous utiliserons pour la suite, la spécification 2.1 est obsolète et disparaitra d'ici 2020
 des serveurs.
 
 Il existe plusieurs type d'EJB que nous allons détailler.

#### Les EJB au sein d'une application
Les EJB font partie d'un module à part ayant un packaging ejb.

Ils sont introduit au sein de l'ear par une directive ejbModule.

En fonction des serveurs, il est peut etre nécessaire d'adjoindre dans le META-INF du module un fichier xml de description
appelé descripteur de déploiement. Voici quelques exemples :
- ejb-jar.xml : le descripteur par défaut, équivalent au web.xml ou l'application.xml des war et ear
- weblogic-ejb-jar.xml : le descripteur propre à weblogic
- jboss-ejb-jar.xml : celui de jboss
- etc ...

A vous de voir en fonction des infrastructures.

#### Session : Le service au client
Les EJB Session représentent le type d'EJB le plus utilisé. Ils fournissent un service au client.

2 annotations définissent leur protocole de contact :
- @Local
- @Remote

2 annotations définissent leur états :
- @Stateless
- @Statefull

Ils représentent le point d'entrée de la couche métier.

Quelles différences faites vous entre ces annotations ?

Pour définir un EJB session nous avons besoin :
- d'un contrat, une interface dépendante d'un protocole afin d'être appelé
- d'une implémentation définissant le type d'EJB qui nous intéresse.

Les deux peuvent être liés au sein d'un objet, l'interface etant plus simple pour l'exposition aux clients pour définir
les appels sans exposer les implémentations.

##### Le session Stateless
- Créez un module ejb au sein du projet, regarder les types de packaging maven.
- Ajoutez un EJB effectuant le produit de deux entiers.
- Intégrez ce module dans votre application
- Déployez la

Une fois déployez appeler votre EJB.

pour l'appeler vous aurez besoin de la dépendence suivante :
```xml
<!-- https://mvnrepository.com/artifact/org.wildfly.wildfly-http-client/wildfly-http-ejb-client -->
<dependency>
    <groupId>org.wildfly.wildfly-http-client</groupId>
    <artifactId>wildfly-http-ejb-client</artifactId>
    <version>1.0.12.Final</version>
    <scope>test</scope>
</dependency>
```

##### Le session Statefull
Creer un EJB Statefull au sein de votre module.

Cet ejb sera voué à tracer un historique des calculs effectués par votre calculatrice. Il possèdera deux méthodes :
- une méthode historiser prenant en parametre l'opération et son résultat et l'archivant
- une méthode history(int n) : qui renvoie l'historique n ou si n <= 0 l'historique complet

Modifier votre code en conséquence pour historiser vos calculs.

Un ejb peut s'injecter via l'annotation @EJB.

Démarrer en mode debug si ce n'est fait

Que constatez vous ?

Que faudrait il pour unifier l'historique ? 

#### Entity : La persistance en action

Un EJB Entity est un ejb qui permet de se mapper sur un modèle relationnel de base de données.

A quoi cela vous fait il penser ?

De quoi aurez vous besoin pour persister votre historique ?

Créer un EJB Entity qui modélise votre historique en base de données.
Modifier votre EJB historique pour persiter vos opérations
Ajouter une servlet qui en fonction d'un get ou d'un post renvoie l'intégralité, ou persiste en base, votre historique.

#### Message : pourquoi on ne traiterait pas en arrière plan

Là où les EJB Session effectuent un travail synchrone par des appels distants de méthodes, JMS (Java Message Service) 
permet d'ajouter de l'asynchronisme dans les traitements d'applications distribuées.

En effet, grace au Message Driven Bean (typiquement appelé EJB Message ou Listener JMS), ces EJB
se connectent à une Queue qui stoque les messages, il est de la responsabilité du serveur de distribuer aux instances 
de MDB les messages à traiter.

##### Passage par la case configuration.
Avant de traiter un message, il faut créer une Queue.

Sous windows editez le fichier wildfly-14.0.1.Final\bin\standalone.conf.bat
Ajouter la directive set "JAVA_OPTS=%JAVA_OPTS% -Djboss.server.default.config=standalone-full.xml"

Sous linux, la même opération est possible via standalone.conf

Créer une Queue dans le serveur JMS par défaut de WildFly.

Le code suivant permet d'envoyer un message dans une file distante.

Sous wildfly il faut un utilisateur applicatif ayant les droits de poster sur une file JMS. *(attention ceci n'est pas 
valable pour tous les serveurs JEE)*

Créer un utilisateur avec le script add-user et mettez le dans le group guest

```java

public class Test {

       public void testMessageDriven() throws Exception{
       
               Hashtable<String,String> env = new Hashtable<>();
               env.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
               env.put(Context.PROVIDER_URL,"remote+http://localhost:8080");
               Context context = new InitialContext(env);
               ConnectionFactory factory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
               Connection connection = factory.createConnection(appUserName, appUserPass);
               Queue queue = (Queue) context.lookup("jms/queue/Diviseur");
               Session session = connection.createSession();
               MessageProducer messageProducer = session.createProducer(queue);
               ObjectMessage message = session.createObjectMessage();
               message.setObject(new Operation(8L,4L,'/',-1L));
               messageProducer.send(message);
               messageProducer.close();
               session.close();
               connection.close();
           }
        
}
```

ActiveMq est le moteur JMS utilisé sous WildFly, pour pouvoir vous y connecter vous aurez besoin de la dépendance

```xml
<!-- https://mvnrepository.com/artifact/org.apache.activemq/artemis-jms-client -->
<dependency>
    <groupId>org.apache.activemq</groupId>
    <artifactId>artemis-jms-client</artifactId>
    <version>2.6.3</version>
    <scope>test</scope>
</dependency>


```

*Notez que sous Jboss EAP, il s'agit de HornetMQ, Weblogic possède sa propre implémentation, etc.*  

Envoyer votre message et consulter son arrivée dans la console de WildFly

Créer un MessageDrivenBean traitant les divisions sur votre calculteur.

Sa signature est la suivante 
```java
@MessageDriven(
    name = "DiviseurMessageEJB",
        description = "EJB Message",
        activationConfig = {
                @ActivationConfigProperty( propertyName = "destinationType",
                        propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty( propertyName = "destination",
                        propertyValue ="jms/queue/Diviseur")
        }

)
public class DiviseurMessageBean implements MessageListener {
    
}
``` 

Vérifier la consommation de vos messages par votre servlet d'historique

#### La particularité du XA et du 2 phase Commit

Certaines sources de données peuvent être déployée sous un driver nommé XA Driver.

Ces drivers sont particuliers au transactions distribuées faisant intervenir des composants nécessitant la mise à jour 
de sources de données différentes au sein d'un même contexte.

Tout ceci gérer sous la spécification JTA et JTS ()Java Transaction APi et Services)

Le 2 phases commit permet de préparer le commit global de transaction avant que cette derniere ne soit totalement close 
et que les sources de données soit réellement commité.

Si la première phase de commit échoue (commit qualifié de préparatoire) alors un rollback global est effectué 
sur la transaction.

## Performance et monitoring
### La JVM et le Garbage Collector

### La mémoire

### Amusons nous avec notre calculatrice