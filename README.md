# Kingdee K3Cloud Web Api
为方便使用单独写在这里，使用方法可以<a href="https://github.com/cashlifei/Kingdee-K3Cloud-Web-Api/wiki">查看文档</a>。
在使用过程中，<span style="color:red">如遇到以下错误</span>，请检查并输出相应的http请求响应内容是否为版权提示（{"Message":"还剩26天，当前您的软件处于未授权或版本使用期满，为确保您的权益不受侵害，请及时购买正版！\n电话销售：4008 830 830","MessageCode":....,"STimeZone":{"OffsetTicks":288000000000,"StandardName":"(UTC+08:00)北京，重庆，香港特别行政区，乌鲁木齐","Id":230,"Number":"1078_SYS","CanBeUsed":true}},"KDSVCSessionId":null,"FormId":"BOS_CloudInspection","RedirectFormParam":null,"FormInputObject":26,"ErrorStackTrace":null,"Lcid":0,"AccessToken":null,"KdAccessResult":null,"IsSuccessByAPI":true}）：<br/>

java.lang.ClassCastException: java.lang.Class cannot be cast to java.lang.String<br/>
java.lang.ClassCastException: java.lang.Class cannot be cast to java.util.List<br/>
本项目默认使用httpclient，部分方法模块已经过时，可采用项目中封装的其他http（jodd,okhttp）实现，<br/>
  public static RequestExecutor<String, String> create(RequestHttp requestHttp) {<br/>
    switch (requestHttp.getRequestType()) {<br/>
      case APACHE_HTTP:<br/>
        return new ApacheSimplePostRequestExecutor(requestHttp);<br/>
      case JODD_HTTP:<br/>
        return new JoddHttpSimplePostRequestExecutor(requestHttp);<br/>
      case OK_HTTP:<br/>
        return new OkHttpSimplePostRequestExecutor(requestHttp);<br/>
      default:<br/>
        return null;<br/>
    }<br/>
  }<br/>
