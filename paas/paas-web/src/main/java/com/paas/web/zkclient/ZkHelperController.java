package com.paas.web.zkclient;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * create by sumerian on 2020/6/18
 * <p>
 * desc:zk 相关的操作辅助
 **/
@RestController
@RequestMapping("/access/helper/zk")
@Slf4j
public class ZkHelperController {

    @Autowired
    private ZKClientOperation operation;

    /***
     * 查询path下的所有节点
     * @param path
     * @return
     * @throws Exception
     */
    @GetMapping("/findPath")
    public List<String> findPath(String path) throws Exception {
        return new ArrayList<>(operation.getZkClient().getChildren().forPath(path));
    }

    @GetMapping("/data")
    public String dataPath(String path) throws Exception {
        return new String(operation.getZkClient().getData().forPath(path), "UTF-8");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ZKPath {
        private String path;
        private List<ZKPath> children;
    }

    /****
     * 递归查询path下的所有子节点
     * @param path
     * @return
     * @throws Exception
     */
    @GetMapping("/treePath")
    public List<ZKPath> treePath(String path) throws Exception {
        List<ZKPath> paths = findPath(path).stream().map(str -> new ZKPath(Objects.equals(path, "/") ? "" : path + "/" + str, Lists.newArrayList()))
                .collect(Collectors.toList());
     for(ZKPath zkPath:paths){
        getNode(zkPath);
     }
       return paths;
    }

    private void getNode(ZKPath parent) throws Exception {
      for(String path:findPath(parent.path)){
          ZKPath children = new ZKPath(parent.path + "/" + path, Lists.newArrayList());
         parent.getChildren().add(children);
         getNode(children);
      }

    }
}
