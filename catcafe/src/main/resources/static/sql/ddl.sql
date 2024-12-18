CREATE TABLE cat_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 主键
    cat_name VARCHAR(255) NOT NULL,         -- 猫の名前
    cat_intro TEXT,                         -- 猫の紹介
    likes_count INT,                        -- 猫のいいね数
    osusume_flg INT,                        -- 猫のいいね数
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