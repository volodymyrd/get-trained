INSERT INTO NOTIF_CHANNELS VALUES
  ('EMAIL','EMAIL channel'),('PUSH','PUSH channel'),('SMS','SMS channel'),('SYSTEM','SYSTEM channel');

INSERT INTO NOTIF_EVENT_GROUPS VALUES ('ASSESSMENT', 'Group of the events for the Assessment module with the COMPANY scope', 'Assessment', 'key_name', 'key_desc');

INSERT INTO NOTIF_EVENTS VALUES
  ('CONFIRM_REGISTRATION',0,'Confirm registration event', 'SYSTEM', TRUE,'EN','ASSESSMENT'),
  ('OBLIGATION_ENROLLMENT',0,'Obligation enrollment event', 'COMPANY', NULL,'EN','ASSESSMENT'),
  ('OBLIGATION_ENROLLMENT_DEADLINE_REMINDER',0,'Obligation enrollment reminder about deadline event', 'COMPANY', NULL,'EN','ASSESSMENT'),
  ('RESTORE_PASSWORD',0,'Restore password event', 'SYSTEM', TRUE,'EN','ASSESSMENT'),
  ('SELF_ENROLLMENT',0,'Self enrollment event', 'COMPANY', NULL,'EN','ASSESSMENT'),
  ('SELF_ENROLLMENT_DEADLINE_REMINDER',0,'Self enrollment reminder about deadline event', 'COMPANY', NULL,'EN','ASSESSMENT'),
  ('SUCCESS_LOGIN',0,'Success login event', 'SYSTEM', TRUE,'EN','ASSESSMENT'),
  ('SUCCESS_REGISTRATION',0,'Success registration event', 'SYSTEM', TRUE,'EN','ASSESSMENT');

