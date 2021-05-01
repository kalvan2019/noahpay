import cn.hutool.extra.validation.BeanValidationResult;
import cn.hutool.extra.validation.ValidationUtil;
import com.kalvan.client.constant.CommonReturnCode;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Response;
import com.noahpay.pay.TradeApplication;
import com.noahpay.pay.commons.db.trade.mapper.PayBillMapper;
import com.noahpay.pay.trade.bean.req.TransRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.iface.ITrans;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @author chenliang
 */
@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = {TradeApplication.class})
public class TradeTest {

    private static TransRequest transRequest;
    private static Response<TransResponse> transResponse;
    @Resource
    PayBillMapper payBillMapper;
    @Resource
    ITrans iTrans;

    @Test
    public void testValidate() {
        TransRequest request = new TransRequest();
        BeanValidationResult validate = ValidationUtil.warpValidate(request);
        if (!validate.isSuccess()) {
            throw new BizException(CommonReturnCode.PARAM_ILLEGAL.formatMessage(validate.getErrorMessages().stream().map(BeanValidationResult.ErrorMessage::getMessage).collect(Collectors.joining(","))));
        }
    }
}
