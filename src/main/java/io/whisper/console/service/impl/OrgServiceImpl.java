package io.whisper.console.service.impl;

import com.alibaba.fastjson.JSON;
import io.whisper.console.dao.RedisDao;
import io.whisper.console.entity.WhisperOrg;
import io.whisper.console.pojo.Result;
import io.whisper.console.pojo.TreeNode;
import io.whisper.console.service.OrgService;
import io.whisper.console.constant.RedisConstant;
import io.whisper.console.utils.StrUtil;
import io.whisper.console.utils.TreeUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("orgService")
public class OrgServiceImpl extends BaseServiceImpl implements OrgService {

    @Override
    public List<TreeNode> getTreeData() {

        // 获取数据
        String key = RedisConstant.ORG_PRE + "tree";
        List<TreeNode> tnlist = null;
        String tnStr = redisDao.get(key);
        if (!StrUtil.isEmpty(key)) {
            tnlist = JSON.parseArray(tnStr, TreeNode.class);
        }
        if (tnlist != null) {
            return tnlist;
        } else {
            String hql = "from Org order by levelCode asc";
            List<WhisperOrg> orgs = this.find(hql);
            Map<String, TreeNode> nodelist = new LinkedHashMap<String, TreeNode>();
            for (WhisperOrg org : orgs) {
                TreeNode node = new TreeNode();
                node.setText(org.getName());
                node.setId(org.getId());
                node.setParentId(org.getParentId());
                node.setLevelCode(org.getLevelCode());
                nodelist.put(node.getId(), node);
            }
            // 构造树形结构
            tnlist = TreeUtil.getNodeList(nodelist);
            redisDao.save(key, tnlist);
            return tnlist;
        }
    }

    public List<WhisperOrg> getOrgsByCode(String code) {
        String key = RedisConstant.ORG_PRE + code;
        List orgs = redisDao.get(key, List.class);
        if (orgs == null) {
            String hql = "from Org where code='" + code + "'";
            WhisperOrg org = this.get(hql);
            orgs = this.find("from Org where parentId='" + org.getId() + "' order by levelCode");
            redisDao.add(key, orgs);
            return orgs;
        } else {
            return orgs;
        }
    }

    public boolean referByUser(String orgId) {
        WhisperOrg org = this.get(WhisperOrg.class, orgId);
        String sql = "select id from tbl_user u left join tbl_org o on u.dept_id=o.id " +
                " where o.levelCode like :levelCode";
        Map<String,Object> param=new HashMap<>();
        param.put("levelCode",org.getLevelCode()+"%");
        List<Map<String, Object>> list=this.findMapBySql(sql,param);
        if(list.isEmpty())
            return false;
        else
            return true;
    }

    @Override
    public Result getOrgNames(String id) {
        String name=this.redisDao.get("org:"+id);
        if(StrUtil.isEmpty(name)) {
            WhisperOrg org = this.get(WhisperOrg.class,id);
            if(org.getLevelCode().length()==12){
                redisDao.save("org:"+id,org.getName());
                return new Result(true,org.getName(),"获取成功");
            }else{
                name = getOrgNames(org.getParentId()).getData().toString()+"->"+org.getName();
                redisDao.save("org:"+id,name);
                return new Result(true,name,"获取成功");
            }
        }else{
            return new Result(true,name,"获取成功");
        }
    }
}
