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
 - Créer un additionneur de deux entiers qui appelait en POST execute la somme des entiers en paramètres et en GET renvoie le dernier résultat qu'il a calculé.
  
 