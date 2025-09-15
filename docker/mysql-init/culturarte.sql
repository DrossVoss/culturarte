-- culturarte.sql (v4 fixed)
-- Versión corregida a partir de culturarte_implementado_v4.zip
-- - Sin bloques comentados que impidan la ejecución
-- - Biografías: solo las 6 filas solicitadas
-- - Seguidores: única tabla estructurada con O/X/NULL
SET @@foreign_key_checks = 0;

CREATE DATABASE IF NOT EXISTS `culturarte` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `culturarte`;

-- Usuarios
DROP TABLE IF EXISTS usuarios;
CREATE TABLE usuarios (
  id VARCHAR(8) PRIMARY KEY,
  apodo VARCHAR(50),
  email VARCHAR(150),
  nombre VARCHAR(100),
  apellido VARCHAR(100),
  fecha_nacimiento DATE,
  tipo CHAR(1),
  creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO usuarios (id, apodo, email, nombre, apellido, fecha_nacimiento, tipo) VALUES
('HR','hrubino','horacio.rubino@guambia.com.uy','Horacio','Rubino','1962-02-25','P'),
('MB','mbusca','Martin.bus@agadu.org.uy','Martín','Buscaglia','1972-06-14','P'),
('HG','hectorg','hector.gui@elgalpon.org.uy','Héctor','Guido','1954-01-07','P'),
('TC','tabarec','tabare.car@agadu.org.uy','Tabaré','Cardozo','1971-07-24','P'),
('CS','cachilas','Cachila.sil@c1080.org.uy','Waldemar','Silva','1947-01-01','P'),
('JB','juliob','juliobocca@sodre.com.uy','Julio','Bocca','1967-03-16','P'),
('DP','diegop','diego@efectocine.com','Diego','Parodi','1975-01-01','P'),
('KH','kairoh','kairoher@pilsenrock.com.uy','Kairo','Herrera','1840-04-25','P'),
('LB','losBardo','losbardo@bardocientifico.com','Los','Bardo','1980-10-31','P'),
('RH','robinh','Robin.h@tinglesa.com.uy','Robin','Henderson','1940-08-03','C'),
('MT','marcelot','marcelot@ideasdelsur.com.ar','Marcelo','Tinelli','1960-04-01','C'),
('EN','novick','edgardo@novick.com.uy','Edgardo','Novick','1952-07-17','C'),
('SP','sergiop','puglia@alpanpan.com.uy','Sergio','Puglia','1950-01-28','C'),
('AR','chino','chino@trico.org.uy','Alvaro','Recoba','1976-03-17','C'),
('AP','tonyp','eltony@manya.org.uy','Antonio','Pacheco','1955-02-14','C'),
('NJ','nicoJ','jodal@artech.com.uy','Nicolás','Jodal','1960-08-09','C'),
('JP','juanP','juanp@elpueblo.com','Juan','Perez','1970-01-01','C'),
('MG','Mengano','menganog@elpueblo.com','Mengano','Gómez','1982-02-02','C'),
('PL','Perengano','pere@elpueblo.com','Perengano','López','1985-03-03','C'),
('TJ','Tiajaci','jacinta@elpueblo.com','Tía','Jacinta','1990-04-04','C');

-- Detalles de proponentes
DROP TABLE IF EXISTS detalles_proponentes;
CREATE TABLE detalles_proponentes (
  usuario_id VARCHAR(8) PRIMARY KEY,
  imagen_url VARCHAR(255),
  direccion VARCHAR(255),
  sitio_web VARCHAR(255),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO detalles_proponentes (usuario_id, imagen_url, direccion, sitio_web) VALUES
('HR','http://bit.ly/2idUfBi','18 de Julio tecnologo','https://twitter.com/horaciorubino'),
('MB','http://bit.ly/2wp4Lv1','Colonia 4321','http://www.martinbuscaglia.com/'),
('TC','http://bit.ly/2vQsIZJ','Santiago Rivas 1212','https://www.facebook.com/Tabar%C3%A9-Cardozo-55179094281/?ref=br_rs'),
('CS','http://bit.ly/2idCAcJ','Br. Artigas 4567','https://www.facebook.com/C1080?ref=br_rs'),
('HG','http://bit.ly/2vchQnJ','Gral. Flores 5645',NULL),
('JB',NULL,'Benito Blanco 4321',NULL),
('DP',NULL,'Emilio Frugoni 1138 Ap. 02','http://www.efectocine.com'),
('KH','http://bit.ly/2xaKS8G','Paraguay 1423',NULL),
('LB','http://bit.ly/2xqGged','8 de Octubre 1429','https://bardocientifico.com/');

-- Biografías (SOLO las 6 filas solicitadas)
DROP TABLE IF EXISTS biografias;
CREATE TABLE biografias (
  usuario_id VARCHAR(8) PRIMARY KEY,
  texto TEXT,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO biografias (usuario_id, texto) VALUES
('HR','Horacio Rubino Torres nace el 25 de febrero de 1962, es conductor, actor y libretista.  Debuta en 1982 en carnaval en Los \"Klaper''s\", donde estuvo cuatro años, actuando y libretando. Luego para \"Gaby''s\" (6 años), escribió en categoría revistas y humoristas y desde el comienzo y hasta el presente en su propio conjunto Momosapiens.'),
('MB','Martín Buscaglia (Montevideo, 1972) es un artista, músico, compositor y productor uruguayo. Tanto con su banda (Los Bochamakers) como en su formato Hombre orquesta, o solo con su guitarra, ha recorrido el mundo tocando entre otros países en España, Estados Unidos, Inglaterra, Francia, Australia, Brasil, Colombia, Argentina, Chile, Paraguay, México y Uruguay. (Actualmente los Bochamakers son Matías Rada, Martín Ibarburu, Mateo Moreno, Herman Klang) Paralelamente, tiene proyectos a dúo con el español Kiko Veneno, la cubana Yusa, el argentino Lisandro Aristimuño, su compatriota Antolín, y a trío junto a los brasileros Os Mulheres Negras.'),
('TC','Tabaré Cardozo (Montevideo, 24 de julio de 1971) es un cantante, compositor y murguista uruguayo; conocido por su participación en la murga Agarrate Catalina, conjunto que fundó junto a su hermano Yamandú y Carlos Tanco en el año 2001.'),
('CS','Nace en el año 1947 en el conventillo \"Medio Mundo\" ubicado en pleno Barrio Sur. Es heredero parcialmentejunto al resto de sus hermanos- de la Comparsa \"Morenada\" (inactiva desde el fallecimiento de Juan Ángel Silva), en 1999 forma su propia Comparsa de negros y lubolos \"Cuareim 1080\". Director responsable, compositor y cantante de la misma.'),
('HG','En 1972 ingresó a la Escuela de Arte Dramático del teatro El Galpón. Participó en más de treinta obras teatrales y varios largometrajes. Integró el elenco estable de Radioteatro del Sodre, y en 2006 fue asesor de su Consejo Directivo. Como actor recibió múltiples reconocimientos: cuatro premios Florencio, premio al mejor actor extranjero del Festival de Miami y premio Mejor Actor de Cine 2008. Durante varios períodos fue directivo del teatro El Galpón y dirigente de la Sociedad Uruguaya de Actores (SUA); integró también la Federación Uruguaya de Teatros Independientes (FUTI). Formó parte del equipo de gestión de la refacción de los teatros La Máscara, Astral y El Galpón, y del equipo de gestión en la construcción del teatro De la Candela y de la sala Atahualpa de El Galpón.'),
('LB','Queremos ser vistos y reconocidos como una organización: referente en divulgación científica con un fuerte espíritu didáctico y divertido, a través de acciones coordinadas con otros divulgadores científicos, que permitan establecer puentes de comunicación. Impulsora en la generación de espacios de democratización y apropiación social del conocimiento científico.');

-- Imagenes colaboradores
DROP TABLE IF EXISTS imagenes_colaboradores;
CREATE TABLE imagenes_colaboradores (
  usuario_id VARCHAR(8) PRIMARY KEY,
  imagen_url VARCHAR(255),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO imagenes_colaboradores (usuario_id, imagen_url) VALUES
('MT','http://bit.ly/2v3A66R'),
('EN','http://bit.ly/2v0if0A'),
('SP','http://bit.ly/2xarQ2g'),
('AP','http://bit.ly/2vWrOfX'),
('NJ','http://bit.ly/2xb342i');

-- Categorias
DROP TABLE IF EXISTS categorias;
CREATE TABLE categorias (
  ref VARCHAR(8) PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  padre VARCHAR(8) DEFAULT NULL,
  FOREIGN KEY (padre) REFERENCES categorias(ref) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO categorias (ref, nombre, padre) VALUES
('TEA','Teatro', NULL),
('TDR','Teatro Dramático','TEA'),
('TMU','Teatro Musical','TEA'),
('COM','Comedia','TEA'),
('SUP','Stand-up','COM'),
('LIT','Literatura',NULL),
('MUS','Música',NULL),
('FES','Festival','MUS'),
('CON','Concierto','MUS'),
('CIN','Cine',NULL),
('CAL','Cine al Aire Libre','CIN'),
('CIP','Cine a Pedal','CIN'),
('DAN','Danza',NULL),
('BAL','Ballet','DAN'),
('FLA','Flamenco','DAN'),
('CAR','Carnaval',NULL),
('MUR','Murga','CAR'),
('HUM','Humoristas','CAR'),
('PAR','Parodistas','CAR'),
('LUB','Lubolos','CAR'),
('REV','Revista','CAR');

-- Propuestas
DROP TABLE IF EXISTS propuestas;
CREATE TABLE propuestas (
  ref VARCHAR(8) PRIMARY KEY,
  proponente VARCHAR(8),
  titulo VARCHAR(255),
  categoria_ref VARCHAR(8),
  fecha DATE,
  lugar VARCHAR(255),
  precio_entrada INT,
  monto INT,
  tipo_retorno VARCHAR(50),
  descripcion TEXT,
  imagen_url VARCHAR(255),
  FOREIGN KEY (proponente) REFERENCES usuarios(id) ON DELETE SET NULL,
  FOREIGN KEY (categoria_ref) REFERENCES categorias(ref) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO propuestas (ref, proponente, titulo, categoria_ref, fecha, lugar, precio_entrada, monto, tipo_retorno) VALUES
('CEB','DP','Cine en el Botánico','CAL','2017-09-16','Jardín Botánico',200,150000,'porcentaje'),
('MOM','HR','Religiosamente','PAR','2017-10-07','Teatro de Verano',300,300000,'entrada'),
('PIM','MB','El Pimiento Indomable','CON','2017-10-19','Teatro Solís',400,400000,'porcentaje'),
('PIL','KH','Pilsen Rock','FES','2017-10-21','Rural de Prado',1000,900000,'entrada'),
('RYJ','JB','Romeo y Julieta','BAL','2017-11-05','Auditorio Nacional del Sodre',800,750000,'porcentaje'),
('UDJ','TC','Un día de Julio','MUR','2017-11-16','Landia',650,300000,'entrada'),
('LDT','HG','El Lazarillo de Tormes','TDR','2017-12-03','Teatro el Galpón',350,175000,'entrada'),
('BEF','LB','Bardo en la FING','SUP','2017-12-10','Anfiteatro Edificio \"José Luis Massera\"',200,100000,'entrada');

-- Descripciones propuestas
DROP TABLE IF EXISTS descripciones_propuestas;
CREATE TABLE descripciones_propuestas (
  ref VARCHAR(8) PRIMARY KEY,
  descripcion TEXT,
  FOREIGN KEY (ref) REFERENCES propuestas(ref) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO descripciones_propuestas (ref, descripcion) VALUES
('CEB','El 16 de Diciembre a la hora 20 se proyectará la película \"Clever\", en el Jardín Botánico (Av. 19 de Abril 1181) en el marco de las actividades realizadas por el ciclo Cultura al Aire Libre. El largometraje uruguayo de ficción Clever es dirigido por Federico Borgia y Guillermo Madeiro. Es apto para mayores de 15 años.'),
('MOM','MOMOSAPIENS presenta \"Religiosamente\". Mediante dos parodias y un hilo conductor que aborda la temática de la religión Momosapiens, mediante el humor y la reflexión...'),
('PIM','El Pimiento Indomable, formación compuesta por Kiko Veneno y el uruguayo Martín Buscaglia, presentará este 19 de Octubre, su primer trabajo...'),
('PIL','La edición 2017 del Pilsen Rock se celebrará el 21 de Octubre en la Rural del Prado y contará con la participación de más de 15 bandas...'),
('RYJ','Romeo y Julieta de Kenneth MacMillan, uno de los ballets favoritos del director artístico Julio Bocca, se presentará nuevamente el 5 de Noviembre...'),
('UDJ','La Catalina presenta el espectáculo \"Un Día de Julio\" en Landia. Un hombre misterioso y solitario vive encerrado entre las cuatro paredes de su casa...'),
('LDT','Vuelve unas de las producciones de El Galpón más aclamadas de los últimos tiempos...'),
('BEF','El 10 de Diciembre se presentará Bardo Científico en la FING. El humor puede ser usado como una herramienta importante para el aprendizaje...');

-- Imagenes propuestas
DROP TABLE IF EXISTS imagenes_propuestas;
CREATE TABLE imagenes_propuestas (
  ref VARCHAR(8),
  imagen_url VARCHAR(255),
  PRIMARY KEY (ref, imagen_url),
  FOREIGN KEY (ref) REFERENCES propuestas(ref) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO imagenes_propuestas (ref, imagen_url) VALUES
('MOM','http://bit.ly/2wCsKqI'),
('PIM','http://bit.ly/2vmvbJm'),
('PIL','http://bit.ly/2xr8lC1'),
('RYJ','http://bit.ly/2wlFtyd'),
('UDJ','http://bit.ly/2wwSK7y');

-- Estados propuestas
DROP TABLE IF EXISTS estados_propuestas;
CREATE TABLE estados_propuestas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  propuesta_ref VARCHAR(8),
  estado VARCHAR(50),
  fecha DATE,
  hora TIME,
  FOREIGN KEY (propuesta_ref) REFERENCES propuestas(ref) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO estados_propuestas (propuesta_ref, estado, fecha, hora) VALUES
('CEB','Ingresada','2017-05-15','15:30:00'),
('CEB','Publicada','2017-05-17','08:30:00'),
('CEB','En Financiación','2017-05-20','14:30:00'),
('CEB','Financiada','2017-05-30','18:30:00'),
('CEB','Cancelada','2017-06-15','14:50:00'),
('MOM','Ingresada','2017-06-18','04:28:00'),
('MOM','Publicada','2017-06-20','04:56:00'),
('MOM','En Financiación','2017-06-30','14:25:00'),
('MOM','Financiada','2017-07-15','09:45:00'),
('PIM','Ingresada','2017-07-26','15:30:00'),
('PIM','Publicada','2017-07-31','08:30:00'),
('PIM','En Financiación','2017-08-01','07:40:00'),
('PIL','Ingresada','2017-07-30','15:40:00'),
('PIL','Publicada','2017-08-01','14:30:00'),
('PIL','En Financiación','2017-08-05','16:50:00'),
('RYJ','Ingresada','2017-08-04','12:20:00'),
('RYJ','Publicada','2017-08-10','10:25:00'),
('RYJ','En Financiación','2017-08-13','04:58:00'),
('UDJ','Ingresada','2017-08-06','02:00:00'),
('UDJ','Publicada','2017-08-12','04:50:00'),
('UDJ','En Financiación','2017-08-15','04:48:00'),
('LDT','Ingresada','2017-08-18','02:40:00'),
('LDT','Publicada','2017-08-20','21:58:00'),
('BEF','Ingresada','2017-08-23','02:12:00');

-- Colaboraciones
DROP TABLE IF EXISTS colaboraciones;
CREATE TABLE colaboraciones (
  id VARCHAR(16) PRIMARY KEY,
  colaborador VARCHAR(8),
  propuesta_ref VARCHAR(8),
  fecha DATE,
  hora TIME,
  monto INT,
  retorno_elegido VARCHAR(50),
  FOREIGN KEY (colaborador) REFERENCES usuarios(id) ON DELETE SET NULL,
  FOREIGN KEY (propuesta_ref) REFERENCES propuestas(ref) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO colaboraciones (id, colaborador, propuesta_ref, fecha, hora, monto, retorno_elegido) VALUES
('Col01','EN','CEB','2017-05-20','14:30:00',50000,'porcentaje'),
('Col02','RH','CEB','2017-05-24','17:25:00',50000,'porcentaje'),
('Col03','NJ','CEB','2017-05-30','18:30:00',50000,'porcentaje'),
('Col04','MT','MOM','2017-06-30','14:25:00',200000,'porcentaje'),
('Col05','TJ','MOM','2017-07-01','18:05:00',500,'entrada'),
('Col06','MG','MOM','2017-07-07','17:45:00',600,'entrada'),
('Col07','EN','MOM','2017-07-10','14:35:00',50000,'porcentaje'),
('Col08','SP','MOM','2017-07-15','09:45:00',50000,'porcentaje'),
('Col09','MT','PIM','2017-08-01','07:40:00',200000,'porcentaje'),
('Col10','SP','PIM','2017-08-03','09:25:00',80000,'porcentaje'),
('Col11','AR','PIL','2017-08-05','16:50:00',50000,'entrada'),
('Col12','EN','PIL','2017-08-10','15:50:00',120000,'porcentaje'),
('Col13','AP','PIL','2017-08-15','19:30:00',120000,'entrada'),
('Col14','SP','RYJ','2017-08-13','04:58:00',100000,'porcentaje'),
('Col15','MT','RYJ','2017-08-14','11:25:00',200000,'porcentaje'),
('Col16','AP','UDJ','2017-08-15','04:48:00',30000,'entrada'),
('Col17','MT','UDJ','2017-08-17','15:30:00',150000,'porcentaje');

-- Seguidores: única tabla estructurada (llenada según la matriz proporcionada)
DROP TABLE IF EXISTS seguidores;
CREATE TABLE seguidores (
  sigue VARCHAR(8) PRIMARY KEY,
  HR CHAR(1) DEFAULT NULL,
  MB CHAR(1) DEFAULT NULL,
  HG CHAR(1) DEFAULT NULL,
  TC CHAR(1) DEFAULT NULL,
  CS CHAR(1) DEFAULT NULL,
  JB CHAR(1) DEFAULT NULL,
  DP CHAR(1) DEFAULT NULL,
  KH CHAR(1) DEFAULT NULL,
  LB CHAR(1) DEFAULT NULL,
  RH CHAR(1) DEFAULT NULL,
  MT CHAR(1) DEFAULT NULL,
  EN CHAR(1) DEFAULT NULL,
  SP CHAR(1) DEFAULT NULL,
  AR CHAR(1) DEFAULT NULL,
  AP CHAR(1) DEFAULT NULL,
  NJ CHAR(1) DEFAULT NULL,
  JP CHAR(1) DEFAULT NULL,
  MG CHAR(1) DEFAULT NULL,
  PL CHAR(1) DEFAULT NULL,
  TJ CHAR(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insertar la matriz tal como la interpretamos desde el bloque que nos enviaste.
INSERT INTO seguidores (sigue, HR,MB,HG,TC,CS,JB,DP,KH,LB,RH,MT,EN,SP,AR,AP,NJ,JP,MG,PL,TJ) VALUES
('HR','O','X',NULL,NULL,'X','X',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('MB',NULL,'O','X','X',NULL,'X',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('HG',NULL,'X','O',NULL,'X',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('TC','X',NULL,'O','X',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('CS','X',NULL,NULL,'O',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('JB',NULL,'X',NULL,NULL,'O','X',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('DP',NULL,NULL,'X',NULL,NULL,'O','X',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('KH',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'O',NULL,NULL,NULL,'X',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('LB','X',NULL,NULL,NULL,NULL,NULL,NULL,'O',NULL,NULL,NULL,NULL,NULL,'X',NULL,NULL,NULL,NULL,NULL,NULL),
('RH',NULL,NULL,'X',NULL,'X','X',NULL,'O',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('MT',NULL,NULL,NULL,NULL,'X','X','X',NULL,'O',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('EN','X',NULL,'X','X',NULL,NULL,NULL,NULL,NULL,'O',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('SP',NULL,'X',NULL,NULL,'X','X',NULL,NULL,NULL,NULL,'O',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
('AR',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'O','X',NULL,NULL,NULL,NULL,NULL),
('AP',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'X','O',NULL,NULL,NULL,NULL,NULL),
('NJ',NULL,NULL,NULL,NULL,NULL,NULL,'X','X',NULL,NULL,NULL,NULL,NULL,'O',NULL,NULL,NULL,NULL,NULL,NULL),
('JP',NULL,NULL,NULL,'X','X',NULL,'X',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'O',NULL,NULL,NULL,NULL,NULL),
('MG',NULL,NULL,'X',NULL,'X',NULL,NULL,NULL,NULL,NULL,NULL,'X',NULL,NULL,'O',NULL,NULL,NULL,NULL,NULL),
('PL',NULL,NULL,NULL,NULL,NULL,NULL,'X',NULL,NULL,NULL,NULL,NULL,NULL,'X',NULL,NULL,'O',NULL,NULL,NULL),
('TJ',NULL,NULL,NULL,NULL,NULL,'X','X',NULL,NULL,NULL,'X',NULL,NULL,NULL,NULL,NULL,'O',NULL,NULL,NULL);


SET @@foreign_key_checks = 1;
COMMIT;

-- Vista: v_detalle_propuesta (opcional)
DROP VIEW IF EXISTS v_detalle_propuesta;
CREATE VIEW v_detalle_propuesta AS
SELECT p.ref, p.titulo, p.proponente, u.nombre AS proponente_nombre, p.categoria_ref, c.nombre AS categoria_nombre, p.fecha, p.lugar, p.precio_entrada, p.monto, p.tipo_retorno, ip.imagen_url
FROM propuestas p
LEFT JOIN usuarios u ON p.proponente = u.id
LEFT JOIN categorias c ON p.categoria_ref = c.ref
LEFT JOIN imagenes_propuestas ip ON p.ref = ip.ref;
