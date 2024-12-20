# GradientTextView
## 支持字体渐变色，支持加渐变色粗边

[![](https://jitpack.io/v/FlyJingFish/GradientTextView.svg)](https://jitpack.io/#FlyJingFish/GradientTextView)
[![GitHub stars](https://img.shields.io/github/stars/FlyJingFish/GradientTextView.svg)](https://github.com/FlyJingFish/GradientTextView/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/FlyJingFish/GradientTextView.svg)](https://github.com/FlyJingFish/GradientTextView/network)
[![GitHub issues](https://img.shields.io/github/issues/FlyJingFish/GradientTextView.svg)](https://github.com/FlyJingFish/GradientTextView/issues)
[![GitHub license](https://img.shields.io/github/license/FlyJingFish/GradientTextView.svg)](https://github.com/FlyJingFish/GradientTextView/blob/master/LICENSE)


<img src="https://github.com/FlyJingFish/GradientTextView/blob/master/screenshot/show.png" width="320px" height="420px" alt="show" />

## 特色功能

## 新增继承 [PerfectTextView](https://github.com/FlyJingFish/PerfectTextView)

**继承[PerfectTextView](https://github.com/FlyJingFish/PerfectTextView) 可使用其所有功能，您可前往查看如何使用[点这里查看](https://github.com/FlyJingFish/PerfectTextView)**

1，支持前景字体渐变色，支持设置渐变方向

2，支持设置字体背景粗边，粗边支持渐变色

3，同时支持以上两点混合

4，所有颜色支持 ColorStateList 


## 第一步，根目录build.gradle

```gradle
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
## 第二步，需要引用的build.gradle （最新版本[![](https://jitpack.io/v/FlyJingFish/GradientTextView.svg)](https://jitpack.io/#FlyJingFish/GradientTextView)）

```gradle
    dependencies {
        implementation 'com.github.FlyJingFish:GradientTextView:1.2.5'
    }
```
## 第三步，使用说明

**前景渐变颜色示例**

```xml
<com.flyjingfish.gradienttextviewlib.GradientTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_margin="10dp"
    android:text=" Hello World! "
    android:textColor="@color/white"
    android:textSize="30sp"
    app:gradient_angle="0"
    app:gradient_endColor="@color/white"
    app:gradient_startColor="@color/teal_200"
```

**粗边渐变颜色示例**

```xml
<com.flyjingfish.gradienttextviewlib.GradientTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_margin="10dp"
    android:text=" Hello World! "
    android:textColor="@color/white"
    android:textSize="30sp"
    app:gradient_stroke_angle="0"
    app:gradient_stroke_strokeWidth="6dp"
    app:gradient_stroke_textColor="@color/purple_700" />
```

**前景粗边渐变颜色混合设置示例**

```xml
<com.flyjingfish.gradienttextviewlib.GradientTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_margin="10dp"
    android:text=" Hello World! "
    android:textColor="@color/white"
    android:textSize="30sp"
    app:gradient_angle="0"
    app:gradient_endColor="@color/white"
    app:gradient_startColor="@color/teal_200"
    app:gradient_stroke_angle="0"
    app:gradient_stroke_strokeWidth="6dp"
    app:gradient_stroke_textColor="@color/purple_700" />
```

**番外：如果你需要自定义加粗的粗细程度，只需要将粗边的颜色配置和字体一样，然后就可以随意设置粗边宽度了，如此一来加粗效果就是自定义的了**

### XML 属性一览

| attr                        |     format      |              description              |
|-----------------------------|:---------------:|:-------------------------------------:|
| gradient_startColor         | color/reference |             前景字体渐变颜色开始颜色              |
| gradient_centerColor        | color/reference |             前景字体渐变颜色中心颜色              |
| gradient_endColor           | color/reference |             前景字体渐变颜色结束颜色              |
| gradient_angle              |      float      |             前景字体渐变颜色开始角度              |
| gradient_rtl_angle          |     boolean     |        前景字体渐变颜色开始角度是否支持镜像Rtl适配        |
| gradient_stroke_startColor  | color/reference |             字体粗边渐变颜色开始颜色              |
| gradient_stroke_centerColor | color/reference |             字体粗边渐变颜色中心颜色              |
| gradient_stroke_endColor    | color/reference |             字体粗边渐变颜色结束颜色              |
| gradient_stroke_angle       |      float      |             字体粗边渐变颜色开始角度              |
| gradient_stroke_rtl_angle   |     boolean     |        字体粗边渐变颜色开始角度是否支持镜像Rtl适配        |
| gradient_stroke_strokeWidth |    dimension    |               字体粗边画笔宽度                |
| gradient_stroke_textColor   | color/reference | 字体粗边颜色（设置粗边渐变色之后此属性无效,设置此属性后粗边渐变色也失效） |
| gradient_stroke_join        |      enum       |   字体粗边样式 round/bevel/miter 具体效果自行尝试   |

### 代码配置

请自行探索代码中设置颜色的配置方法


## 最后推荐我写的另外一些库

- [OpenImage 轻松实现在应用内点击小图查看大图的动画放大效果](https://github.com/FlyJingFish/OpenImage)

- [AndroidAOP 一个注解就可请求权限，禁止多点，切换线程等等，更可定制出属于你的 Aop 代码](https://github.com/FlyJingFish/AndroidAOP)

- [主页查看更多开源库](https://github.com/FlyJingFish)


