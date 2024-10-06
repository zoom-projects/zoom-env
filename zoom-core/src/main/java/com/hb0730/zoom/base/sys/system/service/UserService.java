package com.hb0730.zoom.base.sys.system.service;

import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.base.sys.system.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户 SERVICE
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/23
 */
@Service
@Slf4j
public class UserService extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<UserMapper, SysUser> implements com.baomidou.mybatisplus.extension.service.IService<SysUser> {

}
