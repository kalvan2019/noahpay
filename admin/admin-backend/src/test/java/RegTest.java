import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegTest {
    public static void main(String[] args) {
        String returnDesc = "ReturnCode SUCCESS_IMPORT = new ReturnCode(\"0000\", \"成功新增{0}行数据{1}\");";
        String reg = "new ReturnCode\\((.*?)\\)";
        Pattern pattern = Pattern.compile(reg);
        Matcher m = pattern.matcher(returnDesc);
        if (m.find()) {
            String content = m.group(1);
            System.out.println(content);
        }
    }
}
