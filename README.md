# ADAggregation

（我服了你们了，我真的受够了😡😡😡）

近年来，各种各样的广告在各种设备上涌现，包括但不限于你的手机、电脑、以及各种电子产品。可是，一些组织为了赚多一点 广告费，用尽各种各样的手段，什么点击、上滑跳转的广告就算了，扭一扭、摇一摇跳转的广告也想得出来，但凡地球在转你都会跳进异世界是吧。😡

很多用户（尤其长辈）在官方应用商店下载普通 APP 后，手机遭遇疯狂弹窗广告；软件很难卸载干净，广告关不掉，甚至出现超长广告，广告结束立刻接续下一轮轰炸；同一款软件，不同手机上表现不一样，审核环境和普通用户手机运行效果完全不同。

今天这份README就把广告从获取到展示全链路讲清给你，以及去除广告和加入广告（？）的办法。

## 目录

**第一部分 · 广告是怎么走到你面前的**

- [一、广告全链路：广告如何走进你的手机](#一广告全链路广告如何走进你的手机)
    - [1. 广告主侧：投放申请](#1-广告主侧投放申请)
    - [2. 媒体侧：接入广告 SDK](#2-媒体侧接入广告-sdk)
    - [3. 用户侧：广告请求链路](#3-用户侧广告请求链路)
    - [4. 广告渲染与曝光判定](#4-广告渲染与曝光判定)
    - [5. 计费、反作弊、归因与数据回传](#5-计费反作弊归因与数据回传)
- [二、极简总流程](#二极简总流程)
- [三、点击广告之后会飞去哪里](#三点击广告之后会飞去哪里)

**第二部分 · 广告牛皮癣是怎么作恶的**

- [四、流氓 App 的广告弹窗作恶手法](#四流氓-app-的广告弹窗作恶手法)
    - [4.1 现象层：弹窗是怎么"关不掉"的](#41-现象层弹窗是怎么关不掉的)
    - [4.2 审核隐身术：一套代码两种人格](#42-审核隐身术一套代码两种人格)
    - [4.3 后台保活：关掉页面，软件还在偷偷跑](#43-后台保活关掉页面软件还在偷偷跑)
    - [4.4 借系统漏洞阻碍卸载](#44-借系统漏洞阻碍卸载)
    - [4.5 云端风控开关：作恶可远程控制](#45-云端风控开关作恶可远程控制)
    - [4.6 这条灰色产业链怎么赚钱](#46-这条灰色产业链怎么赚钱)

**第三部分 · 怎么办**

- [五、如何去除广告](#五如何去除广告)
    - [法一：系统设置内直接"停用"（无需 root）](#法一系统设置内直接停用无需-root)
    - [法二：用工具"冻结"（适合系统不给停用的情况）](#法二用工具冻结适合系统不给停用的情况)
    - [法三：修改系统 host，屏蔽广告 SDK 相关的域名（需要 Root）（不是很推荐）](#法三修改系统host屏蔽广告sdk相关的域名需要-root不是很推荐)
    - [法四：直接修改 App](#法四直接修改app)
- [六、如何加入广告：AdAggregation 广告聚合 SDK 接入指南](#六如何加入广告adaggregation-广告聚合-sdk-接入指南)
    - [1. 环境要求](#1-环境要求)
    - [2. 添加依赖](#2-添加依赖)
    - [3. 权限](#3-权限)
    - [4. 初始化](#4-初始化)
    - [5. 开屏广告接入](#5-开屏广告接入)
    - [6. 信息流广告接入](#6-信息流广告接入)
    - [7. 视频广告（激励视频）接入](#7-视频广告激励视频接入)
    - [8. 插屏广告接入](#8-插屏广告接入)
    - [9. Banner 广告接入](#9-banner-广告接入)
    - [10. API 一览](#10-api-一览)
    - [11. 支持的广告平台](#11-支持的广告平台)
    - [12. 广告位 ID 配置](#12-广告位-id-配置)
    - [13. 混淆规则](#13-混淆规则)
    - [14. 注意事项](#14-注意事项)
    - [15. 构建与产物](#15-构建与产物)

---

## 一、广告全链路：广告如何走进你的手机

> [!NOTE]
> 前提：这里指**App 内广告（信息流 / 开屏 / 插屏 / 激励视频等）**，主流是 SDK 广告联盟模式（穿山甲、优量汇、快手联盟、AdMob 等）；分为【广告主投放申请】、【媒体 App 接入准备】、【广告请求链路】、【广告渲染曝光】、【后续计费 \& 归因】五大阶段。

### 1. 广告主侧：投放申请

#### 1\. 开户资质申请

广告主在广告平台后台注册账号，提交资质：营业执照、行业许可证、法人信息、落地页 / App 包、隐私协议；平台人工审核，判断行业是否禁投（博彩、违规金融等不能开）。

- 审核通过 → 开通广告账户，充值预算。

#### 2\. 创建广告计划 \+ 广告素材

1. 新建计划：选择**投放目标**（App 下载、表单、激活、商品下单）、定向（地域、年龄、系统、兴趣、设备、人群包）、出价方式（oCPC、oCPM、CPC、CPM）、预算、投放时段。

2. 上传素材：图片 / 视频 / 文案、落地页 / 应用下载链接；

3. **素材预审**：平台审核素材是否违规（夸大宣传、敏感内容），素材不通过无法上线。

#### 3\. 计划上线，等待流量竞价

计划审核通过，状态变为投放中。广告主设置的出价，会参与每次广告请求的实时竞价。

> [!NOTE]
> 广告主这边做完，广告素材已经存放在广告平台素材服务器，等待流量来触发竞价。

---

### 2. 媒体侧：接入广告 SDK

> [!TIP]
> 这一步是**App 开发阶段就要完成**，在用户打开 App 之前就做好，不是每次打开 App 才做。

1. 媒体在广告联盟平台注册媒体账号，提交 App 信息、应用商店链接、隐私政策，**媒体应用审核**。

2. 平台审核通过，分配 App ID、广告位 ID（比如开屏广告位、激励视频广告位）。

3. 开发者集成广告 SDK 到 App 代码中，写好广告位逻辑：

    - 什么时候请求广告（启动 App 时预加载开屏；用户点奖励按钮才加载激励视频）

    - 广告展示时机、关闭按钮、跳过逻辑、广告事件回调（加载成功、曝光、点击、关闭）

4. App 打包上架应用商店；

5. 用户下载安装这个带广告 SDK 的 App。

> [!NOTE]
> 到这里：App 具备拉取广告的能力，广告位 ID、SDK 已经内置在 App 安装包。

---

### 3. 用户侧：广告请求链路

场景举例：用户打开 App，触发【开屏广告预加载】

1. **触发广告请求**
   App 代码调用 SDK 接口：`loadAd(广告位ID)`，发起广告请求。
   SDK 收集合规范围内的设备信息（设备标识、系统版本、网络类型、地域，**必须遵守隐私协议，用户未授权不能拿 IDFA/OAID**），组装请求报文，发给广告联盟服务器。

2. **广告平台流量分发 \& RTB 实时竞价（如果是 RTB 模式）**
   广告联盟收到请求：
   ① 校验媒体、广告位是否有效，过滤无效流量；
   ② 匹配广告主投放计划：筛选符合当前用户定向的所有广告计划；
   ③ **实时竞价**：符合条件的广告主参与竞价，价高者胜出（同时综合预估点击率、转化，不是只看出价）；
   ④ 返回胜出广告的信息：素材地址、广告 ID、曝光监测地址、点击监测地址、广告配置参数。

> [!NOTE]
> 非 RTB 自营广告：平台直接挑选内部广告，不需要多方竞价。

3. **SDK 接收广告返回，本地缓存素材**
   SDK 拿到广告返回数据，开始下载广告素材（视频 / 图片）到手机本地缓存。

> [!IMPORTANT]
> 这里只是**广告加载成功**，**还没有曝光**！加载成功≠曝光。很多激励视频会提前预加载，等用户触发再展示。

---

### 4. 广告渲染与曝光判定

1. **触发广告展示**
   到达预设时机：App 调用 SDK `showAd()`，SDK 在 App 上层渲染广告视图（开屏覆盖页面、弹出插屏、播放激励视频）。

2. **有效曝光监测（核心！计费以有效曝光为准）**
   广告渲染到屏幕后，SDK 开始校验曝光条件（各家联盟标准略有差异，通用规则）：

- 广告**可视面积≥50%**（部分视频要求≥70%）；

- 持续可见时长达标：图片广告一般 1 秒；视频广告 2 秒；

- 页面没有被遮挡、没有后台切走；

- 不是模拟器 / 脚本刷量的虚假流量。

 全部条件满足 → SDK 上报**曝光事件**给广告监测服务器（广告联盟 / 第三方监测 MTA）。

> [!NOTE]
> 上报成功，才算完成【曝光】，广告主开始会计费（CPM 按曝光扣费；CPC/oCPC 曝光不扣费，点击 / 转化才扣）。

> [!TIP]
> 如果广告刚弹出来用户立刻关掉、App 切后台、广告被弹窗遮挡：**不计有效曝光**。

3. 附加：用户行为上报

- 用户点击广告：SDK 上报点击事件，跳转落地页 / App 下载；

- 用户跳过 / 关闭广告：上报关闭事件；

- 激励视频看完：上报播放完成，App 给用户发放奖励。

---

### 5. 计费、反作弊、归因与数据回传

1. **反作弊校验**
   平台对曝光 / 点击做校验：识别模拟器、群控、脚本刷量、重复曝光、设备作弊，过滤虚假曝光，**虚假曝光不计费**。

2. **广告主扣费**
   按广告计划出价模式扣费：

- CPM：千次有效曝光扣费；

- CPC：点击才扣费，曝光免费；

- oCPM/oCPC：按转化目标优化，曝光 / 点击不扣费，激活 / 表单等转化扣费。

3. **归因（针对 App 下载广告）**
   用户点击广告后下载安装 App，归因 SDK 匹配广告点击 ID，判定这个激活来自哪条广告计划，把激活事件回传给广告平台，广告主看到转化数据。

4. **数据报表回传**
   广告主后台：看到曝光量、点击、CTR、激活、成本；
   媒体后台：看到曝光、有效曝光、eCPM、广告收益。

---

## 二、极简总流程

广告主：开户 → 充值 → 建计划 \+ 上传素材 → 素材审核上线 → 等待流量竞价

媒体：注册媒体账号 → 集成 SDK → App 上架

用户侧：打开 App → SDK 发起广告请求 → 平台竞价返回广告 → SDK 下载素材 → 调用展示广告 → 满足可视条件上报有效曝光 → 反作弊校验 → 计费 \+ 归因 \+ 报表

## 三、点击广告之后会飞去哪里

### App 广告点击完整技术链路（包含：Deeplink / 快应用 hap:/// 小程序 / H5 降级逻辑）

> 前提：广告素材（图片 / 视频）本身只是展示资源，**点击不是直接跳转目标地址，先走广告统计服务器**，这是核心。
> 协议清单：`http/https`\(H5\)、`scheme://`\(Deeplink\)、`hap://`\(快应用\)、小程序专属协议。
>
>

#### 完整时序

1. **用户点击广告 View（客户端）**
   App 捕获点击事件，不会立刻跳转。
   客户端先组装点击上报参数：

    ```Plain Text
    广告ID、设备ID、渠道、IP、时间戳、点击位置、包名、系统版本、是否安装目标App、是否支持快应用
    ```

2. **请求广告平台点击追踪接口（HTTP GET/POST）**
   客户端发起网络请求到广告服务商的点击服务器（中转服务器）。
   作用：**记录点击、计费、反作弊、判断用户设备环境，决定最终跳转目标**
   > [!NOTE]
   > 我们看到一瞬间空白页面，很多就是这个中转请求。

3. **广告服务器做决策（核心分支，在这里选择跳转类型）**
   服务器拿到设备信息，按优先级判断跳转方案：

   > 优先级一般：Deeplink 唤起 App \> 快应用 hap:// \> 小程序 \> H5 落地页 \> 应用商店下载页

    - 情况 A：用户**已安装目标 App** → 返回 `xxx://xxx` deeplink 协议，指令客户端尝试唤起 App 指定页面

    - 情况 B：安卓国产机，支持快应用，目标有快应用版本 → 返回 `hap://xxx` 快应用协议，拉起快应用

    - 情况 C：微信 / 支付宝内广告，目标有小程序 → 返回小程序协议，打开小程序

    - 情况 D：上面都不满足 → 返回 https H5 落地页；落地页内再放下载按钮

    - 情况 E：目标是推广 App 且不支持上面方案 → 返回应用商店链接（华为 / 小米商店 / App Store）

4. **服务器返回 302 重定向 / 返回跳转指令给 App 客户端**
   两种常见实现：

    - 网页广告：HTTP `302 Redirect`，浏览器自动跟着跳转目标地址

    - App 原生广告：服务器返回 JSON，里面携带跳转 scheme，App 代码主动执行跳转

5. **客户端执行跳转，并且做降级兜底**

   > 降级逻辑非常关键：协议唤起失败，立刻回退备选方案

    - Deeplink：尝试 `xxx://page` 唤起 App；唤起失败（没装 App）→ 跳应用商店

    - 快应用 `hap://`：系统框架接收 hap 协议，打开快应用；系统无快应用引擎 / 快应用下架 → 跳备用 H5

    - 小程序：宿主（微信 / 支付宝）直接打开小程序，没有降级（非宿主环境不支持）

    - H5：App 内置 WebView 打开，或者唤起系统浏览器；H5 页面内部还可以继续嵌套跳转、再触发 deeplink

6. **到达目标页面 \+ 上报转化事件（后端）**
   成功打开页面 / 完成下载 / 注册 / 下单 → 客户端上报「转化事件」回广告平台
   广告平台：匹配前面的点击记录，结算广告费用。

---

### 精简链路一句话版

> 用户点击广告 → App 上报点击信息给广告服务器 → 服务器校验、反作弊，根据设备环境选择跳转协议（Deeplink /hap 快应用 / 小程序 / H5 / 商店）→ 302 或返回 scheme 给客户端 → 客户端尝试唤起，失败自动降级兜底 → 落地目标页面，后续上报转化。

### 补充几个容易踩坑的细节

1. **点击和转化是分开计费**：点击只记录点击；真正给钱（转化）要等后续事件（安装、注册）上报成功。

2. **反作弊在第 3 步就开始**：服务器会校验 IP、设备指纹、点击频率，机器人点击直接丢弃，不跳转。

3. **协议区别**

    - `scheme://`：Deeplink，自定义 App 协议，所有平台都支持，但需要目标 App 注册该协议

    - `hap://`：**仅国内安卓厂商（华为 / 小米 / OPPO/vivo）快应用引擎支持，iOS 完全无效**

4. **多层跳转套娃**：H5 落地页里面还可以再放 deeplink、hap、下载按钮，形成二次跳转。

### 举个实例：安卓手机点一条游戏广告

1. 用户点击广告卡片

2. App 上报点击到广告平台

3. 服务器检测：安卓，没有安装这款游戏，支持快应用，游戏有快应用版本

4. 返回 `hap://shturl.cc/VeS`

5. 系统拉起快应用，打开游戏快应用页面

6. 用户在快应用点【下载完整版】→ 跳应用商店

7. 安装完成，App 上报安装转化，广告主扣费

## 四、流氓 App 的广告弹窗作恶手法

> 本节整理自 B 站视频 **BV1LN2YY6En6《卸不了，关不掉的流氓广告 APP，被我们抓到作恶证据了！》**（UP 主：差评君，联合逆向技术人员 epcdiy、边亮\_网络安全）。视频复盘的是真实案例：**从官方应用商店下载的正规 App，到了普通用户（尤其是长辈）手机上就变成广告轰炸机**——同一款软件，审核机干净、用户机作恶。技术人员逆向拆包 + 抓包后，拿到了完整的作恶链条。

### 4.1 现象层：弹窗是怎么"关不掉"的

普通用户最先感受到的，就是"这广告怎么关都关不掉"：

| 现象 | 用户侧感受 | 常见实现手法 |
| --- | --- | --- |
| 假关闭按钮 | 点"×"没关掉，反而下载了别的软件 | 关闭区域做误导设计，热区实际绑定的是跳转 / 下载 |
| 超长广告 | 一条广告最长可达 **144 秒**，一个结束立刻接续下一个 | "剧场版"广告串播，无间隔轮播 |
| 误触即弹 | 滑动滑块、点按钮、甚至**手离开屏幕**都会弹广告 | 摇一摇 / 扭一扭 / 上滑跳转，把触摸与体感事件当成"用户主动点击" |
| 双面表现 | 同一款 App，别人的手机很干净，自己的手机广告不停 | 环境检测 + 云端开关，见 4.2 / 4.5 |
| 卸载困难 | 长按图标卸载菜单消失、系统设置闪退 | 利用系统漏洞，见 4.4 |
| 悄悄变卡耗电 | 后台都划掉了，手机还是越来越卡 | 后台保活 + 静默刷量，见 4.3 |

> [!NOTE]
> 摇一摇 / 扭一扭这类"体感跳转"广告，靠的是读取加速度传感器、陀螺仪数据，把手机任何微小晃动都判定成"用户主动点击"——这就是为什么"地球在转你都会跳进异世界"。

### 4.2 审核隐身术：一套代码两种人格

App 内置大量环境检测逻辑，实时判断当前设备是不是审核 / 测试环境，**只有在确认是普通用户时，才打开作恶开关**：

- 检测设备是否虚拟机、是否开启开发者模式、是否挂代理；
- 读取 IP 地址与地区信息，区分审核人员还是真实普通用户；
- **读取手机已安装应用列表**：一旦发现逆向工具、维权投诉、政务举报类 App，立刻收敛恶意行为、减少广告，伪装成正常软件；
- 代码做多层混淆、加密，恶意逻辑动态加载，静态扫描很难识别，从而蒙混过商店审核成功上架。

### 4.3 后台保活：关掉页面，软件还在偷偷跑

为了持续弹广告、刷广告收益，App 会想方设法不让系统回收进程：

- **无声音频保活**：后台循环播放一段完全没有声音的音频，系统误判为音乐类应用，于是不回收它的后台进程，软件就能常驻后台；
- 注册大量组件、监听各类系统广播，被手动划掉后过一会儿又自动拉起；
- **后台静默模拟点击广告**：不需要用户动手就自动完成广告点击，骗取广告主的流量分成，把手机变成"广告肉鸡"。

### 4.4 借系统漏洞阻碍卸载

> [!IMPORTANT]
> 该漏洞仅影响 **Android 12L 之前（系统 SDK 版本 < 32）** 的设备，谷歌在后续版本已修复。但国内存量大量长辈仍在用旧机型，正好落在受影响范围内。

- App 安装时直接注册接近 **4000 个**随机命名的 Activity 组件；
- 当组件数量超过 **3711 个** 即触发系统 BUG：用户长按桌面图标想卸载时，**系统设置 App 直接崩溃，卸载菜单完全弹不出来**；
- 桌面快捷方式卸载失效，而很多长辈并不知道要去「设置 → 应用管理」里卸载，软件就"卸不掉"了；
- 还有软件伪装虚假的"强力卸载"按钮，点下去不是卸载，而是打开 App 继续播广告。

> [!NOTE]
> 这不是传统病毒木马，而是"公开的系统缺陷 + 应用层代码"实现的流氓行为，全程没有 root、也没有高危权限。

### 4.5 云端风控开关：作恶可远程控制

广告弹不弹、弹多少、对哪些人弹，并不完全写死在本地安装包里，而是由远端服务器下发策略：

- 应用商店审核时，服务器下发"干净模式"，软件表现正常；
- 普通用户设备满足触发条件后，云端打开"作恶开关"，开启广告轰炸；
- 版本更新即可换一套规避手段，治理难度大。

### 4.6 这条灰色产业链怎么赚钱

这类 App 本身功能极其简陋，根本不靠软件赚钱，完整灰色链条早已成型：

1. 开发者做一款看起来正常的工具类 App（清理、手电筒、小工具等长辈高频下载品类）；
2. 用 4.2 的环境检测骗过各大应用商店审核，成功上架；
3. 接入广告联盟 SDK，识别到目标用户后疯狂弹窗 + 静默刷点击，赚取广告分成；
4. 产业链分工明确：恶意代码开发者、广告中介、上架运营多方获利；
5. 专门瞄准**中老年群体**：辨别能力弱、容易误触、不懂正确卸载方式，是产业链最喜欢的"精准人群"。

> [!TIP]
> 现实困境：传统静态扫描 + 审核机人工测试，很难捕获这种"动态触发"的恶意行为；安卓碎片化又让大量旧机型漏洞长期无法修补；加上云端远程控制，版本一更新就能换一套规避手段。所以**不要迷信"官方应用商店绝对安全"**。

---

## 五、如何去除广告

目前有效的方法是**从系统层面停用或冻结“快应用服务框架”**，而不是只关掉“快应用中心”。因为快应用的跳转大多由服务框架在底层触发，只关中心入口往往堵不住。

### 法一：系统设置内直接“停用”（无需 root）

这是最安全、最容易操作的方式。不同品牌的路径略有差异，核心都是找到 **“快应用服务框架”**（部分机型叫“快应用引擎”）并把它**强行停止 + 停用/禁用**。

*   **华为/荣耀**：设置 → 应用和服务 → 应用管理 → 搜索“快应用服务框架” → 强行停止 → 停用。同时去应用市场 → 我的 → 快应用管理，关闭“允许唤起快应用”和“停止快应用中心服务”。
*   **小米/Redmi**：设置 → 应用设置 → 应用管理 → 右上角显示系统程序 → 搜索“快应用服务框架” → 强行停止 → 停用（或撤回隐私同意）。
*   **OPPO/一加/realme/vivo**：设置 → 应用管理 → 开启“显示系统进程” → 搜索“快应用引擎” → 强行停止 → 禁用。

做完这一步，大部分网页和 App 试图通过 `quickapp://` 协议唤起快应用的链路就会被直接掐断。建议同时进浏览器设置，关闭“允许网页唤起快应用”或类似的开关。

### 法二：用工具“冻结”（适合系统不给停用的情况）

如果手机系统不允许直接“停用”该框架，可以借助 Shizuku 配合 **冰箱**、**雹** 等工具进行冻结。或者通过 ADB 命令在电脑上操作：`adb shell pm disable-user --user 0 com.miui.hybrid`（小米的包名，其他品牌需查对应包名），这条命令的效果相当于冻结。

> [!NOTE]
> 快应用跳转有时也和**传感器权限**有关。部分“摇一摇”广告就是靠读取陀螺仪/方向传感器数据触发的。在设置 → 隐私 → 权限管理中，找到“身体传感器”或“方向传感器”，把非必要 App 的权限关掉，可以进一步降低晃动跳转的概率。

> [!IMPORTANT]
> 停用或冻结“快应用服务框架”后，你手机里所有依赖快应用的功能（比如部分负一屏卡片、某些 App 内嵌的轻服务）也会一并失效。如果你只想屏蔽特定几个烦人的快应用，可以先去荣耀快服务或对应入口的“唤起管理”里，对单个快应用选择“禁止跳转”。

### 法三：修改系统host，屏蔽广告sdk相关的域名（需要 Root）（不是很推荐）

用修改 hosts 的方式来阻止快应用跳转

对于广告sdk的相关域名，可以通过修改系统 hosts的方式，把 广告域名/IP 重定向到127.0.0.1。

目前已有相关的 Magisk 模块可以实现这类操作。

> [!IMPORTANT]
> 虽然可行，但是一些App通过广告获取奖励的方式可能失效。所以我不推荐这种操作。

### 法四：直接修改App

通过修改App的方式实现去除广告

> [!NOTE]
> 那种上架应用商店但没有实际功能的流氓应用，**只会给你挖个坑，然后让你高高兴兴的跳进去**。
> 
> 而且这种App还会加固，没有实力你根本破不了。即使破了，也没有任何实际功能。

> [!CAUTION]
> 这里先买个关子。网上相应的教程很多，但是公开这一类的方法往往会招致法律问题。
> 
> ### 核心区分：是“过滤”还是“跳过”？
> 
> 这是判断法律风险的关键分界线。
> 
> *   **“过滤”类方法（高风险）**：通过修改服务器返回的数据、拦截网络请求等方式，**使广告根本不加载或不可见**。这直接减损了广告的曝光和计费，司法实践中**普遍被认定为不正当竞争**。
>     *   典型案例：[世界之窗浏览器因过滤腾讯视频贴片广告，被判赔偿**189万余元**](http://media.people.com.cn/n1/2019/0106/c40606-30505828.html)；[ADsafe净网大师被判赔爱奇艺**20万元**](https://www.shzcfy.gov.cn/detail.jhtml?id=10006944)。
> *   **“跳过”类方法（风险相对较低，但仍有争议）**：利用系统无障碍服务，**替代用户自动点击平台自己设置的“跳过”按钮**。广告的展示链路和计费逻辑未被改变，更接近用户意志的自动化执行。
>     *   典型争议：这类工具 [如“李跳跳”收到了律师函，被指涉嫌不正当竞争](https://www.sohu.com/a/714475020_120133310)，但尚未有生效判决明确认定其违法。其开发者通常以“非营利、无经营行为”作为抗辩理由。

---

## 六、如何加入广告：AdAggregation 广告聚合 SDK 接入指南

[事已至此，先听：](.github/music.mp3)

既然这种App已经遍地开花，这个仓库秉承着“打不过就加入”的理念，让我们在自己的App里面也加入广告，丰富应用形式。😈

`library` 模块把 20 家广告平台的 SDK 收敛成统一入口（`AdPlatform` 枚举 + `loadAdByType`），
使用方只需要选择平台、传入广告容器，即可完成开屏广告、信息流广告、插屏广告、Banner（横幅）广告与视频广告（激励视频）的加载，无需逐个平台对接。

| 模块 | 说明 |
| --- | --- |
| `library` | 广告聚合 SDK，包名 `com.FreshingAir.Ad.Aggregation`，最终产物为 AAR |
| `adexample` | 接入示例 App（纯 AndroidX，不含 Compose / Material），演示权限申请与广告加载 |

```
ADAggregation/
├── library/
│   ├── libs/                     各广告平台的本地 AAR（穿山甲、广点通、快手、百度…）
│   ├── src/main/java/com/FreshingAir/Ad/Aggregation/
│   │   ├── AdPlatform.java       平台枚举
│   │   ├── Init.java             各平台 SDK 初始化状态表
│   │   ├── loadAdByType.java     统一加载入口（开屏 / 信息流 / 插屏 / Banner / 视频广告）
│   │   ├── SplashAdCallback.kt   SDK 内部回调出口（开屏）
│   │   ├── InterstitialAdCallback.kt  SDK 内部回调出口（插屏）
│   │   ├── BannerAdCallback.kt   SDK 内部回调出口（Banner）
│   │   ├── RewardVideoAdCallback.kt  SDK 内部回调出口（视频广告）
│   │   ├── LocalAdBridge.kt      回调桥接，使用方在此接管广告结果
│   │   ├── ads/                  各平台实现
│   │   └── utils/Id.java         广告位 ID（均为测试 ID，上线需替换）
│   ├── src/main/AndroidManifest.xml   权限与各平台组件声明（会自动合并给使用方）
│   └── proguard-rules.pro             混淆规则（通过 consumerProguardFiles 传递给使用方）
└── adexample/                    接入示例
```

---

### 1. 环境要求

| 项 | 要求 |
| --- | --- |
| JDK | 17（`library` 的 `compileOptions` 为 Java 17） |
| Gradle / AGP | Gradle 9.4.1 / AGP 9.2.1 |
| minSdk | 24 |
| compileSdk / targetSdk | 37 |
| ABI | `arm64-v8a`、`armeabi-v7a` |

> `library` 以 Java 17 编译，使用方的 `sourceCompatibility` / `targetCompatibility` 不能低于 17，
> 否则编译期读取它的 class 会报 `class file has wrong version 61.0, should be 55.0`。

使用方工程必须开启 AndroidX 与 Jetifier（Tanx 等旧 support 库依赖需要被重写为 androidx）：

```properties
# gradle.properties
android.useAndroidX=true
android.enableJetifier=true
```

---

### 2. 添加依赖

#### 方案 A：源码模块（推荐）

把 `library` 目录放进使用方工程，在 `settings.gradle` 中引入：

```groovy
// settings.gradle
include ':library'

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
        maven { url 'https://developer.huawei.com/repo/' }   // 华为广告
        maven { url 'https://maven.aliyun.com/repository/public' }  // 阿里 Tanx 等
        // library 内部通过坐标引用 libs 下的本地 AAR，必须配置该 flatDir 仓库
        flatDir { dirs "$rootDir/library/libs" }
    }
}
```

```groovy
// app/build.gradle
dependencies {
    implementation project(':library')
}
```

> `library` 里所有依赖都用 `api` 暴露，因此使用方无需重复声明 androidx、okhttp、glide 等。

#### 方案 B：AAR

执行 `./gradlew :library:assembleRelease`，取 `library/build/outputs/aar/library-release.aar` 放入使用方 `libs/`：

```groovy
dependencies {
    implementation files('libs/library-release.aar')
}
```

**注意**：AAR 不带 POM，Gradle 无法解析其传递依赖。采用方案 B 时，还需要：

1. 把 `library/libs/` 下的全部广告平台 AAR 一并拷入使用方 `libs/`，并配置 `flatDir`；
2. 手动补齐 maven 依赖（material、play-services-ads、ads-lite、glide、okhttp、gson、retrofit、Tanx 等）；
3. 自行拷贝混淆规则（见 [13. 混淆规则](#13-混淆规则)）。

依赖较多时，建议把 AAR 发布到私有 Maven 仓库生成 POM，而不是直接 `files()` 引用。

---

### 3. 权限

`library` 的 `AndroidManifest.xml` 已声明所需权限与各平台组件，会随依赖自动合并到使用方 APK，**无需重复声明**。

其中 `READ_PHONE_STATE`、`ACCESS_FINE_LOCATION`、`ACCESS_COARSE_LOCATION`、`POST_NOTIFICATIONS` 属于运行时权限，
需要在启动时主动申请（否则部分平台会因缺少设备标识/位置而填充率下降）。示例不引入第三方权限库，
直接用 AndroidX 的 `registerForActivityResult` 申请：

```kotlin
private val permissionLauncher =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        val granted = result.values.count { it }
        // 无论是否授予都继续加载广告
        loadSplashAd()
    }

// POST_NOTIFICATIONS 仅 Android 13（API 33）及以上需要
val missing = listOf(
    Manifest.permission.READ_PHONE_STATE,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
).plus(
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) listOf(Manifest.permission.POST_NOTIFICATIONS) else emptyList()
).filter { ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }

if (missing.isEmpty()) loadSplashAd() else permissionLauncher.launch(missing.toTypedArray())
```

> 想换成 XXPermissions 等第三方库也可以，只需替换上面这段申请逻辑。

> 存储权限在清单中带 `maxSdkVersion="32"`，Android 13+ 无需申请。

---

### 4. 初始化

统一入口为 `loadAdByType`。`loadSplashAd` / `loadFeedAd` 内部会检查 `Init.adSDKisLoaded`，
对未初始化的平台自动调用 `initSDKByAdPlatform`，因此**通常不需要手动初始化**；
如需提前初始化（例如在 Application 中预热），或需要传入自己的应用 ID，可显式调用：

```java
// 使用 utils/Id.java 中的示例 ID
loadAdByType.initSDKByAdPlatform(context, AdPlatform.CSJ);

// 传入自己的 APP_ID / APP_KEY（appKey 仅 Sigmob、Tanx、OSET 需要；Taptap 的 appId 即 MediaID）
loadAdByType.initSDKByAdPlatform(context, AdPlatform.SIGMOB, "你的APP_ID", "你的APP_KEY");
```

`initSDKByAdPlatform` 未传 ID 时读取 `utils/Id.java` 中对应平台的 `APP_ID` / `APP_KEY`。

---

### 5. 开屏广告接入

#### 5.1 注册回调

SDK 在广告加载成功、关闭、失败或超时时，通过 `SplashAdCallback` 转发到 `LocalAdBridge`，
使用方在这里接管“展示广告”与“进入主页”两件事：

```kotlin
// 广告 View 渲染完成，把它挂到自己的容器里
LocalAdBridge.onSplashAdLoaded = { context, adView ->
    splashAdContainer.addView(adView)
}

// 广告结束（关闭 / 失败 / 超时），进入主页
LocalAdBridge.onSplashAdFinished = { context ->
    startActivity(Intent(context, MainActivity::class.java))
    finish()
}
```

Java 写法（Kotlin 函数类型返回 `Unit`，需要显式返回）：

```java
LocalAdBridge.setOnSplashAdLoaded((context, adView) -> {
    splashAdContainer.addView(adView);
    return kotlin.Unit.INSTANCE;
});
LocalAdBridge.setOnSplashAdFinished(context -> {
    context.startActivity(new Intent(context, MainActivity.class));
    return kotlin.Unit.INSTANCE;
});
```

#### 5.2 加载广告

```java
// container：开屏广告容器（普通 ViewGroup）
// 注意：华为平台必须传 com.huawei.hms.ads.splash.SplashView 作为 container
// 使用 utils/Id.java 中的示例广告位
loadAdByType.loadSplashAd(context, AdPlatform.CSJ, splashAdContainer);

// 传入自己的开屏广告位 ID（传 null / 空串同样回退到示例值）
loadAdByType.loadSplashAd(context, AdPlatform.CSJ, splashAdContainer, "你的开屏广告位ID");
```

#### 5.3 完整流程

```
启动页（申请权限）
      │  注册 LocalAdBridge 回调
      ▼
loadAdByType.loadSplashAd(context, platform, container)
      │
      ├─ onSplashAdLoaded  → 将广告 View 加入容器
      └─ onSplashAdFinished → 进入主页（无论关闭/失败/超时都会回调）
```

#### 5.4 释放

页面销毁时清空回调，避免 SDK 持有已销毁页面的引用：

```kotlin
override fun onDestroy() {
    LocalAdBridge.clear()
    super.onDestroy()
}
```

---

### 6. 信息流广告接入

把 `feedAdContainer` 作为广告容器，直接调用即可，广告内容由 SDK 自行填充：

```java
// 使用 utils/Id.java 中的示例广告位
loadAdByType.loadFeedAd(activity, feedAdContainer, AdPlatform.GDT);

// 传入自己的信息流广告位 ID
loadAdByType.loadFeedAd(activity, feedAdContainer, AdPlatform.GDT, "你的信息流广告位ID");
```

容器建议使用 `FrameLayout` / `LinearLayout`，并保证宽度为屏幕宽度（模板渲染依赖容器宽度）。

---

### 7. 视频广告（激励视频）接入

视频广告即各平台的「激励视频」，加载成功后由 SDK 自动全屏播放，使用方只需注册回调处理奖励发放与页面交互。

#### 7.1 注册回调

视频广告的生命周期通过 `LocalAdBridge` 的 5 个回调转发给页面：

```kotlin
LocalAdBridge.onRewardVideoAdLoaded = { AdLog.append("视频广告：加载成功") }
LocalAdBridge.onRewardVideoAdShow = { AdLog.append("视频广告：开始播放") }
LocalAdBridge.onRewardVideoAdRewarded = { _, name, amount ->
    // 发放奖励，name / amount 为平台返回的激励名称与数量（平台未提供时为空串 / 0）
    AdLog.append("视频广告：发放奖励 name=$name amount=$amount")
}
LocalAdBridge.onRewardVideoAdClose = { AdLog.append("视频广告：关闭") }
LocalAdBridge.onRewardVideoAdError = { _, code, msg ->
    AdLog.append("视频广告：失败 code=$code msg=$msg")
}
```

Java 写法（Kotlin 函数类型返回 `Unit`，需要显式返回）：

```java
LocalAdBridge.setOnRewardVideoAdLoaded(context -> {
    Log.i("Ad", "视频广告加载成功");
    return kotlin.Unit.INSTANCE;
});
LocalAdBridge.setOnRewardVideoAdRewarded((context, name, amount) -> {
    Log.i("Ad", "发放奖励 " + name + " x " + amount);
    return kotlin.Unit.INSTANCE;
});
LocalAdBridge.setOnRewardVideoAdError((context, code, msg) -> {
    Log.e("Ad", "视频广告失败 " + code + " / " + msg);
    return kotlin.Unit.INSTANCE;
});
```

#### 7.2 加载广告

```java
// activity：展示广告的页面（激励视频全屏播放依赖 Activity）
// 使用 utils/Id.java 中的示例广告位
loadAdByType.loadRewardVideoAd(activity, AdPlatform.CSJ);

// 传入自己的激励视频广告位 ID（传 null / 空串同样回退到示例值）
loadAdByType.loadRewardVideoAd(activity, AdPlatform.CSJ, "你的激励视频广告位ID");
```

#### 7.3 完整流程

```
注册 LocalAdBridge 视频广告回调
      │
      ▼
loadAdByType.loadRewardVideoAd(activity, platform[, rewardAdId])
      │
      ├─ onRewardVideoAdLoaded → 广告已就绪（多数平台随即自动展示）
      ├─ onRewardVideoAdShow   → 开始播放
      ├─ onRewardVideoAdRewarded → 观看完成，发放奖励
      ├─ onRewardVideoAdClose  → 广告关闭
      └─ onRewardVideoAdError  → 加载 / 播放失败
```

#### 7.4 释放

页面销毁时清空回调，避免 SDK 持有已销毁页面的引用（`clear()` 会同时清空开屏、视频广告、插屏与 Banner 回调）：

```kotlin
override fun onDestroy() {
    LocalAdBridge.clear()
    super.onDestroy()
}
```

---

### 8. 插屏广告接入

插屏广告（Interstitial）加载成功后由 SDK 自动全屏展示，使用方只需注册回调、发起请求。

#### 8.1 注册回调

```kotlin
LocalAdBridge.onInterstitialAdLoaded = { AdLog.append("插屏广告：加载成功") }
LocalAdBridge.onInterstitialAdShow = { AdLog.append("插屏广告：开始展示") }
LocalAdBridge.onInterstitialAdClose = { AdLog.append("插屏广告：关闭") }
LocalAdBridge.onInterstitialAdError = { _, code, msg ->
    AdLog.append("插屏广告：失败 code=$code msg=$msg")
}
```

Java 写法（Kotlin 函数类型返回 `Unit`，需要显式返回）：

```java
LocalAdBridge.setOnInterstitialAdLoaded(context -> {
    Log.i("Ad", "插屏广告加载成功");
    return kotlin.Unit.INSTANCE;
});
LocalAdBridge.setOnInterstitialAdError((context, code, msg) -> {
    Log.e("Ad", "插屏广告失败 " + code + " / " + msg);
    return kotlin.Unit.INSTANCE;
});
```

#### 8.2 加载广告

```java
// activity：展示广告的页面（插屏全屏展示依赖 Activity）
// 使用 utils/Id.java 中的示例广告位
loadAdByType.loadInterstitialAd(activity, AdPlatform.CSJ);

// 传入自己的插屏广告位 ID（传 null / 空串同样回退到示例值）
loadAdByType.loadInterstitialAd(activity, AdPlatform.CSJ, "你的插屏广告位ID");
```

#### 8.3 完整流程

```
注册 LocalAdBridge 插屏回调
      │
      ▼
loadAdByType.loadInterstitialAd(activity, platform[, interstitialAdId])
      │
      ├─ onInterstitialAdLoaded → 广告已就绪（多数平台随即自动展示）
      ├─ onInterstitialAdShow   → 开始展示
      ├─ onInterstitialAdClose  → 广告关闭
      └─ onInterstitialAdError  → 加载 / 展示失败
```

> 阿里 Tanx、新浪移动联盟的 SDK 未提供插屏接口，对它们调用 `loadInterstitialAd` 会直接回调
> `onInterstitialAdError`；倍孜、启明、米盟、京东、友盟的插屏广告位需在各自后台创建后传入。

---

### 9. Banner 广告接入

Banner（横幅）广告会把广告 View 渲染进使用方传入的容器，使用方只需提供容器、注册回调。

#### 9.1 注册回调

```kotlin
LocalAdBridge.onBannerAdLoaded = { _, container ->
    // 广告 View 已由 SDK 加入 container，可在此调整容器高度
    AdLog.append("Banner 广告：渲染完成，容器子 View=${container.childCount}")
}
LocalAdBridge.onBannerAdError = { _, code, msg ->
    AdLog.append("Banner 广告：失败 code=$code msg=$msg")
}
```

#### 9.2 加载广告

```java
// bannerAdContainer：Banner 容器（FrameLayout / LinearLayout 均可），广告 View 由 SDK 自行加入
// 使用 utils/Id.java 中的示例广告位
loadAdByType.loadBannerAd(activity, bannerAdContainer, AdPlatform.GDT);

// 传入自己的 Banner 广告位 ID（传 null / 空串同样回退到示例值）
loadAdByType.loadBannerAd(activity, bannerAdContainer, AdPlatform.GDT, "你的Banner广告位ID");
```

#### 9.3 完整流程

```
注册 LocalAdBridge Banner 回调
      │
      ▼
loadAdByType.loadBannerAd(activity, container, platform[, bannerAdId])
      │
      ├─ onBannerAdLoaded → 广告 View 已加入容器
      └─ onBannerAdError  → 加载 / 渲染失败
```

> 百度、Sigmob、倍孜、阿里 Tanx、新浪移动联盟的 SDK 未提供公开的 Banner 接口，对它们调用
> `loadBannerAd` 会直接回调 `onBannerAdError`。
> 友盟的 Banner 走「原生横幅」，与信息流一致地用 `UMNativeLayout` 渲染。
> 快手、Taptap、InMobi 的广告位为 `long`，传入非数字字符串时同样回退到示例值。

---

### 10. API 一览

#### `loadAdByType`

| 方法 | 说明 |
| --- | --- |
| `getAdPlatform(String)` | 字符串转 `AdPlatform`，非法参数抛 `IllegalArgumentException` |
| `initSDKByAdPlatform(Context, AdPlatform)` | 初始化指定平台的 SDK，使用示例 APP_ID / APP_KEY |
| `initSDKByAdPlatform(Context, AdPlatform, String appId, String appKey)` | 初始化并传入自定义 APP_ID / APP_KEY，传 `null` 或空串则用示例值 |
| `loadSplashAd(Context, AdPlatform, ViewGroup)` | 加载开屏广告，使用示例广告位；按屏幕方向分发（竖屏支持全部平台，横屏仅 MIMO、TAPTAP） |
| `loadSplashAd(Context, AdPlatform, ViewGroup, String splashAdId)` | 加载开屏广告并传入自定义广告位 ID |
| `loadFeedAd(Activity, ViewGroup, AdPlatform)` | 加载信息流广告，使用示例广告位 |
| `loadFeedAd(Activity, ViewGroup, AdPlatform, String feedAdId)` | 加载信息流广告并传入自定义广告位 ID |
| `loadRewardVideoAd(Activity, AdPlatform)` | 加载视频广告（激励视频），使用示例广告位 |
| `loadRewardVideoAd(Activity, AdPlatform, String rewardAdId)` | 加载视频广告并传入自定义广告位 ID |
| `loadInterstitialAd(Activity, AdPlatform)` | 加载插屏广告，使用示例广告位；加载成功后自动展示 |
| `loadInterstitialAd(Activity, AdPlatform, String interstitialAdId)` | 加载插屏广告并传入自定义广告位 ID |
| `loadBannerAd(Activity, ViewGroup, AdPlatform)` | 加载 Banner（横幅）广告，使用示例广告位；广告 View 由 SDK 加入容器 |
| `loadBannerAd(Activity, ViewGroup, AdPlatform, String bannerAdId)` | 加载 Banner 广告并传入自定义广告位 ID |

> 传入的 ID 为 `null` 或空串时一律回退到 `utils/Id.java` 的示例值；
> 快手、Taptap、InMobi 的广告位 ID 为 `long`，传入非数字字符串时同样回退到示例值。

#### `LocalAdBridge`

| 成员 | 说明 |
| --- | --- |
| `onSplashAdLoaded: ((Context, ViewGroup) -> Unit)?` | 广告 View 就绪回调 |
| `onSplashAdFinished: ((Context) -> Unit)?` | 广告结束回调（关闭 / 失败 / 超时） |
| `onRewardVideoAdLoaded: ((Context) -> Unit)?` | 视频广告加载成功回调 |
| `onRewardVideoAdShow: ((Context) -> Unit)?` | 视频广告开始播放回调 |
| `onRewardVideoAdRewarded: ((Context, String, Int) -> Unit)?` | 视频广告发放奖励回调（激励名称、数量） |
| `onRewardVideoAdClose: ((Context) -> Unit)?` | 视频广告关闭回调 |
| `onRewardVideoAdError: ((Context, Int, String) -> Unit)?` | 视频广告加载 / 播放失败回调（错误码、错误信息） |
| `onInterstitialAdLoaded: ((Context) -> Unit)?` | 插屏加载成功回调 |
| `onInterstitialAdShow: ((Context) -> Unit)?` | 插屏开始展示回调 |
| `onInterstitialAdClose: ((Context) -> Unit)?` | 插屏关闭回调 |
| `onInterstitialAdError: ((Context, Int, String) -> Unit)?` | 插屏加载 / 展示失败回调（错误码、错误信息） |
| `onBannerAdLoaded: ((Context, ViewGroup) -> Unit)?` | Banner 渲染完成回调（广告 View 已加入容器） |
| `onBannerAdError: ((Context, Int, String) -> Unit)?` | Banner 加载 / 渲染失败回调（错误码、错误信息） |
| `clear()` | 清空开屏、视频广告、插屏与 Banner 的全部回调 |
| `clearRewardVideoAd()` | 仅清空视频广告（激励视频）的回调 |
| `clearInterstitialAd()` | 仅清空插屏广告的回调 |
| `clearBannerAd()` | 仅清空 Banner 广告的回调 |

#### `Init`

| 成员 | 说明 |
| --- | --- |
| `adSDKisLoaded: Map<AdPlatform, Boolean>` | 各平台 SDK 初始化状态，SDK 内部维护 |

#### `SplashAdCallback`

SDK 内部使用，使用方一般不需要直接调用。各平台实现在广告生命周期节点回调
`onSplashAdLoaded` 与 `goToMainActivity`，再由 `LocalAdBridge` 转发给页面。

#### `RewardVideoAdCallback`

SDK 内部使用，使用方一般不需要直接调用。各平台实现在激励视频的生命周期节点回调
`onRewardAdLoaded` / `onRewardAdShow` / `onRewardAdRewarded` / `onRewardAdClose` / `onRewardAdError`，
再由 `LocalAdBridge` 转发给页面。

#### `InterstitialAdCallback`

SDK 内部使用，使用方一般不需要直接调用。各平台实现在插屏的生命周期节点回调
`onInterstitialAdLoaded` / `onInterstitialAdShow` / `onInterstitialAdClose` / `onInterstitialAdError`，
再由 `LocalAdBridge` 转发给页面。

#### `BannerAdCallback`

SDK 内部使用，使用方一般不需要直接调用。各平台实现在 Banner 渲染完成、把广告 View 加入容器后回调
`onBannerAdLoaded`，加载 / 渲染失败时回调 `onBannerAdError`，再由 `LocalAdBridge` 转发给页面。

---

### 11. 支持的广告平台

| 平台 | 枚举值 | 开屏（竖屏） | 开屏（横屏） | 信息流 | 插屏 | Banner | 视频广告 |
| --- | --- | :---: | :---: | :---: | :---: | :---: | :---: |
| 穿山甲 | `CSJ` | ✓ | | ✓ | ✓ | ✓ | ✓ |
| 优量汇（广点通） | `GDT` | ✓ | | ✓ | ✓ | ✓ | ✓ |
| 百度 | `BAIDU` | ✓ | | ✓ | ✓ | | ✓ |
| 快手 | `KS` | ✓ | | ✓ | ✓ | ✓ | ✓ |
| Sigmob | `SIGMOB` | ✓ | | ✓ | ✓ | | ✓ |
| 米盟 | `MIMO` | ✓ | ✓ | | ✓ | ✓ | ✓ |
| 美数 | `MS` | ✓ | | ✓ | ✓ | ✓ | ✓ |
| 章鱼 | `OCTOPUS` | ✓ | | ✓ | ✓ | ✓ | ✓ |
| 京东 | `JD` | ✓ | | | ✓ | ✓ | |
| Taptap | `TAPTAP` | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| OSET | `OSET` | ✓ | | | ✓ | ✓ | ✓ |
| 启明 | `QIMING` | ✓ | | | ✓ | ✓ | ✓ |
| 华为 | `HW` | ✓ | | | ✓ | ✓ | ✓ |
| 倍孜 | `BEIZI` | ✓ | | | ✓ | | ✓ |
| AdMob | `ADMOB` | ✓ | | | ✓ | ✓ | ✓ |
| 阿里 Tanx | `TANX` | ✓ | | | | | |
| InMobi | `INMOBI` | | | ✓ | ✓ | ✓ | |
| 友盟+ U-AppWin | `UMENG` | ✓ | | ✓ | ✓ | ✓ | ✓ |
| 新浪移动联盟 | `SINA` | | | ✓ | | | |
| 爱奇艺联盟 | `QIYI` | ✓ | | ✓ | ✓ | ✓ | ✓ |

> - 「视频广告」列为激励视频（Rewarded Video）。京东、阿里 Tanx、InMobi、新浪移动联盟未提供激励视频格式，对它们调用 `loadRewardVideoAd` 会回调 `onRewardVideoAdError`。
> - 米盟（`MIMO`）的视频广告仅在小米设备可用，非小米设备会直接回调错误。
> - 「插屏」列为插屏广告（Interstitial），「Banner」列为横幅广告。阿里 Tanx、新浪移动联盟两种都不提供；百度、Sigmob、倍孜不提供 Banner。

> - 竖屏未覆盖的平台或方向不匹配时，`loadSplashAd` 会直接触发 `onSplashAdFinished`，页面应正常进入主页。
> - `getAdPlatform(String)` 未处理 `TANX`，使用字符串转换时请直接使用枚举。
> - 华为开屏的容器必须是 `SplashView`，SDK 内部会做强制类型转换。
> - InMobi（`INMOBI`）为海外平台，SDK 只提供 Banner / 插屏 / 原生三种格式，**没有开屏格式**，因此只支持信息流；对 `INMOBI` 调用 `loadSplashAd` 会直接走 `onSplashAdFinished`。
> - 新浪移动联盟（`SINA`）同样只有信息流一种形式，且 SDK 为普通 jar：`library` 清单已代为声明其 `SinaAdBrowser` / `SinaFeedAdBrowser` 两个 Activity，无需使用方重复声明。
> - 爱奇艺联盟（`QIYI`）为模板渲染模式，开屏 / 信息流 / Banner / 插屏 / 激励视频齐全，信息流与 Banner 共用同一套 `loadBannerAd` 请求接口、仅模板样式不同。SDK 的 AAR 自带清单已声明全部广告 Activity、`QyFileProvider`（`${applicationId}.qy.fileprovider`）与模拟器检测 Service，**无需使用方重复声明**；初始化必须在主线程调用，且 `QyCustomMade#getOaid()` 为必传项（缺失会明显影响广告转化效果）。
> - 友盟+ U-AppWin（`UMENG`）开屏由 SDK 自行渲染进传入的容器，与倍孜的实现一致：先回调 `onSplashAdLoaded`，再由 `onDismissed` / `onError` 回调 `onSplashAdFinished`。
> - 优量汇（`GDT`）即腾讯广告联盟，也就是广点通，是同一平台、同一套 SDK（包名 `com.qq.e`，本地 AAR 为 `GDTSDK.unionNormal.*.aar`）。因此表中以「优量汇（广点通）」列示，**不存在两个独立平台**，接入时使用 `AdPlatform.GDT` / `GDTAd` / `Id.GDTId` 即可。

---

### 12. 广告位 ID 配置

所有示例广告位集中在 `library/src/main/java/com/FreshingAir/Ad/Aggregation/utils/Id.java`，
**当前全部为各开放平台提供的测试 ID，上线前必须替换为正式广告位**，否则会产生无效曝光。

有两种替换方式，二选一：

1. **直接改 `Id.java`**：适合全局统一替换；
2. **调用时传入**：`initSDKByAdPlatform` / `loadSplashAd` / `loadFeedAd` 的重载方法都支持传入自定义 ID，
   适合多套广告位、按渠道下发配置的场景，不传则回退到 `Id.java` 的示例值。

| 平台 | 内部类 | 关键字段 |
| --- | --- | --- |
| 穿山甲 | `CsjId` | `APP_ID`、`SPLASH_ID`、`NATIVE_RECYCLERVIEW_ID`、`INTER_ID`、`BANNER_ID`、`REWARD_ID` |
| 优量汇（广点通） | `GDTId` | `APP_ID`、`SPLASH_ID`、`NATIVE_EXPRESS_ID_PICTURE_VIDEO`、`INTERTERISTAL_ID`、`BANNER_ID`、`REWARD_VIDEO_AD_ID_SUPPORT_H` |
| 百度 | `BaiduId` | `APP_ID`、`SPLASH_ID`、`NATIVE_SIMPLE_ID`、`INTER_ID`、`REWARD_ID` |
| 快手 | `KsId` | `APP_ID`、`SPLASH_ID`、`FEED_ID`、`INTER_ID`、`BANNER_ID`、`REWARD_ID` |
| Sigmob | `SigmobId` | `APP_ID`、`APP_KEY`、`SPLASH_ID`、`INTER_ID`、`FEED_ID`、`REWARD_ID` |
| 米盟 | `MimoId` | `SPLASH_ID`、`INTER_ID`、`BANNER_ID`、`REWARD_ID`（需在米盟后台创建） |
| 美数 | `MSId` | `APP_ID`、`SPLASH_ID`、`INTER_ID`、`BANNER_ID`、`FEED_ID`、`REWARD_ID` |
| 章鱼 | `OctopusId` | `APP_ID`、`SPLASH_ID`、`INTER_ID`、`BANNER_ID`、`NATIVE_RECYCLERVIEW_ID`、`REWARD_ID` |
| 京东 | `JdId` | `APP_ID`、`ESPLASH_ID`、`INTER_ID`、`BANNER_ID` |
| Taptap | `TaptapId` | `MEDIA_ID`、`MEDIA_KEY`、`SPLASH_ID`、`INTER_FULL_ID`、`BANNER_ID`、`REWARD_ID` |
| OSET | `OpenSetId` | `APP_KEY`、`SPLASH_ID`、`INTER_ID`、`BANNER_ID`、`REWARD_ID` |
| 启明 | `QiMingId` | `INTER_ID`、`BANNER_ID`、`REWARD_ID`（需在启明后台创建） |
| 华为 | `HwId` | `SPLASH_ID_PORTRAIT`、`INTER_ID_VIDEO`、`BANNER_ID`、`REWARD_ID` |
| 倍孜 | `BeiziId` | `SPLASH_ID`、`INTER_ID`、`REWARD_ID` |
| AdMob | `AdMobId` | `APP_ID`、`SPLASH_ID`、`INTER_ID`、`BANNER_ID`、`REWARD_ID` |
| 阿里 Tanx | `TanxId` | `APP_ID`、`APP_KEY`（SDK 无插屏 / Banner 格式） |
| InMobi | `InMobiId` | `APP_ID`、`FEED_ID`、`INTER_ID`、`BANNER_ID`（真实值需在 InMobi 后台创建，见下） |
| 友盟+ U-AppWin | `UmengId` | `APP_KEY`、`SPLASH_ID`、`FEED_ID`、`INTER_ID`、`BANNER_ID`、`REWARD_ID`（真实值需在友盟后台创建，见下） |
| 新浪移动联盟 | `SinaId` | `APP_KEY`、`APP_RID`（当前为官方 Sample 的示例值；SDK 无插屏 / Banner 格式） |
| 爱奇艺联盟 | `QiYiId` | `APP_ID`、`OAID`、`SPLASH_ID`、`FEED_ID`、`BANNER_ID`、`INTER_ID`、`REWARD_ID`（当前为官方 Demo 示例值，`FEED_ID` 与 `BANNER_ID` 暂共用同一测试位） |

> 米盟、启明、友盟未提供公共测试激励视频广告位，`REWARD_ID` 默认为空串，需在对应平台后台创建后通过
> `loadRewardVideoAd(activity, platform, rewardAdId)` 传入，否则会直接回调 `onRewardVideoAdError`。

> 插屏 / Banner 广告位同理：米盟、启明、京东、友盟、倍孜、美数未提供公共测试值，对应 `INTER_ID` /
> `BANNER_ID` 默认为空串，需在平台后台创建后通过 `loadInterstitialAd(activity, platform, adId)` /
> `loadBannerAd(activity, container, platform, adId)` 传入；快手的 `BANNER_ID` 为占位示例值，上线前必须替换。

> 启明（`QIMING`）与 Tanx 的开屏广告位在 `loadAdByType` 中为 `TODO` 占位，
> 接入时通过 `loadSplashAd(context, platform, container, id)` 传入，或直接替换占位串。

AdMob 还需在清单中配置 `com.google.android.gms.ads.APPLICATION_ID`，替换 `library` 清单里的示例值。

> 爱奇艺联盟的 AppId 与广告位同样取自官方 Demo。**OAID 为初始化必传项**，缺失会明显影响广告转化效果：
> 可通过 `initSDKByAdPlatform(context, AdPlatform.QIYI, appId, oaid)` 传入，或在拿到 OAID 后调用
> `QiYiAd.setOaid(oaid)`。本工程未内置 OAID SDK 采集，`Id.QiYiId.OAID` 默认为空串。
> `library` 未接入微信开放平台 SDK，因此爱奇艺的「微信生态链路预算广告」（小程序落地页）需使用方自行补充依赖。

> InMobi 不提供公共测试账号：`Id.InMobiId.APP_ID`（Account ID）与 `FEED_ID`（原生广告位）默认为空串 / `0`，
> 需在 InMobi 后台创建后填写，或通过 `initSDKByAdPlatform(context, AdPlatform.INMOBI, appId, null)` 与
> `loadFeedAd(activity, container, AdPlatform.INMOBI, feedAdId)` 传入。未配置时 SDK 初始化会回调
> `INVALID_ACCOUNT_ID`，信息流加载直接跳过（不会崩溃）。`Id.InMobiId` 中另附了官方长期开放的
> 测试插屏 / 激励广告位，可用于验证 SDK 初始化链路。

---

### 13. 混淆规则

`library/build.gradle` 中通过 `consumerProguardFiles 'proguard-rules.pro'` 声明，
使用方开启 `minifyEnabled` 后规则会自动生效（已包含各平台 `-keep` / `-dontwarn`）。

两点需要在使用方自行处理：

1. Sigmob 原工程要求 `-dontoptimize`，而 AGP 不允许在 consumer 规则中声明全局优化选项，
   如需保留请在使用方 `proguard-rules.pro` 中自行添加；
2. 若使用方对 `library` 包名做了混淆/加固，需保证 `AdPlatform`、`loadAdByType`、`LocalAdBridge`、`SplashAdCallback`、`InterstitialAdCallback`、`BannerAdCallback`、`RewardVideoAdCallback`
   等入口类不被裁剪（SDK 源码中已通过 `@Keep` 标注，也可由使用方 proguard 规则保留）。

---

### 14. 注意事项

- **回调时机**：必须等 `onSplashAdLoaded` 拿到广告 View 后再 `addView`，不要自行预估时间。
- **回调唯一**：`LocalAdBridge` 只保存最后一次注册的回调，多页面使用时注意注册时机与 `clear()`。
- **视频广告上下文**：`loadRewardVideoAd` 的第一个参数必须是 `Activity`（全屏播放依赖 Activity），传入非 Activity 的 `Context` 会直接回调错误。
- **视频广告奖励**：`onRewardVideoAdRewarded` 仅表示用户满足发奖条件，使用方应在此发放奖励；部分平台一次播放可能回调多次，如需严格去重请在业务侧做幂等处理。
- **ABI**：SDK 与使用方均限制 `arm64-v8a` / `armeabi-v7a`，模拟器（x86）下广告可能无法加载。
- **插屏上下文**：`loadInterstitialAd` 的第一个参数必须是 `Activity`（插屏全屏展示依赖 Activity），传入非 Activity 的 `Context` 会直接回调错误。
- **插屏自动展示**：多数平台在加载成功后由 SDK 立即展示；未实现插屏的平台会直接回调 `onInterstitialAdError`。
- **Banner 容器**：`loadBannerAd` 的容器宽度建议占满屏幕；广告 View 由 SDK 自行加入容器，不要在回调前抢先 `removeAllViews()`。百度、Sigmob、倍孜不支持 Banner 时会直接回调 `onBannerAdError`。
- **横竖屏**：`loadSplashAd` 依据 `Configuration.orientation` 分发，横屏仅支持米盟与 Taptap。
- **开屏容器尺寸**：容器需占满可展示区域，部分平台按容器尺寸请求素材。
- **明文流量**：`library` 清单已开启 `usesCleartextTraffic`，部分平台素材依赖 HTTP。
- **爱奇艺初始化线程**：爱奇艺联盟 SDK 要求 `QySdk.init` 在主线程调用，非主线程会抛 `Wrong Thread! Please init QySdk in main thread.`；`loadAdByType` 会在加载前按需初始化，确保在 Activity / Application 的主线程发起请求即可。
- **爱奇艺回调线程**：其 Banner 与激励视频的交互回调运行在 SDK 子线程，`QiYiAd` 已统一切回主线程后再对外转发，使用方无需额外做线程切换。

---

### 15. 构建与产物

```bash
./gradlew :library:assembleDebug      # 产物：library/build/outputs/aar/library-debug.aar
./gradlew :library:assembleRelease    # 产物：library/build/outputs/aar/library-release.aar
./gradlew :adexample:assembleDebug    # 产物：adexample/build/outputs/apk/debug/adexample-debug.apk
```

`adexample` 是完整的接入参考实现，界面全部由 AndroidX 基础控件构成（AppCompat + ViewBinding），
**不使用 Compose，也不依赖 Material Components**：

| 文件 | 职责 |
| --- | --- |
| `SplashActivity`（launcher） | 用 AndroidX 权限 API 申请运行时权限 → 注册 `LocalAdBridge` 回调 → 加载开屏广告 → 广告结束 / 超时 / 点击“跳过”后进入主页；目标平台为华为时自动切换到 `SplashView` 容器 |
| `MainActivity` | 下拉选择平台，发起开屏（跳转启动页）、信息流、插屏、Banner 与视频广告请求、清空容器，并在日志面板查看回调时序与 `Init.adSDKIsLoaded` 初始化状态 |
| `AllAdsActivity` | 全广告页：进入即按 SDK 轮流加载全部广告形式（开屏 / 信息流 / 插屏 / Banner / 视频广告），请求逐个派发、开屏展示期间挂起队列，避免请求过于频繁；页面只保留广告容器 |
| `AdDemo` | 平台清单（开屏 18 家 / 信息流 12 家 / 插屏 18 家 / Banner 15 家 / 视频广告 16 家）与运行时权限列表 |
| `AdLog` | 回调日志缓冲，按时间记录并同步输出到 Logcat（tag：`AdExample`） |

示例的 `build.gradle` 中同样把 `sourceCompatibility` / `targetCompatibility` 设为 17，
并把 ABI 限制为 `arm64-v8a` / `armeabi-v7a`，与 `library` 保持一致。
