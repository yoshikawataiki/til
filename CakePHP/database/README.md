## MySQLの利用

### MySQLの起動
```
mysql.server start
```

### MySQLの停止
```
mysql.server stop
```

### DBの作成
```
create database set_nutritionist_cakephp;
grant all on set_nutritionist_cakephp.* to dbuser@localhost identified by 'hfi32uo854';
use set_nutritionist_cakephp

create table posts (
  id int unsigned auto_increment primary key,
  body text,
  created datetime default null,
  modified datetime default null
);
insert into posts (body, created, modified) values
('body 1', now(), now()),
('body 2', now(), now()),
('body 3', now(), now());

select * from posts;
```
