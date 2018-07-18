# 代码混淆压缩比，在0~7之间，默认为5,一般不下需要修改
-optimizationpasses 5
# 混淆时不使用大小写混合，混淆后的类名为小写
-dontusemixedcaseclassnames
# 不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
#忽略警告
-ignorewarning
 #优化  不优化输入的类文件
    -dontoptimize
     #混淆时是否做预校验
    -dontpreverify
     #混淆时是否记录日志
    -verbose
     # 混淆时所采用的算法
    -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
    #保护注解
    -keepattributes *Annotation*
     #如果引用了v4或者v7包
    -dontwarn android.support.**
    #保持 native 方法不被混淆
    -keepclasseswithmembernames class * {
        native <methods>;
    }
    #保持自定义控件类不被混淆
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
    }
    #保持自定义控件类不被混淆
    -keepclassmembers class * extends android.app.Activity {
       public void *(android.view.View);
    }
     # 保持自定义控件类不被混淆
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
    }
    # 保持自定义控件类不被混淆
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet, int);
    }
    #保持 Parcelable 不被混淆
    -keep class * implements android.os.Parcelable {
      public static final android.os.Parcelable$Creator *;
    }

    #保持 Serializable 不被混淆
    -keepnames class * implements java.io.Serializable
    #保持 Serializable 不被混淆并且enum 类也不被混淆
    -keepclassmembers class * implements java.io.Serializable {
        static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        !static !transient <fields>;
        !private <fields>;
        !private <methods>;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
    }
    #保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
    #-keepclassmembers enum * {
    #  public static **[] values();
    #  public static ** valueOf(java.lang.String);
    #}

    -keepclassmembers class * {
        public void *ButtonClicked(android.view.View);
    }
    #不混淆资源类
    -keepclassmembers class **.R$* {
        public static <fields>;
    }
    #避免混淆泛型 如果混淆报错建议关掉
    #–keepattributes Signature


-keep class **.R$* {*;}
-keep class **.R{*;}
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.res

-keep class com.tencent.** {*;}
-keep class com.qq.** {*;}
-keep class com.sina.**{*;}
-keep class org.apache.**{*;}
-keep class android.net.** {*;}
-keep class com.sijla.** {*;}

-keep class org.xmlpull.v1.** { *;}
-dontwarn org.xmlpull.v1.**

# ShareSDK
-keep class cn.sharesdk.**{*;}
-keep class com.mob.**{*;}

# 高德地图
-keep class com.amap.api.**{*;}
-keep class com.autonavi.**{*;}

# 信鸽
-keep class com.lc.xinge.** {*;}
-keep class com.jg.** {*;}
-keep class lc.com.wup.** {*;}

# 直播
-keep class com.google.android.exoplayer2.** {*;}

# X5浏览器
-keep class MTT.ThirdAppInfoNew {*;}

# AChartEngine
-keep class org.achartengine.** {*;}

# 支付宝
-keep class com.alipay.** {*;}
-keep class ta.utdid2.** {*;}
-keep class ut.device.** {*;}
-keep class org.json.** {*;}


# 讯飞
-keep class com.iflytek.** {*;}