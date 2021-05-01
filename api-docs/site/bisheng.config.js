const path = require("path");
const replaceLib = require("@ant-design/tools/lib/replaceLib");
const getWebpackConfig = require("@ant-design/tools/lib/getWebpackConfig");
const { version } = require("../package.json");
const themeConfig = require("./themeConfig");
const { webpack } = getWebpackConfig;

function alertBabelConfig(rules) {
  rules.forEach((rule) => {
    if (rule.loader && rule.loader === "babel-loader") {
      if (rule.options.plugins.indexOf(replaceLib) === -1) {
        rule.options.plugins.push(replaceLib);
      }
      rule.options.plugins = rule.options.plugins.filter(
        (plugin) =>
          !plugin.indexOf ||
          plugin.indexOf("babel-plugin-add-module-exports") === -1
      );
      // Add babel-plugin-add-react-displayname
      rule.options.plugins.push(
        require.resolve("babel-plugin-add-react-displayname")
      );
    } else if (rule.use) {
      alertBabelConfig(rule.use);
    }
  });
}

module.exports = {
  port: 9000,
  hash: true,
  source: {
    components: "./components",
    docs: "./docs",
  },
  theme: "./site/theme",
  htmlTemplate: "./site/theme/static/template.html",
  themeConfig,
  doraConfig: {
    verbose: true,
  },
  lessConfig: {
    javascriptEnabled: true,
  },
  webpackConfig(config) {
    config.resolve.alias = {
      "apiDocs/lib": path.join(process.cwd(), "components"),
      "apiDocs/es": path.join(process.cwd(), "components"),
      site: path.join(process.cwd(), "site"),
      "react-router": "react-router/umd/ReactRouter",
    };
    config.externals = {
      "react-router-dom": "ReactRouterDOM",
    };
    alertBabelConfig(config.module.rules);
    config.module.rules.push({
      test: /\.mjs$/,
      include: /node_modules/,
      type: "javascript/auto",
    });
    config.plugins.push(
      new webpack.DefinePlugin({
        antdReproduceVersion: JSON.stringify(version),
      })
    );
    delete config.module.noParse;
    return config;
  },
};
