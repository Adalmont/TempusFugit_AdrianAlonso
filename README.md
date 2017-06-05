25/04/2017: Creada la seccion de Contacto - Comenzada la seccion de Oferta - El campo "ciudad" en Usuario ahora es un objeto de tipo Ciudad ||||
02/05/2017: Añadida la validacion de contraseña al registro de usuarios - creadas las paginas para listar las ofertas y visualizar ofertas individuales - Creada una seccion
en el perfil de usuario para crear ofertas; Aun no funcional por un error de Hibernate que no he identificado aun. La seccion de mensajes va a ir en la pagina de ofertas individuales,
donde mostrara los mensajes pertinentes a cada oferta, aunque aun no esta implementada debido a los problemas al añadir ofertas ||||
09/05/2017: Creada la seccion de ofertas. Se pueden añadir ofertas desde el perfil de usuario y visualizarlas en la lista de ofertas. En la lista se puede seleccionar
una oferta concreta para visualizar mas informacion sobre ella - en la pagina de cada oferta se ha añadido una seccion de mensajes, que permite ver los mensajes dirigidos a esa
oferta en concreto. Los mensajes privados solo pueden visualizarse por el creador de la oferta y el escritor del mensaje. La creacion de mensajes desde la pagina (con el boton 
"crear mensaje") aun no es funcional. |||| 16/05/2017: Seccion de mensajes funcional. Implementada la seccion del buscador (Solo pueden acceder los usuarios registrados). Seccion
multimedia aun en construccion. |||| 23/05/2017: Subida de Imagenes implementada. Ya se pueden subir avatares para los usuarios e imagenes para las ofertas. Contraseñas encriptadas.
Implementados los codigos promocionales. Contratacion de aun ofertas en construccion. Añado documentos de texto con los usuarios y como hacer funcionar la subida de imagenes.
|||| 30/05/2017: Modifica la creacion de ofertas: Ahora la gestion de ofertas cuenta con su propia seccion dentro del perfil de usuario (en construccion, solo funcional la creacion
de ofertas). Modificada la creacion de ofertas: ahora se debe elegir un horario al crear la oferta (De momento funcional solo la opcion de Horario Unico). Ahora los horarios se muestran
en la pagina de la oferta individual, pero la contratacion de horarios aun no funciona correctamente. Detectado un error al crear la oferta (El mes de la fecha siempre es enero).||||
06/06/2017: Finalizada la contratacion de ofertas. En el perfil de usuario se ha creado una seccion donde se podran gestionar ofertas, incluyendo crear ofertas nuevas, aceptar o rechazar las
solicitudes de servicios, comprobar la lista de servicios aceptados a realizar y comprobar los servicios contratados (e indicar cuando se ha finalizado el servicio). Un usuario podra
contratar horarios de servicios desde la pagina de la oferta, siempre que disponga de fondos suficentes. Al contratar el servicio se sustraen los fondos al cliente, pero el ofertante
no los recibe hasta que el cliente indique que el servicio ha sido realizado. Si el ofertante rechaza el servicio, el cliente es reembolsado.
