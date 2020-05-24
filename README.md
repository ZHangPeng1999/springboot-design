# 项目描述
实现毕业设计双向选择：  
具体功能说明：     
+ 教师：   
        1. 登录，导入多门课程excel表格     
        2. 为课程设置加权分数    
        3. 设置实际指导学生数    
        4. 设置加权后，有资格学生范围    
        5. 查看当前已接收学生    
        6. 各参数可修改   
        7. 设置毕设方向，设置毕设方向权值
+ 学生：   
        1.输入学号显示曾经选修课程  
        2.提交    
        3.匹配    
        4.达到最大数关闭提交  
        5.后台并发判断    
         
# 开发环境
+ idea2019.3
+ git 2.22.0
+ springboot 2.2.5
# 开发过程
## 2020.3.10   
Init    
## 2020.3.12   
### 1.根据需求完成以下实体类的设计： 
1. 学生类
    + id为学号 
    + 姓名属性 （在填写报名时可提供校验）
    + isSelected 表示根据课程成绩等内容计算后是否能被选择
    + teacher 与教师类 根据是否被选择 有一个 MangToOne的映射
    + courseElective 与课程类有一个中间表elective 根据中间表维护与课程ManyToMany的关系
2. 教师类
    + id 
    + password 教师身份校验
    + SelectStudentNum 能够选择的学生数
    + WantStudentNum 希望选择的学生上限
    + student 与学生的ManyToOne 映射  
    + courses 教师创建的课程   
    + directions 教师创建的方向
3. 课程类
    + id
    + name 名称
    + minGrade 最低分数线
    + value 权重
    + courseElective 与选课表的映射关系
4. courseElective(选课表)
    + id
    + grade 成绩
    + detail 描述
    + student
    + course~~
### 2.完成以上实体类的jpaRepository
说明：为解决jpaRepository没有refresh()方法，自定义一个继承自jpaRepository的接口
### Target:
完成方向需求的实体类设计与jpa接口实现
## 2020.3.13
完成方向类 方向与学生中间类的编写  
## 2020.3.22    
Service层：       

+ 教师：   
    1.添加/修改教师信息    
    2.添加/修改教师信息     
    3.添加/修改课程   
    4.添加/修改方向   
    5.添加内定学生    
    6.确认学生是否能够被选择
+ 学生:   
    1.添加学生  
    2.选择教师   
    3.修改学生信息      
    4.建立课程与学生的联系   
    5.建立方向与学生的联系

## 2020.3.31
调整service层部分逻辑错误    
## 2020.5.19
### 调整实体类
实体类如下：
学生类，教师类与用户类建立一对一的关系，将学生类与教师类共有属性添加至用户类。 
+ id        
+ name  
+ number 学号/工号  
+ role 身份   
添加方向类与方向跟学生之间的映射类，大致属性同课程类与课程学生映射类
### 调整服务类
+ 将原本的学生服务，教师服务调整为 用户服务跟课程服务    
+ 将对用户的操作放在用户服务中，对课程，方向的操作放置在课程服务中  
### 添加公用组件
+ MyToken：用于前后端传递token信息   
    + 三个final属性 防止拼写错误  
    + authorization 前后端传递的token信息，具体内容即id与role  
+ EncryptComponent：用于加密解密向前端传递的内容，暂时只有token向前端传递需要加密 因此只包含对token的加密解密方法
+ RequestComponent：为方便获得HTTPRequest传递的id，role  
+ InitComponent： 初始化管理员用户
### 添加拦截器
+ LoginIntercept：拦截request请求，解密前端携带的authorization,将用户id，role添加到request携带内容中，便于后端处理数据时使用id，role
+ WebMvcConfig: 声明拦截器拦截策略，因为用户id，role需要通过LoginIntercept获取，因此除了登录请求所有的请求都需要通过LoginIntercept拦截

## 2020.5.24
### 添加控制类
+ LoginCollector 登录 获取账号密码 登录账号 将Authorization和Role返回给前端
+ StudentCollector 学生控制类 包含学生权限可获取的一系列资源，可进行的一系列修改
+ TeacherCollector 教师控制类 包含教师权限可获取的一系列资源，可进行的一系列修改
+ AdminCollector 管理员控制类     管理员权限课获取的一系列资源，可进行的一系列修改
### 调整服务类
对于部分需要但之前没有写入的服务类方法，进行添加。
