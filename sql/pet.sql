-- 用户表 (Users)
/*
表名：tb_users
注释：
- user_id：用户ID，主键，自增
- username：用户名，唯一，不可为空
- password_hash：用户密码哈希值，不可为空
- email：用户邮箱，唯一，不可为空
- phone_number：用户手机号（可空）
- address：用户地址（可空）
- avatar_url：用户头像图片URL（可空）
- created_at：记录创建时间，默认为当前时间
- created_by：记录创建人ID
- updated_at：记录更新时间，默认为当前时间，更新时自动更新
- updated_by：记录更新人ID
外键关系：
- user_id 关联 tb_users 表中的用户
*/
CREATE TABLE tb_users (
                          user_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID，主键，自增',
                          username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名，唯一，不可为空',
                          password_hash VARCHAR(255) NOT NULL COMMENT '用户密码哈希值，不可为空',
                          email VARCHAR(100) NOT NULL UNIQUE COMMENT '用户邮箱，唯一，不可为空',
                          phone_number VARCHAR(15) COMMENT '用户手机号',
                          address VARCHAR(255) COMMENT '用户地址',
                          avatar_url VARCHAR(255) COMMENT '用户头像图片URL',
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间，默认为当前时间',
                          created_by INT COMMENT '记录创建人ID',
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间，默认为当前时间，更新时自动更新',
                          updated_by Int COMMENT '记录更新人ID'
)comment '用户表';

-- 宠物表 (Pets)
/*
表名：tb_pets
注释：
- pet_id：宠物ID，主键，自增
- pet_name：宠物名称，不可为空
- species：宠物种类，不可为空
- breed：宠物品种（可空）
- age：宠物年龄（可空）
- gender：宠物性别，枚举值：Male/Female/Other
- pet_image_url：宠物图片URL（可空）
- vaccination_record_url：疫苗接种记录图片URL（可空）
- user_id：所属用户ID，外键，关联 tb_users 表
- created_at：记录创建时间，默认为当前时间
- created_by：记录创建人ID
- updated_at：记录更新时间，默认为当前时间，更新时自动更新
- updated_by：记录更新人ID
外键关系：
- user_id 关联 tb_users 表中的用户
*/
CREATE TABLE tb_pets (
                         pet_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '宠物ID，主键，自增',
                         pet_name VARCHAR(100) NOT NULL COMMENT '宠物名称，不可为空',
                         species VARCHAR(50) NOT NULL COMMENT '宠物种类，不可为空',
                         breed VARCHAR(50) COMMENT '宠物品种',
                         age INT COMMENT '宠物年龄',
                         gender ENUM('Male', 'Female', 'Other') COMMENT '宠物性别，枚举值：Male/Female/Other',
                         pet_image_url VARCHAR(255) COMMENT '宠物图片URL',
                         vaccination_record_url VARCHAR(255) COMMENT '疫苗接种记录图片URL',
                         user_id INT COMMENT '所属用户ID，外键，关联 tb_users 表',
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间，默认为当前时间',
                         created_by Int COMMENT '记录创建人ID',
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间，默认为当前时间，更新时自动更新',
                         updated_by Int COMMENT '记录更新人ID'
)comment '宠物表';

-- 宠物健康记录表 (PetHealthRecords)
/*
表名：tb_pet_health_records
注释：
- record_id：健康记录ID，主键，自增
- pet_id：宠物ID，外键，关联 tb_pets 表
- record_date：健康记录日期，不可为空
- description：健康记录描述（可空）
- veterinarian_name：兽医姓名（可空）
- created_at：记录创建时间，默认为当前时间
- created_by：记录创建人ID
- updated_at：记录更新时间，默认为当前时间，更新时自动更新
- updated_by：记录更新人ID
外键关系：
- pet_id 关联 tb_pets 表中的宠物
*/
CREATE TABLE tb_pet_health_records (
                                       record_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '健康记录ID，主键，自增',
                                       pet_id INT COMMENT '宠物ID，外键，关联 tb_pets 表',
                                       record_date DATE NOT NULL COMMENT '健康记录日期，不可为空',
                                       description TEXT COMMENT '健康记录描述',
                                       veterinarian_name VARCHAR(100) COMMENT '兽医姓名',
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间，默认为当前时间',
                                       created_by Int COMMENT '记录创建人ID',
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间，默认为当前时间，更新时自动更新',
                                       updated_by Int COMMENT '记录更新人ID'
)comment '宠物健康记录表';

