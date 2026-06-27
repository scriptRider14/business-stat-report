business-stat-report
业务统计报表后端项目
项目介绍
本项目是基于 SpringBoot 3 + MyBatis + MySQL 开发的收支业务统计报表后端系统，实现收支明细管理、时间段汇总统计、同期环比分析能力，接口规范化封装，集成在线接口文档、全局异常统一处理，可直接对接前端页面完成数据可视化报表展示。
技术栈
核心框架：SpringBoot 3.x
持久层：MyBatis
数据库：MySQL 8.x
接口文档：Knife4j (OpenAPI3)
工具：Lombok 简化实体类代码
功能模块
收支明细分页查询
GET / POST 两种传参方式
支持部门名称、收支类型、起止时间多条件筛选分页
按日 / 按月收支汇总统计
自动分组统计每日 / 每月总收入、总支出、单据笔数
适配折线图、柱状图前端数据渲染
同期环比增长率分析
输入本期时间区间，自动推算上一周期数据
计算收入、支出、净收支三项环比增长率，空数据自动容错，避免空指针异常
项目结构
plaintext
src/main/java/com/report/business_report_backend
├─ common                  // 公共通用类
│  ├─ Result.java          // 全局统一返回结果封装
│  └─ GlobalExceptionHandler.java  // 全局统一异常处理器
├─ config
│  └─ Knife4jConfig.java   // Knife4j 在线接口文档配置
├─ controller              // 控制层，接收前端请求
├─ dto                     // 请求入参封装类
├─ entity                  // 数据库实体、分页封装对象
├─ mapper                  // MyBatis 数据访问接口
├─ service                 // 业务接口层
│  └─ impl                 // 业务实现层
└─ vo                      // 接口返回视图对象

resources
├─ mapper/*.xml            // MyBatis SQL映射文件
└─ application.yml         // 项目配置文件（数据库、MyBatis配置）
接口访问说明
在线接口文档地址
plaintext
http://localhost:8080/doc.html
可在线查看全部接口、填写参数调试请求，直观查看返回数据格式。
核心接口清单
表格
请求方式	接口地址	功能说明
GET / POST	/api/record/page	收支明细多条件分页查询
GET / POST	/api/record/report/date	按日 / 按月收支汇总统计
GET / POST	/api/record/report/compare	同期收支环比分析
部署运行步骤
创建 MySQL 数据库，执行业务记录表建表语句
修改 application.yml 内数据库连接地址、账号、密码
启动主类 BusinessReportBackendApplication
访问 http://localhost:8080/doc.html 调试验证接口
打包部署：mvn clean package -DskipTests，运行 Jar 包部署服务器
统一返回格式
成功响应
json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
异常响应
json
{
  "code": 500,
  "msg": "系统内部异常，请稍后重试",
  "data": null
}

项目展示

<img width="1914" height="989" alt="image" src="https://github.com/user-attachments/assets/fd32b6dd-216d-4b09-9c6c-3a57dc2eef34" />

<img width="1919" height="1014" alt="image" src="https://github.com/user-attachments/assets/93b79873-69a2-4926-bd96-7cd50c4589c7" />
<img width="1860" height="903" alt="image" src="https://github.com/user-attachments/assets/5ea20fd9-cd1c-44af-b3c3-b09be4bb5747" />
<img width="1794" height="947" alt="image" src="https://github.com/user-attachments/assets/0aa5000f-789b-440e-989f-df9c78543676" />
<img width="1889" height="995" alt="image" src="https://github.com/user-attachments/assets/fa31e6e2-3150-40b2-a585-c275c3f58ea1" />
<img width="1894" height="926" alt="image" src="https://github.com/user-attachments/assets/d0092f7b-bd79-446e-b79b-0b2a413b1b29" />
<img width="1851" height="863" alt="image" src="https://github.com/user-attachments/assets/1e4e5213-47a1-45da-8706-9c2d7ed6120c" />
<img width="1919" height="928" alt="image" src="https://github.com/user-attachments/assets/9e77e2ea-cc27-4a96-a185-974bb55f4e54" />
<img width="1888" height="943" alt="image" src="https://github.com/user-attachments/assets/fd2e4977-9c67-4ae3-9868-75e4b71512d1" />
<img width="1885" height="964" alt="image" src="https://github.com/user-attachments/assets/13cf94b2-4e48-482f-99a9-0a9cc9dc682a" />
