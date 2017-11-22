![](https://github.com/Lorry0822/KotlinTest/blob/master/md_img/banner.png)
# 前言
- 这个项目是我用来学习 **Kotlin** 语言开发的项目。
- 项目内容借鉴了网上很多的 **MovieGuide**。
- 项目采用 **MVP** 模式架构，其中使用到了 **Dagger2**、**DataBinding**、**Retrofit**、**RxJava**、**Glide** 等第三方库。
- 项目采用了 **Material Design** 界面设计。

# 效果

![](https://github.com/Lorry0822/KotlinTest/blob/master/md_img/preview.gif)

# 技术点
- 基于 **Dagger2**、**DataBinding** 封装了 **BaseActivity**、**BaseFragment**，并在此基础上添加右滑返回功能封装了 **BaseSwipeBackActivity**。
- 基于 **DataBinding**、**RecyclerView** 封装了 **BaseRvAdapter**、**BaseRvViewHolder**，并且使用反射技术自动生成 **ViewHolder** 对象，使用时只需让 **Adapter** 继承 **BaseRvAdapter**、**ViewHolder** 继承 **BaseRvViewHolder** 即可。
- 使用 **SwipeToLoadLayout** 配置刷新功能。

# 致谢
- 这里要感谢[Aspsine/SwipeToLoadLayout](https://github.com/Aspsine/SwipeToLoadLayout)，项目中使用的 **SwipeToLoadLayout** 控件就是参考于他，将 **Java** 代码转换成 **Kotlin** 代码。
- 同样要感谢

This repository is used to learn Kotlin