-- 服务表 (Services)
/*
表名：tb_services
注释：
- service_id：服务ID，主键，自增
- service_name：服务名称，不可为空
- description：服务描述（可空）
- price：服务价格，不可为空
- service_image_url：服务展示图片URL（可空）
- created_at：记录创建时间，默认为当前时间
- created_by：记录创建人ID
- updated_at：记录更新时间，默认为当前时间，更新时自动更新
- updated_by：记录更新人ID
*/
CREATE TABLE tb_services (
                             service_id Int AUTO_INCREMENT PRIMARY KEY COMMENT '服务ID，主键，自增',
                             service_name VARCHAR(100) NOT NULL COMMENT '服务名称，不可为空',
                             description TEXT COMMENT '服务描述',
                             price DECIMAL(10, 2) NOT NULL COMMENT '服务价格，不可为空',
                             is_available BOOLEAN DEFAULT TRUE COMMENT '服务是否可用，默认为TRUE',
                             service_type varchar(20) DEFAULT 'General' COMMENT '服务类型（常规/寄养）',
                             room_number INT NOT NULL COMMENT '房间号',
                             room_type varchar(25) NOT NULL COMMENT '房型',
                             daily_price DECIMAL(10,2) NOT NULL COMMENT '每日价格',
                             max_pets_per_room INT  COMMENT '单间最大宠物数量',
                             include_walk int COMMENT '包含遛狗服务',
                             special_requirements TEXT COMMENT '特殊要求说明',
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间，默认为当前时间',
                             created_by Int COMMENT '记录创建人ID',
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间，默认为当前时间，更新时自动更新',
                             updated_by Int COMMENT '记录更新人ID'
)comment '服务表';

-- 预约表 (Appointments)
/*
表名：tb_appointments
注释：
- appointment_id：预约ID，主键，自增
- pet_id：宠物ID，外键，关联 tb_pets 表
- service_id：服务ID，外键，关联 tb_services 表
- appointment_date：预约日期和时间，不可为空
- status：预约状态，枚举值：Pending/Confirmed/Cancelled/Completed，默认为Pending
- created_at：记录创建时间，默认为当前时间
- created_by：记录创建人ID
- updated_at：记录更新时间，默认为当前时间，更新时自动更新
- updated_by：记录更新人ID
外键关系：
- pet_id 关联 tb_pets 表中的宠物
- service_id 关联 tb_services 表中的服务
*/
CREATE TABLE tb_appointments (
                                 appointment_id Int AUTO_INCREMENT PRIMARY KEY COMMENT '预约ID，主键，自增',
                                 pet_id Int COMMENT '宠物ID，外键，关联 tb_pets 表',
                                 service_id Int COMMENT '服务ID，外键，关联 tb_services 表',
                                 appointment_date DATETIME NOT NULL COMMENT '预约日期和时间，不可为空',
                                 status ENUM('Pending', 'Confirmed', 'Cancelled', 'Completed') DEFAULT 'Pending' COMMENT '预约状态，枚举值：Pending/Confirmed/Cancelled/Completed',
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间，默认为当前时间',
                                 created_by Int COMMENT '记录创建人ID',
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间，默认为当前时间，更新时自动更新',
                                 updated_by Int COMMENT '记录更新人ID'
)comment '预约表';

-- 订单表 (Orders)
/*
表名：tb_orders
注释：
- order_id：订单ID，主键，自增
- user_id：用户ID，外键，关联 tb_users 表
- total_amount：订单总金额，不可为空
- order_date：订单日期和时间，不可为空
- payment_proof_url：支付凭证图片URL（可空）
- status：订单状态，枚举值：Pending/Paid/Cancelled/Completed，默认为Pending
- created_at：记录创建时间，默认为当前时间
- created_by：记录创建人ID
- updated_at：记录更新时间，默认为当前时间，更新时自动更新
- updated_by：记录更新人ID
外键关系：
- user_id 关联 tb_users 表中的用户
*/
CREATE TABLE tb_orders (
                           order_id Int AUTO_INCREMENT PRIMARY KEY COMMENT '订单ID，主键，自增',
                           user_id Int COMMENT '用户ID，外键，关联 tb_users 表',
                           total_amount DECIMAL(10, 2) NOT NULL COMMENT '订单总金额，不可为空',
                           order_date DATETIME NOT NULL COMMENT '订单日期和时间，不可为空',
                           payment_proof_url VARCHAR(255) COMMENT '支付凭证图片URL',
                           status ENUM('Pending', 'Paid', 'Cancelled', 'Complete') DEFAULT 'Pending' COMMENT '订单状态，枚举值：Pending/Paid/Cancelled/Completed',
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间，默认为当前时间',
                           created_by Int COMMENT '记录创建人ID',
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间，默认为当前时间，更新时自动更新',
                           updated_by Int COMMENT '记录更新人ID'
)comment '订单表';

