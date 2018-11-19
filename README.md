[![Build Status](https://travis-ci.com/jonigl/tacs-grupo1-api.svg?branch=master)](https://travis-ci.com/jonigl/tacs-grupo1-api)

**TACS Grupo 1**
--

*App frontend: https://github.com/jonigl/tacs-grupo1-frontend/* 

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

Username: user2 <br />
Password: user2<br />

Para el rol ADMIN creamos el siguiente usuario:<br />
Username: admin <br />
Password: admin<br />
<br />
Ademas agregamos dos listas para cada usuario con el rol USER. El servicio que usamos para incializar se encuentra en `src/main/java/ar/com/tacsutn/grupo1/eventapp/BootstrapData.java` 
<br />

Para las alarmas utilizamos una estrategia lazy (hasta que tengamos Telegram y hagamos un llamado diario).

Nuestra **nueva documentación** está basada en swagger y se puede acceder desde el siguiente link: [`https://tacs-grupo1.herokuapp.com/swagger-ui.html`](https://tacs-grupo1.herokuapp.com/swagger-ui.html) y localmente desde: [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html) .

**Importante:** Hemos deprecado la documentación en postman porque hemos alcanzado el límite impuesto para TEAMS.

Entrega 3
-

El código del front-end se encuentra en el siguiente repositorio: [`https://github.com/jonigl/tacs-grupo1-frontend/`](https://github.com/jonigl/tacs-grupo1-frontend/)

El bot de telegram es el sigueinte: https://t.me/tacs_grupo1_test_bot

Entrega 4
-
Decidimos utilizar Heroku para deployar la aplicación en la nube: https://tacs-grupo1.herokuapp.com

Decidimos pasar de H2 que es una base de datos relacional en memoria a mongoDB porque al ser una base de datos no relacional nos permitiría  a futuro, de ser necesario, escalar horizontalmetne con una buena consistencia de datos y tolerancia a fallos. Aprovechamos el servicio gratuito de https://mlab.com (sandbox).

Entrega 5
-
Con respecto a docker hemos utilizado varias estrategias para utilizar docker:
Dockerfile default para crear a partir del compilado una imagen (la cual ya está subida a docker hub). La app usa una variable de entorno para la base mongo. Se puede correr directamente configurandola para cualquier db remota o usando la local.
Build: 
docker build --no-cache -t jonigl/tacs-grupo1:1.0.0 .
Run:  
https://docs.docker.com/network/host/ (only linux)
docker run --network host --env MONGO_URI=mongodb://localhost/eventapp -p 8080:8080 jonigl/tacs-grupo1:1.0.0

Dockerfiles con multi-stage por ambiente o con varible de entorno, que compila en un paso la aplicación y la salida la agrega al siguiente paso generando una imagen que sólo contiene el compilado. Este métdo lo hemos implementado también para el frontend.

docker-compose default que trae la imagen desde docker hub y levanta un mongodb para deployar la app y poder testearla rápidamente.
Para levantar todo:
docker-compose up -d
Para bajarlo:
docker-compose down

docker-compose con buld que toma el dockerfile y si no fue hecho el build previamente lo genera y levanta la app con un mongo db.
Para hacer el build:
docker-compose build
Para levantar todo y si no está hecho el build lo hace:
docker-compose up -d
Para bajarlo:
docker-compose down

Para agregar a nuestro workflow CI utilizamos travis (que lo aprovechamos con github education pack). Conectamos a travis con nuestro repositorio en github y agregamos el archivo .travis.yml configurado para que corra los tests y se conecte con heroku (https://docs.travis-ci.com/user/deployment/heroku/).
En Heroku teníamos deploy automáticos para master (además de las review apps) por lo que configuramos que los deploy automáticos aguarden a travis para hacer los deploys. En travis configuramos que se genere un job por cada PR o commit a master el cual va correr todos nuestros tests, si corren de manera exitosa permite el deploy en Heroku sino lo saltea. 
Travis url: https://travis-ci.com/jonigl/tacs-grupo1-api

