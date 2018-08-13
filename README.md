## 欢迎使用代码生成器Code-Builder

[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.gitee.hengboy/spring-boot-starter-mybatis-pageable/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.gitee.hengboy/spring-boot-starter-mybatis-pageable)

### 欢迎关注公众号

![微信公众号](http://resource.hengboy.com/image/qrcode.jpg)
关注微信公众号，回复`加群`，获取交流群群号。

`MyBatis-Pageable`是一款自动化分页的插件，基于`MyBatis`内部的插件`Interceptor`拦截器编写完成，拦截`Executor.query`的两个重载方法计算出分页的信息以及根据配置的数据库`Dialect`自动执行不同的查询语句完成总数量的统计。

### 支持的数据库
`MyBatis-Pageable`目前支持的主流数据库：
	1. DB2
    2. Derby
    3. DM、
    4. H2、
    5. HSQL、
    6. InforMix、
    7. Mariadb、
    8. MySQL、
    9. Oracle、
    10. Postgres、
    11. SqlLite、
    12. SqlServer2000以上版本

### 快速开始
`MyBatis-Pageable`可以在传统的`Spring`项目内使用，也可以在最新`SpringBoot`项目内使用。

#### SpringBoot集成
`MyBatis-Pageable`目前已经上传到`Maven`中央仓库，你可以随时配置依赖使用，如果你是`Maven`管理的项目，复制如下代码到你的`pom.xml`配置文件内：
```
<dependency>
     <groupId>com.gitee.hengboy</groupId>
     <artifactId>spring-boot-starter-mybatis-pageable</artifactId>
     <version>{lastVersion}</version>
 </dependency>
```
如果你使用的`Gradle`管理的项目，那么你需要复制如下的代码到你们的`build.gradle`文件内：
```
compile 'com.gitee.hengboy:mybatis-pageable:{lastVersion}'
```
> 注意：上面内容需要把`{lastVersion}`改成目前最新版本的`MyBatis-Pageable`版本号
##### 配置插件
添加完成	`starter`依赖后，你可以在配置文件`application.yml`或者`application.properties`内完成配置，下面是`yml`格式配置：
```
hengboy:
  pageable:
      dialect: mysql
```
在使用`pageable`时你仅仅需要配置一个数据库方言，如果你使用`MySQL`或者`MariaDB`数据库你可以不配置数据库方言。

#### 传统Spring集成
##### 使用mybatis-config.xml 配置插件
如果你项目中配置使用了`mybatis-config.xml`，那么你需要添加如下代码到你的配置文件：
```
<plugins>
    <plugin interceptor="com.gitee.hengboy.mybatis.pageable.interceptor.MyBatisExecutePageableInterceptor">
        <!-- 配置数据库方言 -->
        <property name="dialect" value="mysql"/>
	</plugin>
</plugins>
```

##### 使用application.xml 配置插件
当然你如果不使用`mybatis-config.xml`配置文件配置插件，也可以通过`SqlSessionFactoryBean`的插件列表来完成集成`MyBatis-Pageable`，如下配置：
```
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
  <!-- 配置插件列表 -->
  <property name="plugins">
    <array>
      <bean class="com.gitee.hengboy.mybatis.pageable.interceptor.MyBatisExecutePageableInterceptor">
        <property name="properties">
          <!-- 配置数据库方言 -->
          <value>
            dialect=mysql
          </value>
        </property>
      </bean>
    </array>
  </property>
</bean>
```

### 怎么使用？
目前`MyBatis-Pageable`使用比较简单，有一个分页请求类`PageableRequest`来完成自动分页操作，我们来看个简单的示例：
```
Page<UserEntity> page = PageableRequest.of(1, 5).request(() -> userMapper.selectAll());
```

- `of`  配置分页的`当前页码`以及`每页的限制条数`
- `request` 该方法需要传递一个夜晚逻辑方法，也就是你需要执行分页的方法

#### Page对象详解
在上面简单的一行代码就可以完成自动分页以及读取出分页相关的信息，分页执行后我们通过`Page`对象都可以获取到什么内容呢？
- `data` 分页后的数据列表，具体的返回值可以使用`Page<T>`泛型接收
- `totalPages` 总页数
- `totalElements` 总条数
- `pageIndex` 当前页码
- `pageSize` 每页限制条数
- `offset` 分页开始位置
- `endRow` 分页结束位置
- `hasNext` 是否存在下一页，`true`：存在，`false`：不存在
- `hasPrevious` 是否存在上一页，`true`：存在，`false`：不存在
- `isFirst` 是否为首页，`true`：首页，`false`：非首页
- `isLast` 是否为末页，`true`：末页，`false`：非末页

#### 翻页查询
实际开发过程中存在这种情况，虽然传递的分页页码为`1`，但是种种判断过后我需要查询`上一页`或者`下一页`、`首页`的数据，这时候你就可以`PageableRequest`对象的`next()`、`previous()`、`first()`方法来处理这种事情的发生，如下示例：
```
Pageable pageable = PageableRequest.of(2, 10);
        if (xx = xx) {
            pageable.next();
        }
        Page<UserEntity> page = pageable.request(()->userMapper.selectAll());
```
上面是`翻页到下一页`的查询示例，当然这个功能是为了尽可能的方便分页的使用，同样的`previous()`、`first()`方法都可以这么使用。

