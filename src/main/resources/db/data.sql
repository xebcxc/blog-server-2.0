INSERT INTO article (id, article_id, thumb, title, topic, introduce, tags, content, visit, compliment, publish, is_delete, create_time, modify_time) VALUES (220, '6106a86bd5624b25a25366867a239ded', '/api/static/image/2019-9-20-23-56-43-523.jpg', '算法菜单之复杂度分析', '', '来呀，一起来看算法呀', '231', '> 困而学，学而知
>
> 站在巨人的肩膀上

在前面的一篇关于[二分查找算法](https://meisen.pro/article/5cb09abee7fe413f9b112664060a7e85)的文章中，简单提了二分查找的算法的算法时间复杂度是`O(logn)`。但是对于什么是算法的复杂度，文中并没有提。为了能够更好理解后面讲解的文章，今天就来就先来学学算法的复杂度。

首先，我们先来说说为什么需要复杂度分析？

在写了一段代码之后，我们总会想着对代码进行优化。而要进行优化，就不得不进行系统分析。至于怎么分析法，我们都会很简单的想到，直接运行一遍就得了，查看运行的耗时。不错，这样的分析很简单，也很直接。但这个有一个很致命的弊端，就是总被代码运行的环境因素制约。这段代码在我的老式电脑上面运行可能要`1minutes`，而在大家的顶配MacBookPro上面却只要`1second`。还有一个弊端就是很受数据规模的制约，比如说我们在本地开发环境中，数据只有`100+`，运行起来超快，只需要`1second`，但是在生产服务器上，数据却有`1million+`，运行起来却要`1minutes`。这些场景其实在我们平常的开发中很常见。

那么，怎么客观的来分析一段代码的复杂度呢？这里就需要引入两个概念： `时间复杂度`、`空间复杂度`。

## 时间复杂度

说算法复杂度之前，我们先来一个概念：大O复杂度表示法。

大O复杂度表示法描述了***所有代码的执行时间T(n)与每行代码的执行次数成正比***，如果用数学公式来展示，就变成了下述的公式。

```
T(n)=O(∫(n))
```

我们来解释一下公式。`T(n)`表示代码执行的时间；n表示数据规模的大小；`∫(n)`表示每行代码执行的次数总和。因为这是一个公式，所以用`∫(n)`来表示。公式中的`O`，表示代码的执行时间`T(n)`与`∫(n)`表达式成正比。

先来看看两个代码片段

```java

public int cal(int n) {
  //  片段1
   int sum1 = 0; 			// 执行1次
   int k = 1;					// 执行1次
   for (; k <= n; ++i) {// 执行n次
     sum1 = sum1 + k;			// 执行n次
   }

  // 片段2
   int sum2 = 0;			// 执行1次
   int i = 1;				// 执行1次
   int j = 1;				// 执行1次
   for (; i <= n; ++i) {// 执行n次
     j = 1;							// 执行n次
     for (; j <= n; ++j) { // 执行n²次
       sum2 = sum2 +  i * j; // 执行n²次
     }
   }

   return sum1 + sum2;
 }
}

```

我们用前面的数学公式来各自分析这两个代码片段的执行时间T(n)是多少。

代码片段1：片段1的所有代码的执行次数总和是 `2n +2` 也就是 `∫(n)=2n+2`，那么它的执行时间就是`T(n)=O(2n+2)`

代码片段2：片段2的所有代码的执行次数总和是`2n²+2n+3`也就是`∫(n)=2n²+2n+3`,那么它的执行就是`T(n)=O(2n²+2n+3)`

下面我们可以引出时间复杂度的定义了。

其实`T(n)=O(2n+2)`和`T(n)=O(2n²+2n+3)`就是**大O时间复杂度表示法**。大O时间复杂度实际上并不具体表示代码真正的执行时间，而是表示**代码执行时间随数据规模增长的变化趋势**，所以，也叫作**渐进时间复杂度**（asymptotic time complexity），简称**时间复杂度**。

> 上述两个代码片段和概念定义出自极客时间专栏《数据结构和算法之美》

这些好像和平常看到的算法时间复杂度不太一样啊，平常看到不是`O(n)`、`O(n²)`这种形式的吗？这里为什么是这样子的呢？客官不急，且听在下慢慢道来



### 时间复杂度的分析

现在唠叨一些分析时间复杂度的三个原则。

- **只关注循环执行次数最多的一段代码**

  在上述的时间复杂度的定义中讲了`时间复杂度`只是描述了执行时间随数据规模增长的变化趋势，既然是趋势，那么公式中的常量、低级、系数就不需要了，我们就需要一个最高阶就好了。那么代码片段1中最终的时间复杂度就是`O(n)`，代码片段2中的最终的时间复杂度就是`O(n²)`。

- **加法法则：总复杂度等于量级最大的那段代码的复杂度**

  还是上面的代码片段1和代码片段2，这两个代码片段都是在一个方法里面，那么这个方法的时间复杂度是多少呢？还是将这个方法所有代码的执行次数综合加起来就得到`∫(n)=2n²+2n+3 + 2n+2`。由于只是关心趋势，所以就需要取最高阶就好了。也就是说这个方法的时间复杂度就是`O(n²)`

- **乘法法则：嵌套代码的复杂度等于嵌套内外代码复杂度的乘积**

  现在来只关心代码片段2，可以看到片段2中有一个嵌套循环，很容易就能想到嵌套最里面的代码的执行时间需要乘以嵌套外面的代码的执行次数。在代码片段2中就是*n*²。



> 虽然大神都说不用记忆，但是如果最开始不记忆，后面也不能融会贯通



## 常见的时间复杂度实例

说到时间复杂度实例，这里又有两个量级分类：*多项式量级*和*非多项式量级*

非多项式量级: O(2n)、O(n!)。只有这两项。这种量级很低效

多项式量级：常量阶：O(1)、对数阶：O(logn)、线性阶：O(n)、线性对数阶: O(nlogn)、平方阶： O(n²) 、K次方阶：O(n²)



### 关于常量阶O(1)

一般情况下，只要算法中不存在循环语句、递归语句，即使有成千上万行的代码，其时间复杂度也是Ο(1)。

### 关于对数阶O(logn)和线性对数阶O(nlogn)

很常见的一种时间复杂度，二分查找的时间复杂度就是O(logn)。

```java
 i=1;
 while (i <= n)  {
   i = i * 2;
 }
```

来看上面的代码片段，`while`循环中，每循环一次 `i`就乘以`2`，每次循环都会距离`n`更近一步。假设第`x`次循环之后，`i`就大于`n`了，上面的代码片段也就执行完了。 这时候的执行次数是$$2^x$$大于n，用数学公式表示就是

$$2^x$$ = n。

![](/api/static/image/2019-9-20-23-59-29-568.png)

这里的x就是执行次数，从上面的内容知道*执行时间*和代码的*执行次数*成正比，那就是说只要知道了`x`的值是多少，就可以知道上述代码的时间复杂度。到了这一步就很简单了，经历过高考的我们都知道x=$$log_2$$n，也就是T(n)=O($$log_2$$n)。在时间复杂度的世界里就被记做：T(n)=O(logn)。

这里有个问题，底数2去了哪里？

因为对数之间是可以互相转换的，$$log_3$$n就等于$log_3$2 * $$log_2$$n，所以O(n) $$log_3$$= O(C *$$log_2$$n)，其中C=$$log_3$$2是一个常量。前面再讲时间复杂度分析的时候讲过使用大O复杂度表示法的时候，可以忽略常量等系数的，也就是说O(C*∫(n))=O(∫(n))的。既然不算以什么为底数都可以转换成O(C * $$log_2$$n)，那还不如吧底数去掉，直接记为O(logn)。

如果一段代码的时间复杂度是O(logn)，我们循环执行n遍，时间复杂度就是O(nlogn)了。而且，O(nlogn)也是一种非常常见的算法时间复杂度。比如，归并排序、快速排序的时间复杂度都是O(nlogn)。



## 空间复杂度分析

类比前面的时间复杂度以及时间复杂度的定义，可以很容易的推断出空间复杂度以及空间复杂度的定义。

空间复杂度，全程`渐进空间复杂度（asymptotic space complexity）`，标示算法的存储空间与数据规模之间的增长关系。

常见的空间复杂度O(1)、O(n)、O(n2 )', 0, 0, 1, 0, '2019-09-20 23:58:20', '2019-09-21 00:02:49');

INSERT INTO `article`(`id`,`article_id`, `thumb`, `title`, `topic`, `introduce`, `tags`, `content`, `visit`, `compliment`, `publish`, `create_time`, `modify_time`)
VALUES (201,'182ffdf6c60f4aaa9fd002d641bed96c', '/api/static/image/eef36de7114c3fddc116624be01dd09f.jpeg',
 'heptt', '技术','http', '101,102', '```java
public final class String \n
    implements java.io.Serializable, Comparable<String>, CharSequence {
	  /** 用来保存字符的数组. */
    private final char value[];

    /** 缓存String的hash值 */
    private int hash; // Default to 0
}
```', 88, 1, 1, parsedatetime('17-09-2018 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS') , parsedatetime('17-09-2018 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'));

INSERT INTO `article`(`id`,`article_id`, `thumb`, `title`, `topic`, `introduce`, `tags`, `content`, `visit`, `compliment`, `publish`, `create_time`, `modify_time`)
VALUES (202,'209429c5e71c40d89d4f18addc3f5f61', '/api/static/image/7214ca91b2e39ddea00a0a8e8b2c4ae8.jpg', '先给自己立一个小目标','杂谈', '2018年还剩下不到5个月了，趁着这5个月，我要给自己立一个小目标',
 '102', 'dasd', 13, 2, 1, parsedatetime('18-04-2018 12:07:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('18-05-2018 12:07:52.69', 'dd-MM-yyyy hh:mm:ss.SS'));

INSERT INTO `article`(`id`,`article_id`, `thumb`, `title`,`topic`, `introduce`, `tags`, `content`, `visit`, `compliment`, `publish`, `is_delete`, `create_time`, `modify_time`)
VALUES (203,'371fd4fcccaf49ebb7debfcfad879ffd', '/api/static/image/3f2b43d65d49144c909c8d237b251123.jpg', 'Hello Blog','技术',
 '新的旅程开始了！。', '103,104',
'sdjasl', 78, 1, 1, 0,parsedatetime('18-04-2019 12:07:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('18-04-2019 12:07:52.69', 'dd-MM-yyyy hh:mm:ss.SS'));

INSERT INTO `article`(`id`,`article_id`, `thumb`, `title`,`topic`, `introduce`, `tags`, `content`, `visit`, `compliment`, `publish`,`is_delete`, `create_time`, `modify_time`)
VALUES (204,'371fd4fcccaf49ebb7desfcfad879ffd', '/api/static/image/3f2b43d65d49144c909c8d237b251123.jpg', 'Hello Blog','技术',
'乘风破浪会有时，直挂云帆济沧海。', '105',
 'sdfjsdlafjlasdf', 77, 7, 1,0, parsedatetime('18-07-2018 11:22:33.44', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('19-07-2018 11:55:22.23', 'dd-MM-yyyy hh:mm:ss.SS'));

INSERT INTO `article`(`id`,`article_id`, `thumb`, `title`,`topic`, `introduce`, `tags`, `content`, `visit`, `compliment`, `publish`,`is_delete`, `create_time`, `modify_time`)
VALUES (205,'5a7c6cc7a1aa4013b35bc3eab9e953e9', '/api/static/image/eb548cb4a866e6caa42bda1feb71110c.jpg',
'Git合并两个仓库','技术', '日常学习笔记', '106,107', 'dsfjflsajdfla',
 115, 0, 1, 0, parsedatetime('18-05-2019 06:36:42.23', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('18-04-2019 02:47:52.39', 'dd-MM-yyyy hh:mm:ss.SS'));


INSERT INTO `tag`(`id`, `tag_name`, `article_ids`,`create_time`, `modify_time`)
VALUES (101, 'HTTP', '201', parsedatetime('18-05-2019 06:36:42.23', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('18-04-2019 02:47:52.39', 'dd-MM-yyyy hh:mm:ss.SS'));

INSERT INTO `tag`(`id`, `tag_name`, `article_ids`,`create_time`, `modify_time`)
VALUES (102, '杂谈', '202,201', parsedatetime('18-05-2019 06:36:42.23', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('18-04-2019 02:47:52.39', 'dd-MM-yyyy hh:mm:ss.SS'));

INSERT INTO `tag`(`id`, `tag_name`, `article_ids`,`create_time`, `modify_time`)
VALUES (103, 'nginx', '203', parsedatetime('18-05-2019 06:36:42.23', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('18-04-2019 02:47:52.39', 'dd-MM-yyyy hh:mm:ss.SS'));

INSERT INTO `tag`(`id`, `tag_name`, `article_ids`,`create_time`, `modify_time`)
VALUES (104, '干货', '203', parsedatetime('18-05-2019 06:36:42.23', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('18-04-2019 02:47:52.39', 'dd-MM-yyyy hh:mm:ss.SS'));

INSERT INTO `tag`(`id`, `tag_name`, `article_ids`,`create_time`, `modify_time`)
VALUES (105, 'Hello Blog', '204', parsedatetime('18-05-2019 06:36:42.23', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('18-04-2019 02:47:52.39', 'dd-MM-yyyy hh:mm:ss.SS'));

INSERT INTO `tag`(`id`, `tag_name`, `article_ids`,`create_time`, `modify_time`)
VALUES (106, '笔记', '205', parsedatetime('18-05-2019 06:36:42.23', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('18-04-2019 02:47:52.39', 'dd-MM-yyyy hh:mm:ss.SS'));

INSERT INTO `tag`(`id`, `tag_name`, `article_ids`,`create_time`, `modify_time`)
VALUES (107, 'Git', '205', parsedatetime('18-05-2019 06:36:42.23', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('18-04-2019 02:47:52.39', 'dd-MM-yyyy hh:mm:ss.SS'));



INSERT INTO `user`(`id`, `account`, `username`,`password`,`role_id`,`is_active`, `create_time`, `modify_time`)
VALUES (10001, '', 'admin', '928bfd2577490322a6e19b793691467e', null, 1, parsedatetime('18-05-2019 06:36:42.23', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('18-04-2019 02:47:52.39', 'dd-MM-yyyy hh:mm:ss.SS'));




