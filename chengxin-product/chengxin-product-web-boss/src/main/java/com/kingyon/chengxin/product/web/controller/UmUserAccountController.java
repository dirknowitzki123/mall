package com.kingyon.chengxin.product.web.controller;

import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.exception.WebException;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.UmUserAccountService;
import com.kingyon.chengxin.product.dto.UserAccountDto;
import com.kingyon.chengxin.product.dto.query.OrderQuery;
import com.kingyon.chengxin.product.dto.query.UserAccountQuery;
import com.kingyon.chengxin.product.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Created: 2018/11/16 0016.
 */
@RestController
@RequestMapping("/boss/account")
public class UmUserAccountController {


    @DubboReference
    UmUserAccountService userAccountService;

    @PostMapping("/v1/userAccountList")
    public Response userAccountList(@ModelAttribute UserAccountQuery query) throws Exception {
        PageDto pageDto = userAccountService.userAccountList(query);
        return new Response(pageDto);
    }

    @PostMapping("/v1/userAccountDetail")
    public Response userAccountDetail(@RequestParam("id") Long id) throws Exception {
        UserAccountDto userAccountDto = userAccountService.userAccountDetail(id);
        return new Response(userAccountDto);
    }

    @PostMapping("/v1/userAccountRemark")
    public Response userAccountRemark(@RequestParam("id") Long id, @RequestParam("remark") String remark) throws Exception {
        Boolean result = userAccountService.userAccountRemark(id, remark);
        if (!result) {
            throw new WebException(ProductErrorCode.SAVE_FAIL);
        }
        return new Response(result);
    }


    @RequestMapping(value = "/v1/exportUserAccount", method = {RequestMethod.POST})
    public String createExcel(@ModelAttribute UserAccountQuery query, HttpServletResponse response) throws IOException {
        PageDto pageDto = userAccountService.userAccountList(query);
        List list = pageDto.getDataList();

        String[] titleArr = {"昵称", "账号", "支付次数", "消费总额", "注册时间", "备注"};
        String[][] contentArr = new String[list.size()][7];
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> param = (Map) list.get(i);
            contentArr[i][0] = (String) param.get("nickName");
            contentArr[i][1] = (String) param.get("accountName");
            contentArr[i][2] = String.valueOf(param.get("buyNum"));
            contentArr[i][3] = String.valueOf(param.get("amount"));
            contentArr[i][4] = String.valueOf(param.get("createTime"));
            contentArr[i][5] = (String) param.get("remark");
        }

        String sheetName = "用户列表列表";
        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook(sheetName, titleArr, contentArr);
        //输出Excel文件
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=userlist.xls");//文件名这里可以改
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/msexcel");
        workbook.write(output);
        output.close();
        return null;
    }


}
