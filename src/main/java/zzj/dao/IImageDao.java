package zzj.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import zzj.bean.Image;

public interface IImageDao {

    /* find */
    Image findById(int imageId);
    List<Integer> findIdList();
    List<Integer> findIdListByKeyword(@Param("keyword") String keyword);
    List<Integer> findIdListByDate(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);
    
    /* insert */
    int insertRecord(Image image);
    
    /* update */
    int updateDescriptionById(@Param(value="imageId") int imageId, @Param(value="description") String description);
    
    /* delete */
    int deleteRecordById(int imageId);
}
