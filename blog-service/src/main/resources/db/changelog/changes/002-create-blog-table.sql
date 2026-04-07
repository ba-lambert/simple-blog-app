-- liquibase formatted sql

-- changeset author:002-create-blog-table
CREATE TABLE "BLGS-BLOGS" (
    ID VARCHAR(36) NOT NULL,
    CONSTRAINT PK_BLGS_BLOGS PRIMARY KEY (ID)
);

-- rollback DROP TABLE "BLGS-BLOGS";
