package io.whisper.console.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import io.whisper.console.entity.WhisperDict;
import io.whisper.console.pojo.Result;
import io.whisper.console.pojo.TreeNode;
import io.whisper.console.utils.StrUtil;
import io.whisper.console.service.DictService;
import io.whisper.console.constant.RedisConstant;

@Controller
@RequestMapping(value = "/dict")
public class DictController {

    @Resource
    private DictService dictService;

    /**
     * 用户列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tree")
    private String list() {

        return "base/dict/dict_tree";
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public List<WhisperDict> getAll() {

        String hql = "from Dict order by levelCode asc";
        return dictService.find(hql.toString());
    }

    /**
     * getTreeData 构造bootstrap-treeview格式数据
     *
     * @return
     */
    @RequestMapping(value = "/treeData", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeNode> getTreeData() {

        return dictService.getTreeData();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    @ResponseBody
    public WhisperDict get(@PathVariable("id") String id) {
        WhisperDict dict = dictService.get(WhisperDict.class, id);
        if (!StrUtil.isEmpty(dict.getParentId())) {
            dict.setParentName(dictService.get(WhisperDict.class, dict.getParentId()).getName());
        } else {
            dict.setParentName("系统字典");
        }
        return dict;
    }

    @RequestMapping(value = "/getDictsByCode", method = RequestMethod.POST)
    @ResponseBody
    public List<WhisperDict> getDictsByCode(String code) {
        return dictService.getDictsByCode(code);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(WhisperDict dict) {

        dict.setUpdateDateTime(new Date());
        dictService.saveOrUpdate(dict);
        if (!StrUtil.isEmpty(dict.getParentId())) {
        	WhisperDict parent = dictService.get(WhisperDict.class, dict.getParentId());
            dictService.deleteCacheByKey(RedisConstant.DICT_PRE + parent.getCode());
        }
        dictService.deleteCacheByKey(RedisConstant.DICT_PRE+"tree");
        return new Result(true);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable("id") String id) {

        try {
            WhisperDict dict = dictService.get(WhisperDict.class, id);
            dictService.delete(dict);
            if (!StrUtil.isEmpty(dict.getParentId())) {
                WhisperDict parent = dictService.get(WhisperDict.class, dict.getParentId());
                dictService.deleteCacheByKey(RedisConstant.DICT_PRE + parent.getCode());
            }
            dictService.deleteCacheByKey(RedisConstant.DICT_PRE+"tree");
            return new Result(true);
        } catch (Exception e) {
            return new Result(false, "该字典已经被其他数据引用，不可删除");
        }
    }

}
