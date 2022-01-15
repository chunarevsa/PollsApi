INSERT INTO user (user_id, username, password, is_active)
	VALUES (1, 'admin', '$2a$10$vehdpeN2ACohVgR/rOnKz..TdMPCvPsR.M.uT8c2f0e8OW.IpnYnG', true);
INSERT INTO user_authority (user_id, role_id)
	VALUES (1, 1);
INSERT INTO user_authority (user_id, role_id)
	VALUES (1, 2);

UPDATE user_seq SET next_val = 2