package com.free.dao.server;

import com.free.dao.model.ServerLog;
import com.free.dao.model.ServerLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ServerLogMapper {
    int countByExample(ServerLogExample example);

    int deleteByExample(ServerLogExample example);

    int insert(ServerLog record);

    int insertSelective(ServerLog record);

    List<ServerLog> selectByExampleWithBLOBs(ServerLogExample example);

    List<ServerLog> selectByExample(ServerLogExample example);

    int updateByExampleSelective(@Param("record") ServerLog record, @Param("example") ServerLogExample example);

    int updateByExampleWithBLOBs(@Param("record") ServerLog record, @Param("example") ServerLogExample example);

    int updateByExample(@Param("record") ServerLog record, @Param("example") ServerLogExample example);
}