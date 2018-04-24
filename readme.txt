Installation de notre application
=================================

0) Prérequis
	Une installation de JDK 7 ou plus fonctionnelle;
	Une installation du SGBD Oracle
	Une installation de Netbeans EE 8.1 ou plus fonctionnelle, avec l'option serveur Tomcat cochée lors de l'installation (ou une installation manuelle et correctement configurée dans Netbeans)

	NB: Le SGBD Oracle peut-être remplacé par un autre SBGD ayant un pilote JDBC fonctionnel. Cependant nous ne pourrions pas vous apporter une garantie de résultat et nous vous déconseillons cette option, à moins que vous soyez certain de ce que vous faîtes et en accepter les conséquences.
	NB: Netbeans est ici utilisé par commodité. Bien qu'un administrateur expert peut utiliser directement Maven ou un autre IDE, nous utiliserons Netbeans en raison de sa facilité d'installation.

1) Configuration de la base de données
	Exécutez le script SQL/deploy.sql sur la base de données du serveur
	En cas de problème, utilisez la base de données de Maxime Gourgoulhon:
		nom d'utilisateur: gourgoum
		mot de passe: gourgoum

2) Configuration de l'application
	Dans le répertoire src/main/webapp/META-INF:
		- Copiez context_sample.xml vers context.xml
		- Dans context.xml, complétez les champs:
			username
			passward
			url
	Dans le fichier ./ACVL/src/main/java/ensimag/acvl/config/Config.java:
		- Remplacez les prix de la cantine et de la garderie, en remplaçant les "1f" par les prix au format "X.Xf".
		- Garderie0 est la garderie du matin, les trois autres garderies sont celles du soir, par ordre chronologique.

3) Configuration de Netbeans
Ces étapes ne sont à réaliser que si votre installation de Netbeans n'est pas reliée à votre base de données.
	3.1) Oracle JDBC
		Vous aurez besoin de télécharger le pilote JBDC d'Oracle à l'adresse suivante: http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html

		3.1.1)
			Dans le menu Windows, appuyez sur Services.
			Dans l'onglet services, sélectionnez Databases, puis Driver.
			Faîtes un clic droit sur Oracle Thin, puis customize.
			Dans Driver File(s), rajouter le fichier ojdbc?.jar téléchargé.

		3.1.2)
			Dans le menu Tools choisissez Librairies et ajoutez le fichier jar téléchargé dans le Classpath.

	3.2) Connexion à la base de données dans Netbeans
		Dans le menu Windows, appuyez sur Services.
		Dans l'onglet services, sélectionnez Databases et choisissez New Connection
		Choisissez alors le driver Oracle Thin et complétez les champs en accord avec la configuration de votre serveur Oracle.

4) Compilation de l'application
	Dans File, sélectionnez "Open project".
	Dans l'explorateur, sélectionnez le dossier ACVL et validez
	Appuyez sur la touche F6.
	Votre navigateur devrait se lancer avec notre application.

5) Création du compte administrateur
	Il suffit de faire un compte nouveau compte dont l'identifiant est "admin"
	En vous connectant sur ce compte, vous pouvez ensuite accéder au panneau d'administration

6) Débogage
	- La moulinette est automatiquement appelée toutes les heures, afin d'attribuer les places aux élèves.
	Pour la désactiver et passer la moulinette en mode manuel, il faut se rendre dans le panneau d'administration, puis dans l'onglet débogage.
	- Ce même menu permet de changer la date de l'application sans changer l'horloge du système
	- Ce même menu permet aussi d'afficher des informations de débogages qui se révèlent utiles pour le développement
