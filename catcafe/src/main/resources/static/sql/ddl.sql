CREATE TABLE cat_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 主键
    cat_name VARCHAR(255) NOT NULL,         -- 猫の名前
    cat_intro TEXT,                         -- 猫の紹介
    likes_count INT,                        -- 猫のいいね数
    osusume_flg INT,                        -- おすすめFLG
    cat_image VARCHAR(255),                 -- 猫の画像
    delete_flag TINYINT DEFAULT 0,          -- 删除标志，0 表示未删除，1 表示已删除
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  -- 更新时间，自动更新
);


CREATE TABLE store_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 主键
    carousel1 VARCHAR(255),                -- 画面カルーセル1
    carousel2 VARCHAR(255),                -- 画面カルーセル2
    carousel3 VARCHAR(255),                -- 画面カルーセル3
    store_intro TEXT,                      -- 店舗紹介
    cats_intro TEXT,                       -- 猫たちの紹介
    cats_adoption TEXT,                    -- 猫の里親募集
    cats_family TEXT,                      -- 猫の家族
    blog TEXT,                             -- ブログ
    delete_flag TINYINT DEFAULT 0,         -- 删除标志，0 表示未删除，1 表示已删除
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  -- 更新时间，自动更新
);

create table tab_user
(
    id       int auto_increment comment '主键'
        primary key,
    name     varchar(50) null comment '名字',
    email    varchar(30) null comment '邮箱',
    password varchar(30) null comment '密码',
    phone    varchar(20) null comment '电话号码',
    create_time datetime not null default current_timestamp comment '创建时间',
    update_time datetime not null default current_timestamp comment '更新时间',
    del_flag int not null default 0 comment '是否删除 0-未删除，1-已删除'
) comment '用户表';
ALTER TABLE cat_info
ADD COLUMN cat_age TINYINT AFTER cat_intro;
<<<<<<< HEAD

CREATE TABLE about_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,             -- 主キー、自動増加
    about_title VARCHAR(255) NOT NULL,               -- Aboutタイトル
    about_description TEXT,                          -- Aboutの説明
    cat_image_url VARCHAR(255),                      -- 猫の画像URL
    cat_name VARCHAR(255),                           -- 猫の名前
    cat_tip TEXT,                                    -- 注意事項
    cat_description TEXT,                            -- 猫の詳細説明
    team_title VARCHAR(255),                         -- チームタイトル
    team_url VARCHAR(255),                           -- チーム画像URL
    team_description TEXT,                           -- チームの説明
    team_members TEXT,                               -- チームメンバー情報
    delete_flag TINYINT DEFAULT 0,                   -- 削除フラグ、0:未削除、1:削除済み
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新日時
);



