INSERT INTO LOCAL_LANGUAGES VALUES ('EN',false,true), ('RU',false,true), ('UA',false,true);
INSERT INTO LOCAL_LANGUAGE_LOCALS VALUES ('EN','English','EN');
INSERT INTO LOCAL_LANGUAGE_LOCALS VALUES ('EN','Ukraine','UA');
INSERT INTO LOCAL_LANGUAGE_LOCALS VALUES ('EN','Russian','RU');

INSERT INTO LOCAL_COUNTRIES VALUES ('UA', '+380');
INSERT INTO LOCAL_COUNTRY_LOCALS VALUES ('UA','Ukraine','EN');

INSERT INTO LOCAL_CITIES VALUES (1, 'UA',NULL);
INSERT INTO LOCAL_CITY_LOCALS VALUES (1,'Kharkiv','EN');
INSERT INTO LOCAL_CITIES VALUES (2, 'UA',NULL);
INSERT INTO LOCAL_CITY_LOCALS VALUES (2,'Kyiv','EN');

INSERT INTO USR_PROFILES VALUES
  (-1,'2001-01-01 00:00',NULL,0,'I am an admin',NULL,'2001-01-01 00:00','Admin','Admin Berize','MALE','Admin',1,'UA','EN'),
  (-2,'2001-01-01 00:00',NULL,0,'I am a tutor',NULL,'2001-01-01 00:00','Tutor','Tutor Berize','MALE','Tutor',2,'UA','EN'),
  (-50,'2001-01-01 00:00',NULL,0,'I am an batch user',NULL,'2001-01-01 00:00','Batch','Batch User','MALE','Batch',1,'UA','EN'),
  (1,'2001-01-01 00:00',NULL,0,'User 1',NULL,'2001-01-01 00:00','User','User 1','MALE','User 1',2,'UA','EN'),
  (2,'2001-01-01 00:00',NULL,0,'User 2',NULL,'2001-01-01 00:00','User','User 2','MALE','User 3',2,'UA','EN'),
  (3,'2001-01-01 00:00',NULL,0,'User 3',NULL,'2001-01-01 00:00','User','User 3','MALE','User 3',2,'UA','EN'),
  (4,'2001-01-01 00:00',NULL,0,'User 3',NULL,'2001-01-01 00:00','User','User 4','MALE','User 4',2,'UA','EN'),
  (5,'2001-01-01 00:00',NULL,0,'User 5',NULL,'2001-01-01 00:00','User','User 5','MALE','User 5',2,'UA','EN');

INSERT INTO USR_USERS VALUES
  (-1,'2001-01-01 00:00',NULL,0,true,NULL,'admin@berize.com','9999-12-31',NULL,false,false,NULL,'','REGISTERED','admin@berize.com','EN',-1),
  (-2,'2001-01-01 00:00',NULL,0,true,NULL,'tutor@berize.com','9999-12-31',NULL,false,false,NULL,'','REGISTERED','tutor@berize.com','EN',-2),
  (-50,'2001-01-01 00:00',NULL,0,true,NULL,'batch@berize.net','9999-12-31',NULL,false,false,NULL,'','REGISTERED','batch@berize.net','EN',-50),
  (1,'2001-01-01 00:00',NULL,0,true,NULL,'user1@berize.com','9999-12-31',NULL,false,false,NULL,'','REGISTERED','user1@berize.com','EN',1),
  (2,'2001-01-01 00:00',NULL,0,true,NULL,'user2@berize.com','9999-12-31',NULL,false,false,NULL,'','REGISTERED','user2@berize.com','EN',2),
  (3,'2001-01-01 00:00',NULL,0,true,NULL,'user3@berize.com','9999-12-31',NULL,false,false,NULL,'','REGISTERED','user3@berize.com','EN',3),
  (4,'2001-01-01 00:00',NULL,0,true,NULL,'user4@berize.com','9999-12-31',NULL,false,false,NULL,'','REGISTERED','user4@berize.com','EN',4),
  (5,'2001-01-01 00:00',NULL,0,true,NULL,'user5@berize.com','9999-12-31',NULL,false,false,NULL,'','REGISTERED','user5@berize.com','EN',5);

INSERT INTO USR_ROLES VALUES
  (-1,'2001-01-01 00:00',NULL,0,'Full rights','BERIZE_ALL','SYSTEM','EN'),
  (-2,'2001-01-01 00:00',NULL,0,'Role for administrator of system','ROLE_ADMIN','SYSTEM','EN'),
  (-3,'2001-01-01 00:00',NULL,0,'Base role for all users','ROLE_USER','SYSTEM','EN');

INSERT INTO USR_USERS_ROLES VALUES (-1,-2), (-2,-3), (1, -3), (2, -3), (3, -3);

INSERT INTO LOCAL_LOCALIZATION VALUES
  (1,'2001-01-01 00:00',NULL,0,'dashboard.menu.exit','DASHBOARD_MENU','Exit',NULL,'EN'),
  (2,'2001-01-01 00:00',NULL,0,'dashboard.menu.my_records','DASHBOARD_MENU','My records',NULL,'EN'),
  (3,'2001-01-01 00:00',NULL,0,'dashboard.menu.tests','DASHBOARD_MENU','Audio tests',NULL,'EN'),
  (4,'2001-01-01 00:00',NULL,0,'dashboard.menu.cabinet.notifications','DASHBOARD_MENU','Notifications',NULL,'EN'),
  (5,'2001-01-01 00:00',NULL,0,'dashboard.menu.cabinet.settings','DASHBOARD_MENU','Settings',NULL,'EN'),
  (6,'2001-01-01 00:00',NULL,0,'dashboard.menu.cabinet.profile','DASHBOARD_MENU','Profile',NULL,'EN'),
  (7,'2001-01-01 00:00',NULL,0,'dashboard.menu.cabinet','DASHBOARD_MENU','Cabinet',NULL,'EN'),
  (8,'2001-01-01 00:00',NULL,0,'dashboard.menu.admin.notifications','DASHBOARD_MENU','Notifications',NULL,'EN'),
  (9,'2001-01-01 00:00',NULL,0,'dashboard.menu.admin.users_auth.roles','DASHBOARD_MENU','Roles',NULL,'EN'),
  (10,'2001-01-01 00:00',NULL,0,'dashboard.menu.admin.users_auth.users','DASHBOARD_MENU','Users',NULL,'EN'),
  (11,'2001-01-01 00:00',NULL,0,'dashboard.menu.admin.users_auth','DASHBOARD_MENU','Users and Authorities',NULL,'EN'),
  (12,'2001-01-01 00:00',NULL,0,'dashboard.menu.admin','DASHBOARD_MENU','Administration',NULL,'EN');
