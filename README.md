## 钉钉群机器人
项目封装了钉钉群机器人的发送方法，只需要输入ACCESS_TOKEN和SECRET，选择合适的发送类型，就可以一键发送。
## 食用

 1. 添加pom依赖
 		

	```java
	 	<repositories>
	        <repository>
	            <id>jitpack.io</id>
	            <url>https://www.jitpack.io</url>
	        </repository>
	    </repositories>
	     <dependencies>
	        <dependency>
	            <groupId>com.gitee.godchin</groupId>
	            <artifactId>dingding_Utils</artifactId>
	            <version>1.0.0</version>
	        </dependency>
	     </dependencies>
	```

 

 1. 获取access_token和秘钥
![钉钉机器人](https://img-blog.csdnimg.cn/a3dc774451084bee8039582deafcd74d.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBASVRkZnE=,size_20,color_FFFFFF,t_70,g_se,x_16)
https://oapi.dingtalk.com/robot/send?access_token=####
获取access_token:
 2. 调用DingDingService.send()方法发送消息，在参数中设置access_token和秘钥。
更多发送参数，参考：[钉钉机器人文档](https://open.dingtalk.com/document/app#/serverapi2/qf2nxq)
 3. 测试

	```java
	 @Test
	    void test()  {
	        TextMsg textMsg = new TextMsg();
	        textMsg.setSecret(SECRET);
	        textMsg.setAccessToken(ACCESS_TOKEN);
	        textMsg.setAt(new TextMsg.At(null, null, false));
	        textMsg.setText(new TextMsg.Text("sdsarwhfnsduhfsbhfiadfdusfwefwe"));
	        DingResult send = DingDingService.send(textMsg);
	        System.out.println(send);
	    }
	```
结果：
![结果](https://img-blog.csdnimg.cn/4582fb55e20b4729bedba54e3a8470a2.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBASVRkZnE=,size_20,color_FFFFFF,t_70,g_se,x_16)
![机器人发送消息](https://img-blog.csdnimg.cn/601dbf17a3ed4ae8ba641877d91ae15b.png)
源码地址：[码云](https://gitee.com/godchin/dingding_Utils)
