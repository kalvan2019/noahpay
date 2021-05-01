import * as React from "react";
import { Dropdown, Menu, Button } from "antd";
import { FormattedMessage } from "react-intl";
import { DownOutlined } from "@ant-design/icons";
import { SharedProps } from "./interface";

export function getEcosystemGroup({ isZhCN }: SharedProps): React.ReactElement {
  return (
    <Menu.ItemGroup
      key="ecosystem"
      title={<FormattedMessage id="app.header.menu.ecosystem" />}
    >
      <Menu.Item key="course1" className="hide-in-home-page">
        <a
          href="http://www.baidu.com"
          className="header-link"
          target="_blank"
          rel="noopener noreferrer"
        >
          <FormattedMessage id="app.header.menu.link1" />
        </a>
      </Menu.Item>
      <Menu.Item key="course2" className="hide-in-home-page">
        <a
          href="http://www.baidu.com"
          className="header-link"
          target="_blank"
          rel="noopener noreferrer"
        >
          <FormattedMessage id="app.header.menu.link2" />
        </a>
      </Menu.Item>
    </Menu.ItemGroup>
  );
}

export default (props: SharedProps) => {
  const menu = <Menu>{getEcosystemGroup(props)}</Menu>;
  const downstyle = props.isRTL ? "-1px 2px 0 0" : "-1px 0 0 2px";
  return (
    <Dropdown overlay={menu} placement="bottomRight">
      <Button size="small" className="header-button">
        <FormattedMessage id="app.header.menu.more" />
        <DownOutlined
          style={{
            fontSize: "9px",
            margin: downstyle,
            verticalAlign: "middle",
          }}
        />
      </Button>
    </Dropdown>
  );
};
