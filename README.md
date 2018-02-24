# Kingdee K3Cloud Web Api
为方便使用单独写在这里，使用方法可以<a href="https://github.com/cashlifei/Kingdee-K3Cloud-Web-Api/wiki">查看文档</a>。
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
