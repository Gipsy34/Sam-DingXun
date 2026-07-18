-- 创建Mysql数据库和表
Create Database If Not Exists app_game_data Character Set UTF8;
use app_game_data;

-- 创建管理员表
create table tb_admin (
    id varchar(32) comment '管理员主键',
    username varchar(20) comment '用户名',
    password varchar(20) comment '密码',
    name varchar(12) comment '姓名',
    tele varchar(11) comment '电话',
    PRIMARY KEY (`id`)
)comment '管理员表';-- 创建用户表
create table tb_user (
    id varchar(32) comment '用户主键',
    username varchar(20) comment '用户名',
    password varchar(20) comment '密码',
    name varchar(18) comment '姓名',
    avatar varchar(255) comment '头像',
    gender varchar(10) comment '性别',
    age int comment '年龄',
    tele varchar(11) comment '电话',
    biaoq varchar(32) comment '游戏标签',
    gamesId varchar(32) comment '喜欢的游戏',
    youx double comment '账号总价值',
    gameh double comment '总游戏时长',
    PRIMARY KEY (`id`)
)comment '用户表';-- 创建游戏热点表
create table tb_newsInfo (
    id varchar(32) comment '游戏热点主键',
    showpic varchar(255) comment '资讯首图',
    showtitle varchar(255) comment '资讯标题',
    biaoq varchar(32) comment '标签',
    showdesc TEXT comment '内容简介',
    showdetail TEXT comment '详情',
    publishtime datetime comment '发布时间',
    vv varchar(255) comment '更多信息',
    PRIMARY KEY (`id`)
)comment '游戏热点表';-- 创建玩家动态表
create table tb_gameData (
    id varchar(32) comment '玩家动态主键',
    showpic varchar(255) comment '首图',
    showtitle varchar(25) comment '标题',
    gameid varchar(32) comment '游戏',
    yongh varchar(32) comment '用户',
    biaoq varchar(32) comment '标签',
    showdesc varchar(30) comment '描述',
    showdetail TEXT comment '我的成就详情',
    hours int comment '游戏时长H',
    publishtime date comment '发布时间',
    vv varchar(255) comment '展示',
    PRIMARY KEY (`id`)
)comment '玩家动态表';-- 创建开源社区表
create table tb_userShare (
    id varchar(32) comment '开源社区主键',
    showpic varchar(255) comment '首图',
    showtitle varchar(25) comment '分享标题',
    youx varchar(32) comment '游戏',
    fabr varchar(32) comment '发布人',
    showdesc varchar(30) comment '简介',
    showdetail TEXT comment '详情',
    publishtime date comment '发布时间',
    vv varchar(255) comment '操作视频',
    PRIMARY KEY (`id`)
)comment '开源社区表';-- 创建Game Library表
create table tb_games (
    id varchar(32) comment 'Game Library主键',
    name varchar(18) comment '游戏名',
    youxjj varchar(255) comment '游戏简介',
    company varchar(255) comment '发布公司',
    hots int comment '玩家人数',
    fabsj date comment '发布时间',
    PRIMARY KEY (`id`)
)comment 'Game Library表';-- 创建Creative Workshop表
create table tb_sucai (
    id varchar(32) comment 'Creative Workshop主键',
    name varchar(18) comment '素材名称',
    youx varchar(32) comment '游戏',
    jianj varchar(255) comment '简介',
    files varchar(255) comment '素材文件',
    createtime datetime comment '游戏时间',
    PRIMARY KEY (`id`)
)comment 'Creative Workshop表';-- 创建游戏标签表
create table tb_tagInfo (
    id varchar(32) comment '游戏标签主键',
    name varchar(18) comment '标签名',
    description varchar(255) comment '标签说明',
    PRIMARY KEY (`id`)
)comment '游戏标签表';-- 创建公告表
create table tb_notice (
    id varchar(32) comment '公告主键',
    title varchar(255) comment '标题',
    content varchar(255) comment '内容',
    createtime date comment '发布时间',
    PRIMARY KEY (`id`)
)comment '公告表';-- 创建评论表
create table tb_userComment (
    id varchar(32) comment '评论主键',
    userid varchar(255) comment '用户编号',
    username varchar(255) comment '用户名',
    rolech varchar(255) comment '用户角色',
    content varchar(255) comment '内容',
    createtime date comment '发布时间',
    ctid varchar(255) comment '内容编号',
    type varchar(255) comment '内容类型',
    status varchar(255) comment '状态',
    PRIMARY KEY (`id`)
)comment '评论表';

create table tb_chat (
    id varchar(40) comment '主键',
    sid varchar(255) comment '发送者',
    rid varchar(255) comment '接收者',
    stype varchar(255) comment '发送者类型',
    rtype varchar(255) comment '接收者类型',
    content varchar(255) comment '内容',
    flag varchar(255) comment '是否已读',
    createtime datetime comment '发送时间',
    PRIMARY KEY (`id`)
)comment '聊天表';

create table tb_star (
    id varchar(255) comment '主键',
    itemid varchar(255) comment 'Favorite项',
    itemtype varchar(255) comment 'Favorite类型',
    userid varchar(255) comment '用户',
    userrole varchar(255) comment '角色',
    type varchar(10) comment 'Like或Favorite',
    createtime datetime comment '创建时间',
    PRIMARY KEY (`id`)
)comment 'Favorite表';

-- 创建用户并授权
CREATE USER 'app_game_data'@'%' IDENTIFIED BY 'app_game_data';
GRANT ALL ON app_game_data.* TO 'app_game_data'@'%';