-- 订单详情表 (OrderDetails)
/*
表名：tb_order_details
注释：
- order_detail_id：订单详情ID，主键，自增
- order_id：订单ID，外键，关联 tb_orders 表
- service_id：服务ID，外键，关联 tb_services 表
- quantity：服务数量，不可为空
- price：服务单价，不可为空
- created_at：记录创建时间，默认为当前时间
- created_by：记录创建人ID
- updated_at：记录更新时间，默认为当前时间，更新时自动更新
- updated_by：记录更新人ID
外键关系：
- order_id 关联 tb_orders 表中的订单
- service_id 关联 tb_services 表中的服务
*/
CREATE TABLE tb_order_details (
                                  order_detail_id Int AUTO_INCREMENT PRIMARY KEY COMMENT '订单详情ID，主键，自增',
                                  order_id Int COMMENT '订单ID，外键，关联 tb_orders 表',
                                  service_id Int COMMENT '服务ID，外键，关联 tb_services 表',
                                  quantity Int NOT NULL COMMENT '服务数量，不可为空',
                                  price DECIMAL(10, 2) NOT NULL COMMENT '服务单价，不可为空',
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间，默认为当前时间',
                                  created_by Int COMMENT '记录创建人ID',
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  COMMENT '记录更新时间，默认为当前时间，更新时自动更新',
                                  updated_by Int COMMENT '记录更新人ID'
)comment '订单详情表';


-- 统计表 (Statistics)
/*
表名：tb_statistics
注释：
- stat_id：统计ID，主键，自增
- stat_date：统计日期，不可为空
- total_users：总用户数，默认为0
- total_orders：总订单数，默认为0
- total_revenue：总收入，默认为0
- created_at：记录创建时间，默认为当前时间
- created_by：记录创建人ID
- updated_at：记录更新时间，默认为当前时间，更新时自动更新
- updated_by：记录更新人ID
*/
CREATE TABLE tb_statistics (
                               stat_id Int AUTO_INCREMENT PRIMARY KEY COMMENT '统计ID，主键，自增',
                               stat_date DATE NOT NULL COMMENT '统计日期，不可为空',
                               total_users INT DEFAULT 0 COMMENT '总用户数，默认为0',
                               total_orders INT DEFAULT 0 COMMENT '总订单数，默认为0',
                               total_revenue DECIMAL(15, 2) DEFAULT 0 COMMENT '总收入，默认为0',
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间，默认为当前时间',
                               created_by Int COMMENT '记录创建人ID',
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录更新时间，默认为当前时间，更新时自动更新',
                               updated_by Int COMMENT '记录更新人ID'
)comment '统计表';

-- 动态表 (Posts)
/*
表名：tb_posts
注释：
- post_id：动态ID，主键，自增
- user_id：用户ID，外键，关联 tb_users 表
- content：动态内容
- post_image_urls：动态图片URL列表（JSON格式）
- created_at：记录创建时间，默认为当前时间
- created_by：记录创建人ID
- updated_at：记录更新时间，默认为当前时间，更新时自动更新
- updated_by：记录更新人ID
外键关系：
- user_id 关联 tb_users 表中的用户
*/
CREATE TABLE tb_posts (
                          post_id Int AUTO_INCREMENT PRIMARY KEY COMMENT '动态ID，主键，自增',
                          user_id Int COMMENT '用户ID，外键，关联 tb_users 表',
                          content TEXT COMMENT '动态内容',
                          post_image_urls JSON COMMENT '动态图片URL列表（JSON格式）',
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间，默认为当前时间',
                          created_by Int COMMENT '记录创建人ID',
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录更新时间，默认为当前时间，更新时自动更新',
                          updated_by Int COMMENT '记录更新人ID'
)comment '动态表';

