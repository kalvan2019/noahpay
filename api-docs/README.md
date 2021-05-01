##  编译配置文件 `site/bisheng.config.js`
```
port: 9000, //服务监听的端口号
source: {
  components: "./components", //API放置的位置
  docs: "./docs", //介绍文档放置的位置
},
theme: "./site/theme", //文档主题，可以修改成自己喜欢的，或者用bisheng中原有的
htmlTemplate: './site/theme/static/template.html',
```

## 路由配置`site/theme/index.js`

## 菜單配置
```
./site/theme/template/Layout/Header/Navigation.tsx
./site/theme/template/Layout/Header/More.tsx
```

## 中文菜单配置`site/theme/zh-CN.js`

## 英文菜单配置`site/theme/zh-US.js`

## 安装依赖
> 使用`v14.16.0`
```
npm install
```

## 编译
```
npm run buid
```

## 运行
```
npm run start
```
