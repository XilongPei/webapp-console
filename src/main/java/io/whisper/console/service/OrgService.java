package io.whisper.console.service;

import io.whisper.console.entity.WhisperDict;
import io.whisper.console.entity.WhisperOrg;
import io.whisper.console.pojo.Result;
import io.whisper.console.pojo.TreeNode;
import java.util.List;
import io.whisper.console.service.base.WhisperBaseService;

public interface OrgService extends WhisperBaseService {

    List<TreeNode> getTreeData();

    List<WhisperOrg> getOrgsByCode(String code);

    boolean referByUser(String orgId);

    Result getOrgNames(String id);
}