CREATE TABLE tb_foster_services (
    foster_service_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '寄养服务ID',
    service_id INT NOT NULL COMMENT '关联基础服务ID',
    room_number INT NOT NULL COMMENT '房间号',
    room_type ENUM('Standard', 'Deluxe', 'VIP') NOT NULL COMMENT '房型',
    daily_price DECIMAL(10,2) NOT NULL COMMENT '每日价格',
    max_pets_per_room INT DEFAULT 1 COMMENT '单间最大宠物数量',
    include_walk BOOLEAN DEFAULT 0 COMMENT '包含遛狗服务',
    special_requirements TEXT COMMENT '特殊要求说明',
    FOREIGN KEY (service_id) REFERENCES tb_services(service_id)
) COMMENT '寄养服务信息表';

create table tb_foster_rooms
(
    foster_room_id     int auto_increment comment '寄养房间ID'
        primary key,
    room_number        int                                  not null comment '房间号',
    room_image         varchar(255)                         not null comment '房间照片',
    room_type          varchar(50)                          not null comment '房型',
    brand_name         varchar(100)                         not null comment '品牌',
    max_pets_per_room  int        default 1                 null comment '单间最大宠物数量',
    current_pets_count int        default 0                 null comment '当前房间宠物数量',
    is_available       tinyint(1) default 1                 null comment '房间是否可用',
    create_time        timestamp  default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '寄养房间信息表';

CREATE TABLE tb_foster_orders (
    foster_order_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '寄养订单ID',
    order_id INT NOT NULL COMMENT '关联主订单ID',
    pet_id INT NOT NULL COMMENT '关联宠物ID',
    checkin_date DATE NOT NULL COMMENT '入住日期',
    checkout_date DATE NOT NULL COMMENT '退房日期',
    daily_care_times INT DEFAULT 2 COMMENT '每日照顾次数',
    special_notes TEXT COMMENT '特别注意事项',
    current_status varchar(50) DEFAULT 'Pending',
    FOREIGN KEY (order_id) REFERENCES tb_orders(order_id),
    FOREIGN KEY (pet_id) REFERENCES tb_pets(pet_id)
) COMMENT '寄养订单详细信息表';

CREATE TABLE tb_breeds (
                           breed_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '品种ID，主键，自增',
                           breed_name VARCHAR(100) NOT NULL COMMENT '宠物品种名称，不可为空',
                           species VARCHAR(50) NOT NULL COMMENT '宠物种类，不可为空'
) COMMENT '宠物品种表';

CREATE TABLE tb_foster_room_types (
                                      room_type_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '房间类型ID，主键，自增',
                                      room_type_name VARCHAR(50) NOT NULL COMMENT '房间类型名称，不可为空',
                                      room_number INT NOT NULL COMMENT '房间号，不可为空',
                                      max_pets_per_room INT DEFAULT 1 COMMENT '单间最大宠物数量'
) COMMENT '寄养房间类型表';
-- 服务类型表 (ServiceTypes)
/*
表名：tb_service_types
注释：
- service_type_id：服务类型ID，主键，自增
- service_type_name：服务类型名称，不可为空
- description：服务类型描述（可空）
- created_at：记录创建时间，默认为当前时间
- created_by：记录创建人ID
- updated_at：记录更新时间，默认为当前时间，更新时自动更新
- updated_by：记录更新人ID
*/
CREATE TABLE tb_service_types (
                                  service_type_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '服务类型ID，主键，自增',
                                  service_type_code VARCHAR(50) NOT NULL COMMENT '服务类型编码，不可为空',
                                  service_type VARCHAR(100) NOT NULL COMMENT '服务类型(0-常规，1-寄养)'
) COMMENT '服务类型表';

create table tb_policy
(
    policy_id   bigint auto_increment comment '策略id'
        primary key,
    policy_name varchar(30)                         null comment '策略名称',
    discount    int                                 null comment '策略方案，如：80代表8折',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_time timestamp default CURRENT_TIMESTAMP null comment '修改时间',
    constraint tb_policy_policy_name_uindex
        unique (policy_name)
)
    comment '策略表';


