const path = require("path");

function pickerGenerator(module) {
  const tester = new RegExp(`^docs/${module}`);
  return (markdownData) => {
    const { filename } = markdownData.meta;
    if (tester.test(filename)) {
      return {
        meta: markdownData.meta,
      };
    }
    return null;
  };
}

module.exports = {
  lazyLoad: true,
  pick: {
    components(markdownData) {
      return {
        meta: markdownData.meta,
      };
    },
    "docs/api": pickerGenerator("api"),
    "docs/learn": pickerGenerator("learn"),
    "docs/version": pickerGenerator("version"),
  },
  plugins: [
    "bisheng-plugin-description",
    "bisheng-plugin-toc?maxDepth=2&keepElem",
    "@ant-design/bisheng-plugin?injectProvider",
    "bisheng-plugin-react?lang=__react",
  ],
  routes: {
    path: "/",
    component: "./template/Layout/index",
    childRoutes: [
      {
        path: "docs/index",
        component: "./template/Home/index",
      },
      {
        path: "docs/index-cn",
        component: "./template/Home/index",
      },
      {
        path: 'docs/resources',
        component: "./template/Resources/index",
      },
      {
        path: 'docs/resources-cn',
        component: "./template/Resources/index",
      },
      {
        path: "docs/api/:children",
        component: "./template/Content/index",
      },
      {
        path: "docs/learn/:children",
        component: "./template/Content/index",
      },
      {
        path: "docs/version/:children",
        component: "./template/Content/index",
      },
    ],
  },
};
