**TACS Grupo 1**
--

 Entrega 1
 -

 La aplicación se encuentra deployada en Heroku en la siguiente url: https://tacs-grupo1.herokuapp.com/.
 
 Para probar los diferentes endpoints utilizar Postman importando el siguiente archivo en el cual se encuentran documentados: [`grupo1-entrega1.postman_collection.json`](postman/entrega1/grupo1-entrega1.postman_collection.json?ts=4). También lo publicamos en su versión web: https://documenter.getpostman.com/view/261545/RWaDXXWX.

 Además hemos utilizado SpringFox (http://springfox.github.io/springfox/) con el cual hemos generado de forma automática la documentación de nuestra API. Dicha documentación swagger se encuentra en el siguiente link: https://tacs-grupo1.herokuapp.com/swagger-ui.html.

 Para ejecutar la aplicación localmente debe ejecutar el siguiente comando:
 
    mvn spring-boot:run

Entrega 2
-
Agregamos datos iniciales para que sea más fácil probar la aplicación. Principalmente dos tipos de usuarios.<br />
<br />
Para el rol USER creamos el siguiente usuario:<br />
Username: user <br />
Password: user<br />
<br />
Para el rol ADMIN creamos el siguiente usuario:<br />
Username: admin <br />
Password: admin<br />
<br />
Ademas agregamos dos listas para cada usuario. El servicio que usamos para incializar se encuentra en `src/main/java/ar/com/tacsutn/grupo1/eventapp/BootstrapData.java` 
<br />
<br />
Nota: Para las alarmas utilizamos una estrategia lazy (hasta que tengamos Telegram y hagamos un llamado diario).
<br />
