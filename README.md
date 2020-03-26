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
        + course
### 2.完成以上实体类的jpaRepository
说明：为解决jpaRepository没有refresh()方法，自定义一个继承自jpaRepository的接口
### Target:
完成方向需求的实体类设计与jpa接口实现
## 2020.3.13
完成方向类 方向与学生中间类的编写  
## 2020.3.22    
业务逻辑层：       

+ 教师：   
    1.添加/修改教师信息    
    2.添加/修改教师信息     
    3.添加/修改课程   
    4.添加/修改方向 
+ 学生:   
    1.添加学生  
    2.选择教师   
    3.修改学生信息      
    4.建立课程与学生的联系   
    5.建立方向与学生的联系