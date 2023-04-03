# GradientTextView
## 支持字体渐变色，支持加渐变色粗边

[![](https://jitpack.io/v/FlyJingFish/GradientTextView.svg)](https://jitpack.io/#FlyJingFish/GradientTextView)
[![GitHub stars](https://img.shields.io/github/stars/FlyJingFish/GradientTextView.svg)](https://github.com/FlyJingFish/GradientTextView/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/FlyJingFish/GradientTextView.svg)](https://github.com/FlyJingFish/GradientTextView/network)
[![GitHub issues](https://img.shields.io/github/issues/FlyJingFish/GradientTextView.svg)](https://github.com/FlyJingFish/GradientTextView/issues)
[![GitHub license](https://img.shields.io/github/license/FlyJingFish/GradientTextView.svg)](https://github.com/FlyJingFish/GradientTextView/blob/master/LICENSE)


<img src="https://github.com/FlyJingFish/GradientTextView/blob/master/screenshot/Screenshot_20221012_141234.jpg" width="405px" height="842px" alt="show" />

## 特色功能

## 新增继承 [PerfectTextView](https://github.com/FlyJingFish/PerfectTextView)

**继承[PerfectTextView](https://github.com/FlyJingFish/PerfectTextView) 可使用其所有功能，您可前往查看如何使用[点这里查看](https://github.com/FlyJingFish/PerfectTextView)**

1，支持前景字体渐变色，支持设置渐变方向

2，支持设置字体背景粗边，粗边支持渐变色

3，同时支持以上两点混合


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
        implementation 'com.github.FlyJingFish:GradientTextView:1.0.8'
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

### 属性一览

| attr                        |  format   |       description       |
|-----------------------------|:---------:|:-----------------------:|
| gradient_startColor         |   color   |      前景字体渐变颜色开始颜色       |
| gradient_centerColor        |   color   |      前景字体渐变颜色中心颜色       |
| gradient_endColor           |   color   |      前景字体渐变颜色结束颜色       |
| gradient_angle              |   float   |      前景字体渐变颜色开始角度       |
| gradient_rtl_angle          |  boolean  | 前景字体渐变颜色开始角度是否支持镜像Rtl适配 |
| gradient_stroke_startColor  |   color   |      字体粗边渐变颜色开始颜色       |
| gradient_stroke_centerColor |   color   |      字体粗边渐变颜色中心颜色       |
| gradient_stroke_endColor    |   color   |      字体粗边渐变颜色结束颜色       |
| gradient_stroke_angle       |   float   |      字体粗边渐变颜色开始角度       |
| gradient_stroke_rtl_angle   |  boolean  | 字体粗边渐变颜色开始角度是否支持镜像Rtl适配 |
| gradient_stroke_strokeWidth | dimension |        字体粗边画笔宽度         |
| gradient_stroke_textColor   |   color   |  字体粗边颜色（设置渐变色之后此属性无效）   |

### 常见问题

1，如果使用粗边可能会存在左右两侧被切除一部分，可在字符串两侧添加空格解决问题


# 最后推荐我写的另一个库，轻松实现在应用内点击小图查看大图的动画放大效果

- [OpenImage](https://github.com/FlyJingFish/OpenImage) 

- [主页查看更多开源库](https://github.com/FlyJingFish)


