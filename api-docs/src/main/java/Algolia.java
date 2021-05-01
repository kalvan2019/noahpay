import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;


/**
 * @author chenliang
 */
public class Algolia {
  public static void main(String[] args) throws Exception {
    codeConvert();
  }

  /**
   * 将文件生成algolia索引数据格式
   * "anchor": "引入-antd",
   * "content": null,
   * "hierarchy": {
   * "lvl0": "Documentation",
   * "lvl1": "在 create-react-app 中使用",
   * "lvl2": "引入 antd ",
   * "lvl3": null,
   * "lvl4": null,
   * "lvl5": null,
   * "lvl6": null
   * },
   * "url": "https://ant.design/docs/react/use-with-create-react-app-cn#引入-antd"*  }
   */
  public static void codeConvert() throws Exception {
    // GBK编码格式源码路径
    String srcDirPath = "/Users/chenliang/dev/workspace/spring_thunder/kalvan-pay/api-docs/docs";
    // 获取所有java文件
    Collection<File> files = FileUtils.listFiles(new File(srcDirPath), new String[]{"md"}, true);
    List list = new ArrayList();
    for (File file : files) {
      if (!file.getName().endsWith(".zh-CN.md")) {
        continue;
      }
      //固定
      String lvl0 = "Documentation";
      //文件名处理中文
      String fileName = file.getName().split("\\.")[0];
      if (file.getName().endsWith(".zh-CN.md")) {
        fileName = fileName + "-cn";
      }
      //目录判断
      String dir = file.getParentFile().getName();
      if (dir.equals("api")) {
        continue;
      }
      if (!dir.equals("docs")) {
        fileName = dir + "/" + fileName;
      }
      Collection<String> lines = FileUtils.readLines(file);
      String lvl1 = "";
      String lvl2 = "";
      for (String line : lines) {
        //解析文件中的title:
        if (line.startsWith("title:")) {
          lvl1 = line.substring(6);
        }
        //解析文件中的##
        if (!line.startsWith("## ")) {
          continue;
        }
        lvl2 = line.substring(3);
        String anchor = lvl2;
        String url = String.format("https://kalvan.store/docs/%s#%s", fileName, lvl2);
        //algolia数据
        Map object = new HashMap();
        object.put("anchor", anchor);
        object.put("url", url);
        Map objectHierarchy = new HashMap();
        objectHierarchy.put("lvl0", lvl0);
        objectHierarchy.put("lvl1", lvl1);
        objectHierarchy.put("lvl2", lvl2);
        object.put("hierarchy", objectHierarchy);
        list.add(object);
      }
    }
    System.out.println(JSON.toJSONString(list));
  }
}
