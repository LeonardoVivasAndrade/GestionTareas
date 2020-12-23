**Proyecto Web TareasUFPS**

Este proyecto se realiza como trabajo final de la materia Computacion en la Nube correspondiente al Pensum 115 del programa Ingeniería de Sistemas de la Universidad Francisco de Paula Santander.

***Desarrollo***
- Angel Leonardo Vivas Andrade
GitLab: https://gitlab.com/angelleonardovian

- Ana Gabriela Cardenas Omaña
GitLab: https://gitlab.com/anagabrielacaom 

_Estudiantes 8vo. semestre Ingeniería de Sistemas | 2020_



***Lenguaje de Programación***
La aplicación esta desarrollada en lenguaje Java, un patrón de arquitectura de software MVC, como gestor de persistencia utiliza Eclipse Link JPA.


***Funcionalidad***
Se trata de una aplicación web para compartir documentos alojados en Google Drive. 


***Autorizados***
Los usuarios pertenecientes al dominio @ufps.edu.co son los unicos autorizados para iniciar sesion y compartir archivos. La visualización de archivos compartidos es público.


***Herramientas de desarrollo***
- Api Google Drive
- Google Picker
- Google OAuth
- Tomcat 9
- Java 11
- Nginx
- MariaDB
- EclipseLink

***Persistencia***
En la base de datos no se almacenan archivos, no se descarga documentos de google drive para el usuario ni en el servidor. Solo se obtiene url de visualizacion del archivo alojado en google Drive. La base de datos desarrollada en MySQL, el archivo Script se encuentra en el directorio /Database.


