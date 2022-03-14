package com.base.myFramework.mapper;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author sx
 * @date: 2022/03/14
 * @description: mapstruct示例
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        imports = {}
)
public interface ExampleMapper {

    ExampleMapper INSTANCE = Mappers.getMapper(ExampleMapper.class);
    // 下面即可写转换方法

}
