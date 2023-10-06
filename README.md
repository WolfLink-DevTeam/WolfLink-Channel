# WolfLink Channel - 绫狼跨服频道

![开源协议](https://img.shields.io/github/license/Vanilla-Non-Benefit-Community/VanillaGlobalChannel?style=for-the-badge)
![Stars](https://img.shields.io/github/stars/Vanilla-Non-Benefit-Community/VanillaGlobalChannel?style=for-the-badge)
![Last Commit](https://img.shields.io/github/last-commit/Vanilla-Non-Benefit-Community/VanillaGlobalChannel?style=for-the-badge)
[![Star History Chart](https://api.star-history.com/svg?repos=Vanilla-Non-Benefit-Community/VanillaGlobalChannel&type=Date)](https://star-history.com/#Vanilla-Non-Benefit-Community/VanillaGlobalChannel&Date)

## 写在前面

关于这个异地跨服聊天系统，有什么建议或者Bug的话，最好是发issues里面哦

如果不想发issue，嫌麻烦可以直接加我QQ 3401286177 私聊反馈给我也可以，看到就会回复

停更了很长一段时间，最近回来打算再重构一下然后更新一些内容

## 开发理念

国内Minecraft服务器有数千个，但多数服务器仍处在 "与世隔绝" 的环境中，
因为没有足够的玩家基数导致服务器内的聊天功能似乎成了可有可无的存在。
不论服务器内容有多么新颖有趣，如果没有条件积累起足够数量的玩家，
想必这样的服务器在现在的环境里很难长久下去。
即使是希望与其他服务器合办联谊活动或之类的，也要承担相应的风险。

> 1-5-1: 每个MCBBS的会员只允许有一个服务器宣传帖，每个服务器、群组服、联合团队只允许有一个宣传帖，
> 同一账号或同一服务器、群组服、联合团队发布超过一个主题帖时，超额的帖子将直接删帖发卡警告并判为违规多帖宣传。

至于什么是联合团队嘛...? 大家心知肚明就好了。
https://www.mcbbs.net/thread-1234992-1-1.html
这个插件旨在帮助那些落单的服务器联系起来，
让服务器的聊天系统再次热闹起来，让玩家们不那么孤单。

**另外也祝各位 早日摆脱泥潭**

## 功能简介

香草跨服聊天频道，是一个真正意义上的**MC跨服聊天**系统，
传统的跨服聊天插件基于**Bungeecord/Velocity**等群组核心本身进行消息广播，
然而，这个插件则是基于**Springboot**(新版本基于Solon)搭建的中央消息广播服务器实现其跨服聊天的功能。
因此，服务器与服务器之间即使不处于同一个群组环境下，或者不处于同一个网络环境中，
仍然能够实现跨服聊天！
一定要找个什么东西来比喻一下的话，这就像是MC内置的简易聊天室吧，就像早期的QQ那样。
目前支持的服务端：
Bungeecord/Bukkit/Velocity/Nukkit
所有基于Bukkit/Nukkit的服务端理论上都能够正常使用！例如Catserver，Purpur，Mohist，Spigot, NukkitX, PNX…

## 使用效果

![image](https://user-images.githubusercontent.com/77883323/173993829-7ef82ba4-ab3c-4b8a-9205-df129dedd2da.png)

## 部署方式

(提示：Bungeecord服务端需要在服务端启动脚本里面添加 -Dfile.encoding=UTF-8 启动参数，否则中文会乱码哦)

**不同服务端插件生成的配置文件夹名字不同**
Bukkit服务端：BukkitVGC
Nukkit服务端：NukkitVGC
Bungeecord服务端：Bungeecord-VanillaGlobalChannel
Velocity服务端：velocityglobalchannel
**但是配置文件内容一致！**

### 接入大型公开聊天服务器(普通服主推荐方案！)

目前存在的大型公开聊天服务器如下：

官方公益频道：(2023/10/6留)  
> IP：180.188.16.207  
> Http端口：1454  
> WebSocket端口：11454  
> 请联系 QQ 3401286177 申请账号密码，需要提供服务器宣传帖等信息

XXXXX搭建的频道：xx.xx.xx.xx:xxxxx
(如果有谁搭建起其他公共频道可以联系我帮忙写到帖子里宣传)

1. 下载VanillaGlobalChannel-Plugin插件，根据自己服务端类型下载对应的插件。
2. 将插件安装到你的服务端，重启服务端一次，然后关闭，会生成相应的插件配置文件。
3. 联系中央服务器供应方，获取你的服务器ID，账号，密码并在配置文件中填写，修改中央服务器IP，按需要修改一下其他配置，然后保存。
4. 再次启动群组，不出意外的话，至此应该成功连接上你自己的中央服务器了。

### 搭建自己专属的聊天服务器(推荐中大型服务器团队采用该方案！)

1. 下载VanillaGloalChannel-Springboot整合包，放到你的服务器(主机/vps)上。
2. 配置好整合包文件夹内的application.yml，确保对应的端口开放了TCP协议！
3. 右键 启动.bat 用记事本打开，修改java路径，需要使用java17及以上。
4. 双击启动，至此，你专属的中央聊天转发服务器已经部署完毕
5. 下载VanillaGlobalChannel-Plugin插件，根据自己服务端类型下载对应的插件。
6. 将插件安装到你的服务端，重启服务端一次，然后关闭，会生成相应的插件配置文件。
7. 填写好你自己的服务器ID，账号，密码，修改中央服务器IP，按需要修改一下其他配置，然后保存。
8. 再次启动群组，不出意外的话，至此应该成功连接上你自己的中央服务器了。

## 如何下载

在Github-Release栏目里找到最新版本，按需下载即可~

## 关于源码

**开源协议** GNU Affero General Public License v3.0
[https://choosealicense.com/licenses/agpl-3.0/#]

点点繁星，终将汇成星河
如果觉得好用，请记得回来给咱们点一个 Star 哦~
开源不易，每一颗星星都能鼓励我们更加坚定的前行！
