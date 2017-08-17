package io.whisper.console.controller.base;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import io.whisper.console.utils.ObjectUtil;
import io.whisper.console.utils.StrUtil;
import io.whisper.console.service.base.WhisperBaseService;
import io.whisper.console.pojo.Result;

@Controller
@RequestMapping("/base")
public class WhisperBaseController {

    @Resource
    private WhisperBaseService whisperBaseService;

    /**
     * 唯一性校验
     *
     * @param className  类名
     * @param fieldName  属性名
     * @param fieldValue 属性值
     * @param id         当前对象id
     * @return Map 校验通过
     */
    @RequestMapping("/checkUnique")
    @ResponseBody
    public Map checkUnique(String className, String fieldName, String fieldValue, String id) {

        Map<String, Boolean> map = new HashMap<String, Boolean>();
        try {
            Class<?> objClass = Class.forName(className);
            Object obj = objClass.newInstance();
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = objClass.getMethod(ObjectUtil.methodName(fieldName, "set"), String.class);
            method.invoke(obj, fieldValue);
            String condition = null;
            if (!StrUtil.isEmpty(id))
                condition = "{alias}.id!='" + id + "'";
            List objList = whisperBaseService.findByExample(obj, condition, false);
            map.put("valid", objList.isEmpty());
            return map;
        } catch (Exception ex) {
            System.out.println(ex.getMessage().toString());
            map.put("valid", false);
            return map;
        }
    }

    @RequestMapping("/getServerTime")
    @ResponseBody
    public Date getServerTime() {
        return new Date();
    }

    /**
     * 通用删除记录
     *
     * @param className 类名
     * @param id        主键id
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    public Result deleteObj(String className, String id) {

        try {
            Assert.notNull(id, "id 不能为空");
            Class<?> objClass = Class.forName(className);
            Object obj = whisperBaseService.get(objClass, id);
            whisperBaseService.delete(obj);
            return new Result(true, "删除成功！");
        } catch (ClassNotFoundException ex) {
            return new Result(false, "该类不存在！");
        } catch (IllegalArgumentException ex) {
            return new Result(false, "id不能为空！");
        } catch (Exception ex) {
            return new Result(false, "该数据已经被其他数据引用，不可删除！");
        }
    }

}
