Forge commands to regenrate the project from database

project-new --named assist --top-level-package br.org.asipeca.assist --final-name assist --version 1.0 --stack JAVA_EE_7
jpa-setup --provider Hibernate --db-type MYSQL --data-source-name java:jboss/ds/assist

-- Mac / Linux
jpa-generate-entities-from-tables --target-package br.org.asipeca.assist.model \
 --hibernate-dialect org.hibernate.dialect.MySQL5InnoDBDialect \
 --driver-class com.mysql.cj.jdbc.Driver \
 --driver-location /Users/murilotuvani/Documents/Java/mysql-connector-java-8.0.12/mysql-connector-java-8.0.12.jar \
 --user-name root --user-password root \
 --jdbc-url jdbc:mysql://localhost/asipeca --database-tables * ;

-- Windows
You'll need to change the --driver-location to something like this T:/Java/mysql-connector-java-5.1.42/mysql-connector-java-5.1.42-bin.jar.

 
faces-setup
scaffold-generate --provider Faces --targets br.org.asipeca.assist.model.*
as-setup --server wildfly  --version 13.0.0.Final