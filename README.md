# virgo-spring-security
最新版 Spring Security 、OAuth2 使用案例，持续更新中，目前已完成OpaqueToken 基于内存存储模式的授权服务， OpaqueToken资源服务

### 授权服务获取token 请求 

POST http://localhost:1000/oauth/token?username=user&password=123456&grant_type=password
content-type: application/x-www-form-urlencoded
authorization: Basic dGVzdDp0ZXN0

### 资源服务器请求接口

GET http://localhost:2000/user/list
Authorization:bearer 5f419cc2-1bf9-47b2-8ae1-16b9df5f988f
