CREATE TABLE usr_user (
                          id VARCHAR(36) NOT NULL,
                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP NOT NULL,
                          usr_id VARCHAR(255) NOT NULL,
                          usr_age INT,
                          usr_gender VARCHAR(50),
                          usr_bio VARCHAR(500),
                          usr_profile_img VARCHAR(500),
                          CONSTRAINT PK_USR_USER PRIMARY KEY (id),
                          CONSTRAINT UK_USR_USER_USR_ID UNIQUE (usr_id)
);

CREATE INDEX IDX_USR_USER_USR_ID ON usr_user(usr_id);