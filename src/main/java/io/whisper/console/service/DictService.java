package io.whisper.console.service;

import java.util.List;
import io.whisper.console.entity.WhisperDict;
import io.whisper.console.pojo.TreeNode;
import io.whisper.console.service.base.WhisperBaseService;

public interface DictService extends WhisperBaseService {

    List<TreeNode> getTreeData();

    List<WhisperDict> getDictsByCode(String code);
}
