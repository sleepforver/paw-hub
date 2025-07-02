# 🐾 PetCareMaster - 宠物管理系统

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)
![Redis](https://img.shields.io/badge/Redis-6.0-red.svg)
![License](https://img.shields.io/badge/License-MIT-blue.svg)

**基于 Spring Boot 3.x + Vue 3.x 的现代化宠物管理平台**

</div>

## 📖 项目简介

PetCareMaster（爪聚）是一个功能完善的宠物管理系统，专为宠物店、宠物医院、宠物寄养中心等机构设计。系统采用前后端分离架构，基于 Spring Boot 3.x 和 Vue 3.x 技术栈构建，提供宠物管理、预约服务、健康记录、订单管理等核心功能。

### ✨ 核心特性

- 🐕 **宠物管理**：完整的宠物信息管理，包括基本信息、健康记录、疫苗接种等
- 📅 **预约服务**：灵活的预约系统，支持多种服务类型预约
- 💊 **健康管理**：AI 驱动的宠物健康分析和建议
- 🏠 **寄养服务**：专业的寄养房间管理和服务
- 💰 **订单管理**：完整的订单流程和支付管理
- 👥 **用户管理**：多角色用户权限管理
- 📊 **数据统计**：实时数据分析和报表
- 🤖 **AI 智能**：集成多种 AI 模型，提供智能服务

## 🏗️ 技术架构

### 后端技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.4.4 | 主框架 |
| Spring Security | 6.2.0 | 安全框架 |
| MyBatis | 3.0.3 | ORM 框架 |
| MySQL | 8.0.33 | 数据库 |
| Redis | 6.0+ | 缓存数据库 |
| Druid | 1.2.21 | 数据库连接池 |
| JWT | 0.9.1 | 身份认证 |
| Swagger | 3.0.0 | API 文档 |
| Spring AI | 1.0.0-M6 | AI 集成 |

### 前端技术栈

- **Vue 3.x** - 渐进式 JavaScript 框架
- **Element Plus** - Vue 3 组件库
- **Vite** - 构建工具
- **TypeScript** - 类型安全
- **Pinia** - 状态管理

## 📁 项目结构

```
pet-test/
├── pet-admin/          # 管理后台模块
├── pet-manager/        # 业务管理模块
├── pet-framework/      # 核心框架模块
├── pet-common/         # 公共工具模块
├── pet-generator/      # 代码生成模块
├── pet-quartz/         # 定时任务模块
├── pet-system/         # 系统管理模块
├── sql/               # 数据库脚本
├── doc/               # 项目文档
└── bin/               # 启动脚本
```

### 模块说明

| 模块 | 功能描述 |
|------|----------|
| `pet-admin` | 系统管理后台，提供管理端接口和功能 |
| `pet-manager` | 核心业务模块，包含宠物、预约、订单等业务逻辑 |
| `pet-framework` | 系统核心框架，封装通用功能和配置 |
| `pet-common` | 公共模块，存放工具类、通用实体等 |
| `pet-generator` | 代码生成模块，支持一键生成业务代码 |
| `pet-quartz` | 定时任务模块，集成任务调度功能 |
| `pet-system` | 系统管理模块，包含用户、权限、菜单等基础功能 |

## 🚀 快速开始

### 环境要求

- **JDK**: 17+
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **Redis**: 6.0+
- **Node.js**: 16+ (前端开发)

### 1. 克隆项目

```bash
git clone https://github.com/your-username/pet-test.git
cd pet-test
```

### 2. 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE pet DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 导入数据库脚本：
```bash
mysql -u root -p pet < sql/pet.sql
mysql -u root -p pet < sql/quartz.sql
```

### 3. 配置文件

修改 `pet-admin/src/main/resources/application-druid.yml`：

```yaml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://localhost:3306/pet?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        username: your-username
        password: your-password
```

### 4. 启动项目

#### 方式一：IDE 启动
1. 使用 IDEA 或 Eclipse 导入项目
2. 运行 `pet-admin` 模块下的 `PawHubApplication.java`

#### 方式二：命令行启动
```bash
# 编译项目
mvn clean compile

# 启动项目
mvn spring-boot:run -pl pet-admin
```

### 5. 访问系统

- **管理后台**: http://localhost:8700
- **API 文档**: http://localhost:8700/swagger-ui.html
- **数据库监控**: http://localhost:8700/druid

## 📊 核心功能

### 宠物管理
- 宠物基本信息管理
- 宠物健康记录
- 疫苗接种记录
- 宠物图片管理

### 预约服务
- 多种服务类型预约
- 预约状态管理
- 预约时间冲突检测
- 预约提醒功能

### 寄养服务
- 寄养房间管理
- 寄养订单处理
- 房间类型配置
- 寄养服务统计

### 订单管理
- 订单创建和跟踪
- 支付状态管理
- 订单详情记录
- 订单统计分析

### AI 智能服务
- 宠物健康分析
- 智能建议生成
- 多模型支持（OpenAI、Qwen、GLM）
- 健康报告生成

## 🔧 配置说明

### 数据库配置
```yaml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://localhost:3306/pet
        username: root
        password: 123456
```

### Redis 配置
```yaml
spring:
  data:
    redis:
      host: 192.168.10.100
      port: 6379
      database: 0
```

### AI 模型配置
```yaml
spring:
  ai:
    openai:
      api-key: your-api-key
      base-url: https://api.siliconflow.cn/
      chat:
        options:
          model: deepseek-ai/DeepSeek-R1-Distill-Qwen-32B
```

### 文件存储配置
```yaml
dromara:
  x-file-storage:
    default-platform: aliyun-oss-1
    aliyun-oss:
      - platform: aliyun-oss-1
        access-key: your-access-key
        secret-key: your-secret-key
        end-point: oss-cn-shenzhen.aliyuncs.com
        bucket-name: pet-pawhub
```

## 📝 API 文档

系统集成了 Swagger 3.0，启动后可通过以下地址访问 API 文档：

- **Swagger UI**: http://localhost:8700/swagger-ui.html
- **API JSON**: http://localhost:8700/v3/api-docs

### 主要 API 接口

| 模块 | 接口前缀 | 说明 |
|------|----------|------|
| 宠物管理 | `/pet` | 宠物相关操作 |
| 预约管理 | `/appointment` | 预约相关操作 |
| 订单管理 | `/order` | 订单相关操作 |
| 用户管理 | `/user` | 用户相关操作 |
| 健康记录 | `/health` | 健康记录相关操作 |

## 🛠️ 开发指南

### 代码生成

系统提供了代码生成功能，可以快速生成 CRUD 代码：

1. 访问代码生成页面
2. 选择数据表
3. 配置生成参数
4. 生成代码并下载

### 自定义开发

1. **添加新模块**：在 `pet-manager` 模块下创建新的包
2. **数据库设计**：在 `sql/` 目录下添加建表脚本
3. **API 开发**：遵循 RESTful 规范
4. **权限配置**：在系统管理中配置菜单和权限

### 部署说明

#### 开发环境
```bash
mvn spring-boot:run -pl pet-admin
```

#### 生产环境
```bash
# 打包
mvn clean package -Dmaven.test.skip=true

# 运行
java -jar pet-admin/target/pet-admin.jar
```

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🙏 致谢

- 感谢 [RuoYi](http://ruoyi.vip/) 开源框架提供的技术支持
- 感谢所有为项目做出贡献的开发者

## 📞 联系我们

- **项目地址**: [GitHub](https://github.com/your-username/pet-test)
- **问题反馈**: [Issues](https://github.com/your-username/pet-test/issues)
- **邮箱**: your-email@example.com

---

<div align="center">

**如果这个项目对您有帮助，请给个 ⭐️ 支持一下！**

</div>