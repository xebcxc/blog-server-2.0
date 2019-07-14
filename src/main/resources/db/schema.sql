drop table article if exists ;
drop table tag if exists ;

CREATE TABLE `article` (
  `id` int(50) NOT NULL auto_increment PRIMARY KEY,
  `article_id` varchar(100) NOT null,
  `thumb` varchar(600) DEFAULT NULL,
  `title` varchar(200) NOT NULL,
  `introduce` varchar(400) DEFAULT NULL,
  `tags` varchar(200) DEFAULT NULL,
  `content` varchar(500),
  `visit` int(11) DEFAULT '0' ,
  `compliment` int(11) DEFAULT '0' ,
  `publish` tinyint(1) DEFAULT '0',
  `create_time` date ,
  `modify_time` date
);


CREATE TABLE `tag` (
  `id` int(50) NOT NULL auto_increment PRIMARY KEY ,
  `tag_name` varchar(100) DEFAULT NULL,
  `article_ids` varchar(600) NOT NULL,
  `create_time` date ,
  `modify_time` date
);


