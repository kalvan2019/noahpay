import * as React from "react";
import classNames from "classnames";
import { FormattedMessage } from "react-intl";
import { Link } from "bisheng/router";
import { UnorderedListOutlined } from "@ant-design/icons";
import { Menu } from "antd";
import { getEcosystemGroup } from "./More";
import * as utils from "../../utils";
import { SharedProps } from "./interface";

import "./Navigation.less";

export interface NavigationProps extends SharedProps {
  isMobile: boolean;
  isRTL: boolean;
  pathname: string;
  responsive: null | "narrow" | "crowded";
  location: { pathname: string; query: any };
  directionText: string;
  onLangChange: () => void;
  onDirectionChange: () => void;
}

export default ({
  isZhCN,
  isRTL,
  isMobile,
  pathname,
  responsive,
  location,
  onLangChange,
}: NavigationProps) => {
  const menuMode = isMobile ? "inline" : "horizontal";

  const module = pathname.split("/").slice(0, -1).join("/");
  let activeMenuItem = module || "home";
  if (location.pathname === "index" || location.pathname === "index-cn") {
    activeMenuItem = "docs/index";
  } else if (
    location.pathname === "docs/index" ||
    location.pathname === "docs/index-cn"
  ) {
    activeMenuItem = "docs/index";
  } else if (
    location.pathname === "docs/resources" ||
    location.pathname === "docs/resources-cn"
  ) {
    activeMenuItem = "docs/resources";
  }

  let additional: React.ReactNode = null;
  const additionalItems = [
    // <Menu.Item key="switch-lang" onClick={onLangChange}>
    //   <FormattedMessage id="app.header.lang" />
    // </Menu.Item>,
    getEcosystemGroup({ isZhCN, isRTL }),
  ];

  if (isMobile) {
    additional = additionalItems;
  } else if (responsive === "crowded") {
    additional = (
      <Menu.SubMenu key="additional" title={<UnorderedListOutlined />}>
        {additionalItems}
      </Menu.SubMenu>
    );
  }

  return (
    <Menu
      className={classNames("menu-site")}
      mode={menuMode}
      selectedKeys={[activeMenuItem]}
      id="nav"
    >
      <Menu.Item key="docs/index">
        <Link
          to={utils.getLocalizedPathname("/docs/index", isZhCN, location.query)}
        >
          <FormattedMessage id="app.header.menu.index" />
        </Link>
      </Menu.Item>
      <Menu.Item key="docs/resources">
        <Link
          to={utils.getLocalizedPathname("/docs/resources", isZhCN, location.query)}
        >
          <FormattedMessage id="app.header.menu.product" />
        </Link>
      </Menu.Item>
      <Menu.Item key="docs/api">
        <Link
          to={utils.getLocalizedPathname("/docs/api/readers", isZhCN, location.query)}
        >
          <FormattedMessage id="app.header.menu.api" />
        </Link>
      </Menu.Item>
      <Menu.Item key="docs/learn">
        <Link
          to={utils.getLocalizedPathname("/docs/learn/install-env", isZhCN, location.query)}
        >
          <FormattedMessage id="app.header.menu.documentation" />
        </Link>
      </Menu.Item>
      <Menu.Item key="docs/version">
        <Link
          to={utils.getLocalizedPathname("/docs/version/v1", isZhCN, location.query)}
        >
          <FormattedMessage id="app.header.menu.version" />
        </Link>
      </Menu.Item>
      <Menu.Item key="demo">
        <a
          href="http://baidu.com"
          target="_blank"
          rel="noopener noreferrer"
        >
          <FormattedMessage id="app.header.menu.demo" />
        </a>
      </Menu.Item>
      {additional}
    </Menu>
  );
};
