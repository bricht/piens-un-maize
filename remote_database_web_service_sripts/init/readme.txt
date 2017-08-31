Created by Guntars Berzins 2017.08.28

.htaccess  	<< deny access to these files from outside.

login_url.ini 	<< is used by all php sripts who needs connection
		to database. It is used to resolve where are located actual login infromation

mysql_login.ini << this stores actual database login infromation. It should NEVER be
		available for access publicitly. This file location should be refered into 
		'login_url.ini' file. 

