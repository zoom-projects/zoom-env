package com.hb0730.zoom.remote;

import com.hb0730.zoom.base.data.Domain;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import com.hb0730.zoom.sofa.rpc.core.RpcApi;

import java.util.List;

/**
 * @param <Q>         query
 * @param <V>         view
 * @param <CreateReq> create request
 * @param <UpdateReq> update request
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/21
 */
public interface IRemoteService<Q extends PageRequest, V extends Domain, CreateReq extends Domain,
        UpdateReq extends Domain> extends RpcApi {

    /**
     * page query
     *
     * @param query query
     * @return page
     */
    Page<V> page(Q query);

    /**
     * list query
     *
     * @param query query
     * @return list
     */
    List<V> list(Q query);

    /**
     * save
     *
     * @param createReq .
     * @return .
     */
    boolean create(CreateReq createReq);

    /**
     * save
     *
     * @param createReq .
     * @return .
     */
    V createReturn(CreateReq createReq);

    /**
     * update
     *
     * @param updateReq .
     * @return .
     */
    boolean updateById(String id, UpdateReq updateReq);

    /**
     * update
     *
     * @param updateReq .
     * @return .
     */
    V updateReturnById(String id, UpdateReq updateReq);

    /**
     * get by id
     *
     * @param id id
     * @return .
     */
    V get(String id);

    /**
     * delete by id
     *
     * @param id id
     * @return boolean
     */
    boolean deleteById(String id);
}
